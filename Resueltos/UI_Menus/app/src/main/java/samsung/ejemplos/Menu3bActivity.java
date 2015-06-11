package samsung.ejemplos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class Menu3bActivity extends Activity {
	
	private static final int MNU_OPC4 = 4;
	
	private TextView lblMensaje;
	private CheckBox chkMenuExtendido;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_3_layout);
		
		lblMensaje = (TextView)findViewById(R.id.LblMensaje);
        chkMenuExtendido = (CheckBox)findViewById(R.id.ChkMenuExtendido);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.menu_3, menu);
		return true;
	}

	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
	    if(chkMenuExtendido.isChecked()) {
	    	if(menu.findItem(MNU_OPC4)==null){
	        	menu.add(Menu.NONE, MNU_OPC4, Menu.NONE, "Opcion4")
	        		.setIcon(android.R.drawable.ic_menu_camera);
	    	}
	    } else {
	    	menu.removeItem(MNU_OPC4);
	    }
	    return super.onPrepareOptionsMenu(menu);
    }
		
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.MnuOpc1:
            lblMensaje.setText("Opcion 1 pulsada!");
            return true;
        case R.id.MnuOpc2:
        	lblMensaje.setText("Opcion 2 pulsada!");;
            return true;
        case R.id.MnuOpc3:
        	lblMensaje.setText("Opcion 3 pulsada!");;
            return true;
        case R.id.SubMnuOpc1:
        	lblMensaje.setText("Opcion 3.1 pulsada!");
         	item.setChecked(true);
            return true;
        case R.id.SubMnuOpc2:
        	lblMensaje.setText("Opcion 3.2 pulsada!");
        	item.setChecked(true);
            return true;
        case MNU_OPC4:
        	lblMensaje.setText("Opcion 4 pulsada!");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
