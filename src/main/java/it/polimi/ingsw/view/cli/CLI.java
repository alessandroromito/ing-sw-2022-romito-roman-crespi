package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;

public class CLI extends ViewObservable implements View {

    private final PrintStream out;
    private Thread inputThread;

    public CLI(){
        out = System.out;
    }



    public void clearCli() {
        out.flush();
    }
}
