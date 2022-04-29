package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    private ArrayList<StudentDisc> bagStudents;

    @BeforeEach
    void setUp() {
        Bag bag = new Bag();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getSorted() {
    }

    @Test
    void isStudentsListEmpty() {
        assertEquals(bagStudents.getType(), ArrayList<bagStudents> )
    }

    @Test
    void getBagStudents(){
        Bag bag = new Bag();
        //assertInstanceOf(ArrayList<StudentDisc>, bag.getBagStudents());
        //assertArrayEquals(bag.getBagStudents().toArray(new StudentDisc[0]), );
        //assertNotNull(bag.getBagStudents());
    }

    @Test
    void notifyMovingStudents() {
    }
}