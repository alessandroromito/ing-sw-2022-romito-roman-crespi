package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.LoginRequest;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;
import it.polimi.ingsw.server.extra.ANSICostants;
import it.polimi.ingsw.server.extra.PingSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client handler of a client - server connection.
 */
public class ClientHandler implements Runnable {
    private final Socket client;
    private final SocketServer socketServer;
    private final MessageHandler messageHandler;
    private final PingSender pingSender;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    /**
     * Default constructor.
     *
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
        this.pingSender = new PingSender(this);
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
                            socketServer.addClient(message.getNickname(), this);
                        } else {
                            Server.LOGGER.info(() -> ANSICostants.ANSI_BLUE + "Messaggio ricevuto: " + message + ANSICostants.ANSI_RESET);
                            message.handle(messageHandler);
                        }
                    }
                    else pingSender.pingReceived();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Found a disconnection");
            pingSender.setConnected(false);
            connected = false;

            disconnect();
        }
    }

    /**
     * @return status of connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Method to handle the disconnection of a client.
     */
    public void disconnect() {
        if(!isConnected()) {
            socketServer.onDisconnect(this);

            try {
                if(!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            connected = false;
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Sends a message to the client via socket.
     * @param message message to send to the client.
     */
    public void sendMessage(Message message) {
        if(!isConnected())
            return;
        try {
            synchronized (outputLock) {
                out.reset();
                out.writeObject(message);
                out.flush();
                out.reset();
                Server.LOGGER.info(() -> "Messaggio inviato: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
