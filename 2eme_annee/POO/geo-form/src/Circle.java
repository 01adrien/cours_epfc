public class Circle implements TranScalable {

    Point p;
    int r;

    public Circle(int x, int y, int r) {
        this.p = new Point(x, y);
        this.r = r;
    }

    @Override
    public void translate(int moveX, int moveY) {
        this.p.translate(moveX, moveY);
    }

    @Override
    public void scale(int factor) {
        this.r *= factor;
    }

    @Override
    public String toString() {
        return "<Circle " + p.toString() + " <-> " + r + ">";
    }

}
