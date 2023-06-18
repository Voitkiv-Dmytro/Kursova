module com.example.buskursova {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.buskursova to javafx.fxml;
    exports com.example.buskursova.db;
    exports com.example.buskursova;
}