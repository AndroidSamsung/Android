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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vladymix.emthorarios.utils.BDD;
import com.vladymix.emthorarios.utils.Favorito;
import com.vladymix.emthorarios.utils.Linea;
import com.vladymix.emthorarios.utils.Parada;
import com.vladymix.emthorarios.utils.ParseXML;

import java.util.List;


public class FavoritesActivity extends ListActivity {

    List<Favorito> misFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        InitializeComponents();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ViewHolder info = (ViewHolder)v.getTag();

        if(info.vif_Titulo_Ubicacion_Direccion.getText().toString().equals(getString(R.string.title_ubicacion))){
            Intent i = new Intent(this, ArrivedsActivity.class);
            i.putExtra(FINALVALUE.STOP_NAME,info.vif_Ubicacion_Direccion.getText().toString());
            i.putExtra(FINALVALUE.STOP_IDSTOP, info.vif_label_node.getText().toString());
            startActivityForResult(i, FINALVALUE.ID_ACTIVIDAD_ARRIVEDS);
        }
        else{
            //InfoLineActivity.linea = lineas.get(position);
            Intent i = new Intent(this,InfoLineActivity.class);

            ParseXML parseXML  = new ParseXML(this);
            Linea linea = parseXML.getLinebyLabel(info.vif_label_node.getText().toString());
            if(linea!=null){
                i.putExtra(FINALVALUE.LINE_LINE, linea.getLine());
                i.putExtra(FINALVALUE.LINE_LABEL, linea.getLabel());
                i.putExtra(FINALVALUE.LINE_NAMEA, linea.getNameA());
                 i.putExtra(FINALVALUE.LINE_NAMEB, linea.getNameB());
                startActivityForResult(i, FINALVALUE.ID_ACTIVIDAD_INFO_LINE);

            }


        }

    }

    private void InitializeComponents(){
        BDD bdd = BDD.getInstance();
        bdd.setContext(this);
        misFavoritos = bdd.getListaFavoritos();
        setListAdapter(getmyArrarAdapter());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InitializeComponents();
    }

    private ArrayAdapter<Favorito> getmyArrarAdapter(){

        ArrayAdapter<Favorito> myAdapter = new ArrayAdapter<Favorito>(this,R.layout.view_item_favorite, R.id.vif_label_node, misFavoritos){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

               ViewHolder holder;
                if(convertView==null){
                    holder = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.view_item_favorite, null);

                    holder.vif_image = (ImageView)convertView.findViewById(R.id.vif_image);
                    holder.vif_label_node = (TextView)convertView.findViewById(R.id.vif_label_node);
                    holder.vif_Titulo_Ubicacion_Direccion = (TextView)convertView.findViewById(R.id.vif_Titulo_Ubicacion_Direccion);
                    holder.vif_Ubicacion_Direccion = (TextView)convertView.findViewById(R.id.vif_Ubicacion_Direccion);

                    convertView.setTag(holder);
                }
                else{
                    holder =(ViewHolder)convertView.getTag();
                }

                    //idParada_Linea, Titulo_Ubicacion_Direccion, Ubicacion_Direccion
                    Favorito item = new Favorito();
                    item = misFavoritos.get(position);
                    if (item.getTitulo_Ubicacion_Direccion().equals("Ubicacion")) {
                        holder.vif_image.setImageResource(R.drawable.applicationbar_stop);
                        holder.vif_Titulo_Ubicacion_Direccion.setText(getString(R.string.title_ubicacion));
                    }
                else{
                        holder.vif_image.setImageResource(R.drawable.applicationbar_line);
                        holder.vif_Titulo_Ubicacion_Direccion.setText(getString(R.string.title_direccion));
                    }
                    holder.vif_label_node.setText(item.getIdParada_Linea());

                    holder.vif_Ubicacion_Direccion.setText(item.getUbicacion_Direccion());



                return  convertView;
            }
        };


        return myAdapter;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    public void navigate_to_lines(MenuItem menuItem){
        Intent i = new Intent(this, LineasActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIDAD_LINEAS);
    }

    public void navigate_to_stops(MenuItem menuItem){
        Intent i = new Intent(this, ParadasActivity.class);
        startActivityForResult(i, FINALVALUE.ID_ACTIVIDAD_PARADAS);

    }

    public class ViewHolder{

        TextView vif_label_node,vif_Titulo_Ubicacion_Direccion,vif_Ubicacion_Direccion;
        ImageView vif_image;

    }


}
