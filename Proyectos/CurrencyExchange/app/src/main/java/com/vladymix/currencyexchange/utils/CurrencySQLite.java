package com.vladymix.currencyexchange.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fabricio on 29/05/2015.
 */
public class CurrencySQLite  extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "infoCurrencys";
    public static final String BDD_NAME = "DBRates.db";


    private static final String TABLE_CREATE =
                    "CREATE TABLE "+TABLE_NAME+" ("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "moneda VARCHAR(3) NOT NULL,"+
                    "pais VARCHAR(80) NOT NULL ,"+
                    "nombremoneda VARCHAR(150) NOT NULL,"+
                    "ratio REAL DEFAULT '0' NOT NULL,"+
                    "bandera INTEGER DEFAULT '0' NOT NULL,"+
                    "banderacircle INTEGER DEFAULT '0' NOT NULL)";

    public CurrencySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aqui utilizamos directamente
        //      la opcion de eliminar la tabla anterior y crearla de nuevo
        //      vacia con el nuevo formato.
        //      Sin embargo lo normal sera que haya que migrar datos de la
        //      tabla antigua a la nueva, por lo que este metodo deberia
        //      ser mas elaborado.

        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        //Se crea la nueva version de la tabla
        db.execSQL(TABLE_CREATE);
    }


    public static  Cursor getDatosByFilter(String filtro, SQLiteDatabase db){
        String[] campos = {"_id","pais", "nombremoneda","ratio", "moneda", "bandera","banderacircle"};
        String where = "moneda like ?";
        String[] argumentos = {filtro+"%"};
        return db.query(CurrencySQLite.TABLE_NAME, campos, where, argumentos, null, null, null, null);
    }


}
