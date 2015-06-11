package ejemplos.samsung.api_rest_basico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

public class TareaServicio extends AsyncTask<String, Void, String> {

    private final String URL_API = "http://demo.calamar.eui.upm.es/dasmapi/v1/demo/fichas";
    private ProgressDialog pDialog;
    private Context contexto;

    public TareaServicio(Context contexto){
        this.contexto = contexto;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(contexto);
        pDialog.setMessage(contexto.getString(R.string.realizarPeticion));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
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
        String url_final = URL_API;
        if (!dni.equals("")) {
            url_final += "/" + dni;
        }

//        //AndroidHttpClient cliente = AndroidHttpClient.newInstance("AndroidHttpClient");
//        HttpClient cliente = new DefaultHttpClient();
//        HttpGet peticionGet = new HttpGet(url_final);
//        try {
//            HttpResponse respuesta = cliente.execute(peticionGet);
//            if(respuesta.getStatusLine().getStatusCode()==200){
//                HttpEntity cuerpoMensaje = respuesta.getEntity();
//                informacion = EntityUtils.toString(cuerpoMensaje);
//            }
//        } catch (IOException e) {
//        } finally {
//            //cliente.close();
//        }

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(url_final);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                informacion = readStream(urlConnection.getInputStream());
            }
        } catch (IOException e) {
        } finally {
            urlConnection.disconnect();
        }
        return informacion;
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(contexto, "Proceso cancelado", Toast.LENGTH_SHORT).show();
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(String resultado) {
        pDialog.dismiss();
        ((CallbackResultados)contexto).obtenerResultados(resultado);
        super.onPostExecute(resultado);
    }

    private String readStream(InputStream in) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        return sb.toString();
    }
}
