package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card209Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -8849298157951805972L;

    int studentPos;
    int islandNumber;

    public Card209Message(String nickname, int studentPos, int islandNumber) {
        super(nickname, 209);
        this.studentPos = studentPos;
        this.islandNumber = islandNumber;
    }

    public int getStudentPos() {
        return studentPos;
    }

    public int getIslandNumber() {
        return islandNumber;
    }

    @Override
    public void handle(MessageHandler messageHandler) {
        messageHandler.handleMessage(this);
    }

    @Override
    public String toString() {
        return "Card209Message[nickname:" + getNickname() + ", island number: " + islandNumber + ", student position: " + studentPos + "]";
    }
}
