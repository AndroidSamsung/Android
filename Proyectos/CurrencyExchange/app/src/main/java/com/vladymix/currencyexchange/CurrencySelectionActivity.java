package com.vladymix.currencyexchange;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CurrencySelectionActivity extends ListActivity {

    public static Document document;
    private SQLiteDatabase db;
    private Cursor datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);

        loadDBSQLITE();

        STATICVALUE.loadlista();

        setListAdapter(getlistAdapter());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ViewHolder info = (ViewHolder)v.getTag();
        Intent i = new Intent();
        i.putExtra(FINALVALUE.PAIS, info.vPais.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }


    //region SQLITE
    public void loadDBSQLITE(){
        try {
            //obtenemos un helper para majejar la base de datos 'DBRates'
            CurrencySQLite ratesHelper = new CurrencySQLite(this, "DBRates.db", null, 1);
            //Abrimos la base de datos 'DBRates' en modo escritura
            db = ratesHelper.getWritableDatabase();

        }
        catch (Exception ex){
            PushMensaje(ex.getMessage());
        }
    }

    public void deleteTable(String table){
        CurrencySQLite.deleteTable(table, db);
    }

    private Cursor getRatesCursor(){
       return CurrencySQLite.getDatos(db);
    }

    //endregion


    @Override
    protected void onDestroy() {
        try {
            //Cerramos el cursor
            if (datos != null) datos.close();
            //Cerramos la base de datos
            if (db.isOpen()) db.close();

        }
        catch (Exception e){

        }
        super.onDestroy();
    }

    public void loadXMLCurrencys(){
        final String TAG = " [readXMLRatesFileIntoArrayList] ";

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
            int pos = STATICVALUE.getPositionPaisbyCurrecny(elemento.getAttribute("currency"));
            if(pos !=-1){
                Pais mod = STATICVALUE.ListaPaises.get(pos);
                mod.setValueCurrency(Double.valueOf(elemento.getAttribute("rate")));
                STATICVALUE.ListaPaises.set(pos, mod);
            }
        }
        PushMensaje("Load XML");
    }

    public void saveXMTtoSQLITE(){

        try{
            loadXMLCurrencys();
            deleteTable(CurrencySQLite.TABLE_NAME); //Remplazar con update

            for(Pais item : STATICVALUE.ListaPaises){
                ContentValues values = new ContentValues();
                values.put("moneda", item.getCurrency());
                values.put("pais", item.getNombre());
                values.put("nombremoneda", item.getNameCurrency());
                values.put("ratio", item.getValueCurrency());
                values.put("bandera", item.getIdflag());
                values.put("banderacircle", item.getIdcircle());
                CurrencySQLite.insertValues(values, db);
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Fallo al insertar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.w("SQLite ratesInfo", "Fallo al insertar: " + e.getMessage());
        }
    }

    public ArrayAdapter<Pais> getlistAdapter(){

        ArrayAdapter<Pais> adaptadorFicheroDatos = new ArrayAdapter<Pais>(
                        this,
                        R.layout.view_item_currency,
                        R.id.vFlag,
                        STATICVALUE.ListaPaises){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.view_item_currency, null);
                    holder = new ViewHolder();
                    holder.vFlag = (ImageView) convertView.findViewById(R.id.vFlag);
                    holder.vPais = (TextView) convertView.findViewById(R.id.vPais);
                    holder.vNameCurrency = (TextView) convertView.findViewById(R.id.vNameCurrency);
                    holder.vValueCurrency = (TextView) convertView.findViewById(R.id.vValueCurrency);
                    holder.vCurrency = (TextView) convertView.findViewById(R.id.vCurrency);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

              //  int flag_id = getResources().getIdentifier(listItem[0].toLowerCase() + "_flag", "drawable", getPackageName());
                Pais p = STATICVALUE.ListaPaises.get(position);

                holder.vFlag.setImageResource( STATICVALUE.ListaPaises.get(position).getIdcircle());
                holder.vFlag.setTag( STATICVALUE.ListaPaises.get(position).getIdcircle());
                holder.vPais.setText(STATICVALUE.ListaPaises.get(position).getNombre());
                holder.vNameCurrency.setText( STATICVALUE.ListaPaises.get(position).getNameCurrency());
                holder.vCurrency.setText( STATICVALUE.ListaPaises.get(position).getCurrency());
                holder.vValueCurrency.setText(String.valueOf( STATICVALUE.ListaPaises.get(position).getValueCurrency()));

                return (convertView);
            }

          };

        return adaptadorFicheroDatos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency_selection, menu);
        return true;
    }

    public void loadListFromSQLite(MenuItem menuItem){

        String[] from = {"pais",   "nombremoneda",    "ratio",           "moneda",    "banderacircle"};
        int[] to = {R.id.vPais, R.id.vNameCurrency, R.id.vValueCurrency, R.id.vCurrency, R.id.vFlag};

        datos = CurrencySQLite.getDatos(db);

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(
                this, R.layout.view_item_currency, datos, from, to, 0);

        adaptador.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return CurrencySQLite.getDatosByFilter(constraint.toString(),db);
            }
        });

        getListView().setTextFilterEnabled(true);

        setListAdapter(adaptador);

        EditText busqueda = (EditText)findViewById(R.id.textFilter);

        busqueda.addTextChangedListener(new WatcherText());

        PushMensaje("Load list from SQlite");
    }

    public void savetoSQLite(MenuItem menuItem){
        saveXMTtoSQLITE();
        datos= getRatesCursor();
        PushMensaje("Registros " + datos.getCount() + " añadidos");
    }

    public void loadListFromXML(MenuItem menuItem){

        loadXMLCurrencys();
        PushMensaje("Load list from XML");
        setListAdapter(getlistAdapter());
    }

    public static final class ViewHolder{
        TextView vPais;
        TextView vNameCurrency;
        TextView vCurrency;
        TextView vValueCurrency;
        ImageView vFlag;


    }

    public void PushMensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT ).show();
    }

    public class WatcherText implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ((Filterable) getListAdapter()).getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    public void loadURL(MenuItem menuItem) throws InterruptedException, ExecutionException {

        ProgressDialog progressDialog = new ProgressDialog(CurrencySelectionActivity.this);
        progressDialog.setMessage("Leyendo URL Currencys Euro");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        TareaAsincrona tareaAsincrona = new TareaAsincrona(new Callback());
        tareaAsincrona.setProgressDialog(progressDialog);
        tareaAsincrona.execute();
    }


    public class Callback implements ResultadosURL {
        @Override
        public void onTaskCompleted(Document document) {
            // do something with result here!
            if (document != null) {
                NodeList listaCube = document.getElementsByTagName("Cube");

                Element elemento;
                for (int i = 2; i < listaCube.getLength(); i++) {
                    elemento = (Element) listaCube.item(i);
                    String currency = elemento.getAttribute("currency");
                    int pos = STATICVALUE.getPositionPaisbyCurrecny(currency);
                    if (pos != -1) {
                        Pais mod = STATICVALUE.ListaPaises.get(pos);
                        mod.setValueCurrency(Double.valueOf(elemento.getAttribute("rate")));
                        STATICVALUE.ListaPaises.set(pos, mod);
                    }
                }
                setListAdapter(getlistAdapter());
                PushMensaje("With callback");

            }
        }
    }
}
