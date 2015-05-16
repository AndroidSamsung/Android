package com.vladymix.com.calculadora;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    Vibrator vib;
    int duration_vibrate = 20;
    private MiCalculadora miCalculadora;
    private TextView tfDisplay;
    private TextView resultdisplay;
    Double men;
    Boolean typed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.activity_main);

        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.barratitulo);


        miCalculadora = new MiCalculadora();
        miCalculadora.setTypedcomplete(true);
        tfDisplay = (TextView)findViewById(R.id.tfdisplay);
        resultdisplay = (TextView)findViewById(R.id.resultdisplay);

        vib = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
    }

    public void eventoButtonNumero (View v){
        vib.vibrate(duration_vibrate);
        textview_numeros(((Button) v).getText().toString());
    }

    public void eventoButtonOperaciones (View v){
        vib.vibrate(duration_vibrate);
        textview_operaciones(((Button) v).getText().toString());
    }
    public void eventoButtonOperacionesespeciales (View v){
        vib.vibrate(duration_vibrate);
        textview_operacionesEspeciales(((Button) v).getText().toString());
    }

    private  void textview_numeros(String nt) {

            if (miCalculadora.isTypedcomplete()) {
                if (miCalculadora.getTypeoperation().equals("=")) {
                    resultdisplay.setText("");
                    tfDisplay.setText(nt);
                }
                else {
                    tfDisplay.setText(nt);
                    miCalculadora.setTypedcomplete(false);
                }
            }
            else if (!miCalculadora.isTypedcomplete()) {
                if (nt.equals(".")) {
                    if (!tfDisplay.getText().toString().contains("."))
                        tfDisplay.setText(tfDisplay.getText() + nt);
                } else
                    tfDisplay.setText(tfDisplay.getText() + nt);

            }


    }

    private void textview_operaciones (String op){
        try {
            if (op.equals("c")) {
                tfDisplay.setText("");
                return;
            }

            if(tfDisplay.getText().equals(""))
                return;

            //Grafico los datos
            if(miCalculadora.getTypeoperation().equals("=")){
                resultdisplay.setText(tfDisplay.getText()+op);
            }
           else if(!miCalculadora.isTypedcomplete())
                resultdisplay.setText(resultdisplay.getText().toString() +tfDisplay.getText().toString()+ op);

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

    private void textview_operacionesEspeciales(String op){
         int enabled = Color.rgb(255, 0, 125);
         int  disabled = Color.rgb(210, 0, 0);

        switch (op) {
            case "+/-":
                if (!tfDisplay.getText().equals(""))
                {
                    Double numaux = Double.valueOf(tfDisplay.getText().toString());
                    numaux = (-1) * numaux;
                    tfDisplay.setText(String.valueOf(numaux));
                 }

            break;

            case "MC":

                break;

            case "MR":
                men = Double.parseDouble(tfDisplay.getText().toString());
                typed = true;
                break;
            case "M+":
                tfDisplay.setText(String.valueOf(men));
                break;

            case "M-":
                break;

            case "C":
                tfDisplay.setText("0");
                resultdisplay.setText("");
                miCalculadora.clearAllDates();
                miCalculadora.setTypedcomplete(true);
                break;

           /*      if (op == bRaiz) {
                if (!tfDisplay.getText().equals(""))
                    resultdisplay.setText(resultdisplay.getText() + "?" + tfDisplay.getText());
                Double numero;
                numero = Math.sqrt(Double.valueOf(tfDisplay.getText()));
                miCalculadora.setResult(numero);
                tfDisplay.setText(String.valueOf(miCalculadora.getResult()));
                miCalculadora.setTypedcomplete(true);
            }
            else if (op == bcuadrados) {
                if (!tfDisplay.getText().equals("")) {
                    float cuad = Float.valueOf(tfDisplay.getText());
                    char a;
                    a = 178;
                    resultdisplay.setText(resultdisplay.getText() + tfDisplay.getText() + String.valueOf(a));
                    cuad = cuad * cuad;
                    tfDisplay.setText(String.valueOf(cuad));
                    typed = true;
                }*/

            }
        }


}
