package tcp.server;

import RMI.RMIServerInterface;
import common.Configuracao;
import common.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cyberfox21 on 13/10/17.
 */
public class ConnectionTCP extends UnicastRemoteObject implements Runnable{

    Socket clientSocket;
    private static RMIServerInterface svInterface;
    static String hostname;
    private String data = "";
    static int eleicao_id,maquina_id,user_id,dept_id;
    BufferedReader inFromServer = null;
    PrintWriter outToServer;
    Configuracao config;
    Timer time = new Timer();
    String tipo;
    Thread t = new Thread(this);
    AtomicBoolean atomicBoolean;
    private BlockingQueue<Integer> integers;

    public ConnectionTCP(Socket AclientSocket, RMIServerInterface svInterface,AtomicBoolean atomicBoolean,BlockingQueue<Integer> integers) throws RemoteException {
        super();
        config = new Configuracao();
        this.svInterface = svInterface;
        this.atomicBoolean = atomicBoolean;
        this.integers = integers;
        try {
            clientSocket = AclientSocket;
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            t.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }



    @Override
    public void run() {
        String username = "";
        boolean loggedIn = false,printed = false;
        while(true){
            try {
                while(true){
                    if(atomicBoolean.compareAndSet(true, false)){
                        break;
                    }
                    if(!printed){
                        outToServer.println("type | status ; blocked | on");
                        outToServer.flush();
                        svInterface.novoEstado(eleicao_id,maquina_id,"maquina de voto","maquina de voto fechou",new Timestamp(new java.util.Date().getTime()),0);
                        printed = true;
                        eleicao_id = integers.take();
                        maquina_id = integers.take();
                        dept_id = integers.take();
                    }
                }
                if(printed){
                    outToServer.println("type | status ; blocked | off");
                    outToServer.flush();
                    svInterface.novoEstado(eleicao_id,maquina_id,"maquina de voto","maquina de voto abriu",new Timestamp(new java.util.Date().getTime()),0);
                }
                data = inFromServer.readLine();
                if(data.contains("type | login")){
                    username = getElementFromInput(data,"username").replaceAll("\\s+","");
                    String password = getElementFromInput(data,"password").replaceAll("\\s+","");
                    if(svInterface.verificaLogin(username,password).isSuccess()){
                        outToServer.println("type | status ; logged | on");
                        outToServer.flush();
                        user_id = Integer.parseInt(svInterface.verificaId(username).getInfo().get(0));
                        tipo = svInterface.tipoPessoa(user_id).getInfo().get(0);
                        loggedIn = true;
                        printed = false;
                        atomicBoolean.set(true);
                        Response answer = svInterface.boletimVoto(eleicao_id,tipo);
                        String toClient = "type | item_list ; item_count | "+answer.getInfo().size()+" ; ";
                        for(int i=0;i<answer.getInfo().size();i++){
                            String [] temp = answer.getInfo().get(i).split(" ");
                            toClient +="item_"+i+"_name | Lista "+temp[1].trim()+" ; ";
                        }
                        outToServer.println(toClient);
                        outToServer.flush();
                    }
                    else {
                        loggedIn = false;
                        atomicBoolean.set(false);
                        printed = false;
                    }
                }
                else if(data.contains("type | vote") && loggedIn){
                    String lista = getElementFromInput(data,"list").trim();
                    atomicBoolean.set(false);
                    printed = false;
                    String voto = listaParaResultados(lista,eleicao_id);
                    svInterface.novoEstado(eleicao_id,maquina_id,"voto","utilizador "+username+" votou",new Timestamp(new java.util.Date().getTime()),0);
                    switch (voto){
                        case "nulo":
                            svInterface.votar(user_id,dept_id,eleicao_id,"nulo",new Timestamp(new java.util.Date().getTime()));
                            break;
                        case "valido":
                            svInterface.votar(user_id,dept_id,eleicao_id,lista,new Timestamp(new java.util.Date().getTime()));
                            break;
                        case "branco":
                            svInterface.votar(user_id,dept_id,eleicao_id,"branco",new Timestamp(new java.util.Date().getTime()));
                            break;
                    }
                    outToServer.println("type | vote ; status | true");
                    outToServer.flush();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public String getElementFromInput(String data,String element){
        StringTokenizer demo = new StringTokenizer(data,";|");
        while(demo.hasMoreElements()){
            if(((String )demo.nextElement()).contains(element) || demo.nextElement().equals(element) ) return demo.nextElement().toString();
        }
        return null;
    }

    public String listaParaResultados(String lista,int eleicao_id) throws RemoteException{
        if(lista.isEmpty() || lista.replaceAll("\\s+","").isEmpty()){
            return "branco";
        }
        else if(!lista.matches("[a-zA-Z0-9 ]*") || !svInterface.verificaLista(eleicao_id,lista).isSuccess()){
            return "nulo";
        }
        else
            return "valido";

    }


}
