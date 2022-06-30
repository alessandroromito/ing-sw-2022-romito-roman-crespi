package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;
import java.util.ArrayList;

public class Card215Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = 3620628774513746063L;

    ArrayList<Integer> entranceStudents;
    ArrayList<Integer> cardStudents;

    public Card215Message(String nickname, ArrayList<Integer> entrance, ArrayList<Integer> card) {
        super(nickname, 215);
        this.entranceStudents = entrance;
        this.cardStudents = card;
    }

    public ArrayList<Integer> getEntranceStudents() {
        return entranceStudents;
    }

    public ArrayList<Integer> getCardStudents(){
        return cardStudents;
    }

    /**
     * This method communicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card215Message[nickname:" + getNickname() + " entrance size: " + entranceStudents.size() + " cards size: " + cardStudents.size() + "]";
    }
}
