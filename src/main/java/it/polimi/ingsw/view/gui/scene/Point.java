package it.polimi.ingsw.view.gui.scene;

/**
 * Class that represent a measure in the space of the scene of the MapSceneManager.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Default constructor.
     * @param x parameter x.
     * @param y parameter y.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return parameter x.
     */
    public double getX() {
        return x;
    }

    /**
     * @return parameter y.
     */
    public double getY() {
        return y;
    }
}
