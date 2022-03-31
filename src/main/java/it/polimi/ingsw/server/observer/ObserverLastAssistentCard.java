package it.polimi.ingsw.server.observer;

import it.polimi.ingsw.server.model.player.Player;

public class ObserverLastAssistentCard extends MainObserver{
    private Player player;

    public ObserverLastAssistentCard(Player player){
        this.player = player;
    }

    @Override
    public void onUpdate(){
        if(player.isLastAssistantCard())    setEndGame();
    }
}
