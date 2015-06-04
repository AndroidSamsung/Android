package com.vladymix.currencyexchange;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Fabricio on 01/06/2015.
 */
public class SetPreferencesActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName("settings");
        addPreferencesFromResource(R.xml.preferencias);
    }

}
