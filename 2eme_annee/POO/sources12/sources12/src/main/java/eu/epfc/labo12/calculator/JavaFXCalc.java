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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXCalc extends Application {

    private final Label labelNumber1 = new Label("Number 1:");
    private final TextField textFieldNumber1 = new TextField();
    private final Label labelNumber2 = new Label("Number 2:");
    private final TextField textFieldNumber2 = new TextField();
    private final Label labelResult = new Label("Result:");
    private final TextField textFieldResult = new TextField();
    private final Button buttonAdd = new Button("Add");
    private final Button buttonSubstract = new Button("Substract");
    private final Button buttonMultiply = new Button("Multiply");
    private final Button buttonDivide = new Button("Divide");

    private final VBox root = new VBox();
    private final HBox boxNumbers = new HBox();
    private final HBox boxButtons = new HBox();

    @Override
    public void start(Stage primaryStage) {
        layout();
        Scene scene = new Scene(root);
        manageButtons();
        primaryStage.setTitle("JavaFX Calc");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void manageButtons() {
        EventHandler<ActionEvent> handler = (ActionEvent event) -> {
            double number1 = Double.valueOf(textFieldNumber1.getText());
            double number2 = Double.valueOf(textFieldNumber2.getText());

            if (event.getSource() == buttonAdd)
                textFieldResult.setText(number1 + number2 + "");
            else if (event.getSource() == buttonSubstract)
                textFieldResult.setText(number1 - number2 + "");
            else if (event.getSource() == buttonMultiply)
                textFieldResult.setText(number1 * number2 + "");
            else if (event.getSource() == buttonDivide)
                textFieldResult.setText(number1 / number2 + "");
        };
        buttonAdd.setOnAction(handler);
        buttonSubstract.setOnAction(handler);
        buttonMultiply.setOnAction(handler);
        buttonDivide.setOnAction(handler);
    }

    private void layout() {
        boxNumbers.getChildren().addAll(labelNumber1, textFieldNumber1, labelNumber2,
                textFieldNumber2, labelResult, textFieldResult);

        boxButtons.getChildren().addAll(buttonAdd, buttonSubstract, buttonMultiply,
                buttonDivide);

        root.getChildren().addAll(boxNumbers, boxButtons);
        beautify();
    }

    private void beautify() {
        textFieldNumber1.setPrefColumnCount(3);
        textFieldNumber2.setPrefColumnCount(3);
        textFieldResult.setPrefColumnCount(3);

        boxNumbers.setSpacing(10);
        boxNumbers.setPadding(new Insets(5, 5, 0, 5));
        boxNumbers.setAlignment(Pos.CENTER);

        boxButtons.setSpacing(10);
        boxButtons.setPadding(new Insets(5, 5, 5, 5));
        boxButtons.setAlignment(Pos.CENTER);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
