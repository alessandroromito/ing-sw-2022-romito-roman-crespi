package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CLI extends ViewObservable implements View {

    private final String GAME_VERSION = "1.0";
    private final PrintStream out;
    private Thread readThread;

    public CLI(){
        out = System.out;
    }

    public void initialization() {
        out.println(
                "/ ____/____(_)___ _____  / /___  _______\n" +
                "/ __/ / ___/ / __ `/ __ |/ __/ / / / ___/\n" +
                "/ /___/ /  / / /_/ / / / / /_/ /_/ (__  )\n" +
                "/_____/_/  /_/|__,_/_/ /_/|__/|__, /____/\n" +
                "                             /____/"
                );

        out.println("Welcome in Eriantys!");
        out.println("Game developed by Alessandro Romito, Gioele Crespi and Matteo Roman");
        out.println("GAME VERSION: " + GAME_VERSION);
        parametersConfiguration();
    }

    public void parametersConfiguration() throws ExecutionException, InterruptedException {
        Map<String, String> server = new Map<>();
        boolean validInput;
        out.println("Inserisci i seguenti parametri per iniziare a giocare!\n");
        do{
            out.println("Server address: ");
            String address = readRow();
            if(Validator.validateIpAddress(address)) {
                server.put("address", address);
                validInput = true;
            }
            else {
                clearCli();
                out.println("Indirizzo vuoto o non valido! Riprova!");
                validInput = false;
            }
        }while(!validInput);

        //inserimento porta
        do{
            out.println("Port address: ");
            String port = readRow();
            if(Validator.validatePort(port)) {
                server.put("port", port);
                validInput = true;
            }
            else {
                clearCli();
                out.println("Indirizzo vuoto o non valido! Riprova!");
                validInput = false;
            }
        }while(!validInput);
        //observersrsrsrsrsrsrsrsrrsrsrsrsrsrsrsrrsrss
        //notifyObserver(obs -> obs.onUpdateServerInfo(server));
        //



            //Da implementare nel clientController






        //
    }

    public String readRow() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new ReadTask());
        readThread = new Thread(futureTask);
        readThread.start();
        return futureTask.get();
    }


    public void clearCli() {
        out.flush();
    }
}
