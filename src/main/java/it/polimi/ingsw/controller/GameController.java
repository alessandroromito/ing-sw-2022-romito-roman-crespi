package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.enumerations.ActionPhaseState;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.ExpertGame;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController implements Observer {
    public static final String SAVING = "GameController.sav";

    private List<String> playersNicknames = new ArrayList<>();
    private Map<String, VirtualView> virtualViewMap;

    private int chosenPlayerNumber;
    private boolean chosenExpertMode = false;

    private GameState gameState;
    private TurnController turnController;
    private InputController inputController;

    public static final String SAVED_GAME_FILE = "gameController.saving";
    private Game game;

    public GameController(){
        init();
    }

    public void init(){
        this.inputController = new InputController(this, virtualViewMap);
        this.virtualViewMap = new HashMap<>();
        setGameState(GameState.GAME_ROOM);
    }

    private void startGame() {
        setGameState(GameState.IN_GAME);
        this.game = chosenExpertMode ? new ExpertGame(playersNicknames) : new Game(playersNicknames);
        game.setExpertMode(chosenExpertMode);

        for(VirtualView vv : virtualViewMap.values())
            game.addObserver(vv);

        turnController = new TurnController(this, virtualViewMap);
        game.setTurnController(turnController);

        inputController.setGame(game);
        inputController.setVirtualViewMap(virtualViewMap);

        showGenericMessageToAll("GAME STARTED!");
        turnController.newTurn();
    }

    public void askToMoveStudent() {
        Player player = game.getPlayerByNickname(turnController.getActivePlayer());
        List<StudentDisc> studentDiscList = player.getScoreboard().getEntrance();

        VirtualView virtualView = virtualViewMap.get(player.getNickname());
        virtualView.askToMoveAStudent(studentDiscList, 0, 0 );
    }

    public void askToMoveMotherNature() {
        Player player = game.getPlayerByNickname(turnController.getActivePlayer());

        VirtualView virtualView = virtualViewMap.get(player.getNickname());
        virtualView.askToMoveMotherNature(player.getCurrentCard().getMovement());
    }

    public void askToChooseACloud() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askToChooseACloud(game.getMap().getClouds());
    }


    public void askCharacterCard() {
        if(game.isExpertMode()){
            VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
            virtualView.askCharacterCard(game.getCharacterCards());
        }
        else{
            turnController.nextActionPhase();
        }
    }

    /**
     * Set the State of the current Game.
     *
     * @param gameState State of the current Game.
     */
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

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

    public void win(Player player){
        showGenericMessageToAll("### PLAYER:" + player.getNickname() + "WIN!!! ###");
        endGame();
    }


    public void endGame() {
        game = null;

        // Delete storage data

        init();
        System.out.println("Game Finished!");
    }

    public void addPlayer(String nickname, VirtualView virtualView) throws MaxPlayerException, MissingPlayersException, MissingPlayerNicknameException, InvalidActionPhaseStateException, InterruptedException, CloudNotEmptyException {
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
        } else if (virtualViewMap.size() < chosenPlayerNumber){
            virtualViewMap.put(nickname, virtualView);
            playersNicknames.add(nickname);

            if(chosenPlayerNumber == playersNicknames.size()){

                //check if there is a saved game

                startGame();
            }
        } else {
            virtualView.showLoginResult(nickname,true, false);
        }
    }

    /*
            if (game.getNumCurrentPlayers() == game.getChosenPlayersNumber()) { // If all players logged

                // check saved matches.
                StorageData storageData = new StorageData();
                GameController savedGameController = storageData.restore();
                if (savedGameController != null &&
                        game.getPlayersNicknames().containsAll(savedGameController.getTurnController().getNicknameQueue())) {
                    restoreControllers(savedGameController);
                    broadcastRestoreMessages();
                    Server.LOGGER.info("Saved Match restored.");
                    turnController.newTurn();
                } else {
                    startGame();
                }
            }
        } else {
            virtualView.showLoginResult(true, false, Game.SERVER_NICKNAME);
        }
    }
    */

    /**
     * Verifies the nickname through the InputController.
     *
     * @param nickname the nickname to be checked.
     * @return see {@link InputController#checkLoginNickname(String)}
     */
    public boolean checkLoginNickname(String nickname) {
        return inputController.checkLoginNickname(nickname);
    }

    public void setChosenPlayerNumber(PlayerNumberReply message) {
        if(inputController.playerNumberReplyCheck(message.getPlayerNumber())) {
            this.chosenPlayerNumber = message.getPlayerNumber();
            showGenericMessageToAll("Waiting for other players...");
        }
        else{
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.askPlayersNumber();
        }
    }

    public void setChosenExpertMode(GameModeReplyMessage message) {
        this.chosenExpertMode = message.getExpertMode();
        showGenericMessageToAll("GameMode set to: " + (chosenExpertMode ? "Esperta" : "Normale"));

        VirtualView virtualView = virtualViewMap.get(message.getNickname());
        virtualView.askPlayersNumber();
    }

    public boolean isNicknameTaken(String nickname) {
        return playersNicknames.stream()
                .anyMatch(nickname::equals);
    }

    /**
     * Check if the message is sent by the active player, if true he could take actions
     *
     * @param message
     * @return
     */
    public boolean checkUser(Message message) {
        return message.getNickname().equals(getTurnController().getActivePlayer());
    }

    public void moveMotherNature(MoveMotherNatureMessage message) {
        int steps = message.getSteps();
        if (turnController.getPhaseState() == PhaseState.ACTION_PHASE && turnController.getActionPhaseState() == ActionPhaseState.MOVE_MOTHER_NATURE) {
            if (inputController.moveCheck(message)) {
                game.moveMotherNature(steps);

                turnController.nextActionPhase();
            }
        }
        else System.out.println("Incorrect ActionPhase to move MotherNature!");
    }

    public void moveStudent(MoveStudentMessage message) {
        if((turnController.getPhaseState() == PhaseState.ACTION_PHASE) && (turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT1 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT2 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT3)) {
            StudentDisc student = message.getStudentDiscs().get(0);
            Player player = game.getPlayerByNickname(message.getNickname());

            switch (message.getPosition()) {
                case 0 -> {
                    game.moveStudentToDiningRoom(student);
                }
                case 1 -> {
                    game.moveStudentToIsland(message.getStudentDiscs().get(0), message.getIslandNumber()-1);
                    game.getPlayerByNickname(message.getNickname()).getScoreboard().removeStudent(student);
                }
                default -> showMessage(player.getNickname(), "Invalid MoveStudentMessage!");
            }

            // go to the next action phase move
            turnController.nextActionPhase();
        }
        else showMessage(turnController.getActivePlayer(), "You can't move a student in this phase!");
    }

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

    public void showMessage(String nickname, String message){
        VirtualView virtualView = virtualViewMap.get(nickname);
        virtualView.showGenericMessage(message);
    }

    public void showDisconnectionMessage(String nickname, String message) {
        VirtualView virtualView = virtualViewMap.get(nickname);
        virtualView.showDisconnectedPlayerMessage(nickname, message);
    }

    public void refillClouds(){
        game.refillClouds();
    }

    public void setAssistantCard(AssistantCardMessage assistantCardMessage) {
        if(turnController.getPhaseState() == PhaseState.PLANNING_PHASE){
            game.setAssistantCard(assistantCardMessage.getNickname(), assistantCardMessage.getAssistantCards().get(0).getID());
            showMessage(assistantCardMessage.getNickname(), "Assistant Card Set!");

            turnController.next();
        }
        else showMessage(assistantCardMessage.getNickname(), "You can't set the assistant card in this phase!");
    }

    public void sendInfo(GameInfoMessage gameInfoMessage) {
        VirtualView virtualView = virtualViewMap.get(gameInfoMessage.getNickname());
        virtualView.showGameInfo(game.getPlayersNicknames(), game.getMap().getNumberOfGhostIsland(), game.getBag().getBagStudents().size(), turnController.getActivePlayer());
    }

    public void sendInfo(ExpertGameInfoMessage expertGameInfoMessage) {
        VirtualView virtualView = virtualViewMap.get(expertGameInfoMessage.getNickname());
        virtualView.showGameInfo(game.getPlayersNicknames(), game.getMap().getNumberOfGhostIsland(), game.getBag().getBagStudents().size(), turnController.getActivePlayer(), game.getCharacterCards());
    }

    public void applyEffect(UseEffectMessage useEffectMessage) {
        if(turnController.getPhaseState() == PhaseState.ACTION_PHASE){
            if(inputController.checkCoin(useEffectMessage)){
                switch(useEffectMessage.getCardID()){
                    case 209 -> {
                        Card209Message card209Message = (Card209Message) useEffectMessage;
                        if(game.useCharacter(209))
                            game.use_209(card209Message.getStudentPos(), card209Message.getIslandNumber());
                    }
                    case 210 -> {
                        if(game.useCharacter(210))
                            game.use_210();
                    }
                    case 211 -> {
                        Card211Message card211Message = (Card211Message) useEffectMessage;
                        if(game.useCharacter(211))
                            game.use_211(card211Message.getIslandNumber());
                    }
                    case 212 -> {
                        if(game.useCharacter(212))
                            game.use_212();
                    }
                    case 213 -> {
                        Card213Message card213Message = (Card213Message) useEffectMessage;
                        if(game.useCharacter(213))
                            game.use_213(card213Message.getIslandNumber());
                    }
                    case 214 -> {
                        if(game.useCharacter(214))
                            game.use_214();
                    }
                    case 216 -> {
                        if(game.useCharacter(216))
                            game.use_216(game.getPlayerByNickname(useEffectMessage.getNickname()));
                    }
                    case 217 -> {
                        Card217Message card217Message = (Card217Message) useEffectMessage;
                        if(game.useCharacter(217))
                            game.use_217(card217Message.getColor());
                    }
                    case 219 -> {
                        Card219Message card219Message = (Card219Message) useEffectMessage;
                        if(game.useCharacter(219))
                            game.use_219(game.getPlayerByNickname(card219Message.getNickname()), card219Message.getNumber());
                    }
                }
            }
            else{
                showMessage(useEffectMessage.getNickname(), "You don't have enough coin!");
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
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        switch (message.getMessageType()) {

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
     * Return Turn Controller of the Game.
     *
     * @return turnController of the Game.
     */
    public TurnController getTurnController() {
        return turnController;
    }

    /**
     * Return Input Controller of the Game.
     *
     * @return inputController of the Game.
     */
    public InputController getInputController() {
        return inputController;
    }

    /**
     * Return the gameState of the game
     *
     * @return gameState enum
     */
    public GameState getGameState() {
        return gameState;
    }

    public Game getGame() {
        return this.game;
    }

    public List<String> getPlayersNicknames() {
        return playersNicknames;
    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

}
