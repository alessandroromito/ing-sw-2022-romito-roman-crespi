package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloudTest {
    Cloud cloud;

    @BeforeEach
    void setUp() {
        cloud = new Cloud(0);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public ArrayList<StudentDisc> getCloudStudents() {
        ArrayList<StudentDisc> cloudStudents = new ArrayList<>();
        StudentDisc studentDisc1, studentDisc2, studentDisc3;
        studentDisc1 = new StudentDisc(1, PawnColors.RED);
        studentDisc2 = new StudentDisc(2, PawnColors.BLUE);
        studentDisc3 = new StudentDisc(3, PawnColors.YELLOW);
        reset();
        addStudent(studentDisc1);
        addStudent(studentDisc2);
        addStudent(studentDisc3);
        assertEquals(getCloudStudents().get(0), studentDisc1);
        assertEquals(getCloudStudents().get(1), studentDisc2);
        assertEquals(getCloudStudents().get(2), studentDisc3);
        return cloudStudents;
    }

    @Test
    void reset() {
        ArrayList<StudentDisc> cloudStudents = new ArrayList<>();
        StudentDisc studentDisc1, studentDisc2, studentDisc3;
        studentDisc1 = new StudentDisc(1, PawnColors.RED);
        studentDisc2 = new StudentDisc(2, PawnColors.BLUE);
        studentDisc3 = new StudentDisc(3, PawnColors.YELLOW);
        addStudent(studentDisc1);
        addStudent(studentDisc2);
        addStudent(studentDisc3);

        reset();
        assertEquals(getCloudStudents(), null);
    }

    @Test
    void addStudent(StudentDisc studentDisc) {
        cloud.addStudent(studentDisc);
        ArrayList<StudentDisc> cloudStudents = getCloudStudents();
        int k = 0;
        for( StudentDisc i : cloudStudents) {
            if (studentDisc == cloudStudents.get(k)) {
                assert true;
                return;
            }
            k++;
        }
        assert false;

    }
}