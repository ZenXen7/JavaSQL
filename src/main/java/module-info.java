module com.example.passwordcracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.passwordcracker to javafx.fxml;
    exports com.example.passwordcracker;

    exports JavaFXSQL; // Export the package containing your database-related classes
}

