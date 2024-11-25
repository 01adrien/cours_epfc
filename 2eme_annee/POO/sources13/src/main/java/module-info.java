module eu.epfc.labo13 {
    requires javafx.controls;
    requires javafx.fxml;

	opens eu.epfc.labo13.model to javafx.fxml;
    opens eu.epfc.labo13.view to javafx.fxml;

    exports eu.epfc.labo13.view;
}

