package org.example.anc3;

import org.example.anc3.model.Counter;
import org.example.anc3.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.anc3.viewmodel.MainViewModel;

public class CounterApp extends Application {

    @Override
    public void start(Stage stage) {
        var model = new Counter("My Counter")
        var viewModel = new MainViewModel(model);
        var view = new MainView(viewModel);
        createScene(stage, view);
        stage.show();
    }

    private void createScene(Stage stage, MainView view) {
        Scene scene = new Scene(view);
        stage.setTitle("Counter");
        stage.setHeight(160);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}