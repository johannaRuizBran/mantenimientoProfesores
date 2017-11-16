package com.example.joha.mantenimiento;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class mas_informacion_fragment extends Fragment {

    /*VARIABLES GLOBALES*/
    private View rootView;
    private Conexion conexion = new Conexion();
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

        cargarInformacionDeReporte();
        return rootView;
    }


    public void cargarInformacionDeReporte(){
        /*Parámetros:
        * Descripción: CArga la información que se encuentra dentro de la base de datos
         * sobre el reporte a mostrar en pantalla
        * */
        try{
            Call<Reporte> call = conexion.getServidor().obtenerInfoReporte(Global.idABuscar);
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