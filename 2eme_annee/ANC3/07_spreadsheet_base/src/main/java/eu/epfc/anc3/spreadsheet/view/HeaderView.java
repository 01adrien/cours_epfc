package eu.epfc.anc3.spreadsheet.view;


import eu.epfc.anc3.spreadsheet.viewmodel.SpreadsheetViewModel;
import javafx.beans.binding.Binding;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class HeaderView extends VBox {
    final SpreadsheetViewModel viewModel;



    public HeaderView(SpreadsheetViewModel viewModel) {
        this.viewModel = viewModel;
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.manageComponent();
    }

    private void manageComponent() {
        TextField input = new TextField("");
        input.textProperty().bindBidirectional(viewModel.inputValueProperty());

        /*
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!Objects.equals(oldValue, newValue) && viewModel.isSelectedCell()) {
                viewModel.getSelectedCellProperty().set(newValue);

                // viewModel.addAction("DEBUG : change view " + newVal); // TODO : remove
            }
        });

*/
        this.getChildren().add(input);
    }
}
