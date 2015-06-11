package com.vladymix.currencyexchange;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
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

import com.vladymix.currencyexchange.utils.BDD;
import com.vladymix.currencyexchange.utils.CurrencySQLite;
import com.vladymix.currencyexchange.utils.Pais;


public class CurrencySelectionActivity extends ListActivity {

    private Cursor cursor;
    private SQLiteDatabase db;

    String[] from = {"pais",   "nombremoneda",    "ratio",           "moneda",  "bandera",  "banderacircle"};
    int[] to = {R.id.vPais, R.id.vNameCurrency, R.id.vValueCurrency, R.id.vCurrency,R.id.vFlag, R.id.vFlagCircle};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);


        try {
            BDD bdd = BDD.getInstance();
            bdd.setInstance(this);
            cursor = bdd.getCursor();
            db = bdd.getSQLiteDataBase();

            SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.view_item_currency, cursor, from, to, 0);
            adaptador.setFilterQueryProvider(new FilterQueryProvider() {
                public Cursor runQuery(CharSequence constraint) {
                    return CurrencySQLite.getDatosByFilter(constraint.toString(), db);
                }
            });

            getListView().setTextFilterEnabled(true);


            setListAdapter(adaptador);

            EditText busqueda = (EditText) findViewById(R.id.textFilter);

            busqueda.addTextChangedListener(new WatcherText());

        }
        catch (Exception ex){
            Toast.makeText(this, "Error "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onListItemClick(ListView lv, View v, int position, long id) {
        Cursor datos = ((SimpleCursorAdapter)lv.getAdapter()).getCursor();
        Intent i = new Intent();

        //Almaceno los datos del pais en el intent
        i.putExtra(FINALVALUE.NOMBRE, datos.getString(datos.getColumnIndex("pais")));
        i.putExtra(FINALVALUE.CURRENCY, datos.getString(datos.getColumnIndex("moneda")));
        i.putExtra(FINALVALUE.RATIO, datos.getDouble(datos.getColumnIndex("ratio")));
        i.putExtra(FINALVALUE.NAME_CURRENCY, datos.getString(datos.getColumnIndex("nombremoneda")));
        i.putExtra(FINALVALUE.IDFLAG, datos.getInt(datos.getColumnIndex("bandera")));
        i.putExtra(FINALVALUE.IDFLAGCIRCLE, datos.getInt(datos.getColumnIndex("banderacircle")));

        setResult(RESULT_OK, i);
        finish();
    }

    public ArrayAdapter<Pais> getlistadapter(){

        ArrayAdapter<Pais> adaptadorFicheroDatos = new ArrayAdapter<Pais>(
                        this,
                        R.layout.view_item_currency,
                        R.id.vFlagCircle,
                        STATICVALUE.ListaPaises){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.view_item_currency, null);
                    holder = new ViewHolder();
                    holder.vFlag = (ImageView) convertView.findViewById(R.id.vFlagCircle);
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

}
