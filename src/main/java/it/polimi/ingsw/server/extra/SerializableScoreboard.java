package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Scoreboard;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game data serializable for scoreboards.
 */
public class SerializableScoreboard implements Serializable {
    @Serial
    private static final long serialVersionUID = 5449490962500277502L;

    private final String nickname;
    private final ArrayList<PawnColors> entrance = new ArrayList<>();
    private final ArrayList<Integer> entranceId = new ArrayList<>();
    private int diningRedStudents = 0;
    private int diningGreenStudents = 0;
    private int diningBlueStudents = 0;
    private int diningYellowStudents = 0;
    private int diningPinkStudents = 0;
    private final int towerNumber;

    private final int coins;
    private final TowerColors towerColor;
    private final ArrayList<PawnColors> professors = new ArrayList<>();

    /**
     * Default Constructor
     * @param scoreboard scoreboard t be serialized
     * @param player owner of the scoreboard
     */
    public SerializableScoreboard(Scoreboard scoreboard, Player player){
        this.nickname = player.getNickname();
        setStudents(scoreboard);
        setProfessors(scoreboard);
        this.coins = player.getCoin();
        this.towerColor = scoreboard.getTowerColor();
        this.towerNumber = scoreboard.getNumTowers();
    }

    /**
     * Fill the serializableScoreboard with the professors
     * @param scoreboard scoreboard of the player
     */
    private void setProfessors(Scoreboard scoreboard) {
        for(ProfessorPawn professor : scoreboard.getProfessorList()){
            professors.add(professor.getColor());
        }
    }

    /**
     * Fill the serializableScoreboard with the students
     * @param scoreboard scoreboard of the player
     */
    private void setStudents(Scoreboard scoreboard) {
        for(StudentDisc studentDisc : scoreboard.getEntrance()){
            if(studentDisc != null)
                entrance.add(studentDisc.getColor());
            if(studentDisc != null)
                entranceId.add(studentDisc.getID());
        }
        Integer[] dining = scoreboard.getDiningRoom();
        diningRedStudents = dining[0];
        diningBlueStudents = dining[1];
        diningGreenStudents = dining[2];
        diningYellowStudents = dining[3];
        diningPinkStudents = dining[4];
    }

    /**
     * Getter method
     * @return
     */
    public int getDiningRedStudents() {
        return diningRedStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getDiningGreenStudents() {
        return diningGreenStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getDiningBlueStudents() {
        return diningBlueStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getDiningYellowStudents() {
        return diningYellowStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getDiningPinkStudents() {
        return diningPinkStudents;
    }

    /**
     * Getter method
     * @return
     */
    public int getTowerNumber() {
        return towerNumber;
    }

    /**
     * Getter method
     * @return
     */
    public TowerColors getTowerColor() {
        return towerColor;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<PawnColors> availableProfessors(){
        return professors;
    }

    /**
     * Getter method
     * @return
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Getter method
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<PawnColors> getEntrance() {
        return entrance;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<Integer> getEntranceId() { return entranceId; }
}

