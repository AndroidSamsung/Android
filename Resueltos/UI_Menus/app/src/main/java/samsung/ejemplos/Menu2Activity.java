package samsung.ejemplos;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Menu2Activity extends Activity {

	private TextView lblMensaje;
	private ListView lstLista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_2_layout);
		
		//Obtenemos las referencias a los controles
        lblMensaje = (TextView)findViewById(R.id.LblMensaje);
        lstLista = (ListView)findViewById(R.id.LstLista);
        
        //Rellenamos la lista con datos de ejemplo
        String[] datos =
            new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
         
        ArrayAdapter<String> adaptador =
            new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, datos);
        
        lstLista.setAdapter(adaptador);
        
        //Asociamos los men�s contextuales a los controles
        registerForContextMenu(lblMensaje);
        registerForContextMenu(lstLista);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_2, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_settings:
            lblMensaje.setText("Menú de configuración pulsado!");
            return true;
        default:
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, 
    		                        ContextMenuInfo menuInfo) {
    	MenuInflater inflater = getMenuInflater();
    	
		switch(v.getId()){
		case R.id.LblMensaje: 				
				menu.setHeaderTitle("Menú etiqueta:");
				inflater.inflate(R.menu.menu_2_ctx_etiqueta, menu);
				break;
		case R.id.LstLista:
				AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
				String elemento = String.valueOf(info.position);
				//String elemento = lstLista.getAdapter().getItem(info.position).toString();
				menu.setHeaderTitle("Menú lista ["+elemento+"]:");
				inflater.inflate(R.menu.menu_2_ctx_lista, menu);
				break;
		}
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	
		AdapterContextMenuInfo info = 
    				(AdapterContextMenuInfo) item.getMenuInfo();
    	
		switch (item.getItemId()) {
			case R.id.CtxLblOpc1:
				lblMensaje.setText("Etiqueta: Opción 1 pulsada!");
				return true;
			case R.id.CtxLblOpc2:
				lblMensaje.setText("Etiqueta: Opción 2 pulsada!");
				return true;
			case R.id.CtxLstOpc1:
				lblMensaje.setText("Lista[" + info.position + "]: Opción 1 pulsada!");
				return true;
			case R.id.CtxLstOpc2:
				lblMensaje.setText("Lista[" + info.position + "]: Opción 2 pulsada!");
				return true;
			default:
				return super.onContextItemSelected(item);
		}
    }
}