package com.practica1agrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerComparaDosEstrategias implements Initializable {

    @FXML
    private ComboBox<String> comb1;

    @FXML
    private ComboBox<String> comb2;

    @FXML
    void select1(ActionEvent event) {

    }

    @FXML
    void select2(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comb1.setItems(FXCollections.observableArrayList("Exhaustivo", "Exhaustivo Poda", "Divide y venceras", "Divide y Venceras Mejorado"));
        comb2.setItems(FXCollections.observableArrayList("Exhaustivo", "Exhaustivo Poda", "Divide y venceras", "Divide y Venceras Mejorado"));
    }
}