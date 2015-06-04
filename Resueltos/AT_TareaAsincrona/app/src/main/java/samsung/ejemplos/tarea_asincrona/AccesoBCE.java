package samsung.ejemplos.tarea_asincrona;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AccesoBCE extends AsyncTask<Void, Void, InputStream> {
	private final String URL_BCE = "http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml";

	private ProgressDialog pDialog;
	private Context contexto;
	private URL url;

	public AccesoBCE(Context contexto){
		this.contexto = contexto;
		try {
			url = new URL(URL_BCE);				
		} catch (MalformedURLException e){
			Log.e("AccesoBCE", e.getMessage());
			cancel(true);
		}
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();            
        pDialog = new ProgressDialog(contexto);
        pDialog.setMessage("Conectando con el BCE");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
				Log.e("AccesoBCE","Proceso cancelado por el usuario");
                cancel(true);
            }
        });
        pDialog.show();
    }

	@Override
	protected InputStream doInBackground(Void... parameters) {
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e) {
			Log.e("AccesoBCE", e.getMessage());
			cancel(true);
		}
		return is;
	}
	
    @Override
    protected void onPostExecute(InputStream is) {
		if(is!=null){
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String linea;
				while((linea=br.readLine())!=null){
					Log.d("AccesoBCE", linea);
				}
				br.close();
			} catch (IOException e) {
				Log.e("AccesoBCE", e.getMessage());
			}
		}
		pDialog.dismiss();
	}
    
	@Override
	protected void onCancelled() {
		Toast.makeText(contexto, "Proceso cancelado por el usuario", Toast.LENGTH_SHORT).show();
		super.onCancelled();
	}

}

