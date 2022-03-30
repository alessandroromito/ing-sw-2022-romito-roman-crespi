package it.polimi.ingsw.server.exception;

public class MissingPlayerNicknameException extends Throwable {
    public MissingPlayerNicknameException(String nickname) {
        super("Player" + nickname + "doesn't exist!");
    }
}
