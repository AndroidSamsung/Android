package ejemplos.samsung.euroratetosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RatesSQLiteHelper extends SQLiteOpenHelper{
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "infoRates";
	
	
    private static final String TABLE_CREATE = 
    		    "CREATE TABLE "+TABLE_NAME+" ("+
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    			"moneda VARCHAR(3) NOT NULL,"+
    			"ratio REAL DEFAULT '0' NOT NULL,"+
    			"bandera INTEGER DEFAULT '0' NOT NULL)";


	public RatesSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(TABLE_CREATE);
	}

	@Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
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
