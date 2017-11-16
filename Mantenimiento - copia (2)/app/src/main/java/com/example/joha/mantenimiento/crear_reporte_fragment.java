package com.example.joha.mantenimiento;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class crear_reporte_fragment extends Fragment {

    /*VARIABLES GLOBALES*/
    private View rootView;
    EditText descripcionInput;
    TextView  fechaInput;
    String [] listaDeLAboratorios={"LAB-01","LAB-02","Miniauditorio","Moviles","SIRZEE"};
    Spinner spinnerLaboratorios;
    Button botonAceptar, botonCalendario;
    Conexion conexion;
    private int dia, mes, año;
    private DatePickerDialog datePickerDialog;


    public crear_reporte_fragment() {
        // Required empty public constructor
    }

    public void insertarReporteObjeto(){
        /*Parámetros:
        * Descripción: Crea instancia de un nuevo objeto Reporte el cual posee la información
        * a insertar por parte del usuario.
        * */
        String establecimiento= spinnerLaboratorios.getSelectedItem().toString();
        String fecha=fechaInput.getText().toString();
        String desc=descripcionInput.getText().toString();

        if(fecha.isEmpty() || establecimiento.isEmpty()){
            Toast.makeText(getContext(),Global.errorEspacioVacio, Toast.LENGTH_LONG).show();
        }
        else {
            Reporte nuevo= new Reporte(0,Global.estadoNuevo,null,null,fecha,
                    desc,establecimiento, Autentificacion.getNombreUsuarioConectado(),null);
            insertarNuevoreporteEnBase(nuevo);
        }
    }


    public void obtenrTokenUsuario(final String usuarioReporte){
        try{
            Call<List<String>> mensaje= conexion.getServidor().obtenerListaDeTokenAdmin();
            mensaje.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    List<String>lista= response.body();
                    for (int i = 0; i < lista.size(); i++)
                    {
                        enviarMensaje(lista.get(i),usuarioReporte);
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                }
            });
        }
        catch (Exception e){

        }
    }

    public void enviarMensaje(String token, final String usuarioReporte){
        try{
            ConexionPush clase= new ConexionPush(token,usuarioReporte+ " ha creado un NUEVO reporte",3);
            Call<String> mensaje= conexion.getServidor().serverEnviarMensajePush(clase);
            mensaje.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }

    public void insertarNuevoreporteEnBase(final Reporte nuevo){
        /*Parámetros: nuevo= objeto reporte a insertar dentro de la base de datos
        * Descripción: Envía al nuevo Reporte a una función del servidor la cual insertará dicho
        * objeto dentro de la base de datos.
        * */
        try{
            Call<Boolean> call = conexion.getServidor().crearReporte(nuevo);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Toast.makeText(getContext(),Global.mensajeInsertado, Toast.LENGTH_LONG).show();
                    obtenrTokenUsuario(Autentificacion.getNombreUsuarioConectado());

                    descripcionInput.setText("");
                    fechaInput.setText("");
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(),Global.errorConexion, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_crear_reporte_fragment, container, false);
        descripcionInput= (EditText)rootView.findViewById(R.id.crearInputDescripcion);
        botonAceptar= (Button) rootView.findViewById(R.id.crearBotonInsertar);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarReporteObjeto();
            }
        });
        conexion= new Conexion();
        introducirLaboratoriosAEspinner();
        fechaInput= (TextView)rootView.findViewById(R.id.fechaSeleccionada);
        botonCalendario=(Button)rootView.findViewById(R.id.botonCalendariio);
        botonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarShow();
            }
        });

        return rootView;
    }

    private void calendarShow() {
        /*Parámetros:
        * Descripción: función que permite la visualización del calendario y muestra fecha seleccionada en
        * textView en caso de seleccionarse fecha.
        * */
        Calendar c = Calendar.getInstance();
        año = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaInput.setText(+dayOfMonth+"-"+(month+1)+"-"+year);
            }
        },año,mes,dia);
        datePickerDialog.show();
    }

    public void introducirLaboratoriosAEspinner(){
        /*Parámetros:
        * Descripción: Introduce los laboratorios existentes den
        * */
        spinnerLaboratorios = (Spinner) rootView.findViewById(R.id.crearSpinnerLab);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaDeLAboratorios);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerLaboratorios.setAdapter(adapter);
    }
}