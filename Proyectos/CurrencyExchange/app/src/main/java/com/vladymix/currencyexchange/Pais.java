package com.vladymix.currencyexchange;

/**
 * Created by Fabricio on 29/05/2015.
 */
public class Pais {
    String nombre;
    String currency;
    Double valueCurrency;
    String nameCurrency;
    int idflag;
    int idcircle;

    public Pais(String nombre, String currency, Double valueCurrency, String nameCurrency, int idflag, int idcircle) {
        this.nombre = nombre;
        this.currency = currency;
        this.valueCurrency = valueCurrency;
        this.nameCurrency = nameCurrency;
        this.idflag = idflag;
        this.idcircle = idcircle;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValueCurrency() {
        return valueCurrency;
    }

    public void setValueCurrency(Double valueCurrency) {
        this.valueCurrency = valueCurrency;
    }

    public String getNameCurrency() {
        return nameCurrency;
    }

    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    public int getIdflag() {
        return idflag;
    }

    public void setIdflag(int idflag) {
        this.idflag = idflag;
    }

    public int getIdcircle() {
        return idcircle;
    }

    public void setIdcircle(int idcircle) {
        this.idcircle = idcircle;
    }
}
