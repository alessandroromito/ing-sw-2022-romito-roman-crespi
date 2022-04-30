package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class LoginRequest extends Message {
    private static final long serialVersionUID = -1L; //da scegliere

    public LoginRequest(String nickname) {
        super(nickname, MessageType.LOGIN_REQUEST);
    }

    @Override
    public String toString() {
        return "LoginRequest[" + "nickname:" + getNickname() + "]";
    }
}
