package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.GameAlreadyStartedException;
import it.polimi.ingsw.server.extra.ANSICostants;
import it.polimi.ingsw.server.extra.DataSaving;
import it.polimi.ingsw.server.model.ExpertGame;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * This class represent the controller of the game.
 * Show messages, check win conditions, add players to the game.
 */
public class GameController implements Observer, Serializable {
    @Serial
    private static final long serialVersionUID = -5123455777913761268L;

    public static final String SAVING = "dataSaving.rcr";

    private List<String> playersNicknames = new ArrayList<>();
    private transient Map<String, VirtualView> virtualViewMap;

    private ArrayList<String> reconnectingPlayersList;

    private int chosenPlayerNumber = 3;
    private boolean chosenExpertMode = false;

    private GameState gameState;

    private TurnController turnController;
    private InputController inputController;

    private Game game;

    private boolean resume = false;
    private boolean inPause;

    /**
     * Default constructor.
     */
    public GameController(){
        init();
    }

    /**
     * Initialize the gameController
     */
    public void init(){
        this.inputController = new InputController(this, virtualViewMap);

        this.virtualViewMap = new HashMap<>();
        this.playersNicknames = new ArrayList<>();
        this.reconnectingPlayersList = new ArrayList<>();

        chosenExpertMode = false;
        chosenPlayerNumber = 3;

        inPause = false;

        setGameState(GameState.GAME_ROOM);
    }

    /**
     * Start a new game, initialize the TurnController, add the observers
     * Called when all the players are connected
     */
    public void startGame() {
        setGameState(GameState.IN_GAME);
        this.game = chosenExpertMode ? new ExpertGame(playersNicknames) : new Game(playersNicknames);
        game.setExpertMode(chosenExpertMode);

        // Adding Observers
        for(Player player : game.getPlayers())
            player.addObserver(this);
        for(VirtualView vv : virtualViewMap.values())
            game.addObserver(vv);
        game.getMap().addObserver(this);

        turnController = new TurnController(this, virtualViewMap);
        game.setTurnController(turnController);

        inputController.setGame(game);
        inputController.setVirtualViewMap(virtualViewMap);

        showGenericMessageToAll("GAME STARTED!");
        turnController.newTurn();
    }

    /**
     * Method to ask an assistant card to the active player
     */
    public void askAssistantCard() {
        Player player = game.getPlayerByNickname(turnController.getActivePlayer());
        List<AssistantCard> assistantCardList = new ArrayList<>(player.getHand());
        List<AssistantCard> playedAssistantCards = new ArrayList<>(game.getPlayedAssistantCards());
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askAssistantCard(assistantCardList, playedAssistantCards);
    }
    /**
     * Method to ask to move a student to the active player
     */
    public void askToMoveStudent() {
        Player player = game.getPlayerByNickname(turnController.getActivePlayer());
        List<StudentDisc> studentDiscList = player.getScoreboard().getEntrance();

        VirtualView virtualView = virtualViewMap.get(player.getNickname());
        virtualView.askToMoveAStudent(studentDiscList, 0, 0 );
    }

    /**
     * Method to ask to move motherNature to the active player
     */
    public void askToMoveMotherNature() {
        Player player = game.getPlayerByNickname(turnController.getActivePlayer());

        VirtualView virtualView = virtualViewMap.get(player.getNickname());
        if(game.isExpertMode() && game.getActiveCardID() == 212)
            virtualView.askToMoveMotherNature(player.getCurrentCard().getMovement() + 2);
        else virtualView.askToMoveMotherNature(player.getCurrentCard().getMovement());
    }

