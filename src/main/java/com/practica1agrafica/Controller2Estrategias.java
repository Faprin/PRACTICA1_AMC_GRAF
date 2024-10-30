package com.practica1agrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller2Estrategias implements Initializable {

    @FXML
    private ComboBox<String> comb1;

    @FXML
    private ComboBox<String> comb2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Exhaustivo", "Exhaustivo Poda", "Divide y Venceras", "Divide y Venceras Mejorado");
        comb1.setItems(list);
        comb2.setItems(list);
    }

    @FXML
    void comparar() {
        String Algorimo1 = comb1.getSelectionModel().getSelectedItem();
        String Algorimo2 = comb2.getSelectionModel().getSelectedItem();


    }

    @FXML
    void volver() {
        // boton de volver
    }
}
