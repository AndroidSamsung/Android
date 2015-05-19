package com.vladymix.dnivalidation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        Initialized();

    }

    public void Initialized(){

        TextView v = (TextView)findViewById(R.id.dniResult);
try {
    String dni = getIntent().getStringExtra(FINALVALUES.VALORDNI);

    dni = getLeter(Integer.parseInt(dni));

    v.setText(dni);
}
catch (Exception ex){
    Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
}

    }

    public String getLeter(int numero){
        String[] letras =  {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
        return Integer.toString(numero)+letras[numero%23];
    }

    @Override
    public void onBackPressed(){
        saveValues();
        setResult(FINALVALUES.OK_RESULT_CODE);
        super.onBackPressed();

    }

    public void go_to_comeback(View v )  {

        saveValues();
        finish();
        //startActivityForResult(i, FINALVALUES.IDACTIVIDAD2 );

    }

    public void saveValues(){

        Intent i = new Intent();

        i.putExtra(FINALVALUES.VALORDNI, ((TextView)findViewById(R.id.dniResult)).getText().toString());

        setResult(FINALVALUES.OK_RESULT_CODE,i); //Envio el valor id del resultado y el intent
    }


}
