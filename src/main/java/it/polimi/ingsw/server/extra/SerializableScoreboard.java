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

    public SerializableScoreboard(Scoreboard scoreboard, Player player){
        this.nickname = player.getNickname();
        setStudents(scoreboard);
        setProfessors(scoreboard);
        this.coins = player.getCoin();
        this.towerColor = scoreboard.getTowerColor();
        this.towerNumber = scoreboard.getNumTowers();
    }

    private void setProfessors(Scoreboard scoreboard) {
        for(ProfessorPawn professor : scoreboard.getProfessorList()){
            professors.add(professor.getColor());
        }
    }

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

    public int getDiningRedStudents() {
        return diningRedStudents;
    }

    public int getDiningGreenStudents() {
        return diningGreenStudents;
    }

    public int getDiningBlueStudents() {
        return diningBlueStudents;
    }

    public int getDiningYellowStudents() {
        return diningYellowStudents;
    }

    public int getDiningPinkStudents() {
        return diningPinkStudents;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public TowerColors getTowerColor() {
        return towerColor;
    }

    public ArrayList<PawnColors> availableProfessors(){
        return professors;
    }

    public int getCoins() {
        return coins;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<PawnColors> getEntrance() {
        return entrance;
    }

    public ArrayList<Integer> getEntranceId() { return entranceId; }
}
