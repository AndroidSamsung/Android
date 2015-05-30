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
    public static int getPositionPaisbyCurecny(String currency){
        int i=0;
        if(ListaPaises!=null){
            for(Pais item : ListaPaises){
                if(item.getNameCurrency().equals(currency.toUpperCase()))
                    return i;
                i++;
            }
        }
        else return -1;
        return -1;
    }

    public static void loadlista(){
        ListaPaises = new ArrayList<Pais>();
        ListaPaises.add(new Pais("Pais","USD",1.1298,"Moneda", R.drawable.flag_usd, R.drawable.circle_usd));
        ListaPaises.add(new Pais("Pais","JPY",134.50,"Moneda", R.drawable.flag_jpy, R.drawable.circle_jpy));
        ListaPaises.add(new Pais("Pais","BGN",1.9558,"Moneda", R.drawable.flag_bgn, R.drawable.circle_bgn));
        ListaPaises.add(new Pais("Pais","CZK",27.444,"Moneda", R.drawable.flag_czk, R.drawable.circle_czk));
        ListaPaises.add(new Pais("Pais","DKK",7.4637,"Moneda", R.drawable.flag_dkk, R.drawable.circle_dkk));
        ListaPaises.add(new Pais("Pais","GBP",0.73520,"Moneda", R.drawable.flag_gbp, R.drawable.circle_gbp));
        ListaPaises.add(new Pais("Pais","HUF",305.09,"Moneda", R.drawable.flag_huf, R.drawable.circle_huf));
        ListaPaises.add(new Pais("Pais","PLN",4.1746,"Moneda", R.drawable.flag_pln, R.drawable.circle_pln));
        ListaPaises.add(new Pais("Pais","RON",4.4483,"Moneda", R.drawable.flag_ron, R.drawable.circle_ron));
        ListaPaises.add(new Pais("Pais","SEK",9.5656,"Moneda", R.drawable.flag_sek, R.drawable.circle_sek));
        ListaPaises.add(new Pais("Pais","CHF",1.0727,"Moneda", R.drawable.flag_chf, R.drawable.circle_chf));
        ListaPaises.add(new Pais("Pais","NOK",8.6405,"Moneda", R.drawable.flag_nok, R.drawable.circle_nok));
        ListaPaises.add(new Pais("Pais","HRK",7.7115,"Moneda", R.drawable.flag_hrk, R.drawable.circle_hrk));
        ListaPaises.add(new Pais("Pais","RUB",72.5628,"Moneda", R.drawable.flag_rub, R.drawable.circle_rub));
        ListaPaises.add(new Pais("Pais","TRY",2.8037,"Moneda", R.drawable.flag_try, R.drawable.circle_try));
        ListaPaises.add(new Pais("Pais","AUD",1.4510,"Moneda", R.drawable.flag_aud, R.drawable.circle_aud));
        ListaPaises.add(new Pais("Pais","BRL",3.2729,"Moneda", R.drawable.flag_brl, R.drawable.circle_brl));
        ListaPaises.add(new Pais("Pais","CAD",1.4256,"Moneda", R.drawable.flag_cad, R.drawable.circle_cad));
        ListaPaises.add(new Pais("Pais","CNY",7.0686,"Moneda", R.drawable.flag_cny, R.drawable.circle_cny));
        ListaPaises.add(new Pais("Pais","HKD",8.7631,"Moneda", R.drawable.flag_hkd, R.drawable.circle_hkd));
        ListaPaises.add(new Pais("Pais","IDR",14607.09,"Moneda", R.drawable.flag_idr, R.drawable.circle_idr));
        ListaPaises.add(new Pais("Pais","ILS",4.3602,"Moneda", R.drawable.flag_ils, R.drawable.circle_ils));
        ListaPaises.add(new Pais("Pais","INR",70.4035,"Moneda", R.drawable.flag_inr, R.drawable.circle_inr));
        ListaPaises.add(new Pais("Pais","KRW",1253.43,"Moneda", R.drawable.flag_krw, R.drawable.circle_krw));
        ListaPaises.add(new Pais("Pais","MXN",17.0685,"Moneda", R.drawable.flag_mxn, R.drawable.circle_mxn));
        ListaPaises.add(new Pais("Pais","MYR",4.1093,"Moneda", R.drawable.flag_myr, R.drawable.circle_myr));
        ListaPaises.add(new Pais("Pais","NZD",1.5041,"Moneda", R.drawable.flag_nzd, R.drawable.circle_nzd));
        ListaPaises.add(new Pais("Pais","PHP",50.063,"Moneda", R.drawable.flag_php, R.drawable.circle_php));
        ListaPaises.add(new Pais("Pais","SGD",1.5384,"Moneda", R.drawable.flag_sgd, R.drawable.circle_sgd));
        ListaPaises.add(new Pais("Pais","THB",36.786,"Moneda", R.drawable.flag_thb, R.drawable.circle_thb));
        ListaPaises.add(new Pais("Pais","ZAR",13.2311,"Moneda", R.drawable.flag_zar, R.drawable.circle_zar));
    }
}
