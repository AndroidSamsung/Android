package samsung.ejemplos.webservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Ficha implements Parcelable{
	
	private String DNI;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String direccion;
	private String equipo;

	public Ficha() {
		this.DNI = "";
		this.nombre = "";
		this.apellidos = "";
		this.telefono = "";
		this.direccion = "";
		this.equipo = "";
	}

	public Ficha(String DNI, String nombre, String apellidos,
		         String telefono, String direccion, String equipo) {
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.direccion = direccion;
		this.equipo = equipo;
	}

	public Ficha(JSONObject objeto) throws JSONException {
		this.DNI = objeto.getString("DNI");
		this.nombre = objeto.getString("Nombre");
		this.apellidos = objeto.getString("Apellidos");
		this.telefono = objeto.getString("Telefono");
		this.direccion = objeto.getString("Direccion");
		this.equipo = objeto.getString("Equipo");
	}

	public Ficha(Parcel in) {
		readFromParcel(in);
	}

	public void setDNI(String DNI){
		this.DNI = DNI;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setApellidos(String apellidos){
		this.apellidos = apellidos;
	}
	
	public void setDireccion(String direccion){
		this.direccion = direccion;
	}
	
	public void setTelefono(String telefono){
		this.telefono = telefono;
	}
	
	public void setEquipo(String equipo){
		this.equipo = equipo;
	}
	
	public String getDNI(){
		return DNI;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getApellidos(){
		return apellidos;
	}
	
	public String getDireccion(){
		return direccion;
	}
	
	public String getTelefono(){
		return telefono;
	}
	
	public String getEquipo(){
		return equipo;
	}
	
	public String toJSONString() throws JSONException{
		JSONObject objeto = new JSONObject();
		objeto.put("DNI", getDNI());
		objeto.put("Nombre", getNombre());
		objeto.put("Apellidos", getApellidos());
		objeto.put("Direccion", getDireccion());
		objeto.put("Telefono", getTelefono());
		objeto.put("Equipo", getEquipo());
		return objeto.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.DNI);
		dest.writeString(this.nombre);
		dest.writeString(this.apellidos);
		dest.writeString(this.direccion);
		dest.writeString(this.telefono);
		dest.writeString(this.equipo);
	}
	
	private void readFromParcel(Parcel in) {
		this.DNI = in.readString();
		this.nombre = in.readString();
		this.apellidos = in.readString();
		this.direccion = in.readString();
		this.telefono = in.readString();
		this.equipo = in.readString();
	}
	
	public static final Parcelable.Creator<Ficha> CREATOR = new Parcelable.Creator<Ficha>() {

		@Override
		public Ficha createFromParcel(Parcel in) {
			return new Ficha(in);
		}

		@Override
		public Ficha[] newArray(int size) {
			return new Ficha[size];
		}
	};

}
