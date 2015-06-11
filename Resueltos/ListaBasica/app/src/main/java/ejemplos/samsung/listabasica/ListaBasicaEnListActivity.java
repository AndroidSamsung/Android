package ejemplos.samsung.listabasica;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListaBasicaEnListActivity extends ListActivity {

    private String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_basica_la);

        datos = new String[]{
                "Elemento 1",
                "Elemento 2",
                "Elemento 3",
                "Elemento 4",
                "Elemento 5",
                "Elemento 6",
                "Elemento 7",
                "Elemento 8",
                "Elemento 9",
                "Elemento 10",
                "Elemento 11",
                "Elemento 12",
                "Elemento 13",
                "Elemento 14"
        };
        //datos = new String[]{};

        ListAdapter adaptador = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_checked,
                datos);

        setListAdapter(adaptador);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String dato = "";
        dato = datos[position];
        dato = ((TextView) v).getText().toString();
        dato = (String) l.getItemAtPosition(position);
        dato = l.getAdapter().getItem(position).toString();
        Toast.makeText(this, dato, Toast.LENGTH_SHORT).show();
    }
}
