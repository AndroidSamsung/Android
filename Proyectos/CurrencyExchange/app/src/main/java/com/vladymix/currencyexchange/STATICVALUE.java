package com.vladymix.currencyexchange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabricio on 29/05/2015.
 */
public class STATICVALUE {
    public static List<Pais> ListaPaises;

    public static Pais getPaisbyName(String name){
        if(ListaPaises!=null){
            for(Pais item : ListaPaises){
                if(item.getNombre().equals(name))
                    return item;
             }
        }
        else return null;

        return null;
    }
    public static Pais getPaisbyCurrency(String currency){
        if(ListaPaises!=null){
            for(Pais item : ListaPaises){
                if(item.getNameCurrency().equals(currency))
                    return item;
            }
        }
        else return null;

        return null;
    }
    public static int getPositionPaisbyName(String name){
        int i=0;
        if(ListaPaises!=null){
            for(Pais item : ListaPaises){
                if(item.getNombre().equalsIgnoreCase(name))
                    return i;
                i++;
            }
        }
        else return -1;
        return -1;
    }
    public static int getPositionPaisbyCurrecny(String currency){
        int i=0;
        if(ListaPaises!=null){
            for(Pais item : ListaPaises){
                if(item.getCurrency().equals(currency.toUpperCase()))
                    return i;
                i++;
            }
        }
        else return -1;
        return -1;
    }

    public static void loadlista(){
        ListaPaises = new ArrayList<Pais>();
        ListaPaises.add(new Pais("Estados Unidos","USD",1.1298,"Dolar", R.drawable.flag_usd, R.drawable.circle_usd));
        ListaPaises.add(new Pais("Japon","JPY",134.50,"Yen", R.drawable.flag_jpy, R.drawable.circle_jpy));
        ListaPaises.add(new Pais("Bulgaria","BGN",1.9558,"Lev", R.drawable.flag_bgn, R.drawable.circle_bgn));
        ListaPaises.add(new Pais("Republica Checa","CZK",27.444,"Corona", R.drawable.flag_czk, R.drawable.circle_czk));
        ListaPaises.add(new Pais("Dinamarca","DKK",7.4637,"Corona", R.drawable.flag_dkk, R.drawable.circle_dkk));
        ListaPaises.add(new Pais("Reino Unido","GBP",0.73520,"Libra Esterlina", R.drawable.flag_gbp, R.drawable.circle_gbp));
        ListaPaises.add(new Pais("Hungria","HUF",305.09,"Forint", R.drawable.flag_huf, R.drawable.circle_huf));
        ListaPaises.add(new Pais("Polonia","PLN",4.1746,"Zloty", R.drawable.flag_pln, R.drawable.circle_pln));
        ListaPaises.add(new Pais("Rumania","RON",4.4483,"Nueva lei", R.drawable.flag_ron, R.drawable.circle_ron));
        ListaPaises.add(new Pais("Suecia","SEK",9.5656,"Corona", R.drawable.flag_sek, R.drawable.circle_sek));
        ListaPaises.add(new Pais("Suiza","CHF",1.0727,"Franco", R.drawable.flag_chf, R.drawable.circle_chf));
        ListaPaises.add(new Pais("Noruega","NOK",8.6405,"Corona", R.drawable.flag_nok, R.drawable.circle_nok));
        ListaPaises.add(new Pais("Croacia","HRK",7.7115,"kuna", R.drawable.flag_hrk, R.drawable.circle_hrk));
        ListaPaises.add(new Pais("Rusia","RUB",72.5628,"Rublo", R.drawable.flag_rub, R.drawable.circle_rub));
        ListaPaises.add(new Pais("Turquia","TRY",2.8037,"Lira", R.drawable.flag_try, R.drawable.circle_try));
        ListaPaises.add(new Pais("Australia","AUD",1.4510,"Dolar Autraliano", R.drawable.flag_aud, R.drawable.circle_aud));
        ListaPaises.add(new Pais("Brazil","BRL",3.2729,"Real", R.drawable.flag_brl, R.drawable.circle_brl));
        ListaPaises.add(new Pais("Canada","CAD",1.4256,"Dolar Canadience", R.drawable.flag_cad, R.drawable.circle_cad));
        ListaPaises.add(new Pais("China","CNY",7.0686,"Renmimbi Yuan", R.drawable.flag_cny, R.drawable.circle_cny));
        ListaPaises.add(new Pais("Hong Kong","HKD",8.7631,"Dolar", R.drawable.flag_hkd, R.drawable.circle_hkd));
        ListaPaises.add(new Pais("Indonesia","IDR",14607.09,"Rupia", R.drawable.flag_idr, R.drawable.circle_idr));
        ListaPaises.add(new Pais("Israel","ILS",4.3602,"Nuevo Shekel", R.drawable.flag_ils, R.drawable.circle_ils));
        ListaPaises.add(new Pais("India","INR",70.4035,"Rupia India", R.drawable.flag_inr, R.drawable.circle_inr));
        ListaPaises.add(new Pais("Corea del sur","KRW",1253.43,"Won", R.drawable.flag_krw, R.drawable.circle_krw));
        ListaPaises.add(new Pais("Mexico","MXN",17.0685,"Peso Mexicano", R.drawable.flag_mxn, R.drawable.circle_mxn));
        ListaPaises.add(new Pais("Malasia","MYR",4.1093,"Ringgit", R.drawable.flag_myr, R.drawable.circle_myr));
        ListaPaises.add(new Pais("Nueva Selanda","NZD",1.5041,"Dolar", R.drawable.flag_nzd, R.drawable.circle_nzd));
        ListaPaises.add(new Pais("Filipinas","PHP",50.063,"Peso", R.drawable.flag_php, R.drawable.circle_php));
        ListaPaises.add(new Pais("Singapur","SGD",1.5384,"Dolar", R.drawable.flag_sgd, R.drawable.circle_sgd));
        ListaPaises.add(new Pais("Tailandia","THB",36.786,"Baht", R.drawable.flag_thb, R.drawable.circle_thb));
        ListaPaises.add(new Pais("Surafrica","ZAR",13.2311,"Rand", R.drawable.flag_zar, R.drawable.circle_zar));
    }
}
