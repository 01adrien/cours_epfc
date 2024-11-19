module eu.epfc.labo12 {
    requires javafx.controls;
    requires javafx.fxml;

    opens eu.epfc.labo12.calculator to javafx.fxml;
    opens eu.epfc.labo12.microwave to javafx.fxml;
    opens eu.epfc.labo12.decoratedmicrowave to javafx.fxml;
    exports eu.epfc.labo12.calculator;
    exports eu.epfc.labo12.microwave;
    exports eu.epfc.labo12.decoratedmicrowave;
}