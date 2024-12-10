package counter.view;

import counter.model.CounterModel;
import counter.tools.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;


public class MainView extends VBox {
    public MainView(CounterModel model) {
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);
        setFillWidth(false);
        CounterView counterView = new CounterView(model);
        Logger logger = new Logger(model);
        model.addObserver(counterView);
        model.addObserver(logger);
        getChildren().add(counterView);
    }
}
