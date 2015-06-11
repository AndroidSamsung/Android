package com.example.fabricio.correofragment;

/**
 * Created by Fabricio on 09/06/2015.
 */
public class Correo {
    String De;
    String Para;
    String Asunto;
    String Mensaje;

    public Correo(String de, String para, String asunto, String mensaje) {
        De = de;
        Para = para;
        Asunto = asunto;
        Mensaje = mensaje;
    }

    public String getDe() {
        return De;
    }

    public void setDe(String de) {
        De = de;
    }

    public String getPara() {
        return Para;
    }

    public void setPara(String para) {
        Para = para;
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String asunto) {
        Asunto = asunto;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
