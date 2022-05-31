package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.LoginRequest;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final SocketServer socketServer;
    private final MessageHandler messageHandler;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream out;
    private ObjectInputStream in;

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

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (inputLock) {
                    Message message = (Message) in.readObject();
                    if (message != null && !message.getClass().equals(PingMessage.class)) {
                        if (message.getClass().equals(LoginRequest.class)) {
                            System.out.println(message);
                            socketServer.addClient(message.getNickname(), this);
                        } else {
                            System.out.println(message);
                            message.handle(messageHandler);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            disconnect();
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void disconnect() {
        if(connected) {
            try {
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {

            }
            connected = false;
            Thread.currentThread().interrupt();
            socketServer.onDisconnect(this);
        }
    }

    public void sendMessage(Message message) {
        if(!isConnected()) return;
        try {
            synchronized (outputLock) {
                out.writeObject(message);
                out.reset();
                out.flush();
                out.reset();
            }
        } catch (IOException e) {
            disconnect();
        }
    }

}
