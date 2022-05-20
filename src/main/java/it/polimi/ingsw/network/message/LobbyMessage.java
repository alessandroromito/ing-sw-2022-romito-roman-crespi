package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.util.List;

public class LobbyMessage extends Message {
    private static final long serialVersionUID = 1L;
    private final List<String> playersNickname;
    private final int numMaxPlayers;

    public LobbyMessage(List<String> playersNickname, int numMaxPlayers) {
        super(Game.SERVER_NAME, MessageType.LOBBY);
        this.playersNickname = playersNickname;
        this.numMaxPlayers = numMaxPlayers;
    }

    public List<String> getPlayersNickname() {
        return playersNickname;
    }

    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString(){
        return "LobbyMessage[nickname:" + getNickname() +
                ", playersNickname:" + playersNickname + "]";
    }
}
