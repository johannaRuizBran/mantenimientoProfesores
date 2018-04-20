package com.example.joha.mantenimiento.Conexiones;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joha on 26/5/2017.
 */

/**
 * Esta clase permite la incorporación de la conexión retrofit de toda la aplicación
 *
 * @see:
 * @author: Johanna Ruiz B.
 */

public class ConexionIP {

    /*VARIABLES GLOBALES*/

    private String baseurl;
    private final Retrofit retrofit;
    private Servidor servidor;

    public ConexionIP() {
        /*Parámetros:
        * Descripción: Permite la conexíon y posee la IP del servidor
        * */
        this.baseurl = "http://172.24.4.41:1150";  //BASE DE DATOS LOCAL MIA
        //this.baseurl = "http://172.24.20.41:1150"; //BASE DE DATOS EN PRODUCCION
        //this.baseurl = "http://172.24.44.4:8090"; //BASE DE DATOS LOCAL JULIANA

        this.retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        this.servidor = retrofit.create(Servidor.class);
    }

    public Servidor getServidor() {
        return this.servidor;
    }
}