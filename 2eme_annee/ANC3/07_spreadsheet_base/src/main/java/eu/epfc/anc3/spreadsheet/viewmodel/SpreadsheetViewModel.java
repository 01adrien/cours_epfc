package eu.epfc.anc3.spreadsheet.viewmodel;

import eu.epfc.anc3.spreadsheet.model.SpreadsheetCellModel;
import eu.epfc.anc3.spreadsheet.model.SpreadsheetModel;
import javafx.beans.property.*;



public class SpreadsheetViewModel {

    private final SpreadsheetModel model;

    public SpreadsheetViewModel(SpreadsheetModel model) {
        this.model = model;

    }
    private SpreadsheetCellViewModel getCellViewModel(int line, int column) {
        return new SpreadsheetCellViewModel(model.getCell(line, column));
    }

    public int getRowCount() {
        return model.getRowCount();
    }

    public int getColumnCount() {
        return model.getColumnCount();
    }


    public SimpleObjectProperty<String> getCellValueProperty(int row, int column) {
        return (SimpleObjectProperty<String>) getCellViewModel(row, column).getCellValueProperty();
    }

    public void setCellValue(int line, int column, String value) {
        getCellViewModel(line, column).setCellValue(value);
    }

    public SimpleObjectProperty<String> inputValueProperty() {
        return model.inputValueProperty();
    }

    public void setSelectedCell(int row, int col) {

        if (model.getSelectedCell() != null) {
            model.getSelectedCell().valueProperty().unbindBidirectional(model.inputValueProperty());
        }
        model.setSelectedCell(row, col);
        getCellValueProperty(row, col).bindBidirectional(model.inputValueProperty());
        // model.setInput(model.getSelectedCell().valueProperty().get());

    }

    public void setInputValue(String value){
        model.setInput(value);
    }

    public SimpleObjectProperty<String> getSelectedCellProperty() {
        return model.getSelectedCell().valueProperty();
    }

    public boolean isSelectedCell(){
        return model.getSelectedCell() != null;
    }




























































































}
