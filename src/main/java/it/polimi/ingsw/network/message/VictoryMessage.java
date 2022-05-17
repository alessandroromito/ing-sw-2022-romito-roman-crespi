package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

public class VictoryMessage extends Message {
    private static final long serialVersionUID = 2456845241456325457L;
    private final String winnerNickname;

    public VictoryMessage(String winnerNickname) {
        super(Game.SERVER_NAME, MessageType.WINNER_DECLARATION);
        this.winnerNickname = winnerNickname;
    }

    public String getWinnerNickname() {
        return winnerNickname;
    }

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
