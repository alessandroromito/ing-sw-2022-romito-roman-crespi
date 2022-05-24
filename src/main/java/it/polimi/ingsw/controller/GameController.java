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

    private int chosenPlayerNumber = 0;
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
        //if(chosenExpertMode)
        //    assert game instanceof ExpertGame;

        turnController = new TurnController(this, virtualViewMap);
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
        virtualView.askToMoveMotherNature(player.getCurrentCard().getMovement() );
    }

    public void askToChooseACloud() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askToChooseACloud(game.getMap().getClouds());
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
                //game.addObserver(virtualView);
                playersNicknames.add(nickname);

                virtualView.showLoginResult(true, true);
                virtualView.askPlayersNumber();
                virtualView.askGameMode();

            } catch(GameAlreadyStartedException e) {
                throw new RuntimeException(e);
            }
        } else if (virtualViewMap.size() < chosenPlayerNumber){
            virtualViewMap.put(nickname, virtualView);
            //game.addObserver(virtualView);
            playersNicknames.add(nickname);

            if(chosenPlayerNumber == playersNicknames.size()){

                //check if there is a saved game

                startGame();
            }
        } else {
            virtualView.showLoginResult(true, false);
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

    public void setChosenExpertMode(GameModeMessage message) {
        this.chosenExpertMode = message.getExpertMode();
        showGenericMessageToAll("GameMode set to: " + (chosenExpertMode ? "ExpertMode" : "NormalMode"));
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
            try {
                if (inputController.moveCheck(message)) {
                    game.moveMotherNature(steps);

                    // Go to the next action phase
                    turnController.nextActionPhase();
                }
            } catch (NullCurrentCardException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void moveStudent(MoveStudentMessage message) {
        if((turnController.getPhaseState() == PhaseState.ACTION_PHASE) && (turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT1 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT2 || turnController.getActionPhaseState() == ActionPhaseState.MOVE_STUDENT3)) {
            StudentDisc student = message.getStudentDiscs().get(0);
            Player player = game.getPlayerByNickname(message.getNickname());

            switch (message.getPosition()) {
                case 0 -> player.getScoreboard().addStudentOnEntrance(student);
                case 1 -> game.getMap().getIsland(message.getIslandNumber()).addStudent(student.getColor());
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
            Player player = game.getPlayerByNickname(message.getNickname());

            game.pickAndPlaceFromCloud(chosenCloud.getCloudID());
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



    public void refillClouds(){
        game.refillClouds();
    }

    public void setAssistantCard(AssistantCardMessage assistantCardMessage) {
        if(turnController.getPhaseState() == PhaseState.PLANNING_PHASE){
            game.setAssistantCard(assistantCardMessage.getNickname(), assistantCardMessage.getAssistantCards().get(0).getID());

            //Verifica se tutti l'hanno settata
            for(Player p : game.getPlayers()){
                if(p.getCurrentCard() == null)
                    return;
            }
            turnController.nextPhase();
        }

        else showMessage(assistantCardMessage.getNickname(), "You can't set the assistant card in this phase!");
    }

    public void sendInfo(GameInfoMessage gameInfoMessage) {
        VirtualView virtualView = virtualViewMap.get(gameInfoMessage.getNickname());
        virtualView.showGameInfo(game.getPlayersNicknames(), game.getMap().getGhostIslandNumber(), game.getBag().getBagStudents().size(), turnController.getActivePlayer());
    }

    public void sendInfo(ExpertGameInfoMessage expertGameInfoMessage) {
        VirtualView virtualView = virtualViewMap.get(expertGameInfoMessage.getNickname());
        ExpertGame tempGame = (ExpertGame) game ;
    //    virtualView.showGameInfo(game.getPlayersNicknames(), game.getMap().getGhostIslandNumber(), game.getBag().getBagStudents().size(), turnController.getActivePlayer(), tempGame.);
    }

    public void applyEffect(UseEffectMessage useEffectMessage) {
        if(turnController.getPhaseState() == PhaseState.ACTION_PHASE){

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
            case ERROR -> {
                ErrorMessage errMsg = (ErrorMessage) message;
                System.out.println(((ErrorMessage) message).getError());
            }
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
        return game;
    }

    public int getPlayersNumber(){
        return playersNicknames.size();
    }

    public List<String> getPlayersNicknames() {
        return playersNicknames;
    }
}
