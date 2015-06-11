package samsung.ejemplos.basicwebservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ConsultaFichasReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(ConsultaFichasService.ACTION_FIN)) {
			Intent i = new Intent(context, ViewDataActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.putExtra("data", intent.getStringExtra("textoJSON"));
			context.startActivity(i);
		}
		if(intent.getAction().equals(ConsultaFichasService.ACTION_ERROR)) {
			Toast.makeText(context, context.getString(R.string.errorAPI), Toast.LENGTH_SHORT).show();
		}
		if(intent.getAction().equals(ConsultaFichasService.ACTION_NO_RECORD)) {
			Toast.makeText(context, context.getString(R.string.sinRegistros), Toast.LENGTH_SHORT).show();
		}
		DialogoConexion.ocultarDialogo();
	}

}
