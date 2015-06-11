package ejemplos.samsung.api_rest_basico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements CallbackResultados{

    private EditText dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dni = (EditText)findViewById(R.id.dniConsulta);
    }

    public void realizarPeticion(View v){
        new TareaServicio(this).execute(dni.getText().toString());
    }

    @Override
    public void obtenerResultados(String resultado) {
        //Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        try {
            JSONArray respuesta = new JSONArray(resultado);
            JSONObject numRegistrosObj = respuesta.getJSONObject(0);
            int numRegistros = numRegistrosObj.getInt("NUMREG");
            String mensaje ="";
            switch(numRegistros){
                case -1: mensaje = getString(R.string.errorAPI);
                         break;
                case  0: mensaje = getString(R.string.sinRegistros);
                         break;
                default: mensaje = getString(R.string.conRegistros, numRegistros);
                        Intent i = new Intent(this, ViewDataActivity.class);
                        i.putExtra("data", resultado);
                        startActivity(i);
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(this, R.string.errorData, Toast.LENGTH_LONG).show();
        }
    }
}
