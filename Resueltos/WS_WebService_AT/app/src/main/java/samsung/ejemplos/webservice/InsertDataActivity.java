package samsung.ejemplos.webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.WebService.Operacion;
import samsung.ejemplos.webservice.tasks.APIConnectionTask;
import samsung.ejemplos.webservice.tasks.InsertTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class InsertDataActivity extends Activity implements CallbackResultados {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_base);
		
		EditText txt_dni = (EditText) findViewById(R.id.dniText);	
		txt_dni.setText(getIntent().getExtras().getString("DNI"));
		((TextView)findViewById(R.id.lblTitulo)).setText(R.string.labelInsercion);
		((TextView)findViewById(R.id.lblSubtitulo)).setText("");
		((LinearLayout) findViewById(R.id.botonera)).setVisibility(View.GONE);
		((ImageButton)findViewById(R.id.btnAccion)).setImageResource(android.R.drawable.ic_menu_add);
	}
	
	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.setData(Uri.parse(getString(R.string.insercionCancelada)));
		setResult(RESULT_OK, data);
		super.onBackPressed();
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
			//new InsertTask(this, URL).execute(ficha.toJSONString());
	    	new APIConnectionTask(this, URL, Operacion.INSERT).execute(ficha.toJSONString());
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
			case  0: mensaje = getString(R.string.insercionCancelada);
	                 break;
			case  1: mensaje = getString(R.string.insercionOk);
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
