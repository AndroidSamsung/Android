package com.vladymix.emthorarios;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vladymix.emthorarios.utils.Arrived;
import com.vladymix.emthorarios.utils.BDD;
import com.vladymix.emthorarios.utils.Favorito;
import com.vladymix.emthorarios.utils.Linea;
import com.vladymix.emthorarios.utils.Parada;
import com.vladymix.emthorarios.utils.ProcessAsync;
import com.vladymix.emthorarios.utils.ResultadosURL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


public class ArrivedsActivity extends ListActivity {

    String idStop,name;
    List<Arrived> stopsArriveds;
    Parada parada;
    BDD bdd;

    public static class ViewHolder{
        TextView visa_idLine;
        TextView visa_time;
    }

    public ArrayAdapter<Arrived> getlistAdapter(){

        ArrayAdapter<Arrived> listaadapter = new ArrayAdapter<Arrived>(
                this,
                R.layout.view_item_stop_arrived,
                R.id.visa_idLine,
                stopsArriveds){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView==null){
                    convertView = getLayoutInflater().inflate(R.layout.view_item_stop_arrived,null);
                    holder = new ViewHolder();
                    holder.visa_idLine = (TextView)convertView.findViewById(R.id.visa_idLine);
                    holder.visa_time =(TextView)convertView.findViewById(R.id.visa_time);

                    convertView.setTag(holder);
                }
                else{
                    holder = (ViewHolder)convertView.getTag();
                }
                Arrived item = stopsArriveds.get(position);
                holder.visa_idLine.setText(item.getIdStop());
                holder.visa_time.setText(item.getTimeLeftBus());

                return convertView;
            }
        };

        return listaadapter;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arriveds);
        LoadIntent();
        bdd = BDD.getInstance();
        bdd.setContext(this);
    }

    private void LoadIntent(){
        Bundle bundle = getIntent().getExtras();
        idStop = bundle.getString(FINALVALUE.STOP_IDSTOP);
        name =bundle.getString(FINALVALUE.STOP_NAME);
        getInfoStop(idStop);
    try {
        getArrivedsStop(idStop);
    }
    catch (Exception ex){
        Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();}
    }

    private void getInfoStop(String idStop){
        ProcessAsync processAsync = new ProcessAsync(this,"Obteniendo informacion "+idStop,new callbackInfoStop());
        processAsync.execute("INFOSTOP", idStop);
    }

    private void getArrivedsStop(String idStop){
        ProcessAsync processAsync = new ProcessAsync(this,"Obteniendo informacion "+idStop,new callbackArrivedsStop());
        processAsync.execute("ARRIVEDSSTOP", idStop);
    }

    public class callbackInfoStop implements ResultadosURL {

        @Override
        public void CallbackURL(Document doc) {
            try {
                NodeList listaREG = doc.getElementsByTagName("Stop");
                Element elemento;
                Element line;
                List<Linea> lineas;
                parada = new Parada();

                for(int i=0; i<listaREG.getLength(); i++) {

                    elemento = (Element)listaREG.item(i);
                    String node = elemento.getElementsByTagName("IdStop").item(0).getFirstChild().getNodeValue();
                    String name = elemento.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue();
                    String postaladress = elemento.getElementsByTagName("PostalAdress").item(0).getFirstChild().getNodeValue();

                    parada.setNode(node);
                    parada.setName(name);
                    parada.setPostalAdress(postaladress);

                    NodeList listalinea = elemento.getElementsByTagName("Line");
                   lineas = new ArrayList<Linea>();

                    for(int x=0;x<listalinea.getLength();x++){
                        line = (Element)listalinea.item(x);
                        String Line = line.getElementsByTagName("IdLine").item(0).getFirstChild().getNodeValue();
                        String Label = line.getElementsByTagName("Label").item(0).getFirstChild().getNodeValue();
                        String Direccion = line.getElementsByTagName("Direction").item(0).getFirstChild().getNodeValue();
                        String NameA = line.getElementsByTagName("HeaderA").item(0).getFirstChild().getNodeValue();
                        String NameB = line.getElementsByTagName("HeaderB").item(0).getFirstChild().getNodeValue();

                    lineas.add(new Linea(Line,Label,Direccion,NameA,NameB));
                    }
                    parada.setListaLineas(lineas);
                    parada.setLines(lista_lineas_string(parada.getListaLineas()));
                }
                ((TextView)findViewById(R.id.ac_arriveds_ubicacion)).setText(parada.getPostalAdress());
                ((TextView)findViewById(R.id.ac_arriveds_lineas)).setText(parada.getLines());
                setTitle("Parada "+ parada.getNode());
                getActionBar().setSubtitle(parada.getName());

            }
            catch (Exception ex){}

        }
    }

    public String lista_lineas_string(List<Linea> listalineas){
        String result = "";
        for(Linea item : listalineas){
            if(item.getDirection().equals("A")){
                result += item.getLabel() +" "+getResources().getString(R.string.direction_code)+" "+item.getNameA()+"\n";
            }
            else{
                result += item.getLabel() +" "+getResources().getString(R.string.direction_code)+" "+item.getNameB()+"\n";

            }

        }
return result;

    }

    public class callbackArrivedsStop implements ResultadosURL{

        @Override
        public void CallbackURL(Document doc) {
            try {
                Integer minutes=0;
                if(doc==null){
                    Toast.makeText(ArrivedsActivity.this,"Documento null",Toast.LENGTH_SHORT).show();
                    return;
                }

                NodeList listaREG = doc.getElementsByTagName("Arrive");
                Element elemento;
                stopsArriveds= new ArrayList<Arrived>();
                for(int i=0; i<listaREG.getLength(); i++) {
                    elemento = (Element)listaREG.item(i);
                    String timeleft = elemento.getElementsByTagName("TimeLeftBus").item(0).getFirstChild().getNodeValue();
                    Integer seconds = Integer.valueOf(timeleft);
                    if(seconds==999999){
                        timeleft = "> 20 "+ getResources().getString(R.string.lb_minutos);
                    }
                    else if(seconds==0){
                        timeleft =getResources().getString(R.string.lb_inmediaciones);
                    }
                    else {
                        minutes = seconds/60;
                        timeleft= String.valueOf(minutes)+" "+getResources().getString(R.string.lb_minutos);
                    }
                    String idLine = elemento.getElementsByTagName("idLine").item(0).getFirstChild().getNodeValue();
                    stopsArriveds.add(new Arrived(idLine,timeleft));
                    }

                setListAdapter(getlistAdapter());
                }
            catch (Exception ex){
                Toast.makeText(ArrivedsActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arriveds, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Boolean exits = bdd.existStop(idStop);
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
               if(bdd.deleteStop(idStop))
                   Toast.makeText(this,"Elemento eliminado", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_add:
                if(bdd.AgregarFavorito(new Favorito("Stop", String.valueOf(parada.getNode()), parada.getPostalAdress(), "Favorite Stop")))
                    Toast.makeText(this, "Agregado correctamente",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateTimes(MenuItem menuItem){
        getArrivedsStop(idStop);
    }




}
