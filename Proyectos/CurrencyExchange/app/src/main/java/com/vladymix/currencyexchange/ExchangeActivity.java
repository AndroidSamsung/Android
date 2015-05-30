package com.vladymix.currencyexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ExchangeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exchange, menu);
        return true;
    }


    public void selecctionCurrency(MenuItem menuItem){

        Intent i = new Intent(this, CurrencySelectionActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIADA_LISTA_MONEDAS);
    }
}
