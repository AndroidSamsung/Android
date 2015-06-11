package com.example.fabricio.dnijson.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.fabricio.dnijson.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Fabricio on 05/06/2015.
 */

                                //Parametros progresos resultados
public class ProcessAsync extends AsyncTask<String,Void,String> {
    private final String URL_API = "http://demo.calamar.eui.upm.es/dasmapi/v1/miw14/fichas";
    ProgressDialog pDialog;
    Context contexto;
    public ProcessAsync(Context context) {
        this.contexto = context;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(contexto);
        pDialog.setMessage(contexto.getString(R.string.title_activity_view_data));
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
    protected String doInBackground(String... params) {
        String informacion = "[{\"NUMREG\":-1}]";
        String method = params[0];
        URL url;



        HttpURLConnection urlConnection = null;
        switch (params[0]){
            case "POST":
                try {
                    url = new URL(URL_API);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setRequestMethod(method);

                    //Write
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(params[1]);
                    writer.close();
                    os.close();

                    urlConnection.connect();
                    if(urlConnection.getResponseCode()==200){
                        informacion = readStream(urlConnection.getInputStream());
                    }
                }
                catch (Exception e) {
                    e.getMessage();
                }
                finally {
                    urlConnection.disconnect();
                }

             break;
            case "GET":
                try {
                    String url_final = URL_API;
                    String dni = params[1];
                    if (!dni.equals("")) {
                        url_final += "/" + dni;
                    }
                    url = new URL(url_final);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod(method);
                    urlConnection.connect();
                    if(urlConnection.getResponseCode()==200){
                        informacion = readStream(urlConnection.getInputStream());
                    }
                }
                catch (Exception ex){

                }
                finally {
                    urlConnection.disconnect();
                }

                break;
            case "DELETE":
                try {
                    String url_final = URL_API;
                    String dni = params[1];
                    if (!dni.equals("")) {
                        url_final += "/" + dni;
                    }
                    else{
                        return informacion;
                    }

                    url = new URL(url_final);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Accept", "application/json");
                    urlConnection.setRequestMethod(method);

                    //Write
                    /*
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(params[1]);
                    writer.close();
                    os.close();
                    */
                    urlConnection.connect();
                    if(urlConnection.getResponseCode()==200){
                        informacion = readStream(urlConnection.getInputStream());
                    }
                }
                catch (Exception e) {
                    e.getMessage();
                }
                finally {
                    urlConnection.disconnect();
                }
                break;
            case "PUT":
              try{
                url = new URL(URL_API);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod(method);
                //Write
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(params[1]);
                    writer.close();
                    os.close();

                urlConnection.connect();
                if(urlConnection.getResponseCode()==200){
                    informacion = readStream(urlConnection.getInputStream());
                }
            }
            catch (Exception e) {
                e.getMessage();
            }
            finally {
                urlConnection.disconnect();
            }
                break;

        }

        return informacion;
    }

    @Override
    protected void onPostExecute(String results) {
        ((ResultadosJSON)contexto).CallbackJSON(results);
         pDialog.dismiss();
        super.onPostExecute(results);
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
