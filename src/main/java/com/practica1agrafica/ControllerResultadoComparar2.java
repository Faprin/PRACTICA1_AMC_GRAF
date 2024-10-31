// ResultadoController.java
package com.practica1agrafica;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerResultadoComparar2 {
    @FXML
    private TextArea resultadoTextArea;

    public void setResultado(String resultado) {
        resultadoTextArea.setText(resultado);
    }
}
