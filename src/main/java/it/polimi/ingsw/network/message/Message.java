package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract main class for messages.
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 237749030537619332L;

    private final String nickname;
    private final MessageType messageType;

    /**
     * Default constructor.
     * @param nickname nickname of the player owner of the message.
     * @param messageType type of message to be sent.
     */
    public Message(String nickname, MessageType messageType) {
        this.nickname = nickname;
        this.messageType = messageType;
    }

    /**
     * @return nickname of the player owner of the message.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return type of message to be sent.
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * This method comunicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    public abstract void handle(MessageHandler messageHandler);

    @Override
    public String toString(){
        return "Message{nickname:" + nickname + ", messageType:" + messageType + "}";
    }

}
