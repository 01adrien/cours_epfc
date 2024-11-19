package eu.epfc.labo12.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JavaFXCalc extends Application {

    public double num1 = 0;
    public double num2 = 0;
    public double res = 0;

    public TextField number1 = null;

    public TextField number2 = null;

    public TextField result = null;
    public boolean canDo = false;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setTop(inputsPane());
        root.setBottom(buttonsPane());
        Scene scene = new Scene(root, 500, 100);
        primaryStage.setTitle("JavaFX Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public HBox inputsPane() {
        HBox box = new HBox();
        HBox inputPane1 = inputPane("Number 1");
        box.getChildren().add(inputPane1);
        box.getChildren().add(inputPane("Number 2"));
        box.getChildren().add(inputPane("Result"));
        box.setAlignment(Pos.BASELINE_CENTER);
        return box;
    }

    public HBox buttonsPane() {
        HBox box = new HBox();
        box.setSpacing(20);
        Button add = new Button("Add");
        Button sub = new Button("Sub");
        Button mult = new Button("mult");
        Button div = new Button("div");
        box.getChildren().add(add);
        box.getChildren().add(sub);
        box.getChildren().add(mult);
        box.getChildren().add(div);
        box.setAlignment(Pos.BASELINE_CENTER);
        add.setOnAction((ActionEvent e) -> {
            if (canDo) {
                res = num1 + num2;
            }
        } );
        return box;
    }

    public HBox inputPane(String name){
        HBox box = new HBox();
        TextField textField = new TextField();
        textField.getText();
        textField.setPrefWidth(70);
        box.getChildren().add(new Label(name + "  "));
        box.getChildren().add(textField);
        box.setPadding(new Insets(5));
        return box;
    }


}
