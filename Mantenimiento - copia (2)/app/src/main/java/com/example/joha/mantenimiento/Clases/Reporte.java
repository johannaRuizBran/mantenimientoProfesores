package com.example.joha.mantenimiento.Clases;

/**
 * Created by Joha on 26/5/2017.
 */
public class Reporte {
    int id;
    String estadoReporte;
    String prioridadReporte;
    String fechaReporte;
    String fechaFinalizacion;
    String descripcion;
    String establecimiento;
    String nombreUsuario;
    String nombre;

    public Reporte(int id, String estadoReporte, String prioridadReporte, String fechaReporte,
                   String fechaFinalizacion, String descripcion, String establecimiento, String nombreUsuario,
                   String nombre) {
        this.id = id;
        this.estadoReporte = estadoReporte;
        this.prioridadReporte = prioridadReporte;
        this.fechaReporte = fechaReporte;
        this.fechaFinalizacion = fechaFinalizacion;
        this.descripcion = descripcion;
        this.establecimiento = establecimiento;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstadoReporte() {
        return estadoReporte;
    }

    public void setEstadoReporte(String estadoReporte) {
        this.estadoReporte = estadoReporte;
    }

    public String getPrioridadReporte() {
        return prioridadReporte;
    }

    public void setPrioridadReporte(String prioridadReporte) {
        this.prioridadReporte = prioridadReporte;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(String fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }
}