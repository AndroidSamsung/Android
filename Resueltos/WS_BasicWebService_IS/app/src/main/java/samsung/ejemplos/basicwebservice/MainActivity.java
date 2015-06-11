package samsung.ejemplos.basicwebservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText dni;
	protected static Context contexto;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.contexto = this;
        setContentView(R.layout.activity_main);        
        
        dni = (EditText)findViewById(R.id.dniConsulta);
        
//      IntentFilter filter = new IntentFilter();
//      filter.addAction(ConsultaFichasService.ACTION_NO_RECORD);
//      filter.addAction(ConsultaFichasService.ACTION_ERROR);
//      filter.addAction(ConsultaFichasService.ACTION_FIN);
//      ConsultaFichasReceiver rcv = new ConsultaFichasReceiver();
//      registerReceiver(rcv, filter);
    }
    
    public void realizarPeticion(View v){
        DialogoConexion.verDialogo();		
        Intent msgIntent = new Intent(this, ConsultaFichasService.class);
		msgIntent.putExtra("DNI", dni.getText().toString());
		startService(msgIntent);    	
    }

}
