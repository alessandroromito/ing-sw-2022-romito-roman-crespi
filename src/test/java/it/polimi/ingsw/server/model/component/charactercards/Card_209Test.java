package it.polimi.ingsw.server.model.component.charactercards;

import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.CharArrayReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Card_209Test {


    private ArrayList<StudentDisc> students;
    private StudentDisc student1 = new StudentDisc(10, PawnColors.RED);
    private StudentDisc student2 = new StudentDisc(11, PawnColors.GREEN);
    private StudentDisc student3 = new StudentDisc(12, PawnColors.YELLOW);
    private StudentDisc student4 = new StudentDisc(13, PawnColors.PINK);
    private StudentDisc studentNext = new StudentDisc(14, PawnColors.BLUE);
    private int studentPos = 1;
    private Card_209 card209;


    @BeforeEach
    void setUp() {
        students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        card209 = new Card_209(students);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void use() {
        StudentDisc studentDiscRemoved = card209.use(studentPos, studentNext);
        assertEquals(StudentDisc.class , studentDiscRemoved.getClass());
        assertFalse(students.contains(studentDiscRemoved));
        assertTrue(students.contains(studentNext));
        assertEquals(studentNext, students.get(students.size()-1));
    }

    @Test
    void getStudents() {
        assertEquals(students, card209.getStudents());
    }
}