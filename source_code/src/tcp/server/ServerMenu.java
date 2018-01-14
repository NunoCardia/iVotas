package tcp.server;

import RMI.RMIServerInterface;
import common.Response;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by cyberfox21 on 20/10/17.
 */
public class ServerMenu implements Runnable{

    private static RMIServerInterface svInterface;
    private Thread t = new Thread(this);
    AtomicBoolean atomicBoolean;
    private BlockingQueue<Integer> integers;
    private String hostnameRMI;
    private int registryNumberRMI;


    public ServerMenu(RMIServerInterface svInterface,AtomicBoolean atomicBoolean,BlockingQueue<Integer> integers,String hostnameRMI,int registryNumberRMI) {
        this.svInterface = svInterface;
        this.atomicBoolean = atomicBoolean;
        this.integers = integers;
        this.hostnameRMI = hostnameRMI;
        this.registryNumberRMI = registryNumberRMI;
        t.start();

    }

    public void reconnect(){
        try {
            svInterface = (RMIServerInterface) LocateRegistry.getRegistry(
                    hostnameRMI, registryNumberRMI).lookup("server");
        } catch (NotBoundException e) {
            System.err.println(e.getMessage());
        } catch (AccessException e) {
            System.err.println(e.getMessage());
        } catch (RemoteException e) {
            System.err.println("O servidor RMI não está online...");
            System.exit(0);
        }
    }


    @Override
    public void run() {
        while (true){
            int dept_id=0,eleicao_id = 0,maquina_id = 0, eleicaoDeptId = 0, deptPessoa;
            String campo,valor,nome,tipoEleicao;
            Scanner sc = new Scanner(System.in);
            System.out.println("MESA DE VOTO (para fechar a mesa escreva \"quit\")");
            System.out.println("Nome do departamento: ");
            String dept_nome = sc.nextLine();
            Response answer,answer2 = null;
            try {
                answer = svInterface.verificaDepartamento(dept_nome);
                if(answer.isSuccess()){
                    dept_id = Integer.parseInt(answer.getInfo().get(0));
                }
                answer = svInterface.verificaDepartamentoMaquina(dept_id);
                if(!answer.isSuccess()){
                    System.out.println("Não se encontra registada nenhuma máquina associada a este departamento");
                    System.exit(0);
                }
                maquina_id = Integer.parseInt(answer.getInfo().get(0));
                answer = svInterface.verEleicaoDepartamento(dept_id);
                if(answer.getInfo().size() == 0){
                    System.out.println("Não existem eleições a decorrer neste momento para este departamento");
                    System.exit(0);
                }
                else answer.getInfo().forEach(System.out::println);
                System.out.println("Id da eleição: ");
                eleicao_id = Integer.parseInt(sc.nextLine());
                answer = svInterface.mostraEleicoesPassadas();
                for(int i = 0; i < answer.getInfo().size();i+=2){
                    if(Integer.parseInt(answer.getInfo().get(i)) == eleicao_id){
                        System.out.println("Votação para a eleição já fechou!!");
                        System.exit(0);
                    }
                }
                answer = svInterface.escolheEleicao(eleicao_id);
                tipoEleicao = answer.getInfo().get(1);
                if(tipoEleicao.equalsIgnoreCase("nucleo de estudantes")){
                    eleicaoDeptId = Integer.parseInt(answer.getInfo().get(2));
                }
                svInterface.novoEstado(eleicao_id,maquina_id,"mesa de voto","abriu mesa de voto",new Timestamp(new java.util.Date().getTime()),0);
                Response r = svInterface.mostraEleicoesPassadas();
                for(int i = 0; i < r.getInfo().size();i+=2){
                    if(Integer.parseInt(r.getInfo().get(i)) == eleicao_id){
                        System.out.println("Votação para a eleição já fechou!!");
                        svInterface.novoEstado(eleicao_id,maquina_id,"mesa de voto","fechou mesa de voto",new Timestamp(new java.util.Date().getTime()),0);
                        System.exit(0);
                    }
                }
                while (true){

                    System.out.println("Identificação do eleitor\n\nCampo: ");
                    campo = sc.nextLine();
                    if(campo.equalsIgnoreCase("quit")){
                        svInterface.novoEstado(eleicao_id,maquina_id,"mesa de voto","fechou mesa de voto",new Timestamp(new java.util.Date().getTime()),0);
                        System.exit(0);
                    }
                    System.out.println(campo+": ");
                    valor = sc.nextLine();
                    answer = svInterface.verificaUtilizador(campo,valor);
                    nome = answer.getInfo().get(0);
                    answer2 = svInterface.identificaVotante(nome,eleicao_id);
                    deptPessoa = Integer.parseInt(svInterface.deptPessoa(nome).getInfo().get(0));
                    if(tipoEleicao.equalsIgnoreCase("nucleo de estudantes")){
                        if(deptPessoa != eleicaoDeptId){
                            System.out.println("Pessoa não pertence a este departamento");
                            continue;
                        }
                        else {
                            if(answer.isSuccess() && !answer2.isSuccess()){
                                System.out.println("Utilizador autorizado a votar!!");
                                svInterface.novoEstado(eleicao_id,maquina_id,"mesa de voto","autenticou utilizador: "+nome,new Timestamp(new java.util.Date().getTime()),0);
                                atomicBoolean.set(true);
                                integers.put(eleicao_id);
                                integers.put(maquina_id);
                                integers.put(dept_id);
                            }
                            else {
                                System.out.println("Utilizador já votou para esta eleicao!!");
                            }
                        }
                    }
                    else {
                        if(answer.isSuccess() && !answer2.isSuccess()){
                            System.out.println("Utilizador autorizado a votar!!");
                            svInterface.novoEstado(eleicao_id,maquina_id,"mesa de voto","autenticou utilizador: "+nome,new Timestamp(new java.util.Date().getTime()),0);
                            atomicBoolean.set(true);
                            integers.put(eleicao_id);
                            integers.put(maquina_id);
                            integers.put(dept_id);
                        }
                        else {
                            System.out.println("Utilizador já votou para esta eleicao!!");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                try {
                    System.err.println(e.getMessage());
                    Thread.sleep(7000);
                    reconnect();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

}
