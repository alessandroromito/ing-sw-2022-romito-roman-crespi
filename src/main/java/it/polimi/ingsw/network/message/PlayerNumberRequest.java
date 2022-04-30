package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Model;

public class PlayerNumberRequest extends Message {
    private static final long serialVersionUID = -1L; //da scegliere

    public PlayerNumberRequest() {
        super(Model.SERVER_NAME, MessageType.PLAYER_NUMBER_REQUEST);
    }

    @Override
    public String toString() {
        return "PlayerNumberRequest[" + "nickname:" + getNickname() + "]";
    }
}
