package com.vladymix.navegacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class second extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView v = (TextView)findViewById(R.id.txbrecived);

        Intent i = getIntent();
       v.setText(i.getStringExtra("VALOR"));

    }

    public void gotoactivity1(View v ){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("VALOR2", ((TextView)findViewById(R.id.textalgo)).getText().toString());

        startActivity(i);

    }

}
