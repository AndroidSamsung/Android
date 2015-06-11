package ejemplos.samsung.bce_desdecero.utiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBCEHelper extends SQLiteOpenHelper {

    private static final String TABLE_CREATE =
            "CREATE TABLE infoRates ("+
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "moneda VARCHAR(3) NOT NULL,"+
                    "ratio REAL DEFAULT '0' NOT NULL,"+
                    "bandera INTEGER DEFAULT '0' NOT NULL)";

    public SQLiteBCEHelper(Context context, int version) {
        super(context, "BCERates", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS infoRates");
        db.execSQL(TABLE_CREATE);
    }
}
