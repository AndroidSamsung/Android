package ejemplos.samsung.api_rest_basico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewDataActivity extends Activity {
	
	private int registroActual, numRegistros;
	private JSONArray datos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_view);
		
		String response=getIntent().getStringExtra("data");
		try {
			datos = new JSONArray(response);
			numRegistros = datos.getJSONObject(0).getInt("NUMREG");
		} catch (JSONException e) {
			procesarError();
		}
		if(numRegistros==1){
			((LinearLayout) findViewById(R.id.botonera)).setVisibility(View.GONE);
		}
		registroActual = 1;
		verRegistro();
	}

	private void verRegistro(){
		try{
			JSONObject objetoActual = datos.getJSONObject(registroActual);
			((EditText) findViewById(R.id.dniText)).setText(objetoActual.getString("DNI"));
			((EditText) findViewById(R.id.nombreText)).setText(objetoActual.getString("Nombre"));
			((EditText) findViewById(R.id.apellidosText)).setText(objetoActual.getString("Apellidos"));
			((EditText) findViewById(R.id.direccionText)).setText(objetoActual.getString("Direccion"));
			((EditText) findViewById(R.id.telefonoText)).setText(objetoActual.getString("Telefono"));
			((EditText) findViewById(R.id.equipoText)).setText(objetoActual.getString("Equipo"));
			((TextView) findViewById(R.id.regView)).setText("registro "+registroActual+" de "+numRegistros);
		} catch (JSONException e){
			procesarError();
		}
	}

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.setData(Uri.parse(getString(R.string.consultaOK)));
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }

	private void procesarError(){
		Intent data = new Intent();
		data.setData(Uri.parse(getString(R.string.errorData)));
		setResult(RESULT_CANCELED, data);
		finish();
	}
	
	public void irPrimero(View v){
		registroActual = 1;
		verRegistro();
	}
	
	public void irAnterior(View v){
		if(registroActual>1)
			registroActual--;
		verRegistro();
	}
	
	public void irSiguiente(View v){
		if(registroActual<numRegistros)
			registroActual++;
		verRegistro();
	}
	
	public void irUltimo(View v){
		registroActual = numRegistros;
		verRegistro();
	}
}
