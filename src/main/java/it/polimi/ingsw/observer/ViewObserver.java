package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;

import java.util.List;

public interface ViewObserver {
    // per i metodi ci sarà la reference al clientController
    // da inserire tutti gli onUpdate che coinvolgono la view
    // in pratica l'utente sceglie e il server viene informato tramite gli onUpdate presenti qui

    void onUpdateNickname(String nickname);

    void onUpdatePlayersNumber(int playersNumber);

    void onUpdateUseEffect(CharacterCard characterCard);

    void onUpdateMotherNaturePosition(int steps);

    void onUpdatePickCloud(List<Cloud> cloudList);

    void onUpdateMoveStudent(int position, int islandNumber);

}
