package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.component.StudentDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    void pickSorted(){
        int id;
        for(int i = 1; i <= 130; i++ ){
            id = bag.pickSorted().getID();
            if(1 <= id-58 && id-58 <= 130) assert true;
            else assert false;
            System.out.println("ID: " + (id-59));
        }
    }

    /*@Test
    void pickSorted_1() {
        int k;
        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        assertFalse(bagStudents.isEmpty());
        Collections.shuffle(this.bagStudents);
        for (int i = 1 ; i <= 130 ; i++) {
            for(k = 0; bagStudents.get(k) != i && k < 130; k++){
                if (bagStudents.get(k) == i) assert true;
            }
            int temp = bagStudents.get(k);
            bagStudents.remove(k);
            if( bagStudents.size() - 1 > 130 - k )  assertNotEquals(bagStudents.get(k), temp );
        }
    }
     */

    @Test
    void isStudentsListEmpty_Empty() {
        assertEquals(bag.isStudentsListEmpty(), bag.getBagStudents().isEmpty());
    }

    /*@Test
    void isStudentsListEmpty_NotEmpty() {

        for ( int i = 1 ; i <= 130 ; i++)
            this.bagStudents.add(i);
        for ( int i = 130 ; i > 1 ; i--) {
            this.bagStudents.remove(bagStudents.size()-1);
            assertFalse(bagStudents.isEmpty());
        }
        assertFalse(bag.isStudentsListEmpty());
        assertEquals(bag.isStudentsListEmpty(), bag.getBagStudents().isEmpty());
    }
    */

    @Test
    void getBagStudents(){
        assertEquals(bag.getBagStudents().getClass(), bagStudents.getClass());
    }

}