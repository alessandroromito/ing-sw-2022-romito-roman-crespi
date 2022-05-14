package it.polimi.ingsw.view;

import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.MotherNature;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.List;

public interface View {

    /**
     * Asks how many players
     */

    //ask to user to choose how many player will play
    void askPlayersNumber();

    //ask to a player to choose his nickname
    void askPlayerNickname();

    //show connected players
    void showLobby(List<String> playersNickname, int numPlayers);

    //show disconnection message
    void showDisconnectedPlayerMessage(String nicknameDisconnected, String text);

    //show error message
    void showErrorMessage(String error);

    //show game scenario
    void showGameScenario();

    //show game info detailed
    void showGameInfo(List<String> playersNickname, int unifiedIslandsNumber, int remainingBagStudents, String activePlayer);

    //show generic message
    void showGenericMessage(String genericMessage);

    //ask to choose assistant card
    void askAssistantCard();

    //ask to move a student
    void askToMoveAStudent();

    //ask to move mother nature
    void askToMoveMotherNature();

    //ask to choose a Cloud (and take 3 students)
    void askToChooseACloudAndTake3Students(List<Cloud> cloudList);

    //show if the login has gone successful
    void showLoginResult(boolean playerNicknameAccepted, boolean connectionSuccessful, String playerNickname);

    //show victory message
    void showVictoryMessage(String winner);






}