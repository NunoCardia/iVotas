package RMI;

import common.Sinal;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

/**
 * Created by cyberfox21 on 22/10/17.
 */
public class AssignServers implements Runnable{

    private int task;
    private Sinal server;
    public Thread t;
    private int firstPort;

    private int numero;

    public AssignServers(Sinal server, int firstPort){
        this.server = server;
        this.firstPort = firstPort;
        this.t = new Thread(this);
        t.start();
    }


    @Override
    public void run() {
        synchronized(server){
            while(!server.getStatus()){
                try {
                    server.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            server.setStatus(false);
            task = server.getType();
        }

        if(task == 2){
            System.out.println("Executar como BACKUP");

            synchronized(server){
                while(!server.getStatus()){
                    try {
                        server.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                server.setStatus(false);
                task = server.getType();
            }
        }

        if(task == 1){
            System.out.println("Executar como PRINCIPAL");
            System.out.println("ONLINE");

            try {
                Registry registry = LocateRegistry.createRegistry(7900);
                registry.rebind("server", (RMIServerInterface) new RMIServer());
                System.out.println("Rmi Server ready and Running...");
            } catch (AccessException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
