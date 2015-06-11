package samsung.ejemplos.webservice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.WebService.Operacion;
import samsung.ejemplos.webservice.tasks.APIConnectionTask;
import samsung.ejemplos.webservice.tasks.DeleteTask;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeleteDataActivity extends Activity implements CallbackResultados {
	
	private ArrayList<Ficha> registros;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_base);
		
		registros = getIntent().getParcelableArrayListExtra("data");
		
		((TextView)findViewById(R.id.lblTitulo)).setText(R.string.labelBorrado);
		((TextView)findViewById(R.id.lblSubtitulo)).setText("");
		((LinearLayout) findViewById(R.id.botonera)).setVisibility(View.GONE);
		((ImageButton)findViewById(R.id.btnAccion)).setImageResource(android.R.drawable.ic_menu_delete);
		((EditText) findViewById(R.id.nombreText)).setFocusable(false);
		((EditText) findViewById(R.id.apellidosText)).setFocusable(false);
		((EditText) findViewById(R.id.direccionText)).setFocusable(false);
		((EditText) findViewById(R.id.telefonoText)).setFocusable(false);
		((EditText) findViewById(R.id.equipoText)).setFocusable(false);

		verRegistro();
	}
	
	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.setData(Uri.parse(getString(R.string.borradoCancelado)));
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
		String txt_dni = ((EditText) findViewById(R.id.dniText)).getText().toString();
		String URL = getSharedPreferences("dasm", MODE_PRIVATE).getString("URL_API", "");
		//new DeleteTask(this, URL).execute(txt_dni);
		String url_final = URL + "/" + txt_dni;
    	new APIConnectionTask(this, url_final, Operacion.DELETE).execute();
	}

	@Override
	public void obtenerResultados(String resultado) {
		String mensaje = getString(R.string.errorAPI);
		try {
			JSONArray datos = new JSONArray(resultado);
			JSONObject numRegObj = datos.getJSONObject(0);
			int numReg = numRegObj.getInt("NUMREG");
			switch(numReg){
			case  0: mensaje = getString(R.string.borradoCancelado);
	                 break;
			case  1: mensaje = getString(R.string.borradoOk);
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
