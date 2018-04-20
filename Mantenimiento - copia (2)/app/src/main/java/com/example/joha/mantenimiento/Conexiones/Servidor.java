package com.example.joha.mantenimiento.Conexiones;

import com.example.joha.mantenimiento.Clases.Reporte;
import com.example.joha.mantenimiento.Clases.Token;
import com.example.joha.mantenimiento.Clases.Usuario;
import com.example.joha.mantenimiento.Clases.informacionFaltante;
import com.example.joha.mantenimiento.Conexiones.ConexionPush;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Joha on 26/5/2017.
 */

/**
 * Mediante este interface, se posee los distintos endpoints o rutas hacia el servidor que permiten las distintas
 * consultas a la base de datos del sistema mantenimiento.
 *
 * @see:
 * @author: Johanna Ruiz B.
 */
public interface Servidor {
    /*Parámetros:
    * Descripción:
    * */

    @GET("Usuarios/ObtenerInfo/{nombreU}/{contrasenna}")
    Call<Usuario> get(@Path("nombreU") String nombreU, @Path("contrasenna") String contrasenna);

    @GET("Reportes/ObtenerMisReportes/{nombreU}")
    Call<List<Reporte>> obtenerReportesDeUnUsuario(@Path("nombreU") String nombreU);

    @POST("Reportes/informacionFaltante/agregar/{idReporte}/{infomacion}")
    Call<Boolean> agregaMasInformacion(@Path("idReporte") int idReporte, @Path("infomacion") String estado);

    @POST("Reportes/cambiarEstado/{idReporte}/{estado}")
    Call<Boolean> cencelarReporte(@Path("idReporte") int idReporte, @Path("estado") String estado);

    @GET("Reportes/informacionReporte/{idReporte}")
    Call<Reporte> obtenerInfoReporte(@Path("idReporte") int idReporte);

    @GET("Reportes/informacionFaltante/{idReporte}")
    Call<informacionFaltante> informacionFaltanteBase(@Path("idReporte") int idReporte);

    @POST("Reportes/crearReporte")
    Call<Boolean> crearReporte(@Body Reporte reporte);

    @POST("/Usuarios/actualizarTokenPush")
    Call<Boolean> serverActualizarToken(@Body Token token);

    @POST("/Usuarios/enviarMensajePush")
    Call<String> serverEnviarMensajePush(@Body ConexionPush conexionPush);

    @GET("Usuarios/obtenerListaTokenAdministradores")
    Call<List<String>> obtenerListaDeTokenAdmin();

    @POST("Usuarios/insertarUsuario/{permisoAdmin}")
    Call<Boolean> insertarUsuarioServer(@Body Usuario usuario,@Path("permisoAdmin") String permiso);

    @POST("Reporte/crearEnlaceLab/{idUsuario}")
    Call<Boolean> crearEnlaceReporteALab(@Path("idUsuario") String idUsuario);
}