package eu.epfc.anc3.spreadsheet.viewmodel;

import eu.epfc.anc3.spreadsheet.model.SpreadsheetCellModel;
import eu.epfc.anc3.spreadsheet.model.SpreadsheetModel;
import javafx.beans.property.*;



public class SpreadsheetViewModel {

    private final SpreadsheetModel model;
    private  final SimpleBooleanProperty inputDisabled = new SimpleBooleanProperty(true);

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

    public SimpleStringProperty inputValueProperty() {
        return model.inputValueProperty();
    }

    public void setSelectedCell(int row, int col) {

        if (isSelectedCell()) {
            getSelectedCellProperty().unbindBidirectional(model.inputValueProperty());
        }
        model.setSelectedCell(row, col);
        model.setInput(getSelectedCellProperty().getValue());
        getSelectedCellProperty().bindBidirectional(model.inputValueProperty());

    }

    public void setInputValue(String str){
        model.setInput(str);
    }

    public SimpleObjectProperty<String> getSelectedCellProperty() {
        return model.getSelectedCell().valueProperty();
    }

    public boolean isSelectedCell(){
        return model.getSelectedCell() != null;
    }




























































































}
