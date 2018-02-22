package com.example.joha.mantenimiento;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.joha.mantenimiento.Globales.Autentificacion;
import com.example.joha.mantenimiento.Globales.Global;

/**
 * Created by Joha on 9/6/2017.
 */

public class QuitDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Desea salir de la aplicación")
                .setTitle("Cerrar Sesión")
                .setPositiveButton("Sí, salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //de esta manera se manda al home
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Autentificacion.nombreUsuario("");
                        SharedPreferences.Editor editor = Global.sharedPreferences.edit();
                        editor.putString("username","");
                        editor.putString("password","");
                        editor.apply();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}