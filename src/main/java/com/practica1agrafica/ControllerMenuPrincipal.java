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

    private static boolean peorCaso = false;

    public static ArrayList<Punto> generaDatasetPorTalla(int talla) {
        // tenemos que verificar si la variable peorCaso esta a true
        Random random = new Random(System.nanoTime());
        ArrayList<Punto> datos = new ArrayList();
        int num = 0, den = 0;
        double x, y;
        if (!getPeorCaso()) {
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
    public void switchPeorCaso() {
        if (peorCaso) {
            peorCaso = false;
        } else {
            peorCaso = true;
        }
        System.out.println("Peor caso a " + peorCaso);
    }

    public static boolean getPeorCaso() {
        return peorCaso;
    }

    private ArrayList<Punto> copiaArray(ArrayList<Punto> original) {
        return new ArrayList<>(original);
    }


    // CASE 1
    @FXML
    public void crearDatasetAleatorio() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear dataset");
        dialog.setHeaderText("Capacidad del dataset: ");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(capacidadStr -> {
            try {
                int capacidad = Integer.parseInt(capacidadStr);
                if (!getPeorCaso()) {
                    ProcessFile.createFile(capacidad);
                } else {
                    ProcessFile.createFileWorstCase(capacidad);
                }
            } catch (NumberFormatException e) {
                System.out.println("No se puede crear dataset");
            }
        });
    }

    // CASE 2
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

    // CASE 3
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
            ControllerCompobarEstrategias controller = fxmlLoader.getController();
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

    // CASE 4
    @FXML
    public void compararTodasEstrategias() throws IOException {
        // abro la ventana y subdelego el codigo al controlador
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("case4.fxml"));
            Parent root = fxmlLoader.load();

            // Crear y configurar la nueva ventana (Stage)
            Stage stage = new Stage();
            stage.setTitle("Comparar todas las estrategias");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana: " + e.getMessage());
        }
    }


    // CASE 5
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
