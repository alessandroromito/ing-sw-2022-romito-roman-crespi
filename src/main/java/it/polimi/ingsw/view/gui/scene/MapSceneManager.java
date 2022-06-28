package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.view.gui.GraphicController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.List;
import java.util.random.RandomGenerator;

import static it.polimi.ingsw.view.gui.GraphicController.nickname;
import static java.lang.Math.abs;

public class MapSceneManager extends ViewObservable implements SceneManagerInterface {

    @FXML
    private Rectangle bg;

    @FXML
    private Rectangle bgCards;

    @FXML
    private ImageView switchHandMap;

    @FXML
    private ImageView toScoreboard;

    @FXML
    private ImageView xButton;

    @FXML
    private ImageView assistentCard0;

    @FXML
    private ImageView assistentCard1;

    @FXML
    private ImageView assistentCard2;

    @FXML
    private ImageView assistentCard3;

    @FXML
    private ImageView assistentCard4;

    @FXML
    private ImageView assistentCard5;

    @FXML
    private ImageView assistentCard6;

    @FXML
    private ImageView assistentCard7;

    @FXML
    private ImageView assistentCard8;

    @FXML
    private ImageView assistentCard9;

    @FXML
    private ImageView card1;

    @FXML
    private ImageView card2;

    @FXML
    private ImageView card3;

    @FXML
    private ImageView cloud1;

    @FXML
    private ImageView cloud2;

    @FXML
    private ImageView island1;

    @FXML
    private ImageView island10;

    @FXML
    private ImageView island11;

    @FXML
    private ImageView island12;

    @FXML
    private ImageView island2;

    @FXML
    private ImageView island3;

    @FXML
    private ImageView island4;

    @FXML
    private ImageView island5;

    @FXML
    private ImageView island6;

    @FXML
    private ImageView island7;

    @FXML
    private ImageView island8;

    @FXML
    private ImageView island9;

    @FXML
    private ImageView bridge_10_11;

    @FXML
    private ImageView bridge_11_12;

    @FXML
    private ImageView bridge_1_12;

    @FXML
    private ImageView bridge_1_2;

    @FXML
    private ImageView bridge_2_3;

    @FXML
    private ImageView bridge_3_4;

    @FXML
    private ImageView bridge_4_5;

    @FXML
    private ImageView bridge_5_6;

    @FXML
    private ImageView bridge_6_7;

    @FXML
    private ImageView bridge_7_8;

    @FXML
    private ImageView bridge_8_9;

    @FXML
    private ImageView bridge_9_10;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView towerBase0;

    @FXML
    private ImageView towerBase1;

    @FXML
    private ImageView towerBase10;

    @FXML
    private ImageView towerBase11;

    @FXML
    private ImageView towerBase2;

    @FXML
    private ImageView towerBase3;

    @FXML
    private ImageView towerBase4;

    @FXML
    private ImageView towerBase5;

    @FXML
    private ImageView towerBase6;

    @FXML
    private ImageView towerBase7;

    @FXML
    private ImageView towerBase8;

    @FXML
    private ImageView towerBase9;

    @FXML
    private ImageView cloud1Student0;

    @FXML
    private ImageView cloud1Student1;

    @FXML
    private ImageView cloud1Student2;

    @FXML
    private ImageView cloud1Student3;

    @FXML
    private ImageView cloud2Student0;

    @FXML
    private ImageView cloud2Student1;

    @FXML
    private ImageView cloud2Student2;

    @FXML
    private ImageView cloud2Student3;

    @FXML
    private ImageView motherNature;

    @FXML
    private ImageView coin;

    @FXML
    private Label labelHand;

    @FXML
    private Label labelScrbd;

    @FXML
    private Label labelCoins;

    @FXML
    private Label previous1lbl;

    @FXML
    private Label nicknameOp1;

    @FXML
    private Rectangle bgOpCard1;

    @FXML
    private ImageView assistentCardOpponent1;

    @FXML
    private Label previous2lbl;

    @FXML
    private Label nicknameOp2;

    @FXML
    private Rectangle bgOpCard2;

    @FXML
    private ImageView assistentCardOpponent2;


    private double r = 22;
    private double rIsl;


    GameSerialized gameSerialized;
    List<AssistantCard> assistantCardsList = new ArrayList<>();
    private boolean opCard1 = false;
    private boolean opCard2 = false;
    ScoreboardSceneManager scoreboardSceneManager = null;

    public void setPlayedAssistantCardsList(List<AssistantCard> playedAssistantCardsList) {
        this.playedAssistantCardsList = playedAssistantCardsList;
    }

