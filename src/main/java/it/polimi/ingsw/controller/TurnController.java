package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.enumerations.PhaseState;
import it.polimi.ingsw.server.exception.MissingPlayerNicknameException;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class contains all the methods used to manage every single turn of the match.
 */
public class TurnController {

    private final Model model;
    private final Game game;
    private final List<String> nicknameQueue;
    private String activePlayer;

    private PhaseState phaseState;

    /**
     * Default Constructor of the TurnController
     */
    public TurnController() {
        this.model = Model.getModel();
        this.game = model.getGame();
        this.nicknameQueue = new ArrayList<>(game.getPlayersNicknames());
        this.activePlayer = nicknameQueue.get(0); // set first active player
    }

    /**
     * @return the nickname of the active player.
     */
    public String getActivePlayer() {
        return activePlayer;
    }

    /**
     * @param activePlayer the active Player to be set.
     */
    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * Initialize a new Turn.
     */
    public void newTurn() throws MissingPlayerNicknameException {
        activePlayer = nicknameQueue.get(0);
        // 1
        game.locateStudentsFromBag();
        // 2
        while(getPhaseState() == PhaseState.PLANNING_PHASE){
            askToChooseAssistantCard();
        }
    }

    private void askToChooseAssistantCard() throws MissingPlayerNicknameException {
        Player player = game.getPlayerByNickname(getActivePlayer());
        // asking to choose an assistant card
        // set the chosen assistant card to the player currentAssistantCard attribute
        next();
    }

    private void buildQueue(List<String> playersList) throws MissingPlayerNicknameException {
        List<Player> players = new ArrayList<>();

        for (String s : playersList) {
            Player p = game.getPlayerByNickname(s);
            players.add(p);
        }
        players.sort(new ComparatorAssistantCard());

        nicknameQueue.clear();

        for(Player player : players) {
            nicknameQueue.add(player.getNickname());
        }
    }


    /**
     * Set the next activePlayer.
     */
    public void next() throws MissingPlayerNicknameException {

        int currentActive = nicknameQueue.indexOf(activePlayer);
        if (currentActive + 1 < model.getNumberOfPlayer()) {
            currentActive = currentActive + 1;
        } else {
            nextPhase();
        }
        activePlayer = nicknameQueue.get(currentActive);
    }


    /**
     * Go to the next phase.
     */
    public void nextPhase() throws MissingPlayerNicknameException {
        switch (getPhaseState()) {
            case PLANNING_PHASE -> {
                phaseState = PhaseState.ACTION_PHASE;
                buildQueue(nicknameQueue);

            }
            case ACTION_PHASE -> {
                phaseState = PhaseState.PLANNING_PHASE;
                newTurn();
            }
            default -> System.out.println("error: INVALID PHASE STATE!");
        }
    }

    public PhaseState getPhaseState(){
        return phaseState;
    }
}
