module com.practica1agrafica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.practica1agrafica to javafx.fxml;
    exports com.practica1agrafica;
}