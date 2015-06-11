package ejemplos.samsung.listabasica;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Lista2Items extends ListActivity {

    private String[][] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_basica_la);

        datos = new String[][]{
                {"Título 1", "Subtítulo largo 1"},
                {"Título 2", "Subtítulo largo 2"},
                {"Título 3", "Subtítulo largo 3"},
                {"Título 4", "Subtítulo largo 4"},
                {"Título 5", "Subtítulo largo 5"},
                {"Título 6", "Subtítulo largo 6"},
                {"Título 7", "Subtítulo largo 7"},
        };

        ListAdapter adaptador = new ArrayAdapter<String[]>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View vista = super.getView(position, convertView, parent);
                TextView t1 = (TextView)vista.findViewById(android.R.id.text1);
                TextView t2 = (TextView)vista.findViewById(android.R.id.text2);
                t1.setText(datos[position][0]);
                t2.setText(datos[position][1]);
                return vista;
            }
        };

        ListAdapter adaptador2 = new ArrayAdapter<String[]>(
                this,
                R.layout.listado_personalizado,
                R.id.titulo,
                datos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View vista = super.getView(position, convertView, parent);
                TextView t1 = (TextView)vista.findViewById(R.id.titulo);
                TextView t2 = (TextView)vista.findViewById(R.id.subtitulo);
                t1.setText(datos[position][0]);
                t2.setText(datos[position][1]);
                return vista;
            }
        };

        setListAdapter(adaptador2);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String dato = "";
        dato = datos[position][0];
        //TextView t1 = (TextView)v.findViewById(android.R.id.text1);
        //dato = t1.getText().toString();
        //dato = ((String[])l.getItemAtPosition(position))[0];
        //dato = ((String[])l.getAdapter().getItem(position))[0];
        Toast.makeText(this, dato, Toast.LENGTH_SHORT).show();
    }
}
