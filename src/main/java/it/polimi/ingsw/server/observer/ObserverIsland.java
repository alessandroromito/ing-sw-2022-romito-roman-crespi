package it.polimi.ingsw.server.observer;

import it.polimi.ingsw.server.model.map.Map;

public class ObserverIsland extends MainObserver{
    private Map map;

    public ObserverIsland(Map map){
        this.map = map;
    }

    public void onUpdate(){
        int numGroupID;
        for(int i; i < 12; i++){
            map.getIsland(i);
        }
    }
}
