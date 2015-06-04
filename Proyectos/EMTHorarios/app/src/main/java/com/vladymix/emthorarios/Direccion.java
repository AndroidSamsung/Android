package com.vladymix.emthorarios;

import java.util.Calendar;


/**
 * Created by Fabricio on 02/06/2015.
 */
public class Direccion {

    /// <summary>
    /// Optiene la lista de paradas de la ruta de la linea por su Nombre
    /// </summary>
    public static String getRuteLine(String linea)
    {
        Calendar c1 = Calendar.getInstance();
       // String onlyFecha = fecha.ToString("dd/MM/yyyy");
        String  onlyFecha = c1.get(Calendar.DATE)+"/"+c1.get(Calendar.MONTH)+"/"+ c1.get(Calendar.YEAR);
        return "https://servicios.emtmadrid.es:8443//bus/servicebus.asmx/GetRouteLines?idClient=WEB.SERV.vlady-mix@hotmail.com&PassKey=2A2FEBD8-9481-4BE4-B343-06B3BB4B8D7E&SelectDate=" + onlyFecha + "&Lines=" + linea;
    }
    /// <summary>
    /// Optiene la informacion de la/s parada/s de una distancia en metros / 0 para descripcion de una sola parada
    /// </summary>
    public static String getStopsFromStop(String idParada, String Metros)
    {
        return "https://servicios.emtmadrid.es:8443//geo/servicegeo.asmx/getStopsFromStop?idClient=WEB.SERV.vlady-mix@hotmail.com&PassKey=2A2FEBD8-9481-4BE4-B343-06B3BB4B8D7E&idStop=" + idParada + "&Radius=" + Metros + "&statistics=''&cultureInfo=''";
    }
    /// <summary>
    /// Optiene una lista con la informacion de tiempos de espera de la parada solicitada
    /// </summary>
    public static String getArriveStop(String idParada)
    {
        return "https://servicios.emtmadrid.es:8443//geo/servicegeo.asmx/getArriveStop?idClient=WEB.SERV.vlady-mix@hotmail.com&PassKey=2A2FEBD8-9481-4BE4-B343-06B3BB4B8D7E&idStop=" + idParada + "&statistics=''&cultureInfo=''";
    }
    /// <summary>
    /// Optiene una lista de paradas en unas coordenadas especificas y con un radio en metros
    /// </summary>
    public static String getStopsFromXY(String longitudX, String latitudY, String radio){

        return "https://servicios.emtmadrid.es:8443//geo/servicegeo.asmx/getStopsFromXY?idClient=WEB.SERV.vlady-mix@hotmail.com&PassKey=2A2FEBD8-9481-4BE4-B343-06B3BB4B8D7E&coordinateX=" + longitudX + "&coordinateY=" + latitudY + "&Radius=" + radio + "&statistics=''&cultureInfo=''";
    }

    /// <summary>
    /// Optiene la lista de las paradas de una linea dependiendo de su dirreccion
    /// </summary>
    public static String getStopsLine(String Linea, String Direccion) {

        return "https://servicios.emtmadrid.es:8443//geo/servicegeo.asmx/getStopsLine?idClient=WEB.SERV.vlady-mix@hotmail.com&PassKey=2A2FEBD8-9481-4BE4-B343-06B3BB4B8D7E&line="+Linea+"&direction="+Direccion+"&statistics=''&cultureInfo=''";
    }

}
