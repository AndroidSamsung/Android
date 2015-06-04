package com.vladymix.emthorarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class FavoritesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    public void navigate_to_lines(MenuItem menuItem){
        Intent i = new Intent(this, LineasActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIDAD_LINEAS);
    }
    public void navigate_to_stops(MenuItem menuItem){
        Intent i = new Intent(this, ParadasActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIDAD_PARADAS);

    }
}
