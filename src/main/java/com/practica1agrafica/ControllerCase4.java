package com.practica1agrafica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Random;

import consoleApp.ProcessFile;
import consoleApp.Algoritmos;
import consoleApp.Punto;

public class ControllerCase4 {

    private ArrayList<Punto> copiaArray(ArrayList<Punto> original) {
        return new ArrayList<>(original);
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
    private TableView<Resultado> tablaResultados;
    @FXML
    private TableColumn<Resultado, Integer> colTalla;
    @FXML
    private TableColumn<Resultado, Double> colExhaustivo;
    @FXML
    private TableColumn<Resultado, Double> colExhaustivoPoda;
    @FXML
    private TableColumn<Resultado, Double> colDyV;
    @FXML
    private TableColumn<Resultado, Double> colDyVMejorado;

    @FXML
    public void initialize() {
        // Configurar las columnas
        colTalla.setCellValueFactory(new PropertyValueFactory<>("talla"));
        colExhaustivo.setCellValueFactory(new PropertyValueFactory<>("tiempoExhaustivo"));
        colExhaustivoPoda.setCellValueFactory(new PropertyValueFactory<>("tiempoExhaustivoPoda"));
        colDyV.setCellValueFactory(new PropertyValueFactory<>("tiempoDyV"));
        colDyVMejorado.setCellValueFactory(new PropertyValueFactory<>("tiempoDyVMejorado"));

        // Llamar al método que calcula y llena la tabla
        cargarResultados();
    }

    public void cargarResultados() {
        double[] tiempoExhaustivo = new double[5];
        double[] tiempoExhaustivoPoda = new double[5];
        double[] tiempoDyV = new double[5];
        double[] tiempoDyVMejorado = new double[5];

        int iter = 0;
        ObservableList<Resultado> resultados = FXCollections.observableArrayList();

        for (int i = 1000; i <= 5000; i += 1000) {
            for (int j = 0; j < 10; j++) {
                ArrayList<Punto> dataset = generaDatasetPorTalla(i);
                ArrayList<Punto> estrategias = copiaArray(dataset);
                double startTime, endTime;

                // Algoritmo Exhaustivo
                Algoritmos.resetContador();
                startTime = System.nanoTime();
                Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
                endTime = System.nanoTime();
                tiempoExhaustivo[iter] += (endTime - startTime);

                // Algoritmo Exhaustivo con Poda
                Algoritmos.resetContador();
                estrategias = copiaArray(dataset);
                startTime = System.nanoTime();
                Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
                endTime = System.nanoTime();
                tiempoExhaustivoPoda[iter] += (endTime - startTime);

                // Divide y Vencerás
                Algoritmos.resetContador();
                estrategias = copiaArray(dataset);
                startTime = System.nanoTime();
                Algoritmos.quickSort(estrategias, 'x');
                Algoritmos.dyv(estrategias, 0, estrategias.size());
                endTime = System.nanoTime();
                tiempoDyV[iter] += (endTime - startTime);

                // Divide y Vencerás Mejorado
                Algoritmos.resetContador();
                estrategias = copiaArray(dataset);
                startTime = System.nanoTime();
                Algoritmos.quickSort(estrategias, 'x');
                Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
                endTime = System.nanoTime();
                tiempoDyVMejorado[iter] += (endTime - startTime);
            }

            // Agregar los resultados de esta iteración
            resultados.add(new Resultado(
                    i,
                    tiempoExhaustivo[iter] / 1e6,
                    tiempoExhaustivoPoda[iter] / 1e6,
                    tiempoDyV[iter] / 1e6,
                    tiempoDyVMejorado[iter] / 1e6
            ));
            iter++;
        }

        // Establecer los datos en la tabla
        tablaResultados.setItems(resultados);

        // Guardar en archivos si es necesario
        ProcessFile.createSizeFile(tiempoExhaustivo, "exhaustivo", 1000, 1000);
        ProcessFile.createSizeFile(tiempoExhaustivoPoda, "exhaustivo-poda", 1000, 1000);
        ProcessFile.createSizeFile(tiempoDyV, "dyv", 1000, 1000);
        ProcessFile.createSizeFile(tiempoDyVMejorado, "dyvMejorado", 1000, 1000);
    }

    // Asegúrate de incluir métodos como generaDatasetPorTalla y copiaArray en la misma clase o accesibles
}
