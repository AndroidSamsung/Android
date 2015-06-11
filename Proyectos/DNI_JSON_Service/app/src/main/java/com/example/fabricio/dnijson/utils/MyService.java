package com.example.fabricio.dnijson.utils;

import android.app.IntentService;
import android.content.Intent;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;


/**
 * Created by Fabricio on 08/06/2015.
 */
//NO va a estar el el hilo principal
public class MyService extends IntentService {
    public static final String ACTION_FIN = "com.example.fabricio.dnijson.action.FIN";
    public static final String ACTION_ERROR = "com.example.fabricio.dnijson.action.ERROR";
    public static final String ACTION_NO_RECORD = "com.example.fabricio.dnijson.NO_RECORD";

    private final int NO_RECORD = 0;
    private final int ERROR = -1;

    private String myAction;


    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String dni = intent.getStringExtra("DNI");
        String url_final="http://demo.calamar.eui.upm.es/dasmapi/v1/miw14/fichas";

        if (!dni.equals("")) {
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
