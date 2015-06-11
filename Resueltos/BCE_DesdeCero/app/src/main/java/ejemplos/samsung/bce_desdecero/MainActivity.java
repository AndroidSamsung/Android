package ejemplos.samsung.bce_desdecero;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import ejemplos.samsung.bce_desdecero.utiles.DatosBCE;
import ejemplos.samsung.bce_desdecero.utiles.InterfazBCE;
import ejemplos.samsung.bce_desdecero.utiles.SQLiteBCEHelper;
import ejemplos.samsung.bce_desdecero.utiles.TareaBCE;


public class MainActivity extends Activity implements InterfazBCE {

    private double ratio;
    private String moneda;
    private int bandera;

    private EditText txtEuros;
    private EditText txtDivisa;
    private DecimalFormat decf;

    private SharedPreferences prefs;
    private SQLiteDatabase databaseBCE;

    private final int LISTA_DIVISAS = 001;
    private final int PREFERENCIAS = 002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEuros  = (EditText) findViewById(R.id.txtEuros);
        txtDivisa = (EditText) findViewById(R.id.txtDivisa);

        prefs = getSharedPreferences("divisas.xml", MODE_PRIVATE);
        if (prefs.getInt("ultima_actualizacion", 0) != hoy()) {
            new TareaBCE(this).execute();
        }
        bandera = prefs.getInt("bandera", R.drawable.eur_flag);
        moneda = prefs.getString("moneda", "EUR");
        ratio = Double.longBitsToDouble(prefs.getLong("ratio", 1));

        fijarDecimales();
        visualizarDivisa();
    }

    public void listarDivisas (MenuItem item){
        Intent divisas = new Intent(getApplicationContext(), ListaDivisasActivity.class);
        startActivityForResult(divisas,LISTA_DIVISAS);
    }

    public void verConfiguracion(MenuItem item1){
        startActivityForResult(new Intent(this, PreferenciasActivity.class), PREFERENCIAS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void aEuros(View v){
        Double valorDivisa;
        try{
            valorDivisa = Double.parseDouble(txtDivisa.getText().toString().replace(',', '.'));
            txtEuros.setText(decf.format(valorDivisa/ratio));
        } catch(Exception e) {
            error();
        }
    }

    public void aDivisa(View v){
        Double valorEuros;
        try{
            valorEuros = Double.parseDouble(txtEuros.getText().toString().replace(',', '.'));
            txtDivisa.setText(decf.format(valorEuros*ratio));
        } catch(Exception e) {
            error();
        }
    }
    public void limpiar(View v){
        txtEuros.setHint(decf.format(0));
        txtEuros.setText("");
        txtDivisa.setHint(decf.format(0));
        txtDivisa.setText("");
    }

    public void salir(View v){
        setResult(RESULT_OK);
        finish();
    }

    private void error(){
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        limpiar(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)	{
        if (requestCode == LISTA_DIVISAS) {
            if (resultCode == RESULT_OK) {
                ratio = data.getDoubleExtra("ratio", 0.0);
                moneda = data.getStringExtra("moneda");
                bandera = data.getIntExtra("bandera", 0);
                prefs.edit().putInt("bandera", bandera)
                        .putString("moneda", moneda)
                        .putLong("ratio", Double.doubleToLongBits(ratio))
                        .putBoolean("primera_ejecucion", false)
                        .commit();
                visualizarDivisa();
            }
        }
        if (requestCode == PREFERENCIAS) {
            fijarDecimales();
        }
    }

    private void visualizarDivisa(){
        ((TextView) findViewById(R.id.etqDivisa)).setText(moneda);
        ((Button) findViewById(R.id.botonDivisa)).setText(moneda);
        ((Button) findViewById(R.id.botonDivisa)).setCompoundDrawablesWithIntrinsicBounds(0, bandera , 0, 0);
        getActionBar().setSubtitle(getString(R.string.info_cambio, moneda, ratio));
        limpiar(null);
    }

    public int hoy(){
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
//        Calendar fecha = new GregorianCalendar();
//        int anio = fecha.get(Calendar.YEAR);
//        int mes  = fecha.get(Calendar.MONTH);
//        int dia  = fecha.get(Calendar.DAY_OF_MONTH);
//        return dia+"/"+mes+"/"+anio;
    }

    @Override
    public void recuperarRatios(ArrayList<DatosBCE> lista) {
        int bandera;
        ContentValues valores;

        SQLiteBCEHelper helperBCE = new SQLiteBCEHelper(this, 1);
        databaseBCE = helperBCE.getWritableDatabase();
        valores = new ContentValues();
        databaseBCE.execSQL("DELETE FROM infoRates");
        for(DatosBCE dato: lista){
            valores.put("moneda", dato.getMoneda());
            valores.put("ratio", dato.getRatio());
            if(dato.getMoneda().equals(moneda)){
                ratio = dato.getRatio();
                prefs.edit().putLong("ratio", Double.doubleToLongBits(ratio)).commit();
                getActionBar().setSubtitle(getString(R.string.info_cambio, moneda, ratio));
            }
            bandera = getResources().getIdentifier(dato.getMoneda().toLowerCase()+"_flag", "drawable", getPackageName());
            valores.put("bandera", bandera);
            databaseBCE.insert("infoRates", null, valores);
        }
        Toast.makeText(this, getString(R.string.bbdd_actualizada), Toast.LENGTH_SHORT).show();
//        String[] args = {moneda};
//        Cursor cursorAUX = databaseBCE.rawQuery("SELECT ratio FROM infoRates WHERE moneda = ?", args);
//        cursorAUX.moveToFirst();
//        ratio = cursorAUX.getDouble(0);
//        cursorAUX.close();
//        getActionBar().setSubtitle(getString(R.string.info_cambio, moneda, ratio));
//        prefs.edit().putLong("ratio", Double.doubleToLongBits(ratio)).commit();
        prefs.edit().putInt("ultima_actualizacion", hoy()).commit();
        if(prefs.getBoolean("primera_ejecucion", true)){
            listarDivisas(null);
        }
    }

    private void fijarDecimales(){
        String separatorDigits = prefs.getString("separator_digits_preference", ".");
        DecimalFormatSymbols puntoDecimal = new DecimalFormatSymbols();
        puntoDecimal.setDecimalSeparator(separatorDigits.charAt(0));
        decf = new DecimalFormat("0.0000", puntoDecimal);
        int decimalDigits = Integer.valueOf(prefs.getString("decimal_digits_preference", "4"));
        decf.setMaximumFractionDigits(decimalDigits);
        decf.setMinimumFractionDigits(decimalDigits);
        limpiar(null);
    }

}
