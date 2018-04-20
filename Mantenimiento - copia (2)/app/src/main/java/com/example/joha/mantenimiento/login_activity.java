package com.example.joha.mantenimiento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joha.mantenimiento.Clases.Reporte;
import com.example.joha.mantenimiento.Clases.Usuario;
import com.example.joha.mantenimiento.Conexiones.ConexionIP;
import com.example.joha.mantenimiento.Firebase.MyService;
import com.example.joha.mantenimiento.Globales.Autentificacion;
import com.example.joha.mantenimiento.Globales.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_activity extends AppCompatActivity {

    /*VARIABLES GLOBALES*/
    EditText valContrasenna,valNombreUsuario;
    TextView registroLink;
    Button botonLogin;
    String nombreUsuario;
    CheckBox checkBox;
    private ConexionIP conexionIP = new ConexionIP();


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try{
            if(intent.getExtras()!= null){
                setIntent(intent);
                for (String key : getIntent().getExtras().keySet()) {
                    try{
                        Object value = getIntent().getExtras().get(key);
                        if(key.equals("mensaje")){
                            Global.idABuscar=Integer.valueOf((String) value);
                            enviarAOtraPaginaSegunPush();
                        }
                    }catch (Exception e){

                    }
                }
            }
        }
        catch (Exception e){

        }
    }

    public void enviarAOtraPaginaSegunPush(){
        try{
            Call<Reporte> call = conexionIP.getServidor().obtenerInfoReporte(Global.idABuscar);
            call.enqueue(new Callback<Reporte>() {
                @Override
                public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                    Reporte reporte= response.body();
                    if(!Global.sharedPreferences_username.isEmpty() && !Global.sharedPreferences_password.isEmpty()){
                        if(reporte.getEstadoReporte().equals("informacion")){
                            Global.idSender= "MasInformacion";
                            Intent paginaPrincipal= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(paginaPrincipal);
                        }
                        else{
                            Global.idSender= "info";
                            Intent paginaPrincipal= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(paginaPrincipal);
                        }
                    }else{
                        if(reporte.getEstadoReporte().equals("informacion")){
                            Global.idSender= "MasInformacion";
                        }
                        else{
                            Global.idSender= "info";
                        }
                    }
                }

                @Override
                public void onFailure(Call<Reporte> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        valContrasenna= (EditText)findViewById(R.id.loginInputContrasenna);
        valNombreUsuario= (EditText)findViewById(R.id.loginInputNombreUsuario);
        checkBox= (CheckBox)findViewById(R.id.checkBoxUsuario);
        registroLink= (TextView)findViewById(R.id.linkRegistro);
        botonLogin= (Button)findViewById(R.id.loginBotonIniciar);
        Global.sharedPreferences =  getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        Global.sharedPreferences_username = Global.sharedPreferences.getString("username","");
        Global.sharedPreferences_password = Global.sharedPreferences.getString("password","");

        valNombreUsuario.setText(Global.sharedPreferences_username);
        valContrasenna.setText(Global.sharedPreferences_password);

        if(!valNombreUsuario.getText().toString().equals("")){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }

        registroLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registroVista= new Intent(getApplicationContext(),Registro_activity.class);
                startActivity(registroVista);
            }
        });
        accionarBotonIniciar();
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null)
        {
            for (String key : getIntent().getExtras().keySet()) {
                try{
                    Object value = getIntent().getExtras().get(key);
                    if(key.equals("mensaje")){
                        Global.idABuscar=Integer.valueOf((String) value);
                        enviarAOtraPaginaSegunPush();
                    }
                }catch (Exception e){

                }
            }
            String desdeBarra =  getIntent().getExtras().getString("message","");
            if(!desdeBarra.equals("Back")){
                iniciarService();
            }
            boolean mostrarToast = getIntent().getExtras().getBoolean("Barra", false);
            if (mostrarToast)
            {
                iniciarService();
            }
        }else

            iniciarService();
            setContentView(R.layout.activity_login_activity);
    }

    public void iniciarService(){
        startService(new Intent(getApplicationContext(), MyService.class));
    }


    private void accionarBotonIniciar(){
        /*Parámetros:
        * Descripción: Loguea al usuario dentro del sistema
        * */
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            final String contraseña= valContrasenna.getText().toString().trim();
            nombreUsuario= valNombreUsuario.getText().toString().trim();

            if (contraseña.equals("") || nombreUsuario.equals("")){
                Toast.makeText(getApplication(),Global.errorEspacioVacio,Toast.LENGTH_LONG).show();
            }
            else{
                try{
                    Call<Usuario> call = conexionIP.getServidor().get(nombreUsuario,contraseña);
                    call.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Autentificacion.nombreUsuario(nombreUsuario);
                            if (checkBox.isChecked()){
                                SharedPreferences.Editor editor = Global.sharedPreferences.edit();
                                editor.putString("username",nombreUsuario);
                                editor.putString("password",contraseña);
                                editor.putString("button","active");
                                editor.apply();
                            }
                            Autentificacion.setNombreUsuarioConectado();
                            Intent paginaPrincipal= new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(paginaPrincipal);
                        }
                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),Global.errorUsuarioInvalido,Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(getApplication(),Global.errorConexion,Toast.LENGTH_LONG).show();
                }
            }
            }
        });
    }
}