    /**
     * Method to ask to choose a cloud to the active player
     */
    public void askToChooseACloud() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askToChooseACloud(game.getMap().getClouds());
    }

    /**
     * Method to ask to choose a character card to the active player
     */
    public void askCharacterCard() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askCharacterCard(game.getCharacterCards());
    }

    /**
     * Set the State of the current Game.
     *
     * @param gameState State of the current Game.
     */
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Check if there is a winner.
     */
    public void winnerChecker(){
        List<Player> possibleWinners = new ArrayList<>();
        int minTower = 8, maxProfessor = 0;

        for(Player p : game.getPlayers())
            if(p.getScoreboard().getNumTowers() < minTower)
                minTower = p.getScoreboard().getNumTowers();

        for(Player p : game.getPlayers())
            if(p.getScoreboard().getNumTowers() == minTower)
                possibleWinners.add(p);

        if(possibleWinners.size() == 1)
            win(possibleWinners.get(0));
        else{
            for(Player p : possibleWinners)
                if(p.getScoreboard().getNumProf() > maxProfessor)
                    maxProfessor = p.getScoreboard().getNumProf();

            for(Player p : possibleWinners)
                if(p.getScoreboard().getNumProf() != maxProfessor)
                    possibleWinners.remove(p);

            win(possibleWinners.get(0));
        }
    }

    /**
     * Called when a player has won the match, it notifies all the player who has won
     */
    public void win(Player player){
        for(Map.Entry<String, VirtualView> entry : virtualViewMap.entrySet()){
            if(player.getNickname().equals(entry.getKey()))
                entry.getValue().showVictoryMessage(player.getNickname());
            else entry.getValue().showGenericMessage("\n Il gioco è terminato! Il VINCITORE è " + player.getNickname() +"! \n");
        }

        endGame();
    }


    /**
     * Re-initialise the controller and prepare for a new game.
     */
    public void endGame() {
        game = null;
        playersNicknames = new ArrayList<>();
        reconnectingPlayersList = new ArrayList<>();

        // Delete storage data
        try {
            DataSaving dataSaving = new DataSaving();
            dataSaving.remove();
        } catch (IOException ignored) {
        }

        init();
        System.out.println("Game Finished!");
        System.out.println("\n \n \n \n \n \n");
        System.out.println("Server ready for a new game!");
    }

    /**
     * Method that handle the login of a player.
     * Check if it's the first or if is a reconnecting player.
     *
     * @param nickname nickname of the player.
     * @param virtualView virtualView associated.
     */
    public void addPlayer(String nickname, VirtualView virtualView) {
        if(playersNicknames.contains(nickname) && reconnectingPlayersList.contains((nickname))){

            resume = true;
            inPause = false;

            reconnectingPlayersList.remove(nickname);
            game.getPlayerByNickname(nickname).setConnected(true);

            virtualViewMap.put(nickname, virtualView);
            inputController.setVirtualViewMap(virtualViewMap);
            turnController.setVirtualViewMap(virtualViewMap);

            game.getPlayerByNickname(nickname).addObserver(this);
            game.addObserver(virtualView);

            virtualView.showLoginResult(nickname,true, true);

            showReconnectingMessage(nickname);

            turnController.restartTurn();

            return;
        }

        //First player joining
        if (virtualViewMap.isEmpty()) {
            try{
                virtualViewMap.put(nickname, virtualView);
                playersNicknames.add(nickname);

                virtualView.showLoginResult(nickname,true, true);
                virtualView.askGameMode();

            } catch(GameAlreadyStartedException e) {
                e.printStackTrace();
            }

        } else if (virtualViewMap.size() < chosenPlayerNumber - reconnectingPlayersList.size()){
            virtualViewMap.put(nickname, virtualView);
            if(!playersNicknames.contains(nickname))
                playersNicknames.add(nickname);

            virtualView.showLoginResult(nickname,true, true);

            if(chosenPlayerNumber == virtualViewMap.size()){

                //check if there is a saved game
                DataSaving storageData = new DataSaving();
                GameController restoredGameController = null;
                try {
                    restoredGameController = storageData.restore();
                } catch (IOException e) {
                    System.out.println("No game data file.");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (restoredGameController != null && new HashSet<>(playersNicknames).containsAll( restoredGameController.getTurnController().getNicknameQueue() ) &&
                        getChosenPlayerNumber() == restoredGameController.getChosenPlayerNumber() &&
                            chosenExpertMode == restoredGameController.chosenExpertMode) {
                    initControllersFromRestoreGameSaved( restoredGameController );
                    updateGraphicInterfaces();
                    turnController.resettingTurnCount();
                    Server.LOGGER.info("Game saved found. Restoring...");
                    turnController.newTurn();
                } else {
                    startGame();
                }
            }
        } else
            virtualView.showLoginResult(nickname,true, false);
    }

    /**
     * Method that initialize a new game from data saved and restored.
     * @param restoredGameController gameController restored from "dataSaving.rcr" thanks to DataSaving class.
     */
    public void initControllersFromRestoreGameSaved(GameController restoredGameController) {
        it.polimi.ingsw.server.model.map.Map restoredMap = restoredGameController.game.getMap();
        Bag restoredBag = restoredGameController.game.getBag();
        ArrayList<Component> restoredComponents = restoredGameController.game.getComponents();
        List<Player> restoredPlayers = restoredGameController.game.getPlayers();

        List<String> stringListOfRestoredPlayers = new ArrayList<>();
        for(Player p : restoredPlayers) stringListOfRestoredPlayers.add(p.getNickname());

        boolean restoredExpertMode = restoredGameController.game.isExpertMode();
        if(restoredExpertMode) {
            ExpertGame restoredExpertGame = (ExpertGame) restoredGameController.getGame();
            int restoredActiveCardID = restoredGameController.game.getActiveCardID();
            CharacterCard restoreActiveCard = restoredExpertGame.getActiveCard();
            ArrayList<CharacterCard> restorePool = restoredExpertGame.getPool();
            this.game = new ExpertGame(stringListOfRestoredPlayers);
            this.game.restoreGame(restoredMap, restoredBag, restoredComponents, restoredPlayers, true, restoredActiveCardID, restoreActiveCard, restorePool);
        }
        else {
            this.game = new Game(stringListOfRestoredPlayers);
            this.game.restoreGame(restoredMap, restoredBag, restoredComponents, restoredPlayers, false, 0, null, null);
        }

        this.turnController = restoredGameController.turnController;
        this.game.setTurnController(restoredGameController.turnController);
        turnController.setGameController(this);
        this.gameState = restoredGameController.gameState;

        // Adding Observers
        for(Player player : game.getPlayers())
            player.addObserver(this);
        for(VirtualView vv : virtualViewMap.values())
            game.addObserver(vv);
        game.getMap().addObserver(this);

        inputController = new InputController(this, this.virtualViewMap);
        turnController.setVirtualViewMap(this.virtualViewMap);

        showGenericMessageToAll(ANSICostants.ANSI_GREEN + "Rigenerazione partita avvenuta con successo!" + ANSICostants.ANSI_RESET);
    }

    /**
     * Update graphic interfaces after a restoring of gameController from a file.
     */
    public void updateGraphicInterfaces() {
        for (VirtualView virtualView : virtualViewMap.values() ) {
            virtualView.showGameScenario(game.getGameSerialized());
        }

        //update delle match info
    }

    /**
     * Shows a message to all player that someone has reconnected.
     * @param nickname nickname of the reconnected player.
     */
    private void showReconnectingMessage(String nickname) {
        for(VirtualView virtualView : virtualViewMap.values()){
            if(virtualView != virtualViewMap.get(nickname))
                virtualView.showReconnectedMessage(nickname);
        }

        long start = System.currentTimeMillis();
        long end = start + 1000;
        System.out.println("Waiting 30 sec for reconnecting...");
        while (System.currentTimeMillis() < end) {
            if(resume) break;
        }

        virtualViewMap.get(nickname).showGameScenario(new GameSerialized(game));

    }

    /**
     * Verifies the nickname through the InputController.
     *
     * @param nickname the nickname to be checked.
     * @return see {@link InputController#checkLoginNickname(String)}
     */
    public boolean checkLoginNickname(String nickname) {
        return inputController.checkLoginNickname(nickname);
    }

    /**
     * Set the number of player chosen by the host
     * @param message
     */
    public void setChosenPlayerNumber(PlayerNumberReply message) {
        if(inputController.playerNumberReplyCheck(message.getPlayerNumber())) {
            chosenPlayerNumber = message.getPlayerNumber();
            if(message.getPlayerNumber() == virtualViewMap.size())
                startGame();
            else
                showGenericMessageToAll("Waiting for other players...");
        }
        else{
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.askPlayersNumber();
        }
    }

    /**
     * Set the game mode selected by the host.
     * @param message
     */
    public void setChosenExpertMode(GameModeReplyMessage message) {
        this.chosenExpertMode = message.getExpertMode();
        showGenericMessageToAll("GameMode set to: " + (chosenExpertMode ? "Esperta" : "Normale"));

        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        virtualView.askPlayersNumber();
    }

    /**
     * Check if the nickname chosen is valid or not.
     * @param nickname nickname chosen.
     * @return true if it's ok, false otherwise.
     */
    public boolean isNicknameTaken(String nickname) {
        if(gameState.equals(GameState.GAME_ROOM))
            return playersNicknames.stream()
                .anyMatch(nickname::equals);

        return false;
    }

    /**
     * Check if the message is sent by the active player, if true he could take actions
     *
     * @param message Message sent by the client
     * @return true if it's ok
     */
    public boolean checkUser(Message message) {
        return message.getNickname().equals(getTurnController().getActivePlayer());
    }

    /**
     * Method used to move Mother Nature forward, it calls the game method
     * @param message
     */
    public void moveMotherNature(MoveMotherNatureMessage message) {
        int steps = message.getSteps();
        if (turnController.getPhaseState() == PhaseState.ACTION_PHASE && turnController.getActionPhaseState() == ActionPhaseState.MOVE_MOTHER_NATURE) {
            if (inputController.moveCheck(message)) {
                game.moveMotherNature(steps);

                turnController.nextActionPhase();
            }
            else {
                VirtualView virtualView = virtualViewMap.get(message.getNickname());
                virtualView.askToMoveMotherNature(message.getSteps());
            }
        }
        else System.out.println("Incorrect ActionPhase to move MotherNature!");
    }

    /**
     * Method used to move a student from the entrance
     * @param message
     */
    public void moveStudent(MoveStudentMessage message) {
        if((turnController.getPhaseState() == PhaseState.ACTION_PHASE) && (turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT1 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT2 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT3 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT4)) {
            StudentDisc student = message.getStudentDiscs().get(0);
            Player player = game.getPlayerByNickname(message.getNickname());

            switch (message.getPosition()) {
                case 0 -> game.moveStudentToDiningRoom(student);
                case 1 -> game.moveStudentToIsland(student, message.getIslandNumber()-1);
                default -> {
                    showMessage(player.getNickname(), "Invalid MoveStudentMessage!");
                    askToMoveStudent();
                }
            }

            turnController.nextActionPhase();
        }
        else showMessage(turnController.getActivePlayer(), "You can't move a student in this phase!");
    }

    /**
     * Method used to choose a cloud and pick his students
     * @param message message received by client
     */
    public void pickCloud(CloudMessage message) {
        if((turnController.getPhaseState() == PhaseState.ACTION_PHASE) && (turnController.getActionPhaseState() == ActionPhaseState.PICK_CLOUD)) {
            Cloud chosenCloud = message.getCloudList().get(0);

            game.pickAndPlaceFromCloud(chosenCloud.getCloudID());

            showMessage(message.getNickname(), "Attesa che gli altri giocatori finiscano il turno...");

            turnController.next();
        }
    }

    /**
     * Show a message to all players
     * @param message message to show
     */
    public void showGenericMessageToAll(String message) {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showGenericMessage(message);
        }
    }

    /**
     * Send a game scenario message to all players
     */
    public void sendGameScenarioMessageToAll() {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showGameScenario(new GameSerialized(game));
        }
    }

    /**
     * Show a message only to the parameter player
     * @param nickname nickname of the player to send a message
     * @param message message to be sent
     */
    public void showMessage(String nickname, String message){
        VirtualView virtualView = virtualViewMap.get(nickname);
        virtualView.showGenericMessage(message);
    }


    /**
     * Show a message to notify the disconnection of a player from the game
     * @param nickname nick of the player disconnected
     */
    public void showDisconnectionMessage(String nickname) {
        for(VirtualView vv : virtualViewMap.values())
            vv.showDisconnectedPlayerMessage(nickname);
    }

    /**
     * Call a game method that refill the clouds with students.
     */
    public void refillClouds(){
        game.refillClouds();
    }

    /**
     * Set the assistant card.
     * @param assistantCardMessage assistantCard chosen.
     */
    public void setAssistantCard(AssistantCardMessage assistantCardMessage) {
        if(turnController.getPhaseState() == PhaseState.PLANNING_PHASE){
            if(inputController.validateCard(assistantCardMessage.getAssistantCards().get(0))) {
                game.setAssistantCard(assistantCardMessage.getNickname(), assistantCardMessage.getAssistantCards().get(0).getID());
                showMessage(assistantCardMessage.getNickname(), "Assistant Card Set!");

                sendGameScenarioMessageToAll();

                turnController.next();
            }
            else{
                showMessage(assistantCardMessage.getNickname(), "Qualcuno ha gia scelto questa carta! Scegline un'altra perfavore");
                askAssistantCard();
            }

        }
        else showMessage(assistantCardMessage.getNickname(), "You can't set the assistant card in this phase!");
    }

    /**
     * Send info method for gamemode normal.
     * @param gameInfoMessage message that contains all the parameters required.
     */
    public void sendInfo(GameInfoMessage gameInfoMessage) {
        VirtualView virtualView = virtualViewMap.get(gameInfoMessage.getNickname());
        virtualView.showGameInfo(game.getPlayersNicknames(), game.getMap().getNumberOfGhostIsland(), game.getBag().getBagStudents().size(), turnController.getActivePlayer());
    }

    /**
     * Send info method for gamemode expert.
     * @param expertGameInfoMessage message that contains all the parameters required.
     */
    public void sendInfo(ExpertGameInfoMessage expertGameInfoMessage) {
        VirtualView virtualView = virtualViewMap.get(expertGameInfoMessage.getNickname());
        virtualView.showGameInfo(game.getPlayersNicknames(), game.getMap().getNumberOfGhostIsland(), game.getBag().getBagStudents().size(), turnController.getActivePlayer(), game.getCharacterCards());
    }

    /**
     * Manage the user choose of not to use the effect of the characterCard.
     */
    public void noApplyEffect() {
        if(turnController.getPhaseState() == PhaseState.ACTION_PHASE && turnController.getActionPhaseState() == ActionPhaseState.USE_EFFECT)
            turnController.nextActionPhase();
    }

    /**
     * Manage the user choose to use the effect of the characterCard.
     * @param useEffectMessage specify the effect to be used.
     */
    public void applyEffect(UseEffectMessage useEffectMessage) {
        if(turnController.getPhaseState() == PhaseState.ACTION_PHASE){
            if(inputController.checkCoin(useEffectMessage)){
                switch(useEffectMessage.getCardID()){
                    case 209 -> {
                        System.out.println("Using 209");
                        Card209Message card209Message = (Card209Message) useEffectMessage;
                        if(game.useCharacter(209))
                            game.use_209(card209Message.getStudentPos(), card209Message.getIslandNumber()-1);
                        else System.out.println("Error while using 209");
                    }
                    case 210 -> {
                        System.out.println("Using 210");
                        if(game.useCharacter(210))
                            game.use_210();
                        else System.out.println("Error while using 210");
                    }
                    case 211 -> {
                        System.out.println("Using 211");
                        Card211Message card211Message = (Card211Message) useEffectMessage;
                        if(game.useCharacter(211))
                            game.use_211(card211Message.getIslandNumber());
                        else System.out.println("Error while using 211");
                    }
                    case 212 -> {
                        System.out.println("Using 212");
                        if(game.useCharacter(212))
                            game.use_212();
                        else System.out.println("Error while using 212");
                    }
                    case 213 -> {
                        System.out.println("Using 213");
                        Card213Message card213Message = (Card213Message) useEffectMessage;
                        if(game.useCharacter(213))
                            game.use_213(card213Message.getIslandNumber());
                        else System.out.println("Error while using 213");

                    }
                    case 214 -> {
                        System.out.println("Using 214");
                        if(game.useCharacter(214))
                            game.use_214();
                        else System.out.println("Error while using 214");
                    }
                    case 215 -> {
                        System.out.println("Using 215");
                        Card215Message card215Message = (Card215Message) useEffectMessage;
                        if(game.useCharacter(215))
                            game.use_215(card215Message.getEntranceStudents(), card215Message.getCardStudents());
                        else System.out.println("Error while using 215");

                    }
                    case 216 -> {
                        System.out.println("Using 216");
                        if(game.useCharacter(216))
                            game.use_216(game.getPlayerByNickname(useEffectMessage.getNickname()));
                        else System.out.println("Error while using 216");

                    }
                    case 217 -> {
                        System.out.println("Using 217");
                        Card217Message card217Message = (Card217Message) useEffectMessage;
                        if(game.useCharacter(217))
                            game.use_217(card217Message.getColor());
                        else System.out.println("Error while using 217");
                    }
                    case 218 -> {
                        System.out.println("Using 218");
                        Card218Message card218Message = (Card218Message) useEffectMessage;
                        if(game.useCharacter(218))
                            game.use_218(card218Message.getEntranceList(), card218Message.getDiningList());
                        else System.out.println("Error while using 218");
                    }
                    case 219 -> {
                        System.out.println("Using 219");
                        Card219Message card219Message = (Card219Message) useEffectMessage;
                        if(game.useCharacter(219))
                            game.use_219(card219Message.getNumber());
                        else System.out.println("Error while using 219");
                    }
                    case 220 -> {
                        System.out.println("Using 220");
                        Card220Message card220Message = (Card220Message) useEffectMessage;
                        if(game.useCharacter(220))
                            game.use_220(card220Message.getColor());
                        else System.out.println("Error while using 220");
                    }
                }
            }
            else{
                showMessage(useEffectMessage.getNickname(), "Non hai abbastanza monete per usare questa carta personaggio!");
                askCharacterCard();
            }
        }
        else{
            showMessage(useEffectMessage.getNickname(), "You are not in the correct phase!");
        }
    }

    /**
     * Receive update from the Game
     * @param message
     */
    @Override
    public void update(Message message) {
        switch (message.getMessageType()) {
            case WIN_CHECK -> winnerChecker();
            case WINNER_DECLARATION -> win(game.getPlayerByNickname(message.getNickname()));
            case ERROR -> System.out.println(((ErrorMessage) message).getError());
            default -> showGenericMessageToAll("Invalid update!");
        }
    }

    /**
     * Method to reset the assistantCard at the start of every turn
     */
    public void refreshAssistantCard() {
        for(Player p : game.getPlayers()){
            p.resetAssistantCard();
        }
    }

    /**
     * Method used to remove the VirtualView of a player from the game and
     * setting him as not connected
     *
     * @param nickname nickname of the player to remove the virtualView
     */
    public void removeVirtualView(String nickname) {
        VirtualView virtualView = virtualViewMap.remove(nickname);

        if(getGameState() == GameState.IN_GAME) {
            game.removeObserver(virtualView);
            game.getPlayerByNickname(nickname).removeObserver(virtualView);

            game.getPlayerByNickname(nickname).setConnected(false);

            turnController.removeVirtualView(nickname);
            inputController.removeVirtualView(nickname);
        }
    }

    /**
     * Return Turn Controller of the Game.
     *
     * @return turnController of the Game.
     */
    public TurnController getTurnController() {
        return turnController;
    }


    /**
     * Return the gameState of the game.
     * @return gameState enum
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Getter method.
     * @return game.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Getter method.
     * @return a list of String containing the nickname of all the players.
     */
    public List<String> getPlayersNicknames() {
        return playersNicknames;
    }

    /**
     * Getter method.
     * @return the virtualViewMap.
     */
    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

    /**
     * Getter method.
     * @return the player number chosen by the user at the start of the game (2 or 3).
     */
    public int getChosenPlayerNumber() {
        return chosenPlayerNumber;
    }

    /**
     * @return true if the game could be resumed, false otherwise
     */
    public boolean resumeGame() {
        return resume;
    }

    /**
     * Set the resumeGame parameter.
     * @param resumeGame boolean value to be assigned to this.resume.
     */
    public void setResumeGame(boolean resumeGame) {
        this.resume = resumeGame;
    }

    /**
     * Set the inPause parameter.
     * @param inPause boolean value to be assigned to this.inPause.
     */
    public void setInPause(boolean inPause) {
        this.inPause = inPause;
    }

    /**
     * Getter method.
     * @return true if game is in pause, false if it is not.
     */
    public boolean isInPause() {
        return inPause;
    }


    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    /**
     * Getter method.
     * @return the reconnectingPlayerList.
     */
    public ArrayList<String> getReconnectingPlayersList() {
        return reconnectingPlayersList;
    }

    public void setGame(ExpertGame expertgame) {
        this.game = expertgame;
    }
}
