package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.enumerations.TowerColors;
import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.Card_209;
import it.polimi.ingsw.server.model.component.charactercards.Card_219;
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
    boolean expertMode;

    private int activeCardID;

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
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                    Runtime.getRuntime().exec("cls");
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (Exception e) {
            //  Handle any exceptions.
        }

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
            expertMode = finalGamemode != "Normale";
        }
        catch (ExecutionException e) {
            out.println("ExecutionException!");
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
            out.println(nickname);
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
        out.println("------------------------------");

        //islands
        for(SerializableIsland island : islands)
        {
            if(island.isGhost()){
                Iterator<Integer> i = island.getReferencedIslands().iterator();
                out.print("ISOLA ");
                out.print(i.next());
                while(i.hasNext()) {
                    out.print(", ");
                    out.print(i.next());
                }
                out.println(":");
            }else {
                out.println("ISOLA " + (island.getId() + 1) + ":");
            }

            if(gameSerialized.getMotherNaturePos() == island.getId() || (!island.getReferencedIslands().isEmpty() && island.getReferencedIslands().contains(gameSerialized.getMotherNaturePos())))
                out.println(ANSI_WHITE + "MOTHER NATURE" + ANSI_RESET);

            if(island.getTowerNumber() != 0){
                for(int i=0; i < island.getTowerNumber(); i++)
                    out.println(printTower(island.getTowerColor()));
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

        out.println("------------------------------");

        //scoreboard
        for(SerializableScoreboard scoreboard : scoreboards){
            if(scoreboard.getNickname().equals(nickname)){
                out.println("LA TUA SCOREBOARD:");
            }
            else {
                out.println("SCOREBOARD DI " + scoreboard.getNickname() + ":");
            }
            if(expertMode)
                out.println("Coin: " + scoreboard.getCoins());

            showScoreboard(scoreboard);
        }
    }

    public void showScoreboard(SerializableScoreboard currentPlayerScoreboard){

        out.println("Torri:");
        for(int i=0; i< currentPlayerScoreboard.getTowerNumber(); i++){
            out.print(printTower(currentPlayerScoreboard.getTowerColor()));
        }
        out.println();

        out.println("Dining Room:");
        for(int i = 0; i < currentPlayerScoreboard.getDiningRedStudents(); i++)
            out.print(ANSI_RED + "o" + ANSI_RESET);
        out.println();
        for(int i = 0; i < currentPlayerScoreboard.getDiningBlueStudents(); i++)
            out.print(ANSI_BLUE + "o" + ANSI_RESET);
        out.println();
        for(int i = 0; i < currentPlayerScoreboard.getDiningYellowStudents(); i++)
            out.print(ANSI_YELLOW + "o" + ANSI_RESET);
        out.println();
        for(int i = 0; i < currentPlayerScoreboard.getDiningGreenStudents(); i++)
            out.print(ANSI_GREEN + "o" + ANSI_RESET);
        out.println();
        for(int i = 0; i < currentPlayerScoreboard.getDiningPinkStudents(); i++)
            out.print(ANSI_PINK + "o" + ANSI_RESET);

        out.println();
        out.println("Entrata:");
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

        out.println("Professori:");
        for(PawnColors color : currentPlayerScoreboard.availableProfessors()) {
            switch (color) {
                case RED -> out.println(ANSI_RED + "PROFESSOR RED" + ANSI_RESET);
                case BLUE -> out.println(ANSI_BLUE + "PROFESSOR BLUE" + ANSI_RESET);
                case YELLOW -> out.println(ANSI_YELLOW + "PROFESSOR YELLOW" + ANSI_RESET);
                case PINK -> out.println(ANSI_PINK + "PROFESSOR PINK" + ANSI_RESET);
                case GREEN -> out.println(ANSI_GREEN + "PROFESSOR GREEN" + ANSI_RESET);
            }
        }
        out.println();
    }

    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland){
        out.println("Isole unite:");
        for(Integer i : unifiedIsland){
            out.print( i + ", ");
        }
    }

    @Override
    public void askCharacterCard(List<CharacterCard> characterCards) {
        int choose;
        char answer;
        try{
            do {
                out.println("E' il tuo turno...");
                out.println("Vuoi usare una carta personaggio? Y/N");
                answer = readRow().charAt(0);
                if(answer != 'Y'){
                    notifyObserver(obs -> obs.onUpdateUseEffect(false));
                    return;
                }

                out.println("Scegli tra le seguenti Carte Personaggio:");
                int i = 0;
                for (CharacterCard characterCard : characterCards){
                    out.println("Carta " + i + " - Costo " + characterCard.getCost());
                    printEffect(characterCard.getID());
                    i++;
                }
                out.println("Inserisci un numero tra 0 e " + (characterCards.size() - 1) + ":");
                choose = Integer.parseInt(readRow());
                if(choose > characterCards.size() - 1 || choose < 0)
                    out.println("Numero inserito non valido. Riprovare.");
            } while(choose > characterCards.size() - 1 || choose < 0);

            applyEffect(characterCards.get(choose));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void applyEffect(CharacterCard characterCard) {
        switch (characterCard.getID()){
            case 209 -> {
                Card_209 card209 = (Card_209) characterCard;
                int studentPos;
                int islandNum;

                try{
                    boolean error;
                    do {
                        error = false;
                        out.println("Scegli 1 studente:");
                        int i = 0;
                        for (StudentDisc student : card209.getStudents()){
                            out.println(i + " " + printStudent(student));
                            i++;
                        }
                        studentPos = Integer.parseInt(readRow());
                        if(studentPos > i - 1 || studentPos < 0) {
                            out.println("Numero inserito non valido. Riprovare.");
                            error = true;
                        }
                    } while(error);

                    do {
                        error = false;
                        out.println("Scegli 1 isola:");
                        islandNum = Integer.parseInt(readRow());
                        if(islandNum > 12 || islandNum < 0) {
                            out.println("Numero inserito non valido. Riprovare.");
                            error = true;
                        }
                    } while(error);

                    int finalStudentPos = studentPos;
                    int finalIslandNum = islandNum;
                    notifyObserver(obs -> obs.onUpdateUse209(finalStudentPos, finalIslandNum));

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 210 -> {
                notifyObserver(ViewObserver::onUpdateUse210);
            }
            case 211 -> {
                int islandNum;

                try{
                    boolean error;
                    do {
                        error = false;
                        out.println("Scegli 1 isola:");
                        islandNum = Integer.parseInt(readRow());
                        if(islandNum > 12 || islandNum < 0) {
                            out.println("Numero inserito non valido. Riprovare.");
                            error = true;
                        }
                    } while(error);

                    int finalIslandNum = islandNum;
                    notifyObserver(obs -> obs.onUpdateUse211(finalIslandNum));

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 212 -> {
                activeCardID = 212;
                notifyObserver(ViewObserver::onUpdateUse212);
            }
            case 213 -> {
                int islandNum;

                try{
                    boolean error;
                    do {
                        error = false;
                        out.println("Scegli 1 isola:");
                        islandNum = Integer.parseInt(readRow());
                        if(islandNum > 12 || islandNum < 0) {
                            out.println("Numero inserito non valido. Riprovare.");
                            error = true;
                        }
                    } while(error);

                    int finalIslandNum = islandNum;
                    notifyObserver(obs -> obs.onUpdateUse213(finalIslandNum));

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 214 -> notifyObserver(ViewObserver::onUpdateUse214);
            case 216 -> notifyObserver(ViewObserver::onUpdateUse216);
            case 217 -> {
                String color;

                try{
                    boolean error;
                    do {
                        error = false;
                        out.println("Scegli un colore:");
                        out.print((ANSI_RED + "rosso, " + ANSI_RESET));
                        out.print((ANSI_YELLOW + "giallo, " + ANSI_RESET));
                        out.print((ANSI_GREEN + "verde, " + ANSI_RESET));
                        out.print((ANSI_BLUE + "blu, " + ANSI_RESET));
                        out.println((ANSI_PINK + "rosa" + ANSI_RESET));

                        color = readRow();
                        if(!color.equals("rosso") && !color.equals("verde") && !color.equals("giallo") && !color.equals("rosa") && !color.equals("blu")) {
                            out.println("Colore inserito non valido. Riprovare.");
                            error = true;
                        }
                    } while(error);

                    switch (color){
                        case "rosso" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.RED));
                        case "giallo" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.YELLOW));
                        case "verde" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.GREEN));
                        case "blu" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.BLUE));
                        case "rosa" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.PINK));
                    }

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case 219 -> {
                Card_219 card219 = (Card_219) characterCard;
                int studentPos;

                try{
                    boolean error;
                    do {
                        error = false;
                        out.println("Scegli 1 studente:");
                        int i = 0;
                        for (StudentDisc student : card219.getStudents()){
                            out.println(i + " " + printStudent(student));
                            i++;
                        }
                        studentPos = Integer.parseInt(readRow());
                        if(studentPos > i - 1 || studentPos < 0) {
                            out.println("Numero inserito non valido. Riprovare.");
                            error = true;
                        }
                    } while(error);

                    int finalStudent = studentPos;
                    notifyObserver(obs -> obs.onUpdateUse219(finalStudent));

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + characterCard.getID());
        }
    }

    private void printEffect(int ID) {
        switch(ID){
            case 209 -> out.println(ANSI_GREEN + "Prendi 1 studente dalla carta e piazzalo su un isola a tua scelta." + ANSI_RESET );
            case 210 -> out.println(ANSI_GREEN + "Durante questo turno prendi il controllo dei professori anche se nella tua sala hai lo stesso numero di studenti del giocatore che li controlla in quel momento." + ANSI_RESET );
            case 211 -> out.println(ANSI_GREEN + "Scegli un isola e calcola la maggioranza come se madre natura avesse terminato il suo percorso lì. \nIn questo turno madre natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata" + ANSI_RESET );
            case 212 -> out.println(ANSI_GREEN + "Puoi muovere madre natura di 2 isole addizionali rispetto a quanto indicato sulla carta assistente." + ANSI_RESET );
            case 213 -> out.println(ANSI_GREEN + "Piazza una tessera divieto su un isola a tua scelta, la prima volta che madre natura termina il suo movimento lì verrà rimossa e non verrà calcolata l'influenza ne piazzate torri. " + ANSI_RESET );
            case 214 -> out.println(ANSI_GREEN + "Durante il conteggio dell'influenza su un isola, le torri presenti non vengono calcolate." + ANSI_RESET );
            case 216 -> out.println(ANSI_GREEN + "In questo turno, durante il calcolo dell'influenza hai 2 punti addizionali." + ANSI_RESET );
            case 217 -> out.println(ANSI_GREEN + "Scegli un colore di uno studente, in questo turno durante il calcolo dell'influenza quel colore non fornisce influenza. " + ANSI_RESET );
            case 219 -> out.println(ANSI_GREEN + "Prendi 1 studente da questa carta e piazzalo nella tua sala." + ANSI_RESET );
        }
    }

    @Override
    public void showGameInfo(List<String> playersNicknames, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards) {
        out.println("Informazioni chiave:");
        out.println("Giocatori in partita: ");
        for(String nickname : playersNicknames) {
            out.println(nickname);
        }
        out.println("Numero di isole unificate: " + unifiedIslandsNumber);
        out.println("Studenti rimanenti nella bag: " + remainingBagStudents);
        out.println("Player che sta giocando: " + activePlayer);
        //da implementare se serve le charactercards
    }

    @Override
    public void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer) {
        out.println("Informazioni chiave:");
        out.println("Giocatori in partita: ");
        for(String nickname : playersNicknames) {
            out.println(nickname);
        }
        out.println("Player che sta giocando: " + activePlayer);
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

                if(Objects.equals(dest, "Isola") || Objects.equals(dest, "isola") || Objects.equals(dest, "i"))
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
        boolean error;
        try{
            do {
                error = false;
                if(activeCardID == 212) maxSteps = maxSteps + 2;
                out.println("Di quante isole vuoi muovere Madre Natura? Al massimo puoi fare " + maxSteps + " passi.");

                steps = Integer.parseInt(readRow());

                if(steps <= 0 || steps > maxSteps){
                    out.println("Impossibile muoversi di " + steps + " passi. Riprova!");
                    error = true;
                }
            } while(error);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        int finalSteps = steps;
        notifyObserver(obs -> obs.onUpdateMotherNaturePosition(finalSteps));
        activeCardID = -1;
    }

    @Override
    public void askToChooseACloud(ArrayList<Cloud> cloudList) {
        int choose = -1;
        try{
            do {
                out.println("Scegli tra le seguenti Nuvole:");
                for (Cloud cloud : cloudList) {
                    out.println("Nuvola " + cloud.getCloudID());
                    for(StudentDisc studentDisc : cloud.getCloudStudents()){
                        out.print(printStudent(studentDisc));
                    }
                    out.println();
                }

                out.println("Inserisci un numero tra 0 e " + (cloudList.size() - 1) + ":");

                choose = Integer.parseInt(readRow());

                if(choose > cloudList.size()-1 || choose < 0)
                    out.println("Numero inserito non valido. Riprovare.");

            }while(choose > cloudList.size()-1 || choose < 0);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        int finalChoose = choose;
        ArrayList<Cloud> cloud = new ArrayList<>();
        cloud.add(cloudList.get(finalChoose));

        notifyObserver(obs -> obs.onUpdatePickCloud(cloud));

        clearCli();
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
        out.println("Il gioco è terminato. Il VINCITORE è " + winner + "!");
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

    public String printTower(TowerColors color) {
        switch (color) {
            case BLACK -> {
                return (ANSI_BLACK + "T" + ANSI_RESET);
            }
            case GREY -> {
                return (ANSI_GREY + "T" + ANSI_RESET);
            }
            case WHITE -> {
                return (ANSI_WHITE + "T" + ANSI_RESET);
            }
        }
        return null;
    }

}
