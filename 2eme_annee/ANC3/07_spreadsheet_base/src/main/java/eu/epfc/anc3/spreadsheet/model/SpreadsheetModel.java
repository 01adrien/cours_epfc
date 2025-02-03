package eu.epfc.anc3.spreadsheet.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.IntStream;

public class SpreadsheetModel {
    private final int rows;
    private final int columns;
    private final ObservableList<ObservableList<SpreadsheetCellModel>> data = FXCollections.observableArrayList();
    private final SimpleStringProperty inputProperty = new SimpleStringProperty("");
    private SpreadsheetCellModel selectedCell;


    public SpreadsheetModel(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        IntStream.range(0, rows).forEach(a -> addNewRow());
    }

    public int getRowCount() {
        return rows;
    }

    public int getColumnCount() {
        return columns;
    }

    public SpreadsheetCellModel getCell(int line, int column) {
        return data.get(line).get(column);
    }

    public SimpleStringProperty inputValueProperty () {
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
        selectedCell = getCell(row, col);
    }

    public SpreadsheetCellModel getSelectedCell(){
        return selectedCell;
    }


}
