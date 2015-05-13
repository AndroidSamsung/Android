package com.example.android.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.security.acl.Group;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getApplicationContext();
                if(isChecked)
                Toast.makeText(context, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
            }
        });

        ((RadioGroup)findViewById(R.id.gruporadios)).setOnCheckedChangeListener(new eventoGrupo());
        ((Button)findViewById(R.id.button)).setOnClickListener(new eventoBotton());
        ((ImageButton)findViewById(R.id.imageButton)).setOnClickListener(new eventoBotton());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class eventoBotton implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Context context = getApplicationContext();
            if(v.getId() == R.id.button){
                Toast.makeText(context,"Boton con imagen", Toast.LENGTH_SHORT).show();
            }
            else if(v.getId()== R.id.imageButton){
             ImageButton on_off =   (ImageButton)findViewById(R.id.imageButton);
                int on = Color.RED;
                int off = Color.BLACK;

                if(on_off.getSolidColor()==on)
                    on_off.setBackgroundColor(off);
                else
                    on_off.setBackgroundColor(on);


            }
        }
    }



    public class eventoGrupo implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Context context = getApplicationContext();
            CharSequence text = "Evento";
            int duracion = Toast.LENGTH_SHORT;

            if(checkedId ==R.id.radioButton){

                Toast.makeText(context, "Presionó radiobutton 1" , Toast.LENGTH_SHORT).show();

            }
            else if(checkedId ==R.id.radioButton2){

                Toast.makeText(context, "Presionó radiobutton 2" , Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(context, "Presionó radiobutton 3" , Toast.LENGTH_SHORT).show();
            }
        }
    }


}
