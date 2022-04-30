package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientHandler implements ClientHandler, Runnable {
    private final Socket client;
    private final SocketServer socketServer;

    private boolean connected;

    private final Object inputLock;
    private final Object outputLock;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketClientHandler(SocketServer socketServer, Socket client) throws IOException {
        this.socketServer = socketServer;
        this.client = client;
        this.connected = true;

        this.inputLock = new Object();
        this.outputLock = new Object();

        this.out = new ObjectOutputStream(client.getOutputStream());
        this.in = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        try {
            handleClientConnectior(); //da implementare
        } catch (IOException e) {
            disconnect();
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void disconnect() {
        if(connected) {
            try {
                if (!client.isClosed()) {client.close();}
            } catch (IOException e) {

            }
            connected = false;
            Thread.currentThread().interrupt();
            socketServer.onDisconnect(this);
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (outputLock) {
                out.writeObject(message);
                out.reset();
            }
        } catch (IOException e) {
            disconnect();
        }
    }

}
