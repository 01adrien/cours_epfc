package eu.epfc.labo13.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import eu.epfc.labo13.model.Circle;
import eu.epfc.labo13.model.Point;
import eu.epfc.labo13.model.Rectangle;
import eu.epfc.labo13.model.Shape;
import eu.epfc.labo13.model.ShapeList;
import eu.epfc.labo13.model.Mode;

public class FXPaint extends Application {

    private final ShapeList shapeList = new ShapeList();
    private final BorderPane root = new BorderPane();
    private final HBox boxButtons = new HBox();
    private final Button btCircle = new Button("Add a Circle");
    private final Button btRectangle = new Button("Add a Rectangle");
    private final Button btMove = new Button("Move a Shape");
    private final Button btClear = new Button("Clear");
    private final DrawingPane drawingPane = new DrawingPane(shapeList);

    @Override
    public void start(Stage primaryStage) {
        configurePanes();
        addButtonsHandlers();
        addDrawingPaneListener();
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setTitle("FXPaint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurePanes() {
        boxButtons.setAlignment(Pos.CENTER);
        boxButtons.setSpacing(20);
        boxButtons.getChildren().addAll(btCircle, btRectangle, btMove, btClear);
        root.setBottom(boxButtons);
        root.setCenter(drawingPane);

    }

    private void addButtonsHandlers() {
        btClear.setOnAction((ActionEvent e) -> {
            shapeList.clear();
            drawingPane.repaint();
            drawingPane.setMode(Mode.NULL);
        });

        btRectangle.setOnAction((ActionEvent e) -> {
            drawingPane.setMode(Mode.RECTANGLE);
        });

        btCircle.setOnAction((ActionEvent e) -> {
            drawingPane.setMode(Mode.CIRCLE);
        });

        btMove.setOnAction((ActionEvent e) -> {
            drawingPane.setMode(Mode.MOVE);

        });

    }

    private void addDrawingPaneListener() {
        drawingPane.setOnMousePressed((MouseEvent e) -> {
            if (!drawingPane.getMode().equals(Mode.MOVE)) {   
                drawingPane.setStartDraw(new Point(e.getX(), e.getY()));
            }
            else {
                drawingPane.setSelectedShape(getSelectedShape(new Point(e.getX(), e.getY())));
            }
        });

        drawingPane.setOnMouseReleased((MouseEvent e) -> {
            if (!drawingPane.getMode().equals(Mode.MOVE)) {
                drawingPane.setEndDraw(new Point(e.getX(), e.getY()));
                drawShape();
            }
        });

        drawingPane.setOnMouseDragEntered((MouseEvent e) -> {
            if (drawingPane.getMode().equals(Mode.MOVE)) {
                drawingPane.setSelectedShape(getSelectedShape(new Point(e.getX(), e.getY())));
            }
        });

        drawingPane.setOnMouseDragged((MouseEvent e) -> {
            if (drawingPane.getMode().equals(Mode.MOVE)) {
                Shape shape = drawingPane.getSelectedShape();
                if (shape != null) {
                    shape.moveTo(e.getX(), e.getY());
                    drawingPane.repaint();
                }
            }
        });
    }

    public void drawShape() {
        switch (drawingPane.getMode()) {
            case RECTANGLE:
                Point start = drawingPane.getStartDraw();
                Point end = drawingPane.getEndDraw();
                double x = Math.min(start.x, end.x);
                double y = Math.min(start.y, end.y);
                double width = Math.abs(end.x - start.x);
                double height = Math.abs(end.y - start.y);
                Rectangle r = new Rectangle(x, y, width, height);
                shapeList.add(r);
                r.paintOn(drawingPane.getGraphicsContext());
                break;

            default:
                break;
        }
    }

    public Shape getSelectedShape(Point point) {
        for (Shape shape : shapeList) {
            if (shape.isOn(point.x, point.y)) {
                return shape;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
