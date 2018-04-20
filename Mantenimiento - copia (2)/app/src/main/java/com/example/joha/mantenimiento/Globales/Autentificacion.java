package com.example.joha.mantenimiento.Globales;

import com.example.joha.mantenimiento.Clases.Token;
import com.example.joha.mantenimiento.Conexiones.ConexionIP;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Joha on 26/5/2017.
 */

/**
 * Esta clase mantiene
 *
 * @see: Conexiones/Servidor
 * @author: Johanna Ruiz B.
 */

public class Autentificacion {
    /*Clase la cual contiene instancia del usuario que se encuentra logueado*/
    private static ConexionIP conexionIP = new ConexionIP();
    private static String nombreUsuarioConectado= "";


    public static void nombreUsuario(String nombre){
        Autentificacion.nombreUsuarioConectado = nombre;
    }


    public Autentificacion(){
    }

    public static String getNombreUsuarioConectado() {
        return nombreUsuarioConectado;
    }

    /**
     * Esta función permite cambiar el token del usuario que se haya conectado.
     *
     * @see: Conexiones/Servidor
     * @author: Johanna Ruiz B.
     */

    public static void setNombreUsuarioConectado() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Token token= new Token(refreshedToken,Autentificacion.nombreUsuarioConectado);
        Call<Boolean> call = conexionIP.getServidor().serverActualizarToken(token);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }
}
