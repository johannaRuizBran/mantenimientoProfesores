package com.example.joha.mantenimiento.Clases;

/**
 * Created by Joha on 4/6/2017.
 */

/**
 * Esta clase permite gestionar y formar la estructura de la información sobre un reporte que se encuentre en estado información
 *
 * @see:
 * @author: Johanna Ruiz B.
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
