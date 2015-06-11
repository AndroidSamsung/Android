package samsung.ejemplos.webservice;

import java.util.ArrayList;

import samsung.ejemplos.basicwebservice.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewDataActivity extends Activity {
	
	private ArrayList<Ficha> registros;
	private int registroActual, numRegistros;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_base);
		
		registros = getIntent().getParcelableArrayListExtra("data");
		numRegistros = registros.size();
		
		((TextView)findViewById(R.id.lblTitulo)).setText(R.string.labelConsulta);
		((ImageButton)findViewById(R.id.btnAccion)).setVisibility(View.GONE);
		((EditText) findViewById(R.id.nombreText)).setFocusable(false);
		((EditText) findViewById(R.id.apellidosText)).setFocusable(false);
		((EditText) findViewById(R.id.direccionText)).setFocusable(false);
		((EditText) findViewById(R.id.telefonoText)).setFocusable(false);
		((EditText) findViewById(R.id.equipoText)).setFocusable(false);
		if(numRegistros==1){
			((LinearLayout) findViewById(R.id.botonera)).setVisibility(View.GONE);
		} 
		registroActual = 0;
		verRegistro();
	}
	
	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		data.setData(Uri.parse(getString(R.string.consultaOk)));
		setResult(RESULT_OK, data);
		super.onBackPressed();
	}

	private void verRegistro(){
		Ficha fichaActual = registros.get(registroActual);
		((EditText) findViewById(R.id.dniText)).setText(fichaActual.getDNI());
		((EditText) findViewById(R.id.nombreText)).setText(fichaActual.getNombre());
		((EditText) findViewById(R.id.apellidosText)).setText(fichaActual.getApellidos());
		((EditText) findViewById(R.id.direccionText)).setText(fichaActual.getDireccion());
		((EditText) findViewById(R.id.telefonoText)).setText(fichaActual.getTelefono());
		((EditText) findViewById(R.id.equipoText)).setText(fichaActual.getEquipo());
		((TextView) findViewById(R.id.lblSubtitulo)).setText("registro "+(registroActual+1)+" de "+numRegistros);
	}
	
	public void irPrimero(View v){
		registroActual = 0;
		verRegistro();
	}
	
	public void irAnterior(View v){
		if(registroActual>0)
			registroActual--;
		verRegistro();
	}
	
	public void irSiguiente(View v){
		if(registroActual<numRegistros-1)
			registroActual++;
		verRegistro();
	}
	
	public void irUltimo(View v){
		registroActual = numRegistros-1;
		verRegistro();
	}
}
