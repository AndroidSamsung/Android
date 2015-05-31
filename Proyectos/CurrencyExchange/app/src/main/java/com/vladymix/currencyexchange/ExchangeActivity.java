package com.vladymix.currencyexchange;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;


public class ExchangeActivity extends Activity {

    private Boolean toEuro = false;
    Vibrator vib;
    int duration_vibrate = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        vib = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
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

    public void changeDirection(View v){
        try {
            vib.vibrate(duration_vibrate);
            toEuro = !toEuro;
            Animation giro;
            giro = AnimationUtils.loadAnimation(this, R.animator.girar);
            giro.reset();
            ((ImageButton) findViewById(R.id.imageButton)).startAnimation(giro);
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_SHORT).show();
        }



        if(toEuro);

    }
}
