package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable{
    private final Server server;
    private final int port;
    private String DEFAULT_IP = "127.0.0.1";
    ServerSocket serverSocket;

    public SocketServer(Server server , int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port, 0, InetAddress.getByName(DEFAULT_IP));
        } catch (IOException e) {
            e.printStackTrace(); //temporary
            return;
        }

        System.out.println("Server Socket listening at port: " + port);
        System.out.println("Server socket ready on IP: " + serverSocket.getInetAddress().getHostAddress());

        while(!Thread.currentThread().isInterrupted()) {
            Socket client = null;
            try {
                client = serverSocket.accept();
                System.out.println("Received client connection");
            } catch(IOException e) {
                System.out.println("SocketServer.run(): Client connection not accepted");
                continue;
            }

            ClientHandler clientHandler = null;
            try {
                clientHandler = new ClientHandler(this, client);
                Thread thread = new Thread (clientHandler, "serverSocket_handler" + client.getInetAddress());
                thread.start();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SocketServer.run(): Error creating a clientHandler");
                System.out.println("Server still active");
            }
        }
    }

    public void addClient(String nickname, ClientHandler clientHandler){
        server.addClient(nickname, clientHandler);
    }

    public void onDisconnect(ClientHandler clientHandler) {
        server.onDisconnect(clientHandler);
    }

    public GameController getGameController(){
        return server.getGameController();
    }

}