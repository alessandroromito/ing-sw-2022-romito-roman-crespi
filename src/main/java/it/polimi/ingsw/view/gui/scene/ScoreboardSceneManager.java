package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.view.gui.GraphicController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.GraphicController.nickname;

public class ScoreboardSceneManager extends ViewObservable implements SceneManagerInterface, Initializable {

    private final Stage stage;


    @FXML
    private ImageView blue0;

    @FXML
    private ImageView blue0_1;

    @FXML
    private ImageView blue0_2;

    @FXML
    private ImageView blue1;

    @FXML
    private ImageView blue1_1;

    @FXML
    private ImageView blue1_2;

    @FXML
    private ImageView blue2;

    @FXML
    private ImageView blue2_1;

    @FXML
    private ImageView blue2_2;

    @FXML
    private ImageView blue3;

    @FXML
    private ImageView blue3_1;

    @FXML
    private ImageView blue3_2;

    @FXML
    private ImageView blue4;

    @FXML
    private ImageView blue4_1;

    @FXML
    private ImageView blue4_2;

    @FXML
    private ImageView blue5;

    @FXML
    private ImageView blue5_1;

    @FXML
    private ImageView blue5_2;

    @FXML
    private ImageView blue6;

    @FXML
    private ImageView blue6_1;

    @FXML
    private ImageView blue6_2;

    @FXML
    private ImageView blue7;

    @FXML
    private ImageView blue7_1;

    @FXML
    private ImageView blue7_2;

    @FXML
    private ImageView blue8;

    @FXML
    private ImageView blue8_1;

    @FXML
    private ImageView blue8_2;

    @FXML
    private ImageView blue9;

    @FXML
    private ImageView blue9_1;

    @FXML
    private ImageView blue9_2;

    @FXML
    private ImageView entrance0;

    @FXML
    private ImageView entrance0_1;

    @FXML
    private ImageView entrance0_2;

    @FXML
    private ImageView entrance1;

    @FXML
    private ImageView entrance1_1;

    @FXML
    private ImageView entrance1_2;

    @FXML
    private ImageView entrance2;

    @FXML
    private ImageView entrance2_1;

    @FXML
    private ImageView entrance2_2;

    @FXML
    private ImageView entrance3;

    @FXML
    private ImageView entrance3_1;

    @FXML
    private ImageView entrance3_2;

    @FXML
    private ImageView entrance4;

    @FXML
    private ImageView entrance4_1;

    @FXML
    private ImageView entrance4_2;

    @FXML
    private ImageView entrance5;

    @FXML
    private ImageView entrance5_1;

    @FXML
    private ImageView entrance5_2;

    @FXML
    private ImageView entrance6;

    @FXML
    private ImageView entrance6_1;

    @FXML
    private ImageView entrance6_2;

    @FXML
    private ImageView entrance7;

    @FXML
    private ImageView entrance7_1;

    @FXML
    private ImageView entrance7_2;

    @FXML
    private ImageView entrance8;

    @FXML
    private ImageView entrance8_1;

    @FXML
    private ImageView entrance8_2;

    @FXML
    private ImageView green0;

    @FXML
    private ImageView green0_1;

    @FXML
    private ImageView green0_2;

    @FXML
    private ImageView green1;

    @FXML
    private ImageView green1_1;

    @FXML
    private ImageView green1_2;

    @FXML
    private ImageView green2;

    @FXML
    private ImageView green2_1;

    @FXML
    private ImageView green2_2;

    @FXML
    private ImageView green3;

    @FXML
    private ImageView green3_1;

    @FXML
    private ImageView green3_2;

    @FXML
    private ImageView green4;

    @FXML
    private ImageView green4_1;

    @FXML
    private ImageView green4_2;

    @FXML
    private ImageView green5;

    @FXML
    private ImageView green5_1;

    @FXML
    private ImageView green5_2;

    @FXML
    private ImageView green6;

    @FXML
    private ImageView green6_1;

    @FXML
    private ImageView green6_2;

    @FXML
    private ImageView green7;

    @FXML
    private ImageView green7_1;

    @FXML
    private ImageView green7_2;

    @FXML
    private ImageView green8;

    @FXML
    private ImageView green8_1;

