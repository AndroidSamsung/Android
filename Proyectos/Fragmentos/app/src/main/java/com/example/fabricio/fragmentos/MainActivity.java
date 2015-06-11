package com.example.fabricio.fragmentos;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VerFragmentosDimanic();

       if( getRequestedOrientation()== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){

       }
    }

    public void  verFramentdos(View v){
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentoDOS())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null) //fragmento alcual remplazado
                .commit();

    }
    public void verTextoFragmentouno(View v){
        Toast.makeText(this, ((TextView) findViewById(R.id.idTextFragUno)).getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void VerFragmentosDimanic(){
        getFragmentManager().beginTransaction()
                .add(R.id.idFramentLayoutMain ,new FragmentoUNO(),null)
                .commit();
    }

}
