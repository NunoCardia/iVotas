package RMI;

import common.Sinal;

import java.io.IOException;
import java.net.*;

/**
 * Created by cyberfox21 on 22/10/17.
 */
public class RMICheckServer  implements Runnable{

    private int task;
    private Sinal server;
    public Thread t;
    private int firstPort;
    private String secondHost;
    private int secondPort;

    public RMICheckServer(Sinal server, int firstPort, String secondHost, int secondPort){
        this.server = server;
        this.t = new Thread(this);
        this.firstPort = firstPort;
        this.secondHost = secondHost;
        this.secondPort = secondPort;
        t.start();
    }

    @Override
    public void run() {
        System.out.println("Server Checker a iniciar...");
        DatagramSocket UDPSocket = null;

        try{
            UDPSocket = new DatagramSocket(firstPort);
        } catch(SocketException e){
            System.out.println(e.getMessage());
        }


        try{
            System.out.println("Envio de HELLO");

            byte[] msg = "HELLO".getBytes();
            DatagramPacket reply = new DatagramPacket(msg, msg.length, InetAddress.getByName(secondHost), secondPort);
            UDPSocket.send(reply);

            System.out.println("A esperar por resposta...");
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            UDPSocket.setSoTimeout(5000);	//Aguarda 5 segundos por resposta
            UDPSocket.receive(packet);
            System.out.println("Resposta recebida");

            String s = new String(packet.getData(), 0, packet.getLength());


            if(s.compareTo("HELLO") == 0){
                task = 1;
                synchronized(server){
                    server.setStatus(true);
                    server.setType(1);
                    server.notify();
                }
            }
            else{
                synchronized(server){
                    server.setStatus(true);
                    server.setType(2);
                    server.notify();
                }
                task = 2;
            }

        } catch(SocketTimeoutException e){
            System.out.println("WORLD NAO RECEBIDO. executar como PRINCIPAL.");
            synchronized(server){
                server.setStatus(true);
                server.setType(1);
                server.notify();
            }
            task = 1;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



        while(true){
            try{
                if(task == 1){
                    System.out.println("A escutar o servidor de backup...");
                    byte[] buffer = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    System.out.println("A esperar por HELLO...");
                    UDPSocket.setSoTimeout(0);
                    UDPSocket.receive(packet);
                    System.out.println("HELLO recebido");

                    byte[] msg = "WORLD".getBytes();
                    DatagramPacket reply = new DatagramPacket(msg, msg.length, InetAddress.getByName(secondHost), secondPort);
                    UDPSocket.send(reply);
                    System.out.println("WORLD enviado.");
                }
                else if(task == 2){
                    System.out.println("A controlar o servidor principal...");
                    byte[] msg = "HELLO".getBytes();
                    DatagramPacket packet = new DatagramPacket(msg, msg.length, InetAddress.getByName(secondHost), secondPort);
                    UDPSocket.send(packet);
                    System.out.println("HELLO enviado. A aguardar WORLD...");

                    byte[] buffer = new byte[256];
                    DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                    UDPSocket.setSoTimeout(5000);
                    UDPSocket.receive(reply);
                    System.out.println("WORLD recebido: ");
                    Thread.sleep(5000);
                }
            } catch(SocketTimeoutException e){
                System.out.println("WORLD NAO RECEBIDO. Vai executar como PRINCIPAL.");
                synchronized(server){
                    server.setStatus(true);
                    server.setType(1);
                    server.notify();
                }
                task = 1;
            } catch (IOException e){
                System.out.println(e.getMessage());

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());

            }
        }
    }
}
