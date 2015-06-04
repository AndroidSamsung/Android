package com.vladymix.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void lanzar_asyntask(View v){
      //  new MyTareaAsincrona().execute();

     /*   TareaAsincrona asincrona = new TareaAsincrona();

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Trabajando en background en clase con parametro");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        asincrona.setProgressDialog(progressDialog);
        asincrona.execute(3000);*/

        // new MyTareaAsincrona().execute();
       //  new MyTareaAsincronaRetun().execute(1000);
        new MyTareaAsincronaRetunDeterminated().execute(1000,5);
    }

    private class MyTareaAsincrona extends AsyncTask<Void,Void,Void> {

        private ProgressDialog progressDialog;

        //antes de la ejecucion
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Trabajando en background");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);

            progressDialog.show();
        }

        //en ejecucion
        @Override
        protected Void doInBackground(Void... params) {
            for(int i =0; i<5;i++){
               try{ Thread.sleep(1000);}catch (Exception ex){}
            }
            return null;
        }

        //Despues de la ejecucion
        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }

    private class MyTareaAsincronaRetun extends AsyncTask<Integer,Void,String> {

        private ProgressDialog progressDialog;

        //antes de la ejecucion
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Trabajando en background cancele");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);//Forzamos aque se cancele la tarea asincrono
                }
            });
            progressDialog.show();
        }

        //en ejecucion
        @Override
        protected String doInBackground(Integer... params) {
            for(int i =0; i<5;i++) {
                if(isCancelled())break;

                try {
                    Thread.sleep(params[0]);
                } catch (Exception ex) {
                }
            }
            return "Todo ok";
        }

        //Despues de la ejecucion
        @Override
        protected void onPostExecute(String aString) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "do in bacgroun "+aString, Toast.LENGTH_SHORT).show();
            super.onPostExecute(aString);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "On Cancela execute",Toast.LENGTH_SHORT).show();
                super.onCancelled();
        }
    }


    private class MyTareaAsincronaRetunDeterminated extends AsyncTask<Integer,Integer,String> {

        private ProgressDialog progressDialog;

        //antes de la ejecucion
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Trabajando en background cancele");

            progressDialog.setIndeterminate(false);

            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            progressDialog.setCancelable(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);//Forzamos aque se cancele la tarea asincrono
                }
            });
            progressDialog.show();
        }

        //en ejecucion
        @Override
        protected String doInBackground(Integer... params) {
            for(int i =0; i<params[1];i++) {
                if(isCancelled())break;

                try {
                    Thread.sleep(params[0]);
                    publishProgress((i+1)*20);
                } catch (Exception ex) {
                }
            }
            return "Todo ok";
        }

        //Despues de la ejecucion
        @Override
        protected void onPostExecute(String aString) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "do in bacgroun "+aString, Toast.LENGTH_SHORT).show();
            super.onPostExecute(aString);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "On Cancela execute",Toast.LENGTH_SHORT).show();
            super.onCancelled();
        }
    }
}

