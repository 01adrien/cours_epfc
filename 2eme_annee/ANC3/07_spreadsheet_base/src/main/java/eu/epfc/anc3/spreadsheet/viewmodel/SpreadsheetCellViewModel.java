package eu.epfc.anc3.spreadsheet.viewmodel;

import eu.epfc.anc3.spreadsheet.model.SpreadsheetCellModel;
import javafx.beans.property.ReadOnlyObjectProperty;

public class SpreadsheetCellViewModel {
    private final SpreadsheetCellModel model;

    public SpreadsheetCellViewModel(SpreadsheetCellModel model) {
        this.model = model;
    }

    public ReadOnlyObjectProperty<String> getCellValueProperty() {
        return model.valueProperty();
    }

    public void setCellValue(String value) {
        model.set(value);
    }
}
