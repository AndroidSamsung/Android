package ejemplos.samsung.bce_desdecero;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import ejemplos.samsung.bce_desdecero.utiles.DatosBCE;
import ejemplos.samsung.bce_desdecero.utiles.InterfazBCE;
import ejemplos.samsung.bce_desdecero.utiles.SQLiteBCEHelper;
import ejemplos.samsung.bce_desdecero.utiles.TareaBCE;

public class ListaDivisasActivity extends ListActivity {

    private SQLiteDatabase databaseBCE;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list);

        prefs = getSharedPreferences("divisas.xml", MODE_PRIVATE);

        SQLiteBCEHelper helperBCE = new SQLiteBCEHelper(this, 1);
        databaseBCE = helperBCE.getReadableDatabase();
        cargarListaMonedas(databaseBCE);
    }

    private void cargarListaMonedas(SQLiteDatabase db){
        String[] desde = {"moneda", "ratio", "bandera"};
        int[] hacia = {R.id.moneda, R.id.ratio, R.id.bandera};
        Cursor datos = db.rawQuery("SELECT * FROM infoRates", null);
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.item_divisa, datos, desde, hacia, 0);
        setListAdapter(adaptador);
    }

    @Override
    protected void onListItemClick(ListView lv, View v, int position, long id) {
        Cursor datos = ((SimpleCursorAdapter)lv.getAdapter()).getCursor();
        //datos.moveToPosition(position);
        Intent respuesta = new Intent();
        respuesta.putExtra("moneda", datos.getString(datos.getColumnIndex("moneda")));
        respuesta.putExtra("ratio", datos.getDouble(datos.getColumnIndex("ratio")));
        respuesta.putExtra("bandera", datos.getInt(datos.getColumnIndex("bandera")));
        setResult(RESULT_OK, respuesta);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(prefs.getBoolean("primera_ejecucion", true)){
            Toast.makeText(this, getString(R.string.seleccionar_divisa), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if(databaseBCE.isOpen())
            databaseBCE.close();
        super.onDestroy();
    }
}
