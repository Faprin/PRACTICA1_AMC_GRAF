package com.practica1agrafica;

import consoleApp.ProcessFile;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.Scanner;

public class ControllerMenuCase1 {
    Scanner in = new Scanner(System.in);

    @FXML
    public void datasetCasoGeneral() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Capacidad del dataset");
        dialog.setContentText("Capacidad:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(capacidadStr -> {
            try {
                int capacidad = Integer.parseInt(capacidadStr);
                ProcessFile.createFile(capacidad);
            } catch (NumberFormatException e) {
                System.out.println("Capacidad incorrecta");
            }
        });
    }

    public void datasetPeorCaso() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Capacidad del dataset");
        dialog.setContentText("Capacidad:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(capacidadStr -> {
            try {
                int capacidad = Integer.parseInt(capacidadStr);
                ProcessFile.createFileWorstCase(capacidad);
            } catch (NumberFormatException e) {
                System.out.println("Capacidad incorrecta");
            }
        });
    }
}