    @FXML
    private ImageView green8_2;

    @FXML
    private ImageView green9;

    @FXML
    private ImageView green9_1;

    @FXML
    private ImageView green9_2;

    @FXML
    private ImageView professor_blue;

    @FXML
    private ImageView professor_blue1;

    @FXML
    private ImageView professor_blue2;

    @FXML
    private ImageView professor_green;

    @FXML
    private ImageView professor_green1;

    @FXML
    private ImageView professor_green2;

    @FXML
    private ImageView professor_purple;

    @FXML
    private ImageView professor_purple1;

    @FXML
    private ImageView professor_purple2;

    @FXML
    private ImageView professor_red;

    @FXML
    private ImageView professor_red1;

    @FXML
    private ImageView professor_red2;

    @FXML
    private ImageView professor_yellow;

    @FXML
    private ImageView professor_yellow1;

    @FXML
    private ImageView professor_yellow2;

    @FXML
    private ImageView purple0;

    @FXML
    private ImageView purple0_1;

    @FXML
    private ImageView purple0_2;

    @FXML
    private ImageView purple1;

    @FXML
    private ImageView purple1_1;

    @FXML
    private ImageView purple1_2;

    @FXML
    private ImageView purple2;

    @FXML
    private ImageView purple2_1;

    @FXML
    private ImageView purple2_2;

    @FXML
    private ImageView purple3;

    @FXML
    private ImageView purple3_1;

    @FXML
    private ImageView purple3_2;

    @FXML
    private ImageView purple4;

    @FXML
    private ImageView purple4_1;

    @FXML
    private ImageView purple4_2;

    @FXML
    private ImageView purple5;

    @FXML
    private ImageView purple5_1;

    @FXML
    private ImageView purple5_2;

    @FXML
    private ImageView purple6;

    @FXML
    private ImageView purple6_1;

    @FXML
    private ImageView purple6_2;

    @FXML
    private ImageView purple7;

    @FXML
    private ImageView purple7_1;

    @FXML
    private ImageView purple7_2;

    @FXML
    private ImageView purple8;

    @FXML
    private ImageView purple8_1;

    @FXML
    private ImageView purple8_2;

    @FXML
    private ImageView purple9;

    @FXML
    private ImageView purple9_1;

    @FXML
    private ImageView purple9_2;

    @FXML
    private ImageView red0;

    @FXML
    private ImageView red0_1;

    @FXML
    private ImageView red0_2;

    @FXML
    private ImageView red1;

    @FXML
    private ImageView red1_1;

    @FXML
    private ImageView red1_2;

    @FXML
    private ImageView red2;

    @FXML
    private ImageView red2_1;

    @FXML
    private ImageView red2_2;

    @FXML
    private ImageView red3;

    @FXML
    private ImageView red3_1;

    @FXML
    private ImageView red3_2;

    @FXML
    private ImageView red4;

    @FXML
    private ImageView red4_1;

    @FXML
    private ImageView red4_2;

    @FXML
    private ImageView red5;

    @FXML
    private ImageView red5_1;

    @FXML
    private ImageView red5_2;

    @FXML
    private ImageView red6;

    @FXML
    private ImageView red6_1;

    @FXML
    private ImageView red6_2;

    @FXML
    private ImageView red7;

    @FXML
    private ImageView red7_1;

    @FXML
    private ImageView red7_2;

    @FXML
    private ImageView red8;

    @FXML
    private ImageView red8_1;

    @FXML
    private ImageView red8_2;

    @FXML
    private ImageView red9;

    @FXML
    private ImageView red9_1;

    @FXML
    private ImageView red9_2;

    @FXML
    private ImageView scorebrd;

    @FXML
    private ImageView scorebrd1;

    @FXML
    private ImageView scorebrd2;

    @FXML
    private Circle tower0;

    @FXML
    private Circle tower0_1;

    @FXML
    private Circle tower0_2;

    @FXML
    private Circle tower1;

    @FXML
    private Circle tower1_1;

    @FXML
    private Circle tower1_2;

    @FXML
    private Circle tower2;

    @FXML
    private Circle tower2_1;

    @FXML
    private Circle tower2_2;

