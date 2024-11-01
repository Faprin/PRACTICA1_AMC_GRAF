package com.practica1agrafica;

public class Resultado {
    private int talla;
    private double tiempoExhaustivo;
    private double tiempoExhaustivoPoda;
    private double tiempoDyV;
    private double tiempoDyVMejorado;

    public Resultado(int talla, double tiempoExhaustivo, double tiempoExhaustivoPoda, double tiempoDyV, double tiempoDyVMejorado) {
        this.talla = talla;
        this.tiempoExhaustivo = tiempoExhaustivo;
        this.tiempoExhaustivoPoda = tiempoExhaustivoPoda;
        this.tiempoDyV = tiempoDyV;
        this.tiempoDyVMejorado = tiempoDyVMejorado;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public double getTiempoExhaustivo() {
        return tiempoExhaustivo;
    }

    public void setTiempoExhaustivo(double tiempoExhaustivo) {
        this.tiempoExhaustivo = tiempoExhaustivo;
    }

    public double getTiempoExhaustivoPoda() {
        return tiempoExhaustivoPoda;
    }

    public void setTiempoExhaustivoPoda(double tiempoExhaustivoPoda) {
        this.tiempoExhaustivoPoda = tiempoExhaustivoPoda;
    }

    public double getTiempoDyV() {
        return tiempoDyV;
    }

    public void setTiempoDyV(double tiempoDyV) {
        this.tiempoDyV = tiempoDyV;
    }

    public double getTiempoDyVMejorado() {
        return tiempoDyVMejorado;
    }

    public void setTiempoDyVMejorado(double tiempoDyVMejorado) {
        this.tiempoDyVMejorado = tiempoDyVMejorado;
    }
}
