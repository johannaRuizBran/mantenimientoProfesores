package com.example.joha.mantenimiento.Clases;
/**
 * Esta clase permite gestionar la información y formar la estructura del token del usuario utilizado
 * para la implementación de mensajes Push.
 *
 * @see:
 * @author: Johanna Ruiz B.
 */
public class Token {
    String token;
    String nombreUsuario;

    public Token(String token, String nombreUsuario) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}