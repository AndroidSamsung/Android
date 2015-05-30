package com.vladymix.currencyexchange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Fabricio on 29/05/2015.
 */
public class CurrencySQLite  extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "infoRates";


    private static final String TABLE_CREATE =
            "CREATE TABLE "+TABLE_NAME+" ("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "moneda VARCHAR(3) NOT NULL,"+
                    "pais VARCHAR(80) NOT NULL ,"+
                    "nombremoneda VARCHAR(150) NOT NULL,"+
                    "ratio REAL DEFAULT '0' NOT NULL,"+
                    "bandera INTEGER DEFAULT '0' NOT NULL),"+
                    "banderacircle INTEGER DEFAULT '0' NOT NULL)";

    public CurrencySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLE_CREATE);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente
        //      la opción de eliminar la tabla anterior y crearla de nuevo
        //      vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la
        //      tabla antigua a la nueva, por lo que este método debería
        //      ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        //Se crea la nueva versión de la tabla
        db.execSQL(TABLE_CREATE);
    }
}
