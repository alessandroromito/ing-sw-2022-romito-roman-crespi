package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.view.gui.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreboardSceneManager extends ViewObservable implements SceneManagerInterface, Initializable {

    private final Stage stage;

    @FXML
    private ImageView blue0;

    @FXML
    private ImageView blue1;

    @FXML
    private ImageView blue2;

    @FXML
    private ImageView blue3;

    @FXML
    private ImageView blue4;

    @FXML
    private ImageView blue5;

    @FXML
    private ImageView blue6;

    @FXML
    private ImageView blue7;

    @FXML
    private ImageView blue8;

    @FXML
    private ImageView blue9;

    @FXML
    private ImageView entrance0;

    @FXML
    private ImageView entrance1;

    @FXML
    private ImageView entrance2;

    @FXML
    private ImageView entrance3;

    @FXML
    private ImageView entrance4;

    @FXML
    private ImageView entrance5;

    @FXML
    private ImageView entrance6;

    @FXML
    private ImageView entrance7;

    @FXML
    private ImageView entrance8;

    @FXML
    private ImageView green0;

    @FXML
    private ImageView green1;

    @FXML
    private ImageView green2;

    @FXML
    private ImageView green3;

    @FXML
    private ImageView green4;

    @FXML
    private ImageView green5;

    @FXML
    private ImageView green6;

    @FXML
    private ImageView green7;

    @FXML
    private ImageView green8;

    @FXML
    private ImageView green9;

    @FXML
    private ImageView professor_blue;

    @FXML
    private ImageView professor_green;

    @FXML
    private ImageView professor_purple;

    @FXML
    private ImageView professor_red;

    @FXML
    private ImageView professor_yellow;

    @FXML
    private ImageView purple0;

    @FXML
    private ImageView purple1;

    @FXML
    private ImageView purple2;

    @FXML
    private ImageView purple3;

    @FXML
    private ImageView purple4;

    @FXML
    private ImageView purple5;

    @FXML
    private ImageView purple6;

    @FXML
    private ImageView purple7;

    @FXML
    private ImageView purple8;

    @FXML
    private ImageView purple9;

    @FXML
    private ImageView red2;

    @FXML
    private ImageView red0;

    @FXML
    private ImageView red1;

    @FXML
    private ImageView red3;

    @FXML
    private ImageView red4;

    @FXML
    private ImageView red5;

    @FXML
    private ImageView red6;

    @FXML
    private ImageView red7;

    @FXML
    private ImageView red8;

    @FXML
    private ImageView red9;

    @FXML
    private ImageView scorebrd;

    @FXML
    private ImageView yellow0;

    @FXML
    private ImageView yellow1;

    @FXML
    private ImageView yellow2;

    @FXML
    private ImageView yellow3;

    @FXML
    private ImageView yellow4;

    @FXML
    private ImageView yellow5;

    @FXML
    private ImageView yellow6;

    @FXML
    private ImageView yellow7;

    @FXML
    private ImageView yellow8;

    @FXML
    private ImageView yellow9;

    @FXML
    private Circle tower0;

    @FXML
    private Circle tower1;

    @FXML
    private Circle tower2;

    @FXML
    private Circle tower3;

    @FXML
    private Circle tower4;

    @FXML
    private Circle tower5;

    @FXML
    private Circle tower6;

    @FXML
    private Circle tower7;

    private ImageView[] entrance = new ImageView[9];
    private ImageView[][] diningRoom = new ImageView[5][10];
    private int[] visibleStudents = new int[5];
    private boolean[] entranceFree = new boolean[7];
    private Circle[] towers = new Circle[8];
    private int towerColor;

    public ScoreboardSceneManager() {
        stage = new Stage();
        stage.initOwner(SceneManager.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    @FXML
    void click(MouseEvent event) {

    }

    @FXML
    void in(MouseEvent event) {

    }

    @FXML
    void out(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entrance[0] = entrance0; entrance[1] = entrance1; entrance[2] = entrance2; entrance[3] = entrance3; entrance[4] = entrance4; entrance[5] = entrance5; entrance[6] = entrance6; entrance[7] = entrance7; entrance[8] = entrance8;
        towers[0] = tower0; towers[1] = tower1; towers[2] = tower2; towers[3] = tower3; towers[4] = tower4; towers[5] = tower5; towers[6] = tower6; towers[7] = tower7;

        diningRoom[0][0] = green0; diningRoom[0][1] = green1; diningRoom[0][2] = green2; diningRoom[0][3] = green3; diningRoom[0][4] = green4; diningRoom[0][5] = green5; diningRoom[0][6] = green6; diningRoom[0][7] = green7; diningRoom[0][8] = green8; diningRoom[0][9] = green9;
        diningRoom[1][0] = red0; diningRoom[1][1] = red1; diningRoom[1][2] = red2; diningRoom[1][3] = red3; diningRoom[1][4] = red4; diningRoom[1][5] = red5; diningRoom[1][6] = red6; diningRoom[1][7] = red7; diningRoom[1][8] = red8; diningRoom[1][9] = red9;
        diningRoom[2][0] = yellow0; diningRoom[2][1] = yellow1; diningRoom[2][2] = yellow2; diningRoom[2][3] = yellow3; diningRoom[2][4] = yellow4; diningRoom[2][5] = yellow5; diningRoom[2][6] = yellow6; diningRoom[2][7] = yellow7; diningRoom[2][8] = yellow8; diningRoom[2][9] = yellow9;
        diningRoom[3][0] = purple0; diningRoom[3][1] = purple1; diningRoom[3][2] = purple2; diningRoom[3][3] = purple3; diningRoom[3][4] = purple4; diningRoom[3][5] = purple5; diningRoom[3][6] = purple6; diningRoom[3][7] = purple7; diningRoom[3][8] = purple8; diningRoom[3][9] = purple9;
        diningRoom[4][0] = blue0; diningRoom[4][1] = blue1; diningRoom[4][2] = blue2; diningRoom[4][3] = blue3; diningRoom[4][4] = blue4; diningRoom[4][5] = blue5; diningRoom[4][6] = blue6; diningRoom[4][7] = blue7; diningRoom[4][8] = blue8; diningRoom[4][9] = blue9;

        for(int i=0;i<5;i++)
            for(int k=0;k<10;k++){
                diningRoom[i][k].setVisible(false);
                diningRoom[i][k].setDisable(true);
            }

        for (int i=0;i<5;i++)   visibleStudents[i] = 0;
        for (int i=0;i<7;i++)   entranceFree[i] = false;

        setTowerColor(0);
    }

    //color: 0=black 1=white 2=grey
    public void setTowerColor(int color){
        towerColor = color;
        for(Circle c: towers){
            if(color == 0)
                c.setFill(Color.BLACK);
            if(color == 1)
                c.setFill(Color.WHITE);
            if(color == 2)
                c.setFill(Color.GREY);
        }
    }

    //color: 1/5
    public void addStudentOnDining(int color){
        diningRoom[color-1][visibleStudents[color-1]].setVisible(true);
        visibleStudents[color-1]++;
    }

    //da testare se funziona onmouseclicked
    public void addStudentOnEntrance(int color,int id){
        for (int i=0;i<7;i++)
            if(entranceFree[i]){
                entranceFree[i] = false;
                ImageView temp = entrance[i];
                entrance[i].setImage(createImage(color));
                entrance[i].setId(Integer.toString(id));
                entrance[i].setOnMouseClicked(ck -> openOptionMenu(temp.getId()));
                return;
            }
    }

    public void removeStudentFromEntrance(int id){
        for(int i=0;i<7;i++)
            if(entrance[i].getId().equals(Integer.toString(id))){
                entrance[i].setImage(null);
                entranceFree[i] = true;
            }
    }

    public void openOptionMenu(String id){

    }
/*
    public ImageView findById(String id){

    }
*/
    public Image createImage(int color){
        Image image = null;

        switch (color){
            case 1: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/1_Verde.png")); break;
            case 2: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/2_Rosso.png")); break;
            case 3: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/3_Giallo.png")); break;
            case 4: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/4_Viola.png")); break;
            case 5: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/5_Azzurro.png")); break;
        }

        return image;
    }

    public void showScoreboards() {
        stage.showAndWait();
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public void updateValues(GameSerialized gameSerialized) { }
}
