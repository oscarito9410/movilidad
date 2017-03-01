package com.gruposalinas.elektra.movilidadgs.geolocation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gruposalinas.elektra.movilidadgs.Activities.MainActivity;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.alertar.notificaciones;
import com.gruposalinas.elektra.movilidadgs.async.RegisterMovementAsync;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.receivers.incidenciasreciver;
import com.gruposalinas.elektra.movilidadgs.receivers.notificacion_app;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;


public class EmployeeLocationService  extends Service{
	private static final String TAG = "EMPLOYEE_LOCATION_SERVICE";
	private DBHelper mDBHelper;
	private Date date;
	private String dateString;
	private String hourString;
	boolean byTimer = false;
	int tama=0;
	AlarmManager alarmManager, AlarmaMad;
	Intent alarmIntent,pa;
	PendingIntent pendingIntent,g;
	notificaciones notificaciones;
	@Override
	public IBinder onBind(Intent arg0) {
    // 	TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onCreate() {
    // 	TODO Auto-generated method stub
		super.onCreate();
		PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
		//wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
		PowerManager.WakeLock wakeLock1=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DonotSlepp");

		wakeLock1.acquire();

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent alarmIntent = new Intent(this, EmployeeLocationService.class);

		PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);


		alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 900000L, pendingIntent);

		// parte para notificacion//

		Log.i(TAG, "1- Service Created");

		//startService(new Intent(this, MyService.class));

		wakeLock1.release();

	}

	@SuppressLint("LongLogTag")
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		Log.i(TAG, "2- onStart");

		//Realiza la geolocalización
		iniciarServicio();
		// notificacion de version
		notificacionAlarma();
		// alarma aleatoria//
		alarma3am();
		//alarma incidencias;
		//alarmaincidencias();


	}




	@SuppressLint("LongLogTag")
	public void enviarPendientes(){
		Log.i(TAG, "3- EnviarPendientes");

		Dao dao;
        DBHelper dbHelper = null;
        ArrayList<RegistroGPS> registrosList = new ArrayList<RegistroGPS>();
        try {
        	String noEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
    		dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getGpsDAO();

    		QueryBuilder queryBuilder = dao.queryBuilder();
    	    queryBuilder.setWhere(queryBuilder.where().eq(RegistroGPS.NUM_EMPELADO, noEmpleado)
    	    					 .and().eq(RegistroGPS.SUCCESS, false));

    		List<RegistroGPS> registros = dao.query(queryBuilder.prepare());
    		if (registros.isEmpty())
			{
    			Log.d(TAG, "4- No se encontraron registros sin enviar con id = " + noEmpleado);
    		} else {
    		    Log.d(TAG, "5- Recuperado " + registros.size() + " registros con id = " + registros.get(0).getId() + "\nFecha = " + registros.get(0).getFecha() + " " + registros.get(0).getHora() + "\nGeoloc = " + registros.get(0).getLatitud() + "|" + registros.get(0).getLongitud());
    		}

    		for(int i = 0; i < registros.size(); i++){
    			RegisterMovementAsync registerMovementAsync = new RegisterMovementAsync(this);
    			registerMovementAsync.execute(registros.get(i));

    		    Log.i(TAG, "6- Se ha mandado un registro: " + registros.get(i).getId() + "/" + registros.get(i).isSuccess() + "=>" + registros.get(i).getFecha() + " " + registros.get(i).getHora());
    		}
        } catch (SQLException e) {
    	    Log.e(TAG, "7- Error creando lista de registros");
    	}
	}

	@SuppressLint("LongLogTag")
	public int datos_noEnviados(){
		Dao dao;
		DBHelper dbHelper = null;
		try {
			String noEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
			dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getGpsDAO();

			QueryBuilder queryBuilder = dao.queryBuilder();
			queryBuilder.setWhere(queryBuilder.where().eq(RegistroGPS.NUM_EMPELADO, noEmpleado)
					.and().eq(RegistroGPS.SUCCESS, false));

			List<RegistroGPS> registros = dao.query(queryBuilder.prepare());
			tama=registros.size();



		} catch (SQLException e)
		{
			Log.d("no se obtienen el tamaño", tama + "");
		}

		return tama;
	}



	@SuppressLint("LongLogTag")
	@Override
	public void onDestroy()
	{
    // 	TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "8- onDestroy");
		 notificaciones= new notificaciones();
		notificaciones.Iniciarserv(this, getString(R.string.titleservice), getString(R.string.servicioArrancar), new MainActivity());

		 if (mDBHelper != null) {
	            OpenHelperManager.releaseHelper();
	            mDBHelper = null;
	    }
	}


	@SuppressLint("LongLogTag")
	public void iniciarServicio(){
		//	---
		// Obteniendo ubicacion actual
	//	GPSTracker gps = new GPSTracker(this);
		Intent i =new Intent(getApplicationContext(),GPS_Service.class);
		startService(i);
		Log.i(TAG, "9- iniciarServicio");
		//Si lo hizo porque pasó el tiempo, guardar
		if(byTimer)
		{
			Log.i(TAG, "12- byTimer");
		//	muestraPosicionActual(gps);
			//startService(new Intent(this,MyService.class));

		}

		//SHARED
		else{
		//	muestraPosicionActual(gps);


		}

		byTimer = false;

		//Envía los registros que aún no han podido guardarse en servidor

		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		//	For WiFi Check
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();

		if(isWifi)
		{
			enviarPendientes();

		}

		if(datos_noEnviados()>48)
		{
			enviarPendientes();

		}
		else
		{
			Log.i(TAG, "checar- numero faltante de datos por enviar: "+datos_noEnviados());


		}

	}

	@SuppressLint("LongLogTag")
	public void muestraPosicionActual(GPSTracker _location){
		Log.i(TAG, "17- muestraPosicionActual");


		Log.i(TAG, "18- Latitude = " + _location.getLatitud() + "\nLongitud = " + _location.getLongitud());
		date = new Date();
		@SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
		dateFormatter.setLenient(false);

		@SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
		timeFormatter.setLenient(false);

		dateString = dateFormatter.format(date);
		hourString = timeFormatter.format(date);


		Log.i(TAG, "19- Date = " + dateString + " " + hourString);

		RegistroGPS registroGps = new RegistroGPS(_location.getLatitud(), _location.getLongitud(), getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""),
				  dateString,
				  hourString,
				  Utils.getBatteryLevel(this),
				  Utils.getJsonDate(date),
				  Utils.generateMovementId(dateString, hourString),_location.getProvider());
		registroGps.setJsonDate(generateJsonDate(registroGps));

		//Invocar Webservice para almacenar la posicion
		//RegisterMovementAsync registerMovementAsync = new RegisterMovementAsync(this);
		//registerMovementAsync.execute(registroGps);
		///
		//ultimo_registro(registroGps);

		//
		onPositionSaved(registroGps);


	}


	@SuppressLint("LongLogTag")
	public void onPositionSaved(RegistroGPS result){
		Log.i(TAG, "20- onPositionSaved");
		Log.d(TAG, "20.1 - Registro con id = " + result.getId() + "\nFecha = " + result.getFecha() + " " + result.getHora() + "\nGeoloc = " + result.getLatitud() + "|" + result.getLongitud());
		Dao dao;

		try {
			if(result.isSuccess()){
				Log.i(TAG, "21- Se ha mandado la ubicación exitosamente: " + result.getId());
			}
			else{
				Log.i(TAG, "22- No se ha mandado la ubicación. Guardando en cola. Hora = " + result.getFecha() + " " + result.getHora());
				Log.i(TAG, "22.1- jsonDate = " + result.getJsonDate());
			}

			boolean isNew = true;

			//TODO revisar primero si ya existe el ID, para crearlo o actualizarlo
			//TODO compara el registro con los de BD
			DBHelper dbHelper = null;
			List<RegistroGPS> registrosTemp = null;

			dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getGpsDAO();


			try {
				Log.i(TAG, "23-Test probando buscar registro con ID = " + result.getId());
				QueryBuilder queryBuilder = dao.queryBuilder();
	    	    queryBuilder.setWhere(queryBuilder.where().eq(RegistroGPS.ID, result.getId()));
			    registrosTemp = dao.query(queryBuilder.prepare());

				Log.d(TAG, "24.1- ID = " + registrosTemp.get(0).getId() + "/" + registrosTemp.get(0).isSuccess());
			    Log.e(TAG, "Result id = " + result.getId());
			    Log.e(TAG, "registroTemp id = " + registrosTemp.get(0).getId());
			    if (registrosTemp == null){
			    	Log.i(TAG, "25- No se ha encontrado nada");
			    }
			    else if(registrosTemp.size() == 0 && !registrosTemp.get(0).getId().equals(result.getId())) {
			        Log.i(TAG, "26- Ningún registro con id = " + result.getId());
			    }
				else {
			        Log.i(TAG, "27- Recuperado registro con id = " + registrosTemp.get(0).getId());
			        isNew = false;
			    }

			}catch(Exception e){
				Log.e(TAG, "28- No se hizo la búsqueda el registro");
			}

			if(isNew){
				Log.i(TAG, "29- Se creará la coordenada: " + result.getJsonDate());
				dao.create(result);
				Log.i(TAG, "30- Coordenada creado exitosamente");
			}
			else{
				if(result.isSuccess()){
					Log.i(TAG, "31- Se actualizará la coordenada: " + result.getJsonDate());
					dao.update(result);

					Log.i(TAG, "32- Coordenada actualizada exitosamente");
				}
				else{
					Log.i(TAG, "33- Falló el reintento de mandar la solicitud");
				}
			}

		} catch (SQLException e) {
			Log.e(TAG, "33- Error creando coordenada");
		}
	}

	@SuppressLint("LongLogTag")
	private String generateJsonDate(RegistroGPS registro){
		//Setting jsonDate
		String jsonDate = "";
		@SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date date = new Date();
		try {
			date = formatter.parse(registro.getFecha() + " " + registro.getHora());
		} catch (java.text.ParseException e) {
			Log.e(TAG, "Error al parsear fecha");
		}
	jsonDate = Utils.getJsonDate(date);
		registro.setJsonDate(jsonDate);

		return jsonDate;
	}


	private void notificacionAlarma(){
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		alarmIntent = new Intent(this, notificacion_app.class);

		pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
		Calendar alarmStartTime = Calendar.getInstance();

		int hora=getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getInt(Constants.hora, 0);
		int min=getSharedPreferences(Constants.SHARED_PREFERENCES,Context.MODE_PRIVATE).getInt(Constants.minuto, 0);


		alarmStartTime.set(Calendar.HOUR_OF_DAY, hora);

		alarmStartTime.set(Calendar.MINUTE, min);

		alarmStartTime.set(Calendar.SECOND, 0);

		alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

	}

	private void alarma3am(){

		int numeroAleatorio = (int) (Math.random()*3+1);
		AlarmaMad = (AlarmManager) getSystemService(ALARM_SERVICE);

		pa = new Intent(this, notificacion_app.class);

		g = PendingIntent.getBroadcast(this, 0, pa, 0);
		Calendar alarmStartTime = Calendar.getInstance();

		alarmStartTime.set(Calendar.HOUR_OF_DAY, numeroAleatorio);

		alarmStartTime.set(Calendar.MINUTE, 0);

		alarmStartTime.set(Calendar.SECOND, 0);

		AlarmaMad.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, g);

	}


	public void alarmaincidencias()
	{

		int numeroAleatorio = (int) (Math.random()*12+1);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent alarmIntent = new Intent(this, incidenciasreciver.class);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
		Calendar alarmStartTime = Calendar.getInstance();

		alarmStartTime.set(Calendar.HOUR_OF_DAY, numeroAleatorio);

		alarmStartTime.set(Calendar.MINUTE, 15);

		alarmStartTime.set(Calendar.SECOND, 0);

		alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

	}


}