package consola;

import RMI.RMIServerInterface;
import common.Configuracao;
import common.Response;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;


/**
 * Created by cyberfox21 on 15/10/17.
 */
public class AdminConsole {

    private static int registryNumber;
    private static String hostname;
    private static Configuracao configuracao;
    private static RMIServerInterface svInterface;


    public static void configAdminConsole() {
        configuracao = new Configuracao();
        hostname = configuracao.getConsole_host();
        registryNumber = configuracao.getConsole_port();
    }

    public static void connectToRMI(){
        try {
            System.out.println("hostname and registry number: " + hostname + registryNumber);
            svInterface = (RMIServerInterface) LocateRegistry.getRegistry(
                    hostname, registryNumber).lookup("server");


        } catch (NotBoundException e) {
            System.out.println("Server did not bind the registry yet...");
        } catch (AccessException e) {
            System.err.println(e.getMessage());
        } catch (RemoteException e) {
            System.err.println("RMI: "+e.getMessage());
        }
    }

    public static void mesasEstado(){
        new Thread() {
            public void run() {
                try {
                    int estado_id;
                    while (true){
                        Response answer = svInterface.mostraEstado();
                        for(int i = 0; i< answer.getInfo().size();i+=4){
                            estado_id = Integer.parseInt(answer.getInfo().get(i));
                            System.out.println("Notificacao: a mesa com o id "+answer.getInfo().get(i+1)+" associado a eleicao "+answer.getInfo().get(i+2)+
                                    " notifica que "+answer.getInfo().get(i+3));
                            svInterface.updateEstado(estado_id);
                        }
                    }
                }catch (RemoteException e){
                    System.err.println("Admin console: "+e.getMessage());
                    try {
                        Thread.sleep(5000);
                        System.out.println("reconnecting to RMI server...");
                        connectToRMI();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        }.start();
    }

    public static void eleitores(){
        new Thread() {
            public void run() {
                int voto_id,numeroVotantes = 0;
                while (true){
                    try{
                        Response answer = svInterface.mostraVotantes();
                        for(int i = 0; i< answer.getInfo().size();i+=4){
                            voto_id = Integer.parseInt(answer.getInfo().get(i));
                            numeroVotantes++;
                            System.out.println("Notificacao: o user "+answer.getInfo().get(i+1)+ " votou no departamento "+answer.getInfo().get(i+2)+
                                    " para a eleicao "+answer.getInfo().get(i+3)+". Numero de total de votantes: "+numeroVotantes);
                            svInterface.updateVotante(voto_id);
                        }
                    }
                    catch (RemoteException e) {
                        System.err.println("Admin console: "+e.getMessage());
                        try {
                            Thread.sleep(5000);
                            System.out.println("reconnecting to RMI server...");
                            connectToRMI();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    public static void main(String [] args){
        configAdminConsole();
        connectToRMI();
        mesasEstado();
        eleitores();
        while (true){
            try {
                new MenuConsole(svInterface);
            } catch (RemoteException e) {
                try {
                    Thread.sleep(5000);
                    System.out.println("reconnecting to RMI server...");
                    connectToRMI();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}
