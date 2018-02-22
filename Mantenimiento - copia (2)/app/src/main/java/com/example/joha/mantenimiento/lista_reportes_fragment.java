package com.example.joha.mantenimiento;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joha.mantenimiento.Clases.Reporte;
import com.example.joha.mantenimiento.Conexiones.Conexion;
import com.example.joha.mantenimiento.Conexiones.ConexionPush;
import com.example.joha.mantenimiento.Globales.Autentificacion;
import com.example.joha.mantenimiento.Globales.Global;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class lista_reportes_fragment extends Fragment {
    /*VARIABLES GLOBALES*/

    ListView listViewReportesCreadosUsuario;
    Button BotonInformacion;
    private static List<Reporte> listaReporteBase;
    ArrayList<Reporte> listaReportes= new ArrayList<>();
    ArrayList<Integer>listaElegidos;
    String nombreUsuarioConectado;
    private Conexion conexion = new Conexion();
    FloatingActionButton fab;
    int idReporteSeleccionado;
    private View rootView;
    TextView labelNoPoseeReportes;
    ImageView imagenNoElementos;
    int tieneSoloElementosCancelados;


    public lista_reportes_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume(){
        listaReportes.clear();
        nombreUsuarioConectado= Autentificacion.getNombreUsuarioConectado();
        obtenerListaReportesDeBase();
        if(Global.idSender.equals("MasInformacion")){
            Global.idSender= "nada";
            agregar_informacion_requerida_fragment nuevoFragmento = new agregar_informacion_requerida_fragment();
            FragmentManager manager2 = getActivity().getSupportFragmentManager();
            manager2.beginTransaction().replace(R.id.ContentForFragments,nuevoFragmento).addToBackStack("tag").commit();
        }
        else if(Global.idSender.equals("info")){
            Global.idSender= "nada";
            mas_informacion_fragment nuevoFragment = new mas_informacion_fragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.ContentForFragments,nuevoFragment).addToBackStack("tag").commit();
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_reportes_fragment, container, false);
        listViewReportesCreadosUsuario= (ListView)rootView.findViewById(R.id.paginaPrincipalListView);

        imagenNoElementos= (ImageView)rootView.findViewById(R.id.imageNotFiles);
        labelNoPoseeReportes= (TextView)rootView.findViewById(R.id.labelNoElementos);

        listaReportes=new ArrayList<>();
        listaElegidos= new ArrayList<>();
        listViewReportesCreadosUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.posicionItemListViewPresionado= position;
                showPopupMenu(view);
            }
        });
        fab = (FloatingActionButton)rootView.findViewById(R.id.agregarBoton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear_reporte_fragment nuevoFragment = new crear_reporte_fragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.ContentForFragments,nuevoFragment).addToBackStack("tag").commit();
            }
        });

        return rootView;
    }

    public void mensajeConfirmacion(){
        /*Parámetros:
        * Descripción: Muestra al usaurio un mensaje de confirmación para hacer correcta la cancelación de
         * un reporte.
        * */
        new AlertDialog.Builder(getContext())
            .setMessage("¿Confirma la acción seleccionada?")
            .setTitle("Confirmacion")
            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener()  {
                public void onClick(DialogInterface dialog, int id) {
                    eliminar(Global.posicionItemListViewPresionado);
                    Log.i("Dialogos", "Elemento Eliminado.");
                    dialog.cancel();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.i("Dialogos", "Confirmacion Cancelada.");
                    dialog.cancel();
                }
            }).create().show();

    }

    public void showPopupMenu(final View view){
        /*Parámetros:
        * Descripción: Muestra el popuMenu al usuario y realiza las acciones correspondientes según
        * la selección del usuario (ver más información de un reporte o cancelar el reporte)
        * */
        PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), view);
        popup.getMenuInflater().inflate(R.menu.popu_menu_reportes, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case  R.id.verMasInfoPopu:
                    Global.idABuscar= listaReportes.get(Global.posicionItemListViewPresionado).getId();
                    mas_informacion_fragment nuevoFragment = new mas_informacion_fragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.ContentForFragments,nuevoFragment).addToBackStack("tag").commit();
                    return true;
                case  R.id.eliminarPopu:
                    mensajeConfirmacion();
                return true;
            }
            return true;
            }
        });
        popup.show();
    }

    public void crearObjetoReporte(int posicionItem){
        /*Parámetros: posicionItem= posicón de la lista a evaluar.
        * Descripción: crea a un objeto nuevo de tipo Reporte (con la información contenida en la lista listaReporteBase)
        *e inseta dicho objeto en la lista de reportes listaReportes
        * */
        Reporte nuevo4= new Reporte(listaReporteBase.get(posicionItem).getId(),listaReporteBase.get(posicionItem).getEstadoReporte(),
                listaReporteBase.get(posicionItem).getPrioridadReporte(),listaReporteBase.get(posicionItem).getFechaReporte(),
                listaReporteBase.get(posicionItem).getFechaFinalizacion(),listaReporteBase.get(posicionItem).getDescripcion(),
                listaReporteBase.get(posicionItem).getEstablecimiento(),listaReporteBase.get(posicionItem).getNombreUsuario(),
                listaReporteBase.get(posicionItem).getNombre());
        listaReportes.add(nuevo4);
    }

    public void obtenerListaReportesDeBase(){
        /*Parámetros:
        * Descripción: Obtiene todos los Reportes que se encuentran almacenados dentro de la base de datos.
        * */
        try{
            Call<List<Reporte>> call = conexion.getServidor().obtenerReportesDeUnUsuario(nombreUsuarioConectado);
            call.enqueue(new Callback<List<Reporte>>() {
                @Override
                public void onResponse(Call<List<Reporte>> call, Response<List<Reporte>> response) {
                    listaReporteBase = response.body();
                    if(listaReporteBase.size() == 0){
                        imagenNoElementos.setVisibility(View.VISIBLE);
                        labelNoPoseeReportes.setVisibility(View.VISIBLE);
                        return;
                    }
                    tieneSoloElementosCancelados=0;
                    for (int i = 0; i < listaReporteBase.size(); i++)
                    {
                        if(!listaReporteBase.get(i).getEstadoReporte().equals("Cancelado")){
                            crearObjetoReporte(i);
                        }
                        else{
                            tieneSoloElementosCancelados+=1;
                        }
                    }
                    if (tieneSoloElementosCancelados== listaReporteBase.size()){
                        listViewReportesCreadosUsuario.setVisibility(View.GONE);
                        imagenNoElementos.setVisibility(View.VISIBLE);
                        labelNoPoseeReportes.setVisibility(View.VISIBLE);
                        return;
                    }

                    listViewReportesCreadosUsuario.setAdapter(new Adapter(getContext()));
                }
                @Override
                public void onFailure(Call<List<Reporte>> call, Throwable t) {
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
        }
    }

    public void obtenrTokenUsuario(final int idReporteSeleccionado){
        try{
            Call<List<String>> mensaje= conexion.getServidor().obtenerListaDeTokenAdmin();
            mensaje.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    try{
                        List<String>lista= response.body();
                        for (int i = 0; i < lista.size(); i++)
                        {
                            enviarMensaje(lista.get(i),idReporteSeleccionado);
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),Global.errorConexion, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion, Toast.LENGTH_LONG).show();
        }
    }

    public void enviarMensaje(String token, final int idReporteSeleccionado){
        try{
            ConexionPush clase= new ConexionPush(token,"Se ha CANCELADO el reporte: "+ String.valueOf(idReporteSeleccionado),idReporteSeleccionado);
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
            Toast.makeText(getContext(),Global.errorConexion, Toast.LENGTH_LONG).show();
        }

    }

    public void eliminar(int posicion){
        /*Parámetros: posicion= posición de item a cancelar
        * Descripción:  cancela a un elementeo (que coincide con la posición recibida) de la base de datos.
        * */
        try{
            idReporteSeleccionado= listaReportes.get(posicion).getId();
            Call<Boolean> call = conexion.getServidor().cencelarReporte(idReporteSeleccionado, "Cancelado");
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    try {
                        onResume();
                        Toast.makeText(getContext(),Global.mensajeExito,Toast.LENGTH_LONG).show();
                        obtenrTokenUsuario(idReporteSeleccionado);
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getContext(),Global.errorConexion,Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(),Global.errorConexion, Toast.LENGTH_LONG).show();
        }

        onResume();
    }

    public class Adapter extends BaseAdapter {

        LayoutInflater myInflater;

        public Adapter(Context context){
            myInflater= LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return listaReportes.size();
        }

        @Override
        public Object getItem(int position) {
            return listaReportes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView== null){
                convertView= myInflater.inflate(R.layout.componente,null);
            }
            TextView identificacion= (TextView)convertView.findViewById(R.id.paginaPrincipalInputID);
            identificacion.setText(String.valueOf(listaReportes.get(position).getId()));
            TextView laboratorio= (TextView)convertView.findViewById(R.id.paginaPrincipalInputLab);
            laboratorio.setText(listaReportes.get(position).getEstablecimiento());
            BotonInformacion= (Button)convertView.findViewById(R.id.paginaPrincipalBotonInformacion);
            BotonInformacion.setVisibility(View.INVISIBLE);
            BotonInformacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Global.idABuscar= listaReportes.get(position).getId();
                    agregar_informacion_requerida_fragment nuevoFragment = new agregar_informacion_requerida_fragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.ContentForFragments,nuevoFragment).addToBackStack("tag").commit();
                }
            });
            if (listaReportes.get(position).getEstadoReporte().equals("informacion")){
                BotonInformacion.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }
}