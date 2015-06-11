package com.vladymix.currencyexchange.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vladymix.currencyexchange.FINALVALUE;

/**
 * Created by Fabricio on 08/06/2015.
 */
public class BDD {
    private static BDD ourInstance = new BDD();
    private SQLiteDatabase db;
    private Context context;

    public static BDD getInstance() {
        return ourInstance;
    }

    private BDD() {
    }

    public Cursor getCursor(){
        return db.rawQuery("SELECT * FROM "+CurrencySQLite.TABLE_NAME, null);
    }
    public SQLiteDatabase getSQLiteDataBase(){
        return this.db;
    }

    public void setInstance(Context context){
        this.context = context;
        CurrencySQLite ratesHelper = new CurrencySQLite(context, CurrencySQLite.BDD_NAME, null, 1);
        //Abrimos la base de datos 'DBRates' en modo escritura
        db = ratesHelper.getWritableDatabase();
    }

    public boolean insertCurrency(ContentValues values){
        try {
            db.insertOrThrow(CurrencySQLite.TABLE_NAME, null, values);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public  boolean insertCurrency(String pais, String namecurrency, Double ratio, String currency, Integer id_flag, Integer id_circle ){
        try {
            ContentValues values = new ContentValues();
            values.put("pais", pais);
            values.put("moneda", currency);
            values.put("nombremoneda", namecurrency);
            values.put("ratio", ratio);
            values.put("bandera", id_circle);
            values.put("banderacircle", id_flag);
            db.insertOrThrow(CurrencySQLite.TABLE_NAME, null, values);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public  boolean deleteTable( ){
        try{
            db.execSQL("DELETE FROM " + CurrencySQLite.TABLE_NAME);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean updateRegister(ContentValues values){
        try {
            String where="moneda = '"+values.get("moneda")+"'";
            db.update(CurrencySQLite.TABLE_NAME, values, where, null);
            return true;
        }
        catch (Exception ex){
            return  false;
        }
    }

    public void dismiss(){
     //Cerramos la base de datos
        if(db.isOpen()) db.close();
    }

    public Pais getPaisByCurrency_Code(String moneda){
        String where = " WHERE moneda='"+moneda+"'";

        Cursor datos = db.rawQuery("SELECT * FROM "+CurrencySQLite.TABLE_NAME+where, null);

        if (datos.getCount()>0){
            datos.moveToFirst();

            return new Pais(datos.getString(datos.getColumnIndex("pais")),
                    datos.getString(datos.getColumnIndex("moneda")),
                    datos.getDouble(datos.getColumnIndex("ratio")),
                    datos.getString(datos.getColumnIndex("nombremoneda")),
                    datos.getInt(datos.getColumnIndex("bandera")),
                    datos.getInt(datos.getColumnIndex("banderacircle"))
                    );
        }
        return  null;

    }


}
