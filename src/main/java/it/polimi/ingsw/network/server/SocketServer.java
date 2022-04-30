package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable{
    private final Server server;
    private final int port;
    ServerSocket serverSocket;

    public SocketServer ( Server server , int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace(); //temporarly
            return;
        }

        while(!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                client.setSoTimeout(8000);
                SocketClientHandler clientHandler = new SocketClientHandler(this, client);
                Thread thread = new Thread (clientHandler, "ss_handler" + client.getInetAddress());
                thread.start();
            } catch(IOException e) {
                e.printStackTrace(); //temporarly
            }
        }
    }

    public void addClient(String nickname, ClientHandler clientHandler){
        server.addClient(nickname, clientHandler);
    }

    public void onMessage (Message message) {
        server.onMessageReceived(message);
    }

    public void onDisconnect (ClientHandler clientHandler) {
        server.onDisconnect(clientHandler);
    }


}
