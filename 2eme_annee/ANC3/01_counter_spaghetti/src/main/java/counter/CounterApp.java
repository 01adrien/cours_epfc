package counter;

import counter.model.CounterModel;
import counter.view.CounterView;
import counter.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class CounterApp extends Application {



    @Override
    public void start(Stage stage) {
        CounterModel model = new CounterModel("My counter");
        VBox root = new MainView(model);

        Scene scene = new Scene(root);
        stage.setTitle("Counter");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}