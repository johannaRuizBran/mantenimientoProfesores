package com.example.joha.mantenimiento;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Joha on 26/5/2017.
 */
public class Autentificacion {
    /*Clase la cual contiene instancia del usuario que se encuentra logueado*/
    private static Conexion conexion = new Conexion();
    private static String nombreUsuarioConectado= "";


    public static void nombreUsuario(String nombre){
        Autentificacion.nombreUsuarioConectado = nombre;
    }


    public Autentificacion(){
    }

    public static String getNombreUsuarioConectado() {
        return nombreUsuarioConectado;
    }

    public static void setNombreUsuarioConectado() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Token token= new Token(refreshedToken,Autentificacion.nombreUsuarioConectado);
        Call<Boolean> call = conexion.getServidor().serverActualizarToken(token);
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
