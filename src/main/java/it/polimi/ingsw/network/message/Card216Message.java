package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card216Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = 1521853107510538635L;

    public Card216Message(String nickname) {
        super(nickname, 216);
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card216Message[nickname:" + getNickname() + "]";
    }
}
