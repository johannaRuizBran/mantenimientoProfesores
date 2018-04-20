package com.example.joha.mantenimiento.Conexiones;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Esta clase permite la incorporación de la estructura para la implementación de mensajería push, en donde se debe
 * de almacenar el mensaje que se enviará, el dispositivo al que va dirigido, id del reporte al cual se desea brindar la información,
 * la identificación de la aplicacion en firebase y el id del enviador de también definido en firebase de la aplicación
 * a la cual se desea enviar el mensaje.
 *
 * @see:
 * @author: Johanna Ruiz B.
 */

public class ConexionPush {
    String appID;
    String senderID;
    String deviceID;
    String mensaje;
    int idReporte;
    /*
    String appID = "AAAAgw2gviA:APA91bEUY2_Rm2j3mnb2EkPDlNr4967SBkCD8ivrVo5pRQ4BswCLQuI_zrkVmRu1td-NZuepl5XpUN9Kv_F4ZlDXASpDs4f5KBVe0mW4YartERGKCjEfqjqH1CvTvkyjeNuqOSy97N4z";
    String senderID = "562869354016";

    //Joha
    this.appID="AAAAU-NQ3Ak:APA91bE09KiPNvglm_JW6J25YXBF1crW94fEAjQPcXxEONIsskszOAtUUvBXmOInMf_XwXX3tqabswj6bHIEqJ4iX0BrfrMCDnbVEZjvda6q0hylyfNCxDh0FtIBdX6smRaWu9P9hXwH";
    this.senderID=  "360296012809";
    * */
    public ConexionPush(String deviceID, String mensaje,int id) {
        this.appID="AAAAgw2gviA:APA91bEUY2_Rm2j3mnb2EkPDlNr4967SBkCD8ivrVo5pRQ4BswCLQuI_zrkVmRu1td-NZuepl5XpUN9Kv_F4ZlDXASpDs4f5KBVe0mW4YartERGKCjEfqjqH1CvTvkyjeNuqOSy97N4z";
        this.senderID=  "562869354016";
        this.deviceID = deviceID;
        this.mensaje = mensaje;
        this.idReporte= id;

    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}