package org.example.anc3.view;

import org.example.anc3.model.Counter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.example.anc3.viewmodel.MainViewModel;

public class MainView extends VBox {
    public MainView(MainViewModel viewModel) {
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        getChildren().add(new CounterView(viewModel));
    }
}
