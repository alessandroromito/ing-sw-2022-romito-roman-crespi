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
            out.println("Server address: ");
            String address = null;
            try {
                address = readRow();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
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
            out.println("Port address: ");
            String port = null;
            try {
                port = readRow();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
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
            notifyObserver(obs -> obs.onUpdateNickname(nickname));
        }
        catch(ExecutionException | InterruptedException e){
            out.println("Errore");
        }
        this.nickname = nickname;
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
        //readThread.interrupt();   //con la persistenza alle disconnessioni non serve
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
        for(SerializableIsland i : islands)
        {
            out.println("Isola " + i.getId() + ":");
            out.println("\t Colore e numero delle torri : " + i.getTowerNumber() + i.getTowerColor());
            if (i.getRedStudents() != 0) out.println("\t Numero di studenti rossi : " + i.getRedStudents());
            if (i.getBlueStudents() != 0) out.println("\t Numero di studenti blu : " + i.getBlueStudents());
            if (i.getGreenStudents() != 0) out.println("\t Numero di studenti verdi : " + i.getGreenStudents());
            if (i.getYellowStudents() != 0) out.println("\t Numero di studenti gialli : " + i.getYellowStudents());
            if (i.getPinkStudents() != 0) out.println("\t Numero di studenti rosa : " + i.getPinkStudents());
        }

        //scoreboard
        for(SerializableScoreboard scoreboard : scoreboards){
            if(scoreboard.getNickname() == nickname){
                out.println("La tua scoreboard:");
            }
            else {
                out.println("Scoreboard giocatore " + scoreboard.getNickname() + ":");
            }
            showScoreboard(scoreboard);
        }


    }

    public void showScoreboard(SerializableScoreboard currentPlayerScoreboard){
        out.println("\t Studenti in entrata:");
        if(currentPlayerScoreboard.getRedStudents() != 0)    out.println("\t Numero di studenti rossi : " + currentPlayerScoreboard.getRedStudents());
        if(currentPlayerScoreboard.getBlueStudents() != 0)    out.println("\t Numero di studenti blu : " + currentPlayerScoreboard.getBlueStudents());
        if(currentPlayerScoreboard.getGreenStudents() != 0)    out.println("\t Numero di studenti verdi : " + currentPlayerScoreboard.getGreenStudents());
        if(currentPlayerScoreboard.getYellowStudents() != 0)    out.println("\t Numero di studenti gialli : " + currentPlayerScoreboard.getYellowStudents());
        if(currentPlayerScoreboard.getPinkStudents() != 0)    out.println("\t Numero di studenti rosa : " + currentPlayerScoreboard.getPinkStudents());
        out.println("Professori posseduti:");
        for(PawnColors i : currentPlayerScoreboard.availableProfessors()) {
            out.println("\t Professore " + i.toString());
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
        int choose = -1;
        do {
            out.println("Scegli tra le seguenti Carte Assistente:");
            for (AssistantCard assistantCard : assistantCards) out.println(assistantCard);
            out.println("Inserisci un numero tra 0 e " + (assistantCards.size() - 1) + ":");
            try {
                choose = Integer.parseInt(readRow());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if(choose >= assistantCards.size()-1 || choose < 0) out.println("Numero inserito non valido. Riprovare.");
        }while(choose >= assistantCards.size()-1 || choose < 0);
        List<AssistantCard> response = new ArrayList<>();
        response.add(assistantCards.get(choose));
        notifyObserver(obs -> obs.onUpdatePlayAssistantCard(response));
    }

    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {

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
            if(steps <= 0 || steps >= maxSteps) out.println("Impossibile muoversi di " + steps + "passi. Riprova!");
            int finalSteps = steps;
            notifyObserver(obs -> obs.onUpdateMotherNaturePosition(finalSteps));
        }while(steps <= 0 || steps >= maxSteps);
    }

    @Override
    public void askToChooseACloud(List<Cloud> cloudList) {
        int choose = -1;
        do {
            out.println("Scegli tra le seguenti Nuvole:");
            for (Cloud cloud : cloudList) out.println(cloud);
            out.println("Inserisci un numero tra 0 e " + (cloudList.size() - 1) + ":");
            try {
                choose = Integer.parseInt(readRow());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if(choose >= cloudList.size()-1 || choose < 0) out.println("Numero inserito non valido. Riprovare.");
        }while(choose >= cloudList.size()-1 || choose < 0);
        List<Cloud> response = new ArrayList<>();
        response.add(cloudList.get(choose));
        notifyObserver(obs -> obs.onUpdatePickCloud(response));
    }

    @Override
    public void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful) {
        if(playerNicknameAccepted && connectionSuccessful) {
            out.println("Sei stato connesso al server con successo! Benvenuto " + nickname);
        }
        else if(connectionSuccessful)    askPlayerNickname();
            else if(playerNicknameAccepted) out.println("Max numero di giocatori raggiunto.");
                else showErrorMessage("Impossibile contattare il server. Riprova più tardi.");
    }

    @Override
    public void showVictoryMessage(String winner) {
        out.println("Il gioco è terminato. Il vincitore è " + winner + "!");
        System.exit(1);
    }
}
