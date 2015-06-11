package com.vladymix.emthorarios.utils;

/**
 * Created by Fabricio on 07/06/2015.
 */
public class Favorito {

    private int Id;//id BDD
    private String Tipo;//Parada o Linea
    private String Data; //Imagen patch
    private String Alto; //Imagen alto
    private String Ancho;  //Imagen ancho
    private String Color; //Imagen
    private String Nick_Name; //NOmbre Opcional
    private String idParada_Linea; //Parada -> 2603 Linea -> 145
    private String Titulo_Ubicacion_Direccion;// Ubicacion    Direccion
    private String Ubicacion_Direccion;//Nombre de la parada o Direccion de la linea

    public Favorito(){};

    public Favorito(String tipo, String idParada_Linea, String ubicacion_Direccion, String nick_Name) {

        if(tipo.equals("Stop")){
            Tipo = "Stop";
            this.idParada_Linea = idParada_Linea;
            Titulo_Ubicacion_Direccion = "Ubicacion";
            Ubicacion_Direccion = ubicacion_Direccion;
            Nick_Name = nick_Name;
            Data = Figura.DataParada;
            Alto = "35";
            Ancho = "30";

        }
        else{
            Tipo = "Line";
            this.idParada_Linea = idParada_Linea;
            Titulo_Ubicacion_Direccion = "Direccion";
            Ubicacion_Direccion = ubicacion_Direccion;
            Nick_Name = nick_Name;
            Data = Figura.DataLinea;
            Alto = "20";
            Ancho = "45";

        }

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getAlto() {
        return Alto;
    }

    public void setAlto(String alto) {
        Alto = alto;
    }

    public String getAncho() {
        return Ancho;
    }

    public void setAncho(String ancho) {
        Ancho = ancho;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getNick_Name() {
        return Nick_Name;
    }

    public void setNick_Name(String nick_Name) {
        Nick_Name = nick_Name;
    }

    public String getIdParada_Linea() {
        return idParada_Linea;
    }

    public void setIdParada_Linea(String idParada_Linea) {
        this.idParada_Linea = idParada_Linea;
    }

    public String getTitulo_Ubicacion_Direccion() {
        return Titulo_Ubicacion_Direccion;
    }

    public void setTitulo_Ubicacion_Direccion(String titulo_Ubicacion_Direccion) {
        Titulo_Ubicacion_Direccion = titulo_Ubicacion_Direccion;
    }

    public String getUbicacion_Direccion() {
        return Ubicacion_Direccion;
    }

    public void setUbicacion_Direccion(String ubicacion_Direccion) {
        Ubicacion_Direccion = ubicacion_Direccion;
    }
}
