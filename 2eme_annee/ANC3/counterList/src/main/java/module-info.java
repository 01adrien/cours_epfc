module org.example.counterlist {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.counterlist to javafx.fxml;
    exports org.example.counterlist;
}