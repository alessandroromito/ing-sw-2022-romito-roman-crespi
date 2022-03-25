package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.bag.Bag;
import it.polimi.ingsw.server.model.component.Component;
import it.polimi.ingsw.server.model.component.MotherNature;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Map;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private ArrayList<Component> components;
    private final Map map;
    private final Bag bag;

    /**
     * Default constructor
     */
    public Game(boolean exMode, int pNumber) {
        this.players = new ArrayList<>();
        this.components = new ArrayList<>();
        // Create map
        this.map = new Map();
        this.bag = new Bag();
        // Create Mother Nature
        components.set(0, new MotherNature());
        // Create NoEntry Tile
        for(int i=2; i<6; i++){
            components.set(i, new StudentDisc());
        }
        // Create AssistantCard
        for(int i=1; i<130; i++){
            components.set(i, new StudentDisc());
        }
        // Create students
        for(int i=1; i<130; i++){
            components.set(i, new StudentDisc());
        }
    }

    /**
     * @return the instance of player's list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param nickname the nickname of the player to be found.
     * @return the player given his {@code nickname}, {@code null} otherwise.
     */
    public Player getPlayerByNickname(String nickname) {
        return players.stream()
                .filter(player -> nickname.equals(player.getNickname()))
                .findFirst()
                .orElse(null);
    }

    /**
     * @return the instance of components array
     */
    public ArrayList<Component> getComponents() {
        return components;
    }

    /**
     * @param ID the ID of the component to be found
     * @return the component given his {@code ID}, {@code null} otherwise.
     */
    public Component getComponent(int ID){
        return components.stream()
                .filter(component -> ID == (component.getID()))
                .findFirst()
                .orElse(null);
    }
}