    @FXML
    private Circle tower3;

    @FXML
    private Circle tower3_1;

    @FXML
    private Circle tower3_2;

    @FXML
    private Circle tower4;

    @FXML
    private Circle tower4_1;

    @FXML
    private Circle tower4_2;

    @FXML
    private Circle tower5;

    @FXML
    private Circle tower5_1;

    @FXML
    private Circle tower5_2;

    @FXML
    private Circle tower6;

    @FXML
    private Circle tower6_1;

    @FXML
    private Circle tower6_2;

    @FXML
    private Circle tower7;

    @FXML
    private Circle tower7_1;

    @FXML
    private Circle tower7_2;

    @FXML
    private ImageView yellow0;

    @FXML
    private ImageView yellow0_1;

    @FXML
    private ImageView yellow0_2;

    @FXML
    private ImageView yellow1;

    @FXML
    private ImageView yellow1_1;

    @FXML
    private ImageView yellow1_2;

    @FXML
    private ImageView yellow2;

    @FXML
    private ImageView yellow2_1;

    @FXML
    private ImageView yellow2_2;

    @FXML
    private ImageView yellow3;

    @FXML
    private ImageView yellow3_1;

    @FXML
    private ImageView yellow3_2;

    @FXML
    private ImageView yellow4;

    @FXML
    private ImageView yellow4_1;

    @FXML
    private ImageView yellow4_2;

    @FXML
    private ImageView yellow5;

    @FXML
    private ImageView yellow5_1;

    @FXML
    private ImageView yellow5_2;

    @FXML
    private ImageView yellow6;

    @FXML
    private ImageView yellow6_1;

    @FXML
    private ImageView yellow6_2;

    @FXML
    private ImageView yellow7;

    @FXML
    private ImageView yellow7_1;

    @FXML
    private ImageView yellow7_2;

    @FXML
    private ImageView yellow8;

    @FXML
    private ImageView yellow8_1;

    @FXML
    private ImageView yellow8_2;

    @FXML
    private ImageView yellow9;

    @FXML
    private ImageView yellow9_1;

    @FXML
    private ImageView yellow9_2;


    private ImageView[] entrance = new ImageView[9];
    private ImageView[][] diningRoom = new ImageView[5][10];
    private ImageView[] professors = new ImageView[5];
    private ImageView[] entrance_1 = new ImageView[9];
    private ImageView[][] diningRoom_1 = new ImageView[5][10];
    private ImageView[] professors_1 = new ImageView[5];
    private ImageView[] entrance_2 = new ImageView[9];
    private ImageView[][] diningRoom_2 = new ImageView[5][10];
    private ImageView[] professors_2 = new ImageView[5];

    private int[] visibleStudents = new int[5];
    private int availableTowers = 0;
    private boolean[] entranceFree = new boolean[7];
    private Circle[] towers = new Circle[8];
    private Circle[] towers_1 = new Circle[8];
    private Circle[] towers_2 = new Circle[8];

