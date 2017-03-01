package com.gruposalinas.elektra.movilidadgs.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "gps_status")
public class RegistroGPS extends BaseBean{
	public static final String ID           = "id";
	public static final String DB_NAME  	= "gps_status";
	public static final String LATITUD 		= "latitud";
	public static final String LONGITUD		= "longitud";
	public static final String NUM_EMPELADO = "num_empleado";
	public static final String FECHA 		= "fecha";
	public static final String HORA 		= "hora";
	public static final String BATERIA 		= "bateria";
	public static final String SUCCESS 		= "success";
	public static final String JSONDATE     = "jsondate";
	public static final String PROVIDER     ="provider";
	@DatabaseField(id = true, columnName = ID)
	private String id;
    @DatabaseField(columnName = LATITUD)
    private double latitud;
    @DatabaseField(columnName = LONGITUD)
    private double longitud;
    @DatabaseField(columnName = NUM_EMPELADO)
    private String numEmpleado;
    @DatabaseField(columnName = FECHA)
    private String fecha;
    @DatabaseField(columnName = HORA)
    private String hora;
    @DatabaseField(columnName = BATERIA)
    private double bateria;
    @DatabaseField(columnName = SUCCESS)
    private boolean success;
    @DatabaseField(columnName = JSONDATE)
    private String jsonDate;

	@DatabaseField(columnName = PROVIDER)
	private int Provider;


	private String token;

	//Constructor vacio
    public RegistroGPS(){
    	
    }
    
    //Para mostrar datos en la lista de registros
    public RegistroGPS(double latitud, double longitud, String fecha, String hora, String id) {
		super();
		this.latitud 	= latitud;
		this.longitud 	= longitud;
		this.fecha 		= fecha;
		this.hora 		= hora;
		this.id 		= id;
	}
    
    //Para guardar en webservice
	public RegistroGPS(double latitud, double longitud, String numEmpleado, String fecha, String hora, double bateria, String jsonDate, String id,int provider) {
		super();
		this.latitud 		= latitud;
		this.longitud 		= longitud;
		this.numEmpleado	= numEmpleado;
		this.fecha 			= fecha;
		this.hora 			= hora;
		this.bateria 		= bateria;
		this.jsonDate 		= jsonDate;
		this.id       		= id;
		this.Provider=provider;

	}

	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public String getNumEmpleado() {
		return numEmpleado;
	}
	public void setNumEmpleado(String numEmpleado) {
		this.numEmpleado = numEmpleado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public double getBateria() {
		return bateria;
	}
	public void setBateria(double bateria) {
		this.bateria = bateria;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getJsonDate() {
		return jsonDate;
	}

	public void setJsonDate(String jsonDate) {
		this.jsonDate = jsonDate;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getProvider() {
		return Provider;
	}

	public void setProvider(int provider) {
		Provider = provider;
	}
}