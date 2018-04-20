package com.example.joha.mantenimiento;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joha.mantenimiento.Clases.informacionFaltante;
import com.example.joha.mantenimiento.Conexiones.ConexionIP;
import com.example.joha.mantenimiento.Globales.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class agregar_informacion_requerida_fragment extends Fragment {

    /*VARIABLES GLOBALES*/

    private View rootView;
    EditText descripcionTexto;
    TextView informacionSolicitada;
    private ConexionIP conexionIP = new ConexionIP();
    Button botonAgregarInfo;

    public agregar_informacion_requerida_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_agregar_informacion_requerida_fragment, container, false);
        descripcionTexto= (EditText)rootView.findViewById(R.id.agregarInformacionInput);
        informacionSolicitada= (TextView)rootView.findViewById(R.id.iformacionPedidaEnviadaServ);
        botonAgregarInfo= (Button)rootView.findViewById(R.id.agregarInformacionBoton);
        botonAgregarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarNuevaInformación();
            }
        });
        realizarProcesoInformacion();
        return rootView;
    }

    public void insertarNuevaInformación(){
        /*Parámetros:
        * Descripción: Inserta la información recibida por el usuario (la cual es la información
        * que solicita el administrador) dentro de la base de datos.
        * */
        try{
            String infroamcionNueva= descripcionTexto.getText().toString().trim();
            Call<Boolean> call = conexionIP.getServidor().agregaMasInformacion(Global.idABuscar,infroamcionNueva);
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Toast.makeText(getContext(), Global.mensajeExito , Toast.LENGTH_LONG).show();
                    lista_reportes_fragment nuevoFragment = new lista_reportes_fragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.ContentForFragments, nuevoFragment).commit();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }

    public void realizarProcesoInformacion(){
        AsyncTask<Void,Void, Boolean> processAsync= new AsyncTask<Void, Void, Boolean>() {
            ProgressDialog mDialog= new ProgressDialog(getContext());
            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Loading..");
                mDialog.show();
            }
            @Override
            protected Boolean doInBackground(Void... params) {
                mostrarInformacionenviadaPorAdmin();
                return true;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                mDialog.dismiss();

            };
        };
        processAsync.execute();
    }

    public void mostrarInformacionenviadaPorAdmin(){
        /*Parámetros:
        *Descripción: Muestra la información enviada por el administrados (la cual se encuentra dentro de la base de datos)
        * al usuario
        * */
        try{
            Call<informacionFaltante> call = conexionIP.getServidor().informacionFaltanteBase(Global.idABuscar);
            call.enqueue(new Callback<informacionFaltante>() {
                @Override
                public void onResponse(Call<informacionFaltante> call, Response<informacionFaltante> response) {
                    informacionFaltante informacionFaltante= response.body();
                    informacionSolicitada.setText(informacionFaltante.getObservacion());
                }

                @Override
                public void onFailure(Call<informacionFaltante> call, Throwable t) {
                    Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }
}