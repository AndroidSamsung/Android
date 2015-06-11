package com.vladymix.currencyexchange.utils;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.vladymix.currencyexchange.ExchangeActivity;
import com.vladymix.currencyexchange.FINALVALUE;
import com.vladymix.currencyexchange.R;
import com.vladymix.currencyexchange.SetPreferencesActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Fabricio on 08/06/2015.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(MyService.ACTION_FIN)) {

            BDD bdd = BDD.getInstance();
            bdd.setInstance(context);
            ArrayList<Pais> paises = intent.getParcelableArrayListExtra("LISTA");
          Boolean typeUpdate = false;
           if(paises.size()>0)
               typeUpdate = true;

            for(Pais item :paises){
                ContentValues values = new ContentValues();
                values.put("moneda", item.getCurrency());
                values.put("pais", item.getNombre());
                values.put("nombremoneda", item.getNameCurrency());
                values.put("ratio", item.getValueCurrency());
                values.put("bandera", item.getIdflag());
                values.put("banderacircle", item.getIdcircle());
                if(typeUpdate){
                Boolean resy=    bdd.updateRegister(values);

                }
                else {
                    bdd.insertCurrency(values);
                }
            }
            Toast.makeText(context, "Update "+bdd.getCursor().getCount()+" currencys" , Toast.LENGTH_SHORT).show();
            bdd.dismiss();

            //save preferences las update
            SharedPreferences prefs =context.getSharedPreferences(FINALVALUE.NAME_FILE_SETTINGS, context.MODE_PRIVATE);
            prefs.edit().putInt(SetPreferencesActivity.LAST_UPDATE, hoy())
                    .putString(SetPreferencesActivity.DATE_LAST_UPDATE,Fechahoy())
                    .commit();

          /*  Intent i = new Intent(context, ViewDataActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("data", intent.getStringExtra("textoJSON"));
            context.startActivity(i);*/
        }
        else if(intent.getAction().equals(MyService.ACTION_NO_RECORD)){
            Toast.makeText(context, "No update" , Toast.LENGTH_SHORT).show();
        }
        else    {
            Toast.makeText(context, "Error con el proveedor de internet" , Toast.LENGTH_SHORT).show();
        }

    }

    public String Fechahoy(){
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes  = fecha.get(Calendar.MONTH);
        int dia  = fecha.get(Calendar.DAY_OF_MONTH);
      return dia+"/"+mes+"/"+anio+" - "+fecha.get(Calendar.HOUR_OF_DAY)+":"+fecha.get(Calendar.MINUTE);
    }

    public int hoy(){
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
//        Calendar fecha = new GregorianCalendar();
//        int anio = fecha.get(Calendar.YEAR);
//        int mes  = fecha.get(Calendar.MONTH);
//        int dia  = fecha.get(Calendar.DAY_OF_MONTH);
//        return dia+"/"+mes+"/"+anio;
    }
}
