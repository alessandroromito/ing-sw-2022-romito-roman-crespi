package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;

import java.io.Serial;
import java.util.List;

/**
 * Message that contains information about the game state for the expert mode.
 */
public class ExpertGameInfoMessage extends Message {
    @Serial
    private static final long serialVersionUID = -715355820777094900L;

    private final int unifiedIslandsNumber;
    private final int remainingBagStudents;
    private final List<String>  playersNickname;
    private final String activePlayer;
    private final List<CharacterCard> characterCards;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param playersNickname players nickname.
     * @param unifiedIslandsNumber number of groups of merged islands.
     * @param remainingBagStudents number of students remaining from the bag.
     * @param activePlayer player who's the turn of.
     * @param characterCards character cards in game.
     */
    public ExpertGameInfoMessage(String nickname, List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {
        super(nickname, MessageType.ERROR);
        this.playersNickname = playersNickname;
        this.unifiedIslandsNumber = unifiedIslandsNumber;
        this.remainingBagStudents = remainingBagStudents;
        this.activePlayer = activePlayer;
        this.characterCards = characterCards;
    }

    /**
     * @return player who's the turn of.
     */
    public String getActivePlayer() { return activePlayer; }

    /**
     * @return players nickname.
     */
    public List<String> getPlayersNickname(){ return playersNickname; }

    /**
     * @return number of groups of merged islands.
     */
    public int getUnifiedIslandsNumber(){ return unifiedIslandsNumber; }

    /**
     * @return number of students remaining from the bag.
     */
    public int getRemainingBagStudents(){ return remainingBagStudents; }

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
        return "ExpertGameInfoMessage[nickname:" + getNickname() + ", unifiedIslandsNumber:" + unifiedIslandsNumber + ", remainingBagStudents:" + remainingBagStudents + ", activePlayer:" + activePlayer  + "]";
    }
}
