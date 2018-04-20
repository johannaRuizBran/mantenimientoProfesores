package com.example.joha.mantenimiento;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joha.mantenimiento.Clases.Reporte;
import com.example.joha.mantenimiento.Conexiones.ConexionIP;
import com.example.joha.mantenimiento.Globales.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class mas_informacion_fragment extends Fragment {

    /*VARIABLES GLOBALES*/
    private View rootView;
    private ConexionIP conexionIP = new ConexionIP();
    TextView id, laboratorio, fechaRealizado, fechaDeSolicitud,descripcion;


    public mas_informacion_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_mas_informacion_fragment, container, false);

        //variables
        id= (TextView) rootView.findViewById(R.id.informacionIDServ);
        laboratorio= (TextView) rootView.findViewById(R.id.informacionLabServ);
        fechaDeSolicitud= (TextView) rootView.findViewById(R.id.informacionFechaInicialServ);
        fechaRealizado= (TextView) rootView.findViewById(R.id.informacionFechaFinalServ);
        descripcion= (TextView) rootView.findViewById(R.id.informacionDescripcionServ);
        informacionDelReporte();

        return rootView;
    }

    public void informacionDelReporte(){
        AsyncTask<Void,Void, Boolean> processAsync= new AsyncTask<Void, Void, Boolean>() {
            ProgressDialog mDialog= new ProgressDialog(getContext());
            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Loading..");
                mDialog.show();
            }
            @Override
            protected Boolean doInBackground(Void... params) {
                cargarInformacionDeReporte();
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


    public void cargarInformacionDeReporte(){
        /*Parámetros:
        * Descripción: CArga la información que se encuentra dentro de la base de datos
         * sobre el reporte a mostrar en pantalla
        * */
        try{
            Call<Reporte> call = conexionIP.getServidor().obtenerInfoReporte(Global.idABuscar);
            call.enqueue(new Callback<Reporte>() {
                @Override
                public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                    Reporte reporte= response.body();
                    id.setText(String.valueOf(reporte.getId()));
                    laboratorio.setText(reporte.getEstablecimiento());
                    fechaRealizado.setText(reporte.getFechaReporte());
                    fechaDeSolicitud.setText(reporte.getFechaFinalizacion());
                    descripcion.setText(reporte.getDescripcion());
                }

                @Override
                public void onFailure(Call<Reporte> call, Throwable t) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }
}