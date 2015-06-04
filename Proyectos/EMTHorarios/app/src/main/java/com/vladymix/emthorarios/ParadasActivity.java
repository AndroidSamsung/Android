package com.vladymix.emthorarios;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.vladymix.emthorarios.utils.Parada;
import com.vladymix.emthorarios.utils.ParseXML;

import java.util.ArrayList;
import java.util.List;

public class ParadasActivity extends ListActivity {

    static List<Parada> paradas;
    MYArrayAdapter<Parada> myArrayAdapter;

    public class MYArrayAdapter<Parada> extends ArrayAdapter<Parada>{

        public MYArrayAdapter(Context context, int resource, int textViewResourceId, List<Parada> objects) {
            super(context, resource, textViewResourceId, objects);
        }


        @Override
        public Filter getFilter() {
            return myfilter;

        }

    }

    Filter myfilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<Parada> tempList=new ArrayList<Parada>();
            //constraint is the result from text you want to filter against.
            //objects is your data set you will filter from
            if(constraint != null && paradas!=null) {
                int length=paradas.size();
                int i=0;

                for (Parada stop: paradas){
                    if(stop.getNode().contains(constraint)){
                        tempList.add(stop);
                    }

                }
                //following two lines is very important
                //as publish result can only take FilterResults objects
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {

            if (results.count > 0) {
                setListAdapter(getMyArrayAdapter((List<Parada>) results.values));
            } else {
                 setListAdapter(null);
            }
        }
    };

    private static final class ViewHolder{
        TextView vis_node;
        TextView vis_name;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paradas);

        if(paradas==null){
             ParseXML parseXML = new ParseXML(this);
             paradas = parseXML.getListaParadas();
        }
        myArrayAdapter = getMyArrayAdapter(paradas);
        setListAdapter(myArrayAdapter);

        ((EditText)findViewById(R.id.id_textFilter)).addTextChangedListener(new MyEventeTextWacher());

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ViewHolder info = (ViewHolder)v.getTag();
        Intent i = new Intent(this, ArrivedsActivity.class);
        i.putExtra(FINALVALUE.STOP_NAME, info.vis_name.getText().toString());
        i.putExtra(FINALVALUE.STOP_IDSTOP, info.vis_node.getText().toString());
        startActivity(i);
    }


    private MYArrayAdapter<Parada> getMyArrayAdapter(final List<Parada> misparadas){

        MYArrayAdapter<Parada> adapter = new MYArrayAdapter<Parada>(this,
                R.layout.view_item_stops,R.id.vis_node, misparadas){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView==null){
                    convertView = getLayoutInflater().inflate(R.layout.view_item_stops, null);
                    holder = new ViewHolder();
                    holder.vis_name = (TextView)convertView.findViewById(R.id.vis_name);
                    holder.vis_node = (TextView)convertView.findViewById(R.id.vis_node);
                    convertView.setTag(holder);
                }
                else{
                    holder = (ViewHolder)convertView.getTag();
                }
                try {
                    Parada p = misparadas.get(position);
                    holder.vis_name.setText(p.getName());
                    holder.vis_node.setText(p.getNode());
                }
                catch (Exception ex){}
                return  convertView;
            }
        };


        return adapter;

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paradas, menu);
        return true;
    }

    public class MyEventeTextWacher implements TextWatcher{

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
