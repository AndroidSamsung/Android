package ejemplos.samsung.bce_desdecero.utiles;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import ejemplos.samsung.bce_desdecero.R;

public class TareaBCE extends AsyncTask<Void, Void, ArrayList<DatosBCE>>{

    private final String URL_BCE = "http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml";

    private Context contexto;
    private ProgressDialog pDialog;
    private URL url;

    public TareaBCE(Context contexto){
        this.contexto = contexto;
        try {
            url = new URL(URL_BCE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            cancel(true);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(contexto);
        pDialog.setMessage(contexto.getString(R.string.conectando_BCE));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected ArrayList<DatosBCE> doInBackground(Void... params) {
        ArrayList<DatosBCE> listaDatos = new ArrayList<DatosBCE>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource xml = new InputSource(url.openStream());
            Document doc = db.parse(xml);
            NodeList listaNodos = doc.getElementsByTagName("Cube");
            Element elemento;
            String moneda;
            for(int i=2; i<listaNodos.getLength(); i++){
                elemento = (Element) listaNodos.item(i);
                moneda = elemento.getAttribute("currency");
                double ratio = Double.valueOf(elemento.getAttribute("rate"));
                DatosBCE datos = new DatosBCE(moneda, ratio);
                listaDatos.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            cancel(true);
        }
        return listaDatos;
    }

    @Override
    protected void onPostExecute(ArrayList<DatosBCE> lista) {
        ((InterfazBCE)contexto).recuperarRatios(lista);
        pDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        pDialog.dismiss();
        Toast.makeText(contexto, contexto.getString(R.string.error), Toast.LENGTH_SHORT).show();
        super.onCancelled();
    }
}
