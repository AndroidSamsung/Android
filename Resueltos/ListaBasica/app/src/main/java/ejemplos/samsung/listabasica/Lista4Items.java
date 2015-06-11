package ejemplos.samsung.listabasica;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Lista4Items extends ListActivity {

    private String[][] datos;
    private TextView etiqueta;
    private boolean[] estrellaMarcada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_basica_la);

        etiqueta = (TextView)findViewById(R.id.etiqueta);

        datos = new String[][]{
                {"Alumno 1", "JK001", "GCM21" , "2013"},
                {"Alumno 2", "JK002", "GCM21" , "2012"},
                {"Alumno 3", "JK003", "GCM21" , "2013"},
                {"Alumno 4", "JK004", "GCM21" , "2013"},
                {"Alumno 5", "JK005", "GCM21" , "2011"},
                {"Alumno 6", "JK006", "GCM22" , "2013"},
                {"Alumno 7", "JK007", "GCM22" , "2010"},
                {"Alumno 8", "JK008", "GCM22" , "2013"},
                {"Alumno 9", "JK009", "GCM22" , "2013"}
        };

        estrellaMarcada = new boolean[datos.length];
        for(int i=0; i< estrellaMarcada.length; i++){
            estrellaMarcada[i] = false;
        }

        ListAdapter adaptador = new ArrayAdapter<String[]>(
                this,
                R.layout.listado_personalizado_4items,
                R.id.nombre,
                datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;

                if(convertView!=null){
                    viewHolder = (ViewHolder)convertView.getTag();
                } else {
                    convertView = getLayoutInflater().inflate(R.layout.listado_personalizado_4items, null);
                    viewHolder = new ViewHolder();
                    viewHolder.foto = (ImageView)convertView.findViewById(R.id.foto);
                    viewHolder.estrella = (ImageView)convertView.findViewById(R.id.estrella);
                    viewHolder.nombre = (TextView)convertView.findViewById(R.id.nombre);
                    viewHolder.matricula = (TextView)convertView.findViewById(R.id.matricula);
                    viewHolder.grupo = (TextView)convertView.findViewById(R.id.grupo);
                    viewHolder.anio = (TextView)convertView.findViewById(R.id.anio);
                    convertView.setTag(viewHolder);
                }

                int fotoID = getResources().getIdentifier((datos[position][1]).toLowerCase(), "drawable", getPackageName());
                viewHolder.foto.setImageResource(fotoID);
                int estrellaResource = (estrellaMarcada[position])?android.R.drawable.star_on:android.R.drawable.star_off;
                viewHolder.estrella.setImageResource(estrellaResource);
                viewHolder.nombre.setText(datos[position][0]);
                viewHolder.matricula.setText(datos[position][1]);
                viewHolder.grupo.setText(datos[position][2]);
                viewHolder.anio.setText(datos[position][3]);
                return convertView;
            }
        };

        setListAdapter(adaptador);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        etiqueta.setText("Has elegido: " + datos[position][0]);
        ImageView estrella = (ImageView)v.findViewById(R.id.estrella);
        if(l.isItemChecked(position)){
            estrella.setImageResource(android.R.drawable.star_on);
        }else {
            estrella.setImageResource(android.R.drawable.star_off);
        }
        estrellaMarcada[position] = !estrellaMarcada[position];
    }

    private static class ViewHolder{
        ImageView foto;
        ImageView estrella;
        TextView nombre;
        TextView matricula;
        TextView grupo;
        TextView anio;
    }
}

