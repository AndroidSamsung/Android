package com.example.fabricio.correofragment;

import android.app.ListFragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class FragmentCorreos extends ListFragment {
    ArrayList<Correo> misCorreos;
    private SeleccionCorreo listener;

    public FragmentCorreos() {
        // Required empty public constructor

    }
    public void loadListaCorreos(){
       misCorreos = getListacorreo();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        try {
            loadListaCorreos();
            setListAdapter(getmyListAdapter());
        }
        catch (Exception ex){
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_correos, container, false);
    }

    public ArrayList<Correo> getListacorreo(){
        ArrayList<Correo> correos = new ArrayList<Correo>();
        correos.add(new Correo("vlady-mix@gmail.com","Mi","Mi asunto 1","Mensaje 1"));
        correos.add(new Correo("vanessa@gmail.com","Mi","Mi asunto 2","Mensaje 2"));
        correos.add(new Correo("nancy@gmail.com","Mi","Mi asunto 3","Mensaje 3"));
        correos.add(new Correo("vladymix@gmail.com","Mi","Mi asunto 4","Mensaje 4"));
        correos.add(new Correo("fabricio@gmail.com","Mi","Mi asunto 5","Mensaje 5"));
        correos.add(new Correo("vladimir@gmail.com", "Mi", "Mi asunto 6", "Mensaje 6"));
        return correos;
    }

    public ArrayAdapter<Correo> getmyListAdapter(){

        ArrayAdapter<Correo> arrayAdapter = new ArrayAdapter<Correo>(getActivity(),R.layout.view_item_correo,R.id.vic_de, misCorreos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                try{
                View vista = super.getView(position, convertView, parent);
                TextView t1 = (TextView)vista.findViewById(R.id.vic_de);
                TextView t2 = (TextView)vista.findViewById(R.id.vic_mensaje);
                t1.setText(misCorreos.get(position).getDe());
                t2.setText(misCorreos.get(position).getMensaje());

                return vista;}
                catch (Exception ex){
                    System.out.print(ex.getMessage());
                    return null;
                }

            }
        };

        return  arrayAdapter;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        int orientacion = getActivity().getResources().getConfiguration().orientation;
        int lan = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        if(orientacion ==  lan) {
            ((TextView) getActivity().findViewById(R.id.fp_asunto)).setText(misCorreos.get(position).getAsunto());
            ((TextView) getActivity().findViewById(R.id.fp_de)).setText(misCorreos.get(position).getDe());
            ((TextView) getActivity().findViewById(R.id.fp_para)).setText(misCorreos.get(position).getPara());
            ((TextView) getActivity().findViewById(R.id.fp_mensaje)).setText(misCorreos.get(position).getMensaje());
        }
        else if (orientacion == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            listener =  (SeleccionCorreo)getActivity();

            listener.CorreoSeleccionado(misCorreos.get(position));

        }

       // super.onListItemClick(l, v, position, id);
    }
}
