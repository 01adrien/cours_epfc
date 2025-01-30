package eu.epfc.anc3.spreadsheet.view;

import eu.epfc.anc3.spreadsheet.tools.ExcelConverter;
import eu.epfc.anc3.spreadsheet.viewmodel.SpreadsheetViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.util.Objects;


public class MySpreadsheetView extends SpreadsheetView {
    private static final int CELL_PREF_WIDTH = 150;
    private final SpreadsheetViewModel viewModel;
    private final GridBase grid;


    public MySpreadsheetView(SpreadsheetViewModel viewModel) {
        this.viewModel = viewModel;

        this.grid = createGridAndBindings();
        this.setGrid(this.grid);

        layoutSpreadSheet();
    }


    private void layoutSpreadSheet() {
        for (int column = 0; column < grid.getColumnCount(); column++) {
            this.getColumns().get(column).setPrefWidth(CELL_PREF_WIDTH);
        }
    }

    private GridBase createGridAndBindings() {
        GridBase grid = new GridBase(viewModel.getRowCount(), viewModel.getColumnCount());

        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        addMouseListener();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCell cell = SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "");

                int finalRow = row;
                int finalColumn = column;
                cell.itemProperty().addListener((observableValue, oldVal, newVal) -> {
                    if(!Objects.equals(oldVal, newVal)) {
                        viewModel.setCellValue(finalRow, finalColumn, (String) newVal);

                        System.out.println("DEBUG : change view " + newVal);
                        // viewModel.addAction("DEBUG : change view " + newVal); // TODO : remove
                    }
                });



                viewModel.getCellValueProperty(finalRow, finalColumn).addListener((observableValue, oldVal, newVal) -> {
                    if(!Objects.equals(oldVal, newVal)) {
                        cell.itemProperty().set(newVal);
                        System.out.println("DEBUG : change model " + newVal);
                        // viewModel.addAction("DEBUG : change model " + newVal); // TODO : remove
                    }
                });

                list.add(cell);
            }
            rows.add(list);
        }
        grid.setRows(rows);
        return grid;
    }

    private void addMouseListener() {
        this.getSelectionModel().getSelectedCells().addListener((ListChangeListener<? super TablePosition>) change -> {
            while (change.next()) {
                if (change.wasAdded() && !this.getSelectionModel().getSelectedCells().isEmpty()) {
                TablePosition<?,?> selectedCell = this.getSelectionModel().getSelectedCells().get(0);
                int selectedRow = selectedCell.getRow();
                int selectedColumn = selectedCell.getColumn();

                System.out.println("Cellule sélectionnée : Ligne " + selectedRow + ", Colonne " + selectedColumn);

                // Met à jour la sélection dans le ViewModel
                viewModel.setSelectedCell(selectedRow, selectedColumn);
                viewModel.setInputValue("kkkklklk");
                }
            }
        });
    }
}
