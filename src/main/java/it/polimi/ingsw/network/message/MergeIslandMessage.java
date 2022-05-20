package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;

import java.util.List;

public class MergeIslandMessage extends Message {
    private static final long serialVersionUID = 1002L; //da scegliere
    private final List<Integer> unifiedIslands;

    public MergeIslandMessage(String nickname, List<Integer> unifiedIslands) {
        super(nickname, MessageType.MERGE_ISLANDS);
        this.unifiedIslands = unifiedIslands;
    }

    public List<Integer> getUnifiedIslands() { return this.unifiedIslands; }

    @Override
    public void handle(MessageHandler messageHandler) { messageHandler.handleMessage(this);}

    @Override
    public String toString() { return "MergeIslandsMessage[nickname:" + getNickname() + "]";}
}
