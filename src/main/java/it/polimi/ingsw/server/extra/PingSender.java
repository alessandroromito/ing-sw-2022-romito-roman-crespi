package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.network.message.PingMessage;
import it.polimi.ingsw.network.server.ClientHandler;

public class PingSender implements Runnable{

    private final ClientHandler clientHandler;
    private boolean connected;

    /**
     * Class that check only is the client is connected by
     * sending a ping message every 10 sec
     *
     * @param clientHandler clientHandler of the client
     */
    public PingSender(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
        connected = true;
    }

    /**
     * Method to send a ping every 10 sec. If the answer isn't received set
     * the clientHandler as disconnected
     */
    @Override
    public void run() {
        while(connected){

            clientHandler.sendMessage(new PingMessage());
            connected = false;

            try{
                Thread.sleep(1000*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(clientHandler.isConnected())
                clientHandler.disconnect();

            System.out.println("Disconnection caused by ping");
        }
    }

    /**
     * Called when ping message is received
     */
    public void pingReceived() {
        connected = true;
    }

    public void setConnected(boolean bool){
        connected = bool;
    }
}
