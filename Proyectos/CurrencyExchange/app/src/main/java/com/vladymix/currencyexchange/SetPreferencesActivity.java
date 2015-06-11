package com.vladymix.currencyexchange;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Fabricio on 01/06/2015.
 */
public class SetPreferencesActivity extends PreferenceActivity {

    public static final String NUMERO_DECIMALES ="decimal_digits_preference";
    public static final String UPDATE_AUTOMATIC ="startupdate";

    public static final String START_WITH_CURRENCY ="startfavorite";
    public static final String CURRENCY_FAVORITE ="favoritecurrency";

    public static final String LAST_UPDATE ="ultima_actualizacion";
    public static final String DATE_LAST_UPDATE ="fecha_ultima_actualizacion";

    public static final String FIRST_LOAD="primerainicializacion";

    public static final String NOMBRE ="pais";
    public static final String CURRENCY ="moneda";
    public static final String RATIO ="valuecurrency";
    public static final String NAME_CURRENCY ="namecurrency";
    public static final String IDFLAG ="idflag";
    public static final String IDFLAGCIRCLE ="idflagcircle";





    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(FINALVALUE.NAME_FILE_SETTINGS);
        addPreferencesFromResource(R.xml.preferencias);
    }

}

