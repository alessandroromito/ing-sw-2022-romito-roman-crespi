package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    private List<Integer> bagStudents;
    private Bag bag;

    @BeforeEach
    void setUp() {
        bagStudents = new ArrayList<Integer>();
        bag = new Bag();
    }

    @AfterEach
    void tearDown() {
        bagStudents = null;
        bag = null;
    }

    @Test
    void getSorted() {
    }

    @Test
    void isStudentsListEmpty_Empty() {
        assertTrue(bagStudents.isEmpty());
        //assertEquals(bagStudents.getType(), ArrayList<bagStudents> );
    }

    @Test
    void isStudentsListEmpty_NotEmpty() {
        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        for ( int i = 130 ; i > 1 ; i--) {
            this.bagStudents.remove(bagStudents.size()-1);
            assertFalse(bagStudents.isEmpty());
        }
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