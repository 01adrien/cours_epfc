package counter;

import counter.model.Counter;
import counter.tools.Logger;
import counter.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CounterApp extends Application {

    @Override
    public void start(Stage stage) {
        var model = new Counter("My Counter");
        var logger = new Logger(model);
        var view = new MainView(model);

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