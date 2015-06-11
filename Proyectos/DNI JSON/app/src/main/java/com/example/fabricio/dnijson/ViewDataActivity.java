package com.example.fabricio.dnijson;

import android.app.Activity;
import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabricio.dnijson.utils.ProcessAsync;
import com.example.fabricio.dnijson.utils.ResultadosJSON;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewDataActivity extends Activity implements ResultadosJSON{

    private int registroActual, numRegistros;
    private JSONArray datos;

    private String metodoJSON;
    private TextView title_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        title_activity = (TextView)findViewById(R.id.title_type_activity);

        String response=getIntent().getStringExtra("data");

        try {
            datos = new JSONArray(response);

            numRegistros = datos.getJSONObject(0).getInt("NUMREG");
            if(numRegistros==1){
                title_activity.setText("Registro");
                findViewById(R.id.btn_save).setVisibility(View.INVISIBLE);
            }
            else if(numRegistros>1){
                title_activity.setText("Lista de registros");
                findViewById(R.id.btn_save).setVisibility(View.INVISIBLE);
            }
            else if(numRegistros==0){
                findViewById(R.id.btn_delete).setVisibility(View.INVISIBLE);
                findViewById(R.id.btn_edit).setVisibility(View.INVISIBLE);
            }

            registroActual = 1;
            verRegistro();

        } catch (JSONException e) {
           // procesarError();
            numRegistros=0;
            limpiarDatos();
            ( (EditText)findViewById(R.id.reg_dni)).setText(response);

            findViewById(R.id.btn_delete).setVisibility(View.INVISIBLE);
            findViewById(R.id.btn_edit).setVisibility(View.INVISIBLE);

        }

    }

    private void limpiarDatos(){

        if(numRegistros==0) {
            title_activity.setText("Agregar nuevo registro");
            ((TextView) findViewById(R.id.reg_numreg)).setText("Registro nuevo");
        }

        ((EditText) findViewById(R.id.reg_dni)).setText("");
        ((EditText) findViewById(R.id.reg_nombre)).setText("");
        ((EditText) findViewById(R.id.reg_apellido)).setText("");
        ((EditText) findViewById(R.id.reg_direccion)).setText("");
        ((EditText) findViewById(R.id.reg_telefono)).setText("");
        ((EditText) findViewById(R.id.reg_equipo)).setText("");


    }

    private void verRegistro(){
        try{
            JSONObject objetoActual = datos.getJSONObject(registroActual);
            ((TextView) findViewById(R.id.reg_numreg)).setText("registro "+registroActual+" de "+numRegistros);

            ((EditText) findViewById(R.id.reg_dni)).setText(objetoActual.getString("DNI"));
            ((EditText) findViewById(R.id.reg_nombre)).setText(objetoActual.getString("Nombre"));
            ((EditText) findViewById(R.id.reg_apellido)).setText(objetoActual.getString("Apellidos"));
            ((EditText) findViewById(R.id.reg_direccion)).setText(objetoActual.getString("Direccion"));
            ((EditText) findViewById(R.id.reg_telefono)).setText(objetoActual.getString("Telefono"));
            ((EditText) findViewById(R.id.reg_equipo)).setText(objetoActual.getString("Equipo"));

        } catch (JSONException e){
           // procesarError();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_data, menu);
        return true;
    }

    public void PUSH_RegisterJSON(View v){
        metodoJSON="POST";

        if( ((EditText) findViewById(R.id.reg_dni)).getText().toString().equals(""))
        {
            Toast.makeText(this,"El DNI no tiene que estar en blanco", Toast.LENGTH_LONG).show();
            return;
        }
        String[] values = new String [6];
        values[0]=  ((EditText) findViewById(R.id.reg_dni)).getText().toString();
        values[1]= ((EditText) findViewById(R.id.reg_nombre)).getText().toString();
        values[2]= ((EditText) findViewById(R.id.reg_apellido)).getText().toString();
        values[3]= ((EditText) findViewById(R.id.reg_direccion)).getText().toString();
        values[4]= ((EditText) findViewById(R.id.reg_telefono)).getText().toString();
        values[5]=  ((EditText) findViewById(R.id.reg_equipo)).getText().toString();
        new ProcessAsync(this).execute(metodoJSON, getJSONString(values));
   }

    public void PUT_ItemJSON(View v){
        metodoJSON="PUT";
        String[] values = new String [6];
        values[0]=  ((EditText) findViewById(R.id.reg_dni)).getText().toString();
        values[1]= ((EditText) findViewById(R.id.reg_nombre)).getText().toString();
        values[2]= ((EditText) findViewById(R.id.reg_apellido)).getText().toString();
        values[3]= ((EditText) findViewById(R.id.reg_direccion)).getText().toString();
        values[4]= ((EditText) findViewById(R.id.reg_telefono)).getText().toString();
        values[5]=  ((EditText) findViewById(R.id.reg_equipo)).getText().toString();
        new ProcessAsync(this).execute(metodoJSON, getJSONString(values));

    }
    public void DELETE_ItemJSON(View v){
        metodoJSON="DELETE";
        new ProcessAsync(this).execute(metodoJSON, ((EditText) findViewById(R.id.reg_dni)).getText().toString());

    }

    private String getJSONString(String[] string){
        return "{\"DNI\":\""+string[0]+"\",\"Nombre\":\""+string[1]+"\",\"Apellidos\":\""+string[2]+"\",\"Direccion\":\""+string[3]+"\",\"Telefono\":\""+string[4]+"\",\"Equipo\":\""+string[5]+"\"}";
    }

    @Override
    public void CallbackJSON(String data) {
        try {
            JSONArray respuesta = new JSONArray(data);
            JSONObject numRegistrosObj = respuesta.getJSONObject(0);
            int numReg = numRegistrosObj.getInt("NUMREG");
            String mensaje="";

            switch(numReg){
                case -1: mensaje = getString(R.string.errorAPI);
                    break;
                case  0: mensaje = getString(R.string.sinRegistros);
                    break;

                //Operacion exitosa devuelve 1
                default:
                    switch (metodoJSON){
                        case "PUT":


                            JSONObject objetoActual = new JSONObject();
                            objetoActual.put("DNI", ((EditText) findViewById(R.id.reg_dni)).getText().toString());
                            objetoActual.put("Nombre", ((EditText) findViewById(R.id.reg_nombre)).getText().toString());
                            objetoActual.put("Apellidos", ((EditText) findViewById(R.id.reg_apellido)).getText().toString());
                            objetoActual.put("Direccion", ((EditText) findViewById(R.id.reg_direccion)).getText().toString());
                            objetoActual.put("Telefono", ((EditText) findViewById(R.id.reg_telefono)).getText().toString());
                            objetoActual.put("Equipo", ((EditText) findViewById(R.id.reg_equipo)).getText().toString());

                            datos.put(registroActual, objetoActual);
                            verRegistro();
                            mensaje = getString(R.string.msn_edited);
                            break;
                        case "DELETE":
                            limpiarDatos();
                            datos.remove(registroActual);
                            numRegistros--;
                            verRegistro();
                            mensaje = getString(R.string.msn_deleted);
                            break;
                        case "POST":
                            mensaje = getString(R.string.msn_saved);
                            limpiarDatos();
                            break;
                    }
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
        catch (JSONException e){}


    }

    public void irAnterior(MenuItem v){
        if(registroActual>1)
            registroActual--;
        verRegistro();
    }

    public void irSiguiente(MenuItem v){
        if(registroActual<numRegistros)
            registroActual++;
        verRegistro();
    }


}
