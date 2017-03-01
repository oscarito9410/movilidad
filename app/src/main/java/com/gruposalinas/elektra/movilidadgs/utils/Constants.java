package com.gruposalinas.elektra.movilidadgs.utils;

public class Constants {
	//Date Parsing
	public static final String DATE_FORMAT	= "dd/MM/yyyy HH:mm:ss";
	public static final String DAY_FORMAT	= "dd/MM/yyyy";
	public static final String HOUR_FORMAT	= "HH:mm:ss";
	public static final String DATE_FORMAT1	= "dd/MM/yyyy HH:mm";
	public static final String HOUR_FORMAT1	= "HH:mm";

	//Webservices
  public static final String DOMAIN_URL 		 	= "https://10.51.118.170/ServicioEmpleados.svc";
	//public  static final String DOMAIN_URL          ="http://auditoria01.socio.gs/movilidad_serv/ServicioEmpleados.svc";
	//public static final String DOMAIN_URL 		 	= "https://200.38.122.208/ServicioEmpleados.svc";
	//public static final String DOMAIN_URL 		 	= "https://sociomas.com.mx/ServicioEmpleados.svc";
	public static final String CHECK_EMPLOYEE_WS 	= "ObtenerEmpleado";
	public static final String GET_SETTINGS_WS 		= "ObtenerConfiguracionesTimeString";
	//public static final String REGISTER_EMPLOYEE_WS = "RegistrarEmpleado";
	public static final String REGISTER_MOVEMENT_WS = "RegistrarEmpleadoMovimientos_UTC";
	public  static final String AGREGAR_JUSTIFICACION="AgregarJustificacion";
    public static final String AsignarZonaAUsuario="AsignarZonaAUsuario";
	public static final String CatalogoTiposJustificantes="CatalogoTiposJustificantes";
	public  static final String validarcontrasenia="validarcontrasenia";
	public static final String EliminarUbicacion="EliminarUbicacion";
	//public static final String obtenerhorarioempleado="obtenerhorarioempleado";
	public static final String ELiminar_horario="EditarEmpleadoHorarioServicio";
	public static final String consultarposicionesporempleado="consultarposicionesporempleado";
	public static final String ConsultarDetalleZona="ConsultarDetalleZona";
	public  static final String EliminarZonaAUsuario="EliminarZonaAUsuario";
	public static final String RegistrarPropuestaUbicacion="RegistrarPropuestaUbicacion";
	public static final String EnvioAlerta="EnvioAlerta";
	public static final String escribirlog="escribirlog";
	public static final String HistorialJustificacion="HistorialJustificacion";
	public static final String registrarempleadoprop="registrarempleadoprop";
	public  static final String ListadoEmpleadosConIncidencias="ListadoEmpleadosConIncidencias";
	public static final String ListadoHorariosEmpleadoPlantilla="ListadoHorariosEmpleadoPlantilla";
	public static final String ConsultarPosiciones="ConsultarPosiciones";
	public static final String ObtenerArchivosAdjuntos="ObtenerArchivosAdjuntos";
	public static final String ObtenerConfiguracionesAplicacion="ObtenerConfiguracionesAplicacion";
	public static final String ObtenerEstatusAlerta="ObtenerEstatusAlerta";
	public static final String ObtenerCatalogoZonas="ObtenerCatalogoZonas";
	public static final String RechazarJustificante="RechazarJustificante";
	public static final String RegistrarEventoTelefono="RegistrarEventoTelefono";
	public static final String ValidarJustificante="ValidarJustificante";
	public static final String ValidarRechazarHorarioEmpleado="ValidarRechazarHorarioEmpleado";
	//public  static final String AgregarEmpleado_ArchivoClipMultimedia="AgregarEmpleado_ArchivoClipMultimedia";
	public  static final String AgregarEmpleado_ArchivoClipMultimedia="ArchivoClipMultimedia";
	public static  final String InsertaTelefonoContacto="InsertaTelefonoContacto";

	public static final String ConsultarExclusionesEmpleados="ConsultarExclusionesEmpleados";
	public static final String SolicitarExclusion="SolicitarExclusion";
	public static final String ObtenerddlPermiso="ObtenerddlPermiso";
	public static final String RegistraActualizaEmpleadoExclusion="RegistraActualizaEmpleadoExclusion";
	public static final String InicioRegistroEmpleado="InicioRegistroEmpleado";
	public static final String ListadoEmpleadosUbicaciones="ListadoEmpleadosUbicaciones";
	public static final String AceptarRechazarPropuestaUbicacion="AceptarRechazarPropuestaUbicacion";
	public static final String AceptarRechazarZonas="AceptarRechazarZonas";

	//SharedPreferences
	public static final String SHARED_PREFERENCES	= "spgs";
	public static final String SP_NAME 				= "name";
	public static final String SP_ENTERPRISE 		= "company";
	public static final String SP_POSITION 			= "position";
	public static final String SP_HASLOGGED 		= "is_logged";
	public static final String SP_LOGGED 			= "1";
	public static final String SP_NOTLOGGED 		= "0";
	public static final String SP_ID 				= "id";
	public static final String SP_UPDATE_DATE       = "update_date";
	public static final String SP_DATE_LAST         ="date";
	
	//SharedPreferences para la configuracion de localización
	public static final String SP_TIME              = "conf_time";
	public static final String SP_RANGE             = "conf_range";
	public static final String SP_DISTANCE          = "conf_distance";
	public static final String NUMERO_JEFE          = "";
	// sharePreferences para Eventos dentro del telefono//
	public  static final String EVENTO                 ="0";
	//EmployeeLocationService
	public static final int 	LOCATION_INTERVAL = 1000;
	public static final float 	LOCATION_DISTANCE = 50f;
	
	//LoginActivity
	public static final String EMPLEADO = "EMPLEADO";
	
	//CheckEmployeesWS 
	public static final String ERROR = "error";
	
	//MainActivity
	public static final String APAGADO 		= "APAGADO";
	public static final String NINGUNO 		= "(NINGUNA)";
	public static final String WIFI    		= "WI-FI";
	public static final String ENCENDIDO	= "Activo";
	public static final String DATA_3G		= "3G";

	// MenuAplicaciones//
	public static final int tiempo         =10000;
	public static  final int TIEMPO_INTERVALO=1000;

	//EVENTOS//

	// parte de eventos
	public  static final int MODO_AVION=1;
	public  static  final int  APAGAR_GPS=2;
	public  static  final int APAGAR_DATOS=3;
	public  static  final int APAGAR_TELEFONO=4;
	public  static final String FECHA_EVENTO="";
	public  static  final int CAMBIO_ZONA=5;
	public static  final int CAMBIO_HORA=6;
	public  static  final int CAMBIO_FECHA=7;
	public static final int PRENDERTElEFONO=5;
	public static final int PRENDERGPS=6;
	public  static final int APAGARMODOAVION=9;
	public  static final String hora="HORA";
	public  static  final String minuto="minuto";
	public static final String tel1="telefono1";
	public static final String tel2="telefono2";
	public static final String tel3="telefono3";
	public static  final String provider="provider";
	/// eventos para la base de datos//
	public static final String PROVIDER="provider";


}