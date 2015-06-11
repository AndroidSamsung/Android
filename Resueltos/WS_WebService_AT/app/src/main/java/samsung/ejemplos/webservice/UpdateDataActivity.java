	package samsung.ejemplos.webservice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.WebService.Operacion;
import samsung.ejemplos.webservice.tasks.APIConnectionTask;
import samsung.ejemplos.webservice.tasks.UpdateTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDataActivity extends Activity implements CallbackResultados {
	
	private ArrayList<Ficha> registros;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_base);
		
		registros = getIntent().getParcelableArrayListExtra("data");

		((TextView)findViewById(R.id.lblTitulo)).setText(R.string.labelActualizacion);
		((TextView)findViewById(R.id.lblSubtitulo)).setText("");
		((LinearLayout) findViewById(R.id.botonera)).setVisibility(View.GONE);
		((ImageButton)findViewById(R.id.btnAccion)).setImageResource(android.R.drawable.ic_menu_edit);
		verRegistro();
	}
	
	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.setData(Uri.parse(getString(R.string.actualizacionCancelada)));
		setResult(RESULT_OK, data);
		super.onBackPressed();
	}

	private void verRegistro(){
		Ficha fichaActual = registros.get(0);
		((EditText) findViewById(R.id.dniText)).setText(fichaActual.getDNI());
		((EditText) findViewById(R.id.nombreText)).setText(fichaActual.getNombre());
		((EditText) findViewById(R.id.apellidosText)).setText(fichaActual.getApellidos());
		((EditText) findViewById(R.id.direccionText)).setText(fichaActual.getDireccion());
		((EditText) findViewById(R.id.telefonoText)).setText(fichaActual.getTelefono());
		((EditText) findViewById(R.id.equipoText)).setText(fichaActual.getEquipo());
		//((TextView) findViewById(R.id.regView)).setText("registro "+(registroActual+1)+" de "+numRegistros);
	}

	public void enviarRegistro(View v) {
		Ficha ficha = new Ficha();
		ficha.setDNI(((EditText) findViewById(R.id.dniText)).getText().toString());
		ficha.setNombre(((EditText) findViewById(R.id.nombreText)).getText().toString());
		ficha.setApellidos(((EditText) findViewById(R.id.apellidosText)).getText().toString());
		ficha.setDireccion(((EditText) findViewById(R.id.direccionText)).getText().toString());
		ficha.setTelefono(((EditText) findViewById(R.id.telefonoText)).getText().toString());
		ficha.setEquipo(((EditText) findViewById(R.id.equipoText)).getText().toString());
    	try {
    		String URL = getSharedPreferences("dasm", MODE_PRIVATE).getString("URL_API", "");
			//new UpdateTask(this, URL).execute(ficha.toJSONString());
	    	new APIConnectionTask(this, URL, Operacion.UPDATE).execute(ficha.toJSONString());
		} catch (JSONException e) {
			Toast.makeText(this, R.string.errorData, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void obtenerResultados(String resultado) {
		String mensaje = getString(R.string.errorAPI);
		try {
			JSONArray datos = new JSONArray(resultado);
			JSONObject numRegObj = datos.getJSONObject(0);
			int numReg = numRegObj.getInt("NUMREG");
			switch(numReg){
			case  0: mensaje = getString(R.string.actualizacionCancelada);
	                 break;
			case  1: mensaje = getString(R.string.actualizacionOk);
			}
		} catch (JSONException e) {
			mensaje = getString(R.string.errorData);
		} finally {
			Intent data = new Intent();
			data.setData(Uri.parse(mensaje));
			setResult(RESULT_OK, data);   
			finish();
		}
	}


}
