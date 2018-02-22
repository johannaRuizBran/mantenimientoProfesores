package com.example.joha.mantenimiento;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joha.mantenimiento.Clases.Usuario;
import com.example.joha.mantenimiento.Conexiones.Conexion;
import com.example.joha.mantenimiento.Globales.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro_activity extends AppCompatActivity {

    EditText nombre, nombreUsuario,contrasena,apellido1,apellido2,correo,telefono;
    String nombreS, nombreUsuarioS,apellido1S,apellido2S,correoS,telefonoS,contrasenaS,rolS;
    Button boton;
    Spinner spinerRol;
    Conexion conexion;
    String activo= "N";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_activity);

        spinerRol= (Spinner)findViewById(R.id.spinnerRol);
        contrasena=(EditText)findViewById(R.id.registro_input_password);
        nombre= (EditText)findViewById(R.id.registro_input_nombre);
        nombreUsuario= (EditText)findViewById(R.id.registro_input_nombreUsuario);
        apellido1= (EditText)findViewById(R.id.registro_input_apellido1);
        apellido2= (EditText)findViewById(R.id.registro_input_apellido2);
        correo= (EditText)findViewById(R.id.registro_input_email);
        telefono= (EditText)findViewById(R.id.registro_input_telefono);
        boton=(Button)findViewById(R.id.boton_registrar);
        conexion= new Conexion();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearElemento();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        limpiar();
        insertarDAtosEnSpinnerRol();
    }

    public void insertarDAtosEnSpinnerRol(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.usuario_arreglo, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinerRol.setAdapter(adapter);
    }

    public void crearElemento(){
        nombreS= nombre.getText().toString();
        nombreUsuarioS= nombreUsuario.getText().toString();
        apellido1S= apellido1.getText().toString();
        apellido2S= apellido2.getText().toString();
        correoS= correo.getText().toString();
        telefonoS= telefono.getText().toString();
        contrasenaS= contrasena.getText().toString();
        rolS= spinerRol.getSelectedItem().toString();

        if(nombreUsuarioS.isEmpty() || nombreS.isEmpty() || apellido2S.isEmpty() || apellido1S.isEmpty()
                || correoS.isEmpty() || telefonoS.isEmpty()){
            Toast.makeText(getApplicationContext(), Global.errorEspacioVacio,Toast.LENGTH_LONG).show();
            return;
        }
        else{
            Toast.makeText(getApplicationContext(), nombreUsuarioS,Toast.LENGTH_LONG).show();
            Usuario usuario= new Usuario(nombreUsuarioS, contrasenaS, nombreS, apellido1S,
                     apellido2S, correoS, telefonoS, rolS, activo);
            insertarEnBase(usuario);
        }
    }

    public void limpiar(){
        nombre.setText("");
        nombreUsuario.setText("");
        apellido1.setText("");
        apellido2.setText("");
        correo.setText("");
        telefono.setText("");
        contrasena.setText("");
    }

    public void insertarEnBase(Usuario usuario){
        try{
            Call<Boolean> call = conexion.getServidor().insertarUsuarioServer(usuario,usuario.getActivo());
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body() != null){
                        if(response.body().equals(true)){
                            Toast.makeText(getApplicationContext(),Global.mensajeInsertado, Toast.LENGTH_LONG).show();
                            Intent login = new Intent(getApplicationContext(), login_activity.class);
                            startActivity(login);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),Global.existeError, Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),Global.existeError, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }
}