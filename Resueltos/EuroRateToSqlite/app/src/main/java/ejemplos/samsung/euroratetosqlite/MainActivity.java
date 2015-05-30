package ejemplos.samsung.euroratetosqlite;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends ListActivity {

    private SQLiteDatabase db;
    private  Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //obtenemos un helper para majejar la base de datos 'DBRates'
        RatesSQLiteHelper ratesHelper =
                new RatesSQLiteHelper(this, "DBRates.db", null, 1);

        //Abrimos la base de datos 'DBRates' en modo escritura
        db = ratesHelper.getWritableDatabase();

        db.execSQL("DELETE FROM infoRates");
        fromXMLtoSQLite();

        String[] from = {"moneda", "ratio", "bandera"};
        int[] to = {R.id.moneda, R.id.ratio, R.id.bandera};

        cursor = db.rawQuery("SELECT * FROM infoRates", null);

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(
                this, R.layout.item_divisa, cursor, from, to, 0);

        adaptador.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return recuperarDatos(constraint.toString());
            }
        });

        getListView().setTextFilterEnabled(true);

        setListAdapter(adaptador);

        EditText busqueda = (EditText)findViewById(R.id.busqueda);

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((Filterable) getListAdapter()).getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    private Cursor recuperarDatos(String filtro){
        String[] campos = {"_id", "moneda", "ratio", "bandera"};
        String where = "moneda like ?";
        String[] argumentos = {filtro+"%"};
        return db.query("infoRates", campos, where, argumentos, null, null, null, null);
    }

    private void fromXMLtoSQLite(){
        final String TAG = " [fromXMLtoSQLite] ";

        Document doc = null;
        try {
            InputStream source = getResources().openRawResource(R.raw.eurofxref);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(source);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.d(TAG, e.getMessage());
        } catch (SAXException e) {
            Log.d(TAG, e.getMessage());
        }
        NodeList listaCube = doc.getElementsByTagName("Cube");
        Element elemento;
        for(int i=2; i<listaCube.getLength(); i++) {
            elemento = (Element) listaCube.item(i);

            String moneda = elemento.getAttribute("currency");
            double ratio = Double.parseDouble(elemento.getAttribute("rate"));
            int flag_id = getResources().getIdentifier(moneda.toLowerCase() + "_flag", "drawable", getPackageName());

            ContentValues values = new ContentValues();
            values.put("moneda", moneda);
            values.put("ratio", ratio);
            values.put("bandera", flag_id);

            db.insert("infoRates", null, values);
        }
    }

    @Override
    protected void onDestroy() {
        //Cerramos el cursor
        if(cursor!=null) cursor.close();
        //Cerramos la base de datos
        if(db.isOpen()) db.close();
        super.onDestroy();
    }

}
