package eu.epfc.anc3.spreadsheet;


import com.tangorabox.componentinspector.fx.FXComponentInspectorHandler;
import eu.epfc.anc3.spreadsheet.model.SpreadsheetModel;
import eu.epfc.anc3.spreadsheet.view.MainView;
import eu.epfc.anc3.spreadsheet.viewmodel.SpreadsheetViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

// TEST
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

/*
@Override
    public void start(Stage primaryStage) {
        TableView<RowData> tableView = new TableView<>();
        TableColumn<RowData, String> column = new TableColumn<>("Cell");
        column.setCellValueFactory(data -> data.getValue().valueProperty());

        // Permettre l'édition dans la TableView
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setEditable(true);
        tableView.getColumns().add(column);
        tableView.setEditable(true);

        // Ajouter des données
        tableView.getItems().addAll(new RowData("A1"), new RowData("B1"), new RowData("C1"));

        // Barre de formule
        TextField formulaBar = new TextField();

        // Mettre à jour la barre quand une cellule est sélectionnée
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                formulaBar.textProperty().unbind();
                formulaBar.setText(newSelection.getValue());

                // Synchronisation bidirectionnelle
                formulaBar.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        tableView.getSelectionModel().getSelectedItem().setValue(newValue);
                    }
                });
            } else {
                formulaBar.clear();
            }
        });

        VBox root = new VBox(formulaBar, tableView);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Excel-like JavaFX");
        primaryStage.show();
    }

    public static class RowData {
        private final StringProperty value = new SimpleStringProperty();

        public RowData(String value) {
            this.value.set(value);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }

        public StringProperty valueProperty() {
            return value;
        }
    }
 */