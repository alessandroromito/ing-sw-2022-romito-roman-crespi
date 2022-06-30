package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.PawnColors;

import java.io.Serial;
import java.util.List;

public class Card218Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -3334089856764889553L;

    List<Integer> entranceList;
    List<PawnColors> diningList;

    /**
     * Default Constructor
     * @param nickname nickname of the sender
     * @param entranceList parameter for the effect
     * @param diningList parameter for the effect
     */
    public Card218Message(String nickname, List<Integer> entranceList, List<PawnColors> diningList) {
        super(nickname, 218);
        this.entranceList = entranceList;
        this.diningList = diningList;
    }

    public List<Integer> getEntranceList() {
        return entranceList;
    }

    public List<PawnColors> getDiningList() {
        return diningList;
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
        return "Card218Message[nickname:" + getNickname() + "]";
    }
}
