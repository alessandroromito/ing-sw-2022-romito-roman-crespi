package it.polimi.ingsw.server.model.component;

import it.polimi.ingsw.server.enumerations.Animals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {

    private Animals animal;
    private int value;
    private int movement;
    AssistantCard assistantCard = new AssistantCard(10, 11, 12);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setAnimal() {
        assistantCard.setAnimal(animal);
    }

    @Test
    void getAnimal() {
        if (assistantCard.getAnimal() != null)
            assertEquals(Animals.class, assistantCard.getAnimal().getClass());
    }

    @Test
    void getValue() {
        assertEquals(11, assistantCard.getValue());
    }

    @Test
    void getMovement() {
        assertEquals(12, assistantCard.getMovement());
    }
}