package com.vladymix.currencyexchange;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CurrencySelectionActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);

        STATICVALUE.loadlista();

        setListAdapter(getListAdapter());

        //loadDBSQLITE();

    }

    public void loadDBSQLITE(){
        //obtenemos un helper para majejar la base de datos 'DBRates'
        CurrencySQLite ratesHelper =
                new  CurrencySQLite(this, "DBRates.db", null, 1);
               //Abrimos la base de datos 'DBRates' en modo escritura

        db = ratesHelper.getWritableDatabase();

        db.execSQL("DELETE FROM infoRates");
    }

    private Cursor getRatesCursor(){
        Cursor cursor = db.rawQuery("SELECT * FROM infoRates", null);
        return cursor;
    }

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

    public void saveXMTtoSQLITE(){


        try{
            for(Pais item : STATICVALUE.ListaPaises){
                ContentValues values = new ContentValues();
                values.put("moneda", item.getCurrency());
                values.put("pais", item.getNombre());
                values.put("nombremoneda", item.getNameCurrency());
                values.put("ratio", item.getValueCurrency());
                values.put("bandera", item.getIdflag());
                values.put("banderacircle", item.getIdcircle());
                if(item.getCurrency()!="")
                     db.insertOrThrow("infocurrencys",null,values);
            }
            datos= getRatesCursor();
            Toast.makeText(this, "Registro "+datos.getCount()+" añadido", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "Fallo al insertar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.w("SQLite ratesInfo", "Fallo al insertar: " + e.getMessage());

        }
    }

    public ArrayAdapter<Pais> getListAdapter(){

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


        Toast.makeText(this, "Loaded from SQLite", Toast.LENGTH_SHORT).show();

    }
    public void savetoSQLite(MenuItem menuItem){

        saveXMTtoSQLITE();
    }

    public void loadListFromXML(MenuItem menuItem){

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
           int pos = STATICVALUE.getPositionPaisbyCurecny(elemento.getAttribute("currency"));
            if(pos !=-1){
                Pais mod = STATICVALUE.ListaPaises.get(pos);
                mod.setValueCurrency(Double.valueOf(elemento.getAttribute("rate")));
                STATICVALUE.ListaPaises.set(pos, mod);
            }
        }

        Toast.makeText(this, "Loaded from XML", Toast.LENGTH_SHORT).show();

        setListAdapter(getListAdapter());

    }





    public static final class ViewHolder{
        TextView vPais;
        TextView vNameCurrency;
        TextView vCurrency;
        TextView vValueCurrency;
        ImageView vFlag;


    }


}
