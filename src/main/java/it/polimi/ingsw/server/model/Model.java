package it.polimi.ingsw.server.model;

public class Model {
    /*
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final String SERVER_NAME = "server";

    private Game game;

    private boolean expertMode;

    private List<Player> players;
    private boolean gameStarted;
    int playerNumber;

    /**
     * Default constructor

    public Model() {
        //init
        players = new ArrayList<>();
        expertMode = false;
        playerNumber = 0;
        gameStarted = false;
    }

    /**
     * Return the number of player

    public int getNumberOfPlayer() {
        return players.size();
    }

    /**
     * Adds a player to the game.
     * Update the playersNumber.

    public void addPlayer(Player player) throws GameAlreadyStartedException, MaxPlayerException {
        if (gameStarted) throw new GameAlreadyStartedException("NOT possible to add players when game is already started!");
        if (player == null) throw new NullPointerException("Player cannot be NULL");
        if (playerNumber >= MAX_PLAYERS) throw new MaxPlayerException("Max Number of players reached!");
        players.add(player);
        playerNumber++;
    }

    /**
     * Starts the game

    public boolean startGame() throws GameAlreadyStartedException, MissingPlayersException {

        if(expertMode){
            game = new ExpertGame(players);
        }
        else game = new Game(players);

        return true;
    }

    /**
     * Set expertMode equals to @param bool

    public void setExpertMode(boolean bool){
        expertMode = bool;
    }

    /**
     *
     * @return the instance of the game

    public Game getGame(){
        return game;
    }

    /**
     * Search a nickname in the current players.
     *
     * @param nickname the nickname of the player.
     * @return {@code true} if the nickname is found, {@code false} otherwise.

    public boolean isNicknameTaken(String nickname) {
        return players.stream()
                .anyMatch(p -> nickname.equals(p.getNickname()));
    }

    public boolean isExpertMode(){ return expertMode; }

     */


}

