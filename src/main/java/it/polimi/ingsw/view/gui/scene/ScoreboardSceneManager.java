package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.view.gui.GraphicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static it.polimi.ingsw.view.gui.GraphicController.nickname;

/**
 * This class represent the scoreboard on the view package.
 */
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

    @FXML
    private ImageView xScoreboard;

    @FXML
    private ImageView map;

    @FXML
    private ImageView coinOp1;

    @FXML
    private ImageView coinOp2;

    @FXML
    private Label labelCoinsOp1;

    @FXML
    private Label labelCoinsOp2;

    @FXML
    private MenuButton islandMenu;

    @FXML
    private Button diningButton;

    @FXML
    private Rectangle bgMap;

    @FXML
    private Label nicknameOp1;

    @FXML
    private Label nicknameOp2;



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
    private boolean[] entranceFree = new boolean[9];
    private Circle[] towers = new Circle[8];
    private Circle[] towers_1 = new Circle[8];
    private Circle[] towers_2 = new Circle[8];

    private Integer selEntranceStudentId = null;

    /**
     * Default constructor.
     */
    public ScoreboardSceneManager() {
        stage = new Stage();
        stage.initOwner(GraphicController.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
    }

    /**
     * Set the effect of the ImageView related to the id.
     * @param i ImageView.
     */
    public void setEffect(ImageView i){
        int id = Integer.parseInt(i.getId());
        if(id-59>=0 && id-59<=25)
            i.setEffect(new Glow(1));
        else if(id-59>=26 && id-59<=51)
            i.setEffect(new Glow(0.6));
        else if(id-59>=52 && id-59<=77)
            i.setEffect(new Glow(0.6));
        else if(id-59>=78 && id-59<=103)
            i.setEffect(new Glow(0.8));
        else if(id-59>=104 && id-59<=129)
            i.setEffect(new Glow(0.53));
    }

    /**
     * Get the student in the entrance related to a certain id.
     * @param id id correlated to the student.
     * @return the imageview associated.
     */
    public ImageView getEntranceFromId(String id){
        for(ImageView imageView: entrance)
            if(imageView.getId().equals(id))
                return imageView;
        return null;
    }

    /**
     * Enable the clicking on the students of the entrance.
     */
    public void enableEntrance(){
        for(int i=0;i<9;i++) {
            if (!entranceFree[i])
                entrance[i].setDisable(false);
            else
                entrance[i].setDisable(true);
        }
    }

    /**
     * Disable the clicking on the students of the entrance.
     */
    public void disableEntrance(){
        for(ImageView i:entrance)
            i.setDisable(true);
        islandMenu.setVisible(false);
        diningButton.setVisible(false);
        islandMenu.setDisable(true);
        diningButton.setDisable(true);
    }

    @FXML
    void click(MouseEvent event) {

    }

    /**
     * Menu shown after selecting a student on the entrance.
     * @param mouseEvent mouse event.
     */
    public void selectedEntrance(MouseEvent mouseEvent) {
        String idString = mouseEvent.getSource().toString().substring(13,16);
        if(idString.charAt(2) == ',')   idString = idString.substring(0,2);

        selEntranceStudentId = Integer.valueOf(idString);
        ImageView selEntranceStudent = getEntranceFromId(idString);

        islandMenu.setLayoutX(selEntranceStudent.getLayoutX()+93);
        islandMenu.setLayoutY(selEntranceStudent.getLayoutY()-34);
        diningButton.setLayoutX(selEntranceStudent.getLayoutX()+93);
        diningButton.setLayoutY(selEntranceStudent.getLayoutY());
        islandMenu.setVisible(true);
        diningButton.setVisible(true);
        islandMenu.setDisable(false);
        diningButton.setDisable(false);
    }

    /**
     * Effect of the students of the entrance while mouse entering.
     * @param event mouse event.
     */
    @FXML
    void in(MouseEvent event) {
        String idString = event.getSource().toString().substring(13,16);
        if(idString.charAt(2) == ',')   idString = idString.substring(0,2);

        setEffect(getEntranceFromId(idString));
    }

    /**
     * Effect of the students of the entrance while mouse exiting.
     * @param event mouse event.
     */
    @FXML
    void out(MouseEvent event) {
        for(ImageView i: entrance)
            i.setEffect(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //collegamento main scoreboard
        entrance[0] = entrance0; entrance[1] = entrance1; entrance[2] = entrance2; entrance[3] = entrance3; entrance[4] = entrance4; entrance[5] = entrance5; entrance[6] = entrance6; entrance[7] = entrance7; entrance[8] = entrance8;
        towers[0] = tower0; towers[1] = tower1; towers[2] = tower2; towers[3] = tower3; towers[4] = tower4; towers[5] = tower5; towers[6] = tower6; towers[7] = tower7;
        professors[0] = professor_red; professors[1] = professor_blue; professors[2] = professor_green; professors[3] = professor_yellow; professors[4] = professor_purple;

        diningRoom[0][0] = green0; diningRoom[0][1] = green1; diningRoom[0][2] = green2; diningRoom[0][3] = green3; diningRoom[0][4] = green4; diningRoom[0][5] = green5; diningRoom[0][6] = green6; diningRoom[0][7] = green7; diningRoom[0][8] = green8; diningRoom[0][9] = green9;
        diningRoom[1][0] = red0; diningRoom[1][1] = red1; diningRoom[1][2] = red2; diningRoom[1][3] = red3; diningRoom[1][4] = red4; diningRoom[1][5] = red5; diningRoom[1][6] = red6; diningRoom[1][7] = red7; diningRoom[1][8] = red8; diningRoom[1][9] = red9;
        diningRoom[2][0] = yellow0; diningRoom[2][1] = yellow1; diningRoom[2][2] = yellow2; diningRoom[2][3] = yellow3; diningRoom[2][4] = yellow4; diningRoom[2][5] = yellow5; diningRoom[2][6] = yellow6; diningRoom[2][7] = yellow7; diningRoom[2][8] = yellow8; diningRoom[2][9] = yellow9;
        diningRoom[3][0] = purple0; diningRoom[3][1] = purple1; diningRoom[3][2] = purple2; diningRoom[3][3] = purple3; diningRoom[3][4] = purple4; diningRoom[3][5] = purple5; diningRoom[3][6] = purple6; diningRoom[3][7] = purple7; diningRoom[3][8] = purple8; diningRoom[3][9] = purple9;
        diningRoom[4][0] = blue0; diningRoom[4][1] = blue1; diningRoom[4][2] = blue2; diningRoom[4][3] = blue3; diningRoom[4][4] = blue4; diningRoom[4][5] = blue5; diningRoom[4][6] = blue6; diningRoom[4][7] = blue7; diningRoom[4][8] = blue8; diningRoom[4][9] = blue9;

        //collegamento scoreboard 1
        entrance_1[0] = entrance0_1; entrance_1[1] = entrance1_1; entrance_1[2] = entrance2_1; entrance_1[3] = entrance3_1; entrance_1[4] = entrance4_1; entrance_1[5] = entrance5_1; entrance_1[6] = entrance6_1; entrance_1[7] = entrance7_1; entrance_1[8] = entrance8_1;
        towers_1[0] = tower0_1; towers_1[1] = tower1_1; towers_1[2] = tower2_1; towers_1[3] = tower3_1; towers_1[4] = tower4_1; towers_1[5] = tower5_1; towers_1[6] = tower6_1; towers_1[7] = tower7_1;
        professors_1[0] = professor_red1; professors_1[1] = professor_blue1; professors_1[2] = professor_green1; professors_1[3] = professor_yellow1; professors_1[4] = professor_purple1;

        diningRoom_1[0][0] = green0_1; diningRoom_1[0][1] = green1_1; diningRoom_1[0][2] = green2_1; diningRoom_1[0][3] = green3_1; diningRoom_1[0][4] = green4_1; diningRoom_1[0][5] = green5_1; diningRoom_1[0][6] = green6_1; diningRoom_1[0][7] = green7_1; diningRoom_1[0][8] = green8_1; diningRoom_1[0][9] = green9_1;
        diningRoom_1[1][0] = red0_1; diningRoom_1[1][1] = red1_1; diningRoom_1[1][2] = red2_1; diningRoom_1[1][3] = red3_1; diningRoom_1[1][4] = red4_1; diningRoom_1[1][5] = red5_1; diningRoom_1[1][6] = red6_1; diningRoom_1[1][7] = red7_1; diningRoom_1[1][8] = red8_1; diningRoom_1[1][9] = red9_1;
        diningRoom_1[2][0] = yellow0_1; diningRoom_1[2][1] = yellow1_1; diningRoom_1[2][2] = yellow2_1; diningRoom_1[2][3] = yellow3_1; diningRoom_1[2][4] = yellow4_1; diningRoom_1[2][5] = yellow5_1; diningRoom_1[2][6] = yellow6_1; diningRoom_1[2][7] = yellow7_1; diningRoom_1[2][8] = yellow8_1; diningRoom_1[2][9] = yellow9_1;
        diningRoom_1[3][0] = purple0_1; diningRoom_1[3][1] = purple1_1; diningRoom_1[3][2] = purple2_1; diningRoom_1[3][3] = purple3_1; diningRoom_1[3][4] = purple4_1; diningRoom_1[3][5] = purple5_1; diningRoom_1[3][6] = purple6_1; diningRoom_1[3][7] = purple7_1; diningRoom_1[3][8] = purple8_1; diningRoom_1[3][9] = purple9_1;
        diningRoom_1[4][0] = blue0_1; diningRoom_1[4][1] = blue1_1; diningRoom_1[4][2] = blue2_1; diningRoom_1[4][3] = blue3_1; diningRoom_1[4][4] = blue4_1; diningRoom_1[4][5] = blue5_1; diningRoom_1[4][6] = blue6_1; diningRoom_1[4][7] = blue7_1; diningRoom_1[4][8] = blue8_1; diningRoom_1[4][9] = blue9_1;

        //collegamento scoreboard 2
        entrance_2[0] = entrance0_2; entrance_2[1] = entrance1_2; entrance_2[2] = entrance2_2; entrance_2[3] = entrance3_2; entrance_2[4] = entrance4_2; entrance_2[5] = entrance5_2; entrance_2[6] = entrance6_2; entrance_2[7] = entrance7_2; entrance_2[8] = entrance8_2;
        towers_2[0] = tower0_2; towers_2[1] = tower1_2; towers_2[2] = tower2_2; towers_2[3] = tower3_2; towers_2[4] = tower4_2; towers_2[5] = tower5_2; towers_2[6] = tower6_2; towers_2[7] = tower7_2;
        professors_2[0] = professor_red2; professors_2[1] = professor_blue2; professors_2[2] = professor_green2; professors_2[3] = professor_yellow2; professors_2[4] = professor_purple2;

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

        for(int j=0;j<12;j++) {
            int finalJ = j;
            islandMenu.getItems().get(j).setOnAction(event -> islandMessage(finalJ+1));
        }

        disableEntrance();
    }

    /**
     * Notify the observer about moving a student to an island.
     * @param number number of island to move the student.
     */
    public void islandMessage(int number){
        islandMenu.setDisable(true);
        diningButton.setDisable(true);
        islandMenu.setVisible(false);
        diningButton.setVisible(false);
        for(ImageView i:entrance)
            i.setDisable(true);
        new Thread( () -> notifyObserver(obs -> obs.onUpdateMoveStudent(new StudentDisc(selEntranceStudentId,PawnColors.values()[getColorFromId(selEntranceStudentId)-1]), 1, number))).start();
    }

    /**
     * Setter method.
     * @param image image to be set.
     */
    public void setMap(Image image){
        map.setImage(image);
    }

    /**
     * Set the tower color of main scoreboard.
     * @param color of the tower.
     */
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

    /**
     * Set the tower color of main scoreboard opponent 1.
     * @param color of the tower.
     */
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

    /**
     * Set the tower color of main scoreboard opponent 2.
     * @param color of the tower.
     */
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


    /**
     * Add student to dining room.
     * @param color of the student to move.
     */
    //color: 1/5
    public void addStudentOnDining(int color){
        diningRoom[color-1][visibleStudents[color-1]].setVisible(true);
        visibleStudents[color-1]++;
    }

    /**
     * Remove student from dining room.
     * @param color of the student to remove.
     */
    public void removeSudentfromDining(int color){
        diningRoom[color-1][visibleStudents[color-1]].setVisible(false);
        visibleStudents[color-1]--;
    }

    /**
     * Add student to entrance.
     * @param color of the student to add.
     */
    public void addStudentOnEntrance(int color,int id){
        for (int i=0;i<9;i++)
            if(entranceFree[i]){
                entranceFree[i] = false;
                ImageView temp = entrance[i];
                entrance[i].setImage(createImage(color));
                entrance[i].setId(Integer.toString(id));
                entrance[i].setMouseTransparent(false);

                if(i!=8)
                    for(int j=i+1;j<9;j++)
                        entrance[j].setMouseTransparent(true);
                return;
            }
    }

    /**
     * Clear all the entrance room of main scoreboard.
     */
    public void clearEntrance(){
        for (int i=0;i<9;i++)   entranceFree[i] = true;
        for (int i=0;i<9;i++)   entrance[i].setImage(null);
    }

    /**
     * Clear all the entrance room of scoreboard opponent 1.
     */
    public void clearEntranceSecondaryScoreboards(){
        for (int i=0;i<8;i++)   entrance_1[i].setImage(null);
        for (int i=0;i<8;i++)   entrance_2[i].setImage(null);
    }

    /**
     * Clear all the entrance room of scoreboard opponent 2.
     */
    //scbNumber: 1/2
    public void setSecondaryScoreboardEntrance(ArrayList<Integer> idList,int scbNumber){
        if(scbNumber == 1)
            for(int i=0;i<idList.size();i++)
                entrance_1[i].setImage(createImage(getColorFromId(idList.get(i))));
        if(scbNumber == 2)
            for(int i=0;i<idList.size();i++)
                entrance_2[i].setImage(createImage(getColorFromId(idList.get(i))));
    }

    /**
     * Remove student from entrance room.
     * @param id id of the student to remove.
     */
    public void removeStudentFromEntrance(int id){
        for(int i=0;i<7;i++)
            if(entrance[i].getId().equals(Integer.toString(id))){
                entrance[i].setImage(null);
                entranceFree[i] = true;
            }
    }

    /**
     * Set visible a certain tower.
     * @param number number of the tower to make visible.
     */
    public void setTowerNumber(int number){
        for(int k=0;k<8;k++)
            towers[k].setVisible(false);
        for(int k=0;k<number;k++)
            towers[k].setVisible(true);
    }

    /**
     * Create an image about StudentPawns.
     * @param color color of the pawn.
     * @return the image created.
     */
    public Image createImage(int color){
        Image image = null;

        switch (color){
            case 3: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/1_Verde.png")); break;
            case 1: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/2_Rosso.png")); break;
            case 4: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/3_Giallo.png")); break;
            case 5: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/4_Viola.png")); break;
            case 2: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/2D/5_Azzurro.png")); break;
        }
        return image;
    }

    /**
     * This method show the scoreboards.
     */
    public void showScoreboards() {
        stage.showAndWait();
    }

    /**
     * Set the scene.
     * @param scene scene to be set.
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Close the scoreboard.
     */
    @FXML
    public void closeScoreboard() {
        stage.close();
    }

    /**
     * Get the color from a student id.
     * @param id id of the studentPawn.
     * @return the color of the student.
     */
    private int getColorFromId(int id){
        if(id-59>=0 && id-59<=25)
            return 1;
        else if(id-59>=26 && id-59<=51)
            return 2;
        else if(id-59>=52 && id-59<=77)
            return 3;
        else if(id-59>=78 && id-59<=103)
            return 4;
        else if(id-59>=104 && id-59<=129)
            return 5;
        else System.out.println("id mancante: "+id);

        return 0;
    }

    /**
     * Translator method from ordinal a certain number.
     * @param ordinal ordinal.
     * @return the number translated.
     */
    public int transformOrdinal(int ordinal){
        if(ordinal == 0)
            return 1;
        if(ordinal == 1)
            return 4;
        if(ordinal == 2)
            return 0;
        if(ordinal == 3)
            return 2;
        if(ordinal == 4)
            return 3;
        return -1;
    }

    /**
     * Reset the dining room of the scoreboard.
     */
    public void clearScoreboard(){
        for(int i=0;i<5;i++)
            for(int k=0;k<10;k++)
                diningRoom[i][k].setVisible(false);
        for (int i=0;i<5;i++)   visibleStudents[i] = 0;
    }

    /**
     * Update/correct the values of the scoreboard.
     * @param gameSerialized game data.
     */
    public void updateValues(GameSerialized gameSerialized) {
        for(int id=59;id<=188;id++)   getColorFromId(id);
        SerializableScoreboard scoreboard = null;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(s.getNickname().equals(nickname))
                scoreboard = s;

        clearScoreboard();

        //update diningRoom
        while(scoreboard.getDiningGreenStudents()>visibleStudents[0])  addStudentOnDining(1);
        while(scoreboard.getDiningRedStudents()>visibleStudents[1])  addStudentOnDining(2);
        while(scoreboard.getDiningYellowStudents()>visibleStudents[2])  addStudentOnDining(3);
        while(scoreboard.getDiningPinkStudents()>visibleStudents[3])  addStudentOnDining(4);
        while(scoreboard.getDiningBlueStudents()>visibleStudents[4])  addStudentOnDining(5);

        //update professors
        for(int i=0;i<5;i++)
            professors[i].setVisible(false);
        for(PawnColors c: scoreboard.availableProfessors()){
            professors[c.ordinal()].setVisible(true);
        }

        //update towers
        setTowerNumber(scoreboard.getTowerNumber());
        setTowerColor(scoreboard.getTowerColor().ordinal());

        clearEntrance();

        //update entrance room
        for(int id: scoreboard.getEntranceId()){
            addStudentOnEntrance(getColorFromId(id),id);
        }

        //update entrance room of opponent players
        clearEntranceSecondaryScoreboards();
        int scbNumber = 1;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                if(scbNumber==1) {
                    labelCoinsOp1.setText("X "+s.getCoins());
                    nicknameOp1.setText(s.getNickname().toUpperCase(Locale.ROOT));
                    nicknameOp2.setText(null);
                }
                else {
                    labelCoinsOp2.setText("X "+s.getCoins());
                    nicknameOp2.setText(s.getNickname().toUpperCase(Locale.ROOT));
                }
                setSecondaryScoreboardEntrance(s.getEntranceId(),scbNumber);
                scbNumber++;
            }

        for(int i=0;i<8;i++){
            towers_1[i].setVisible(false);
            towers_2[i].setVisible(false);
        }

        //update entrance room
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

        //update dining room of opponent players
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

        //update professor opponent players
        scbNumber = 1;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                if(scbNumber == 1){
                    for(PawnColors c: s.availableProfessors()){
                        professors_1[c.ordinal()].setVisible(true);
                    }

                }if(scbNumber == 2){
                    for(PawnColors c: s.availableProfessors()){
                        professors_2[c.ordinal()].setVisible(true);
                    }

                }
                scbNumber++;
            }

        if( scbNumber == 2)
            xScoreboard.setVisible(true);
    }

    /**
     * Close the scoreboard.
     * @param mouseEvent mouse event.
     */
    public void toMap(MouseEvent mouseEvent) {
        closeScoreboard();
    }

    /**
     * Effect while mouse entering map ImageView.
     * @param mouseEvent mouse event.
     */
    public void inMap(MouseEvent mouseEvent) {
        bgMap.setEffect(new Glow());
        map.setEffect(new Glow());
    }

    /**
     * Effect while mouse exiting map ImageView.
     * @param mouseEvent mouse event.
     */
    public void outMap(MouseEvent mouseEvent) {
        bgMap.setEffect(null);
        map.setEffect(null);
    }

    /**
     * Notify observer to move students to dining room.
     * @param actionEvent mouse event.
     */
    public void selectedToDining(ActionEvent actionEvent) {
        islandMenu.setDisable(true);
        diningButton.setDisable(true);
        islandMenu.setVisible(false);
        diningButton.setVisible(false);
        for(ImageView i:entrance)
            i.setDisable(true);
        new Thread( () -> notifyObserver(obs -> obs.onUpdateMoveStudent(new StudentDisc(selEntranceStudentId,PawnColors.values()[getColorFromId(selEntranceStudentId)-1]), 0, -1))).start();
    }

}
