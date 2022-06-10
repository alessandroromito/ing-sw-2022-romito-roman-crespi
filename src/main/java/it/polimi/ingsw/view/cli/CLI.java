package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CLI extends ViewObservable implements View {

    private final PrintStream out;
    private Thread readThread;

    private String nickname;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREY = "\u001B[30;1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001b[32;1m";
    public static final String ANSI_YELLOW = "\u001b[33;1m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PINK = "\u001b[35;1m";
    public static final String ANSI_WHITE = "\u001b[37;1m";

    public CLI(){
        out = System.out;
    }

    public void initialization() {
        out.println(
                """
                        / ____/____(_)___ _____  / /___  _______
                        / __/ / ___/ / __ `/ __ |/ __/ / / / ___/
                        / /___/ /  / / /_/ / / / / /_/ /_/ (__  )
                        /_____/_/  /_/|__,_/_/ /_/|__/|__, /____/
                                                     /____/"""
                );

        out.println("Welcome in Eriantys!");
        out.println("Game developed by Alessandro Romito, Gioele Crespi and Matteo Roman");
        String GAME_VERSION = "1.0";
        out.println("GAME VERSION: " + GAME_VERSION);
        askServerParametersConfiguration();
    }

    public void askServerParametersConfiguration() {
        HashMap<String, String> server = new HashMap<>();
        boolean validInput;

        out.println("Inserisci i seguenti parametri per iniziare a giocare!\n");
        do{
            //out.println("Server address: ");
            String address = "127.0.0.1";
            //try {
            //    address = readRow();
            //} catch (ExecutionException | InterruptedException e) {
            //    e.printStackTrace();
            //}
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

        do{
            //out.println("Port address: ");
            String port = "1511";
            //try {
            //    port = readRow();
            //} catch (ExecutionException | InterruptedException e) {
            //    e.printStackTrace();
            //}
            if(Validator.validatePort(port)) {
                server.put("port", port);
                validInput = true;
            }
            else {
                clearCli();
                out.println("Porta vuota o non valida! Riprova!");
                validInput = false;
            }
        }while(!validInput);
        notifyObserver(obs -> obs.onUpdateServerDetails(server));
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

    @Override
    public void askPlayersNumber() {
        int playersNumber = 0;
        try{
            do{
                try{
                    out.println("Inserire numero di giocatori: ");
                    playersNumber = Integer.parseInt(readRow());
                }catch(InterruptedException e){
                    out.println("Errore!");
                }
                if(playersNumber>=4 || playersNumber<=1) out.println("Errore! Scegliere tra 2 e 3");
            }while(playersNumber>=4 || playersNumber<=1);
            int finalPlayersNumber = playersNumber;
            notifyObserver(obs -> obs.onUpdatePlayersNumber(finalPlayersNumber));
        }
        catch (ExecutionException e) {
            out.println("Errore!");
        }
    }

    @Override
    public void askGameMode() {
        String gamemode = "";
        try{
            do{
                try{
                    out.println("Scegli la modalità di gioco (Normale o Esperta): ");
                    gamemode = readRow();
                }catch(InterruptedException e){
                    out.println("Errore!");
                }
                if(!Objects.equals(gamemode, "Normale") && !Objects.equals(gamemode, "Esperta")) out.println("Errore! Scegliere tra Normale ed Esperta");
            }while(!Objects.equals(gamemode, "Normale") && !Objects.equals(gamemode, "Esperta"));

            String finalGamemode = gamemode;
            notifyObserver(obs -> obs.onUpdateGameMode(finalGamemode));
        }
        catch (ExecutionException e) {
            out.println("Errore!");
        }
    }

    @Override
    public void askPlayerNickname() {
        out.println("Inserisci il tuo nickname: ");
        try{
            String nickname = readRow();
            this.nickname = nickname;
            notifyObserver(obs -> obs.onUpdateNickname(nickname));
        }
        catch(ExecutionException | InterruptedException e){
            out.println("Errore");
        }
    }

    @Override
    public void showLobby(List<String> playersNickname, int numPlayers) {
        out.println("Giocatori presenti nella lobby:");
        for(String nickname : playersNickname) {
            out.println(nickname + "\n");
        }
        out.println("Stato della lobby: " + playersNickname.size() + " / " + numPlayers);
    }

    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected, String text) {
        out.println("\n" + nicknameDisconnected + text);
        System.exit(1);
    }

    @Override
    public void showErrorMessage(String error) {
        readThread.interrupt();
        out.println("\nERRORE: " + error);
        System.exit(1);
    }

    @Override
    public void showGameScenario(GameSerialized gameSerialized) {
        ArrayList<SerializableScoreboard> scoreboards = gameSerialized.getSerializableScoreboard();
        ArrayList<SerializableIsland> islands = gameSerialized.getSerializableIslands();

        //islands
        for(SerializableIsland island : islands)
        {
            out.print("ISOLA " + (island.getId()+1) + ":");
            if(gameSerialized.getMotherNaturePos() == island.getId())
                out.println(ANSI_WHITE + "MOTHER NATURE" + ANSI_RESET);
            else out.println();

            if(island.getTowerNumber() != 0){
                for(int i=0; i < island.getTowerNumber(); i++)
                    switch (island.getTowerColor()){
                        case BLACK -> out.print(ANSI_BLACK + "T" + ANSI_RESET);
                        case GREY -> out.print(ANSI_GREY + "T" + ANSI_RESET);
                        case WHITE -> out.print(ANSI_WHITE + "T" + ANSI_RESET);
                    }
            }
            for(int i=0; i < island.getRedStudents(); i++)
                out.print(ANSI_RED + "o" + ANSI_RESET);
            for(int i=0; i < island.getBlueStudents(); i++)
                out.print(ANSI_BLUE + "o" + ANSI_RESET);
            for(int i=0; i < island.getYellowStudents(); i++)
                out.print(ANSI_YELLOW + "o" + ANSI_RESET);
            for(int i=0; i < island.getGreenStudents(); i++)
                out.print(ANSI_GREEN + "o" + ANSI_RESET);
            for(int i=0; i < island.getPinkStudents(); i++)
                out.print(ANSI_PINK + "o" + ANSI_RESET);

            out.println();
        }

        //scoreboard
        for(SerializableScoreboard scoreboard : scoreboards){
            if(scoreboard.getNickname().equals(nickname)){
                out.println("LA TUA SCOREBOARD:");
            }
            else {
                out.println("SCOREBOARD DI " + scoreboard.getNickname() + ":");
            }
            showScoreboard(scoreboard);
        }
    }

    public void showScoreboard(SerializableScoreboard currentPlayerScoreboard){
        out.println("Studenti nella dining room:");
        for(int i = 0; i < currentPlayerScoreboard.getDiningRedStudents(); i++)
            out.print(ANSI_RED + "o" + ANSI_RESET);
        for(int i = 0; i < currentPlayerScoreboard.getDiningBlueStudents(); i++)
            out.print(ANSI_BLUE + "o" + ANSI_RESET);
        for(int i = 0; i < currentPlayerScoreboard.getDiningYellowStudents(); i++)
            out.print(ANSI_YELLOW + "o" + ANSI_RESET);
        for(int i = 0; i < currentPlayerScoreboard.getDiningGreenStudents(); i++)
            out.print(ANSI_GREEN + "o" + ANSI_RESET);
        for(int i = 0; i < currentPlayerScoreboard.getDiningPinkStudents(); i++)
            out.print(ANSI_PINK + "o" + ANSI_RESET);

        out.println();
        out.println("Studenti in entrata:");
        for(PawnColors color : currentPlayerScoreboard.getEntrance()) {
            switch (color){
                case RED -> out.print(ANSI_RED + "o" + ANSI_RESET);
                case BLUE -> out.print(ANSI_BLUE + "o" + ANSI_RESET);
                case YELLOW -> out.print(ANSI_YELLOW + "o" + ANSI_RESET);
                case PINK -> out.print(ANSI_PINK + "o" + ANSI_RESET);
                case GREEN -> out.print(ANSI_GREEN + "o" + ANSI_RESET);
            }
        }
        out.println();

        out.println("Professori posseduti:");
        for(PawnColors color : currentPlayerScoreboard.availableProfessors()) {
            switch (color) {
                case RED -> out.println(ANSI_RED + "PROFESSOR RED" + ANSI_RESET);
                case BLUE -> out.println(ANSI_BLUE + "PROFESSOR BLUE" + ANSI_RESET);
                case YELLOW -> out.print(ANSI_YELLOW + "PROFESSOR YELLOW" + ANSI_RESET);
                case PINK -> out.println(ANSI_PINK + "PROFESSOR PINK" + ANSI_RESET);
                case GREEN -> out.println(ANSI_GREEN + "PROFESSOR GREEN" + ANSI_RESET);
            }
        }
    }

    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland){

    }

    @Override
    public void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {
        out.println("Informazioni chiave:");
        out.println("Giocatori in partita: ");
        for(String nickname : playersNickname) {
            out.println(nickname + "\n");
        }
        out.println("Numero di isole unificate: " + unifiedIslandsNumber);
        out.println("Studenti rimanenti nella bag: " + remainingBagStudents);
        out.println("Player che sta giocando: " + activePlayer);
        //da implementare se serve le charactercards
    }

    @Override
    public void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer) {
    }

    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards) {
        int choose;
        try{
            do {
                out.println("Scegli tra le seguenti Carte Assistente:");
                int i = 0;
                for (AssistantCard assistantCard : assistantCards){
                    out.println("Carta " + i + " | Valore: " + assistantCard.getValue() + " Numero Passi: " + assistantCard.getMovement());
                    i++;
                }
                out.println("Inserisci un numero tra 0 e " + (assistantCards.size() - 1) + ":");
                choose = Integer.parseInt(readRow());
                if(choose > assistantCards.size()-1 || choose < 0)
                    out.println("Numero inserito non valido. Riprovare.");
            } while(choose > assistantCards.size()-1 || choose < 0);

            int finalChoose = choose;
            notifyObserver(obs -> obs.onUpdatePlayAssistantCard(List.of(assistantCards.get(finalChoose))));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {
        int choose;
        String dest;
        boolean toIsland = false;
        int islandDest = -1;
        boolean error;
        StudentDisc studentToReturn = null;

        out.println("E' il tuo turno...");

        try{
            // SCELTA STUDENTE
            out.println("Scegli uno studente da muovere");
            do {
                error = false;
                int i=0;

                for(StudentDisc student : studentDiscs){
                    out.println(i + " " + printStudent(student));
                    i++;
                }
                choose = Integer.parseInt(readRow());

                if(choose >= studentDiscs.size() || choose < 0){
                    out.println("Numero inserito non valido. Riprovare");
                    error = true;
                }else {
                    studentToReturn = studentDiscs.get(choose);
                    if(studentToReturn == null) {
                        out.println("Nessuno studente in quella posizione! Riprova!");
                        error = true;
                    }
                }
            }while(error);

            //SCELTA DESTINAZIONE
            do {
                error = false;
                out.println("Dove vuoi spostarlo? (Isola o Sala)");

                dest = readRow();

                if(Objects.equals(dest, "Isola"))
                    toIsland = true;
                if (dest != null && !checkDest(dest)){
                    out.println("Destinazione non corretta. Ricontrollare");
                    error = true;
                }
            } while(error);

            //SCELTA ISOLA
            do{
                error = false;

                if(toIsland) {
                    out.println("Inserisci numero dell'isola");

                    islandDest = Integer.parseInt(readRow());

                    if(islandDest < 1 || islandDest > 12){
                        out.println("Numero inserito non valido. Riprovare");
                        error = true;
                    }
                }
            }while (error);

        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        StudentDisc finalStudentToReturn = studentToReturn;
        int finalIslandDest = islandDest;
        if(toIsland) {
            notifyObserver(obs -> obs.onUpdateMoveStudent(finalStudentToReturn, 1, finalIslandDest));
        } else notifyObserver(obs -> obs.onUpdateMoveStudent(finalStudentToReturn, 0, finalIslandDest));
    }

    private boolean checkDest(String dest) {
        return dest.equals("isola") || dest.equals("sala") || dest.equals("Isola") || dest.equals("Sala");
    }

    @Override
    public void askToMoveMotherNature(int maxSteps) {
        int steps = 0;
        do {
            out.println("Di quante isole vuoi muovere Madre Natura? Al massimo puoi fare " + maxSteps + "passi.");
            try {
                steps = Integer.parseInt(readRow());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if(steps <= 0 || steps > maxSteps) out.println("Impossibile muoversi di " + steps + "passi. Riprova!");
            int finalSteps = steps;
            notifyObserver(obs -> obs.onUpdateMotherNaturePosition(finalSteps));
        } while(steps <= 0 || steps >= maxSteps);
    }

    @Override
    public void askToChooseACloud(List<Cloud> cloudList) {
        int choose = -1;
        do {
            out.println("Scegli tra le seguenti Nuvole:");
            for (Cloud cloud : cloudList) {
                out.println("Nuvola " + cloud.getCloudID());
                for(StudentDisc studentDisc : cloud.getCloudStudents()){
                    printStudent(studentDisc);
                }
            }

            out.println("Inserisci un numero tra 0 e " + (cloudList.size() - 1) + ":");
            try {
                choose = Integer.parseInt(readRow());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if(choose >= cloudList.size()-1 || choose < 0) out.println("Numero inserito non valido. Riprovare.");
        }while(choose >= cloudList.size()-1 || choose < 0);

        int finalChoose = choose;
        notifyObserver(obs -> obs.onUpdatePickCloud(List.of(cloudList.get(finalChoose))));
    }

    @Override
    public void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful) {
        if(playerNicknameAccepted && connectionSuccessful) {
            out.println("Sei stato connesso al server con successo! Benvenuto " + nickname);
        }
        else if(connectionSuccessful)
            askPlayerNickname();
        else if(playerNicknameAccepted)
            out.println("Max numero di giocatori raggiunto.");
        else
            showErrorMessage("Impossibile contattare il server. Riprova più tardi.");
    }

    @Override
    public void showVictoryMessage(String winner) {
        out.println("Il gioco è terminato. Il vincitore è " + winner + "!");
        System.exit(1);
    }

    public String printStudent(StudentDisc stud){
        if(stud != null){
            switch (stud.getColor()){
                case RED -> {
                    return (ANSI_RED + "o" + ANSI_RESET);
                }
                case BLUE -> {
                    return (ANSI_BLUE + "o" + ANSI_RESET);
                }
                case YELLOW -> {
                    return (ANSI_YELLOW + "o" + ANSI_RESET);
                }
                case PINK -> {
                    return (ANSI_PINK + "o" + ANSI_RESET);
                }
                case GREEN -> {
                    return (ANSI_GREEN + "o" + ANSI_RESET);
                }
            }
        }
        return null;
    }
}