    List<AssistantCard> playedAssistantCardsList = new ArrayList<>();
    ArrayList<Cloud> clouds = new ArrayList<>();
    ImageView[] cloudStudents1 = new ImageView[4];
    ImageView[] cloudStudents2 = new ImageView[4];
    ImageView[] towerBases = new ImageView[12];
    ImageView[] assistantCards = new ImageView[10];
    ArrayList<ImageView>[] students = new ArrayList[12];
    ArrayList<Point>[] islands = new ArrayList[12];
    Point[] islandsPosCentre = new Point[12];
    Point[] motherNaturePoses = new Point[12];
    int motherNaturePos = -1;
    private boolean switchMotherNature = false;

    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = graphicController;
    }

    private GraphicController graphicController = null;

    @FXML
    public void initialize() {
        System.out.println("start initialize fxml");

        for(int i=0;i<12;i++)   students[i] = new ArrayList<ImageView>();

        islandsPosCentre[0] = new Point(island1.getLayoutX()+island1.getFitWidth()/2,island1.getLayoutY()+island1.getFitHeight()/2);
        islandsPosCentre[1] = new Point(island2.getLayoutX()+island2.getFitWidth()/2,island2.getLayoutY()+island2.getFitHeight()/2);
        islandsPosCentre[2] = new Point(island3.getLayoutX()+island3.getFitWidth()/2,island3.getLayoutY()+island3.getFitHeight()/2);
        islandsPosCentre[3] = new Point(island4.getLayoutX()+island4.getFitWidth()/2,island4.getLayoutY()+island4.getFitHeight()/2);
        islandsPosCentre[4] = new Point(island5.getLayoutX()+island5.getFitWidth()/2,island5.getLayoutY()+island5.getFitHeight()/2);
        islandsPosCentre[5] = new Point(island6.getLayoutX()+island6.getFitWidth()/2,island6.getLayoutY()+island6.getFitHeight()/2);
        islandsPosCentre[6] = new Point(island7.getLayoutX()+island7.getFitWidth()/2,island7.getLayoutY()+island7.getFitHeight()/2);
        islandsPosCentre[7] = new Point(island8.getLayoutX()+island8.getFitWidth()/2,island8.getLayoutY()+island8.getFitHeight()/2);
        islandsPosCentre[8] = new Point(island9.getLayoutX()+island9.getFitWidth()/2,island9.getLayoutY()+island9.getFitHeight()/2);
        islandsPosCentre[9] = new Point(island10.getLayoutX()+island10.getFitWidth()/2,island10.getLayoutY()+island10.getFitHeight()/2);
        islandsPosCentre[10] = new Point(island11.getLayoutX()+island11.getFitWidth()/2,island11.getLayoutY()+island11.getFitHeight()/2);
        islandsPosCentre[11] = new Point(island12.getLayoutX()+island12.getFitWidth()/2,island12.getLayoutY()+island12.getFitHeight()/2);

        rIsl = (island1.getFitHeight()+17)/2;

        for(int i=0;i<12;i++)
            islands[i] = new ArrayList<Point>();
        disableIslands();

        assistantCards[0] = assistentCard0; assistantCards[1] = assistentCard1; assistantCards[2] = assistentCard2; assistantCards[3] = assistentCard3; assistantCards[4] = assistentCard4; assistantCards[5] = assistentCard5; assistantCards[6] = assistentCard6; assistantCards[7] = assistentCard7; assistantCards[8] = assistentCard8; assistantCards[9] = assistentCard9;

        towerBases[0] = towerBase0; towerBases[1] = towerBase1; towerBases[2] = towerBase2; towerBases[3] = towerBase3; towerBases[4] = towerBase4; towerBases[5] = towerBase5; towerBases[6] = towerBase6; towerBases[7] = towerBase7; towerBases[8] = towerBase8; towerBases[9] = towerBase9; towerBases[10] = towerBase10; towerBases[11] = towerBase11;

        motherNaturePoses[0] = new Point(1492,536); motherNaturePoses[1] = new Point(1455,684); motherNaturePoses[2] = new Point(1277,684); motherNaturePoses[3] = new Point(1038,724); motherNaturePoses[4] = new Point(688,727); motherNaturePoses[5] = new Point(357,684); motherNaturePoses[6] = new Point(276,578); motherNaturePoses[7] = new Point(365,331); motherNaturePoses[8] = new Point(504,317); motherNaturePoses[9] = new Point(780,264); motherNaturePoses[10] = new Point(1252,317); motherNaturePoses[11] = new Point(1453,324);

        cloudStudents1[0] = cloud1Student0; cloudStudents1[1] = cloud1Student1; cloudStudents1[2] = cloud1Student2; cloudStudents1[3] = cloud1Student3;
        cloudStudents2[0] = cloud2Student0; cloudStudents2[1] = cloud2Student1; cloudStudents2[2] = cloud2Student2; cloudStudents2[3] = cloud2Student3;

        disableClouds();

        for(int k=0;k<10;k++)
        assistantCards[k].setViewOrder(-1);
        xButton.setViewOrder(-1);
        
        assistentCardOpponent1.setViewOrder(-1);    previous1lbl.setViewOrder(-1);  bgOpCard1.setViewOrder(-1); nicknameOp1.setViewOrder(-1);
        assistentCardOpponent2.setViewOrder(-1);    previous2lbl.setViewOrder(-1);  bgOpCard2.setViewOrder(-1); nicknameOp2.setViewOrder(-1);

        System.out.println("fine initialize fxml");
    }

    public void fullscreen(){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setFullScreen(true);
    }


    public void initializeCharacterCards(int[] number){
        System.out.println("initialize characters");
        Image image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front"+number[0]+".jpg"));
        card1.setImage(image);
        card1.setId(Integer.toString(number[0]));
        image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front"+number[1]+".jpg"));
        card2.setImage(image);
        card2.setId(Integer.toString(number[1]));
        image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front"+number[2]+".jpg"));
        card3.setImage(image);
        card3.setId(Integer.toString(number[2]));
    }

