package samsung.ejemplos.basicwebservice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.app.IntentService;
import android.content.Intent;
import android.net.http.AndroidHttpClient;

public class ConsultaFichasService extends IntentService {
	
	public static final String ACTION_FIN = "samsung.ejemplos.fichas.action.FIN";
	public static final String ACTION_ERROR = "samsung.ejemplos.fichas.action.ERROR";
	public static final String ACTION_NO_RECORD = "samsung.ejemplos.fichas.action.NO_RECORD";
	
    private final String URL = "http://demo.calamar.eui.upm.es/dasmapi/v1/demo/fichas";

    private final int NO_RECORD = 0; 
	private final int ERROR = -1; 
	
	public ConsultaFichasService() {
        super("ConsultaFichasService");
    }
	
	@Override
	protected void onHandleIntent(Intent intent) {

        String dni = intent.getStringExtra("DNI");
		String url_final = URL;
		if(!dni.equals("")){
			url_final += "/" + dni;
		}
		
		AndroidHttpClient cliente = AndroidHttpClient.newInstance("AndroidHttpClient");
		HttpGet peticionGet = new HttpGet(url_final);
				
		String actionAux = ACTION_ERROR;
		String informacion = "[{\"NUMREG\":-1}]";
		try {						
			HttpResponse respuesta = cliente.execute(peticionGet);
			if(respuesta.getStatusLine().getStatusCode()==200){
				HttpEntity cuerpoMensaje = respuesta.getEntity();
				informacion = EntityUtils.toString(cuerpoMensaje);		
			}
			JSONArray arrayJSON = new JSONArray(informacion);
			int numRegistros = arrayJSON.getJSONObject(0).getInt("NUMREG");
			
			switch (numRegistros){
				case ERROR: 
					actionAux = ACTION_ERROR;
					break;	
				case NO_RECORD: 
					actionAux = ACTION_NO_RECORD;
					break;	
				default: actionAux = ACTION_FIN;
			}
		} catch (Exception e) {			
		} finally {
			Intent bcIntent = new Intent();	
			bcIntent.setAction(actionAux);
			bcIntent.putExtra("textoJSON", informacion);
			sendBroadcast(bcIntent);
			cliente.close();
		}
	}
}
