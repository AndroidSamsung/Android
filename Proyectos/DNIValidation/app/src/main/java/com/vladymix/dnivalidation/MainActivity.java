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


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
   public void Inicilized(Intent data){

       TextView v = (TextView)findViewById(R.id.dniValidated);
       v.setText(data.getStringExtra(FINALVALUES.VALORDNI));

   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FINALVALUES.IDACTIVIDAD2) {

            //If tod ha ido bien
             if(requestCode ==FINALVALUES.OK_RESULT_CODE)
                  Inicilized(data);
            Toast.makeText(this, "LLEGO DESDE LA SEGUNDA ACTIVIAD", Toast.LENGTH_LONG).show();
        }
    }

    public void btn_go_to_activity2(View v){

        Intent i = new Intent(this, MainActivity2.class);

        i.putExtra(FINALVALUES.VALORDNI, ((EditText)findViewById(R.id.dniValidation)).getText().toString());

        startActivityForResult(i, FINALVALUES.IDACTIVIDAD2);

    }

}
