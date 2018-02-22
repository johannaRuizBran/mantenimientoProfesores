package com.example.joha.mantenimiento.Globales;

import android.content.SharedPreferences;

/**
 * Created by Joha on 26/5/2017.
 */
public class Global {

    public static SharedPreferences sharedPreferences;
    public static String sharedPreferences_username;
    public static String sharedPreferences_password;

    public static int posicionItemListViewPresionado;
    public static String existeError= "Error: es posible que el nombre de usuario ya exista";
    public static String idSender= "nada";

    public static final String estadoNuevo= "nuevo";
    public static final String errorConexion= "Error en coneccion";
    public static final String errorUsuarioInvalido= "Error usuario inválido";
    public static final String mensajeExito= "Se ha realizado exitosamente";
    public static final String mensajeInsertado= "Se ha insertado exitosamente";
    public static final String errorEspacioVacio= "No se permiten espacios vacíos";
    public static int idABuscar;
}