    public ScoreboardSceneManager() {
        stage = new Stage();
        stage.initOwner(GraphicController.getActiveScene().getWindow());
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
        //collegamento main scoreboard
        entrance[0] = entrance0; entrance[1] = entrance1; entrance[2] = entrance2; entrance[3] = entrance3; entrance[4] = entrance4; entrance[5] = entrance5; entrance[6] = entrance6; entrance[7] = entrance7; entrance[8] = entrance8;
        towers[0] = tower0; towers[1] = tower1; towers[2] = tower2; towers[3] = tower3; towers[4] = tower4; towers[5] = tower5; towers[6] = tower6; towers[7] = tower7;
        professors[0] = professor_green; professors[1] = professor_red; professors[2] = professor_yellow; professors[3] = professor_purple; professors[4] = professor_blue;

        diningRoom[0][0] = green0; diningRoom[0][1] = green1; diningRoom[0][2] = green2; diningRoom[0][3] = green3; diningRoom[0][4] = green4; diningRoom[0][5] = green5; diningRoom[0][6] = green6; diningRoom[0][7] = green7; diningRoom[0][8] = green8; diningRoom[0][9] = green9;
        diningRoom[1][0] = red0; diningRoom[1][1] = red1; diningRoom[1][2] = red2; diningRoom[1][3] = red3; diningRoom[1][4] = red4; diningRoom[1][5] = red5; diningRoom[1][6] = red6; diningRoom[1][7] = red7; diningRoom[1][8] = red8; diningRoom[1][9] = red9;
        diningRoom[2][0] = yellow0; diningRoom[2][1] = yellow1; diningRoom[2][2] = yellow2; diningRoom[2][3] = yellow3; diningRoom[2][4] = yellow4; diningRoom[2][5] = yellow5; diningRoom[2][6] = yellow6; diningRoom[2][7] = yellow7; diningRoom[2][8] = yellow8; diningRoom[2][9] = yellow9;
        diningRoom[3][0] = purple0; diningRoom[3][1] = purple1; diningRoom[3][2] = purple2; diningRoom[3][3] = purple3; diningRoom[3][4] = purple4; diningRoom[3][5] = purple5; diningRoom[3][6] = purple6; diningRoom[3][7] = purple7; diningRoom[3][8] = purple8; diningRoom[3][9] = purple9;
        diningRoom[4][0] = blue0; diningRoom[4][1] = blue1; diningRoom[4][2] = blue2; diningRoom[4][3] = blue3; diningRoom[4][4] = blue4; diningRoom[4][5] = blue5; diningRoom[4][6] = blue6; diningRoom[4][7] = blue7; diningRoom[4][8] = blue8; diningRoom[4][9] = blue9;

        //collegamento scoreboard 1
        entrance_1[0] = entrance0_1; entrance_1[1] = entrance1_1; entrance_1[2] = entrance2_1; entrance_1[3] = entrance3_1; entrance_1[4] = entrance4_1; entrance_1[5] = entrance5_1; entrance_1[6] = entrance6_1; entrance_1[7] = entrance7_1; entrance_1[8] = entrance8_1;
        towers_1[0] = tower0_1; towers_1[1] = tower1_1; towers_1[2] = tower2_1; towers_1[3] = tower3_1; towers_1[4] = tower4_1; towers_1[5] = tower5_1; towers_1[6] = tower6_1; towers_1[7] = tower7_1;
        professors_1[0] = professor_green1; professors_1[1] = professor_red1; professors_1[2] = professor_yellow1; professors_1[3] = professor_purple1; professors_1[4] = professor_blue1;

        diningRoom_1[0][0] = green0_1; diningRoom_1[0][1] = green1_1; diningRoom_1[0][2] = green2_1; diningRoom_1[0][3] = green3_1; diningRoom_1[0][4] = green4_1; diningRoom_1[0][5] = green5_1; diningRoom_1[0][6] = green6_1; diningRoom_1[0][7] = green7_1; diningRoom_1[0][8] = green8_1; diningRoom_1[0][9] = green9_1;
        diningRoom_1[1][0] = red0_1; diningRoom_1[1][1] = red1_1; diningRoom_1[1][2] = red2_1; diningRoom_1[1][3] = red3_1; diningRoom_1[1][4] = red4_1; diningRoom_1[1][5] = red5_1; diningRoom_1[1][6] = red6_1; diningRoom_1[1][7] = red7_1; diningRoom_1[1][8] = red8_1; diningRoom_1[1][9] = red9_1;
        diningRoom_1[2][0] = yellow0_1; diningRoom_1[2][1] = yellow1_1; diningRoom_1[2][2] = yellow2_1; diningRoom_1[2][3] = yellow3_1; diningRoom_1[2][4] = yellow4_1; diningRoom_1[2][5] = yellow5_1; diningRoom_1[2][6] = yellow6_1; diningRoom_1[2][7] = yellow7_1; diningRoom_1[2][8] = yellow8_1; diningRoom_1[2][9] = yellow9_1;
        diningRoom_1[3][0] = purple0_1; diningRoom_1[3][1] = purple1_1; diningRoom_1[3][2] = purple2_1; diningRoom_1[3][3] = purple3_1; diningRoom_1[3][4] = purple4_1; diningRoom_1[3][5] = purple5_1; diningRoom_1[3][6] = purple6_1; diningRoom_1[3][7] = purple7_1; diningRoom_1[3][8] = purple8_1; diningRoom_1[3][9] = purple9_1;
        diningRoom_1[4][0] = blue0_1; diningRoom_1[4][1] = blue1_1; diningRoom_1[4][2] = blue2_1; diningRoom_1[4][3] = blue3_1; diningRoom_1[4][4] = blue4_1; diningRoom_1[4][5] = blue5_1; diningRoom_1[4][6] = blue6_1; diningRoom_1[4][7] = blue7_1; diningRoom_1[4][8] = blue8_1; diningRoom_1[4][9] = blue9_1;

        //collegamento scoreboard 2
        entrance_2[0] = entrance0_2; entrance_2[1] = entrance1_2; entrance_2[2] = entrance2_2; entrance_2[3] = entrance3_2; entrance_2[4] = entrance4_2; entrance_2[5] = entrance5_2; entrance_2[6] = entrance6_2; entrance_2[7] = entrance7_2; entrance_2[8] = entrance8_2;
        towers_2[0] = tower0_2; towers_2[1] = tower1_2; towers_2[2] = tower2_2; towers_2[3] = tower3_2; towers_2[4] = tower4_2; towers_2[5] = tower5_2; towers_2[6] = tower6_2; towers_2[7] = tower7_2;
        professors_2[0] = professor_green2; professors_2[1] = professor_red2; professors_2[2] = professor_yellow2; professors_2[3] = professor_purple2; professors_2[4] = professor_blue2;

        diningRoom_2[0][0] = green0_2; diningRoom_2[0][1] = green1_2; diningRoom_2[0][2] = green2_2; diningRoom_2[0][3] = green3_2; diningRoom_2[0][4] = green4_2; diningRoom_2[0][5] = green5_2; diningRoom_2[0][6] = green6_2; diningRoom_2[0][7] = green7_2; diningRoom_2[0][8] = green8_2; diningRoom_2[0][9] = green9_2;
        diningRoom_2[1][0] = red0_2; diningRoom_2[1][1] = red1_2; diningRoom_2[1][2] = red2_2; diningRoom_2[1][3] = red3_2; diningRoom_2[1][4] = red4_2; diningRoom_2[1][5] = red5_2; diningRoom_2[1][6] = red6_2; diningRoom_2[1][7] = red7_2; diningRoom_2[1][8] = red8_2; diningRoom_2[1][9] = red9_2;
        diningRoom_2[2][0] = yellow0_2; diningRoom_2[2][1] = yellow1_2; diningRoom_2[2][2] = yellow2_2; diningRoom_2[2][3] = yellow3_2; diningRoom_2[2][4] = yellow4_2; diningRoom_2[2][5] = yellow5_2; diningRoom_2[2][6] = yellow6_2; diningRoom_2[2][7] = yellow7_2; diningRoom_2[2][8] = yellow8_2; diningRoom_2[2][9] = yellow9_2;
        diningRoom_2[3][0] = purple0_2; diningRoom_2[3][1] = purple1_2; diningRoom_2[3][2] = purple2_2; diningRoom_2[3][3] = purple3_2; diningRoom_2[3][4] = purple4_2; diningRoom_2[3][5] = purple5_2; diningRoom_2[3][6] = purple6_2; diningRoom_2[3][7] = purple7_2; diningRoom_2[3][8] = purple8_2; diningRoom_2[3][9] = purple9_2;
        diningRoom_2[4][0] = blue0_2; diningRoom_2[4][1] = blue1_2; diningRoom_2[4][2] = blue2_2; diningRoom_2[4][3] = blue3_2; diningRoom_2[4][4] = blue4_2; diningRoom_2[4][5] = blue5_2; diningRoom_2[4][6] = blue6_2; diningRoom_2[4][7] = blue7_2; diningRoom_2[4][8] = blue8_2; diningRoom_2[4][9] = blue9_2;

        for(int i=0;i<5;i++)
            for(int k=0;k<10;k++){
                diningRoom[i][k].setDisable(true);
                diningRoom_1[i][k].setDisable(true);
                diningRoom_2[i][k].setDisable(true);
            }

        for (int i=0;i<5;i++)   visibleStudents[i] = 0;
        for (int i=0;i<7;i++)   entranceFree[i] = true;
    }

