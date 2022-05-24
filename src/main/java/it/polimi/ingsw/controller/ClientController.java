package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements ViewObserver, Observer {
    private final View view;
    private String nickname;
    private final ExecutorService queue;
    private Client client;

    /**
     * Default Constructor
     * @param view view
     */
    public ClientController(View view) {
        this.view = view;
        this.queue = Executors.newSingleThreadExecutor();
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void onUpdateServerDetails(HashMap<String, String> server){
        try{
            client = new Client(server.get("address"), Integer.parseInt(server.get("port")));
            client.addObserver(this);
            client.readMessage();
            client.enablePinger(true);
            queue.execute(view::askPlayerNickname);
        }
        catch(IOException e){
            queue.execute(() -> view.showLoginResult(false, false);
        }
    }

    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(this.nickname));
    }

    @Override
    public void onUpdatePlayersNumber(int playersNumber) {
        client.sendMessage(new PlayerNumberReply(this.nickname, playersNumber));
    }

    @Override
    public void onUpdateUseEffect(CharacterCard characterCard) {
    //    client.sendMessage(new UseEffectMessage(this.nickname, List.of(characterCard)));
    }

    @Override
    public void onUpdateMotherNaturePosition(int steps) {
        client.sendMessage(new MoveMotherNatureMessage(this.nickname, steps));
    }

    @Override
    public void onUpdatePickCloud(List<Cloud> cloudList) {

    }

    @Override
    public void onUpdateMoveStudent(int position, int islandNumber) {

    }

    @Override
    public void onUpdateMoveStudent(List<StudentDisc> studentDiscsList, int position, int islandNumber) {

    }

    @Override
    public void onUpdatePickCloud(Cloud cloud) {
        client.sendMessage(new CloudMessage(this.nickname, List.of(cloud)));
    }

    @Override
    public void onUpdateMoveStudent(StudentDisc student, int position, int islandNumber) {
        client.sendMessage(new MoveStudentMessage(this.nickname, List.of(student), position, islandNumber));
    }
}
