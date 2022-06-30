package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;

import java.io.Serial;
import java.util.List;

/**
 * Message that contains the lobby of player waiting to join the game.
 */
public class LobbyMessage extends Message {
    @Serial
    private static final long serialVersionUID = -156805307373219139L;

    private final List<String> playersNickname;
    private final int numMaxPlayers;

    /**
     * Default constructor.
     * @param playersNickname nickname of the players waiting.
     * @param numMaxPlayers max number of player that lobby can contain.
     */
    public LobbyMessage(List<String> playersNickname, int numMaxPlayers) {
        super(Game.SERVER_NAME, MessageType.LOBBY);
        this.playersNickname = playersNickname;
        this.numMaxPlayers = numMaxPlayers;
    }

    /**
     * @return nickname of the players waiting.
     */
    public List<String> getPlayersNickname() {
        return playersNickname;
    }

    /**
     * @return max number of player that lobby can contain.
     */
    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }

    /**
     * This method comunicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
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
