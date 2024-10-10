public class Point implements Translatable {

    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public void translate(int moveX, int moveY) {
        this.x += moveX;
        this.y += moveY;
    }

}
