package com.vladymix.currencyexchange.utils;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.vladymix.currencyexchange.STATICVALUE;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Fabricio on 08/06/2015.
 */
public class MyService extends IntentService {

    public static final String ACTION_FIN = "com.vladymix.currencyexchange.action.FIN";
    public static final String ACTION_ERROR = "com.vladymix.currencyexchange.action.ERROR";
    public static final String ACTION_NO_RECORD = "com.vladymix.currencyexchange.NO_RECORD";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String actionAux="";
        String operation = intent.getStringExtra("UPDATE");

        ArrayList<Pais> listaPaises = new ArrayList<Pais>();
        try {
            if(operation.equals("UPDATE")) {
                URL url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                listaPaises = getListFromURL(doc);
                actionAux = ACTION_FIN;
            }
            else{
                actionAux = ACTION_NO_RECORD;
            }

        }
        catch (Exception ex){
            Log.d("[Conexion]", ex.getMessage());
            actionAux = ACTION_ERROR;
        }
        finally {
            Intent srv = new Intent();
            srv.setAction(actionAux);
            srv.putParcelableArrayListExtra("LISTA", listaPaises);
            sendBroadcast(srv);
        }

    }

    private ArrayList<Pais> getListFromURL(Document document) {

        if (document != null) {
            NodeList listaCube = document.getElementsByTagName("Cube");
            Element elemento;
            //leo xml y almaceno en un arraylst
            for (int i = 2; i < listaCube.getLength(); i++) {
                elemento = (Element) listaCube.item(i);
                String currency = elemento.getAttribute("currency");
                int pos = STATICVALUE.getPositionPaisbyCurrecny(currency);
                if (pos != -1) {
                    Pais mod = STATICVALUE.ListaPaises.get(pos);
                    mod.setValueCurrency(Double.valueOf(elemento.getAttribute("rate")));
                    STATICVALUE.ListaPaises.set(pos, mod);
                }
            }

            return STATICVALUE.ListaPaises;
        }
        else
            return null;
    }
}
