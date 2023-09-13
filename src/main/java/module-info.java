module org.example.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires lombok;


    opens org.example.ui to javafx.fxml;
    exports org.example.ui;
}