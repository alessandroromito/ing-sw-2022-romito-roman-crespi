package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.ErrorMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  Socket client implementation.
 */
public class Client extends Observable {

    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final ExecutorService readExecutionQueue;


    private static final int SOCKET_TIMEOUT = 10000;

    /**
     * Default constructor.
     * @param address ip address of the sever socket.
     * @param port port of the server socket.
     * @throws IOException
     */
    public Client(String address, int port) throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(address, port), SOCKET_TIMEOUT);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.readExecutionQueue = Executors.newSingleThreadExecutor();
    }

    /**
     *  Reads a message from the server vis socket and notify the ClientController via Observer.
     */
    public void readMessage() {
        readExecutionQueue.execute(() -> {
            while (!readExecutionQueue.isShutdown()) {
                Message message;
                try {
                    message = (Message) objectInputStream.readObject();
                    if(message.getMessageType() == MessageType.PING)
                        sendMessage(new PingMessage());
                    else notifyObserver(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    message = new ErrorMessage("Connection lost");
                    notifyObserver(message);
                    disconnect();
                    readExecutionQueue.shutdownNow();
                }
            }
        });
    }

    /**
     * Send a message to the server via socket.
     * @param message message sent to the server.
     */
    public void sendMessage(Message message){
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
            notifyObserver(new ErrorMessage("Could not send message"));
        }
    }


    /**
     * Disconnect the socket from the server.
     */
    public void disconnect() {
        try {
            if (!socket.isClosed()) {
                readExecutionQueue.shutdownNow();
                socket.close();
            }
        } catch (IOException e) {
            notifyObserver(new ErrorMessage("Impossible to disconnect. Retry later."));
        }
    }
}
