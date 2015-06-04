package com.vladymix.currencyexchange;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


public class ExchangeActivity extends Activity {

    private Boolean toEuro = false;
    Vibrator vib;
    int duration_vibrate = 20;

    Pais pais;
    Double total, ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        vib = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)	{
        if (requestCode == FINALVALUE.ID_ACTIVIADA_LISTA_MONEDAS){
            if(resultCode == RESULT_OK){
                graficarPais(data.getStringExtra(FINALVALUE.PAIS));
            }
        }
        if(requestCode == FINALVALUE.ID_PREFERENCE_ACTIVITY){
            Toast.makeText(this, "Setings load", Toast.LENGTH_SHORT).show();
        }
    }

    //region MENU TOOLBAR

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

    public void verConfiguracion(MenuItem menuItem){
        try {
            startActivityForResult(new Intent(this, SetPreferencesActivity.class), FINALVALUE.ID_PREFERENCE_ACTIVITY);
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void addRegistro(MenuItem menuItem){
        Intent intent = new Intent(this, AddDivisaActivity.class);
        startActivity(intent);
    }

    //endregion




    private void graficarPais(String Name){
        pais = STATICVALUE.getPaisbyName(Name);
        if(pais!=null){
            ((ImageView)findViewById(R.id.divisaFlag)).setImageResource(pais.getIdflag());
            (findViewById(R.id.divisaFlag)).setTag(pais.getIdflag());
            ((TextView)findViewById(R.id.divisaCurrency)).setText(pais.getCurrency());
            ((TextView)findViewById(R.id.divisaValorMoneda)).setText(String.valueOf(pais.getValueCurrency()));
            ratio = pais.getValueCurrency();
            grafiarDirecctionExchange();
        }

    }

    private void grafiarDirecctionExchange(){
        if(pais==null)
            return;

        if(toEuro){
            ((TextView)findViewById(R.id.label_dirrection)).setText(pais.getCurrency()+"/EUR");
        }
        else{
            ((TextView)findViewById(R.id.label_dirrection)).setText("EUR/" + pais.getCurrency());
        }
    }


    public void changeDirection(View v){
        try {
            vib.vibrate(duration_vibrate);
            RotateAnimation r;
            toEuro = !toEuro;

            if(toEuro) {
                r = new RotateAnimation(0f, -180f, 60f, 45f); // HERE
                r.setStartOffset(100);
                r.setDuration(500);
                r.setFillAfter(true); //HERE
              }
            else{
                r = new RotateAnimation(-180f, 0f, 60f, 45f); // HERE
                r.setStartOffset(100);
                r.setDuration(500);
                r.setFillAfter(true); //HERE

            }
            (findViewById(R.id.image_arrow)).startAnimation(r);
            grafiarDirecctionExchange();
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void calculateExchange(View v){
        try {
            vib.vibrate(duration_vibrate);
            if (toEuro) {
                Double input = Double.valueOf(((TextView) findViewById(R.id.input_divisa)).getText().toString());
                total = input / ratio;
            } else {
                Double input = Double.valueOf(((TextView) findViewById(R.id.input_euros)).getText().toString());
                total = input * ratio;
            }
            ((TextView) findViewById(R.id.total_cambio)).setText(String.valueOf(total));
        }
        catch (Exception ex){}

    }

    public void navegaraSeleccionCurrency(View v){
        Intent i = new Intent(this, CurrencySelectionActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIADA_LISTA_MONEDAS);
    }

}
