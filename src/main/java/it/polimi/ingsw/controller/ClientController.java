package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.util.ArrayList;
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
        queue = Executors.newSingleThreadExecutor();
    }

    @Override
    public void update(Message message) {
        switch(message.getMessageType()){
            case PING -> {
            }
            case ERROR -> {
                ErrorMessage errorMessage = (ErrorMessage) message;
                view.showErrorMessage(errorMessage.getError());
            }
            case WINNER_DECLARATION -> {
                VictoryMessage victoryMessage = (VictoryMessage) message;
                view.showVictoryMessage(victoryMessage.getWinnerNickname());
            }
            case LOBBY -> {
                LobbyMessage lobbyMessage = (LobbyMessage) message;
                queue.execute(() -> view.showLobby(lobbyMessage.getPlayersNickname(), lobbyMessage.getNumMaxPlayers()));
            }
            case LOGIN_REQUEST -> {

            }
            case LOGIN_REPLY -> {
                LoginReply loginReplyMessage = (LoginReply) message;
                queue.execute(() -> view.showLoginResult(loginReplyMessage.getNickname(), loginReplyMessage.isNicknameAccepted(), loginReplyMessage.isConnectionSuccessful()));
            }
            case MERGE_ISLANDS -> {
                MergeIslandMessage mergeIslandMessage = (MergeIslandMessage) message;
                //
            }
            case PLAYER_NUMBER_REQUEST -> queue.execute(view::askPlayersNumber);
            case PLAY_ASSISTANT_CARD -> {
                AssistantCardMessage assistantCardMessage = (AssistantCardMessage) message;
                queue.execute(() -> view.askAssistantCard(assistantCardMessage.getAssistantCards()));
            }
            case PLAYER_NUMBER_REPLY -> {
            }
            case GENERIC_MESSAGE -> {
                GenericMessage genericMessage = (GenericMessage) message;
                queue.execute(() -> view.showGenericMessage(genericMessage.getMessage()));
            }
            case DISCONNECTED_PLAYER -> {
                DisconnectedPlayerMessage disconnectedPlayerMessage = (DisconnectedPlayerMessage) message;
                client.disconnect();
                view.showDisconnectedPlayerMessage(disconnectedPlayerMessage.getNicknameDisconnected(), disconnectedPlayerMessage.getMessageText());
            }
            case GAME_SCENARIO -> {
                GameScenarioMessage gameScenarioMessage = (GameScenarioMessage) message;
                queue.execute(() -> view.showGameScenario(gameScenarioMessage.getGameSerialized()));
            }
            case MOVE_MOTHER_NATURE -> {
                queue.execute(() -> view.askToMoveMotherNature(((MoveMotherNatureMessage) message).getSteps()));
            }
            case MOVE_STUDENT -> {
                MoveStudentMessage moveStudentMessage = (MoveStudentMessage) message;
                queue.execute(() -> view.askToMoveAStudent(moveStudentMessage.getStudentDiscs(), moveStudentMessage.getPosition(), moveStudentMessage.getIslandNumber()));
            }
            case PICK_CLOUD -> {
                CloudMessage cloudMessage = (CloudMessage) message;
                queue.execute(() -> view.askToChooseACloud(cloudMessage.getCloudList()));
            }
            case GAME_MODE -> {
                queue.execute(view::askGameMode);
            }
            case USE_EFFECT -> {

            }
            case CARD209 -> {
            }
            case CARD210 -> {
            }
            case CARD211 -> {
            }
            case CARD212 -> {
            }
            case CARD213 -> {
            }
            case CARD214 -> {
            }
            case CARD216 -> {
            }
            case CARD217 -> {
            }
            case CARD219 -> {
            }
        }
    }

    @Override
    public void onUpdateGameMode(String gameMode) {
        client.sendMessage(new GameModeReplyMessage(this.nickname, gameMode.equals("Esperta")));
    }

    @Override
    public void onUpdateServerDetails(HashMap<String, String> server){
        try{
            client = new Client(server.get("address"), Integer.parseInt(server.get("port")));
            client.addObserver(this);
            client.readMessage();
            //client.enablePinger(true);
            queue.execute(view::askPlayerNickname);
        }
        catch(IOException e){
            queue.execute(() -> view.showLoginResult(nickname, false, false));
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
    public void onUpdateMotherNaturePosition(int steps) {
        client.sendMessage(new MoveMotherNatureMessage(this.nickname, steps));
    }

    @Override
    public void onUpdatePickCloud(ArrayList<Cloud> clouds) {
        client.sendMessage(new CloudMessage(this.nickname, clouds));
    }

    @Override
    public void onUpdatePlayAssistantCard(List<AssistantCard> assistantCards){
        client.sendMessage(new AssistantCardMessage(nickname, assistantCards));
    }

    @Override
    public void onUpdateMoveStudent(StudentDisc student, int position, int islandNumber) {
        client.sendMessage(new MoveStudentMessage(this.nickname, List.of(student), position, islandNumber));
    }

}
