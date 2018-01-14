package RMI;

import common.Configuracao;
import common.Sinal;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by cyberfox21 on 11/10/17.
 */
public class Initializer {

    private static int serverMachineNumber;
    private static String firstHost;
    private static String secondHost;
    private static int firstPort;
    private static int secondPort;
    private static Sinal sinal;

    private static AssignServers assignServers;
    private static RMICheckServer rmiCheckServer;

    public static void main(String[] args) throws NotBoundException, ClassNotFoundException, SQLException {
        sinal = new Sinal(false);
        configServer();
        rmiCheckServer = new RMICheckServer(sinal,firstPort,secondHost,secondPort);
        assignServers = new AssignServers(sinal,firstPort);
        try {
            rmiCheckServer.t.join();
            assignServers.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void configServer(){
        Configuracao configs = new Configuracao();
        int option;
        Scanner sc = new Scanner(System.in);
        System.out.println("Indique o numero deste servidor [1 ou 2]: ");
        option = sc.nextInt();
        switch(option){
            case 1:
                serverMachineNumber = 1;
                firstHost = configs.getRmiserver1();
                firstPort = configs.getRmiport1();
                secondHost = configs.getRmiserver2();
                secondPort = configs.getRmiport2();
                break;

            case 2:
                serverMachineNumber = 2;
                firstHost = configs.getRmiserver2();
                firstPort = configs.getRmiport2();
                secondHost = configs.getRmiserver1();
                secondPort = configs.getRmiport1();
                break;
        }
    }
}
