package com.practica1agrafica;

import consoleApp.Algoritmos;
import consoleApp.ProcessFile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import consoleApp.Punto;

public class ControllerMenuPrincipal {

    private Scanner in = new Scanner(System.in);
    private String path = "datasets/ch130.tsp";

    private ArrayList<Punto> copiaArray(ArrayList<Punto> original) {
        return new ArrayList<>(original);
    }

    public static ArrayList<Punto> generaDatasetPorTalla(int talla) {
        Random random = new Random(System.nanoTime());
        ArrayList<Punto> datos = new ArrayList();

        for (int i = 0; i < talla; i++) {
            datos.add(new Punto());
            datos.get(i).setId(i + 1);
            datos.get(i).setX(random.nextDouble(500 - 0 + 1));
            datos.get(i).setY(random.nextDouble(500 - 0 + 1));
        }

        return datos;
    }

    @FXML
    public void crearDatasetAleatorio() throws IOException {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuCase1.fxml"));
            Parent root = fxmlLoader.load();

            // Crear y configurar la nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Menu Caso Medio");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana: " + e.getMessage());
        }

    }

    @FXML
    public void cargarDatasetEnMemoria() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cargar fichero");
        dialog.setContentText("Indica la ruta del fichero:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(pathString -> {
            path = pathString.toLowerCase();
            System.out.println(path);
        });
    }

    @FXML
    public void compararEstrategias() {
        try {
            // Cargar los datos y ejecutar los algoritmos
            ArrayList<Punto> memoria = ProcessFile.reader(path);
            StringBuilder resultado = new StringBuilder();
            resultado.append("Estrategia                Punto 1                           Punto 2                           Distancia       Calculadas       Tiempo (mseg)\n");
            resultado.append("=============================================================================================================================================\n");

            // Ejecutar y mostrar resultados de cada algoritmo
            long startTime, endTime;
            double duration;

            // exhaustivo
            ArrayList<Punto> estrategias = copiaArray(memoria);
            Algoritmos.resetContador();
            startTime = System.nanoTime();
            ArrayList<Punto> exhaustivo = Algoritmos.exhaustivo(estrategias, 0, estrategias.size());
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1e6;
            ProcessFile.fileXArray(estrategias, "exhaustivo");

            resultado.append(String.format("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                    "Exhaustivo",
                    exhaustivo.get(0),
                    exhaustivo.get(1),
                    Algoritmos.distancia(exhaustivo.get(0), exhaustivo.get(1)),
                    Algoritmos.getContador(),
                    duration));

            // exhautivo-poda
            estrategias = copiaArray(memoria);
            Algoritmos.resetContador();
            startTime = System.nanoTime();
            ArrayList<Punto> exhaustivoPoda = Algoritmos.exhaustivoPoda(estrategias, 0, estrategias.size());
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1e6;
            ProcessFile.fileXArray(estrategias, "exhaustivoPoda");

            resultado.append(String.format("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                    "Exhaustivo-Poda",
                    exhaustivoPoda.get(0),
                    exhaustivoPoda.get(1),
                    Algoritmos.distancia(exhaustivoPoda.get(0), exhaustivoPoda.get(1)),
                    Algoritmos.getContador(),
                    duration));

            // dyv
            estrategias = copiaArray(memoria);
            Algoritmos.resetContador();
            startTime = System.nanoTime();
            Algoritmos.quickSort(estrategias, 'x');
            ArrayList<Punto> dyv = Algoritmos.dyv(estrategias, 0, estrategias.size());
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1e6;
            ProcessFile.fileXArray(estrategias, "dyv");

            resultado.append(String.format("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                    "DyV",
                    dyv.get(0),
                    dyv.get(1),
                    Algoritmos.distancia(dyv.get(0), dyv.get(1)),
                    Algoritmos.getContador(),
                    duration));

            //dyv-mejorado
            estrategias = copiaArray(memoria);
            Algoritmos.resetContador();
            startTime = System.nanoTime();
            Algoritmos.quickSort(estrategias, 'x');
            ArrayList<Punto> dyvMejorado = Algoritmos.dyvMejorado(estrategias, 0, estrategias.size());
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1e6;
            ProcessFile.fileXArray(estrategias, "dyvMejorado");

            resultado.append(String.format("%-20s %-35s %-35s %-15.10f %-15d %-15.4f%n",
                    "DyV-Mejorado",
                    dyvMejorado.get(0),
                    dyvMejorado.get(1),
                    Algoritmos.distancia(dyvMejorado.get(0), dyvMejorado.get(1)),
                    Algoritmos.getContador(),
                    duration));

            // Cargar el FXML de la nueva ventana de resultados
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("compararEstrategias.fxml"));
            Parent root = fxmlLoader.load();

            // Obtener el controlador de la ventana de resultados y pasar el texto
            ControllerCompararEstrategias controller = fxmlLoader.getController();
            controller.mostrarResultado(resultado.toString());

            // Crear y mostrar la ventana de resultados
            Stage stage = new Stage();
            stage.setTitle("Resultado de la Comparación de Estrategias");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de resultados: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la comparación de estrategias: " + e.getMessage());
        }
    }

    @FXML
    public void compararTodasEstrategias() {
        //POR RELLENAR

    }

    public void comparaDosEstrategias() {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("2Estrategias.fxml"));
            Parent root = fxmlLoader.load();

            // Crear y configurar la nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Comparar dos estrategias");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana: " + e.getMessage());
        }

    }



}