    //color: 0=black 1=white 2=grey
    public void setTowerColor(int color){
        Paint white = Color.WHITE;
        Paint black = Color.BLACK;
        Paint grey = Color.GREY;
        for(Circle c: towers){
            if(color == 0)
                c.setFill(black);
            if(color == 1)
                c.setFill(white);
            if(color == 2)
                c.setFill(grey);
        }
    }

    public void setTowerColor_1(int color){
        Paint white = Color.WHITE;
        Paint black = Color.BLACK;
        Paint grey = Color.GREY;
        for(Circle c: towers_1){
            if(color == 0)
                c.setFill(black);
            if(color == 1)
                c.setFill(white);
            if(color == 2)
                c.setFill(grey);
        }
    }

    public void setTowerColor_2(int color){
        Paint white = Color.WHITE;
        Paint black = Color.BLACK;
        Paint grey = Color.GREY;
        for(Circle c: towers_2){
            if(color == 0)
                c.setFill(black);
            if(color == 1)
                c.setFill(white);
            if(color == 2)
                c.setFill(grey);
        }
    }

    //color: 1/5
    public void addStudentOnDining(int color){
        diningRoom[color-1][visibleStudents[color-1]].setVisible(true);
        visibleStudents[color-1]++;
    }

    public void removeSudentfromDining(int color){
        diningRoom[color-1][visibleStudents[color-1]].setVisible(false);
        visibleStudents[color-1]--;
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

    public void clearEntrance(){
        for (int i=0;i<7;i++)   entranceFree[i] = true;
        for (int i=0;i<8;i++)   entrance[i].setImage(null);
    }

    public void clearEntranceSecondaryScoreboards(){
        System.out.println("clearEntranceSecondaryScoreboards");
        for (int i=0;i<8;i++)   entrance_1[i].setImage(null);
        for (int i=0;i<8;i++)   entrance_2[i].setImage(null);
    }

    //scbNumber: 1/2
    public void setSecondaryScoreboardEntrance(ArrayList<Integer> idList,int scbNumber){
        if(scbNumber == 1)
            for(int i=0;i<idList.size();i++)
                entrance_1[i].setImage(createImage(getColorFromId(idList.get(i))));
        if(scbNumber == 2)
            for(int i=0;i<idList.size();i++)
                entrance_2[i].setImage(createImage(getColorFromId(idList.get(i))));
    }

    public void removeStudentFromEntrance(int id){
        for(int i=0;i<7;i++)
            if(entrance[i].getId().equals(Integer.toString(id))){
                entrance[i].setImage(null);
                entranceFree[i] = true;
            }
    }

    public void setTowerNumber(int number){
        while(number>availableTowers){
            availableTowers++;
            towers[availableTowers-1].setVisible(true);
        }
        while(number<availableTowers){
            availableTowers--;
            towers[availableTowers-1].setVisible(false);
        }
    }

    public void openOptionMenu(String id){

    }

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

    public void closeScoreboard() {
        stage.close();
    }

    private int getColorFromId(int id){
        if(id-59>=0 && id-58<=25)
            return 1;
        if(id-59>=26 && id-58<=51)
            return 2;
        if(id-59>=52 && id-58<=77)
            return 3;
        if(id-59>=78 && id-58<=103)
            return 4;
        if(id-59>=104 && id-58<=129)
            return 5;

        return 0;
    }

    public void updateValues(GameSerialized gameSerialized) {
        SerializableScoreboard scoreboard = null;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(s.getNickname().equals(nickname))
                scoreboard = s;

        //aggiorna diningRoom
        while(scoreboard.getDiningGreenStudents()>visibleStudents[0])  addStudentOnDining(1);
        while(scoreboard.getDiningRedStudents()>visibleStudents[1])  addStudentOnDining(2);
        while(scoreboard.getDiningYellowStudents()>visibleStudents[2])  addStudentOnDining(3);
        while(scoreboard.getDiningPinkStudents()>visibleStudents[3])  addStudentOnDining(4);
        while(scoreboard.getDiningBlueStudents()>visibleStudents[4])  addStudentOnDining(5);
/*
        while(scoreboard.getDiningGreenStudents()<visibleStudents[0])  removeStudentFromDining(1);
        while(scoreboard.getDiningRedStudents()<visibleStudents[1])  removeStudentFromDining(2);
        while(scoreboard.getDiningYellowStudents()<visibleStudents[2])  removeStudentFromDining(3);
        while(scoreboard.getDiningPinkStudents()<visibleStudents[3])  removeStudentFromDining(4);
        while(scoreboard.getDiningBlueStudents()<visibleStudents[4])  removeStudentFromDining(5);
*/
        //aggiorna Professori
        for(PawnColors c: PawnColors.values()){
            if (scoreboard.availableProfessors().contains(c))
                professors[c.ordinal()].setVisible(true);
            else
                professors[c.ordinal()].setVisible(false);
        }

        //aggiorna Torri
        setTowerNumber(scoreboard.getTowerNumber());
        setTowerColor(scoreboard.getTowerColor().ordinal());

        clearEntrance();

        //aggiorna entrata
        for(int id: scoreboard.getEntranceId()){
            addStudentOnEntrance(getColorFromId(id),id);
        }

        //aggiorna entrata secondary scoreboard
        clearEntranceSecondaryScoreboards();
        int scbNumber = 1;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                setSecondaryScoreboardEntrance(s.getEntranceId(),scbNumber);
                scbNumber++;
            }

        for(int i=0;i<8;i++){
            towers_1[i].setVisible(false);
            towers_2[i].setVisible(false);
        }

        //aggiorna torri secondarie
        scbNumber = 1;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                if(scbNumber == 1){
                    setTowerColor_1(s.getTowerColor().ordinal());
                    for(int k=0;k<s.getTowerNumber();k++)
                        towers_1[k].setVisible(true);
                }if(scbNumber == 2){
                    setTowerColor_2(s.getTowerColor().ordinal());
                    for(int k=0;k<s.getTowerNumber();k++)
                        towers_2[k].setVisible(true);
                }
                scbNumber++;
            }

