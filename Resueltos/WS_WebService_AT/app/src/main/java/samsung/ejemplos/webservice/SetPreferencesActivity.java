package samsung.ejemplos.webservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.tasks.ConnectionTask;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SetPreferencesActivity extends Activity implements CallbackResultados {
	private String URL_CONN;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction()
							.replace(android.R.id.content, new PrefsFragment())
							.commit();
	}

	public static class PrefsFragment extends PreferenceFragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	        getPreferenceManager().setSharedPreferencesName("dasm");
			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.connection_preferences);
		}
	}
	
	@Override
	public void onBackPressed() {
		fijarURLs();
    	new ConnectionTask(this,URL_CONN).execute();
	}

	private void fijarURLs(){
		SharedPreferences prefs = getSharedPreferences("dasm", MODE_PRIVATE);
		String servidor = prefs.getString("servidor", ""); // "10.0.2.2" Special alias to your host loopback interface
		String usuario  = prefs.getString("usuario", "");
		String clave    = prefs.getString("clave", "");
		
		URL_CONN = "http://" + servidor + "/dasmapi/v1/" + usuario + "/connect/" + clave;
		String URL_API = "http://" + servidor + "/dasmapi/v1/" + usuario + "/fichas";
        prefs.edit()
        	.putString("URL_CONN", URL_CONN)
        	.putString("URL_API", URL_API)
        	.commit();
	}
	
	@Override
	public void obtenerResultados(String resultado) {
		try {
			JSONArray datos = new JSONArray(resultado);
			JSONObject numRegObj = datos.getJSONObject(0);
			int numReg = numRegObj.getInt("NUMREG");
			if (numReg == 0){
				String mensaje = getString(R.string.conexionOk);
				Intent data = new Intent();
				data.setData(Uri.parse(mensaje));
				setResult(RESULT_OK, data);   
				finish();
			} else {
		    	Toast.makeText(this, R.string.errorConexion, Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
	    	Toast.makeText(this, R.string.errorConexion, Toast.LENGTH_SHORT).show();
		}
	}
}
