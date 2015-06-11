package com.vladymix.emthorarios.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.vladymix.emthorarios.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Fabricio on 05/06/2015.
 */
public class ParseXML {
    private Context context;

    public ParseXML(Context context) {
        this.context = context;
    }

    public List<Parada> getListaParadas(){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getText(R.string.msn_getStops));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();


        final String TAG = " [readXML_STOPS] ";
        List<Parada> paradas = new ArrayList<Parada>();
        Document doc = null;
        try {
            InputStream source = context.getResources().openRawResource(R.raw.nodeslines);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(source);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.d(TAG, e.getMessage());
        } catch (SAXException e) {
            Log.d(TAG, e.getMessage());
        }
            /*
             <REG>
              <Node>1</Node>
              <PosxNode>433743,5</PosxNode>
              <PosyNode>4480432</PosyNode>
              <Name>AV.VALDEMARIN-ALTAIR</Name>
              <Lines>161/1</Lines>
            </REG>
             */
        NodeList listaREG = doc.getElementsByTagName("REG");
        Element elemento;
        String node;
        String name;
        paradas= new ArrayList<Parada>();
        for(int i=0; i<listaREG.getLength(); i++) {
            elemento = (Element)listaREG.item(i);
            node = elemento.getElementsByTagName("Node").item(0).getFirstChild().getNodeValue();
            name = elemento.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
            paradas.add(new Parada(node,name));
        }
        progressDialog.dismiss();
        return paradas;
    }

    public List<Linea> getListaLineas(){
        final String TAG = " [readXML_LINES] ";
        List<Linea> lineas = new ArrayList<Linea>();
        Document doc = null;
        try {
            InputStream source =context.getResources().openRawResource(R.raw.lines);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(source);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.d(TAG, e.getMessage());
        } catch (SAXException e) {
            Log.d(TAG, e.getMessage());
        }
        NodeList listaREG = doc.getElementsByTagName("REG");
        Element elemento;
        String NameA;
        String NameB;
        String Label;
        String Line;
        for(int i=0; i<listaREG.getLength(); i++) {
            elemento = (Element)listaREG.item(i);
            NameA = elemento.getElementsByTagName("NameA").item(0).getFirstChild().getNodeValue();
            NameB = elemento.getElementsByTagName("NameB").item(0).getFirstChild().getNodeValue();
            Label = elemento.getElementsByTagName("Label").item(0).getFirstChild().getNodeValue();
            Line = elemento.getElementsByTagName("Line").item(0).getFirstChild().getNodeValue();
            lineas.add(new Linea(Line,Label, NameA, NameB));
        }

        return lineas;
    }

    public  Linea getLinebyLabel(String label){
        final String TAG = " [readXML_LINES] ";
        List<Linea> lineas = new ArrayList<Linea>();
        Document doc = null;
        try {
            InputStream source =context.getResources().openRawResource(R.raw.lines);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(source);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.d(TAG, e.getMessage());
        } catch (SAXException e) {
            Log.d(TAG, e.getMessage());
        }
        NodeList listaREG = doc.getElementsByTagName("REG");
        Element elemento;
        String NameA;
        String NameB;
        String Label;
        String Line;
        for(int i=0; i<listaREG.getLength(); i++) {
            elemento = (Element)listaREG.item(i);
            NameA = elemento.getElementsByTagName("NameA").item(0).getFirstChild().getNodeValue();
            NameB = elemento.getElementsByTagName("NameB").item(0).getFirstChild().getNodeValue();
            Label = elemento.getElementsByTagName("Label").item(0).getFirstChild().getNodeValue();
            Line = elemento.getElementsByTagName("Line").item(0).getFirstChild().getNodeValue();
            if(Label.equals(label)){
                return new Linea(Line,Label, NameA, NameB);
            }

        }

        return null;


    }

}
