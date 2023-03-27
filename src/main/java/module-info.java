module com.example.oop6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oop6 to javafx.fxml;
    exports com.example.oop6;
    exports com.example.oop6.models;
    opens com.example.oop6.models to javafx.fxml;
}