package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    private ArrayList<StudentDisc> bagStudents = new ArrayList<>();
    private Bag bag = new Bag();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSorted() {
    }

    @Test
    void isStudentsListEmpty() {
        assertTrue(bagStudents.isEmpty());
        //assertEquals(bagStudents.getType(), ArrayList<bagStudents> );
    }

    @Test
    void getBagStudents(){

        //assertInstanceOf(ArrayList<StudentDisc>, bag.getBagStudents());
        //assertArrayEquals(bag.getBagStudents().toArray(new StudentDisc[0]), );
        //assertNotNull(bag.getBagStudents());
    }

    @Test
    void notifyMovingStudents() {
    }
}