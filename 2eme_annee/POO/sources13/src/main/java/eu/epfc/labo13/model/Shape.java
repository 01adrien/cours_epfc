package eu.epfc.labo13.model;

import javafx.scene.canvas.GraphicsContext;

public interface Shape {

    double getX();

    double getY();

    double getWidth();

    double getHeight();

    void move(double directionX, double directionY);

    void moveTo(double x, double y);

    boolean isOn(double x, double y);

    void paintOn(GraphicsContext gc);
}
