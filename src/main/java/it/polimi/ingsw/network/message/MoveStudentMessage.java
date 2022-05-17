package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.util.List;

public class MoveStudentMessage extends Message{

    private static final long serialVersionUID = -1L; //da scegliere

    private final int islandNumber;
    private final List<StudentDisc> studentDiscs;
    private final int position; // 0 = scoreboard, 1 = islands

    public MoveStudentMessage(String nickname, List<StudentDisc> studentDiscs, int position, int islandNumber) {
        super(nickname, MessageType.MOVE_STUDENT);
        this.studentDiscs = studentDiscs;
        this.islandNumber = islandNumber;
        this.position = position;
    }

    public List<StudentDisc> getStudentDiscs() {
        return studentDiscs;
    }

    public int getPosition() {
        return position;
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
        return "MoveStudent[" + "nickname:" + getNickname() + ", islandNumber:" + this.islandNumber + ", studentID:" + studentDiscs + ",position:" + position + "]";
    }
}
