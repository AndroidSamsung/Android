package samsung.ejemplos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivityLista extends Activity {

	private final int FLAGS_ACTIVITY  = 1;
	private final int DELETE_DIALOG = 0;

    private SQLiteDatabase db;
    private ArrayList<RateInfo> datos;
    private int registroActual;    
    private EditText monedaText, ratioText, banderaText;
	private ImageView banderaView;
    private Button btnActualizar;
    private LinearLayout botonesMovimiento, botonesPrincipales, botonesInsertar;
    private TextView etqRegistro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		monedaText = (EditText)findViewById(R.id.monedaText);
		ratioText = (EditText)findViewById(R.id.ratioText);
		banderaText = (EditText)findViewById(R.id.banderaText);
		banderaView = (ImageView)findViewById(R.id.banderaView);
		etqRegistro = (TextView)findViewById(R.id.etqRegistro);
						
		botonesMovimiento = (LinearLayout)findViewById(R.id.botonesMovimiento);
		botonesPrincipales = (LinearLayout)findViewById(R.id.botonesPrincipales);
		botonesInsertar = (LinearLayout)findViewById(R.id.botonesInsertar);
		btnActualizar = (Button)findViewById(R.id.btnActualizar);
		
		TextWatcher ControlModificacion =  new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) {
				btnActualizar.setEnabled(true);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
	    };
	    
		ratioText.addTextChangedListener(ControlModificacion); 
		banderaText.addTextChangedListener(ControlModificacion);
	    
		//obtenemos un helper para majejar la base de datos 'DBRates'
		MonedasSQLiteHelper ratesHelper =
            new MonedasSQLiteHelper(this, "DBRates.db", null, 1);
 
		//Abrimos la base de datos 'DBRates' en modo escritura
        db = ratesHelper.getWritableDatabase();

		//actualizamos id's banderas
		actualizarBanderas();
		//recuperamos todos los datos
        datos = getRatesList();
        
        if(datos.size()==0) {
        	prepararInsercion(null);
            Toast.makeText(this, "No hay registros...", Toast.LENGTH_SHORT).show();
        } else {
	        //visualizamos el primer registro
	        registroActual = 0;
	        verRegistro();       	
        }        
	}
	
    @Override
    protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    	case DELETE_DIALOG:
	    		return new AlertDialog.Builder(this)
		    		.setIcon(android.R.drawable.ic_dialog_alert)
		    		.setTitle("Borrado de Registros")
		    		.setMessage("¿Está seguro?")
	                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   borrarRegistro();
	                   }
	                 })
	                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	       					dialog.cancel();
	                   }
	    		    })
	    		  .create();
	    }
	    return null;
    }

    @Override
	protected void onDestroy() {
        //Cerramos la base de datos
		if(db!=null) db.close();
		super.onDestroy();
	}

	public void elegirBandera(View v){
		Intent i = new Intent(this, GridFlagsActivity.class);
		startActivityForResult(i, FLAGS_ACTIVITY);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)	{
		if (requestCode == FLAGS_ACTIVITY) {
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				if(extras != null) {
					int flagId = extras.getInt("flagId");
					banderaText.setText(String.valueOf(flagId));
					banderaView.setImageResource(flagId);
				}
			}
		}
	}

	private void verRegistro(){
		RateInfo aux = datos.get(registroActual);
		monedaText.setText(aux.getCurrency());
		ratioText.setText(String.valueOf(aux.getRate()));
		banderaText.setText(String.valueOf(aux.getFlag()));
		banderaView.setImageResource(aux.getFlag());
		etqRegistro.setText("Registro "+(registroActual+1)+" de "+datos.size());
		btnActualizar.setEnabled(false);
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
		if(registroActual<datos.size()-1)
			registroActual++;
		verRegistro();
	}
	
	public void irUltimo(View v){
		registroActual = datos.size()-1;
		verRegistro();
	}	
	
	public void prepararInsercion(View v){
		monedaText.setText("");
		ratioText.setText("");
		banderaText.setText("");
		banderaView.setImageResource(R.drawable.no_flag);
		monedaText.setEnabled(true);
		monedaText.requestFocus();
        botonesMovimiento.setVisibility(View.GONE);
		botonesPrincipales.setVisibility(View.GONE);
		botonesInsertar.setVisibility(View.VISIBLE);
		etqRegistro.setText("Insertando Registro...");
	}

	public void insertarRegistro(View v){
		String valorMoneda = monedaText.getText().toString().toUpperCase(Locale.getDefault());
		String valorRatio = ratioText.getText().toString();
		String valorBandera = banderaText.getText().toString();
		if(valorMoneda.equals("")){
			Toast.makeText(this, "Valor del campo 'modeda' obligatorio", Toast.LENGTH_SHORT).show();
			return;
		}
		if(valorRatio.equals("")){
			valorRatio = "0";
		}
		if(valorBandera.equals("")){
			valorBandera = "0";
		}
        ContentValues values = new ContentValues();
        values.put("moneda", valorMoneda);
        values.put("ratio", Double.parseDouble(valorRatio));
        values.put("bandera", Integer.parseInt(valorBandera));
        try{
            db.insertOrThrow("infoRates", null, values);
	        //datos = getRatesList();
	        RateInfo aux = new RateInfo(valorMoneda, valorRatio, valorBandera);
	        datos.add(aux);
            Toast.makeText(this, "Registro "+datos.size()+" añadido", Toast.LENGTH_SHORT).show();
	        irUltimo(null);
        }catch (SQLException e){
            Toast.makeText(this, "Fallo al insertar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        	Log.w("SQLite ratesInfo", "Fallo al insertar: " + e.getMessage());
    		verRegistro();
        }
		monedaText.setEnabled(false);
		botonesInsertar.setVisibility(View.GONE);
        botonesMovimiento.setVisibility(View.VISIBLE);
		botonesPrincipales.setVisibility(View.VISIBLE);
	}

	public void cancelarInsercion(View v){
        if(datos.size()>0) {
			monedaText.setEnabled(false);
			botonesInsertar.setVisibility(View.GONE);
	        botonesMovimiento.setVisibility(View.VISIBLE);
			botonesPrincipales.setVisibility(View.VISIBLE);
			verRegistro();
        } else {
            Toast.makeText(this, "No hay registros...", Toast.LENGTH_SHORT).show();
        }
	}
	
    @SuppressWarnings("deprecation")
	public void borrarRegistro (View v) {
    	showDialog(DELETE_DIALOG);
    }
    
    private void borrarRegistro() {
        String where = "moneda=?";
        String[] args = new String[]{monedaText.getText().toString()};
        try{
            db.delete("infoRates", where, args);
            Toast.makeText(this, "Registro "+(registroActual+1)+" borrado", Toast.LENGTH_SHORT).show();
            //datos = getRatesList();
            datos.remove(registroActual);
            if(datos.size()==0) {
            	prepararInsercion(null);
                Toast.makeText(this, "No hay registros...", Toast.LENGTH_SHORT).show();
            } else {
    	        //visualizamos el primer registro
            	irPrimero(null);      	
            }           
        }catch (SQLException e){
            Toast.makeText(this, "Fallo al borrar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        	Log.w("SQLite ratesInfo", "Fallo al borrar: " + e.getMessage());
        }
    }

    public void actualizarRegistro (View v) {
		String valorMoneda = monedaText.getText().toString().toUpperCase(Locale.getDefault());
		String valorRatio = ratioText.getText().toString();
		String valorBandera = banderaText.getText().toString();
		if(valorRatio.equals("")){
			valorRatio = "0";
		}
		if(valorBandera.equals("")){
			valorBandera = "0";
		}
        ContentValues values = new ContentValues();
        values.put("ratio", Double.parseDouble(valorRatio));
        values.put("bandera", Integer.parseInt(valorBandera));
        String where = "moneda=?";
        String[] args = new String[]{monedaText.getText().toString()};
        try{
            db.update("infoRates", values, where, args);
            Toast.makeText(this, "Registro "+(registroActual+1)+" actualizado", Toast.LENGTH_SHORT).show();
            //datos = getRatesList();
            RateInfo aux = new RateInfo(valorMoneda, valorRatio, valorBandera);
            datos.set(registroActual, aux);
        }catch (SQLException e){
            Toast.makeText(this, "Fallo al actualizar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        	Log.w("SQLite ratesInfo", "Fallo al actualizar: " + e.getMessage());
        }
        verRegistro();
    }

    private ArrayList<RateInfo> getRatesList(){
		ArrayList<RateInfo> rateInfoList = new ArrayList<RateInfo>();
    	Cursor cursor = db.rawQuery("SELECT * FROM infoRates", null);
    	if (cursor.getCount()>0){
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
				RateInfo aux = new RateInfo();
				aux.setCurrency(cursor.getString(0));
				aux.setRate(cursor.getDouble(1));
				aux.setFlag(cursor.getInt(2));
				rateInfoList.add(aux);
	         	cursor.moveToNext();    		
	        }
        }
        cursor.close();
        return rateInfoList;
	}
    
    private void actualizarBanderas(){
		String moneda;
		int flag_id;

    	Cursor cursor = db.rawQuery("SELECT moneda FROM infoRates", null);
        cursor.moveToFirst();
    	if (cursor.getCount()>0){
	        while (!cursor.isAfterLast()) {
	        	moneda = cursor.getString(0);
				flag_id = getResources().getIdentifier(moneda.toLowerCase() + "_flag", "drawable", getPackageName());
				ContentValues values = new ContentValues();
				values.put("bandera", flag_id);
				String where = "moneda='"+moneda+"'";
				db.update("infoRates", values, where, null);
	          	cursor.moveToNext();
	        }
        }
        cursor.close();
	}

	private void volcarInfoRates(View v){
		String moneda;
		double ratio;
		int bandera;

		Cursor cursor = db.rawQuery("SELECT * FROM infoRates", null);
		if (cursor.getCount()>0){
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				moneda = cursor.getString(0);
				ratio = cursor.getDouble(1);
				bandera = cursor.getInt(2);
				Log.d("SQLite ratesInfo", getString(R.string.salida_log, cursor.getPosition(), moneda, ratio, bandera));
				cursor.moveToNext();
			}
		}
		cursor.close();
	}
}
