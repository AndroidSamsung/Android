package samsung.ejemplos.tarea_asincrona;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TareaAsincronaBCE extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_asincrona);
    }
    
    public void lanzarTarea(View v){
    	new ParseXMLTask(this).execute();
        //new AccesoBCE(this).execute();
    }
   
}
