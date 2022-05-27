package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.model.component.ProfessorPawn;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.player.Scoreboard;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableScoreboard implements Serializable {
    private static final long serialVersionUID = 1L;

    private int redStudents = 0;
    private int greenStudents = 0;
    private int blueStudents = 0;
    private int yellowStudents = 0;
    private int pinkStudents = 0;
    private int towerNumber = 0;
    private TowerColors towerColor;
    private ArrayList<PawnColors> professors = new ArrayList<>();

    public SerializableScoreboard(Scoreboard scoreboard){
        setColors(scoreboard);
        setProfessors(scoreboard);
        this.towerColor = scoreboard.getTowerColor();
        this.towerNumber = scoreboard.getNumTowers();
    }

    private void setProfessors(Scoreboard scoreboard) {
        for(ProfessorPawn professor : scoreboard.getProfessorList()){
            professors.add(professor.getColor());
        }
    }

    private void setColors(Scoreboard scoreboard) {
        for(StudentDisc studentDisc : scoreboard.getEntrance()){
            switch (studentDisc.getColor()) {
                case RED -> this.redStudents++;
                case BLUE -> this.blueStudents++;
                case GREEN -> this.greenStudents++;
                case YELLOW -> this.yellowStudents++;
                case PINK -> this.pinkStudents++;
            }
        }
    }

    public int getRedStudents() {
        return redStudents;
    }

    public int getGreenStudents() {
        return greenStudents;
    }

    public int getBlueStudents() {
        return blueStudents;
    }

    public int getYellowStudents() {
        return yellowStudents;
    }

    public int getPinkStudents() {
        return pinkStudents;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public TowerColors getTowerColor() {
        return towerColor;
    }
}
