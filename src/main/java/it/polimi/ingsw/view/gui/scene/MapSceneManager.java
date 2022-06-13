package it.polimi.ingsw.view.gui.scene;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.random.RandomGenerator;

import static java.lang.Math.abs;

public class MapSceneManager implements Initializable {

    @FXML
    private Rectangle bg;

    @FXML
    private Rectangle bgCards;

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
    private ImageView motherNature;


    private double r = 22;
    private double rIsl;

    ImageView[] towerBases = new ImageView[12];
    ArrayList<ImageView> pawns = new ArrayList<ImageView>();
    ArrayList<Point>[] islands = new ArrayList[12];
    Point[] islandsPosCentre = new Point[12];
    Point[] motherNaturePoses = new Point[12];
    int motherNaturePos;
    private boolean switchIslands = false;
    private boolean switchPawns = false;

    public void enableIslands(ActionEvent actionEvent) {
        switchIslands = true;
        disablePawns();
    }

    public void initializeCards(int[] number){
        for(int n:number){
            Image image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front"+n+".jpg"));
            card1.setImage(image);
        }
    }

    public void addColor(int color){
        Image image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/2_RossoWood.png"));;
        ImageView pawn = new ImageView();

        switch (color){
            case 1: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/1_VerdeWood.png")); break;
            case 2: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/2_RossoWood.png")); break;
            case 3: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/3_GialloWood.png")); break;
            case 4: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/4_ViolaWood.png")); break;
            case 5: image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/5_AzzurroWood.png")); break;
        }
        pawn.setImage(image);
        DropShadow dr = new DropShadow(); dr.setWidth(15); dr.setHeight(15);
        pawn.setOnMouseExited(ex -> pawn.setEffect(dr));
        switch(color){
            case 1: pawn.setOnMouseEntered(en -> pawn.setEffect(new Bloom(0.55))); break;
            case 2: pawn.setOnMouseEntered(en -> pawn.setEffect(new Bloom(0.23))); break;
            case 3: pawn.setOnMouseEntered(en -> pawn.setEffect(new Bloom(0.65))); break;
            case 4: pawn.setOnMouseEntered(en -> pawn.setEffect(new Bloom(0.48))); break;
            case 5: pawn.setOnMouseEntered(en -> pawn.setEffect(new Bloom(0.55))); break;
        }

        pawn.setEffect(dr);
//        pawn.setAccessibleText("colore da mettere");
        pawns.add(pawn);

        pawn.setFitHeight(r*2);
        pawn.setFitWidth(r*2);
        pawn.setDisable(true);

        Point pos = findCoord(0);

        pawn.setX(islandsPosCentre[0].getX()-rIsl+pos.getX()-rIsl*0.1);
        pawn.setY(islandsPosCentre[0].getY()-rIsl+pos.getY()-rIsl*0.1);
        pawn.setLayoutX(r/2);

        pane.getChildren().addAll(pawn);
        switchIslands = false;
        darkAll();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        rIsl = island1.getFitHeight()/2;

        for(int i=0;i<12;i++)
            islands[i] = new ArrayList<Point>();

        towerBases[0] = towerBase0; towerBases[1] = towerBase1; towerBases[2] = towerBase2; towerBases[3] = towerBase3; towerBases[4] = towerBase4; towerBases[5] = towerBase5; towerBases[6] = towerBase6; towerBases[7] = towerBases[7]; towerBases[8] = towerBase8; towerBases[9] = towerBase9; towerBases[10] = towerBase10; towerBases[11] = towerBase11;

        motherNaturePoses[0] = new Point(1492,536); motherNaturePoses[1] = new Point(1455,684); motherNaturePoses[2] = new Point(1277,684); motherNaturePoses[3] = new Point(1038,724); motherNaturePoses[4] = new Point(688,727); motherNaturePoses[5] = new Point(357,684); motherNaturePoses[6] = new Point(276,578); motherNaturePoses[7] = new Point(365,331); motherNaturePoses[8] = new Point(504,317); motherNaturePoses[9] = new Point(780,264); motherNaturePoses[10] = new Point(1252,317); motherNaturePoses[11] = new Point(1453,324);
    }

    public void setMotherNaturePoses(int island){
        motherNature.setLayoutX(motherNaturePoses[island].getX());
        motherNature.setLayoutY(motherNaturePoses[island].getY());
        motherNature.setVisible(true);
        motherNaturePos = island;
    }

    public void moveMotherNature(int pos){
        //    if(motherNaturePos+)
    }

    public void light(MouseEvent mouseEvent) {
        Effect e = new Bloom();
        if(switchIslands)
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

    public void enablePawns(){
        switchPawns = true;
        for(ImageView pawn: pawns){
            pawn.setDisable(false);
        }
    }

    public void disablePawns(){
        for(ImageView pawn: pawns){
            pawn.setDisable(true);
        }
    }

    //island: 0=black 1=white 2=grey
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

    public void build(ActionEvent actionEvent) {
        setTower(0,1);
        setTower(5,0);
        setTower(8,2);

    }
}
