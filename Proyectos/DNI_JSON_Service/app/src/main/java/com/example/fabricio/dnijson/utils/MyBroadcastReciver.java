package com.example.fabricio.dnijson.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.fabricio.dnijson.R;
import com.example.fabricio.dnijson.ViewDataActivity;

/**
 * Created by Fabricio on 08/06/2015.
 */
public class MyBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(MyService.ACTION_FIN)) {
            Intent i = new Intent(context, ViewDataActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("data", intent.getStringExtra("textoJSON"));
            context.startActivity(i);
        }
        if(intent.getAction().equals(MyService.ACTION_ERROR)) {
            Toast.makeText(context, context.getString(R.string.errorAPI), Toast.LENGTH_SHORT).show();
        }
        if(intent.getAction().equals(MyService.ACTION_NO_RECORD)) {
            Toast.makeText(context, context.getString(R.string.sinRegistros), Toast.LENGTH_SHORT).show();
        }

         DialogoConexion.cerrarDialogo();
    }
}
