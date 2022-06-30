package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.io.Serial;
import java.util.List;

/**
 * Message that contains information to merge islands.
 */
public class MergeIslandMessage extends Message {
    @Serial
    private static final long serialVersionUID = 4829573440615864710L;
    private final List<Integer> unifiedIslands;

    /**
     * Default constructor.
     * @param nickname nickname of the owner of the message.
     * @param unifiedIslands islands to merge.
     */
    public MergeIslandMessage(String nickname, List<Integer> unifiedIslands) {
        super(nickname, MessageType.MERGE_ISLANDS);
        this.unifiedIslands = unifiedIslands;
    }

    /**
     * @return islands to merge.
     */
    public List<Integer> getUnifiedIslands() {
        return this.unifiedIslands;
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
    public String toString() { return "MergeIslandsMessage[nickname: " + getNickname() + " ]";}
}
