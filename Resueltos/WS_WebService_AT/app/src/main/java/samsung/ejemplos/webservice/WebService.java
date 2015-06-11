package samsung.ejemplos.webservice;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.tasks.APIConnectionTask;
import samsung.ejemplos.webservice.tasks.QueryTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WebService extends Activity implements CallbackResultados {

	public enum Operacion {
		QUERY (1, ViewDataActivity.class),
		INSERT(2, InsertDataActivity.class),
		UPDATE(3, UpdateDataActivity.class),
		DELETE(4, DeleteDataActivity.class),
		CONN  (5, SetPreferencesActivity.class);
		
		private int valor;
		private Class<?> cls;
		
		private Operacion(int valor, Class<?> cls){
			this.valor = valor;
			this.cls = cls;
		}
	};
	private Operacion operacion;
	
	private EditText dni;
    private SharedPreferences prefs;
    private String URL;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dni = (EditText)findViewById(R.id.dniConsulta);
        prefs = getSharedPreferences("dasm", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
    		operacion = Operacion.CONN;
        	startActivityForResult(new Intent(this, SetPreferencesActivity.class), operacion.valor);
        } else {
        	this.URL  = prefs.getString("URL_API", "");
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
    	getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void lanzarPreferencias(MenuItem item){
		operacion = Operacion.CONN;
    	startActivityForResult(new Intent(this, Operacion.CONN.cls), Operacion.CONN.valor);
    }
    
    public void verFichas(View v){
		String txt_dni = dni.getText().toString();
		if(!txt_dni.equals("") && !validarDNI(txt_dni)){
			Toast.makeText(this, R.string.invalidDNI, Toast.LENGTH_LONG).show();
			return;
		}
		operacion = Operacion.QUERY;
    	//new QueryTask(this, URL).execute(txt_dni);
		String url_final = URL;
		if(!txt_dni.equals("")){
			url_final += "/" + txt_dni;
		}
    	new APIConnectionTask(this, url_final, Operacion.QUERY).execute();
	}

	public void insertarFicha(View v) {
		String txt_dni = dni.getText().toString();
		if(!validarDNI(txt_dni)){
			Toast.makeText(this, R.string.invalidDNI, Toast.LENGTH_LONG).show();
			return;
		}
		operacion = Operacion.INSERT;
    	//new QueryTask(this, URL).execute(txt_dni);
		String url_final = URL + "/" + txt_dni;
    	new APIConnectionTask(this, url_final, Operacion.QUERY).execute();
	}
	
	public void modificarFicha(View v) {
		String txt_dni = dni.getText().toString();
		if(!validarDNI(txt_dni)){
			Toast.makeText(this, R.string.invalidDNI, Toast.LENGTH_LONG).show();
			return;
		}
		operacion = Operacion.UPDATE;
    	//new QueryTask(this, URL).execute(txt_dni);
		String url_final = URL + "/" + txt_dni;
    	new APIConnectionTask(this, url_final, Operacion.QUERY).execute();
	}
    
	public void borrarFicha(View v) {
		String txt_dni = dni.getText().toString();
		if(!validarDNI(txt_dni)){
			Toast.makeText(this, R.string.invalidDNI, Toast.LENGTH_LONG).show();
			return;
		}
		operacion = Operacion.DELETE;
    	//new QueryTask(this, URL).execute(txt_dni);
		String url_final = URL + "/" + txt_dni;
    	new APIConnectionTask(this, url_final, Operacion.QUERY).execute();
	}
    
	private boolean validarDNI(String dni){
		Pattern dniPattern = Pattern.compile("\\d{8}");
		Matcher matcher = dniPattern.matcher(dni);
		return matcher.matches(); 
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)	{
    	super.onActivityResult(requestCode, resultCode, data);
		Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();
		if(requestCode == Operacion.CONN.valor){
	        if (prefs.getBoolean("firstrun", true)) {
	        	prefs.edit().putBoolean("firstrun", false).commit();
	        } 
	        this.URL  = prefs.getString("URL_API", "");
		}
	}

    @Override
	public void obtenerResultados(String resultado) {
		try {
			JSONArray datos = new JSONArray(resultado);
			JSONObject numRegObj = datos.getJSONObject(0);
			int numReg = numRegObj.getInt("NUMREG");
			if(numReg == -1){
				Toast.makeText(this, getString(R.string.errorAPI), Toast.LENGTH_SHORT).show();
				return;
			}
			switch(operacion){
			case UPDATE: 
			case DELETE: 
			case QUERY:  procesarOperacion(numReg, datos);
						 break;
			case INSERT: procesarInsercion(numReg);
						 break;
			default:
				break;
			}
		} catch (JSONException e) {
			Toast.makeText(this, R.string.errorData, Toast.LENGTH_LONG).show();
			//Log.d(getString(R.string.app_name), e.getMessage());
		}
	}
    
    private void procesarOperacion(int numReg, JSONArray datos) throws JSONException{
    	if (numReg == 0){
    		Toast.makeText(this, getString(R.string.sinRegistros), Toast.LENGTH_SHORT).show();
    	} else {
        	ArrayList<Ficha> registros = new ArrayList<Ficha>();
        	for(int i=1; i<datos.length(); i++){
        		registros.add(new Ficha(datos.getJSONObject(i)));
        	}
        	Intent i = new Intent(this, operacion.cls);
    		i.putParcelableArrayListExtra("data", registros);

    		startActivityForResult(i, operacion.valor);
		}    	
    }
        
    private void procesarInsercion(int numReg) throws JSONException{
    	if (numReg == 0){
			String txt_dni = dni.getText().toString();
			Intent i = new Intent(this, InsertDataActivity.class);
			i.putExtra("DNI", txt_dni);
			startActivityForResult(i, Operacion.INSERT.valor);
    	} else {
    		Toast.makeText(this, getString(R.string.existRecord), Toast.LENGTH_SHORT).show();
		}    	
    }
    
}
