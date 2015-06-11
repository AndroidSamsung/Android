package com.vladymix.currencyexchange;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.vladymix.currencyexchange.utils.BDD;
import com.vladymix.currencyexchange.utils.MyService;
import com.vladymix.currencyexchange.utils.Pais;

import java.util.Calendar;


public class ExchangeActivity extends Activity {

    private Boolean toEuro = false;
    private Vibrator vib;
    private int duration_vibrate = 20;
    private SharedPreferences prefs;
    private Double total;

    //region Variables Moneda Pais
    private Pais pais;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        vib = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

        LoadPreferences();
    }


    public void updataSQLite(MenuItem menuItem){
        Intent msgIntent = new Intent(this, MyService.class);
        msgIntent.putExtra("UPDATE", "UPDATE");
        startService(msgIntent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)	{
        if (requestCode == FINALVALUE.ID_ACTIVIADA_LISTA_MONEDAS){
            if(resultCode == RESULT_OK){
                graficarPais(data);
            }
        }
        if(requestCode == FINALVALUE.ID_PREFERENCE_ACTIVITY){
            LoadPreferences();
            Toast.makeText(this, "Setings load", Toast.LENGTH_SHORT).show();
        }
    }

    //region Preferences


    private void LoadPreferences(){
        STATICVALUE.loadlista();
        prefs = getSharedPreferences(FINALVALUE.NAME_FILE_SETTINGS, MODE_PRIVATE);
        if(!prefs.getBoolean(SetPreferencesActivity.FIRST_LOAD,false)){
            loadDialog("Preparando lista de monedas");

            BDD bdd = BDD.getInstance();
            bdd.setInstance(this);
            for(Pais item :STATICVALUE.ListaPaises){
                ContentValues values = new ContentValues();
                values.put("moneda", item.getCurrency());
                values.put("pais", item.getNombre());
                values.put("nombremoneda", item.getNameCurrency());
                values.put("ratio", item.getValueCurrency());
                values.put("bandera", item.getIdflag());
                values.put("banderacircle", item.getIdcircle());
                bdd.insertCurrency(values);
            }
            bdd.dismiss();
            //Almaceno que existe ya la base de datos
            prefs.edit().putBoolean(SetPreferencesActivity.FIRST_LOAD, true)
                    .commit();

            dismmisDialog();
        }

        ((TextView)findViewById(R.id.textDateUpdate)).setText(prefs.getString(SetPreferencesActivity.DATE_LAST_UPDATE, "29/05/2015"));

        //Revisar si esta activado la actualizacion automatica
        if(prefs.getBoolean(SetPreferencesActivity.UPDATE_AUTOMATIC,false)){
            //Revisar si ya esta actualizada
            if (prefs.getInt(SetPreferencesActivity.LAST_UPDATE, 0) != hoy()) {
                Intent msgIntent = new Intent(this, MyService.class);
                msgIntent.putExtra("UPDATE", "UPDATE");
                startService(msgIntent);
            }
            else{
                Toast.makeText(this,"Ratios actualizados", Toast.LENGTH_SHORT).show();
            }
        }
        //Empezar con moneda de preferencia
        if(prefs.getBoolean(SetPreferencesActivity.START_WITH_CURRENCY, false)){
            BDD bdd = BDD.getInstance();
            bdd.setInstance(this);
            pais = bdd.getPaisByCurrency_Code(prefs.getString(SetPreferencesActivity.CURRENCY_FAVORITE, "EUR"));
            bdd.dismiss();
            graficarPais(pais);
            Toast.makeText(this,"Moneda favorita", Toast.LENGTH_SHORT).show();
        }
        /*
        pais.setNombre(prefs.getString(SetPreferencesActivity.NOMBRE, "Europa"));
        pais.setCurrency(prefs.getString(SetPreferencesActivity.CURRENCY, "EUR"));
        pais.setValueCurrency(Double.longBitsToDouble(prefs.getLong(SetPreferencesActivity.RATIO, 1)));
        pais.setNameCurrency(prefs.getString(SetPreferencesActivity.NAME_CURRENCY, "Euro"));
        pais.setIdflag(prefs.getInt(SetPreferencesActivity.IDFLAG, R.drawable.flag_empty));
        pais.setIdcircle(prefs.getInt(SetPreferencesActivity.IDFLAGCIRCLE, R.drawable.flag_empty));
    */
     }

    public int hoy(){
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
//        Calendar fecha = new GregorianCalendar();
//        int anio = fecha.get(Calendar.YEAR);
//        int mes  = fecha.get(Calendar.MONTH);
//        int dia  = fecha.get(Calendar.DAY_OF_MONTH);
//        return dia+"/"+mes+"/"+anio;
    }

    //endregion


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


    private void graficarPais(Pais pais){
        if(pais!=null){
            ((ImageView)findViewById(R.id.divisaFlag)).setImageResource(pais.getIdflag());
            (findViewById(R.id.divisaFlag)).setTag(pais.getIdflag());
            ((TextView)findViewById(R.id.divisaCurrency)).setText(pais.getCurrency());
            ((TextView)findViewById(R.id.divisaValorMoneda)).setText(String.valueOf(pais.getValueCurrency()));
            grafiarDirecctionExchange();
        }
    }

    private void graficarPais(Intent data){
        pais = new Pais(data.getStringExtra(FINALVALUE.PAIS),
                data.getStringExtra(FINALVALUE.CURRENCY),
                data.getDoubleExtra(FINALVALUE.RATIO, 1),
                data.getStringExtra(FINALVALUE.NAME_CURRENCY),
                data.getIntExtra(FINALVALUE.IDFLAG, R.drawable.flag_empty),
                data.getIntExtra(FINALVALUE.IDFLAG,R.drawable.circle_eur));

        if(pais!=null){
            ((ImageView)findViewById(R.id.divisaFlag)).setImageResource(pais.getIdflag());
            (findViewById(R.id.divisaFlag)).setTag(pais.getIdflag());

            ((TextView)findViewById(R.id.divisaCurrency)).setText(pais.getCurrency());
            ((TextView)findViewById(R.id.divisaValorMoneda)).setText(String.valueOf(pais.getValueCurrency()));

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
                total = input / pais.getValueCurrency();
            } else {
                Double input = Double.valueOf(((TextView) findViewById(R.id.input_euros)).getText().toString());
                total = input *  pais.getValueCurrency();
            }
            ((TextView) findViewById(R.id.total_cambio)).setText(String.valueOf(total));
        }
        catch (Exception ex){}

    }

    public void navegaraSeleccionCurrency(View v){
        Intent i = new Intent(this, CurrencySelectionActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIADA_LISTA_MONEDAS);
    }

    ProgressDialog progressDialog;
    private void loadDialog(String mensaje){
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle(mensaje);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }
    private void dismmisDialog(){
        if(progressDialog!=null)
        progressDialog.dismiss();
    }
}
