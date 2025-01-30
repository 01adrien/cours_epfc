package eu.epfc.anc3.spreadsheet;


import com.tangorabox.componentinspector.fx.FXComponentInspectorHandler;
import eu.epfc.anc3.spreadsheet.model.SpreadsheetModel;
import eu.epfc.anc3.spreadsheet.view.MainView;
import eu.epfc.anc3.spreadsheet.viewmodel.SpreadsheetViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        SpreadsheetModel model = new SpreadsheetModel(10, 4);
        SpreadsheetViewModel viewModel = new SpreadsheetViewModel(model);
        MainView root = new MainView(viewModel);

        FXComponentInspectorHandler.handleAll();

        Scene scene = new Scene(root, 633, 315);
        primaryStage.setTitle("Spreadsheet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}