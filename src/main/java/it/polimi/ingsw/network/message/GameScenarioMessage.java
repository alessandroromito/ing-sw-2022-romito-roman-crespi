package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameSerialized;

import java.io.Serial;

/**
 * Message that contains all the game data.
 */
public class GameScenarioMessage extends Message {
    @Serial
    private static final long serialVersionUID = -1997062720830543476L;

    private final GameSerialized gameSerialized;

    /**
     * Default constructor.
     * @param gameSerialized data to be shown on CLI/GUI to represent tha game interface.
     */
    public GameScenarioMessage(GameSerialized gameSerialized) {
        super(Game.SERVER_NAME, MessageType.GAME_SCENARIO);
        this.gameSerialized = gameSerialized;
    }

    /**
     * This method communicate with messageHandler to handle the message.
     * @param messageHandler handler of the message.
     */
    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    /**
     * @return the game data.
     */
    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }
}
