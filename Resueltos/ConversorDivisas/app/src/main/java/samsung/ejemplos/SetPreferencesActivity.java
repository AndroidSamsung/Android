package samsung.ejemplos;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SetPreferencesActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName("settings");
        addPreferencesFromResource(R.xml.preferencias);
    }
}
