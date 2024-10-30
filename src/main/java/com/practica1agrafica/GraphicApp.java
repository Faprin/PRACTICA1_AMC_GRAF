package com.practica1agrafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicApp.class.getResource("menuPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("PRACTICA 1");
        stage.setScene(scene);
        ControllerMenuPrincipal controllerMenuPrincipal = fxmlLoader.getController();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}