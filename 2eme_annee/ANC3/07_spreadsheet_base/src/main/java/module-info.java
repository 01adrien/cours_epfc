module eu.epfc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires component.inspector.fx;

    exports eu.epfc.anc3.spreadsheet;
    opens eu.epfc.anc3.spreadsheet to javafx.fxml;
}