package com.practica1agrafica;

import consoleApp.Algoritmos;
import consoleApp.Punto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import javafx.scene.control.TextArea;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller2Estrategias implements Initializable {

    public static ArrayList<Punto> copiaArray(ArrayList<Punto> origen) {
        ArrayList destino = new ArrayList();
        for (int i = 0; i < origen.size(); i++) {
            destino.add(origen.get(i));
        }

        return destino;
    }

    public static ArrayList<Punto> generaDatasetPorTalla(int talla) {
        // tenemos que verificar si la variable peorCaso esta a true
        Random random = new Random(System.nanoTime());
        ArrayList<Punto> datos = new ArrayList();
        int num = 0, den = 0;
        double x, y;
        if (!ControllerMenuPrincipal.getPeorCaso()) {
            for (int i = 0; i < talla; i++) {
                num = random.nextInt(4000 - 1 + 1) + 1; // entre 1 y 4000
                den = random.nextInt(17 - 7 + 1) + 7; // entre 1 y 17
                x = num / ((double) den + 0.37);
                y = (random.nextInt(4000 - 1 + 1) + 1) / ((double) (random.nextInt(17 - 7 + 1) + 7) + 0.37);
                datos.add(new Punto());
                datos.get(i).setId(i + 1);
                datos.get(i).setX(x);
                datos.get(i).setY(y);
            }
        } else {
            // misma coordenada x
            x = random.nextDouble(501);
            for (int i = 0; i < talla; i++) {
                int aux1 = random.nextInt(1000) + 7;
                y = aux1 / ((double) i + 1 + i * 0.100);
                num = random.nextInt(3);
                y += (i % 500) - num * (random.nextInt(100));
                datos.add(new Punto());
                datos.get(i).setId(i + 1);
                datos.get(i).setX(x);
                datos.get(i).setY(y);
            }
        }

        return datos;
    }

    @FXML
    private ComboBox<String> comb1;

    @FXML
    private ComboBox<String> comb2;

    @FXML
    private TextArea resultadoTextArea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Exhaustivo", "Exhaustivo Poda", "Divide y Venceras", "Divide y Venceras Mejorado");
        comb1.setItems(list);
        comb2.setItems(list);
    }

    @FXML
    void comparar() {
        String algoritmo1 = comb1.getSelectionModel().getSelectedItem();
        String algoritmo2 = comb2.getSelectionModel().getSelectedItem();

        if (algoritmo1 == null || algoritmo2 == null) {
            System.out.println("Por favor, selecciona dos algoritmos para comparar.");
            return;
        }

        double[] tiempoAlg1 = new double[5];
        double[] tiempoAlg2 = new double[5];
        int calculadasAlg1 = 0;
        int calculadasAlg2 = 0;
        int iter = 0;

        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("%-20s%-40s%-40s\n", "", algoritmo1, algoritmo2));
        resultado.append(String.format("%-10s%-25s%-25s%-25s%-25s\n", "Talla", "Tiempo", "Distancia", "Tiempo", "Distancia"));

        for (int i = 1000; i <= 5000; i += 1000) {
            for (int j = 0; j < 10; j++) {
                ArrayList<Punto> dataset = generaDatasetPorTalla(i);

                // Comparar algoritmo1
                switch (algoritmo1) {
                    case "Exhaustivo" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg1[iter] += (endTime - startTime);
                        calculadasAlg1 += Algoritmos.getContador();
                    }
                    case "Exhaustivo Poda" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg1[iter] += (endTime - startTime);
                        calculadasAlg1 += Algoritmos.getContador();
                    }
                    case "Divide y Venceras" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.quickSort(estrategias, 'x');
                        Algoritmos.dyv(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg1[iter] += (endTime - startTime);
                        calculadasAlg1 += Algoritmos.getContador();
                    }
                    case "Divide y Venceras Mejorado" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.quickSort(estrategias, 'x');
                        Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg1[iter] += (endTime - startTime);
                        calculadasAlg1 += Algoritmos.getContador();
                    }
                }

                // Comparar algoritmo2
                switch (algoritmo2) {
                    case "Exhaustivo" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg2[iter] += (endTime - startTime);
                        calculadasAlg2 += Algoritmos.getContador();
                    }
                    case "Exhaustivo Poda" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg2[iter] += (endTime - startTime);
                        calculadasAlg2 += Algoritmos.getContador();
                    }
                    case "Divide y Venceras" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.quickSort(estrategias, 'x');
                        Algoritmos.dyv(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg2[iter] += (endTime - startTime);
                        calculadasAlg2 += Algoritmos.getContador();
                    }
                    case "Divide y Venceras Mejorado" -> {
                        ArrayList<Punto> estrategias = copiaArray(dataset);
                        Algoritmos.resetContador();
                        double startTime = System.nanoTime();
                        Algoritmos.quickSort(estrategias, 'x');
                        Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                        double endTime = System.nanoTime();
                        tiempoAlg2[iter] += (endTime - startTime);
                        calculadasAlg2 += Algoritmos.getContador();
                    }
                }
            }

            // Añadir los resultados formateados a la variable resultado
            resultado.append(String.format(
                    "%-10d%-25.4f%-25d%-25.4f%-25d\n", i,
                    (tiempoAlg1[iter] / 1e6), calculadasAlg1,
                    (tiempoAlg2[iter] / 1e6), calculadasAlg2
            ));
            iter++;
        }

        // Mostrar el resultado en una nueva ventana
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resultadoComparar.fxml"));
            Parent root = loader.load();

            ControllerResultadoComparar2 resultadoController = loader.getController();
            resultadoController.setResultado(resultado.toString());

            Stage resultadoStage = new Stage();
            resultadoStage.setTitle("Resultado Comparación");
            resultadoStage.setScene(new Scene(root));
            resultadoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void volver() {
        // Obtiene la ventana actual y la cierra
        Stage stage = (Stage) comb1.getScene().getWindow();
        stage.close();
    }

}
