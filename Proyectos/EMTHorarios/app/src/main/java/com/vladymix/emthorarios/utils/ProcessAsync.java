package com.vladymix.emthorarios.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.vladymix.emthorarios.Direccion;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Fabricio on 05/06/2015.
 */
public class ProcessAsync extends AsyncTask<String,Void,Document> {

    ProgressDialog progressDialog;
    ResultadosURL listener;
    Context context;

    public ProcessAsync(Context context, String mensaje, ResultadosURL listener) {

        this.context = context;
        this.listener = listener;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(mensaje);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);//Forzamos aque se cancele la tarea asincrono
            }
        });
        progressDialog.show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Document doInBackground(String... params) {

        try {
            URL url  = new URL("http://www.galapanet.com");
            switch (params[0]){
                case "RUTELINE":
                    url = new URL(Direccion. getStopsLine(params[1], params[2]));
                    break;

                case "INFOSTOP":
                    url = new URL(Direccion.getStopsFromStop(params[1], "0"));
                    break;
                case "ARRIVEDSSTOP":
                    url = new URL(Direccion.getArriveStop(params[1]));
                    break;
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            return doc;
        }
        catch (Exception ex){
            Log.d("[Conexion]", ex.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Document document) {
        listener.CallbackURL(document);
        progressDialog.dismiss();
        super.onPostExecute(document);
    }

}
