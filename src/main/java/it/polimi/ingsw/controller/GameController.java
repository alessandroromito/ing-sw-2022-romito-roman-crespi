package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MoveMotherNatureMessage;
import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.*;
import it.polimi.ingsw.server.model.ExpertGame;
import it.polimi.ingsw.server.model.Game;
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

        //broadcast message start loading

        this.game = chosenExpertMode ? new ExpertGame(playersNicknames) : new Game(playersNicknames);

        turnController = new TurnController(this);

        // Broadcast message fine loading

        turnController.newTurn();
    }

    public void askAllToChooseAssistantCard() throws MissingPlayerNicknameException, InvalidActionPhaseStateException, CloudNotEmptyException {
        for(String nickname : turnController.getNicknameQueue() ) {
            Player player = game.getPlayerByNickname(nickname);
            askToChooseAssistantCard(player);
        }
        turnController.nextPhase();
    }

    /**
     * Ask to choose assistant card
     */
    private void askToChooseAssistantCard(Player player) {
        // asking to choose an assistant card
        // set the chosen assistant card to the player currentAssistantCard attribute
    }

    public void askToMoveStudent() {

        //chiedi dove e quale ( V Wiew )
    }

    public void askToMoveMotherNature() {
        //chiedi dove ( V Wiew )
    }

    public void askToChooseACloud() {
        //chiedi quale ( V Wiew)
    }

    /**
     * Set the State of the current Game.
     *
     * @param gameState State of the current Game.
     */
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void win(Player player){

        // Broadcast message ...

        endGame();
    }


    public void lastTurn(){
        turnController.setLastTurn();
    }

    public void winnerChecker(){
        ArrayList<Player> possibleWinners = new ArrayList<Player>();
        int minTower = 8, maxProfessor = 0;

        for(Player p:game.getPlayers())
            if(p.getScoreboard().getNumTowers() < minTower)
                minTower = p.getScoreboard().getNumTowers();

        for(Player p:game.getPlayers())
            if(p.getScoreboard().getNumTowers() ==minTower)
                possibleWinners.add(p);

        if(possibleWinners.size() == 1)
            win(possibleWinners.get(0));
        else{
            for(Player p:possibleWinners)
                if(p.getScoreboard().getNumProf() > maxProfessor)
                    maxProfessor = p.getScoreboard().getNumProf();

            for(int i=0;i<possibleWinners.size();i++)
                if(possibleWinners.get(i).getScoreboard().getNumProf() != maxProfessor){
                    possibleWinners.remove(i);
                    i--;
                }


            win(possibleWinners.get(0));

        }
    }

    public void endGame() {
        // Reset Model

        // Delete storage data

        init();

        // Server.LOGGER.info("Game finished.");
    }

    public void addPlayer(String nickname) throws GameAlreadyStartedException, MaxPlayerException, MissingPlayersException, MissingPlayerNicknameException, InvalidActionPhaseStateException, InterruptedException, CloudNotEmptyException {
        if (isGameStarted()) throw new GameAlreadyStartedException("NOT possible to add players when game is already started!");

        playersNicknames.add(nickname);

        if(chosenPlayerNumber == playersNicknames.size()){
            startGame();
        }
    }




    /*
    public void loginHandler(String nickname, VirtualView virtualView) {

        if (virtualViewMap.isEmpty()) { // First player logged. Ask number of players.
            addVirtualView(nickname, virtualView);
            game.addPlayer(new Player(nickname));

            virtualView.showLoginResult(true, true, Model.SERVER_NAME);
            virtualView.askPlayersNumber();

        } else if (virtualViewMap.size() < game.getChosenPlayersNumber()) {
            addVirtualView(nickname, virtualView);
            model.addPlayer(new Player(nickname));
            virtualView.showLoginResult(true, true, Model.SERVER_NAME);

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

    public void setChosenPlayerNumber(int chosenPlayerNumber) {
        this.chosenPlayerNumber = chosenPlayerNumber;
    }

    public void setChosenExpertMode(boolean chosenExpertMode) {
        this.chosenExpertMode = chosenExpertMode;
    }

    /**
     * Checks if the game is already started, then no more players can connect.
     *
     * @return {@code true} if the game isn't started yet, {@code false} otherwise.
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
}
