package eu.epfc.labo13.model;

public class Point {
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean before(Point point) {
        return this.x < point.x;
    }
}
