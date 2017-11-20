package com.example.joha.mantenimiento;

import android.content.SharedPreferences;

/**
 * Created by Joha on 26/5/2017.
 */
public class Global {

    static SharedPreferences sharedPreferences;
    static String sharedPreferences_username;
    static String sharedPreferences_password;

    static int posicionItemListViewPresionado;
    static String existeError= "Error: es posible que el nombre de usuario ya exista";
    static String idSender= "nada";
    static String mensajeCancelar= "se ha cancelado el reporte numero: ";

    static final String estadoNuevo= "nuevo";
    static final String errorConexion= "Error en coneccion";
    static final String errorUsuarioInvalido= "Error usuario inválido";
    static final String mensajeExito= "Se ha realizado exitosamente";
    static final String mensajeInsertado= "Se ha insertado exitosamente";
    static final String errorEspacioVacio= "No se permiten espacios vacíos";
    static int idABuscar;
    public static String mensajeNuevoReporte= "Se ha creado un nuevo reporte ";
}
