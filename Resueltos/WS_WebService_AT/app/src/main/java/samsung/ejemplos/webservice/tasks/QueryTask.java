package samsung.ejemplos.webservice.tasks;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import samsung.ejemplos.basicwebservice.R;
import samsung.ejemplos.webservice.CallbackResultados;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.widget.Toast;

public class QueryTask extends AsyncTask<String, Void, String> {

    private String URL;
    private ProgressDialog pDialog;
	private Context contexto;
	
	public QueryTask(Context contexto, String URL){
		this.contexto = contexto;
		this.URL = URL;
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

	@Override
	protected String doInBackground(String... argumentos) {
		String informacion = "[{\"NUMREG\":-1}]";
		
		String dni = argumentos[0];
		String url_final = URL;
		if(!dni.equals("")){
			url_final += "/" + dni;
		}
		
		AndroidHttpClient cliente = AndroidHttpClient.newInstance("AndroidHttpClient");
		HttpGet peticion = new HttpGet(url_final);
		try {
			HttpResponse respuesta = cliente.execute(peticion);
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
