package eu.epfc.labo12.decoratedmicrowave;

import java.io.BufferedOutputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DecoratedMicrowave extends Application {


    private final BorderPane root = new BorderPane();
    private final VBox foodBox = new VBox();
    private final VBox commandBox = new VBox();
    private final TextField time = new TextField("time display here");
    private final GridPane buttons = new GridPane();


    @Override
    public void start(Stage primaryStage) {
        layout();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Micro Onde");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int getRow(int i) {
        if (i < 3) {
            return 0;
        } if (i >= 3 && i < 6) {
            return 1;
        }
        return 2;
    }

    private void layout(){
        foodBox.getChildren().add(new Label("Place food here"));
    
        for (int i = 0; i < 9; i++) {
            buttons.add(new Button(i + 1 + ""), i >= 3 ? i % 3 : i, getRow(i));    
        }
        
        buttons.add(new Button("0"), 0, 3);
        buttons.add(new Button("L"), 1, 3);
        buttons.add(new Button("S"), 2, 3);
        commandBox.getChildren().addAll(time, buttons);
        root.setCenter(foodBox);
        root.setRight(commandBox);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

