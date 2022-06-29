package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ViewObserver {
    // per i metodi ci sarà la reference al clientController
    // da inserire tutti gli onUpdate che coinvolgono la view
    // in pratica l'utente sceglie e il server viene informato tramite gli onUpdate presenti qui

    void onUpdateServerDetails(HashMap<String, String> server);

    void onUpdateNickname(String nickname);

    void onUpdatePlayersNumber(int playersNumber);

    void onUpdateMotherNaturePosition(int steps);

    void onUpdatePickCloud(ArrayList<Cloud> cloudList);

    void onUpdateMoveStudent(StudentDisc student, int position, int islandNumber);

    void onUpdatePlayAssistantCard(List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards);

    void onUpdateGameMode(String mode);

    void onUpdateUseEffect(boolean useEffect);

    void onUpdateUse209(int studentPos, int island);

    void onUpdateUse210();

    void onUpdateUse211(int islandNum);

    void onUpdateUse212();

    void onUpdateUse213(int islandNum);

    void onUpdateUse214();

    void onUpdateUse215(ArrayList<Integer> entranceStud, ArrayList<Integer> cardStudents);

    void onUpdateUse216();

    void onUpdateUse217(PawnColors color);

    void onUpdateUse218(List<Integer> entranceStud, List<PawnColors> diningStud);

    void onUpdateUse219(int finalStudentPos);

    void onUpdateUse220(PawnColors red);

    void update(Message message);
}
