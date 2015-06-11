package ejemplos.samsung.listabasica;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListaBasicaActivity extends Activity {

    private ListView lista;
    private String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_basica);

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

        lista = (ListView)findViewById(R.id.lista);

        ListAdapter adaptador = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_checked,
                datos);

        lista.setEmptyView(findViewById(R.id.listaVacia));
        lista.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dato = "";
                dato = datos[position];
                dato = ((TextView)view).getText().toString();
                dato = (String)parent.getItemAtPosition(position);
                dato = ((ListAdapter)parent.getAdapter()).getItem(position).toString();
                Toast.makeText(ListaBasicaActivity.this, dato, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
