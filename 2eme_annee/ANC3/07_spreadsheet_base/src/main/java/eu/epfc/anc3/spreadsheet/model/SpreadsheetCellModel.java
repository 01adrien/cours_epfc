package eu.epfc.anc3.spreadsheet.model;

import eu.epfc.anc3.spreadsheet.tools.ExcelConverter;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SpreadsheetCellModel {
    private final SimpleObjectProperty<String> valueProperty = new SimpleObjectProperty<>();
    private final int row;
    private final int column;

    public SpreadsheetCellModel(String value, int row, int column) {
        this.valueProperty.set(value);
        this.row = row;
        this.column = column;
    }

    public SimpleObjectProperty<String> valueProperty() {
        return this.valueProperty;
    }

    public void set(String value) {
        this.valueProperty.set(value);
    }

    public String toString() {
        return "Cell " + ExcelConverter.rowColToExcel(this.row, this.column) + " (row " + this.row + ", column " + this.column + ") = \"" + this.valueProperty.get() + "\"";
    }


}
