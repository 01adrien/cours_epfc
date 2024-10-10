public class Rectangle implements TranScalable {

    Point p;
    int lg, ht;

    public Rectangle(int x, int y, int lg, int ht) {
        this.p = new Point(x, y);
        this.lg = lg;
        this.ht = ht;
    }

    @Override
    public void scale(int factor) {
        this.ht *= factor;
        this.lg *= factor;
    }

    @Override
    public void translate(int moveX, int moveY) {
        this.p.translate(moveX, moveY);
    }

    @Override
    public String toString() {
        return "<Rectangle " + p.toString() + " <-> " + lg + " | " + ht + ">";
    }

}
