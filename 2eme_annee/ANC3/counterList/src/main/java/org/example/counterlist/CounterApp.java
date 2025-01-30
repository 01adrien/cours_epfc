package org.example.counterlist;

import org.example.counterlist.counter.model.CounterList;
import org.example.counterlist.counter.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CounterApp extends Application {

    @Override
    public void start(Stage stage) {
        var model = new CounterList();
        var view = new MainView(model);

        createScene(stage, view);
        stage.show();
    }

    private void createScene(Stage stage, MainView view) {
        Scene scene = new Scene(view);
        stage.setTitle("Counter");
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}