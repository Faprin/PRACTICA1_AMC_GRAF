package com.practica1agrafica;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerCompobarEstrategias {

    @FXML
    private TextArea resultadoTextArea;

    public void mostrarResultado(String resultado) {
        resultadoTextArea.setText(resultado);
    }
}
