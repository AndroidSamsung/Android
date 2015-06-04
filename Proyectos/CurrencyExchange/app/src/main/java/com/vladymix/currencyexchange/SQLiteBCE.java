package com.vladymix.currencyexchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Fabricio on 03/06/2015.
 */

//Solo puede estar instanciado una vez
public class SQLiteBCE {
    private static String BDD_NAME = "DBRates.db";

    private static SQLiteBCE ourInstance = null;

    private static SQLiteDatabase database;



    public static Context getInstance(Context context) {
        if(ourInstance==null){
            ourInstance = new SQLiteBCE(context);
        }
        return context;
    }

    //Esta solo se crea una sola vez
    private SQLiteBCE(Context context) {
        CurrencySQLite ratesHelper = new CurrencySQLite(context, BDD_NAME, null, 1);
        database = ratesHelper.getWritableDatabase();

    }


    public  boolean insertValues(String pais, String namecurrency, Double ratio, String currency, Integer id_flag, Integer id_circle ){
        try {

            ContentValues values = new ContentValues();
            values.put("pais", pais);
            values.put("moneda", currency);
            values.put("nombremoneda", namecurrency);
            values.put("ratio", ratio);
            values.put("bandera", id_circle);
            values.put("banderacircle", id_flag);
            database.insertOrThrow(CurrencySQLite.TABLE_NAME, null, values);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }
}
