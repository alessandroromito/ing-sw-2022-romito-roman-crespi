package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;
import javafx.application.Application;

public class EriantysClient {

    public static void main(String[] args) {

        boolean cliOn = false;

        for (String arg : args) {
            if (arg.equals("--cli")) {
                CLI cliView = new CLI();
                ClientController clientcontroller = new ClientController(cliView);
                cliView.addObserver(clientcontroller);
                cliView.initialization();
                cliOn = true;
                break;
            }
        }
        if(!cliOn)  Application.launch(GUI.class);
    }
}
