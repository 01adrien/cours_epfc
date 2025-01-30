package org.example.counterlist.counter.view;

import org.example.counterlist.counter.model.CounterList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainView extends VBox {

    private final CounterList counterList;

    private final Label lblTotal = new Label();
    private final Button btnAdd = new Button("Add");
    private final Button btnRemove = new Button("Remove Last");

    public MainView(CounterList counterList) {
        this.counterList = counterList;

        layoutControls();
        configureBindings();
    }

    private void layoutControls() {
        setPadding(new Insets(10));
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setFillWidth(false);

        lblTotal.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 22));

        CounterListView listView = new CounterListView(counterList, this::refreshGlobals);
        VBox.setVgrow(listView, Priority.ALWAYS);

        btnAdd.setPrefWidth(100);
        btnRemove.setPrefWidth(100);
        btnRemove.setDisable(counterList.getCounters().isEmpty());
        HBox buttonsBox = new HBox(btnAdd, btnRemove);
        buttonsBox.setSpacing(10);

        getChildren().addAll(lblTotal, listView, buttonsBox);
    }

    private void configureBindings() {
        btnAdd.setOnAction(e -> counterList.addCounter());

        btnRemove.setOnAction(e -> {
            counterList.removeCounter();
            refreshGlobals();
        });
    }

    private void refreshGlobals() {
        lblTotal.setText("Total = " + counterList.getTotal());
        btnRemove.setDisable(counterList.getCounters().isEmpty());
    }
}
