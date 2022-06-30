package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is related to the view part of the application.
 * Depending on the observer turned on there are different methods to be called.
 * Graphic Controller class (GUI) and CLI implements ViewObserver.
 */
public interface ViewObserver {

    /**
     * Update server details to establish a connection client-server.
     * @param server details containing address and port of the server.
     */
    void onUpdateServerDetails(HashMap<String, String> server);

    /**
     * Message to server containing nickname chosen by the player.
     * @param nickname nickname chosen.
     */
    void onUpdateNickname(String nickname);

    /**
     * Message to server containing players number chosen by the player.
     * @param playersNumber player number chosen by the player.
     */
    void onUpdatePlayersNumber(int playersNumber);

    void onUpdateMotherNaturePosition(int steps);

    /**
     * Message to server containing the cloud chosen by the player.
     * @param cloudList cloud chosen by the player.
     */
    void onUpdatePickCloud(ArrayList<Cloud> cloudList);

    /**
     * Message to server containing the motion of a student.
     * @param student student to be moved.
     * @param position where to move the student (0 dining room, 1 island).
     * @param islandNumber if island, in which one move the student.
     */
    void onUpdateMoveStudent(StudentDisc student, int position, int islandNumber);

    /**
     * Message to server containing the assistant card played.
     * @param assistantCards assistant card played.
     * @param playedAssistantCards assistant cards played from the start of the match.
     */
    void onUpdatePlayAssistantCard(List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards);

    /**
     * Message to server containing the game mode type for the match creation.
     * @param mode game mode passed ("Esperta" or "Normale")
     */
    void onUpdateGameMode(String mode);

    /**
     * Message to server containing if actuate the effect.
     * @param useEffect {code @true} use the effect, {code @false} do not use the effect.
     */
    void onUpdateUseEffect(boolean useEffect);

    /**
     * Message to server containing infos about using character card 209.
     * @param studentPos student position.
     * @param island island.
     */
    void onUpdateUse209(int studentPos, int island);

    /**
     * Message to server containing infos about using character card 210.
     */
    void onUpdateUse210();

    /**
     * Message to server containing infos about using character card 211.
     * @param islandNum number of the island.
     */
    void onUpdateUse211(int islandNum);

    /**
     * Message to server containing infos about using character card 212.
     */
    void onUpdateUse212();

    /**
     * Message to server containing infos about using character card 213.
     * @param islandNum number of the island.
     */
    void onUpdateUse213(int islandNum);

    /**
     * Message to server containing infos about using character card 214.
     */
    void onUpdateUse214();

    /**
     * Message to server containing infos about using character card 215.
     * @param entranceStud  array list of id of student in entrance room.
     * @param cardStudents students of the card.
     */
    void onUpdateUse215(ArrayList<Integer> entranceStud, ArrayList<Integer> cardStudents);

    /**
     * Message to server containing infos about using character card 216.
     */
    void onUpdateUse216();

    /**
     * Message to server containing infos about using character card 217.
     * @param color color chosen.
     */
    void onUpdateUse217(PawnColors color);

    /**
     * Message to server containing infos about using character card 218.
     * @param entranceStud
     * @param diningStud
     */
    void onUpdateUse218(List<Integer> entranceStud, List<PawnColors> diningStud);

    /**
     * Message to server containing infos about using character card 219.
     * @param finalStudentPos
     */
    void onUpdateUse219(int finalStudentPos);

    /**
     * Message to server containing infos about using character card 220.
     * @param red
     */
    void onUpdateUse220(PawnColors red);

    /**
     * Generic update method.
     * @param message message of the update.
     */
    void update(Message message);
}
