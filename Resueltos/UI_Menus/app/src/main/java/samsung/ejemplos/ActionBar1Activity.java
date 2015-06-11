package samsung.ejemplos;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ActionBar1Activity extends Activity {
	
	private TextView lblMensaje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_bar_1_layout);
		
		lblMensaje = (TextView)findViewById(R.id.LblMensaje);

		//getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setSubtitle("ActionBar");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	        case R.id.menu_new:
	            Log.i("ActionBar", "Nuevo!");
	            lblMensaje.setText("Nuevo!");
	            return true;
	        case R.id.menu_save:
	            Log.i("ActionBar", "Guardar!");;
	            lblMensaje.setText("Guardar!");
	            return true;
	        case R.id.menu_settings:
	            Log.i("ActionBar", "Settings!");;
	            lblMensaje.setText("Settings!");
	            return true;
	        case R.id.menu_email:
	            Log.i("ActionBar", "Email!");;
	            lblMensaje.setText("Email!");
	            return true;
	        case R.id.menu_share:
	            Log.i("ActionBar", "Share!");;
	            lblMensaje.setText("Share!");
	            return true;
	        case R.id.menu_about:
	            Log.i("ActionBar", "About!");;
	            lblMensaje.setText("About!");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
}
