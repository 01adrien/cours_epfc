package org.example.counterlist.counter.view;

import org.example.counterlist.counter.model.CounterInList;
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

public class CounterView extends VBox {
    private final CounterInList counter;
    private final Runnable refreshGlobals;
    private final Runnable checkDuplicates;

    private final Label lblValue = new Label();
    private final Button btnPlus = new Button("+");
    private final Button btnMinus = new Button("-");
    private final Label errName = new Label("Trimmed length must be >= 3");
    private final Label errDuplicate = new Label("Name must be unique");
    private final TextField txtName = new TextField();

    public CounterView(CounterInList counter, Runnable refreshGlobals, Runnable checkDuplicates) {
        this.counter = counter;
        this.refreshGlobals = refreshGlobals;
        this.checkDuplicates = checkDuplicates;

        layoutControls();
        addListeners();

        // initial refresh
        refresh();
    }

    private void layoutControls() {
        setPadding(new Insets(10));
        setBorder(Border.EMPTY);
        setSpacing(10);

        lblValue.setPrefWidth(50);
        lblValue.setAlignment(Pos.CENTER);
        lblValue.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 18));

        btnPlus.setPrefWidth(50);
        btnMinus.setPrefWidth(50);

        txtName.setAlignment(Pos.CENTER);
        txtName.setMaxWidth(170);
        txtName.setText(counter.getName());

        errName.setTextFill(Color.INDIANRED);
        errName.setVisible(false);
        errName.setManaged(false);

        errDuplicate.setTextFill(Color.INDIANRED);
        errDuplicate.setVisible(false);
        errDuplicate.setManaged(false);

        VBox textBox = new VBox();
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().addAll(txtName, errName, errDuplicate);

        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setSpacing(10);
        buttonsBox.getChildren().addAll(btnMinus, lblValue, btnPlus);

        getChildren().addAll(textBox, buttonsBox);
    }

    private void addListeners() {
        btnPlus.setOnAction(e -> {
            counter.increment();
            refresh();
        });

        btnMinus.setOnAction(e -> {
            counter.decrement();
            refresh();
        });

        txtName.setOnKeyTyped(e -> {
            counter.setName(txtName.getText());
            refresh();
            checkDuplicates.run();
        });
    }

    private void refresh() {
        // valide le nom
        errName.setVisible(!counter.isValidName());
        errName.setManaged(!counter.isValidName());

        // valide la duplication
        refreshDuplicate();

        // détermine l'état des boutons
        btnMinus.setDisable(!counter.isValid() || counter.isMin());
        btnPlus.setDisable(!counter.isValid() || counter.isMax());

        // affiche la valeur du compteur
        lblValue.setText(String.valueOf(counter.getValue()));

        refreshGlobals.run();
    }

    public void refreshDuplicate() {
        errDuplicate.setVisible(counter.isDuplicate());
        errDuplicate.setManaged(counter.isDuplicate());
    }
}
