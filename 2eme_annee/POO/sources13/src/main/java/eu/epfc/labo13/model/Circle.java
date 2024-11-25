package eu.epfc.labo13.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape {

    private double x;  // Le coin supérieur gauche du carré englobant
    private double y;
    private double radius;

    public Circle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return 2 * radius;
    }

    @Override
    public double getHeight() {
        return getWidth();
    }

    @Override
    public void move(double directionX, double directionY) {
        x += directionX;
        y += directionY;
    }

    @Override
    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isOn(double x, double y) {
        double xCenter = this.x + radius;
        double yCenter = this.y + radius;
        return (xCenter - x) * (xCenter - x) + (yCenter - y) * (yCenter - y) <= radius * radius;
    }

    @Override
    public void paintOn(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }

}
