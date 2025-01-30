package eu.epfc.anc3.spreadsheet.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.IntStream;

public class SpreadsheetModel {
    private final int rows;
    private final int columns;
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList();
    private final SimpleObjectProperty<String> inputProperty = new SimpleObjectProperty<>("");
    private SpreadsheetCellModel selectedCell;


    public SpreadsheetModel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        IntStream.range(0, rows).forEach(a -> addNewRow());
    }

    public int getRowCount() {
        return this.rows;
    }

    public int getColumnCount() {
        return this.columns;
    }

    public SpreadsheetCellModel getCell(int line, int column) {
        return data.get(line).get(column);
    }

    public SimpleObjectProperty<String> inputValueProperty () {
        return inputProperty;
    }

    public void setInput(String value) {
        inputProperty.set(value);
    }


    private void addNewRow() {
        ObservableList<SpreadsheetCellModel> newRow = FXCollections.observableArrayList();
        for (int column = 0; column < columns; column++) {
            newRow.add(new SpreadsheetCellModel("", data.size(), column));
        }
        data.add(newRow);
    }

    public void setSelectedCell(int row, int col) {
        SpreadsheetCellModel cell = getCell(row, col);
        selectedCell = cell;
    }

    public SpreadsheetCellModel getSelectedCell(){
        return selectedCell;
    }


}
