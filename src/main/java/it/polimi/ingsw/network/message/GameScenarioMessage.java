package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameSerialized;

public class GameScenarioMessage extends Message {
    private static final long serialVersionUID = -1L; //da scegliere

    private final GameSerialized gameSerialized;

    public GameScenarioMessage(GameSerialized gameSerialized) {
        super(Game.SERVER_NAME, MessageType.GAME_SCENARIO);
        this.gameSerialized = gameSerialized;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }
}
