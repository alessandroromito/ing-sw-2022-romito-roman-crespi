package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.GameState;
import it.polimi.ingsw.server.exception.CloudNotEmptyException;
import it.polimi.ingsw.server.exception.InvalidActionPhaseStateException;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.exception.MissingPlayersException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;

public class GameController {
    public static final String SAVING = "GameController.sav";
    private Model model;
    private Game game;

    private GameState gameState;
    private TurnController turnController;
    private InputController inputController;

    public static final String SAVED_GAME_FILE = "gameController.saving";

    public GameController(){
        init();
    }

    public void init(){
        this.model = new Model();
        this.game = model.getGame();
        this.inputController = new InputController(model, this);

        addGameController();

        setGameState(GameState.GAME_ROOM);
    }

    public Model getModel(){
        return this.model;
    }

    //da invocare appena possibile
    public void addGameController(){
        game.getMap().addGameController(this);
        game.getBag().addGameController(this);
        for(Player p:game.getPlayers()) {
            p.addGameController(this);
            p.getScoreboard().addGameController(this);
        }
    }

    private void startGame() throws MissingPlayerNicknameException, MissingPlayersException, InterruptedException, InvalidActionPhaseStateException, CloudNotEmptyException {
        setGameState(GameState.IN_GAME);

        //broadcast message inizio loading

        model.startGame();
        wait(1000);
        turnController = new TurnController(this);

        // Broadcast message fine loading

        turnController.newTurn();
    }

    public void askAllToChooseAssistantCard() throws MissingPlayerNicknameException, InvalidActionPhaseStateException, CloudNotEmptyException {
        for( String nickname : turnController.getNicknameQueue() ) {
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

    public void win(){

        // Broadcast message ...

        endGame();
    }


    /**
     * Reset the Game Instance and re-initialize GameController Class.
     */

    public void lastTurn(){
        turnController.setLastTurn();
    }

    public void winnerChecker(){
        ArrayList<Player> possibleWinners = new ArrayList<Player>();
        int minTower = 8,maxProfessor = 0;

        for(Player p:game.getPlayers())
            if(p.getScoreboard().getNumTowers() < minTower)
                minTower = p.getScoreboard().getNumTowers();

        for(Player p:game.getPlayers())
            if(p.getScoreboard().getNumTowers() ==minTower)
                possibleWinners.add(p);

        if(possibleWinners.size() == 1)
            endGameWinner(possibleWinners.get(0));
        else{
            for(Player p:possibleWinners)
                if(p.getScoreboard().getNumProf() > maxProfessor)
                    maxProfessor = p.getScoreboard().getNumProf();

            for(int i=0;i<possibleWinners.size();i++)
                if(possibleWinners.get(i).getScoreboard().getNumProf() != maxProfessor){
                    possibleWinners.remove(i);
                    i--;
                }

            if(possibleWinners.size() == 1)
                endGameWinner(possibleWinners.get(0));
            else if(possibleWinners.size() == 2)
                endGameWinner(possibleWinners.get(0),possibleWinners.get(1));
        }
    }

    public void endGameWinner(Player p){
// vince il giocatore p
    }

    public void endGameWinner(Player p1,Player p2){
// nel caso in cui vincano 2 giocatori
    }

    public void endGame() {
        // Reset Model

        // Delete storage data

        init();

        // Server.LOGGER.info("Game finished. Server ready for a new Game.");
    }


    /**
     * Broadcasts a winning message to all the clients connected.
     *
     * @param winningPlayer the nickname of the winning player.
     */
    /*
    private void broadcastWinMessage(String winningPlayer) {
        for (VirtualView vv : virtualViewMap.values()) {
            vv.showWinMessage(winningPlayer);
        }
    }
    */

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


}
