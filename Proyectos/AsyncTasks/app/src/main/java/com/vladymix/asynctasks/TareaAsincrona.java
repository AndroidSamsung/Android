package com.vladymix.asynctasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Fabricio on 01/06/2015.
 */

                                   //Parametros progresos resultados
public class TareaAsincrona extends AsyncTask<Integer,Void,String> {

    private ProgressDialog progressDialog;

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }


    //antes de la ejecucion
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    //en ejecucion
    @Override
    protected String doInBackground(Integer... params) {
        try{ Thread.sleep(params[0]);}catch (Exception ex){}
        return "In backgroud OK";
    }

    //Despues de la ejecucion
    @Override
    protected void onPostExecute(String aString) {
        progressDialog.dismiss();
        super.onPostExecute(aString);
    }
}
