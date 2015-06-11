package com.vladymix.currencyexchange.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabricio on 08/06/2015.
 */
public class Pais implements Parcelable {
    String nombre;
    String currency;
    Double valueCurrency;
    String nameCurrency;
    int idflag;
    int idcircle;

    public Pais(){}

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


    @Override
    public int describeContents() {
        return 0;
    }

    /*
     String nombre;
    String currency;
    Double valueCurrency;
    String nameCurrency;
    int idflag;
    int idcircle;
     */

    public Pais(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.currency);
        dest.writeDouble(this.valueCurrency);
        dest.writeString(this.nameCurrency);
        dest.writeInt(this.idflag);
        dest.writeInt(this.idcircle);
    }

    private void readFromParcel(Parcel in) {
        this.nombre = in.readString();
        this.currency = in.readString();
        this.valueCurrency = in.readDouble();
        this.nameCurrency = in.readString();
        this.idflag = in.readInt();
        this.idcircle = in.readInt();
    }

    public static final Parcelable.Creator<Pais> CREATOR = new Parcelable.Creator<Pais>() {

        @Override
        public Pais createFromParcel(Parcel in) {
            return new Pais(in);
        }

        @Override
        public Pais[] newArray(int size) {
            return new Pais[size];
        }
    };

}
