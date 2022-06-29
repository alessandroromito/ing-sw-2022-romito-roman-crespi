package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;
import java.util.List;

public class GameInfoMessage extends Message {
    @Serial
    private static final long serialVersionUID = -930182368790454493L;

    private final int unifiedIslandsNumber;
    private final int remainingBagStudents;
    private final List<String>  playersNickname;
    private final String activePlayer;

    public GameInfoMessage(String nickname, List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer) {
        super(nickname, MessageType.ERROR);
        this.playersNickname = playersNickname;
        this.unifiedIslandsNumber = unifiedIslandsNumber;
        this.remainingBagStudents = remainingBagStudents;
        this.activePlayer = activePlayer;
    }

    public String getActivePlayer() { return activePlayer; }
    public List<String> getPlayersNickname(){ return playersNickname; }
    public int getUnifiedIslandsNumber(){ return unifiedIslandsNumber; }
    public int getRemainingBagStudents(){ return remainingBagStudents; }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "GameInfoMessage[nickname:" + getNickname() + ", unifiedIslandsNumber:" + unifiedIslandsNumber + ", remainingBagStudents:" + remainingBagStudents + ", activePlayer:" + activePlayer  + "]";
    }
}
