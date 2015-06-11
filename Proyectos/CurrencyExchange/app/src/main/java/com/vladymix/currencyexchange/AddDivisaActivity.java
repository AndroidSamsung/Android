package com.vladymix.currencyexchange;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vladymix.currencyexchange.utils.CurrencySQLite;


public class AddDivisaActivity extends Activity {

    EditText add_name, add_currency, add_namecurrency, add_valuecurrency;
    TextView add_id_flag_circle, add_id_flag;

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_divisa);
        InitializeComponents();
        CleanComponents(null);
    }

    private void InitializeComponents(){
        add_name = (EditText)findViewById(R.id.add_name);
        add_currency = (EditText)findViewById(R.id.add_codecurrency);
        add_namecurrency = (EditText)findViewById(R.id.add_namecurrency);
        add_valuecurrency = (EditText)findViewById(R.id.add_valuecurrency);
        add_id_flag_circle = (TextView)findViewById(R.id.add_id_circle_flag);
        add_id_flag = (TextView)findViewById(R.id.add_id_flag);
    }

    public void CleanComponents(View v){
        String clean="";
        add_name.setText(clean);
        add_currency.setText(clean);
        add_namecurrency.setText(clean);
        add_valuecurrency.setText(clean);

        ((ImageView)findViewById(R.id.add_circle)).setImageResource(R.drawable.circle_eur);
        ((ImageView)findViewById(R.id.add_flag)).setImageResource(R.drawable.flag_eur);
        add_id_flag_circle.setText(String.valueOf(R.drawable.circle_eur));
        add_id_flag.setText(String.valueOf(R.drawable.flag_eur));
    }

    public void addItem(View v){
       CurrencySQLite ratesHelper =     new CurrencySQLite(this, CurrencySQLite.BDD_NAME, null, 1);
        //Abrimos la base de datos 'DBRates' en modo escritura
        db = ratesHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("moneda", add_currency.getText().toString());
        values.put("pais", add_name.getText().toString());
        values.put("nombremoneda", add_namecurrency.getText().toString());
        values.put("ratio", add_valuecurrency.getText().toString());
        values.put("bandera", Integer.valueOf(add_id_flag.getText().toString()));
        values.put("banderacircle", Integer.valueOf(add_id_flag_circle.getText().toString()));

        db.insertOrThrow(CurrencySQLite.TABLE_NAME, null, values);

        Toast.makeText(this, "Moneda guardada", Toast.LENGTH_SHORT).show();



    }
}
