package com.vladymix.emthorarios;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vladymix.emthorarios.utils.Linea;
import com.vladymix.emthorarios.utils.ParseXML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class LineasActivity extends ListActivity {

    static List<Linea> lineas;

    ArrayAdapterLineas<Linea> myArrayAdapter;

    private static  class ViewHolder{
        TextView vil_label;
        TextView vil_nameA;
        TextView vil_nameB;
        String vil_line;
    }
    public void PushMensaje(String mensaje){
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        ViewHolder info = (ViewHolder)v.getTag();
        //InfoLineActivity.linea = lineas.get(position);
        Intent i = new Intent(this,InfoLineActivity.class);
        i.putExtra(FINALVALUE.LINE_LINE, info.vil_line);
        i.putExtra(FINALVALUE.LINE_LABEL, info.vil_label.getText().toString());
        i.putExtra(FINALVALUE.LINE_NAMEA, info.vil_nameA.getText().toString());
        i.putExtra(FINALVALUE.LINE_NAMEB, info.vil_nameB.getText().toString());
         startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineas);
        if(lineas==null){
            ParseXML parseXML = new ParseXML(this);
            lineas = parseXML.getListaLineas();
        }
        myArrayAdapter = getlistAdapter(lineas);
        setListAdapter(getlistAdapter(lineas));
        ((EditText)findViewById(R.id.id_filterLine)).addTextChangedListener(new MyListener());

    }

    public ArrayAdapterLineas<Linea> getlistAdapter(final List<Linea> lineas){

        ArrayAdapterLineas<Linea> adaptadorLineas = new ArrayAdapterLineas<Linea>(
                this,
                R.layout.view_item_lineas,
                R.id.vil_label,
                lineas){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.view_item_lineas,null);
                    holder = new ViewHolder();
                    holder.vil_label = (TextView)convertView.findViewById(R.id.vil_label);
                    holder.vil_nameA =  (TextView)convertView.findViewById(R.id.vil_nameA);
                    holder.vil_nameB = (TextView)convertView.findViewById(R.id.vil_nameB);
                    convertView.setTag(holder);
                }
                else{
                    holder =(ViewHolder)convertView.getTag();
                }
                try {
                    Linea linea = lineas.get(position);
                    holder.vil_line = linea.getLine();
                    holder.vil_label.setText(linea.getLabel());
                    holder.vil_nameA.setText(linea.getNameA());
                    holder.vil_nameB.setText(linea.getNameB());
                }
                catch (Exception ex){
                    Toast.makeText(LineasActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return convertView;
            }
        };


        return adaptadorLineas;
    }
    //region Class ArrayAdapter

    private class ArrayAdapterLineas<Linea> extends ArrayAdapter {

        public ArrayAdapterLineas(Context context, int resource, int textViewResourceId, List<Linea> objects) {
            super(context, resource, textViewResourceId, objects);
        }
        @Override
        public Filter getFilter() {
            return myfilterLine;

        }
    }

    Filter myfilterLine = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Linea> tempList=new ArrayList<Linea>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if(constraint != null && lineas!=null) {
                int length=lineas.size();
                int i=0;

                for (Linea line: lineas){
                    if( line.getLabel().contains(constraint.toString().toUpperCase())){
                        tempList.add(line);
                    }

                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                setListAdapter(getlistAdapter((List<Linea>) results.values));
            } else {
                setListAdapter(null);
            }
        }
    };

    //endregion
    public class MyListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            myArrayAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }



}
