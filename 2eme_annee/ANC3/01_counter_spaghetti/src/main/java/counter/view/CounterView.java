package counter.view;

import counter.model.CounterModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

public class CounterView extends VBox implements Observer {
    private final Label lblValue = new Label();
    private Button btnPlus;
    private Button btnMinus;
    private Label errName;
    private TextField txtName;
    CounterModel counterModel;

    public CounterView(CounterModel counterModel) {
        this.counterModel = counterModel;
        layoutControls();
        addListeners();
        refresh();
    }

    public void layoutControls() {

        lblValue.setPrefWidth(50);
        lblValue.setAlignment(Pos.CENTER);
        lblValue.setStyle("-fx-font-weight: bold; -fx-font-size: 18");

        btnPlus = new Button("+");
        btnPlus.setPrefWidth(50);

        btnMinus = new Button("-");
        btnMinus.setPrefWidth(50);

        VBox counterBox = new VBox();
        counterBox.setPadding(new Insets(10));
        counterBox.setBorder(Border.stroke(Color.BLACK));
        counterBox.setSpacing(10);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);

        VBox textBox = new VBox();
        textBox.setAlignment(Pos.CENTER);

        txtName = new TextField(counterModel.getName());
        txtName.setAlignment(Pos.CENTER);
        txtName.setMaxWidth(170);

        errName = new Label("Trimmed length must be >= 3");
        errName.setVisible(false);
        errName.setManaged(false);
        errName.setTextFill(Color.INDIANRED);

        textBox.getChildren().addAll(txtName, errName);
        buttonsBox.getChildren().addAll(btnMinus, lblValue, btnPlus);
        counterBox.getChildren().addAll(textBox, buttonsBox);
        getChildren().add(counterBox);
    }

    public void addListeners() {
        btnPlus.setOnAction(e -> {
            counterModel.increment();
        });

        btnMinus.setOnAction(e -> {
            counterModel.decrement();
        });

        txtName.setOnKeyTyped(e -> {
            counterModel.setName(txtName.getText());
        });
    }

    public void refresh() {

        boolean isValid = counterModel.isValidName();
        errName.setVisible(isValid);
        errName.setManaged(isValid);

        btnMinus.setDisable(isValid || counterModel.isMin());
        btnPlus.setDisable(isValid || counterModel.isMax());

        lblValue.setText(String.valueOf(counterModel.getValue()));
    }

    @Override
    public void update(Observable o, Object arg) {
        refresh();
    }
}
