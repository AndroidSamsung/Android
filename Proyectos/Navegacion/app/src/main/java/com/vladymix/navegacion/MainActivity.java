package com.vladymix.navegacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView v = (TextView)findViewById(R.id.textView2);

        Intent i = getIntent();
        if(i!=null)
        v.setText(i.getStringExtra("VALOR2"));
    }
public void gotoactivity2(View v ){
        Intent i = new Intent(this, second.class);

    i.putExtra("VALOR", ((TextView)findViewById(R.id.txbname)).getText().toString());

    startActivity(i);

}


}
