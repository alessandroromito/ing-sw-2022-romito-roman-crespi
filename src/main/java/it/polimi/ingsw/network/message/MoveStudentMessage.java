package it.polimi.ingsw.network.message;

import it.polimi.ingsw.server.enumerations.MessageType;

public class MoveStudentMessage extends Message{

    private static final long serialVersionUID = -1L; //da scegliere

    private final int islandNumber;
    private final int studentId;
    private final int position; // 0 = scoreboard, 1 = islands

    public MoveStudentMessage(String nickname, int studentId, int position, int islandNumber) {
        super(nickname, MessageType.MOVE_STUDENT);
        this.studentId = studentId;
        this.islandNumber = islandNumber;
        this.position = position;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getPosition() {
        return position;
    }

    public int getIslandNumber() {
        return islandNumber;
    }

    @Override
    public String toString() {
        return "MoveStudent[" + "nickname:" + getNickname() + ", islandNumber:" + this.islandNumber + "]";
    }
}
