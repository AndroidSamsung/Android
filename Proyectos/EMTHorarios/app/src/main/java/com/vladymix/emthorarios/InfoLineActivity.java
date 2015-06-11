package com.vladymix.emthorarios;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vladymix.emthorarios.utils.BDD;
import com.vladymix.emthorarios.utils.Favorito;
import com.vladymix.emthorarios.utils.Parada;
import com.vladymix.emthorarios.utils.ProcessAsync;
import com.vladymix.emthorarios.utils.ResultadosURL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


public class InfoLineActivity extends ListActivity  {

    private List<Parada> paradas;
    String direccion="1";
    String label,line,namea,nameb;
    BDD bdd;

    public static class ViewHolder{
        TextView visr_node;
        TextView visr_name;
    }

    private ArrayAdapter<Parada> getlistAdapter(){

        ArrayAdapter<Parada> adatador = new ArrayAdapter<Parada>(this,R.layout.view_item_stop,R.id.visr_idstop,paradas){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder;
                if(convertView == null){
                    holder = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.view_item_stop,null);

                    holder.visr_node = (TextView)convertView.findViewById(R.id.visr_idstop);
                    holder.visr_name =(TextView)convertView.findViewById(R.id.visr_location);
                    convertView.setTag(holder);
                }
                else{
                    holder =(ViewHolder)convertView.getTag();
                }

                Parada stop = paradas.get(position);
                holder.visr_name.setText(stop.getName());
                holder.visr_node.setText(stop.getNode());


                return convertView;
            }
        };
                return adatador;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_line);
        loadIntent();
        bdd = BDD.getInstance();
        bdd.setContext(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(this, ArrivedsActivity.class);
        Parada p = paradas.get(position);
        i.putExtra(FINALVALUE.STOP_NAME, p.getName());
        i.putExtra(FINALVALUE.STOP_IDSTOP, p.getNode());
        startActivity(i);
    }

    private void loadIntent(){
        Bundle bundle = getIntent().getExtras();
        line = bundle.getString(FINALVALUE.LINE_LINE);
        label =bundle.getString(FINALVALUE.LINE_LABEL);
        namea = bundle.getString(FINALVALUE.LINE_NAMEA);
        nameb = bundle.getString(FINALVALUE.LINE_NAMEB);
        getInfoRute(line, direccion);


    }

    private void getInfoRute(String line, String direccion){
        ProcessAsync processAsync = new ProcessAsync(this,"Obteniendo ruta linea "+label,new llamadaURL());
        processAsync.execute("RUTELINE", line, direccion);
        setTitle("Ruta de linea " + label);

        String f = getResources().getString(R.string.direction_code);

        if(direccion.equals("1"))
            getActionBar().setSubtitle(f+" " +nameb);
        else
            getActionBar().setSubtitle(f+" " +namea);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_line, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Boolean exits = bdd.existLine(label);
        if(exits) {
            if( menu.findItem(R.id.menu_remove)==null){
                menu.add(Menu.NONE, R.id.menu_remove, Menu.NONE, getString(R.string.lb_removefavorito));
                menu.removeItem(R.id.menu_add);
            }
        }
        else {
            if(menu.findItem(R.id.menu_remove)!=null)
                menu.removeItem(R.id.menu_remove);
            if(menu.findItem(R.id.menu_add)==null)
                menu.add(Menu.NONE, R.id.menu_add, Menu.NONE, getString(R.string.lb_addfavorito));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_remove:
                if(bdd.deleteLine(label))
                    Toast.makeText(this,"Elemento eliminado", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_add:
                if(!label.equals("") && !namea.equals("") && !nameb.equals(""));
                {
                    BDD bdd = BDD.getInstance();
                    bdd.setContext(this);
                    if (bdd.AgregarFavorito(new Favorito("Line", label, namea + "-" + nameb, "Favorite Stop"))) {
                        Toast.makeText(this, "Agregado correctamente", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void changeDireccion(MenuItem menuItem){
        if(direccion.equals("1")){
            direccion="2";
        }
        else{
            direccion="1";
        }
        getInfoRute(line, direccion);
    }


    public class llamadaURL implements ResultadosURL {
        /*
        <REG>
            <Line>001</Line>
            <SecDetail>10</SecDetail>
            <OrderDetail>1</OrderDetail>
            <Node>4514</Node>
            <Distance>0</Distance>
            <DistStopPrev>0</DistStopPrev>
            <Name>ISAAC PERAL-PZA.CRISTO REY</Name>
            <PosxNode>439221</PosxNode>
            <PosyNode>4476870</PosyNode>
        </REG>
         */

        @Override
        public void CallbackURL(Document doc) {
            try {
            NodeList listaREG = doc.getElementsByTagName("Stop");
            Element elemento;
                paradas= new ArrayList<Parada>();
                for(int i=0; i<listaREG.getLength(); i++) {

                    elemento = (Element)listaREG.item(i);

                    String node = elemento.getElementsByTagName("IdStop").item(0).getFirstChild().getNodeValue();
                    String name = elemento.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
                    String coordinateX = elemento.getElementsByTagName("CoordinateX").item(0).getFirstChild().getNodeValue();
                    String coordinateY = elemento.getElementsByTagName("CoordinateY").item(0).getFirstChild().getNodeValue();

                    paradas.add(new Parada( name, node, coordinateX, coordinateY));
                }
                setListAdapter(getlistAdapter());
            }
            catch (Exception ex){}
        }
    }

}
