package com.vladymix.currencyexchange;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Fabricio on 01/06/2015.
 */                                          //Parametros progresos resultados
    public class TareaAsincrona extends AsyncTask<Void,Void,Document> {

    private ProgressDialog progressDialog;
    private ResultadosURL listener;

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
    public TareaAsincrona(ResultadosURL listener){
        this.listener=listener;
    }

    //antes de la ejecucion
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    //en ejecucion


    @Override
    protected Document doInBackground(Void... params) {
    try {
        URL url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
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

    //Despues de la ejecucion
    @Override
    protected void onPostExecute(Document aDoc) {
        progressDialog.dismiss();
        listener.onTaskCompleted(aDoc);
       super.onPostExecute(aDoc);
    }
}
