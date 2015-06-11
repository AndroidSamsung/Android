package com.vladymix.emthorarios.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fabricio on 07/06/2015.
 */
public class EMTSQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "EMTHorarios";

    private static final String TABLE_CREATE =
            "CREATE TABLE "+TABLE_NAME+" ("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "Tipo VARCHAR(12) NULL,"+
                    "Data VARCHAR(700) NULL,"+
                    "Alto VARCHAR(10) NULL,"+
                    "Ancho VARCHAR(10) NULL,"+
                    "Color VARCHAR(10) NULL,"+
                    "Nick_Name VARCHAR(100) NULL,"+
                    "idParada_Linea VARCHAR(20) NULL,"+
                    "Titulo_Ubicacion_Direccion VARCHAR(300) NULL,"+
                    "Ubicacion_Direccion VARCHAR(300) NULL)";

    public EMTSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creacion de la tabla
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //Se crea la nueva version de la tabla
        db.execSQL(TABLE_CREATE);
    }
}
