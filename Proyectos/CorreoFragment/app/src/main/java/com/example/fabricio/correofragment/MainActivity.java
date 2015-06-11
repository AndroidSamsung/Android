package com.example.fabricio.correofragment;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements  SeleccionCorreo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VerFragmentosDimanic();
    }
    public void VerFragmentosDimanic(){
        int orientacion = getResources().getConfiguration().orientation;

        if (orientacion== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getFragmentManager().beginTransaction()
                    .add(R.id.mycontent, new FragmentCorreos(), null)
                    .commit();
        }
    }

    @Override
    public void CorreoSeleccionado(Correo correo) {
            getFragmentManager().beginTransaction()
                    .add(R.id.mycontent, new FragmentPreview(), null)
                    .commit();

    }
}
