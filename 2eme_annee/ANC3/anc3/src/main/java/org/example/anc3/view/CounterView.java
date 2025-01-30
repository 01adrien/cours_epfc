package org.example.anc3.view;

import javafx.beans.binding.BooleanBinding;
import org.example.anc3.model.Counter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.anc3.viewmodel.CounterViewModel;
import org.example.anc3.viewmodel.MainViewModel;

public class CounterView extends VBox {
    private final CounterViewModel viewModel ;

    private final Label lblValue = new Label();
    private final Button btnPlus = new Button("+");
    private final Button btnMinus = new Button("-");
    private final Label errName = new Label("Trimmed length must be >= 3");
    private final TextField txtName = new TextField();
    private final BooleanBinding minusDisabled;
    private final BooleanBinding plusDisabled;

    public CounterView(MainViewModel vm) {
        viewModel = vm.counterViewModel;
        minusDisabled = viewModel.minusDisabled();
        plusDisabled = viewModel.plusDisabled();
        layoutControls();
        addListeners();
        configureBindings();
    }

    private void layoutControls() {
        setPadding(new Insets(10));
        setBorder(Border.EMPTY);
        setSpacing(10);

        lblValue.setPrefWidth(50);
        lblValue.setAlignment(Pos.CENTER);
        lblValue.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 18));
        lblValue.setText(String.valueOf(viewModel.getValue()));

        btnPlus.setPrefWidth(50);
        btnMinus.setPrefWidth(50);

        txtName.setAlignment(Pos.CENTER);
        txtName.setMaxWidth(170);
        txtName.setText(viewModel.getName());

        errName.setTextFill(Color.INDIANRED);
        errName.setVisible(false);
        errName.setManaged(false);

        VBox textBox = new VBox();
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().addAll(txtName, errName);

        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.getChildren().addAll(btnMinus, lblValue, btnPlus);

        getChildren().addAll(textBox, buttonsBox);
    }

    private void addListeners() {
        btnPlus.setOnAction(e -> viewModel.increment());
        btnMinus.setOnAction(e -> viewModel.decrement());
    }

    private void configureBindings() {
        txtName.textProperty().bindBidirectional(viewModel.nameProperty());

        lblValue.textProperty().bind(viewModel.valueProperty().asString());

        errName.visibleProperty().bind(viewModel.isValidNameProperty().not());
        errName.managedProperty().bind(errName.visibleProperty());

        btnMinus.disableProperty().bind(minusDisabled);

        btnPlus.disableProperty().bind(plusDisabled);
    }

}
