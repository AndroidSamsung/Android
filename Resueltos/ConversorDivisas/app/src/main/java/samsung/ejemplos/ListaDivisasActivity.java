package samsung.ejemplos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ListaDivisasActivity extends ListActivity {

	private String[][] datos; 	
	private ArrayList<String[]> rateInfoData;
	private ArrayList<RateInfo> rateInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		datos = new String[][]{
				{"USD", "1.2701"},
				{"JPY", "138.97"},
				{"BGN", "1.9558"},
				{"BRL", "3.1431"},
		};

		rateInfoData = readRatesFileIntoArrayList();
		datos = rateInfoData.toArray(new String[rateInfoData.size()][2]);
		ArrayAdapter<String[]> adaptadorArrayDatos =
			new ArrayAdapter<String[]>(
					this, 
					R.layout.list_item, 
					R.id.moneda,
					datos) {
			  @Override
			  public View getView(int position, View convertView, ViewGroup parent) {
				  ViewHolder holder;
				  if (convertView == null) {
					  convertView = getLayoutInflater().inflate(R.layout.list_item, null);
					  holder = new ViewHolder();
					  holder.bandera = (ImageView) convertView.findViewById(R.id.bandera);
					  holder.moneda = (TextView) convertView.findViewById(R.id.moneda);
					  holder.ratio = (TextView) convertView.findViewById(R.id.ratio);
					  convertView.setTag(holder);
				  } else {
					  holder = (ViewHolder) convertView.getTag();
				  }

				  int flag_id = getResources().getIdentifier(datos[position][0].toLowerCase() + "_flag", "drawable", getPackageName());
				  holder.bandera.setImageResource(flag_id);
				  holder.bandera.setTag(flag_id);
				  holder.moneda.setText(datos[position][0]);
				  holder.ratio.setText(datos[position][1]);

				  return (convertView);
			  }
		}; 
			
		//rateInfoData = readRatesFileIntoArrayList();
		rateInfoData = readXMLRatesFileIntoArrayList();
		ArrayAdapter<String[]> adaptadorFicheroDatos = 
			new ArrayAdapter<String[]>(
					this, 
					R.layout.list_item, 
					R.id.moneda,
					rateInfoData) {
			  @Override
			  public View getView(int position, View convertView, ViewGroup parent) {
				  ViewHolder holder;
				  if (convertView == null) {
					  convertView = getLayoutInflater().inflate(R.layout.list_item, null);
					  holder = new ViewHolder();
					  holder.bandera = (ImageView) convertView.findViewById(R.id.bandera);
					  holder.moneda = (TextView) convertView.findViewById(R.id.moneda);
					  holder.ratio = (TextView) convertView.findViewById(R.id.ratio);
					  convertView.setTag(holder);
				  } else {
					  holder = (ViewHolder) convertView.getTag();
				  }

				  String[] listItem = rateInfoData.get(position);
				  int flag_id = getResources().getIdentifier(listItem[0].toLowerCase() + "_flag", "drawable", getPackageName());
				  holder.bandera.setImageResource(flag_id);
				  holder.bandera.setTag(flag_id);
				  holder.moneda.setText(listItem[0]);
				  holder.ratio.setText(listItem[1]);

				  return (convertView);
			  }
		}; 

		rateInfoList = readRatesFileIntoRateInfoList();
		ArrayAdapter<RateInfo> adaptadorFicheroDatos2 = 
			new ArrayAdapter<RateInfo>(
					this, 
					R.layout.list_item, 
					R.id.moneda,
					rateInfoList) {
			  @Override
			  public View getView(int position, View convertView, ViewGroup parent) {
				  ViewHolder holder;
				  if (convertView == null) {
					  convertView = getLayoutInflater().inflate(R.layout.list_item, null);
					  holder = new ViewHolder();
					  holder.bandera = (ImageView) convertView.findViewById(R.id.bandera);
					  holder.moneda = (TextView) convertView.findViewById(R.id.moneda);
					  holder.ratio = (TextView) convertView.findViewById(R.id.ratio);
					  convertView.setTag(holder);
				  } else {
					  holder = (ViewHolder) convertView.getTag();
				  }

				  RateInfo listItem = rateInfoList.get(position);
				  holder.bandera.setImageResource(listItem.getFlag());
				  holder.bandera.setTag(listItem.getFlag());
				  holder.moneda.setText(listItem.getCurrency());
				  holder.ratio.setText(String.valueOf(listItem.getRate()));

				  return (convertView);
			  }
		}; 

		setListAdapter(adaptadorFicheroDatos);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ViewHolder info = (ViewHolder)v.getTag();
		Intent i = new Intent();
		i.putExtra("moneda", info.moneda.getText().toString());
		i.putExtra("ratio", Double.valueOf(info.ratio.getText().toString()));
		i.putExtra("bandera", (Integer)info.bandera.getTag());

		setResult(RESULT_OK, i);
		finish();
	}

	private ArrayList<String[]> readRatesFileIntoArrayList(){
		final String TAG = " [readRatesFileIntoArrayList] ";
		final String FILE_NAME = "euroratesdata.dat";

		String linea;
		ArrayList<String[]> rates = new ArrayList<String[]>();		

		try {
			//fichero en almacenamiento interno del dispositivo
			//FileInputStream fis = this.openFileInput(FILE_NAME);
			//fichero en res/raw
			InputStream fis = getResources().openRawResource(R.raw.euroratesdata);
			DataInputStream dis = new DataInputStream(fis);
			InputStreamReader isr = new InputStreamReader(dis);
			BufferedReader br = new BufferedReader(isr);
			while ((linea = br.readLine()) != null) {
				rates.add(linea.split(";"));
			}
			br.close();
		} catch (IOException ioe) {
			Log.d(TAG, ioe.getMessage());	
		}
		return rates;
	}
	
	private ArrayList<RateInfo> readRatesFileIntoRateInfoList(){
		final String TAG = " [readRatesFileIntoRateInfoList] ";
		final String FILE_NAME = "euroratesdata.dat";

		String linea, data[];
		int flag_id;
		ArrayList<RateInfo> rates = new ArrayList<RateInfo>();		

		try {
			//fichero en almacenamiento interno del dispositivo
			//FileInputStream fis = this.openFileInput(FILE_NAME);
			//fichero en res/raw
			InputStream fis = getResources().openRawResource(R.raw.euroratesdata);
			DataInputStream dis = new DataInputStream(fis);
			InputStreamReader isr = new InputStreamReader(dis);
			BufferedReader br = new BufferedReader(isr);
			while ((linea = br.readLine()) != null) {
				data = linea.split(";");
 	        	flag_id = getResources().getIdentifier(data[0].toLowerCase(Locale.getDefault()) + "_flag", "drawable", getPackageName());
				rates.add(new RateInfo(data[0], Double.parseDouble(data[1]), flag_id));
			}
			br.close();
		} catch (IOException ioe) {
			Log.d(TAG, ioe.getMessage());	
		}
		return rates;
	}

	private ArrayList<String[]> readXMLRatesFileIntoArrayList(){
		final String TAG = " [readXMLRatesFileIntoArrayList] ";

		Document doc = null;
		try {
			InputStream source = getResources().openRawResource(R.raw.eurofxref);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(source);
		} catch (IOException e) {
			Log.d(TAG, e.getMessage());
		} catch (ParserConfigurationException e) {
			Log.d(TAG, e.getMessage());
		} catch (SAXException e) {
			Log.d(TAG, e.getMessage());
		}
		NodeList listaCube = doc.getElementsByTagName("Cube");
		ArrayList<String[]> listaMonedas = new ArrayList<String[]>();
		Element elemento;
		for(int i=2; i<listaCube.getLength(); i++) {
			elemento = (Element) listaCube.item(i);
			String[] monedaAUX = new String[2];
			monedaAUX[0] = elemento.getAttribute("currency");
			monedaAUX[1] = elemento.getAttribute("rate");
			listaMonedas.add(monedaAUX);
		}
		return listaMonedas;
	}

	private static class ViewHolder {
		TextView moneda;
		TextView ratio;
		ImageView bandera;
	}

}


