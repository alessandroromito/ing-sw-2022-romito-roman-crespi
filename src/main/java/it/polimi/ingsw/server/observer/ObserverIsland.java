package it.polimi.ingsw.server.observer;

import it.polimi.ingsw.server.model.map.Map;

public class ObserverIsland extends MainObserver{
    private Map map;

    public ObserverIsland(Map map){
        this.map = map;
    }

    @Override
    public void onUpdate(){
        int numGroupID;
        for(int i = 0; i < 12; i++){
            map.getIsland(i);
        }
    }
}
