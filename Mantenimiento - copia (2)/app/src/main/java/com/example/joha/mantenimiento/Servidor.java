package com.example.joha.mantenimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Joha on 26/5/2017.
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
}