package com.practica1agrafica;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerCompararEstrategias {

    @FXML
    private TextArea resultadoTextArea;

    // Método para establecer el texto en la TextArea
    public void mostrarResultado(String resultado) {
        resultadoTextArea.setText(resultado);
    }
}
