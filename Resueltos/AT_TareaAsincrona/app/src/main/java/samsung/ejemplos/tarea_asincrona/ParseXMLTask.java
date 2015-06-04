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

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParseXMLTask extends AsyncTask<Void, Void, Document> {
	private final String URL_BCE = "http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml";	

	private ProgressDialog pDialog;
	private Context contexto;
	private URL url;
	private String mensajeError;
	
	public ParseXMLTask(Context contexto){
		this.contexto = contexto;
		try {
			url = new URL(URL_BCE);				
		} catch (MalformedURLException e){
			mensajeError = e.getMessage();
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
    			mensajeError = "Proceso cancelado por el usuario";
                cancel(true);
            }
        });
        pDialog.show();
    }

	@Override
	protected Document doInBackground(Void... parameters) {
		Document doc = null;
		
		try {
			InputSource source = new InputSource(url.openStream());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(source);
		} catch (Exception e) {
			mensajeError = e.getMessage();
			cancel(true);
		}
		return doc;
	}
	
    @Override
    protected void onPostExecute(Document doc) {      	
		//Element root = doc.getDocumentElement();
		//root.normalize();
		Element elemento;
		String currency, rate;
		NodeList listaCube = doc.getElementsByTagName("Cube");
		for(int i=2; i<listaCube.getLength(); i++) {
			elemento = (Element) listaCube.item(i);
			currency = elemento.getAttribute("currency");
			rate = elemento.getAttribute("rate");
			Log.d("TareaAsincronaBCE", currency + "->" + rate);
		}
		pDialog.dismiss();
    }
    
	@Override
	protected void onCancelled() {
		Toast.makeText(contexto, mensajeError, Toast.LENGTH_SHORT).show();
		super.onCancelled();
	}

}

