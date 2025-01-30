package eu.epfc.anc3.spreadsheet.view;

import eu.epfc.anc3.spreadsheet.viewmodel.SpreadsheetViewModel;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {


    public MainView(SpreadsheetViewModel viewModel) {
        this.setTop(new HeaderView(viewModel));
        this.setCenter(new MySpreadsheetView(viewModel));

    }
}
