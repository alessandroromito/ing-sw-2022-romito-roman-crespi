package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class PlayerNumberReply extends Message{
    private static final long serialVersionUID = -1L; //da scegliere
    private final int playerNumber;

    public PlayerNumberReply(String nickname, int playerNumber) {
        super(nickname, MessageType.PLAYERNUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public String toString() {
        return "PlayerNumberReply[" + "nickname:" + getNickname() + ", playerNumber:" + playerNumber + "]";
    }
}
