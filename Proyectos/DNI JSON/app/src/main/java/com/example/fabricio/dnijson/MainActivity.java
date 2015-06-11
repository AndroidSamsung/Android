package com.example.fabricio.dnijson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fabricio.dnijson.utils.ProcessAsync;
import com.example.fabricio.dnijson.utils.ResultadosJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements ResultadosJSON {

    EditText dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dni = (EditText)findViewById(R.id.input_dni);
    }

    public void realizarPeticion(View v){
        new ProcessAsync(this).execute("GET",dni.getText().toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void CallbackJSON(String data) {
        try {
            JSONArray respuesta = new JSONArray(data);
            JSONObject numRegistrosObj = respuesta.getJSONObject(0);
            int numRegistros = numRegistrosObj.getInt("NUMREG");
            Intent i;

            String mensaje ="";

            switch(numRegistros){
                case -1: mensaje = getString(R.string.errorAPI);
                    break;
                case  0: mensaje = getString(R.string.sinRegistros);
                    i = new Intent(this, ViewDataActivity.class);
                    i.putExtra("data", dni.getText().toString());
                    startActivity(i);

                    break;
                default: mensaje = getString(R.string.conRegistros, numRegistros);
                     i = new Intent(this, ViewDataActivity.class);
                    i.putExtra("data", data);
                  startActivity(i);
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(this, R.string.errorData, Toast.LENGTH_LONG).show();
        }
    }
}
