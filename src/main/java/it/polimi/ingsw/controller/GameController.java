package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.ExpertGame;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    public static final String SAVING = "GameController.sav";

    private List<String> playersNicknames = new ArrayList<>();
    private Map<String, VirtualView> virtualViewMap;

    private int chosenPlayerNumber = 0;
    private boolean chosenExpertMode = false;

    private Game game;

    private GameState gameState;
    private TurnController turnController;
    private InputController inputController;

    public static final String SAVED_GAME_FILE = "gameController.saving";

    public GameController(){
        init();
    }

    public void init(){
        this.inputController = new InputController(this, virtualViewMap);
        this.virtualViewMap = new HashMap<>();
        setGameState(GameState.GAME_ROOM);
    }

    private void startGame() throws MissingPlayerNicknameException, MissingPlayersException, InterruptedException, InvalidActionPhaseStateException, CloudNotEmptyException {
        setGameState(GameState.IN_GAME);
        this.game = chosenExpertMode ? new ExpertGame(playersNicknames) : new Game(playersNicknames);

        turnController = new TurnController(this, virtualViewMap);
        showGenericMessage("GAME STARTED!");
        turnController.newTurn();

    }

    public void askAllToChooseAssistantCard() {
        for(String nickname : turnController.getNicknameQueue() ) {
            try {
                VirtualView virtualView = virtualViewMap.get(nickname);
                virtualView.askAssistantCard(nickname, game.getPlayerByNickname(nickname).getHand());
            } catch (MissingPlayerNicknameException e) {
                e.printStackTrace();
            }
        }

        try {
            turnController.nextPhase();
        } catch (MissingPlayerNicknameException | InvalidActionPhaseStateException | CloudNotEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    public void askToMoveStudent() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askToMoveAStudent(turnController.getActivePlayer(),  );
    }

    public void askToMoveMotherNature() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askToMoveMotherNature();
    }

    public void askToChooseACloud() {
        VirtualView virtualView = virtualViewMap.get(turnController.getActivePlayer());
        virtualView.askToChoseACloud();

    }

    /**
     * Set the State of the current Game.
     *
     * @param gameState State of the current Game.
     */
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void lastTurn(){
        turnController.setLastTurn();
    }

    public void winnerChecker(){
        List<Player> possibleWinners = new ArrayList<Player>();
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
        showGenericMessage("### PLAYER:" + player.getNickname() + "WIN!!! ###");
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

                virtualView.showLoginResult(true, true, nickname);
                virtualView.askPlayersNumber();
                virtualView.askGameMode();

            } catch(GameAlreadyStartedException e){
                new GameAlreadyStartedException("NOT possible to add players when game is already started!");
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
            virtualView.showLoginResult(true, false, nickname);
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
            showGenericMessage("Waiting for other players...");
        }
        else{
            VirtualView virtualView = virtualViewMap.get(message.getNickname());
            virtualView.askPlayersNumber();
        }
    }

    public void setChosenExpertMode(GameModeMessage message) {
        this.chosenExpertMode = message.getExpertMode();
        showGenericMessage("GameMode set to: " + (chosenExpertMode ? "ExpertMode" : "NormalMode"));
    }

    /**
     * Checks if the game is already started, then no more players can connect.
     *
     * @return {@code true} if the game is started yet, {@code false} otherwise.
     */
    public boolean isGameStarted() {
        return this.gameState == GameState.GAME_STARTED;
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

    public InputController getInputController() {
        return inputController;
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

        try {
            if(inputController.moveCheck(message)){
                game.moveMotherNature(steps);
            }
        } catch (NullCurrentCardException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show a message to all players
     * @param message message to show
     */
    public void showGenericMessage(String message) {
        for (VirtualView virtualView : virtualViewMap.values()) {
            virtualView.showGenericMessage(message);
        }
    }


    public void refillClouds(){
        game.refillClouds();
    }

    public void moveStudent(MoveStudentMessage message) {
        try {
            StudentDisc student = (StudentDisc) game.getComponent(message.getStudentId());
            Player player = game.getPlayerByNickname(message.getNickname());

            switch (message.getPosition()) {
                case 0: {
                    player.getScoreboard().addStudentOnEntrance(student);
                    break;
                }
                case 1: {
                    game.getMap().getIsland(message.getIslandNumber()).addStudent(student.getColor());
                    break;
                }
                default:
                    showGenericMessage("Invalid MoveStudentMessage!");
            }
        } catch (MissingPlayerNicknameException | EntranceFullException e) {
            e.printStackTrace();
        }
    }

    public void setAssistantCard(AssistantCardList assistantCardList) {
        try {
            game.setAssistantCard(assistantCardList.getNickname(), assistantCardList.getAssistantCards().get(0).getID());
        } catch (MissingAssistantCardException | MissingPlayerNicknameException | NullCurrentCardException |
                 DoubleAssistantCardException e) {
            throw new RuntimeException(e);
        }
    }
}
