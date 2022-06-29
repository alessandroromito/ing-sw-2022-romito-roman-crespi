package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.map.Cloud;

import java.io.Serial;
import java.util.ArrayList;

public class CloudMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8264379611510809851L; //da scegliere

    private final ArrayList<Cloud> cloudList;

    public CloudMessage(String nickname, ArrayList<Cloud> cloudList) {
        super(nickname, MessageType.PICK_CLOUD);
        this.cloudList = cloudList;
    }

    public ArrayList<Cloud> getCloudList() {
            return cloudList;
        }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
        public String toString() {
            return "CloudMessage[" + "nickname:" + getNickname() + ", clouds:" + this.cloudList + "]";
        }
}
