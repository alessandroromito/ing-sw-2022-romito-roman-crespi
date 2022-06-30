package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.enumerations.PawnColors;
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

/**
 * Class client side.
 * The client notify changes on the graphic interface only with the methods of this class.
 * The server notify changes to the client only with "update" method of this class.
 */
public class ClientController implements ViewObserver, Observer {
    private final View view;
    private String nickname;
    private final ExecutorService queue;
    private Client client;

    /**
     * Default Constructor.
     * @param view view (interface implemented)
     */
    public ClientController(View view) {
        this.view = view;
        queue = Executors.newSingleThreadExecutor();
    }

    /**
     * Server updates the client using this method.
     * @param message information to be analyzed to manage the update to supply to the client.
     */
    @Override
    public void update(Message message) {
        switch(message.getMessageType()){
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
                view.showLobby(lobbyMessage.getPlayersNickname(), lobbyMessage.getNumMaxPlayers());
            }
            case LOGIN_REPLY -> {
                LoginReply loginReplyMessage = (LoginReply) message;
                queue.execute(() -> view.showLoginResult(loginReplyMessage.getNickname(), loginReplyMessage.isNicknameAccepted(), loginReplyMessage.isConnectionSuccessful()));
            }
            case MERGE_ISLANDS -> {
                MergeIslandMessage mergeIslandMessage = (MergeIslandMessage) message;
                view.showMergeIslandMessage(mergeIslandMessage.getUnifiedIslands());
            }
            case PLAYER_NUMBER_REQUEST -> queue.execute(view::askPlayersNumber);
            case PLAY_ASSISTANT_CARD -> {
                AssistantCardMessage assistantCardMessage = (AssistantCardMessage) message;
                queue.execute(() -> view.askAssistantCard(assistantCardMessage.getAssistantCards(), assistantCardMessage.getPlayedAssistantCards()));
            }
            case GENERIC_MESSAGE -> {
                GenericMessage genericMessage = (GenericMessage) message;
                queue.execute(() -> view.showGenericMessage(genericMessage.getMessage()));
            }
            case DISCONNECTED_PLAYER -> {
                DisconnectedPlayerMessage disconnectedPlayerMessage = (DisconnectedPlayerMessage) message;
                view.showDisconnectedPlayerMessage(disconnectedPlayerMessage.getNicknameDisconnected());
            }
            case GAME_SCENARIO -> {
                GameScenarioMessage gameScenarioMessage = (GameScenarioMessage) message;
                queue.execute(() -> view.showGameScenario(gameScenarioMessage.getGameSerialized()));
            }
            case MOVE_MOTHER_NATURE -> queue.execute(() -> view.askToMoveMotherNature(((MoveMotherNatureMessage) message).getSteps()));
            case MOVE_STUDENT -> {
                MoveStudentMessage moveStudentMessage = (MoveStudentMessage) message;
                queue.execute(() -> view.askToMoveAStudent(moveStudentMessage.getStudentDiscs(), moveStudentMessage.getPosition(), moveStudentMessage.getIslandNumber()));
            }
            case PICK_CLOUD -> {
                CloudMessage cloudMessage = (CloudMessage) message;
                queue.execute(() -> view.askToChooseACloud(cloudMessage.getCloudList()));
            }
            case GAME_MODE -> queue.execute(view::askGameMode);
            case PLAY_CHARACTER_CARD -> {
                CharacterCardMessage characterCardMessage = (CharacterCardMessage) message;
                queue.execute(() -> view.askCharacterCard(characterCardMessage.getCharacterCards()));
            }
            case RECONNECTING_MESSAGE -> {
                ReconnectingMessage reconnectingMessage = (ReconnectingMessage) message;
                view.showReconnectedMessage(reconnectingMessage.getNicknameReconnecting());
            }
        }
    }

    /**
     * Client updates the server about GameMode.
     * @param gameMode information sent by the client.
     */
    @Override
    public void onUpdateGameMode(String gameMode) {
        client.sendMessage(new GameModeReplyMessage(this.nickname, gameMode.equals("Esperta")));
    }

    /**
     * Client updates the server about the effect to use.
     * @param useEffect effect to use.
     */
    @Override
    public void onUpdateUseEffect(boolean useEffect) {
        client.sendMessage(new CharacterCardReplyMessage(this.nickname, false));
    }

    /**
     * Client updates the server about the address and the port to be connected on.
     * @param server contains address and port.
     */
    @Override
    public void onUpdateServerDetails(HashMap<String, String> server){
        try{
            client = new Client(server.get("address"), Integer.parseInt(server.get("port")));
            client.addObserver(this);
            client.readMessage();
            queue.execute(view::askPlayerNickname);
        }
        catch(IOException e){
            queue.execute(() -> view.showLoginResult(nickname, false, false));
        }
    }

    /**
     * Client updates the server about nickname of the user connected.
     * @param nickname nickname.
     */
    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new LoginRequest(this.nickname));
    }

    /**
     * Client updates the server about player number of the match.
     * @param playersNumber number of player playing the match.
     */
    @Override
    public void onUpdatePlayersNumber(int playersNumber) {
        client.sendMessage(new PlayerNumberReply(this.nickname, playersNumber));
    }

    /**
     * Client updates the server about the movement of mother nature.
     * @param steps movement of mother nature.
     */
    @Override
    public void onUpdateMotherNaturePosition(int steps) {
        client.sendMessage(new MoveMotherNatureMessage(this.nickname, steps));
    }

    /**
     * Client updates the server about picking a cloud.
     * @param clouds cloud picked.
     */
    @Override
    public void onUpdatePickCloud(ArrayList<Cloud> clouds) {
        client.sendMessage(new CloudMessage(this.nickname, clouds));
    }

    /**
     * Client updates the server about playing assistant card.
     * @param assistantCards assistant card chosen by the user.
     * @param playedAssistantCards assistant card played yet.
     */
    @Override
    public void onUpdatePlayAssistantCard(List<AssistantCard> assistantCards, List<AssistantCard> playedAssistantCards){
        client.sendMessage(new AssistantCardMessage(nickname, assistantCards, assistantCards));
    }

    /**
     * Client updates the server about moving a student.
     * @param student student to be moved.
     * @param position dining room (0) or island (1)
     * @param islandNumber island chosen.
     */
    @Override
    public void onUpdateMoveStudent(StudentDisc student, int position, int islandNumber) {
        client.sendMessage(new MoveStudentMessage(this.nickname, List.of(student), position, islandNumber));
    }

    /**
     * Client update the server about the use of the characterCard 209
     * @param studentPos used by the effect
     * @param island used by the effect
     */
    @Override
    public void onUpdateUse209(int studentPos, int island){
        client.sendMessage(new Card209Message(this.nickname, studentPos, island));
    }

    /**
     * Client update the server about the use of the characterCard 210
     */
    @Override
    public void onUpdateUse210() {
        client.sendMessage(new Card210Message(this.nickname));
    }

    /**
     * Client update the server about the use of the characterCard 211
     * @param islandNum
     */
    @Override
    public void onUpdateUse211(int islandNum) {
        client.sendMessage(new Card211Message(this.nickname, islandNum));
    }

    /**
     * Client update the server about the use of the characterCard 212
     */
    @Override
    public void onUpdateUse212() {
        client.sendMessage(new Card212Message(this.nickname));
    }

    /**
     * Client update the server about the use of the characterCard 213
     * @param islandNum
     */
    @Override
    public void onUpdateUse213(int islandNum) {
        client.sendMessage(new Card213Message(this.nickname, islandNum));
    }

    /**
     * Client update the server about the use of the characterCard 214
     */
    @Override
    public void onUpdateUse214() {
        client.sendMessage(new Card214Message(this.nickname));
    }

    /**
     * Client update the server about the use of the characterCard 215
     * @param entranceStud
     * @param cardStudents
     */
    @Override
    public void onUpdateUse215(ArrayList<Integer> entranceStud, ArrayList<Integer> cardStudents) {
        client.sendMessage(new Card215Message(this.nickname, entranceStud, cardStudents));
    }

    /**
     * Client update the server about the use of the characterCard 216
     */
    @Override
    public void onUpdateUse216() {
        client.sendMessage(new Card216Message(this.nickname));
    }

    /**
     * Client update the server about the use of the characterCard 217
     * @param color
     */
    @Override
    public void onUpdateUse217(PawnColors color) {
        client.sendMessage(new Card217Message(this.nickname, color));
    }

    /**
     * Client update the server about the use of the characterCard 218
     * @param entranceStud
     * @param diningStud
     */
    @Override
    public void onUpdateUse218(List<Integer> entranceStud, List<PawnColors> diningStud) {
        client.sendMessage(new Card218Message(this.nickname, entranceStud, diningStud));
    }

    /**
     * Client update the server about the use of the characterCard 219
     * @param studentNum
     */
    @Override
    public void onUpdateUse219(int studentNum) {
        client.sendMessage(new Card219Message(this.nickname, studentNum));
    }

    /**
     * Client update the server about the use of the characterCard 220
     * @param color
     */
    @Override
    public void onUpdateUse220(PawnColors color) {
        client.sendMessage(new Card220Message(this.nickname, color));
    }
}
