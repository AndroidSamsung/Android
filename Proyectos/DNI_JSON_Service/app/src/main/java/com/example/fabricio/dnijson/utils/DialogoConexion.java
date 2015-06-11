package com.example.fabricio.dnijson.utils;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.example.fabricio.dnijson.MainActivity;
import com.example.fabricio.dnijson.R;

/**
 * Created by Fabricio on 08/06/2015.
 */
public class DialogoConexion {
    private static ProgressDialog pDialog;
    static {
        pDialog = new ProgressDialog(MainActivity.contexto);
        pDialog.setMessage("Ralizando peticion al api");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    public static void verDialogo(){
        pDialog.show();
    }
    public static void cerrarDialogo(){
        pDialog.dismiss();
    }

}
