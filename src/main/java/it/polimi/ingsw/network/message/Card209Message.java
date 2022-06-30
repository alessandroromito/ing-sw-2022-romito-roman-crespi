package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;

import java.io.Serial;

public class Card209Message extends UseEffectMessage{
    @Serial
    private static final long serialVersionUID = -8849298157951805972L;

    int studentPos;
    int islandNumber;

    /**
     * Default constructor.
     * @param nickname nickname of the sender
     * @param studentPos parameter of the effect
     * @param islandNumber parameter of the effect
     */
    public Card209Message(String nickname, int studentPos, int islandNumber) {
        super(nickname, 209);
        this.studentPos = studentPos;
        this.islandNumber = islandNumber;
    }

    /**
     * @return studentPos
     */
    public int getStudentPos() {
        return studentPos;
    }

    /**
     * @return islandNumber
     */
    public int getIslandNumber() {
        return islandNumber;
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
    public String toString() {
        return "Card209Message[nickname:" + getNickname() + ", island number: " + islandNumber + ", student position: " + studentPos + "]";
    }
}