        for(int i=0;i<5;i++)
            for(int k=0;k<10;k++){
                diningRoom_1[i][k].setVisible(false);
                diningRoom_2[i][k].setVisible(false);
            }

        //aggiorna dining secondarie
        scbNumber = 1;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                if(scbNumber == 1){
                    for(int k=0;k<s.getDiningGreenStudents();k++)   diningRoom_1[0][k].setVisible(true);
                    for(int k=0;k<s.getDiningRedStudents();k++)   diningRoom_1[1][k].setVisible(true);
                    for(int k=0;k<s.getDiningYellowStudents();k++)   diningRoom_1[2][k].setVisible(true);
                    for(int k=0;k<s.getDiningPinkStudents();k++)   diningRoom_1[3][k].setVisible(true);
                    for(int k=0;k<s.getDiningBlueStudents();k++)   diningRoom_1[4][k].setVisible(true);
                }if(scbNumber == 2){
                    for(int k=0;k<s.getDiningGreenStudents();k++)   diningRoom_2[0][k].setVisible(true);
                    for(int k=0;k<s.getDiningRedStudents();k++)   diningRoom_2[1][k].setVisible(true);
                    for(int k=0;k<s.getDiningYellowStudents();k++)   diningRoom_2[2][k].setVisible(true);
                    for(int k=0;k<s.getDiningPinkStudents();k++)   diningRoom_2[3][k].setVisible(true);
                    for(int k=0;k<s.getDiningBlueStudents();k++)   diningRoom_2[4][k].setVisible(true);
                }
                scbNumber++;
            }

        for(int i=0;i<5;i++){
            professors_1[i].setVisible(false);
            professors_2[i].setVisible(false);
        }

        //aggiorna professors secondari
        scbNumber = 1;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                if(scbNumber == 1){
                    for(PawnColors c: PawnColors.values())
                        if (s.availableProfessors().contains(c))
                            professors_1[c.ordinal()].setVisible(true);
                }if(scbNumber == 2){
                    for(PawnColors c: PawnColors.values())
                        if (s.availableProfessors().contains(c))
                            professors_2[c.ordinal()].setVisible(true);
                }
                scbNumber++;
            }
    }
}
