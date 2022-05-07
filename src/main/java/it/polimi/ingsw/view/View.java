package it.polimi.ingsw.view;

import it.polimi.ingsw.network.message.MoveMotherNatureMessage;

public interface View {

    void update(MoveMotherNatureMessage message);
}