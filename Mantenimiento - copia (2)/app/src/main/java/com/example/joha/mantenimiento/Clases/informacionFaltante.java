package com.example.joha.mantenimiento.Clases;

/**
 * Created by Joha on 4/6/2017.
 */
public class informacionFaltante {
    int idReporte;
    String observacion;

    public informacionFaltante(int idReporte, String observacion) {
        this.idReporte = idReporte;
        this.observacion = observacion;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
