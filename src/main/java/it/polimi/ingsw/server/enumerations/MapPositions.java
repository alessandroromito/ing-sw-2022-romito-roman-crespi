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

    PLAYER1_SCOREBOARD,
    PLAYER2_SCOREBOARD,
    PLAYER3_SCOREBOARD,
    PLAYER4_SCOREBOARD,

    ISLAND_0,
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

    CLOUDS_1,
    CLOUDS_2,
    CLOUDS_3,
    CLOUDS_4,

    CARD_214,
    CARD_218,
    CARD_220,
    CARD_224,

    TRASH,
    BAG,
    NA;  //not assigned

    public static MapPositions getRandomIsland(){
       return MapPositions.values()[(int) (Math.random() * (12 - 24) + 12)];
    }
}


