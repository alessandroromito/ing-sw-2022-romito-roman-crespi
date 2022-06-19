package it.polimi.ingsw.server.model.map;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void getCloudStudents() {
        ArrayList<StudentDisc> cloudStudents = new ArrayList<>();
        StudentDisc studentDisc1, studentDisc2, studentDisc3;
        studentDisc1 = new StudentDisc(1, PawnColors.RED);
        studentDisc2 = new StudentDisc(2, PawnColors.BLUE);
        studentDisc3 = new StudentDisc(3, PawnColors.YELLOW);
        cloud.reset();
        cloud.addStudent(studentDisc1);
        cloud.addStudent(studentDisc2);
        cloud.addStudent(studentDisc3);
        assertEquals(cloud.getCloudStudents().get(0), studentDisc1);
        assertEquals(cloud.getCloudStudents().get(1), studentDisc2);
        assertEquals(cloud.getCloudStudents().get(2), studentDisc3);
    }

    @Test
    void reset() {
        ArrayList<StudentDisc> cloudStudents = new ArrayList<>();
        StudentDisc studentDisc1, studentDisc2, studentDisc3;
        studentDisc1 = new StudentDisc(1, PawnColors.RED);
        studentDisc2 = new StudentDisc(2, PawnColors.BLUE);
        studentDisc3 = new StudentDisc(3, PawnColors.YELLOW);
        cloud.addStudent(studentDisc1);
        cloud.addStudent(studentDisc2);
        cloud.addStudent(studentDisc3);

        cloud.reset();
        assertTrue(cloud.getCloudStudents().isEmpty());
    }

    @Test
    void addStudent() {
        StudentDisc studentDisc = new StudentDisc(10, PawnColors.RED);
        cloud.addStudent(studentDisc);
        ArrayList<StudentDisc> cloudStudents = cloud.getCloudStudents();
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

    @Test
    void getCloudID() {
        Cloud cloud = new Cloud(10);
        assertEquals(10, cloud.getCloudID());
    }
}