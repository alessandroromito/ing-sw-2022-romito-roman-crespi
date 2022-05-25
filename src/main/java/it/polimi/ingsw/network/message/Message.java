package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = 1000L; //da scegliere

    private final String nickname;
    private final MessageType messageType;

    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    public String getNickname() {
        return nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public abstract void handle(MessageHandler messageHandler);

    @Override
    public String toString(){
        return "Message{nickname:" + nickname + ", messageType:" + messageType + "}";
    }

}
