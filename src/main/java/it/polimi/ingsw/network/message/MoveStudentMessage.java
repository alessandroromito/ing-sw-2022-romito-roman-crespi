package it.polimi.ingsw.network.message;

import it.polimi.ingsw.network.server.MessageHandler;
import it.polimi.ingsw.server.enumerations.MessageType;
import it.polimi.ingsw.server.model.component.StudentDisc;

import java.io.Serial;
import java.util.List;

/**
 * Message that contains informations to move a student.
 */
public class MoveStudentMessage extends Message{

    @Serial
    private static final long serialVersionUID = -1L;

    private final int islandNumber;
    private final List<StudentDisc> studentDiscs;
    private final int position; // 0 = scoreboard, 1 = island

    /**
     * Default constructor.
     * @param nickname nickname of the player owner of the message and requester of moving the students.
     * @param studentDiscs studentDisc to move.
     * @param position position to move the studentDisc (0 dining room, 1 island).
     * @param islandNumber if position = 1 than islandNumber to move the student (0 to 11).
     */
    public MoveStudentMessage(String nickname, List<StudentDisc> studentDiscs, int position, int islandNumber) {
        super(nickname, MessageType.MOVE_STUDENT);
        this.studentDiscs = studentDiscs;
        this.islandNumber = islandNumber;
        this.position = position;
    }

    /**
     * @return studentDisc to move.
     */
    public List<StudentDisc> getStudentDiscs() {
        return studentDiscs;
    }

    /**
     * @return position to move the studentDisc (0 dining room, 1 island).
     */
    public int getPosition() {
        return position;
    }

    /**
     * @return if position = 1 than islandNumber to move the student (0 to 11).
     */
    public int getIslandNumber() {
        return islandNumber;
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
    public String toString() {
        return "MoveStudent[" + "nickname:" + getNickname() + ", studentID:" + (this.getNickname().equals("GAME_SERVER") ? "List" : studentDiscs.get(0).getID()) + ", position:" + position + ", islandNumber:" + this.islandNumber + "]";
    }
}
