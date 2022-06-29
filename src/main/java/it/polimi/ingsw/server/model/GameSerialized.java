package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.map.Island;
import it.polimi.ingsw.server.model.player.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Reduced version of the whole game class, used to refresh the map with the changes during the game
 * Reduced as possible to be able to sent it through the network easily
 */
public class GameSerialized implements Serializable {
    @Serial
    private static final long serialVersionUID = -7030282992609372936L;

    private final Boolean expertMode;
    private final int motherNaturePos;
    private int activeCardID = -1;

    private final ArrayList<CharacterCard> characterCards = new ArrayList<>();
    private final ArrayList<Cloud> clouds = new ArrayList<>();
    private final ArrayList<SerializableIsland> serializableIslands = new ArrayList<>();
    private final ArrayList<SerializableScoreboard> serializableScoreboard = new ArrayList<>();

    /**
     * Default constructor
     * @param game game to be reduced
     */
    public GameSerialized(Game game){
        this.motherNaturePos = game.getMap().getMotherNaturePosition();
        this.expertMode = game.isExpertMode();

        ArrayList<Integer> groupIDs = new ArrayList<>();

        for(Island island : game.getMap().getIslands()){
                if (island.isDisabled()) {
                    if(!groupIDs.contains(island.getGroupID())){
                        serializableIslands.add(new SerializableIsland(game.getMap().getGhostIsland(island.getID())));
                        ArrayList<Integer> referencedIslands = new ArrayList<>();
                        for (Island isl : game.getMap().getIslands()) {
                            if (isl.isDisabled() && isl.getGroupID() == island.getGroupID()) {
                                referencedIslands.add(isl.getID() + 1);
                            }
                        }
                        serializableIslands.get(serializableIslands.size() - 1).setReferencedIslands(referencedIslands);
                        groupIDs.add(island.getGroupID());
                    }
                } else {
                    serializableIslands.add(new SerializableIsland(island));
                }
        }

        for(Player player : game.getPlayers()){
            serializableScoreboard.add(new SerializableScoreboard(player.getScoreboard(), player));
        }

        clouds.addAll(game.getMap().getClouds());

        if(expertMode){
            characterCards.addAll(game.getCharacterCards());
            activeCardID = game.getActiveCardID();
        }

    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<SerializableIsland> getSerializableIslands() {
        return serializableIslands;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<SerializableScoreboard> getSerializableScoreboard() {
        return serializableScoreboard;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * Getter method
     * @return
     */
    public int getMotherNaturePos() {
        return motherNaturePos;
    }

    /**
     * Getter method
     * @return
     */
    public Boolean getExpertMode() {
        return expertMode;
    }

    /**
     * Getter method
     * @return
     */
    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    /**
     * Getter method
     * @return
     */
    public int getActiveCardID() {
        return activeCardID;
    }
    @Override
    public String toString() {
        return "GameSerialized:[ " + "islands: " + serializableIslands.size() + "]";
    }

}
