package com.vladymix.emthorarios.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabricio on 07/06/2015.
 */
public class BDD {
    private SQLiteDatabase db;

    private static BDD ourInstance = new BDD();
    private Context context;

    public static BDD getInstance() {

        return ourInstance;
    }
    public void setContext(Context context){
        this.context = context;
        //obtenemos un helper para majejar la base de datos 'DBRates'
        EMTSQLiteHelper ratesHelper = new EMTSQLiteHelper(context, "DBRates.db", null, 1);
        db = ratesHelper.getWritableDatabase();
    }

    private BDD() {
    }

    public boolean AgregarFavorito(Favorito favorito){

        ContentValues values = new ContentValues();
        /*
        int Id;//id BDD
        String Tipo;//Parada o Linea
       String Data; //Imagen patch
       String Alto; //Imagen alto
        String Ancho;  //Imagen ancho
        String Color; //Imagen
        String Nick_Name; //NOmbre Opcional
       String idParada_Linea; //Parada -> 2603 Linea -> 145
        String Titulo_Ubicacion_Direccion;// Ubicacion    Direccion
        String Ubicacion_Direccion;//Nombre de la parada o Direccion de la linea
        */
        values.put("Tipo", favorito.getTipo());
        values.put("Data", favorito.getData());
        values.put("Alto", favorito.getAlto());
        values.put("Ancho", favorito.getAncho());
        values.put("Nick_Name", favorito.getNick_Name());
        values.put("idParada_Linea", favorito.getIdParada_Linea());
        values.put("Titulo_Ubicacion_Direccion", favorito.getTitulo_Ubicacion_Direccion());
        values.put("Ubicacion_Direccion", favorito.getUbicacion_Direccion());

        db.insert(EMTSQLiteHelper.TABLE_NAME, null, values);

        return true;
    }
    public List<Favorito> getListaFavoritos(){
        ArrayList<Favorito> listfavoritos = new ArrayList<Favorito>();
        Cursor cursor = db.rawQuery("SELECT idParada_Linea, Titulo_Ubicacion_Direccion, Ubicacion_Direccion FROM "+EMTSQLiteHelper.TABLE_NAME+
                " ORDER BY Titulo_Ubicacion_Direccion DESC", null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Favorito item = new Favorito();
                item.setIdParada_Linea(cursor.getString(0));
                item.setTitulo_Ubicacion_Direccion(cursor.getString(1));
                item.setUbicacion_Direccion(cursor.getString(2));
                listfavoritos.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listfavoritos;
    }

    public Boolean existStop(String node){
        Cursor cursor = db.rawQuery("SELECT Tipo, idParada_Linea FROM "+EMTSQLiteHelper.TABLE_NAME+" WHERE Tipo='Stop' and idParada_Linea="+node, null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
           // String tipo = cursor.getString(0);
          //  String idpa = cursor.getString(1);
            return true;
      }
        else{
            return false;
        }

    }

    public Boolean existLine(String label){
        Cursor cursor = db.rawQuery("SELECT Tipo, idParada_Linea FROM "+EMTSQLiteHelper.TABLE_NAME+" WHERE Tipo='Line' and idParada_Linea='"+label+"'", null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            // String tipo = cursor.getString(0);
            //  String idpa = cursor.getString(1);
            return true;
        }
        else{
            return false;
        }

    }

    public Boolean deleteStop(String node){
      try{
          String sql = "DELETE  FROM "+EMTSQLiteHelper.TABLE_NAME+" WHERE Tipo='Stop' and idParada_Linea="+node;
          db.execSQL(sql);
            return true;
        }
      catch (Exception ex){
          return false;
      }


    }
    public Boolean deleteLine(String label){
        try{
            String sql = "DELETE  FROM "+EMTSQLiteHelper.TABLE_NAME+" WHERE Tipo='Line' and idParada_Linea='"+label+"'";
            db.execSQL(sql);
            return true;
        }
        catch (Exception ex){
            return false;
        }


    }


}
