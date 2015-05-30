package samsung.ejemplos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private double ratio;
	private String moneda;
	private int bandera;

	private EditText txtEuros;
	private EditText txtDivisa;
    private DecimalFormat decf;

	private SharedPreferences prefs;

    private final int LIST_ACTIVITY = 1;
    private final int PREFERENCE_ACTIVITY = 2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		txtEuros  = (EditText) findViewById(R.id.txtEuros);
        txtDivisa = (EditText) findViewById(R.id.txtDivisa);
        
	    //valores iniciales divisa 
	    prefs = getSharedPreferences("settings", MODE_PRIVATE);
		ratio = Double.longBitsToDouble(prefs.getLong("ratio", Double.doubleToLongBits(1.2701)));
		moneda = prefs.getString("moneda", "USD");
		bandera = prefs.getInt("bandera", getResources().getIdentifier("usd_flag", "drawable", getPackageName()));

		fijarDecimales();
 		visualizarDivisa();
   }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void visualizarDivisa(){
     	((TextView) findViewById(R.id.etqDivisa)).setText(moneda);
     	((Button) findViewById(R.id.botonDivisa)).setText(moneda);
    	((Button) findViewById(R.id.botonDivisa)).setCompoundDrawablesWithIntrinsicBounds(0, bandera , 0, 0);
		getActionBar().setSubtitle(getString(R.string.info_cambio, moneda, ratio));       
 		limpiar(null);
    }
    
    public void aEuros(View arg0){
		Double valorDivisa;
        try{
        	valorDivisa = Double.parseDouble(txtDivisa.getText().toString().replace(',', '.'));
    		txtEuros.setText(decf.format(valorDivisa/ratio));
    	} catch(Exception e) {
    		error();
    	}
    }
    
    public void aDivisa(View arg0){
     	Double valorEuros;
        try{
        	valorEuros = Double.parseDouble(txtEuros.getText().toString().replace(',', '.'));
        	txtDivisa.setText(decf.format(valorEuros*ratio));
    	} catch(Exception e) {
    		error();
    	}
    }
    
    public void verConfiguracion(MenuItem item1){
    	startActivityForResult(new Intent(this, SetPreferencesActivity.class), PREFERENCE_ACTIVITY);
    }
    
    public void limpiar(View arg0){
    	txtEuros.setHint(decf.format(0));
    	txtEuros.setText("");
    	txtDivisa.setHint(decf.format(0));
    	txtDivisa.setText("");
    }
    
    public void salir(View arg0){
        setResult(RESULT_OK);
        finish();
    }
    
    public void listarDivisas(MenuItem item1){
    	Intent i = new Intent(this, ListaDivisasActivity.class);
    	startActivityForResult(i,LIST_ACTIVITY);
    }
    
    private void error(){
    	Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
    	limpiar(null);
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)	{
    	if (requestCode == LIST_ACTIVITY) {
    		if (resultCode == RESULT_OK) {
//    	        Bundle extras = data.getExtras();
//    	        if(extras != null) {
//    	        	ratio = extras.getDouble("ratio");
//    	        	moneda = extras.getString("moneda");
//     	        	bandera = extras.getInt("bandera");
//    	        }
	        	ratio = data.getDoubleExtra("ratio", 0.0);
	        	moneda = data.getStringExtra("moneda");
 	        	bandera = data.getIntExtra("bandera", 0);
 	        	
	    		prefs.edit()
					.putLong("ratio", Double.doubleToLongBits(ratio))
					.putString("moneda", moneda)
					.putInt("bandera", bandera)
					.commit();

 	        	visualizarDivisa();
    		} 
		}
    	if (requestCode == PREFERENCE_ACTIVITY) {
		    fijarDecimales();
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
