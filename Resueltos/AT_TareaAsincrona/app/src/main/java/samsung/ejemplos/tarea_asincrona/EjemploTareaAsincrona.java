package samsung.ejemplos.tarea_asincrona;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EjemploTareaAsincrona extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_asincrona);
    }
    
    public void lanzarTarea(View v){
		new TareaAsincrona(this).execute(100,100);
    }
    
    private class TareaAsincrona extends AsyncTask<Integer,Integer,String>{

    	private ProgressDialog pDialog;
    	private Context contexto;
    	
    	public TareaAsincrona(Context contexto){
    		this.contexto = contexto;
    	}
    	
		@Override
		protected void onPreExecute() {
            super.onPreExecute();
			pDialog = new ProgressDialog(contexto);
			pDialog.setMessage("Trabajando en background");
			pDialog.setIndeterminate(false);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setMax(100);
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                }
            });
			pDialog.show();
		}

		@Override
		protected String doInBackground(Integer... args) {
            int i = 0;
            while (i <= args[0]) {
            	if(isCancelled()) break;
                try {
                    Thread.sleep(args[1]);
                    publishProgress(i/(args[0]/100));
                    i++;
                } catch (Exception e) {}
            }
            return "Proceso en background completado";
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			pDialog.setProgress(values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled() {
        	Toast.makeText(contexto, "Proceso cancelado", Toast.LENGTH_SHORT).show();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			pDialog.dismiss();
			Toast.makeText(contexto, result, Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}   	
    }

}