//color: 1/5  isalnd:0/11
    public void addStudentToIsland(int color,int id,int island){
        Image image = null;
        ImageView student = new ImageView();

        switch (color){
            case 1: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/1_VerdeWood.png")); break;
            case 2: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/2_RossoWood.png")); break;
            case 3: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/3_GialloWood.png")); break;
            case 4: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/4_ViolaWood.png")); break;
            case 5: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/5_AzzurroWood.png")); break;
        }
        student.setImage(image);
        DropShadow dr = new DropShadow(); dr.setWidth(15); dr.setHeight(15);
        student.setOnMouseExited(ex -> student.setEffect(dr));

        switch(color){
            case 1: student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.55))); break;
            case 2: student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.23))); break;
            case 3: student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.65))); break;
            case 4: student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.48))); break;
            case 5: student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.55))); break;
        }

        student.setEffect(dr);
        student.setId(Integer.toString(id));
        students[island].add(student);

        student.setFitHeight(r*2);
        student.setFitWidth(r*2);
        student.setDisable(true);

        Point pos = findCoord(island);

        student.setX(islandsPosCentre[island].getX()-rIsl+pos.getX()-rIsl*0.1);
        student.setY(islandsPosCentre[island].getY()-rIsl+pos.getY()-rIsl*0.1);
        student.setLayoutX(r/2);

        pane.getChildren().addAll(student);
    }

    //number: 1/2
    public void addStudentsToCloud(Cloud cloud, int number){
        clouds.add(cloud);
        Image image = null;

        for(int i=0;i<cloud.getCloudStudents().size();i++) {
            StudentDisc s = cloud.getCloudStudents().get(i);
            switch (s.getColorInt()) {
                case 2:
                    image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/1_VerdeWood.png"));
                    break;
                case 0:
                    image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/2_RossoWood.png"));
                    break;
                case 3:
                    image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/3_GialloWood.png"));
                    break;
                case 4:
                    image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/4_ViolaWood.png"));
                    break;
                case 1:
                    image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/5_AzzurroWood.png"));
                    break;
            }

            if(number == 1) {
                cloudStudents1[i].setImage(image);
                DropShadow dr = new DropShadow();
                dr.setWidth(15);
                dr.setHeight(15);

                cloudStudents1[i].setEffect(dr);
                cloudStudents1[i].setId(Integer.toString(s.getID()));
            }else{
                cloudStudents2[i].setImage(image);
                DropShadow dr = new DropShadow();
                dr.setWidth(15);
                dr.setHeight(15);

                cloudStudents2[i].setEffect(dr);
                cloudStudents2[i].setId(Integer.toString(s.getID()));
            }
        }
    }

    public void chooseCharacterCard() {
        //da inserire la notify e da implementare
    }

    public void removeStudent(int id){
        for(int i=0;i<12;i++)
            for(ImageView p: students[i])
                if(p.getId().equals(Integer.toString(id)))
                    pane.getChildren().remove(p);
    }

    private void darkAll() {
        island1.setEffect(null);
        island2.setEffect(null);
        island3.setEffect(null);
        island4.setEffect(null);
        island5.setEffect(null);
        island6.setEffect(null);
        island7.setEffect(null);
        island8.setEffect(null);
        island9.setEffect(null);
        island10.setEffect(null);
        island11.setEffect(null);
        island12.setEffect(null);

    }

    //island varies form 0 to 11
    public Point findCoord(int island){
        Point p;
        RandomGenerator randomGenerator = new Random();

        for(int i=0;i<1000000;i++){
            boolean valid = true;
            p = new Point(randomGenerator.nextDouble(rIsl*2-r*2-rIsl*0.1),randomGenerator.nextDouble(rIsl*2-r*2-rIsl*0.1));

            if((p.getX()-(rIsl*2-r*2)/2)*(p.getX()-(rIsl*2-r*2)/2)+(p.getY()-(rIsl*2-r*2)/2)*(p.getY()-(rIsl*2-r*2)/2) > (rIsl/2)*(rIsl/2)+rIsl*rIsl*0.2){
                valid = false;
            }

            for(Point rl:islands[island]){
                if((abs(p.getX()-rl.getX())<r*2) && (abs(p.getY()-rl.getY())<r*2))
                    valid = false;
            }

            if(valid){
                islands[island].add(p);
                return p;
            }
        }
        p = new Point(randomGenerator.nextDouble(rIsl*0.8),randomGenerator.nextDouble(rIsl*0.8));
        while((p.getX()-(rIsl*2-r*2)/2)*(p.getX()-(rIsl*2-r*2)/2)+(p.getY()-(rIsl*2-r*2)/2)*(p.getY()-(rIsl*2-r*2)/2) > (rIsl/2)*(rIsl/2)+rIsl*rIsl*0.2)
            p = new Point(randomGenerator.nextDouble(rIsl*2-r*2-rIsl*0.1),randomGenerator.nextDouble(rIsl*2-r*2-rIsl*0.1));
        return p;
    }

    public void setMotherNaturePose(int island){
        motherNature.setLayoutX(motherNaturePoses[island].getX());
        motherNature.setLayoutY(motherNaturePoses[island].getY());
        motherNature.setVisible(true);
        motherNaturePos = island;
    }

    public void enableMotherNature(int maxSteps) {
        switchMotherNature = true;
        for (int i = 1; i <= maxSteps; i++) {
            if (i + motherNaturePos >= 12)
                enableSingleIsland(i+motherNaturePos-11);
            else
                enableSingleIsland(i+motherNaturePos+1);
        }
    }

    public void enableSingleIsland(int number) {
        switch (number) {
            case 1:
                island1.setDisable(false);
                break;
            case 2:
                island2.setDisable(false);
                break;
            case 3:
                island3.setDisable(false);
                break;
            case 4:
                island4.setDisable(false);
                break;
            case 5:
                island5.setDisable(false);
                break;
            case 6:
                island6.setDisable(false);
                break;
            case 7:
                island7.setDisable(false);
                break;
            case 8:
                island8.setDisable(false);
                break;
            case 9:
                island9.setDisable(false);
                break;
            case 10:
                island10.setDisable(false);
                break;
            case 11:
                island11.setDisable(false);
                break;
            case 12:
                island12.setDisable(false);
                break;
        }
    }

    public void moveMotherNature(int pos){
        if(motherNaturePos+pos<=11){
            motherNaturePos += pos;
            setMotherNaturePose(motherNaturePos);
        }else{
            motherNaturePos += pos-12;
            setMotherNaturePose(motherNaturePos);
        }
    }

    public void light(MouseEvent mouseEvent) {
            switch(mouseEvent.getSource().toString().charAt(19)){
                case '2':   island2.setEffect(new Bloom(0.72));   break;
                case '3':   island3.setEffect(new Bloom(0.72));   break;
                case '4':   island4.setEffect(new Bloom(0.72));   break;
                case '5':   island5.setEffect(new Bloom(0.72));   break;
                case '6':   island6.setEffect(new Bloom(0.72));   break;
                case '7':   island7.setEffect(new Bloom(0.72));   break;
                case '8':   island8.setEffect(new Bloom(0.72));   break;
                case '9':   island9.setEffect(new Bloom(0.72));   break;
                case '1':   switch(mouseEvent.getSource().toString().charAt(20)){
                    case '0':
                        island10.setEffect(new Bloom(0.72));
                        break;
                    case '1':
                        island11.setEffect(new Bloom(0.72));
                        break;
                    case '2':
                        island12.setEffect(new Bloom(0.72));
                        break;
                    default:    island1.setEffect(new Bloom(0.72)); break;
                }

            }
    }

    public void dark(MouseEvent mouseEvent) {
        switch(mouseEvent.getSource().toString().charAt(19)){
            case '2':   island2.setEffect(null);   break;
            case '3':   island3.setEffect(null);   break;
            case '4':   island4.setEffect(null);   break;
            case '5':   island5.setEffect(null);   break;
            case '6':   island6.setEffect(null);   break;
            case '7':   island7.setEffect(null);   break;
            case '8':   island8.setEffect(null);   break;
            case '9':   island9.setEffect(null);   break;
            case '1':   switch(mouseEvent.getSource().toString().charAt(20)){
                case '0':
                    island10.setEffect(null);
                    break;
                case '1':
                    island11.setEffect(null);
                    break;
                case '2':
                    island12.setEffect(null);
                    break;
                default:    island1.setEffect(null); break;
            }

        }
    }

    public void disableIslands(){
        island1.setDisable(true);
        island2.setDisable(true);
        island3.setDisable(true);
        island4.setDisable(true);
        island5.setDisable(true);
        island6.setDisable(true);
        island7.setDisable(true);
        island8.setDisable(true);
        island9.setDisable(true);
        island10.setDisable(true);
        island11.setDisable(true);
        island12.setDisable(true);
    }

    public void enableIslands(){
        island2.setDisable(false);
        island1.setDisable(false);
        island3.setDisable(false);
        island4.setDisable(false);
        island5.setDisable(false);
        island6.setDisable(false);
        island7.setDisable(false);
        island8.setDisable(false);
        island9.setDisable(false);
        island10.setDisable(false);
        island11.setDisable(false);
        island12.setDisable(false);
    }

    public void enableStudents(){
        for(int i=0;i<12;i++)
            for(ImageView pawn: students[i])
                pawn.setDisable(false);

    }

    public void disableStudents(){
        for(int i=0;i<12;i++)
            for(ImageView pawn: students[i])
                pawn.setDisable(true);

    }

    //color: 0=black 1=white 2=grey
    public void setTower(int island,int color){
        Image image = null;

        if(color == 0)
            image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerBlack.png"));
        if(color == 1)
            image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerWhite.png"));
        if(color == 2)
            image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerGrey.png"));

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(49);
        imageView.setFitHeight(101);
        imageView.setLayoutY(towerBases[island].getLayoutY()-49);
        imageView.setLayoutX(towerBases[island].getLayoutX()+12);
        imageView.setEffect(new InnerShadow());
        imageView.setOpacity(1);

        pane.getChildren().addAll(imageView);

        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),imageView);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),imageView);
        tt.setFromY(imageView.getY()-20);
        tt.setToY(imageView.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();


    }

    public void chooseIsland(MouseEvent mouseEvent) {
        int choosenIsland = -99;

        switch(mouseEvent.getSource().toString().charAt(19)) {
            case '0':
                choosenIsland = 0;
                break;
            case '2':
                choosenIsland = 2;
                break;
            case '3':
                choosenIsland = 3;
                break;
            case '4':
                choosenIsland = 4;
                break;
            case '5':
                choosenIsland = 5;
                break;
            case '6':
                choosenIsland = 6;
                break;
            case '7':
                choosenIsland = 7;
                break;
            case '8':
                choosenIsland = 8;
                break;
            case '9':
                choosenIsland = 9;
                break;
            case '1':
                switch (mouseEvent.getSource().toString().charAt(20)) {
                    case '0':
                        choosenIsland = 10;
                        break;
                    case '1':
                        choosenIsland = 11;
                        break;
                    case '2':
                        choosenIsland = 12;
                        break;
                    default:
                        choosenIsland = 1;
                        break;
                }
        }
        final int choosen = choosenIsland;
        if(switchMotherNature){
            switchMotherNature = false;
            int steps = 0;

            if(choosen-motherNaturePos>0)
                steps = choosen-motherNaturePos-1;
            else
                steps =  choosen-motherNaturePos+11;

            System.out.println("passi da fare: "+steps);
            int finalSteps = steps;
            moveMotherNature(steps);
            new Thread( () -> notifyObserver(obs -> obs.onUpdateMotherNaturePosition(finalSteps))).start();
        }

        disableIslands();
        darkAll();
    }

    private int getColorFromId(int id){
        if(id-59>=0 && id-59<=25)
            return 2;
        else if(id-59>=26 && id-59<=51)
            return 5;
        else if(id-59>=52 && id-59<=77)
            return 1;
        else if(id-59>=78 && id-59<=103)
            return 3;
        else if(id-59>=104 && id-59<=129)
            return 4;
        else System.out.println("id mancante: "+id);

        return 0;
    }

    private boolean containId(int island,int id){
        return false;
    }

    public void updateValues(GameSerialized gameSerialized) {
        this.gameSerialized = gameSerialized;
        ArrayList<SerializableIsland> islands = gameSerialized.getSerializableIslands();

        if(islands.size() == 12) {
            setMotherNaturePose(gameSerialized.getMotherNaturePos());
            for (SerializableIsland island : islands) {

                if (island.getTowerNumber() != 0) {
                    for (int i = 0; i < island.getTowerNumber(); i++)
                        switch (island.getTowerColor()) {
                            case BLACK -> setTower(islands.indexOf(island), 0);
                            case GREY -> setTower(islands.indexOf(island), 2);
                            case WHITE -> setTower(islands.indexOf(island), 1);
                        }
                }

                for (int i = 0; i < 12; i++) {
                    ArrayList<Integer> islandPawnsId = gameSerialized.getSerializableIslands().get(i).getIslandsPawnsID();

                    for (Integer id : islandPawnsId) {
                        boolean add = true;
                        for (int j = 0; j < students[i].size(); j++) {
                            if (students[i].get(j).getId().equals(Integer.toString(id)))
                                add = false;
                        }
                        if (add)
                            addStudentToIsland(getColorFromId(id), id, i);
                    }
                }
            }
        }



        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(s.getNickname().equals(nickname))
                setCoin(s.getCoins());

        int opponentCount = 0;
        for(SerializableScoreboard s: gameSerialized.getSerializableScoreboard())
            if(!s.getNickname().equals(nickname)){
                if(opponentCount == 0)
                    GraphicController.nicknameOpponent1 = s.getNickname();
                else
                    GraphicController.nicknameOpponent2 = s.getNickname();

            opponentCount++;
            }

        nicknameOp1.setText(GraphicController.nicknameOpponent1.toUpperCase(Locale.ROOT));
        if(GraphicController.nicknameOpponent2!=null) nicknameOp2.setText(GraphicController.nicknameOpponent2.toUpperCase(Locale.ROOT));

        if(scoreboardSceneManager!=null)    scoreboardSceneManager.updateValues(gameSerialized);
    }

    public void setOpponent1Card(int number){
        assistentCardOpponent1.setImage(new Image(getClass().getResourceAsStream("/Graphical_Assets/Assistenti/2x/Assistente("+number+").png")));
    }

    public void setOpponent2Card(int number){
        assistentCardOpponent2.setImage(new Image(getClass().getResourceAsStream("/Graphical_Assets/Assistenti/2x/Assistente("+number+").png")));
    }

    //passare numero isola precedente in senso orario
    public void build(int island) {
        switch(island){
            case 1: build_1_2(); break;
            case 2: build_2_3(); break;
            case 3: build_3_4(); break;
            case 4: build_4_5(); break;
            case 5: build_5_6(); break;
            case 6: build_6_7(); break;
            case 7: build_7_8(); break;
            case 8: build_8_9(); break;
            case 9: build_9_10(); break;
            case 10: build_10_11(); break;
            case 11: build_11_12(); break;
            case 12: build_12_1(); break;
        }

    }

    private void build_1_2(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_1_2);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_1_2);
        tt.setFromY(bridge_1_2.getY()-20);
        tt.setToY(bridge_1_2.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();

    }

    private void build_2_3(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_2_3);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_2_3);
        tt.setFromY(bridge_2_3.getY()-20);
        tt.setToY(bridge_2_3.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_3_4(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_3_4);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_3_4);
        tt.setFromY(bridge_3_4.getY()-20);
        tt.setToY(bridge_3_4.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();

    }

    private void build_4_5(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_4_5);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_4_5);
        tt.setFromY(bridge_4_5.getY()-20);
        tt.setToY(bridge_4_5.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_5_6(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_5_6);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_5_6);
        tt.setFromY(bridge_5_6.getY()-20);
        tt.setToY(bridge_5_6.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_6_7(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_6_7);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_6_7);
        tt.setFromY(bridge_6_7.getY()-20);
        tt.setToY(bridge_6_7.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_7_8(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_7_8);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_7_8);
        tt.setFromY(bridge_7_8.getY()-20);
        tt.setToY(bridge_7_8.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_8_9(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_8_9);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_8_9);
        tt.setFromY(bridge_8_9.getY()-20);
        tt.setToY(bridge_8_9.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_9_10(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_9_10);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_9_10);
        tt.setFromY(bridge_9_10.getY()-20);
        tt.setToY(bridge_9_10.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_10_11(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_10_11);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_10_11);
        tt.setFromY(bridge_10_11.getY()-20);
        tt.setToY(bridge_10_11.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_11_12(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_11_12);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_11_12);
        tt.setFromY(bridge_11_12.getY()-20);
        tt.setToY(bridge_11_12.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_12_1(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),bridge_1_12);
        FadeTransition ft = new FadeTransition(Duration.millis(1000),bridge_1_12);
        tt.setFromY(bridge_1_12.getY()-20);
        tt.setToY(bridge_1_12.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    public void selectedCloud1(MouseEvent mouseEvent) {
        cloud1.setEffect(new DropShadow());
        selectCloudObserverNotification(1);
        disableClouds();
    }

    public void selectedCloud2(MouseEvent mouseEvent) {
        cloud1.setEffect(new DropShadow());
        selectCloudObserverNotification(2);
        disableClouds();
    }

    public void disableClouds(){
        cloud1.setDisable(true);
        cloud2.setDisable(true);
    }

    public void enableClouds(){
        if(cloud1Student0.getImage()!=null) cloud1.setDisable(false);
        if(cloud2Student0.getImage()!=null) cloud2.setDisable(false);
    }

    public void selectCloudObserverNotification(int cloudNumber) {
        ArrayList<Cloud> finalCloud = new ArrayList<>();
        finalCloud.add(clouds.get(cloudNumber-1));
        new Thread( () -> notifyObserver( obs -> obs.onUpdatePickCloud(finalCloud))).start();
        clouds.clear();
        if(cloudNumber == 1)
            clearCloud1();
        if(cloudNumber == 2)
            clearCloud2();
    }

    public void clearCloud1(){
        for(int i=0;i<4;i++)
            cloudStudents1[i].setImage(null);
    }

    public void clearCloud2(){
        for(int i=0;i<4;i++)
            cloudStudents2[i].setImage(null);
    }

    public void inCloud1(MouseEvent mouseEvent) {
        cloud1.setEffect(new Bloom(0.95));
    }

    public void outCloud1(MouseEvent mouseEvent) {
        cloud1.setEffect(new DropShadow());
    }

    public void inCloud2(MouseEvent mouseEvent) {
        cloud2.setEffect(new Bloom(0.95));
    }

    public void outCloud2(MouseEvent mouseEvent) {
        cloud2.setEffect(new DropShadow());
    }

    public void setAssistants(List<AssistantCard> assistantCards){
        for(AssistantCard assistantCard: assistantCards){
            this.assistantCards[assistantCard.getValue()-1].setVisible(true);
            this.assistantCards[assistantCard.getValue()-1].setDisable(false);
        }

    }

    public void choosenAssistant(MouseEvent mouseEvent) {
        int assistantUsed = Integer.parseInt(mouseEvent.getSource().toString().substring(26,27));

        assistantCards[assistantUsed].setMouseTransparent(true);
        //TranslateTransition tt = new TranslateTransition(Duration.millis(1000),assistantCards[assistantUsed]);
        FadeTransition ft = new FadeTransition(Duration.millis(600),assistantCards[assistantUsed]);
        //tt.setFromY(assistantCards[assistantUsed].getY());
        //tt.setToY(assistantCards[assistantUsed].getY()+25);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();
        //tt.play();
        assistantCards[assistantUsed].setDisable(true);

        closeHand(null);
        disableAssistant();

        AssistantCard finalAssistantCard = null;
        for(AssistantCard a: assistantCardsList)
            if(a.getValue() == assistantUsed+1)
                finalAssistantCard = a;

        playedAssistantCardsList.add(finalAssistantCard);
        AssistantCard finalAssistantCard1 = finalAssistantCard;
        new Thread( () -> notifyObserver(obs -> obs.onUpdatePlayAssistantCard(List.of(finalAssistantCard1), playedAssistantCardsList))).start();

        assistantCardsList.remove(finalAssistantCard);
        return;

    }

    public void outAssistant(MouseEvent mouseEvent) {
        assistantCards[Integer.parseInt(mouseEvent.getSource().toString().substring(26,27))].setEffect(new DropShadow());
    }

    public void inAssistant(MouseEvent mouseEvent) {


        assistantCards[Integer.parseInt(mouseEvent.getSource().toString().substring(26,27))].setEffect(new InnerShadow());
    }

    public void enableAssistant(List<AssistantCard> assistantCards){
        this.assistantCardsList = assistantCards;

        switchView(null);
    }

    public void disableAssistant(){
        for(ImageView i: assistantCards) {
            i.setDisable(true);
        }
    }

    public void disableOpcard1(){
        opCard1 = false;
    }

    public void disableOpcard2(){
        opCard2 = false;
    }

    public void viewOpCard1(){
        opCard1 = true;

        FadeTransition ft = new FadeTransition(Duration.millis(600),assistentCardOpponent1);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),bgOpCard1);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),previous1lbl);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),nicknameOp1);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        
    }

    public void viewOpCard2(){
        opCard2 = true;

        FadeTransition ft = new FadeTransition(Duration.millis(600),assistentCardOpponent2);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),bgOpCard2);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),previous2lbl);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),nicknameOp2);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

    }

    public void hideOpCard1(){

        FadeTransition ft = new FadeTransition(Duration.millis(600),assistentCardOpponent1);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),bgOpCard1);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),previous1lbl);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),nicknameOp1);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

    }

    public void hideOpCard2(){

        FadeTransition ft = new FadeTransition(Duration.millis(600),assistentCardOpponent2);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),bgOpCard2);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),previous2lbl);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600),nicknameOp2);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();

    }

    public void switchView(MouseEvent mouseEvent) {
        if(opCard1) viewOpCard1();
        if(opCard2) viewOpCard2();

        for(AssistantCard a: assistantCardsList){
            this.assistantCards[a.getValue()-1].setVisible(true);

            assistantCards[a.getValue()-1].setMouseTransparent(false);
            TranslateTransition tt = new TranslateTransition(Duration.millis(600),assistantCards[a.getValue()-1]);
            FadeTransition ft = new FadeTransition(Duration.millis(600),assistantCards[a.getValue()-1]);
            tt.setFromY(assistantCards[a.getValue()-1].getY()+25);
            tt.setToY(assistantCards[a.getValue()-1].getY());
            ft.setFromValue(0);
            ft.setToValue(1);

            ft.play();
            tt.play();
        }
        TranslateTransition tt = new TranslateTransition(Duration.millis(600),xButton);
        FadeTransition ft = new FadeTransition(Duration.millis(600),xButton);
        tt.setFromY(xButton.getY()+25);
        tt.setToY(xButton.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
        xButton.setDisable(false);

        ft = new FadeTransition(Duration.millis(600),switchHandMap);
        ft.setFromValue(1);
        ft.setToValue(0);
        switchHandMap.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),toScoreboard);
        ft.setFromValue(1);
        ft.setToValue(0);
        toScoreboard.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),labelHand);
        ft.setFromValue(1);
        ft.setToValue(0);
        labelHand.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),labelScrbd);
        ft.setFromValue(1);
        ft.setToValue(0);
        labelScrbd.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),coin);
        ft.setFromValue(1);
        ft.setToValue(0);
        coin.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),labelCoins);
        ft.setFromValue(1);
        ft.setToValue(0);
        labelCoins.setDisable(true);
        ft.play();
    }

    public void inEffect(MouseEvent mouseEvent) {
        switchHandMap.setEffect(new Glow(0.3));
    }

    public void outEffect(MouseEvent mouseEvent) {
        switchHandMap.setEffect(null);
    }

    public void closeHand(MouseEvent mouseEvent) {
        if(opCard1) hideOpCard1();
        if(opCard2) hideOpCard2();

        for(AssistantCard a: assistantCardsList){

            assistantCards[a.getValue()-1].setMouseTransparent(true);
            TranslateTransition tt = new TranslateTransition(Duration.millis(600),assistantCards[a.getValue()-1]);
            FadeTransition ft = new FadeTransition(Duration.millis(600),assistantCards[a.getValue()-1]);
            tt.setFromY(assistantCards[a.getValue()-1].getY());
            tt.setToY(assistantCards[a.getValue()-1].getY()+25);
            ft.setFromValue(1);
            ft.setToValue(0);

            ft.play();
            tt.play();
        }
        TranslateTransition tt = new TranslateTransition(Duration.millis(600),xButton);
        FadeTransition ft = new FadeTransition(Duration.millis(600),xButton);
        tt.setFromY(xButton.getY());
        tt.setToY(xButton.getY()+25);
        ft.setFromValue(1);
        ft.setToValue(0);

        ft.play();
        tt.play();

        xButton.setDisable(true);

        ft = new FadeTransition(Duration.millis(600),switchHandMap);
        ft.setFromValue(0);
        ft.setToValue(1);
        switchHandMap.setDisable(false);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),toScoreboard);
        ft.setFromValue(0);
        ft.setToValue(1);
        toScoreboard.setDisable(false);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),labelHand);
        ft.setFromValue(0);
        ft.setToValue(1);
        labelHand.setDisable(false);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),labelScrbd);
        ft.setFromValue(0);
        ft.setToValue(1);
        labelScrbd.setDisable(false);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),coin);
        ft.setFromValue(0);
        ft.setToValue(1);
        coin.setDisable(false);
        ft.play();

        ft = new FadeTransition(Duration.millis(600),labelCoins);
        ft.setFromValue(0);
        ft.setToValue(1);
        labelCoins.setDisable(false);
        ft.play();
    }

    public void inX(MouseEvent mouseEvent) {
        xButton.setEffect(new Bloom(0));
    }

    public void outX(MouseEvent mouseEvent) {
        xButton.setEffect(new Bloom(0.25));
    }

    public void moveToScoreboard(MouseEvent mouseEvent) {
        scoreboardSceneManager = graphicController.showScoreboards(pane.getScene().snapshot(null));
    }

    public void inScrb(MouseEvent mouseEvent) {
        DropShadow et = new DropShadow();
        BoxBlur et2 = new BoxBlur();
        et2.setInput(new Glow(0.3));
        et.setInput(et2);
        toScoreboard.setEffect(et);
    }

    public void outScrb(MouseEvent mouseEvent) {
        DropShadow et = new DropShadow();
        et.setInput(new BoxBlur());
        toScoreboard.setEffect(et);
    }

    public void addCoin(){
        int coin = Integer.parseInt(String.valueOf(labelCoins.getText().charAt(2)));
        coin++;
        labelCoins.setText("X "+Integer.toString(coin));
    }

    public void removeCoin(){
        int coin = Integer.parseInt(String.valueOf(labelCoins.getText().charAt(2)));
        coin--;
        labelCoins.setText("X "+Integer.toString(coin));
    }

    public void setCoin(int coin){
        labelCoins.setText("X "+Integer.toString(coin));
    }
}
