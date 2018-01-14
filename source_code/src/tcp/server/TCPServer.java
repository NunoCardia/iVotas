package tcp.server;

import common.Configuracao;

/**
 * Created by cyberfox21 on 13/10/17.
 */
public class TCPServer {

    private static setupTCPServer setupConnTCP;
    private static int port;
    private static String hostname;
    private static Configuracao configuracao;
    private static int registryNumberRMI;
    private static String hostnameRMI;

    public static void configServer(){
        configuracao =  new Configuracao();
        port = configuracao.getTcp1();
        hostname = configuracao.getServer1();

        hostnameRMI = configuracao.getServer1();
        registryNumberRMI = configuracao.getConsole_port();
    }

    public static void main(String [] args){
        configServer();
        setupConnTCP = new setupTCPServer(hostname,port,hostnameRMI,registryNumberRMI);
    }
}
