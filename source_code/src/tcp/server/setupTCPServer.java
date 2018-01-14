package tcp.server;

import RMI.RMIServerInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by cyberfox21 on 13/10/17.
 */
public class setupTCPServer implements Runnable{

    private String hostname;
    private int port;
    public Thread t;
    static RMIServerInterface svInterface;
    ServerMenu sm;
    private int registryNumberRMI;
    private String hostnameRMI;
    final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    BlockingQueue<Integer> integers;

    public setupTCPServer(String hostname, int port,String hostnameRMI,int registryNumberRMI) {
        this.hostname = hostname;
        this.port = port;
        this.hostnameRMI = hostnameRMI;
        this.registryNumberRMI = registryNumberRMI;
        this.integers = new ArrayBlockingQueue<Integer>(4);
        t = new Thread(this);
        t.start();
    }

    public void connectRMI(){
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

        connectRMI();
        try {
            System.out.println("Mesa de voto online");
            sm = new ServerMenu(svInterface,atomicBoolean,integers,hostnameRMI,registryNumberRMI);
            ServerSocket listenSocket = new ServerSocket(this.port);
            while (true){
                Socket clientSocket = listenSocket.accept();
                System.out.println("Novo cliente");
                new ConnectionTCP(clientSocket,svInterface,atomicBoolean,integers);
            }
        } catch (IOException e) {
            System.out.println("first: "+e.getMessage());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                System.out.println(e1.getMessage());

            }
        }
    }
}
