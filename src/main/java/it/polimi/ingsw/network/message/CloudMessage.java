package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.List;

public class CloudMessage extends Message {
        private static final long serialVersionUID = -1L; //da scegliere

        private final List<Cloud> cloudList;

        public CloudMessage(String nickname, List<Cloud> cloudList) {
            super(nickname, MessageType.PICK_CLOUD);
            this.cloudList = cloudList;
        }

        public List<Cloud> getCloudList() {
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
