package it.polimi.ingsw.view.gui.scene;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MapSceneManager implements Initializable {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    double mulFactorW = width/1920;
    double mulFactorH = height/1080;

    @FXML
    private Rectangle bg;

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
    private AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bg.setHeight(bg.getHeight()*mulFactorH);
        bg.setWidth(bg.getWidth()*mulFactorW);
        island7.setFitWidth(island7.getFitWidth()*mulFactorW);
        island7.setFitHeight(island7.getFitHeight()*mulFactorH);
        System.out.println(width);
    }
}
