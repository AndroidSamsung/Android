package samsung.ejemplos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.CheckBox;
import android.widget.TextView;

public class Menu3Activity extends Activity {
	
	private static final int MNU_OPC1 = 1;
	private static final int MNU_OPC2 = 2;
	private static final int MNU_OPC3 = 3;
	private static final int MNU_OPC4 = 4;
	
	private static final int SMNU_OPC1 = 31;
	private static final int SMNU_OPC2 = 32;
	
	private static final int GRUPO_MENU_1 = 101;
	
	private TextView lblMensaje;
	private CheckBox chkMenuExtendido;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_3_layout);
		
		lblMensaje = (TextView)findViewById(R.id.LblMensaje);
        chkMenuExtendido = (CheckBox)findViewById(R.id.ChkMenuExtendido);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Opcion1")
                .setIcon(android.R.drawable.ic_menu_preferences);
        menu.add(Menu.NONE, MNU_OPC2, Menu.NONE, "Opcion2")
                .setIcon(android.R.drawable.ic_menu_compass);

        SubMenu smnu = menu.addSubMenu(Menu.NONE, MNU_OPC3, Menu.NONE, "Opcion3")
                .setIcon(android.R.drawable.ic_menu_agenda);
        smnu.add(GRUPO_MENU_1, SMNU_OPC1, Menu.NONE, "Opcion 3.1");
        smnu.add(GRUPO_MENU_1, SMNU_OPC2, Menu.NONE, "Opcion 3.2");

        smnu.setGroupCheckable(GRUPO_MENU_1, true, true);
		return true;
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(chkMenuExtendido.isChecked()) {
            if(menu.findItem(MNU_OPC4)==null){
                menu.add(Menu.NONE, MNU_OPC4, Menu.NONE, "Opcion4")
                        .setIcon(android.R.drawable.ic_menu_camera);
            }
        } else {
            menu.removeItem(MNU_OPC4);
        }
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MNU_OPC1:
            lblMensaje.setText("Opcion 1 pulsada!");
            return true;
        case MNU_OPC2:
        	lblMensaje.setText("Opcion 2 pulsada!");
            return true;
        case MNU_OPC3:
        	lblMensaje.setText("Opcion 3 pulsada!");
            return true;
        case SMNU_OPC1:
        	lblMensaje.setText("Opcion 3.1 pulsada!");
        	item.setChecked(true);
            return true;
        case SMNU_OPC2:
        	lblMensaje.setText("Opcion 3.2 pulsada!");
        	item.setChecked(true);
            return true;
        case MNU_OPC4:
        	lblMensaje.setText("Opcion 4 pulsada!");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
