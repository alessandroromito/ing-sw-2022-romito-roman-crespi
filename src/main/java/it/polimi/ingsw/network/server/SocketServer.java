package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Socket of the server.
 * Handles all the incoming socket connections.
 */
public class SocketServer implements Runnable{
    private final Server server;
    private final int port;
    private final String DEFAULT_IP = "127.0.0.1"; //localhost
    ServerSocket serverSocket;

    /**
     * Default constructor.
     * @param server server associated to the socket of this class.
     * @param port socket port to open.
     */
    public SocketServer(Server server , int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Server.LOGGER.info(() -> "Server avviato su porta " + port + ".");
        } catch (IOException e) {
            Server.LOGGER.severe("Impossibile avviare il server.");
            e.printStackTrace();
            return;
        }

        System.out.println("Server socket ready on IP: " + serverSocket.getInetAddress().getHostAddress());

        try {
            System.out.println("Server socket ready on IP (from arg): " + InetAddress.getByName(DEFAULT_IP));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while(!Thread.currentThread().isInterrupted()) {
            Socket client;
            try {
                client = serverSocket.accept();
                System.out.println("Received client connection");
            } catch(IOException e) {
                System.out.println("SocketServer.run(): Client connection not accepted");
                continue;
            }

            ClientHandler clientHandler;
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

    /**
     * Handles the adding of a new client.
     * @param nickname nickname associated to the client.
     * @param clientHandler clientHandler associated to the client.
     */
    public void addClient(String nickname, ClientHandler clientHandler){
        server.addClient(nickname, clientHandler);
    }

    /**
     * Handles a client disconnection.
     * @param clientHandler clientHandler associated to disconnecting client.
     */
    public void onDisconnect(ClientHandler clientHandler) {
        server.onDisconnect(clientHandler);
    }

    /**
     * Getter method.
     * @return gameController
     */
    public GameController getGameController(){
        return server.getGameController();
    }

}