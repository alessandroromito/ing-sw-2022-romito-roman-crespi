package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;

/**
 * Victory message. It contains the nickname of the winner.
 */
public class VictoryMessage extends Message {
    @Serial
    private static final long serialVersionUID = -2456845241456325457L;

    private final String winnerNickname;

    /**
     * @param winnerNickname nickname of the winner.
     */
    public VictoryMessage(String winnerNickname) {
        super(Game.SERVER_NAME, MessageType.WINNER_DECLARATION);
        this.winnerNickname = winnerNickname;
    }

    /**
     * @return nickname of the winner.
     */
    public String getWinnerNickname() {
        return winnerNickname;
    }

    /**
     * This method comunicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "VictoryMessage[nickname:" + getNickname() +
                ", winner:" + winnerNickname + "]";
    }
}
