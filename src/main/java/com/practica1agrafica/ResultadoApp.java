package com.practica1agrafica;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ResultadoApp extends Application {

    public static class Resultado {
        private final String estrategia;
        private final String punto1;
        private final String punto2;
        private final double distancia;
        private final int calculadas;
        private final double tiempo;

        public Resultado(String estrategia, String punto1, String punto2, double distancia, int calculadas, double tiempo) {
            this.estrategia = estrategia;
            this.punto1 = punto1;
            this.punto2 = punto2;
            this.distancia = distancia;
            this.calculadas = calculadas;
            this.tiempo = tiempo;
        }

        public String getEstrategia() { return estrategia; }
        public String getPunto1() { return punto1; }
        public String getPunto2() { return punto2; }
        public double getDistancia() { return distancia; }
        public int getCalculadas() { return calculadas; }
        public double getTiempo() { return tiempo; }
    }

    @Override
    public void start(Stage stage) {
        TableView<Resultado> tableView = new TableView<>();

        TableColumn<Resultado, String> colEstrategia = new TableColumn<>("Estrategia");
        colEstrategia.setCellValueFactory(new PropertyValueFactory<>("estrategia"));

        TableColumn<Resultado, String> colPunto1 = new TableColumn<>("Punto 1");
        colPunto1.setCellValueFactory(new PropertyValueFactory<>("punto1"));

        TableColumn<Resultado, String> colPunto2 = new TableColumn<>("Punto 2");
        colPunto2.setCellValueFactory(new PropertyValueFactory<>("punto2"));

        TableColumn<Resultado, Double> colDistancia = new TableColumn<>("Distancia");
        colDistancia.setCellValueFactory(new PropertyValueFactory<>("distancia"));

        TableColumn<Resultado, Integer> colCalculadas = new TableColumn<>("Calculadas");
        colCalculadas.setCellValueFactory(new PropertyValueFactory<>("calculadas"));

        TableColumn<Resultado, Double> colTiempo = new TableColumn<>("Tiempo (mseg)");
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));

        tableView.getColumns().addAll(colEstrategia, colPunto1, colPunto2, colDistancia, colCalculadas, colTiempo);

        // Datos de ejemplo
        ObservableList<Resultado> data = FXCollections.observableArrayList(
                new Resultado("Exhaustivo", "(0,0)", "(1,1)", 1.414, 100, 0.01),
                new Resultado("Exhaustivo Poda", "(0,0)", "(2,2)", 2.828, 80, 0.02)
                // Añadir más resultados aquí
        );

        tableView.setItems(data);

        Scene scene = new Scene(tableView);
        stage.setScene(scene);
        stage.setTitle("Resultados");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
