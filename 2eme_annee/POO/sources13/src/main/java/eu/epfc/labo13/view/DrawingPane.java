package eu.epfc.labo13.view;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import eu.epfc.labo13.model.Shape;
import eu.epfc.labo13.model.Circle;
import eu.epfc.labo13.model.Point;
import eu.epfc.labo13.model.Rectangle;
import eu.epfc.labo13.model.ShapeList;
import eu.epfc.labo13.model.Mode;
import javafx.scene.paint.Paint;


public class DrawingPane extends Pane {
    private final Canvas canvas = new Canvas();
    private final ShapeList shapeList;
    private Mode mode ;
    private Point startDraw;
    private Point endDraw;
    private Shape selectedShape;

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void setStartDraw(Point point) {
        this.startDraw = point;
    }


    public void setEndDraw(Point point) {
        this.endDraw = point;
    }


    public Point getStartDraw() {
        return startDraw;
    }

    public Point getEndDraw() {
        return endDraw;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(Shape shape) {
        selectedShape = shape;
    }

    public DrawingPane(ShapeList list) {
        this.shapeList = list;
        canvas.setCursor(Cursor.CROSSHAIR);
        super.getChildren().add(canvas);
    }

    void repaint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Shape f : shapeList) {
            f.paintOn(gc);
        }
    }

    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }

    @Override
    protected void layoutChildren() {
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
        repaint();
    }
}
