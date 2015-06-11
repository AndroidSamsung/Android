package samsung.ejemplos.basicwebservice;

import android.app.ProgressDialog;

public class DialogoConexion {
	private static ProgressDialog pDialog;
	
	static {
 		pDialog = new ProgressDialog(MainActivity.contexto);
		pDialog.setMessage(MainActivity.contexto.getString(R.string.infoDialogo));
		pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);		
	}
	
	public static void verDialogo(){
		pDialog.show();			
	}

	public static void ocultarDialogo(){
		if(pDialog!=null) pDialog.dismiss();			
	}

}
