package com.practica1agrafica;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerComparaDosEstrategias implements Initializable {

    private class Resultado {
        private Integer talla;
        private Double tiempoAlgoritmo1;
        private Integer distanciaAlgoritmo1;
        private Double tiempoAlgoritmo2;
        private Integer distanciaAlgoritmo2;

        public Resultado(int talla, double tiempoAlgoritmo1, int distanciaAlgoritmo1, double tiempoAlgoritmo2, int distanciaAlgoritmo2) {
            this.talla = talla;
            this.tiempoAlgoritmo1 = tiempoAlgoritmo1;
            this.distanciaAlgoritmo1 = distanciaAlgoritmo1;
            this.tiempoAlgoritmo2 = tiempoAlgoritmo2;
            this.distanciaAlgoritmo2 = distanciaAlgoritmo2;
        }

        // Getters y Setters para cada campo
        public Integer getTalla() { return talla; }
        public Double getTiempoAlgoritmo1() { return tiempoAlgoritmo1; }
        public Integer getDistanciaAlgoritmo1() { return distanciaAlgoritmo1; }
        public Double getTiempoAlgoritmo2() { return tiempoAlgoritmo2; }
        public Integer getDistanciaAlgoritmo2() { return distanciaAlgoritmo2; }
    }


    @FXML
    private ComboBox<String> comb1;

    @FXML
    private ComboBox<String> comb2;

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comb1.setItems(FXCollections.observableArrayList("Exhaustivo", "Exhaustivo Poda", "Divide y venceras", "Divide y Venceras Mejorado"));
        comb2.setItems(FXCollections.observableArrayList("Exhaustivo", "Exhaustivo Poda", "Divide y venceras", "Divide y Venceras Mejorado"));
    }

    @FXML
    public void compararDosEstrategias() {
        String algoritmo1 = comb1.getSelectionModel().getSelectedItem();
        String algoritmo2 = comb2.getSelectionModel().getSelectedItem();

        if (algoritmo1 == null || algoritmo2 == null) {
            showAlert("Selecciona ambos algoritmos.");
            return;
        }

        // Crear TableView para mostrar los resultados
        TableView<Resultado> tableView = new TableView<>();

        TableColumn<Resultado, Integer> colTalla = new TableColumn<>("Talla");
        colTalla.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTalla()));

        TableColumn<Resultado, Double> colTiempo1 = new TableColumn<>("Tiempo " + algoritmo1);
        colTiempo1.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTiempoAlgoritmo1()));

        TableColumn<Resultado, Integer> colDistancia1 = new TableColumn<>("Distancia " + algoritmo1);
        colDistancia1.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDistanciaAlgoritmo1()));

        TableColumn<Resultado, Double> colTiempo2 = new TableColumn<>("Tiempo " + algoritmo2);
        colTiempo2.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTiempoAlgoritmo2()));

        TableColumn<Resultado, Integer> colDistancia2 = new TableColumn<>("Distancia " + algoritmo2);
        colDistancia2.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDistanciaAlgoritmo2()));

        tableView.getColumns().addAll(colTalla, colTiempo1, colDistancia1, colTiempo2, colDistancia2);

        ObservableList<Resultado> data = FXCollections.observableArrayList();

        // Lógica para simular datos
        for (int i = 1000; i <= 5000; i += 1000) {
            data.add(new Resultado(i, obtenerTiempo(algoritmo1), obtenerDistancia(algoritmo1), obtenerTiempo(algoritmo2), obtenerDistancia(algoritmo2)));
        }

        tableView.setItems(data);

        // Crear nueva pestaña para mostrar resultados
        Tab resultTab = new Tab("Resultados de Comparación");
        VBox vbox = new VBox(tableView);
        resultTab.setContent(vbox);
        tabPane.getTabs().add(resultTab);
        tabPane.getSelectionModel().select(resultTab);
    }

    private double obtenerTiempo(String algoritmo) {
        // Lógica de obtención de tiempo simulada
        return Math.random() * 100;
    }

    private int obtenerDistancia(String algoritmo) {
        // Lógica de obtención de distancia simulada
        return (int) (Math.random() * 1000);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void salir(ActionEvent e) {
        Node source = (Node) e.getSource();     //Me devuelve el elemento al que hice click
        Stage stage = (Stage) source.getScene().getWindow();    //Me devuelve la ventana donde se encuentra el elemento
        stage.close();
    }


}