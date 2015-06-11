package samsung.ejemplos.webservice.tasks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.CallbackResultados;
import samsung.ejemplos.webservice.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.Toast;

public class APIConnectionTask extends AsyncTask<String, Void, String> {

    private ProgressDialog pDialog;
	private Context contexto;
    private String URL;
    private WebService.Operacion operacion;
	
	public APIConnectionTask(Context contexto, String URL, WebService.Operacion operacion){
		this.contexto = contexto;
		this.URL = URL;
		this.operacion = operacion;
	}

	@Override
	protected void onPreExecute() {
		pDialog = new ProgressDialog(contexto);
		pDialog.setMessage(contexto.getString(R.string.peticionAPI));
		pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });
		pDialog.show();		
		super.onPreExecute();
	}

	private HttpRequestBase preprararOperacion(String... argumentos) throws UnsupportedEncodingException{
		HttpRequestBase peticion = null;		
		switch(operacion){
		case QUERY: 
			peticion = (HttpGet) new HttpGet(URL);
			break;
		case INSERT: 
			peticion = (HttpPost) new HttpPost(URL);
			((HttpPost)peticion).setEntity(new StringEntity(argumentos[0],HTTP.UTF_8));
			break;
		case UPDATE: 
			peticion = (HttpPut) new HttpPut(URL);
			((HttpPut)peticion).setEntity(new StringEntity(argumentos[0],HTTP.UTF_8));
			break;
		case DELETE: 
			peticion = (HttpDelete) new HttpDelete(URL);
			break;
		case CONN: 
			peticion = (HttpPost) new HttpPost(URL);
			break;
		}
		return peticion;
	}
	
	@Override
	protected String doInBackground(String... argumentos) {
		String informacion = "[{\"NUMREG\":-1}]";
		
		AndroidHttpClient cliente = AndroidHttpClient.newInstance("AndroidHttpClient");
		try {
			HttpResponse respuesta = cliente.execute(preprararOperacion(argumentos));
			if(respuesta.getStatusLine().getStatusCode()==200){
				HttpEntity cuerpoMensaje = respuesta.getEntity();
				informacion = EntityUtils.toString(cuerpoMensaje);		
			}
		} catch (IOException e) {
			//Log.d(contexto.getString(R.string.app_name), e.toString());
		} finally {
			cliente.close();
		}
		return informacion;
	}
	
	@Override
	protected void onCancelled() {
    	Toast.makeText(contexto, contexto.getString(R.string.procesoCancelado), Toast.LENGTH_SHORT).show();
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(String resultado) {
		pDialog.dismiss();
		((CallbackResultados)contexto).obtenerResultados(resultado);
		super.onPostExecute(resultado);
	}

}
