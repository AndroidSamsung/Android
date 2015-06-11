package com.example.fabricio.menusfragments;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private DrawerLayout drawerLayout;
    private ListView lista;
    private ListView menurig;
    private String[] opciones;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        lista =(ListView)findViewById(R.id.listviewmenu);
        menurig = (ListView)findViewById(R.id.listviewmenu2);

        lista.setOnItemClickListener(new OnClickMenu());


        opciones = new String[]{"Man Fragment", "Fragmento 1","Fragmento 2"};

        //Por el tipo de componente lnea getActionBat.getthemeContext

        lista.setAdapter(new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, android.R.id.text1, opciones));

        menurig.setAdapter(new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, android.R.id.text1, opciones));


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_navigation_drawer,R.string.drawer_open, R.string.drawer_close);


        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
       // drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            Toast.makeText(this,"tap",Toast.LENGTH_SHORT).show();}
        return true;
    }

    public class OnClickMenu implements AdapterView.OnItemClickListener{

       @Override
       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


           if(position==0){
               getFragmentManager().beginTransaction()
                       .replace(R.id.content_frame, new FragmentMain())
                       .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                       .addToBackStack(null) //fragmento alcual remplazado
                       .commit();
           }
           else if(position==1){
               getFragmentManager().beginTransaction()
                       .replace(R.id.content_frame, new FragmentUno())
                       .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                       .addToBackStack(null) //fragmento alcual remplazado
                       .commit();

           }
           else {
               getFragmentManager().beginTransaction()
                       .replace(R.id.content_frame, new FragmentDos())
                       .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                       .addToBackStack(null) //fragmento alcual remplazado
                       .commit();

           }

           drawerLayout.closeDrawer(lista);
       }
   }


}
