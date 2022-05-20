package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;

import java.util.List;

public class ExpertGameInfoMessage extends Message {
    private static final long serialVersionUID = 1002L; //da scegliere
    private final int unifiedIslandsNumber;
    private final int remainingBagStudents;
    private final List<String>  playersNickname;
    private final String activePlayer;
    private final List<CharacterCard> characterCards;

    public ExpertGameInfoMessage(String nickname, List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {
        super(nickname, MessageType.ERROR);
        this.playersNickname = playersNickname;
        this.unifiedIslandsNumber = unifiedIslandsNumber;
        this.remainingBagStudents = remainingBagStudents;
        this.activePlayer = activePlayer;
        this.characterCards = characterCards;
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
        return "ExpertGameInfoMessage[nickname:" + getNickname() + ", unifiedIslandsNumber:" + unifiedIslandsNumber + ", remainingBagStudents:" + remainingBagStudents + ", activePlayer:" + activePlayer  + "]";
    }
}
