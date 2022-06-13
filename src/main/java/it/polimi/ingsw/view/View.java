package it.polimi.ingsw.view;

import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.ArrayList;
import java.util.List;

public interface View {

    /**
     * Asks how many players
     */

    //ask user to choose how many player will play
    void askPlayersNumber();

    void askGameMode();

    //ask a player to choose his nickname
    void askPlayerNickname();

    //show connected players
    void showLobby(List<String> playersNickname, int numPlayers);

    //show disconnection message
    void showDisconnectedPlayerMessage(String nicknameDisconnected, String text);

    //show error message
    void showErrorMessage(String error);

    //show game scenario
    void showGameScenario(GameSerialized gameSerialized);

    void showMergeIslandMessage(List<Integer> unifiedIsland);

    //show game info detailed
    void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer, List<CharacterCard> characterCards);

    void showGameInfo(List<String> playersNicknames, int length, int size, String activePlayer);

    //show generic message
    void showGenericMessage(String genericMessage);

    //ask to choose assistant card
    void askAssistantCard(List<AssistantCard> assistantCards);

    //ask to move a student
    void askToMoveAStudent(List<StudentDisc> studentDiscs, int position, int islandNumber);

    //ask to move mother nature
    void askToMoveMotherNature(int maxSteps);

    //ask to choose a Cloud
    void askToChooseACloud(ArrayList<Cloud> cloudList);

    //show if the login has gone successful
    void showLoginResult(String nickname, boolean playerNicknameAccepted, boolean connectionSuccessful);

    //show victory message
    void showVictoryMessage(String winner);






}