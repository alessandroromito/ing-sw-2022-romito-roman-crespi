package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.LoginRequest;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client handler of a client - server connnection.
 */
public class ClientHandler implements Runnable {
    private final Socket client;
    private final SocketServer socketServer;
    private final MessageHandler messageHandler;

    private boolean connected;
    private String nickname;

    private final Object inputLock;
    private final Object outputLock;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    /**
     * Default constructor.
     * @param socketServer socket of the server.
     * @param client client to handle.
     * @throws IOException from creating new Object Input or Output Stream
     */
    public ClientHandler(SocketServer socketServer, Socket client) throws IOException {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        this.inputLock = new Object();
        this.outputLock = new Object();

        this.out = new ObjectOutputStream(client.getOutputStream());
        this.in = new ObjectInputStream(client.getInputStream());

        this.messageHandler = new MessageHandler(socketServer);
    }

    /**
     * Handles the connection of the client.
     * Keep listening to the socket.
     */
    @Override
    public void run() {
        Server.LOGGER.info(() -> "Client connected from " + client.getInetAddress());
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) in.readObject();
                    if (message != null && !message.getClass().equals(PingMessage.class)) {
                        if (message.getClass().equals(LoginRequest.class)) {
                            System.out.println(message);
                            this.nickname = message.getNickname();
                            socketServer.addClient(message.getNickname(), this);
                        } else {
                            Server.LOGGER.info(() -> "Messaggio ricevuto: " + message);
                            message.handle(messageHandler);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            disconnect();
        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return status of connection
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Disconnect the socket from the client if connected.
     */
    public void disconnect() {
        if(isConnected()) {
            try {
                if(!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            connected = false;
            Thread.currentThread().interrupt();

            socketServer.onDisconnect(this);
        }
    }

    /**
     * Sends a message to the client via socket.
     * @param message message to send to the client.
     */
    public void sendMessage(Message message) {
        if(!isConnected()) return;
        try {
            synchronized (outputLock) {
                out.writeObject(message);
                out.reset();
                out.flush();
                out.reset();
                Server.LOGGER.info(() -> "Messaggio inviato: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * Getter method.
     * @return nickname saved in ClientHandler class.
     */
    public String getNickname() {
        return nickname;
    }
}
