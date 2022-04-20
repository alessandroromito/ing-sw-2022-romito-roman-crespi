package it.polimi.ingsw.server.enumerations;

public enum MapPositions {

    //AGGIUNGERE NUOVE ENUM ALLA FINE!!!

    PLAYER1_ENTRANCE,
    PLAYER1_DINING,
    PLAYER1_HAND,
    PLAYER2_ENTRANCE,
    PLAYER2_DINING,
    PLAYER2_HAND,
    PLAYER3_ENTRANCE,
    PLAYER3_DINING,
    PLAYER3_HAND,
    PLAYER4_ENTRANCE,
    PLAYER4_DINING,
    PLAYER4_HAND,

    ISLAND_1,
    ISLAND_2,
    ISLAND_3,
    ISLAND_4,
    ISLAND_5,
    ISLAND_6,
    ISLAND_7,
    ISLAND_8,
    ISLAND_9,
    ISLAND_10,
    ISLAND_11,
    ISLAND_12,

    CLOUDS_1,
    CLOUDS_2,
    CLOUDS_3,
    CLOUDS_4,

    TRASH,
    BAG;

    public static MapPositions getRandomIsland(){
       return MapPositions.values()[(int) (Math.random() * (12 - 24) + 12)];
    }
}


