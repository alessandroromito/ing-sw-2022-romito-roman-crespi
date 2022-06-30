package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.server.enumerations.PawnColors;
import it.polimi.ingsw.server.extra.SerializableIsland;
import it.polimi.ingsw.server.extra.SerializableScoreboard;
import it.polimi.ingsw.server.model.GameSerialized;
import it.polimi.ingsw.server.model.component.AssistantCard;
import it.polimi.ingsw.server.model.component.StudentDisc;
import it.polimi.ingsw.server.model.component.charactercards.Card_209;
import it.polimi.ingsw.server.model.component.charactercards.Card_219;
import it.polimi.ingsw.server.model.component.charactercards.CharacterCard;
import it.polimi.ingsw.server.model.map.Cloud;
import it.polimi.ingsw.server.model.map.GhostIsland;
import it.polimi.ingsw.view.gui.GraphicController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
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

    @FXML
    private MenuButton islandMenu;

    @FXML
    private MenuButton colorMenu;

    @FXML
    private Label labelOpTurn;

    @FXML
    private Label labelCrc;


    private double r = 22;
    private double rIsl;
    private boolean active = false;

    private List<List<Integer>> posWithGhost = null;

    GameSerialized gameSerialized;
    List<AssistantCard> assistantCardsList = new ArrayList<>();
    private boolean opCard1 = false;
    private boolean opCard2 = false;
    private boolean actionFase = false;
    private int[] towerColor = new int[12];
    private ImageView[] towers = new ImageView[12];
    ScoreboardSceneManager scoreboardSceneManager = null;
    private int coins;
    private int finalStudentPos;

    public void setPlayedAssistantCardsList(List<AssistantCard> playedAssistantCardsList) {
        this.playedAssistantCardsList = playedAssistantCardsList;
    }

    List<AssistantCard> playedAssistantCardsList = new ArrayList<>();
    ArrayList<Cloud> clouds = new ArrayList<>();
    ImageView[] cloudStudents1 = new ImageView[4];
    ImageView[] cloudStudents2 = new ImageView[4];
    ImageView[] towerBases = new ImageView[12];
    ImageView[] island = new ImageView[12];
    ImageView[] assistantCards = new ImageView[10];
    ArrayList<ImageView>[] students = new ArrayList[12];
    ArrayList<Point>[] islands = new ArrayList[12];
    Point[] islandsPosCentre = new Point[12];
    Point[] motherNaturePoses = new Point[12];
    private int motherNaturePos = -1;
    private boolean switchMotherNature = false;
    private List<CharacterCard> characterCards;
    private ImageView[] cCards = new ImageView[3];
    private ImageView[] card209 = new ImageView[4];
    private ImageView[] card219 = new ImageView[4];
    private Integer startingPose = null;

    private GraphicController graphicController = null;

    public void setGraphicController(GraphicController graphicController) {
        this.graphicController = graphicController;
    }


    @FXML
    public void initialize() {
        System.out.println("start initialize fxml");

        for (int j = 1; j <= 10; j++)
            assistantCardsList.add(new AssistantCard(0, j, 0));

        for (int i = 0; i < 12; i++) students[i] = new ArrayList<ImageView>();

        islandsPosCentre[0] = new Point(island1.getLayoutX() + island1.getFitWidth() / 2, island1.getLayoutY() + island1.getFitHeight() / 2);
        islandsPosCentre[1] = new Point(island2.getLayoutX() + island2.getFitWidth() / 2, island2.getLayoutY() + island2.getFitHeight() / 2);
        islandsPosCentre[2] = new Point(island3.getLayoutX() + island3.getFitWidth() / 2, island3.getLayoutY() + island3.getFitHeight() / 2);
        islandsPosCentre[3] = new Point(island4.getLayoutX() + island4.getFitWidth() / 2, island4.getLayoutY() + island4.getFitHeight() / 2);
        islandsPosCentre[4] = new Point(island5.getLayoutX() + island5.getFitWidth() / 2, island5.getLayoutY() + island5.getFitHeight() / 2);
        islandsPosCentre[5] = new Point(island6.getLayoutX() + island6.getFitWidth() / 2, island6.getLayoutY() + island6.getFitHeight() / 2);
        islandsPosCentre[6] = new Point(island7.getLayoutX() + island7.getFitWidth() / 2, island7.getLayoutY() + island7.getFitHeight() / 2);
        islandsPosCentre[7] = new Point(island8.getLayoutX() + island8.getFitWidth() / 2, island8.getLayoutY() + island8.getFitHeight() / 2);
        islandsPosCentre[8] = new Point(island9.getLayoutX() + island9.getFitWidth() / 2, island9.getLayoutY() + island9.getFitHeight() / 2);
        islandsPosCentre[9] = new Point(island10.getLayoutX() + island10.getFitWidth() / 2, island10.getLayoutY() + island10.getFitHeight() / 2);
        islandsPosCentre[10] = new Point(island11.getLayoutX() + island11.getFitWidth() / 2, island11.getLayoutY() + island11.getFitHeight() / 2);
        islandsPosCentre[11] = new Point(island12.getLayoutX() + island12.getFitWidth() / 2, island12.getLayoutY() + island12.getFitHeight() / 2);

        rIsl = (island1.getFitHeight() + 17) / 2;

        for (int i = 0; i < 12; i++)
            islands[i] = new ArrayList<Point>();
        disableIslands();

        assistantCards[0] = assistentCard0;
        assistantCards[1] = assistentCard1;
        assistantCards[2] = assistentCard2;
        assistantCards[3] = assistentCard3;
        assistantCards[4] = assistentCard4;
        assistantCards[5] = assistentCard5;
        assistantCards[6] = assistentCard6;
        assistantCards[7] = assistentCard7;
        assistantCards[8] = assistentCard8;
        assistantCards[9] = assistentCard9;

        towerBases[0] = towerBase0;
        towerBases[1] = towerBase1;
        towerBases[2] = towerBase2;
        towerBases[3] = towerBase3;
        towerBases[4] = towerBase4;
        towerBases[5] = towerBase5;
        towerBases[6] = towerBase6;
        towerBases[7] = towerBase7;
        towerBases[8] = towerBase8;
        towerBases[9] = towerBase9;
        towerBases[10] = towerBase10;
        towerBases[11] = towerBase11;

        island[0] = island1;
        island[1] = island2;
        island[2] = island3;
        island[3] = island4;
        island[4] = island5;
        island[5] = island6;
        island[6] = island7;
        island[7] = island8;
        island[8] = island9;
        island[9] = island10;
        island[10] = island11;
        island[11] = island12;

        motherNaturePoses[0] = new Point(1492, 536);
        motherNaturePoses[1] = new Point(1455, 684);
        motherNaturePoses[2] = new Point(1277, 684);
        motherNaturePoses[3] = new Point(1038, 724);
        motherNaturePoses[4] = new Point(688, 727);
        motherNaturePoses[5] = new Point(357, 684);
        motherNaturePoses[6] = new Point(276, 578);
        motherNaturePoses[7] = new Point(365, 331);
        motherNaturePoses[8] = new Point(504, 317);
        motherNaturePoses[9] = new Point(780, 264);
        motherNaturePoses[10] = new Point(1252, 317);
        motherNaturePoses[11] = new Point(1453, 324);

        cloudStudents1[0] = cloud1Student0;
        cloudStudents1[1] = cloud1Student1;
        cloudStudents1[2] = cloud1Student2;
        cloudStudents1[3] = cloud1Student3;
        cloudStudents2[0] = cloud2Student0;
        cloudStudents2[1] = cloud2Student1;
        cloudStudents2[2] = cloud2Student2;
        cloudStudents2[3] = cloud2Student3;

        disableClouds();

        for (int k = 0; k < 10; k++)
            assistantCards[k].setViewOrder(-1);
        xButton.setViewOrder(-1);

        assistentCardOpponent1.setViewOrder(-1);
        previous1lbl.setViewOrder(-1);
        bgOpCard1.setViewOrder(-1);
        nicknameOp1.setViewOrder(-1);
        assistentCardOpponent2.setViewOrder(-1);
        previous2lbl.setViewOrder(-1);
        bgOpCard2.setViewOrder(-1);
        nicknameOp2.setViewOrder(-1);

        for (int i = 0; i < 10; i++) assistantCards[i].setVisible(true);

        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);

        cCards[0] = card1;
        cCards[1] = card2;
        cCards[2] = card3;

        labelOpTurn.setViewOrder(-1);

        System.out.println("fine initialize fxml");
    }

    public void fullscreen() {
        Stage stage = (Stage) pane.getScene().getWindow();
        //stage.setFullScreen(true);
    }

    public void initializeCharacterCards() {
        if (gameSerialized.getExpertMode()) {
            labelCrc.setVisible(true);
            card1.setDisable(true);
            card2.setDisable(true);
            card3.setDisable(true);
            int[] finalCharacterNumbers = new int[3];
            for (int i = 0; i < characterCards.size(); i++) {
                finalCharacterNumbers[i] = characterCards.get(i).getID() - 209;
            }

            Image image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front" + finalCharacterNumbers[0] + ".jpg"));
            card1.setImage(image);
            card1.setId(Integer.toString(characterCards.get(0).getID()));
            image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front" + finalCharacterNumbers[1] + ".jpg"));
            card2.setImage(image);
            card2.setId(Integer.toString(characterCards.get(1).getID()));
            image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Personaggi/CarteTOT_front" + finalCharacterNumbers[2] + ".jpg"));
            card3.setImage(image);
            card3.setId(Integer.toString(characterCards.get(2).getID()));

            if (gameSerialized.getActiveCardID() != -1) {
                card1.setMouseTransparent(true);
                card2.setMouseTransparent(true);
                card3.setMouseTransparent(true);
                Glow ef = new Glow(0.45);
                ef.setInput(new DropShadow());
                getCharacterById(gameSerialized.getActiveCardID()).setEffect(ef);
            } else
                for (int i = 0; i < 3; i++) {
                    cCards[i].setMouseTransparent(false);
                    cCards[i].setEffect(new DropShadow());
                }
            for (int i = 0; i < 3; i++)
                if (characterCards.get(i).getCost() <= coins)
                    cCards[i].setDisable(false);

            for (int i = 0; i < 3; i++)
                switch (characterCards.get(i).getID()) {
                    case 209 -> {
                        pane.getChildren().removeAll(card209);
                        card209[0] = new ImageView();
                        card209[1] = new ImageView();
                        card209[2] = new ImageView();
                        card209[3] = new ImageView();
                        Card_209 temp = (Card_209) characterCards.get(i);
                        card209[0].setImage(createImage(temp.getStudents().get(0).getColorInt()));
                        card209[1].setImage(createImage(temp.getStudents().get(1).getColorInt()));
                        card209[2].setImage(createImage(temp.getStudents().get(2).getColorInt()));
                        card209[3].setImage(createImage(temp.getStudents().get(3).getColorInt()));

                        card209[0].setFitWidth(r * 2);
                        card209[0].setFitHeight(r * 2);
                        card209[0].setDisable(true);
                        card209[1].setFitWidth(r * 2);
                        card209[1].setFitHeight(r * 2);
                        card209[1].setDisable(true);
                        card209[2].setFitWidth(r * 2);
                        card209[2].setFitHeight(r * 2);
                        card209[2].setDisable(true);
                        card209[3].setFitWidth(r * 2);
                        card209[3].setFitHeight(r * 2);
                        card209[3].setDisable(true);

                        card209[0].setLayoutX(getCharacterById(209).getLayoutX() + 20);
                        card209[0].setLayoutY(getCharacterById(209).getLayoutY() + 150);
                        card209[1].setLayoutX(getCharacterById(209).getLayoutX() + 70);
                        card209[1].setLayoutY(getCharacterById(209).getLayoutY() + 150);
                        card209[2].setLayoutX(getCharacterById(209).getLayoutX() + 20);
                        card209[2].setLayoutY(getCharacterById(209).getLayoutY() + 100);
                        card209[3].setLayoutX(getCharacterById(209).getLayoutX() + 70);
                        card209[3].setLayoutY(getCharacterById(209).getLayoutY() + 100);
                        pane.getChildren().addAll(card209);
                    }

                    case 219 -> {
                        pane.getChildren().removeAll(card219);
                        card219[0] = new ImageView();
                        card219[1] = new ImageView();
                        card219[2] = new ImageView();
                        card219[3] = new ImageView();
                        Card_219 temp = (Card_219) characterCards.get(i);
                        card219[0].setImage(createImage(temp.getStudents().get(0).getColorInt()));
                        card219[1].setImage(createImage(temp.getStudents().get(1).getColorInt()));
                        card219[2].setImage(createImage(temp.getStudents().get(2).getColorInt()));
                        card219[3].setImage(createImage(temp.getStudents().get(3).getColorInt()));

                        card219[0].setFitWidth(r * 2);
                        card219[0].setFitHeight(r * 2);
                        card219[0].setDisable(true);
                        card219[1].setFitWidth(r * 2);
                        card219[1].setFitHeight(r * 2);
                        card219[1].setDisable(true);
                        card219[2].setFitWidth(r * 2);
                        card219[2].setFitHeight(r * 2);
                        card219[2].setDisable(true);
                        card219[3].setFitWidth(r * 2);
                        card219[3].setFitHeight(r * 2);
                        card219[3].setDisable(true);

                        card219[0].setLayoutX(getCharacterById(219).getLayoutX() + 20);
                        card219[0].setLayoutY(getCharacterById(219).getLayoutY() + 150);
                        card219[1].setLayoutX(getCharacterById(219).getLayoutX() + 70);
                        card219[1].setLayoutY(getCharacterById(219).getLayoutY() + 150);
                        card219[2].setLayoutX(getCharacterById(219).getLayoutX() + 20);
                        card219[2].setLayoutY(getCharacterById(219).getLayoutY() + 100);
                        card219[3].setLayoutX(getCharacterById(219).getLayoutX() + 70);
                        card219[3].setLayoutY(getCharacterById(219).getLayoutY() + 100);
                        pane.getChildren().addAll(card219);
                    }
                }
        }
    }

    public Image createImage(int color) {
        Image image = null;
        switch (color) {
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
        return image;
    }

    //color: 1/5  isalnd:0/11
    public void addStudentToIsland(int color, int id, int island) {
        Image image = null;
        ImageView student = new ImageView();

        switch (color) {
            case 1:
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/1_VerdeWood.png"));
                break;
            case 2:
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/2_RossoWood.png"));
                break;
            case 3:
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/3_GialloWood.png"));
                break;
            case 4:
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/4_ViolaWood.png"));
                break;
            case 5:
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Pedine/3D/5_AzzurroWood.png"));
                break;
        }

        student.setImage(image);
        DropShadow dr = new DropShadow();
        dr.setWidth(15);
        dr.setHeight(15);
        student.setOnMouseExited(ex -> student.setEffect(dr));

        switch (color) {
            case 1:
                student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.55)));
                break;
            case 2:
                student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.23)));
                break;
            case 3:
                student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.65)));
                break;
            case 4:
                student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.48)));
                break;
            case 5:
                student.setOnMouseEntered(en -> student.setEffect(new Bloom(0.55)));
                break;
        }

        student.setEffect(null);
        student.setId(Integer.toString(id));
        students[island].add(student);

        student.setFitHeight(r * 2);
        student.setFitWidth(r * 2);
        student.setDisable(true);

        Point pos = findCoord(island);

        student.setX(islandsPosCentre[island].getX() - rIsl + pos.getX() - rIsl * 0.1);
        student.setY(islandsPosCentre[island].getY() - rIsl + pos.getY() - rIsl * 0.1);
        student.setLayoutX(r / 2);

        pane.getChildren().addAll(student);
    }

    //number: 1/2
    public void addStudentsToCloud(Cloud cloud, int number) {
        clouds.add(cloud);
        Image image = null;

        for (int i = 0; i < cloud.getCloudStudents().size(); i++) {
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

            if (number == 1) {
                cloudStudents1[i].setImage(image);
                DropShadow dr = new DropShadow();
                dr.setWidth(15);
                dr.setHeight(15);

                cloudStudents1[i].setEffect(dr);
                cloudStudents1[i].setId(Integer.toString(s.getID()));
            } else {
                cloudStudents2[i].setImage(image);
                DropShadow dr = new DropShadow();
                dr.setWidth(15);
                dr.setHeight(15);

                cloudStudents2[i].setEffect(dr);
                cloudStudents2[i].setId(Integer.toString(s.getID()));
            }
        }
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
    public Point findCoord(int island) {
        Point p;
        RandomGenerator randomGenerator = new Random();

        for (int i = 0; i < 1000000; i++) {
            boolean valid = true;
            p = new Point(randomGenerator.nextDouble(rIsl * 2 - r * 2 - rIsl * 0.1), randomGenerator.nextDouble(rIsl * 2 - r * 2 - rIsl * 0.1));

            if ((p.getX() - (rIsl * 2 - r * 2) / 2) * (p.getX() - (rIsl * 2 - r * 2) / 2) + (p.getY() - (rIsl * 2 - r * 2) / 2) * (p.getY() - (rIsl * 2 - r * 2) / 2) > (rIsl / 2) * (rIsl / 2) + rIsl * rIsl * 0.2) {
                valid = false;
            }

            for (Point rl : islands[island]) {
                if ((abs(p.getX() - rl.getX()) < r * 2) && (abs(p.getY() - rl.getY()) < r * 2))
                    valid = false;
            }

            if (valid) {
                islands[island].add(p);
                return p;
            }
        }
        p = new Point(randomGenerator.nextDouble(rIsl * 0.8), randomGenerator.nextDouble(rIsl * 0.8));
        while ((p.getX() - (rIsl * 2 - r * 2) / 2) * (p.getX() - (rIsl * 2 - r * 2) / 2) + (p.getY() - (rIsl * 2 - r * 2) / 2) * (p.getY() - (rIsl * 2 - r * 2) / 2) > (rIsl / 2) * (rIsl / 2) + rIsl * rIsl * 0.2)
            p = new Point(randomGenerator.nextDouble(rIsl * 2 - r * 2 - rIsl * 0.1), randomGenerator.nextDouble(rIsl * 2 - r * 2 - rIsl * 0.1));
        return p;
    }

    public void setMotherNaturePose(int island) {
        motherNature.setLayoutX(motherNaturePoses[island].getX());
        motherNature.setLayoutY(motherNaturePoses[island].getY());
        motherNature.setVisible(true);
        motherNaturePos = island;
    }

    public void enableMotherNature(int maxSteps) {
        switchMotherNature = true;
        if (posWithGhost == null) {
            for (int i = 1; i <= maxSteps; i++) {
                if (i + motherNaturePos >= 12)
                    enableSingleIsland(i + motherNaturePos - 11);
                else
                    enableSingleIsland(i + motherNaturePos + 1);
            }
        } else {
            for (int j = 0; j < posWithGhost.size(); j++)
                if (posWithGhost.get(j).contains(motherNaturePos))
                    startingPose = j;

            System.out.println("starting pose: " + startingPose);
            List<List<Integer>> temp = new ArrayList<>();
            temp.addAll(posWithGhost);
            temp.addAll(posWithGhost);


            for (int j = startingPose + 1; j <= startingPose + maxSteps; j++)
                for (int island : temp.get(j))
                    enableSingleIsland(island + 1);
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

    public void moveMotherNature(int steps) {
        if (motherNaturePos + steps <= 11) {
            motherNaturePos += steps;
            setMotherNaturePose(motherNaturePos);
        } else {
            motherNaturePos += steps - 12;
            setMotherNaturePose(motherNaturePos);
        }
    }

    public void light(MouseEvent mouseEvent) {
        String selection = mouseEvent.getSource().toString().substring(19, 21);
        if (selection.charAt(1) == ',')
            selection = selection.substring(0, 1);
        System.out.println("selection: "+selection);

        int sel = Integer.parseInt(selection);
        island[sel - 1].setEffect(new Bloom(0.72));

        if (posWithGhost != null) {
            List<Integer> lightlist = null;
            for (List<Integer> l : posWithGhost)
                if (l.contains(sel-1))
                    lightlist = l;

            if (lightlist != null)
                for (Integer in : lightlist)
                    island[in].setEffect(new Bloom(0.72));
        }
    }

    public void dark(MouseEvent mouseEvent) {
        for (ImageView isl : island)
            isl.setEffect(null);
    }

    public void disableIslands() {
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

    //color: 0=black 1=white 2=grey
    public void setTower(int island, int color) {
        if (towers[island] == null) {
            System.out.println("Aggiungendo torre a " + island);
            Image image = null;

            if (color == 0)
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerBlack.png"));
            if (color == 1)
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerWhite.png"));
            if (color == 2)
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerGrey.png"));

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(49);
            imageView.setFitHeight(101);
            imageView.setLayoutY(towerBases[island].getLayoutY() - 49);
            imageView.setLayoutX(towerBases[island].getLayoutX() + 12);
            imageView.setEffect(new Shadow());
            imageView.setEffect(new DropShadow());
            imageView.setOpacity(1);

            pane.getChildren().addAll(imageView);
            towers[island] = imageView;
            towerColor[island] = color;

            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), imageView);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), imageView);
            tt.setFromY(imageView.getY() - 20);
            tt.setToY(imageView.getY());
            ft.setFromValue(0);
            ft.setToValue(1);

            ft.play();
            tt.play();
        }
        if (towerColor[island] != color) {
            System.out.println("Aggiungendo torre a " + island);
            pane.getChildren().remove(towers[island]);
            Image image = null;

            if (color == 0)
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerBlack.png"));
            if (color == 1)
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerWhite.png"));
            if (color == 2)
                image = new Image(getClass().getResourceAsStream("/Graphical_Assets/Reame/towerGrey.png"));

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(49);
            imageView.setFitHeight(101);
            imageView.setLayoutY(towerBases[island].getLayoutY() - 49);
            imageView.setLayoutX(towerBases[island].getLayoutX() + 12);
            imageView.setEffect(new Shadow());
            imageView.setEffect(new DropShadow());
            imageView.setOpacity(1);

            pane.getChildren().addAll(imageView);
            towers[island] = imageView;
            towerColor[island] = color;

            TranslateTransition tt = new TranslateTransition(Duration.millis(1000), imageView);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), imageView);
            tt.setFromY(imageView.getY() - 20);
            tt.setToY(imageView.getY());
            ft.setFromValue(0);
            ft.setToValue(1);

            ft.play();
            tt.play();
        }


    }

    public void chooseIsland(MouseEvent mouseEvent) {
        int choosenIsland = -99;

        switch (mouseEvent.getSource().toString().charAt(19)) {
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
        System.out.println(choosen);
        if (switchMotherNature) {
            switchMotherNature = false;
            int steps = 0;
            if (choosen - motherNaturePos > 0)
                steps = choosen - motherNaturePos - 1;
            else
                steps = choosen - motherNaturePos + 11;

            moveMotherNature(steps);

            if (posWithGhost == null) {
                int finalSteps = steps;
                new Thread(() -> notifyObserver(obs -> obs.onUpdateMotherNaturePosition(finalSteps))).start();
            } else {
                System.out.println("Spostamento modalità ghost");
                Integer finalPose = null;

                List<List<Integer>> temp = new ArrayList<>();
                temp.addAll(posWithGhost);
                temp.addAll(posWithGhost);

                for (int j = startingPose; j < temp.size(); j++) {
                    if (temp.get(j).contains(choosen - 1)) {
                        finalPose = j;
                        break;
                    }
                }
                System.out.println("starting , final  pose: " + startingPose + " , " + finalPose);
                int move = finalPose - startingPose;
                System.out.println("Passi: " + move);
                new Thread(() -> notifyObserver(obs -> obs.onUpdateMotherNaturePosition(move))).start();
            }
        }

        disableIslands();
        darkAll();
    }

    private int getColorFromId(int id) {
        if (id - 59 >= 0 && id - 59 <= 25)
            return 2;
        else if (id - 59 >= 26 && id - 59 <= 51)
            return 5;
        else if (id - 59 >= 52 && id - 59 <= 77)
            return 1;
        else if (id - 59 >= 78 && id - 59 <= 103)
            return 3;
        else if (id - 59 >= 104 && id - 59 <= 129)
            return 4;
        else System.out.println("id mancante: " + id);

        return 0;
    }

    private boolean containId(int island, int id) {
        return false;
    }

    public ImageView getCharacterById(int id) {
        if (Integer.parseInt(card1.getId()) == id)
            return card1;
        if (Integer.parseInt(card2.getId()) == id)
            return card2;
        if (Integer.parseInt(card3.getId()) == id)
            return card3;

        return null;
    }

    public void updateValues(GameSerialized gameSerialized) {
        disableMenu();
        characterCards = gameSerialized.getCharacterCards();
        for (int i = 0; i < 3; i++)
            cCards[i].setDisable(true);

        clearCloud1();
        clearCloud2();
        addStudentsToCloud(gameSerialized.getClouds().get(0), 1);
        if (gameSerialized.getClouds().get(1) != null) addStudentsToCloud(gameSerialized.getClouds().get(1), 2);

        this.gameSerialized = gameSerialized;
        ArrayList<SerializableIsland> islands = gameSerialized.getSerializableIslands();

        if (islands.size() == 12) {
            setMotherNaturePose(gameSerialized.getMotherNaturePos());
            for (int k = 0; k < 12; k++) {
                if (islands.get(k).getTowerNumber() != 0) {
                    switch (islands.get(k).getTowerColor()) {
                        case BLACK -> setTower(k, 0);
                        case GREY -> setTower(k, 2);
                        case WHITE -> setTower(k, 1);
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
        } else {
            int islandMarker = 0;
            setMotherNaturePose(gameSerialized.getMotherNaturePos());

            //aggiorna torri
            for (SerializableIsland island : islands) {

                if (!island.isGhost()) {
                    if (island.getTowerNumber() != 0)
                        switch (island.getTowerColor()) {
                            case BLACK -> setTower(islandMarker, 0);
                            case GREY -> setTower(islandMarker, 2);
                            case WHITE -> setTower(islandMarker, 1);
                        }
                    islandMarker++;
                }

                if (island.isGhost())
                    for (int islnb : island.getReferencedIslands()) {
                        System.out.println("isole referenced: "+islnb);
                        switch (island.getTowerColor()) {
                            case BLACK -> setTower(islnb-1, 0);
                            case GREY -> setTower(islnb-1, 2);
                            case WHITE -> setTower(islnb-1, 1);
                        }
                        islandMarker++;
                    }
            }
            islandMarker = 0;
            posWithGhost = new ArrayList<>();
            for (SerializableIsland island : islands) {
                ArrayList<Integer> islandPawnsId = island.getIslandsPawnsID();

                if (!island.isGhost()) {
                    System.out.println("Confrontando non ghost isola: " + (islandMarker + 1) + " con isola: " + (islandMarker + 1));
                    for (Integer id : islandPawnsId) {
                        boolean add = true;
                        for (int j = 0; j < students[islandMarker].size(); j++) {
                            if (students[islandMarker].get(j).getId().equals(Integer.toString(id)))
                                add = false;
                        }
                        if (add)
                            addStudentToIsland(getColorFromId(id), id, islandMarker);
                    }
                    posWithGhost.add(List.of(islandMarker));
                    islandMarker++;
                } else if (island.isGhost()) {
                    System.out.println();
                    System.out.print("Confrontando ghost isola: " + (islandMarker + 1) + " con isole: ");
                    for (int is : island.getReferencedIslands()) System.out.print(is + " , ");
                    for (Integer id : islandPawnsId) {
                        boolean add = true;
                        for (int isl : island.getReferencedIslands())
                            for (int j = 0; j < students[isl - 1].size(); j++) {
                                if (students[isl - 1].get(j).getId().equals(Integer.toString(id)))
                                    add = false;
                            }
                        if (add) {
                            int finalIsland = island.getReferencedIslands().get(0) - 1;
                            Integer min = students[island.getReferencedIslands().get(0) - 1].size();
                            for (int isl : island.getReferencedIslands())
                                if (students[isl - 1].size() < min) {
                                    min = students[isl - 1].size();
                                }
                            for (int i = 0; i < island.getReferencedIslands().size(); i++)
                                if (students[island.getReferencedIslands().get(i) - 1].size() == min)
                                    finalIsland = island.getReferencedIslands().get(i) - 1;

                            addStudentToIsland(getColorFromId(id), id, finalIsland);
                        }
                    }
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (int j = 0; j < island.getReferencedIslands().size(); j++)
                        temp.add(island.getReferencedIslands().get(j) - 1);

                    posWithGhost.add(temp);
                    islandMarker += island.getReferencedIslands().size();
                }
            }
            System.out.println();
            System.out.println("posWithGhost: ");
            for (int j = 0; j < posWithGhost.size(); j++) {
                System.out.print("pos: " + j + ":");
                for (int i = 0; i < posWithGhost.get(j).size(); i++)
                    System.out.print(" " + posWithGhost.get(j).get(i));
                System.out.println();
            }
        }

        for (SerializableScoreboard s : gameSerialized.getSerializableScoreboard())
            if (s.getNickname().equals(nickname))
                setCoin(s.getCoins());

        int opponentCount = 0;
        for (SerializableScoreboard s : gameSerialized.getSerializableScoreboard())
            if (!s.getNickname().equals(nickname)) {
                if (opponentCount == 0)
                    GraphicController.nicknameOpponent1 = s.getNickname();
                else
                    GraphicController.nicknameOpponent2 = s.getNickname();

                opponentCount++;
            }

        nicknameOp1.setText(GraphicController.nicknameOpponent1.toUpperCase(Locale.ROOT));
        if (GraphicController.nicknameOpponent2 != null)
            nicknameOp2.setText(GraphicController.nicknameOpponent2.toUpperCase(Locale.ROOT));

        if (scoreboardSceneManager != null) scoreboardSceneManager.updateValues(gameSerialized);

        if (gameSerialized.getExpertMode()) {
            initializeCharacterCards();
        } else {
            coin.setVisible(false);
            labelCoins.setVisible(false);
            switchHandMap.setScaleY(1.5);
            switchHandMap.setScaleX(1.5);
            toScoreboard.setScaleY(1.5);
            toScoreboard.setScaleX(1.5);
            switchHandMap.setLayoutX(832);
            switchHandMap.setLayoutY(406);
            toScoreboard.setLayoutX(833);
            toScoreboard.setLayoutY(590);
            labelHand.setLayoutX(877);
            labelHand.setLayoutY(485);
            labelScrbd.setLayoutX(850);
            labelScrbd.setLayoutY(610);
        }

        if (gameSerialized.getActiveNickname().equals(nickname))
            setActive(true);
        else
            setActive(false);

        resizeElements();
    }

    public void setOpponent1Card(int number) {
        assistentCardOpponent1.setImage(new Image(getClass().getResourceAsStream("/Graphical_Assets/Assistenti/2x/Assistente(" + number + ").png")));
    }

    public void setOpponent2Card(int number) {
        assistentCardOpponent2.setImage(new Image(getClass().getResourceAsStream("/Graphical_Assets/Assistenti/2x/Assistente(" + number + ").png")));
    }

    //passare numero isola precedente in senso orario
    public void build(int island) {
        switch (island) {
            case 1:
                build_1_2();
                break;
            case 2:
                build_2_3();
                break;
            case 3:
                build_3_4();
                break;
            case 4:
                build_4_5();
                break;
            case 5:
                build_5_6();
                break;
            case 6:
                build_6_7();
                break;
            case 7:
                build_7_8();
                break;
            case 8:
                build_8_9();
                break;
            case 9:
                build_9_10();
                break;
            case 10:
                build_10_11();
                break;
            case 11:
                build_11_12();
                break;
            case 12:
                build_12_1();
                break;
        }

    }

    private void build_1_2() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_1_2);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_1_2);
        tt.setFromY(bridge_1_2.getY() - 20);
        tt.setToY(bridge_1_2.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();

    }

    private void build_2_3() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_2_3);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_2_3);
        tt.setFromY(bridge_2_3.getY() - 20);
        tt.setToY(bridge_2_3.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_3_4() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_3_4);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_3_4);
        tt.setFromY(bridge_3_4.getY() - 20);
        tt.setToY(bridge_3_4.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();

    }

    private void build_4_5() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_4_5);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_4_5);
        tt.setFromY(bridge_4_5.getY() - 20);
        tt.setToY(bridge_4_5.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_5_6() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_5_6);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_5_6);
        tt.setFromY(bridge_5_6.getY() - 20);
        tt.setToY(bridge_5_6.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_6_7() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_6_7);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_6_7);
        tt.setFromY(bridge_6_7.getY() - 20);
        tt.setToY(bridge_6_7.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_7_8() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_7_8);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_7_8);
        tt.setFromY(bridge_7_8.getY() - 20);
        tt.setToY(bridge_7_8.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_8_9() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_8_9);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_8_9);
        tt.setFromY(bridge_8_9.getY() - 20);
        tt.setToY(bridge_8_9.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_9_10() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_9_10);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_9_10);
        tt.setFromY(bridge_9_10.getY() - 20);
        tt.setToY(bridge_9_10.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_10_11() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_10_11);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_10_11);
        tt.setFromY(bridge_10_11.getY() - 20);
        tt.setToY(bridge_10_11.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_11_12() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_11_12);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_11_12);
        tt.setFromY(bridge_11_12.getY() - 20);
        tt.setToY(bridge_11_12.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
    }

    private void build_12_1() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), bridge_1_12);
        FadeTransition ft = new FadeTransition(Duration.millis(1000), bridge_1_12);
        tt.setFromY(bridge_1_12.getY() - 20);
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

    public void disableClouds() {
        cloud1.setDisable(true);
        cloud2.setDisable(true);
    }

    public void enableClouds() {
        if (cloud1Student0.getImage() != null) cloud1.setDisable(false);
        if (cloud2Student0.getImage() != null) cloud2.setDisable(false);
    }

    public void selectCloudObserverNotification(int cloudNumber) {
        ArrayList<Cloud> finalCloud = new ArrayList<>();
        finalCloud.add(clouds.get(cloudNumber - 1));
        new Thread(() -> notifyObserver(obs -> obs.onUpdatePickCloud(finalCloud))).start();
        clouds.clear();
        if (cloudNumber == 1)
            clearCloud1();
        if (cloudNumber == 2)
            clearCloud2();
    }

    public void clearCloud1() {
        for (int i = 0; i < 4; i++)
            cloudStudents1[i].setImage(null);
    }

    public void clearCloud2() {
        for (int i = 0; i < 4; i++)
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

    public void setAssistants(List<AssistantCard> assistantCards) {
        for (AssistantCard assistantCard : assistantCards) {
            this.assistantCards[assistantCard.getValue() - 1].setVisible(true);
            this.assistantCards[assistantCard.getValue() - 1].setDisable(false);
        }

    }

    public void choosenAssistant(MouseEvent mouseEvent) {
        int assistantUsed = Integer.parseInt(mouseEvent.getSource().toString().substring(26, 27));

        assistantCards[assistantUsed].setMouseTransparent(true);
        //TranslateTransition tt = new TranslateTransition(Duration.millis(1000),assistantCards[assistantUsed]);
        FadeTransition ft = new FadeTransition(Duration.millis(600), assistantCards[assistantUsed]);
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
        for (AssistantCard a : assistantCardsList)
            if (a.getValue() == assistantUsed + 1)
                finalAssistantCard = a;

        playedAssistantCardsList.add(finalAssistantCard);
        AssistantCard finalAssistantCard1 = finalAssistantCard;
        new Thread(() -> notifyObserver(obs -> obs.onUpdatePlayAssistantCard(List.of(finalAssistantCard1), playedAssistantCardsList))).start();
        assistantCardsList.remove(finalAssistantCard);
        return;

    }

    public void outAssistant(MouseEvent mouseEvent) {
        assistantCards[Integer.parseInt(mouseEvent.getSource().toString().substring(26, 27))].setEffect(new DropShadow());
    }

    public void inAssistant(MouseEvent mouseEvent) {


        assistantCards[Integer.parseInt(mouseEvent.getSource().toString().substring(26, 27))].setEffect(new InnerShadow());
    }

    public void enableAssistant(List<AssistantCard> assistantCards) {
        this.assistantCardsList = assistantCards;
    }

    public void disableAssistant() {
        for (ImageView i : assistantCards) {
            i.setDisable(true);
        }
    }

    public void disableOpcard1() {
        opCard1 = false;
    }

    public void disableOpcard2() {
        opCard2 = false;
    }

    public void enableOpcard1() {
        opCard1 = true;
        if(!xButton.isDisabled())
            viewOpCard1();
    }

    public void enableOpcard2() {
        opCard2 = true;
        if(!xButton.isDisabled())
            viewOpCard2();
    }

    public void viewOpCard1() {
        opCard1 = true;

        FadeTransition ft = new FadeTransition(Duration.millis(600), assistentCardOpponent1);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), bgOpCard1);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), previous1lbl);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), nicknameOp1);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

    }

    public void viewOpCard2() {
        opCard2 = true;

        FadeTransition ft = new FadeTransition(Duration.millis(600), assistentCardOpponent2);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), bgOpCard2);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), previous2lbl);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), nicknameOp2);
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();

    }

    public void hideOpCard1() {

        FadeTransition ft = new FadeTransition(Duration.millis(600), assistentCardOpponent1);
        ft.setFromValue(assistentCardOpponent1.getOpacity());
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), bgOpCard1);
        ft.setFromValue(bgOpCard1.getOpacity());
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), previous1lbl);
        ft.setFromValue(previous1lbl.getOpacity());
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), nicknameOp1);
        ft.setFromValue(nicknameOp1.getOpacity());
        ft.setToValue(0);

        ft.play();

    }

    public void hideOpCard2() {

        FadeTransition ft = new FadeTransition(Duration.millis(600), assistentCardOpponent2);
        ft.setFromValue(assistentCardOpponent2.getOpacity());
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), bgOpCard2);
        ft.setFromValue(bgOpCard2.getOpacity());
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), previous2lbl);
        ft.setFromValue(previous2lbl.getOpacity());
        ft.setToValue(0);

        ft.play();

        ft = new FadeTransition(Duration.millis(600), nicknameOp2);
        ft.setFromValue(nicknameOp2.getOpacity());
        ft.setToValue(0);

        ft.play();

    }

    public void switchView(MouseEvent mouseEvent) {
        if (opCard1) viewOpCard1();
        if (opCard2) viewOpCard2();
        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);

        for (AssistantCard a : assistantCardsList) {
            this.assistantCards[a.getValue() - 1].setVisible(true);

            assistantCards[a.getValue() - 1].setMouseTransparent(false);
            TranslateTransition tt = new TranslateTransition(Duration.millis(600), assistantCards[a.getValue() - 1]);
            FadeTransition ft = new FadeTransition(Duration.millis(600), assistantCards[a.getValue() - 1]);
            tt.setFromY(assistantCards[a.getValue() - 1].getY() + 25);
            tt.setToY(assistantCards[a.getValue() - 1].getY());
            ft.setFromValue(0);
            ft.setToValue(1);

            ft.play();
            tt.play();
        }
        TranslateTransition tt = new TranslateTransition(Duration.millis(600), xButton);
        FadeTransition ft = new FadeTransition(Duration.millis(600), xButton);
        tt.setFromY(xButton.getY() + 25);
        tt.setToY(xButton.getY());
        ft.setFromValue(0);
        ft.setToValue(1);

        ft.play();
        tt.play();
        xButton.setDisable(false);

        ft = new FadeTransition(Duration.millis(600), switchHandMap);
        ft.setFromValue(1);
        ft.setToValue(0);
        switchHandMap.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600), toScoreboard);
        ft.setFromValue(1);
        ft.setToValue(0);
        toScoreboard.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600), labelHand);
        ft.setFromValue(1);
        ft.setToValue(0);
        labelHand.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600), labelScrbd);
        ft.setFromValue(1);
        ft.setToValue(0);
        labelScrbd.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600), coin);
        ft.setFromValue(1);
        ft.setToValue(0);
        coin.setDisable(true);
        ft.play();

        ft = new FadeTransition(Duration.millis(600), labelCoins);
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
        if (opCard1) hideOpCard1();
        if (opCard2) hideOpCard2();
        if (gameSerialized.getExpertMode()) initializeCharacterCards();

            for (AssistantCard a : assistantCardsList) {
                assistantCards[a.getValue() - 1].setMouseTransparent(true);
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), assistantCards[a.getValue() - 1]);
                FadeTransition ft = new FadeTransition(Duration.millis(600), assistantCards[a.getValue() - 1]);
                tt.setFromY(assistantCards[a.getValue() - 1].getY());
                tt.setToY(assistantCards[a.getValue() - 1].getY() + 25);
                ft.setFromValue(1);
                ft.setToValue(0);

                ft.play();
                tt.play();
            }
            TranslateTransition tt = new TranslateTransition(Duration.millis(600), xButton);
            FadeTransition ft = new FadeTransition(Duration.millis(600), xButton);
            tt.setFromY(xButton.getY());
            tt.setToY(xButton.getY() + 25);
            ft.setFromValue(xButton.getOpacity());
            ft.setToValue(0);

            ft.play();
            tt.play();

            xButton.setDisable(true);

            ft = new FadeTransition(Duration.millis(600), switchHandMap);
            ft.setFromValue(switchHandMap.getOpacity());
            ft.setToValue(1);
            switchHandMap.setDisable(false);
            ft.play();

            ft = new FadeTransition(Duration.millis(600), toScoreboard);
            ft.setFromValue(toScoreboard.getOpacity());
            ft.setToValue(1);
            toScoreboard.setDisable(false);
            ft.play();

            ft = new FadeTransition(Duration.millis(600), labelHand);
            ft.setFromValue(labelHand.getOpacity());
            ft.setToValue(1);
            labelHand.setDisable(false);
            ft.play();

            ft = new FadeTransition(Duration.millis(600), labelScrbd);
            ft.setFromValue(labelScrbd.getOpacity());
            ft.setToValue(1);
            labelScrbd.setDisable(false);
            ft.play();

            ft = new FadeTransition(Duration.millis(600), coin);
            ft.setFromValue(coin.getOpacity());
            ft.setToValue(1);
            coin.setDisable(false);
            ft.play();

            ft = new FadeTransition(Duration.millis(600), labelCoins);
            ft.setFromValue(labelCoins.getOpacity());
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
        et.setInput(new Glow());
        toScoreboard.setEffect(et);
    }

    public void outScrb(MouseEvent mouseEvent) {
        toScoreboard.setEffect(new DropShadow());
    }

    public void setCoin(int coin) {
        coins = coin;
        labelCoins.setText("X " + Integer.toString(coin));
    }

    public void inCard1(MouseEvent mouseEvent) {
        if (active && actionFase) {
            Glow iCard = new Glow(0.45);
            iCard.setInput(new DropShadow());

            card1.setEffect(iCard);
        }
    }

    public void outCard1(MouseEvent mouseEvent) {
        card1.setEffect(new DropShadow());
    }

    public void clickCard1(MouseEvent mouseEvent) {
        if (active && actionFase) {
            cardSelected(0);
        }
    }

    public void inCard2(MouseEvent mouseEvent) {
        if (active && actionFase) {
            Glow iCard = new Glow(0.45);
            iCard.setInput(new DropShadow());

            card2.setEffect(iCard);
        }
    }

    public void outCard2(MouseEvent mouseEvent) {
        card2.setEffect(new DropShadow());
    }

    public void clickCard2(MouseEvent mouseEvent) {
        if (active && actionFase) {
            cardSelected(1);
        }
    }

    public void inCard3(MouseEvent mouseEvent) {
        if (active && actionFase) {
            Glow iCard = new Glow(0.45);
            iCard.setInput(new DropShadow());

            card3.setEffect(iCard);
        }
    }

    public void outCard3(MouseEvent mouseEvent) {
        card3.setEffect(new DropShadow());
    }

    public void clickCard3(MouseEvent mouseEvent) {
        if (active && actionFase) {
            cardSelected(2);
        }
    }

    private void cardSelected(int choice) {
        int idCard = Integer.parseInt(cCards[choice].getId());
        for (ImageView im : cCards)
            im.setDisable(true);

        if (idCard == 209)
            for (int i = 0; i < 4; i++) {
                card209[i].setDisable(false);
                int finalI = i;
                card209[i].setOnMousePressed(ev -> {
                    finalStudentPos = finalI;
                    menuCard209();
                });
                card209[i].setOnMouseEntered(ev -> card209[finalI].setEffect(new Glow(0.6)));
                card209[i].setOnMouseExited(ev -> card209[finalI].setEffect(null));
            }

        if (idCard == 219)
            for (int i = 0; i < 4; i++) {
                card219[i].setDisable(false);
                int finalI = i;
                card219[i].setOnMousePressed(ev -> {
                    disableMenu();
                    disable219();
                    new Thread(() -> notifyObserver(obs -> obs.onUpdateUse219(finalI))).start();
                });
                card219[i].setOnMouseEntered(ev -> card219[finalI].setEffect(new Glow(0.6)));
                card219[i].setOnMouseExited(ev -> card219[finalI].setEffect(null));
            }

        if (idCard == 211)
            menuCard211();

        if (idCard == 213)
            menuCard213();

        if (idCard == 217)
            menuCard217();

        if (idCard == 220)
            menuCard220();

        switch (idCard) {
            case 210 -> new Thread(() -> notifyObserver(ViewObserver::onUpdateUse210)).start();
            case 212 -> new Thread(() -> notifyObserver(ViewObserver::onUpdateUse212)).start();
            case 214 -> new Thread(() -> notifyObserver(ViewObserver::onUpdateUse214)).start();
            case 216 -> new Thread(() -> notifyObserver(ViewObserver::onUpdateUse216)).start();
        }
    }

    private void disableMenu() {
        islandMenu.setDisable(true);
        islandMenu.setVisible(false);
        colorMenu.setDisable(true);
        colorMenu.setVisible(false);
    }

    private void disable209() {
        for (ImageView student : card209)
            student.setDisable(true);
    }

    private void disable219() {
        for (ImageView student : card219)
            student.setDisable(true);
    }

    private void menuCard209() {
        islandMenu.setVisible(true);
        islandMenu.setDisable(false);
        islandMenu.setLayoutX(getCharacterById(209).getLayoutX() - 17);
        islandMenu.setLayoutY(getCharacterById(209).getLayoutY() + 10);

        for (int j = 0; j < 12; j++) {
            int finalJ = j;
            islandMenu.getItems().get(j).setOnAction(event -> {
                System.out.println("carta 209 usata");
                useCard209(finalJ + 1);
            });
        }
    }

    private void useCard209(int islandSelected) {
        disableMenu();
        disable209();
        new Thread(() -> notifyObserver(obs -> obs.onUpdateUse209(finalStudentPos, islandSelected)));
    }

    private void menuCard211() {
        islandMenu.setVisible(true);
        islandMenu.setDisable(false);
        islandMenu.setLayoutX(getCharacterById(211).getLayoutX() - 17);
        islandMenu.setLayoutY(getCharacterById(211).getLayoutY() + 10);

        for (int j = 0; j < 12; j++) {
            int finalJ = j;
            islandMenu.getItems().get(j).setOnAction(event -> {
                disableMenu();
                new Thread(() -> notifyObserver(obs -> obs.onUpdateUse211(finalJ + 1))).start();
            });
        }
    }

    private void menuCard213() {
        islandMenu.setVisible(true);
        islandMenu.setDisable(false);
        islandMenu.setLayoutX(getCharacterById(213).getLayoutX() - 17);
        islandMenu.setLayoutY(getCharacterById(213).getLayoutY() + 10);

        for (int j = 0; j < 12; j++) {
            int finalJ = j;
            islandMenu.getItems().get(j).setOnAction(event -> {
                disableMenu();
                new Thread(() -> notifyObserver(obs -> obs.onUpdateUse213(finalJ + 1))).start();
            });
        }
    }

    private void menuCard217() {
        colorMenu.setVisible(true);
        colorMenu.setDisable(false);
        colorMenu.setLayoutX(getCharacterById(217).getLayoutX() - 17);
        colorMenu.setLayoutY(getCharacterById(217).getLayoutY() + 10);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            colorMenu.getItems().get(i).setOnAction(event -> {
                disableMenu();
                disable219();
                new Thread(() -> notifyObserver(obs -> obs.onUpdateUse217(PawnColors.values()[finalI]))).start();
            });
        }
    }

    private void menuCard220() {
        colorMenu.setVisible(true);
        colorMenu.setDisable(false);
        colorMenu.setLayoutX(getCharacterById(220).getLayoutX() - 17);
        colorMenu.setLayoutY(getCharacterById(220).getLayoutY() + 10);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            colorMenu.getItems().get(i).setOnAction(event -> {
                disableMenu();
                disable219();
                new Thread(() -> notifyObserver(obs -> obs.onUpdateUse220(PawnColors.values()[finalI]))).start();
            });
        }
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active) labelOpTurn.setVisible(false);
        else labelOpTurn.setVisible(true);
    }

    public void setActionFase(boolean actionFase) {
        this.actionFase = actionFase;
        if(actionFase) {
            Glow ef = new Glow();
            ef.setInput(new DropShadow());
            labelCrc.setEffect(ef);
        }else
            labelCrc.setEffect(new DropShadow());
    }

    public void resizeElements() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        Stage stage = (Stage) pane.getScene().getWindow();
        Scene scene = pane.getScene();

        System.out.println("Dimensioni schermo: " + width + " x " + height + " , Dimensioni scena: " + bg.getWidth() + " x " + bg.getHeight() + " , Dimensioni stage: " + stage.getWidth() + " x " + stage.getHeight());


        Scale scale = new Scale(scene.getWidth() / (bg.getWidth()), scene.getHeight() / (bg.getHeight()));
        scale.setPivotX(0);
        scale.setPivotY(0);
        pane.getScene().getRoot().getTransforms().setAll(scale);
        resizeElements2();
    }

    private void resizeElements2() {
        Scene scene = pane.getScene();

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    private class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;

        public SceneSizeChangeListener(Scene scene) {
            this.scene = scene;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {

            Scale scale = new Scale(scene.getWidth() / (bg.getWidth()), scene.getHeight() / (bg.getHeight()));
            scale.setPivotX(0);
            scale.setPivotY(0);
            pane.getScene().getRoot().getTransforms().setAll(scale);
        }
    }
}
