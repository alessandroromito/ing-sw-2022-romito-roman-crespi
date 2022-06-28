package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.controller.GameController;

import java.io.*;
import java.nio.file.*;

/**
 * This class represents the implementation of the persistence additional functionality
 */
public class DataSaving {

    /**
     * Save the PersistenceGameController in the file GameController.rcr
     * @param gameController gameController to be saved
     * @throws IOException due to working on files.
     */
    // -> ATTENZIONE!! Il game controller deve essere collegato a game/model <-
    public void save (GameController gameController) throws IOException {
        PersistenceGameController persistenceGameController = new PersistenceGameController(gameController);

        FileOutputStream fileOutputStream = new FileOutputStream(new File(GameController.SAVING));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(persistenceGameController);
    }

    /**
     * Restore the gameController from the PersistenceGameController saved in the file GameController.rcr
     * @return the game controller in the file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GameController restore() throws IOException, ClassNotFoundException {
        PersistenceGameController persistenceGameController;
        FileInputStream fileInputStream = new FileInputStream(new File(GameController.SAVING));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        persistenceGameController = (PersistenceGameController) objectInputStream.readObject();
        return persistenceGameController.getGameController();
    }

    /**
     * Removes the file GameController.rcr if exists.
     * @throws IOException
     */
    public void remove() throws IOException {
        File file = new File(GameController.SAVING);
        Files.deleteIfExists(file.toPath());
    }
}
