package com.vladymix.android.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vladymix.com.calculadora.R;

import Clases.MiCalculadora;


public class MainActivity extends Activity {
    MiCalculadora miCalculadora = new MiCalculadora();

    TextView  tfDisplay;
    TextView  resultdisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tfDisplay = (TextView)findViewById(R.id.textView);
        resultdisplay = (TextView)findViewById(R.id.textView2);
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

    public void click_number (View v){

        String nt = ((Button)v).getText().toString();
        if (nt.equals("c")) {
            tfDisplay.setText("");
        }
        else {
            if(miCalculadora.isTypedcomplete()) {
                if(miCalculadora.getTypeoperation().equals("="))
                {
                    resultdisplay.setText("");
                    tfDisplay.setText(nt);
                }
                else {
                    tfDisplay.setText(nt);
                    miCalculadora.setTypedcomplete(false);
                }
            }
            else if(!miCalculadora.isTypedcomplete())
            {
                if(nt.equals("."))
                {
                    if(!tfDisplay.getText().toString().contains("."))
                        tfDisplay.setText(tfDisplay.getText() + nt);
                }
                else
                    tfDisplay.setText(tfDisplay.getText() + nt);

            }

        }

    }

    public void click_operations (View v){

        operaciones(((Button)v).getText().toString());

    }

    private void operaciones (String op){
        try {

            if(tfDisplay.getText().equals(""))
                return;

            //Grafico los datos
            if(!miCalculadora.isTypedcomplete())
                resultdisplay.setText(resultdisplay.getText().toString() +tfDisplay.getText().toString()+ op);

            else if(miCalculadora.getTypeoperation().equals("=")){
                resultdisplay.setText(tfDisplay.getText()+op);
            }
            else{
                resultdisplay.setText(resultdisplay.getText()+op);
            }

            //Almaceno los numeros
            if (miCalculadora.getNumero1() == 0.0)
                miCalculadora.setNumero1(Double.parseDouble(tfDisplay.getText().toString()));
            else
                miCalculadora.setNumero2(Double.parseDouble(tfDisplay.getText().toString()));

            miCalculadora.makeOperation();
            miCalculadora.setTypeoperation(op);

            tfDisplay.setText(String.valueOf(miCalculadora.getResult()));

            //  resultdisplay.setText(resultdisplay.getText() + op);
        }
        catch (Exception ex){}

    }








}
