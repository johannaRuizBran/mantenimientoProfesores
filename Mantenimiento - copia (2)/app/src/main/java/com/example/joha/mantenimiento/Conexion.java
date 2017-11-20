package com.example.joha.mantenimiento;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joha on 26/5/2017.
 */
public class Conexion {

    /*VARIABLES GLOBALES*/

    private String baseurl;
    private final Retrofit retrofit;
    private Servidor servidor;

    public Conexion() {
        /*Parámetros:
        * Descripción: Permite la conexíon y posee la IP del servidor
        * */
        this.baseurl = "http://172.24.43.59:8090";
        this.retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        this.servidor = retrofit.create(Servidor.class);
    }

    public Servidor getServidor() {
        return this.servidor;
    }
}