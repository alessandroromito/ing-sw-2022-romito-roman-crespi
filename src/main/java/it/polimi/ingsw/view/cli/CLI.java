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
import it.polimi.ingsw.server.model.component.charactercards.Card_215;
import it.polimi.ingsw.server.model.component.charactercards.Card_219;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

/**
 * Class that visualize the game trough the terminal
 */
public class CLI extends ViewObservable implements View {

    private final PrintStream out;

    private String nickname;
    boolean expertMode;

    private int activeCardID;
    private int coin;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREY = "\u001B[30;1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001b[32;1m";
    public static final String ANSI_YELLOW = "\u001b[33;1m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PINK = "\u001b[35;1m";
    public static final String ANSI_WHITE = "\u001b[37;1m";

    /**
     * Default constructor
     */
    public CLI(){
        out = System.out;
    }

    /**
     * Initialization of the cli view
     */
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

    /**
     *
     */
    public void askServerParametersConfiguration() {
        HashMap<String, String> server = new HashMap<>();
        boolean validInput;

        out.println("Inserisci i seguenti parametri per iniziare a giocare!\n");
        do{
            out.println("Server address: ");
            String address = "127.0.0.1";
            //address = readRow();
            if(Validator.validateIpAddress(address)) {
                server.put("address", address);
                validInput = true;
            }
            else {
                out.println("Indirizzo vuoto o non valido! Riprova!");
                validInput = false;
            }
        }while(!validInput);

        do{
            out.println("Port address: ");
            String port = "1511";
            //port = readRow();
            if(Validator.validatePort(port)) {
                server.put("port", port);
                validInput = true;
            }
            else {
                out.println("Porta vuota o non valida! Riprova!");
                validInput = false;
            }
        }while(!validInput);
        notifyObserver(obs -> obs.onUpdateServerDetails(server));
    }

    /**
     * Read the input
     * @return the input
     */
    public String readRow() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            out.println("Invalid Input! Retry...");
            readRow();
        }
        return null;
    }

    /**
     * Ask the nickname to the player
     */
    @Override
    public void askPlayerNickname() {
        out.println("Inserisci il tuo nickname: ");
        String nickname = readRow();
        this.nickname = nickname;

        notifyObserver(obs -> obs.onUpdateNickname(nickname));
    }

    /**
     * Ask how many players want to play the game
     */
    @Override
    public void askPlayersNumber() {
        int playersNumber;
        do{
            try{
                out.println("Inserire numero di giocatori: ");
                playersNumber = Integer.parseInt(readRow());
            }catch(NumberFormatException e){
                out.println("Inserisci un numero!");
                playersNumber = Integer.parseInt(readRow());
            }
            if(playersNumber>=4 || playersNumber<=1) out.println("Errore! Scegli tra 2 e 3");
        }while(playersNumber>=4 || playersNumber<=1);

        int finalPlayersNumber = playersNumber;
        notifyObserver(obs -> obs.onUpdatePlayersNumber(finalPlayersNumber));
    }

    /**
     * Ask the gameMode
     */
    @Override
    public void askGameMode() {
        String gamemode;
        do{
            out.println("Scegli la modalità di gioco (Normale o Esperta): ");
            gamemode = readRow();

            if(!Objects.equals(gamemode, "Normale") && !Objects.equals(gamemode, "Esperta"))
                out.println("Errore! Scegliere tra 'Normale' ed 'Esperta'");

        }while(!Objects.equals(gamemode, "Normale") && !Objects.equals(gamemode, "Esperta"));

        String finalGamemode = gamemode;
        expertMode = finalGamemode.equals("Esperta");

        notifyObserver(obs -> obs.onUpdateGameMode(finalGamemode));
    }

    /**
     * Ask chose an assistant card
     * @param assistantCards assistantCards in hand
     * @param playedAssistantCards cards already played in this turn by the other players
     */
    @Override
    public void askAssistantCard(List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards) {
        int choose;
        do {
            out.println("Scegli tra le seguenti Carte Assistente:");
            int i = 0;
            for (AssistantCard assistantCard : assistantCards){
                out.println("Carta " + i + " | Valore: " + assistantCard.getValue() + " Numero Passi: " + assistantCard.getMovement());
                i++;
            }

            out.println("Carte gia scelte dagli altri giocatori: ");
            for(AssistantCard playedCard : playedAssistantCards){
                out.println("Carta | Valore: " + playedCard.getValue() + " Numero Passi: " + playedCard.getMovement());
            }

            out.println("Inserisci un numero tra 0 e " + (assistantCards.size() - 1) + ":");
            try{
                choose = Integer.parseInt(readRow());
            }catch (NumberFormatException e) {
                out.println("Inserisci un numero!");
                choose = Integer.parseInt(readRow());
            }
            if(choose > assistantCards.size()-1 || choose < 0)
                out.println("Numero inserito non valido. Riprovare.");
        } while(choose > assistantCards.size()-1 || choose < 0);

        int finalChoose = choose;
        playedAssistantCards.add(assistantCards.get(finalChoose));
        notifyObserver(obs -> obs.onUpdatePlayAssistantCard(List.of(assistantCards.get(finalChoose)), playedAssistantCards));
    }



    @Override
    public void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber) {
        int choose;
        String dest;
        boolean toIsland = false;
        int islandDest = -1;
        boolean error;
        StudentDisc studentToReturn = null;

        // SCELTA STUDENTE
        out.println("Scegli uno studente da muovere");
        do {
            error = false;
            int i = 0;

            for (StudentDisc student : studentDiscs) {
                if(student != null)
                    out.println(i + " " + printStudent(student));
                else
                    out.println(i);
                i++;
            }
            try{
                choose = Integer.parseInt(readRow());
            }catch (NumberFormatException e) {
                out.println("Inserisci un numero!");
                choose = Integer.parseInt(readRow());
            }

            if (choose >= studentDiscs.size() || choose < 0) {
                out.println("Numero inserito non valido. Riprovare");
                error = true;
            }else {
                studentToReturn = studentDiscs.get(choose);
                if (studentToReturn == null) {
                    out.println("Nessuno studente in quella posizione! Riprova!");
                    error = true;
                }
            }
        } while(error);

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

                try{
                    islandDest = Integer.parseInt(readRow());
                }catch (NumberFormatException e) {
                    out.println("Inserisci un numero!");
                    islandDest = Integer.parseInt(readRow());
                }

                if(islandDest < 1 || islandDest > 12){
                    out.println("Numero inserito non valido. Riprovare");
                    error = true;
                }
            }
        }while (error);

        StudentDisc finalStudentToReturn = studentToReturn;
        int finalIslandDest = islandDest;
        if(toIsland) {
            notifyObserver(obs -> obs.onUpdateMoveStudent(finalStudentToReturn, 1, finalIslandDest));
        } else notifyObserver(obs -> obs.onUpdateMoveStudent(finalStudentToReturn, 0, finalIslandDest));
    }

    private boolean checkDest(String dest) {
        return dest.equals("isola") || dest.equals("sala") || dest.equals("Isola") || dest.equals("Sala");
    }

    /**
     * Ask how many steps move motherNature forward
     * @param maxSteps max step the player can do
     */
    @Override
    public void askToMoveMotherNature(int maxSteps) {
        int steps;
        boolean error;

        do {
            error = false;
            if(activeCardID == 212) maxSteps = maxSteps + 2;
            out.println("Di quante isole vuoi muovere Madre Natura? Al massimo puoi fare " + maxSteps + " passi.");

            try{
                steps = Integer.parseInt(readRow());
            }catch (NumberFormatException e) {
                out.println("Inserisci un numero!");
                steps = Integer.parseInt(readRow());
            }

            if(steps <= 0 || steps > maxSteps){
                out.println("Impossibile muoversi di " + steps + " passi. Riprova!");
                error = true;
            }
        } while(error);

        int finalSteps = steps;
        notifyObserver(obs -> obs.onUpdateMotherNaturePosition(finalSteps));
        activeCardID = -1;
    }

    /**
     * Ask to choose a cloud to pick the students
     * @param cloudList list of available clouds
     */
    @Override
    public void askToChooseACloud(ArrayList<Cloud> cloudList) {
        int choose;
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

            try{
                choose = Integer.parseInt(readRow());
            }catch (NumberFormatException e) {
                out.println("Inserisci un numero!");
                choose = Integer.parseInt(readRow());
            }

            if(choose > cloudList.size() - 1 || choose < 0 || cloudList.get(choose).getCloudStudents().isEmpty())
                out.println("Numero inserito non valido. Riprovare.");

        }while(choose > cloudList.size()-1 || choose < 0 || cloudList.get(choose).getCloudStudents().isEmpty());

        int finalChoose = choose;
        ArrayList<Cloud> cloud = new ArrayList<>();
        cloud.add(cloudList.get(finalChoose));

        notifyObserver(obs -> obs.onUpdatePickCloud(cloud));
    }

    /**
     * Method to ask if the player want to use a character card
     * @param characterCards list of available character cards
     */
    @Override
    public void askCharacterCard(List<CharacterCard> characterCards) {
        int choose;
        char answer;
        boolean triedToUseCharacter = false;

        do {
            if(!triedToUseCharacter) {
                out.println();
                out.println("E' il tuo turno...");
                out.println();

                int i = 0;
                for (CharacterCard characterCard : characterCards){
                    out.println("Carta " + i + " - Costo " + characterCard.getCost());
                    printEffect(characterCard.getID());
                    i++;
                }
                out.println();
                out.println("Vuoi usare una carta personaggio? Y/N");
            }else
                out.println("Vuoi usare un'altra carta personaggio? Y/N");

            answer = readRow().charAt(0);
            if(answer != 'Y' && answer != 'y'){
                notifyObserver(obs -> obs.onUpdateUseEffect(false));
                return;
            }

            triedToUseCharacter = true;
            out.println("Scegli una Carta Personaggio:");
            out.println("Inserisci un numero tra 0 e " + (characterCards.size() - 1) + ":");

            try{
                choose = Integer.parseInt(readRow());
            }catch (NumberFormatException e) {
                out.println("Inserisci un numero!");
                choose = Integer.parseInt(readRow());
            }

            if(choose > characterCards.size() - 1 || choose < 0)
                out.println("Numero inserito non valido. Riprovare.");
            if(characterCards.get(choose).getCost() > coin)
                out.println("Non hai abbastanza monete per usare questa carta personaggio");
        } while(choose > characterCards.size() - 1 || characterCards.get(choose).getCost() > coin);

        applyEffect(characterCards.get(choose));
    }

    /**
     * Ask the card paramenter to the player and then send a Card### message to the server
     * @param characterCard chosen characterCard
     */
    private void applyEffect(CharacterCard characterCard) {
        boolean error;

        switch (characterCard.getID()){
            case 209 -> {
                Card_209 card209 = (Card_209) characterCard;
                int studentPos;
                int islandNum;

                do {
                    error = false;
                    out.println("Scegli 1 studente:");
                    int i = 0;
                    for (StudentDisc student : card209.getStudents()) {
                        out.println(i + " " + printStudent(student));
                        i++;
                    }

                    try{
                        studentPos = Integer.parseInt(readRow());
                    }catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        studentPos = Integer.parseInt(readRow());
                    }

                    if (studentPos > i - 1 || studentPos < 0) {
                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }
                } while (error);

                do {
                    error = false;
                    out.println("Scegli 1 isola:");

                    try{
                        islandNum = Integer.parseInt(readRow());
                    }catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        islandNum = Integer.parseInt(readRow());
                    }

                    if (islandNum > 12 || islandNum < 1) {

                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }
                } while (error);

                int finalStudentPos = studentPos;
                int finalIslandNum = islandNum;
                out.println("\nEffetto abilitato!\n");
                notifyObserver(obs -> obs.onUpdateUse209(finalStudentPos, finalIslandNum));

            }
            case 210 -> {
                out.println("\nEffetto abilitato!\n");
                notifyObserver(ViewObserver::onUpdateUse210);
            }
            case 211 -> {
                int islandNum;

                do {
                    error = false;
                    out.println("Scegli 1 isola:");

                    try{
                        islandNum = Integer.parseInt(readRow());
                    }catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        islandNum = Integer.parseInt(readRow());
                    }

                    if(islandNum > 12 || islandNum < 1

                    ) {
                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }
                } while(error);

                int finalIslandNum = islandNum;
                out.println("\nEffetto abilitato!\n");
                notifyObserver(obs -> obs.onUpdateUse211(finalIslandNum));
            }
            case 212 -> {
                activeCardID = 212;
                out.println("\nEffetto abilitato!\n");
                notifyObserver(ViewObserver::onUpdateUse212);
            }
            case 213 -> {
                int islandNum;

                do {
                    error = false;
                    out.println("Scegli 1 isola:");

                    try{
                        islandNum = Integer.parseInt(readRow());
                    }catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        islandNum = Integer.parseInt(readRow());
                    }

                    if (islandNum > 12 || islandNum < 1) {
                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }
                } while (error);

                int finalIslandNum = islandNum;
                out.println("\nEffetto abilitato!\n");
                notifyObserver(obs -> obs.onUpdateUse213(finalIslandNum));

            }
            case 214 -> {
                out.println("\nEffetto abilitato!\n");
                notifyObserver(ViewObserver::onUpdateUse214);
            }
            case 215 -> {
                int count = 0;
                Card_215 card_215 = (Card_215) characterCard;
                ArrayList<Integer> entranceStud = new ArrayList<>();
                ArrayList<Integer> cardStudents = new ArrayList<>();

                do {
                    error = false;
                    out.println("Scegli 1 studente da cambiare dalla tua entrata: (inserisci la posizione partendo da 0)");

                    int studentPos;
                    try {
                        studentPos = Integer.parseInt(readRow());
                    } catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        studentPos = Integer.parseInt(readRow());
                    }

                    if (studentPos > 9 || studentPos < 0 || entranceStud.contains(studentPos)) {
                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }

                    if(!error){
                        entranceStud.add(studentPos);
                        count++;
                        out.println("Vuoi scambiarne ancora? (Y/N)");
                        char answer = readRow().charAt(0);
                        if(answer != 'Y' && answer != 'y'){
                            break;
                        }
                    }
                } while (error || count < 3);

                for(int i = 0; i < count; i++) {
                    do {
                        error = false;
                        out.println("Scegli quali studenti prendere dalla carta:");

                        int j = 0;
                        for (StudentDisc student : card_215.getStudents()) {
                            out.println(j + " " + printStudent(student));
                            j++;
                        }

                        int studentPos;
                        try {
                            studentPos = Integer.parseInt(readRow());
                        } catch (NumberFormatException e) {
                            out.println("Inserisci un numero!");
                            studentPos = Integer.parseInt(readRow());
                        }

                        if(studentPos > card_215.getStudents().size() - 1 || studentPos < 0) {
                            out.println("Numero inserito non valido. Riprovare.");
                            error = true;
                        }

                        if(!error) {
                            cardStudents.add(studentPos);
                            card_215.getStudents().remove(studentPos);
                        }
                    } while (error);
                }

                notifyObserver(obs -> obs.onUpdateUse215(entranceStud, cardStudents));
            }
            case 216 -> {
                out.println("Effetto abilitato!");
                notifyObserver(ViewObserver::onUpdateUse216);
            }
            case 217 -> {
                String color;

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

                out.println("Effetto abilitato!");
                switch (color){
                    case "rosso" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.RED));
                    case "giallo" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.YELLOW));
                    case "verde" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.GREEN));
                    case "blu" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.BLUE));
                    case "rosa" -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.PINK));
                }

            }
            case 218 ->{
                int count = 0;
                String color;
                List<Integer> entranceStud = new ArrayList<>();
                List<PawnColors> diningStud = new ArrayList<>();

                do {
                    error = false;
                    out.println("Scegli 1 studente da cambiare dalla tua entrata: (inserisci la posizione)");

                    int studentPos;
                    try {
                        studentPos = Integer.parseInt(readRow());
                    } catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        studentPos = Integer.parseInt(readRow());
                    }

                    if (studentPos > 9 || studentPos < 0 || entranceStud.contains(studentPos)) {
                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }

                    if(!error){
                        entranceStud.add(studentPos);
                        count++;
                        out.println("Vuoi scambiarne ancora? (Y/N)");
                        char answer = readRow().charAt(0);
                        if(answer != 'Y' && answer != 'y'){
                            break;
                        }
                    }
                } while (error || count < 2);

                for(int i = 0; i < count; i++) {
                    do {
                        error = false;
                        out.println("Scegli il colore dello studente della sala con cui scambiarlo:");
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
                        switch (color){
                            case "rosso" -> diningStud.add(PawnColors.RED);
                            case "giallo" -> diningStud.add(PawnColors.YELLOW);
                            case "verde" -> diningStud.add(PawnColors.GREEN);
                            case "blu" -> diningStud.add(PawnColors.BLUE);
                            case "rosa" -> diningStud.add(PawnColors.PINK);
                        }
                    } while (error);
                }

                notifyObserver(obs -> obs.onUpdateUse218(entranceStud, diningStud));
            }
            case 219 -> {
                Card_219 card219 = (Card_219) characterCard;
                int studentPos;

                do {
                    error = false;
                    out.println("Scegli 1 studente:");
                    int i = 0;
                    for (StudentDisc student : card219.getStudents()){
                        out.println(i + " " + printStudent(student));
                        i++;
                    }
                    try{
                        studentPos = Integer.parseInt(readRow());
                    }catch (NumberFormatException e) {
                        out.println("Inserisci un numero!");
                        studentPos = Integer.parseInt(readRow());
                    }
                    if(studentPos > i - 1 || studentPos < 0) {
                        out.println("Numero inserito non valido. Riprovare.");
                        error = true;
                    }
                } while(error);

                int finalStudent = studentPos;
                out.println("Effetto abilitato!");
                notifyObserver(obs -> obs.onUpdateUse219(finalStudent));

            }
            case 220 ->{
                String color;

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

                out.println("Effetto abilitato!");
                switch (color){
                    case "rosso" -> notifyObserver(obs -> obs.onUpdateUse220(PawnColors.RED));
                    case "giallo" -> notifyObserver(obs -> obs.onUpdateUse220(PawnColors.YELLOW));
                    case "verde" -> notifyObserver(obs -> obs.onUpdateUse220(PawnColors.GREEN));
                    case "blu" -> notifyObserver(obs -> obs.onUpdateUse220(PawnColors.BLUE));
                    case "rosa" -> notifyObserver(obs -> obs.onUpdateUse220(PawnColors.PINK));
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + characterCard.getID());
        }
    }

    /**
     * Print the effect of a card
     * @param ID id of the card to print the effect
     */
    private void printEffect(int ID) {
        switch(ID){
            case 209 -> out.println(ANSI_GREEN + "Prendi 1 studente dalla carta e piazzalo su un isola a tua scelta." + ANSI_RESET ); //tested
            case 210 -> out.println(ANSI_GREEN + "Durante questo turno prendi il controllo dei professori anche se nella tua sala hai lo stesso numero di studenti del giocatore che li controlla in quel momento." + ANSI_RESET );
            case 211 -> out.println(ANSI_GREEN + "Scegli un isola e calcola la maggioranza come se madre natura avesse terminato il suo percorso lì. \nIn questo turno madre natura si muoverà come di consueto e nell'isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata" + ANSI_RESET );
            case 212 -> out.println(ANSI_GREEN + "Puoi muovere madre natura di 2 isole addizionali rispetto a quanto indicato sulla carta assistente." + ANSI_RESET );
            case 213 -> out.println(ANSI_GREEN + "Piazza una tessera divieto su un isola a tua scelta, la prima volta che madre natura termina il suo movimento lì verrà rimossa e non verrà calcolata l'influenza ne piazzate torri. " + ANSI_RESET );
            case 214 -> out.println(ANSI_GREEN + "Durante il conteggio dell'influenza su un isola, le torri presenti non vengono calcolate." + ANSI_RESET );
            case 215 -> out.println(ANSI_GREEN + "Puoi prendere fino a 3 studenti da questa carta e scambiarli con altrettant Studenti presenti nel tuo ingresso." + ANSI_RESET);
            case 216 -> out.println(ANSI_GREEN + "In questo turno, durante il calcolo dell'influenza hai 2 punti addizionali." + ANSI_RESET );
            case 217 -> out.println(ANSI_GREEN + "Scegli un colore di uno studente, in questo turno durante il calcolo dell'influenza quel colore non fornisce influenza." + ANSI_RESET );
            case 218 -> out.println(ANSI_GREEN + "puoi scambiare fra loro fino a 2 Studenti presenti nella Sala e nel tuo ingresso." + ANSI_RESET);
            case 219 -> out.println(ANSI_GREEN + "Prendi 1 studente da questa carta e piazzalo nella tua sala." + ANSI_RESET );
            case 220 -> out.println(ANSI_GREEN + "Scegli un colore di uno Studente; ogni giocatore incluso te deve rimettere nel sacchetto 3 studenti di quel colore presenti nella sua sala. chi ne avesse meno rimette tutti quelli che ha." + ANSI_RESET);
        }
    }


    /**
     * Method use to show the players in the lobby
     * @param playersNickname
     * @param numPlayers
     */
    @Override
    public void showLobby(List<String> playersNickname, int numPlayers) {
        out.println("Giocatori presenti nella lobby:");
        for(String nickname : playersNickname) {
            out.println(nickname);
        }
        out.println("Stato della lobby: " + playersNickname.size() + " / " + numPlayers);
    }

    /**
     * Method used to notify all the players that someone has disconnected
     * @param nicknameDisconnected nick of the player that has disconnected
     */
    @Override
    public void showDisconnectedPlayerMessage(String nicknameDisconnected) {
        out.println("\n" + ANSI_RED + nicknameDisconnected + " si è disconnesso dal gioco" + ANSI_RESET);
    }

    /**
     * Method used to notify all the players that someone has reconnected
     * @param nicknameReconnecting nick of the player that has reconnected
     */
    @Override
    public void showReconnectedMessage(String nicknameReconnecting) {
        out.flush();
        out.println(ANSI_GREEN + nicknameReconnecting + " si è riconnesso al gioco!" + ANSI_RESET);
        out.println();
    }

    /**
     * Method used to show an error message
     * @param error text to show
     */
    @Override
    public void showErrorMessage(String error) {
        out.println("\nERRORE: " + error);
    }

    /**
     * Method used to print the state of the game
     * It shows alle the island and all the scoreboard
     *
     * @param gameSerialized reduced version of the game
     */
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

            if((gameSerialized.getMotherNaturePos() == island.getId() && !island.isGhost()) || (island.getReferencedIslands() != null && island.getReferencedIslands().contains(gameSerialized.getMotherNaturePos())))
                out.println(ANSI_WHITE + "MOTHER NATURE" + ANSI_RESET);

            if(island.getTowerNumber() != 0){
                for(int i=0; i < island.getTowerNumber(); i++)
                    out.print(printTower(island.getTowerColor()));
                out.println();
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
                coin = scoreboard.getCoins();
            }
            else {
                out.println("SCOREBOARD DI " + scoreboard.getNickname() + ":");
            }

            expertMode = gameSerialized.getExpertMode();
            showScoreboard(scoreboard);
        }
    }

    /**
     * Method that print a scoreboard
     * @param currentPlayerScoreboard scoreboard to print
     */
    public void showScoreboard(SerializableScoreboard currentPlayerScoreboard){
        if(expertMode){
            out.println("Coin: " + currentPlayerScoreboard.getCoins());
        }

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

    /**
     * Show which island are merged
     * @param unifiedIsland two id of the islands
     */
    @Override
    public void showMergeIslandMessage(List<Integer> unifiedIsland){
        out.println("Isole unite:");
        for(Integer i : unifiedIsland){
            out.print( i + ", ");
        }
    }

    /**
     * Method used to show the info of the game
     * @param playersNicknames nicknames of the players in game
     * @param unifiedIslandsNumber number of unified islands
     * @param remainingBagStudents bag students remaining
     * @param activePlayer player in turn
     * @param characterCards avaiable character cards
     */
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

    /**
     * Method used to show the info of the game
     * @param playersNicknames nicknames of the players in game
     * @param length
     * @param size
     * @param activePlayer player in turn
     */
    @Override
    public void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer) {
        out.println("Informazioni chiave:");
        out.println("Giocatori in partita: ");
        for(String nickname : playersNicknames) {
            out.println(nickname);
        }
        out.println("Player che sta giocando: " + activePlayer);
    }

    /**
     * Print a generic message sent by the server
     * @param genericMessage message to print
     */
    @Override
    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    /**
     * Show the result of the loginRequest
     * @param nickname nickname
     * @param playerNicknameAccepted bool true if accepted
     * @param connectionSuccessful bool true if connected
     */
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

    /**
     * Print a victory message to notify the end of the game
     * @param winner nick of the player that has won
     */
    @Override
    public void showVictoryMessage(String winner) {
        out.println("\n Il gioco è terminato. Il VINCITORE è " + winner + "! \n");
        System.exit(1);
    }

    /**
     * Print a student
     * @param stud student to print
     */
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

    /**
     * Print a tower
     * @param color color of the tower
     * @return
     */
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
