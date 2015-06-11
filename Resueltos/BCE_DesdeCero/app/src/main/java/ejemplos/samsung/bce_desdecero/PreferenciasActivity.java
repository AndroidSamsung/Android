package ejemplos.samsung.bce_desdecero;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenciasActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName("divisas.xml");
        addPreferencesFromResource(R.xml.preferencias);
    }
}
