module com.example.oop6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oop6 to javafx.fxml;
    exports com.example.oop6;
    exports com.example.oop6.models.field;
    opens com.example.oop6.models.field to javafx.fxml;
    exports com.example.oop6.models.field.commands;
    opens com.example.oop6.models.field.commands to javafx.fxml;
}