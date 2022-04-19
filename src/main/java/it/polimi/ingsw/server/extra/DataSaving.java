package it.polimi.ingsw.server.extra;

import it.polimi.ingsw.controller.GameController;

import java.io.*;
import java.nio.file.Files;

public class DataSaving {

    // -> ATTENZIONE!! Il game controller deve essere collegato a game/model <-
    public void save (GameController gameController) throws IOException {
        PersistenceGameController persistenceGameController = new PersistenceGameController(gameController);

        FileOutputStream fileOutputStream = new FileOutputStream(new File(GameController.SAVING));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(persistenceGameController);
    }

    public GameController restore() throws IOException, ClassNotFoundException {
        PersistenceGameController persistenceGameController;
        FileInputStream fileInputStream = new FileInputStream(new File(GameController.SAVING));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        persistenceGameController = (PersistenceGameController) objectInputStream.readObject();
        return persistenceGameController.getGameController();
    }

    public void remove() throws IOException {
        File file = new File(GameController.SAVING);
        Files.deleteIfExists(file.toPath());
    }
}
