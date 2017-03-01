/**
 * 
 */
package com.gruposalinas.elektra.movilidadgs.Activities;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.GetSettingsAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Configuracion;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.RangoMonitoreo;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.beans.Singleton;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.geolocation.HoraExacta;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.QueryBuilder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.provider.Settings;

import org.w3c.dom.Text;

/**
 * @author Eduardo Waring
 *
 */
public class MainActivity extends Activity{
	public static final String TAG = "MAIN_ACTIVITY";
	//Typeface myriadRegularTypeFace = Typeface.createFromAsset(getAssets(), "fonts/myriadproregular.ttf");
	//Typeface myriadBoldTypeFace = Typeface.createFromAsset(getAssets(), "fonts/myriadprobold.ttf");	

	
	private ImageView photoImage;
	private TextView nameText;
	private ImageButton moreButton,posiciones;

	private ImageView paginasweb;

	private DBHelper mDBHelper;
	
	private RelativeLayout progressBar;
	private ImageView boton_incidencia;
	ImageView justificar;
	ImageView permisos;

	DrawerLayout dLayout;
	ImageView panico,gps,wifi;
	TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
	ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
	LinearLayout contacto;



	@Override
	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_main);
		delete=(ImageView)findViewById(R.id.dlete);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dLayout.closeDrawer(GravityCompat.START);
			}
		});
		dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		LinearLayout menu_horario,menu_ubicacion,menu_panico,menu_incidencias;
		menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
		menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
		menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
		menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
		texto_incidencias=(TextView)findViewById(R.id.texto_menuIncidencias);
		texto_panico=(TextView)findViewById(R.id.texto_menupanico);
		panicoimas=(ImageView)findViewById(R.id.icono_panico_menu);
		inciendenciasimas=(ImageView)findViewById(R.id.icono_incidenciasmenu);
		reloj=(ImageView)findViewById(R.id.reloj);
		tocar=(ImageView)findViewById(R.id.tocar);
		texto_menuUbicacion=(TextView)findViewById(R.id.texto_menuUbicacion);
		texto_menuHorario=(TextView)findViewById(R.id.texto_menuHorario);
		contacto=(LinearLayout)findViewById(R.id.contacto);
		justificar=(ImageView)findViewById(R.id.justificar);
		permisos=(ImageView)findViewById(R.id.permisos);

		contacto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent= new Intent(MainActivity.this,Contacto.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		LinearLayout pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
		pendientes_zonas_horarios_menu.setFitsSystemWindows(true);
		pendientes_zonas_horarios_menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


			}
		});

		progressBar = (RelativeLayout) findViewById(R.id.main_progressbar);
		moreButton=(ImageButton)findViewById(R.id.horarios);
		posiciones=(ImageButton)findViewById(R.id.tiendas);
		boton_incidencia=(ImageView)findViewById(R.id.incidenciasboton);
		paginasweb=(ImageView)findViewById(R.id.paginawe9);
		gps=(ImageView)findViewById(R.id.gps);
		panico= (ImageView)findViewById(R.id.panico);
		wifi=(ImageView)findViewById(R.id.wifi);

		menu_ubicacion.setFitsSystemWindows(true);
		menu_horario.setFitsSystemWindows(true);
		menu_panico.setFitsSystemWindows(true);
		menu_incidencias.setFitsSystemWindows(true);

		menu_horario.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//texto_menuHorario.setTextColor(getResources().getColor(R.color.amarillo));
//				reloj.setBackgroundResource(R.drawable.horario_cabeza);

				Intent intent = new Intent(MainActivity.this, Horarios_consulta.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
		menu_ubicacion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//texto_menuUbicacion.setTextColor(getResources().getColor(R.color.amarillo));
		//		tocar.setBackgroundResource(R.drawable.ubicacion_a);

				Intent intent = new Intent(MainActivity.this, Activity_Tiendas.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();

			}
		});

		menu_incidencias.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


//				texto_incidencias.setTextColor(getResources().getColor(R.color.amarillo));
			//	inciendenciasimas.setBackgroundResource(R.drawable.incidencias_amarillo);

				Intent intent= new Intent(MainActivity.this,ListaIncidenciasActivity.class);
				intent.putExtra("plantilla",false);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();



			}
		});

		menu_panico.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//texto_panico.setTextColor(getResources().getColor(R.color.amarillo));
				//panicoimas.setBackgroundResource(R.drawable.panico_amarillo);

				validar();

			}
		});

		//  ---
	   //Manda el el imei o id_dispositivo
       TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
       if (telephonyManager.getDeviceId() != null){
           Singleton.getInstance().setImei(telephonyManager.getDeviceId());
       }else{
           Singleton.getInstance().setImei(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
       }
       //  ---
	 
	  	panico.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	panico.setBackgroundResource(R.drawable.panico_amarillo);
				validar();
			}
		});
		paginasweb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				paginaweb paginaweb= new paginaweb(getApplicationContext());
			}
		});
	   /*ImageView loadingImage = (ImageView)findViewById(R.id.login_logo_imageview);
	   loadingImage.setBackgroundResource(R.drawable.loading_animation);
	   AnimationDrawable loadingAnimation = (AnimationDrawable)loadingImage.getBackground();
	   loadingAnimation.start();*/
	   
	   //Si ya está registrado, se va automaticamente a MainActivity
	   SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

		boton_incidencia.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*

				boton_incidencia.setBackgroundResource(R.drawable.incidencias_amarillo);
				Intent intent = new Intent(MainActivity.this, ListaIncidenciasActivity.class);
				startActivity(intent);
				*/
				//submenu("L");

			//	boton_incidencia.setBackgroundResource(R.drawable.celular_amarillo);

				Intent intent= new Intent(MainActivity.this,Contacto.class);
				startActivity(intent);


			}
		});

		justificar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				submenu("L");

			}
		});

		permisos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				submenu("P");
			}
		});
	   if(prefs.getString(Constants.SP_HASLOGGED, Constants.SP_NOTLOGGED).equals(Constants.SP_NOTLOGGED)){
		   Intent intent = new Intent(this, RegistroActivity.class);
		  // Intent intent= new Intent(this,LoginActivity.class);
		   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		   startActivity(intent);
		   this.finish();
	   }
	   else{
		   // metodo para guardar datos//


		   //
		   //TODO async para adquirir la configuracion
		   Dao dao;
		   DBHelper dbHelper = null;
		   List<Empleado> empleados = null;

		   try {
		       dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getEmpleadoDAO();
		      empleados = dao.queryForAll();

		       Log.i(TAG, "Terminada la consulta");
		       Log.i(TAG, "Size = " + empleados.size());
		       
		       if(empleados == null){
		    	   Log.e(TAG, "No se cargó el usuario");
		       }
		       else if (empleados.get(0) == null) {
		           Log.e(TAG, "Ningún usuario con id = " + getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
		       } else {
		           Log.i(TAG, "Recuperado usuario con id = 1: " + empleados.get(0).getName());
		       }
		   }catch(Exception e){
			   Log.e(TAG, "EXCEPTION No se cargó el usuario");
		   }
		   
		   //TODO checar la hora indicada en el sharedPreferences y comparar
		   SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DAY_FORMAT);
		   String sharedDateString = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_UPDATE_DATE, "01/01/2015 00:00:00");
		   
		   Date compareDate = new Date();
		   Date sharedDate = new Date();
		   
		   try {
			   sharedDate = dateFormat.parse(sharedDateString);
			   compareDate.setHours(0);
			   compareDate.setMinutes(0);
			   compareDate.setSeconds(0);
		   } 
		   catch (ParseException e) {
			   // TODO Auto-generated catch block
		       e.printStackTrace();
		       sharedDate.setDate(2);
		       sharedDate.setMonth(2);
		       sharedDate.setYear(2015);
		   } 
		   catch (java.text.ParseException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   sharedDate.setDate(2);
		       sharedDate.setMonth(2);
		       sharedDate.setYear(2015);
		   }



		   try {
			   Log.d(TAG, "Shared= " + sharedDate.toString() + "\nCompare = " + compareDate.toString());
			   if(false){//if(sharedDate.toString().equals(compareDate.toString())){
				   Log.i(TAG, "La configuración NO requiere actualización");
				   startService();
				   
				   Log.i(TAG, "TEST Inicio del servicio");

			   }
			   else if(false){//sharedDate.compareTo(compareDate) >= 0){
				   Log.i(TAG, "La configuración NO requiere actualización");
				   startService();
				   
				   Log.i(TAG, "TEST Inicio del servicio");

			   } 
			   else{
				   Log.i(TAG, "La configuración requiere actualización");
				   progressBar.setVisibility(View.VISIBLE);
				   GetSettingsAsync getSettingsAsync = new GetSettingsAsync(this);
				   if (empleados != null) {
					   if(empleados.size() > 0){

                           getSettingsAsync.execute(empleados.get(0));

                       }
                       else{
                           progressBar.setVisibility(View.GONE);
                       }
				   }
				   else{
					   progressBar.setVisibility(View.GONE);
					   if(!prefs.getString(Constants.SP_HASLOGGED, Constants.SP_NOTLOGGED).equals(Constants.SP_NOTLOGGED)){

						   startService();
					   }


				   }
			   }
			   
		   } catch (ParseException e) {
			   Log.i(TAG, "Error en dates = " + e);
		   }
	   }
	   
	   photoImage 		= (ImageView)findViewById(R.id.main_photo_image);
	   nameText 		= (TextView)findViewById(R.id.main_name_text);
	  nameText.setText(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_NAME, "(desconocido"));

		checkConnectivityStatus();
	   checkGPSStatus();

		moreButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				submenu("H"); ///

				//moreButton.setBackgroundResource(R.drawable.horario5);
				/*Intent intent=new Intent(MainActivity.this,Horarios_consulta.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				*/
				Log.d(TAG,"Horarios");





			}
		});

		posiciones.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

		//	posiciones.setBackgroundResource(R.drawable.ubicacion5);
			/*	Intent intent=new Intent(MainActivity.this,Activity_Tiendas.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
						Log.d(TAG,"listado de tiendas");
						*/


				submenu("Z");

			}
				});

	   //Listener del GPS, muestra si está encendido o apagado
	   LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	   lm.addGpsStatusListener(new android.location.GpsStatus.Listener()
	   {
	       public void onGpsStatusChanged(int event)
	       {
	           switch(event)
	           {
	           case GpsStatus.GPS_EVENT_STARTED:
	        	   Log.i(TAG, "GPS Encendido");
//	               gpsText.setText(Constants.ENCENDIDO);
	             // changeGpsImage(true);
	               break;
	           case GpsStatus.GPS_EVENT_STOPPED:
	        	   Log.i(TAG, "GPS Apagado");
	        	  // gpsText.setText(Constants.APAGADO);
	        	 // changeGpsImage(false);
	               break;
	           }
	       }
	   });
	   
	   //TODO Listener del Wifi/3g. Muestra cuál se está usando o si está apagado
	   boolean mobileDataEnabled = false;
	   ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
	   try{
		   Class cmClass = Class.forName(cm.getClass().getName());
		   Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
		   method.setAccessible(true);
		   mobileDataEnabled = (Boolean)method.invoke(cm);
		   
		   if(mobileDataEnabled){
			   changeDataImage(true);
		   }
		   else{
			   changeDataImage(false);
		   }
	   }catch(Exception e){}
	   
	   /*ConnectionChangeReceiver connectionChange = new ConnectionChangeReceiver() {
           @Override
           protected void onNewState(int opc) {
        	   changeConnectionStance(opc);
           }

       };
	   connectionChange.onReceive(getApplicationContext(), getIntent());*/
	   
	   /*moreButton.setOnClickListener(new OnClickListener() {
		   @Override
		   public void onClick(View v) {
			   goToMapTrackActivity();
		   }
	   });*/
	   
	   //Se inserta los horarios de monitoreo para la persona en cuestión
	   Dao dao;
	   DBHelper dbHelper = null;
	   List<Empleado> empleados = null;
	   
	   try {
	       dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getEmpleadoDAO();
	       empleados = dao.queryForAll();
	       Log.i(TAG, "Terminada la consulta");
	       Log.i(TAG, "Size = " + empleados.size());
	       
	       if(empleados == null){
	    	   Log.e(TAG, "No se cargó el usuario");
	       }
	       else if (empleados.get(0) == null) {
	           Log.e(TAG, "Ningún usuario con id = " + getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
	       } else {
	           Log.i(TAG, "Recuperado usuario con id = 1: " + empleados.get(0).getName());
	       }
	   }catch(Exception e){
		   Log.e(TAG, "EXCEPTION No se cargó el usuario");
	   }
	   
	   //Se indican las horas de monitoreo en pantalla
	   List<RangoMonitoreo> rangosMonitoreo = null;
	   
	   //Sacamos el día de la semana de hoy
	   SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
	   Date d = new Date();
	   String dayOfTheWeek = sdf.format(d);
	   Log.e(TAG, "Dia de la semana = " + dayOfTheWeek);
	   
	   dayOfTheWeek = getDayOfTheWeek(dayOfTheWeek);
	   
	   boolean isSuccess = false;
	   try {
		   Log.d(TAG, "1");
		   dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getRangosMonitoreoDao();
	      
		   Log.d(TAG, "2 = " + dayOfTheWeek);
		   QueryBuilder queryBuilder = dao.queryBuilder();
   	       //queryBuilder.setWhere(queryBuilder.where().eq(RangoMonitoreo.IS_EXCLUSION, "false")
   	       //				 .and().eq(RangoMonitoreo.NUMERO_DIA, dayOfTheWeek));
   	       
   	       queryBuilder.setWhere(queryBuilder.where().eq(RangoMonitoreo.IS_EXCLUSION, "false").and().eq(RangoMonitoreo.NUMERO_DIA, dayOfTheWeek));
   		
   	       Log.d(TAG, "3");
   	       rangosMonitoreo = dao.query(queryBuilder.prepare());
   	       Log.d(TAG, "3.1");
   	       
   	       if (rangosMonitoreo.size() == 0) {
   	    	   Log.d(TAG, "4- No se encontraron registros del día: " + dayOfTheWeek);                
   	       } else {
   	    	   Log.d(TAG, "5- Recuperado " + rangosMonitoreo.size() + " registros con día = " + rangosMonitoreo.get(0).getNumeroDia());
   	    	   isSuccess = true;
   	       }
	       
	       Log.i(TAG, "Terminada la consulta");
	       Log.i(TAG, "Size = " + rangosMonitoreo.size());
	       
	       if(rangosMonitoreo == null){
	    	   Log.e(TAG, "No se cargaron los horarios");
	       }
	       else if (rangosMonitoreo.get(0) == null) {
	           Log.e(TAG, "Ningún registro con dia = " + dayOfTheWeek);
	       } else {
	           Log.i(TAG, "Recuperados registros con día: " + dayOfTheWeek);
	           isSuccess = true;
	       }
	   }catch(Exception e){
		   Log.e(TAG, "EXCEPTION No se cargaron los horarios");
	   }

	}
	
	private void changeGpsImage(boolean isOn){
		if(isOn){
			gps.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.movilidad_19));
		}
		else{
			gps.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.senal_gps1));
		}
	}
	
	private void changeDataImage(boolean isOn){
		if(isOn){
			wifi.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.movilidad_18));
		}
		else{
			wifi.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.senalwifi));
		}
	}

	public void changeConnectionStance(int opc){
		if(opc == 1){
			//connectionText.setText("WIFI");
			changeDataImage(true);
		}
		else if(opc == 2){
			//connectionText.setText("3G");
			changeDataImage(true);
		}
		else{
			//connectionText.setText("APAGADO");
			changeDataImage(false);
		}
	}

	//Method to start the service
	public void startService() {
		startService(new Intent(getBaseContext(), EmployeeLocationService.class));

	}

	//Method to stop the service
	public void stopService() {
		stopService(new Intent(getBaseContext(), EmployeeLocationService.class));

	}
	
	public void checkConnectivityStatus(){
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		//For 3G check
		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
		             	.isConnectedOrConnecting();
		
		//	For WiFi Check
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
		             	.isConnectedOrConnecting();
		
		if(isWifi){
			//connectionText.setText(Constants.WIFI);
		}
		else if(is3g){
			//connectionText.setText(Constants.DATA_3G);
		}
		else{
			//connectionText.setText(Constants.NINGUNO);
			requestDataEnabling();
		}
	}
	
	public void checkGPSStatus(){
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			//gpsText.setText(Constants.ENCENDIDO);
			changeDataImage(true);
			changeGpsImage(true);
			//gpsImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.movilidad_19));
	    }else{
	    	//gpsText.setText(Constants.APAGADO);
	    	changeDataImage(false);
			changeGpsImage(false);
	    	requestGPSEnabling();
	    }
	}
	
	public void requestGPSEnabling(){

		final Dialog alert = new Dialog(MainActivity.this,R.style.Theme_Dialog_Translucent);
		LayoutInflater inflater1=getLayoutInflater();
		final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
		TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
		LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
		LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
		TextView mensaje_mostrar=(TextView)dialogo.findViewById(R.id.mensajemostrar);
		mensaje_mostrar.setText("¿Desea encenderlo ahora mismo?");
		titulodialo.setText("El GPS está apagado");
		alert.setContentView(dialogo);
		confirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callGPSSettingIntent = new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(callGPSSettingIntent);
				alert.dismiss();

			}
		});

		cancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
		alert.show();


	}
	
	public void requestDataEnabling(){

		final Dialog alert = new Dialog(MainActivity.this,R.style.Theme_Dialog_Translucent);
		LayoutInflater inflater1=getLayoutInflater();
		final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
		TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
		LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
		LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
		TextView mensaje_mostrar=(TextView)dialogo.findViewById(R.id.mensajemostrar);
		titulodialo.setText("No tiene ningun tipo de conexión de red");
		mensaje_mostrar.setText("¿Desea encender el Wifi y/o datos moviles ahora mismo?");
		alert.setContentView(dialogo);
		alert.show();
		confirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callDataSettingIntent = new Intent(
						Settings.ACTION_SETTINGS);
				startActivity(callDataSettingIntent);
				alert.dismiss();
			}
		});
		cancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});

	}
	
	public void updateSettings(Configuracion configuracion){
		Log.i(TAG, "updateSettings");
		progressBar.setVisibility(View.GONE);
		
		//Si adquirió los datos, que sobreescriba los intervalos y demás
		if(configuracion.isSuccess()){
			Log.i(TAG, "SUCCESS!");
			SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
			Editor edit = prefs.edit();
			edit = putDouble(edit, Constants.SP_RANGE, configuracion.getRango());
			edit.putInt(Constants.SP_TIME, configuracion.getTiempo());
			edit = putDouble(edit, Constants.SP_DISTANCE, configuracion.getDistancia());
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			Date thisDate = new Date();
			   
			//Procederá a guardarlos en base de datos
			Dao dao;
			try {
			    dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getConfiguracionDao();
			    dao.create(configuracion);
			    Log.i(TAG, "Configuracion creado exitosamente");
			} catch (SQLException e) {
			    Log.e(TAG, "Error creando configuración");
			}
			
			//Ahora se procede a guardar los rangos de monitoreo
			try {


				dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getRangosMonitoreoDao();

				ArrayList<RangoMonitoreo> rangosMonitoreo = configuracion.getRangosMonitoreo();

			    for(int i = 0; i < rangosMonitoreo.size(); i++) {
					dao.create(rangosMonitoreo.get(i));


					Log.d(TAG, "Creado nuevo rango = " + rangosMonitoreo.get(i).getNumeroDia()
							+ "/" + rangosMonitoreo.get(i).getFecha()
							+ "/" + rangosMonitoreo.get(i).getHoraInicial()
							+ "/" + rangosMonitoreo.get(i).getHoraFinal());
			    }
				Log.i(TAG, "Configuracion creado exitosamente");
			} catch (SQLException e) {
			    Log.e(TAG, "Error creando configuración");
			}
			
			try {
				if(configuracion == null){
					Log.e(TAG, "Error al adquirir las nuevas configuraciones");
				}
				else if(configuracion.isSuccess()){
					edit.putString(Constants.SP_UPDATE_DATE, String.valueOf(dateFormat.format(thisDate)));
					Log.i(TAG, "Se guardó la fecha de actualización");
				}
				else{
					Log.e(TAG, "No se adquirieron las nuevas configuraciones");
				}
				
			} 
			catch(Exception e){
				Log.e(TAG, "No se guardó la fecha de actualización");
			}
			edit.commit();
		}

		Log.i(TAG, "TEST Inicio del servicio con nueva configuración");
		startService();
	}
	
	Editor putDouble(final Editor edit, final String key, final double value){
		return edit.putLong(key,  Double.doubleToRawLongBits(value));
	}

	protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }
	
	public String getDayOfTheWeek(String day){
		String dayOfWeek = "0";
		if(day.equals("Sunday"))
			dayOfWeek = "0";
		else if(day.equals("Monday"))
			dayOfWeek = "1";
		else if(day.equals("Tuesday"))
			dayOfWeek = "2";
		else if(day.equals("Wednesday"))
			dayOfWeek = "3";
		else if(day.equals("Thursday"))
			dayOfWeek = "4";
		else if(day.equals("Friday"))
			dayOfWeek = "5";
		else if(day.equals("Saturday"))
			dayOfWeek = "6";
		
		return dayOfWeek;
	}
	



	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			Intent intent=new Intent(MainActivity.this,MenuAplicaciones.class);
			startActivity(intent);
			finish();
		}

	}


	public void submenu(final String validar)
	{
		final Dialog alert = new Dialog(MainActivity.this,R.style.Theme_Dialog_Translucent);
		final LayoutInflater inflater1=getLayoutInflater();
		final View dialogo=inflater1.inflate(R.layout.sub_menu, null);
		final ImageButton miHorario=(ImageButton)dialogo.findViewById(R.id.misHorarios);
		ImageButton plantillaHorario=(ImageButton)dialogo.findViewById(R.id.miplantillahorario);
		TextView titulo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
		TextView titulo1=(TextView)dialogo.findViewById(R.id.textotitulo);
		TextView plantilla=(TextView)dialogo.findViewById(R.id.plantilla);
		LinearLayout cerrar=(LinearLayout)dialogo.findViewById(R.id.cerrar);

		cerrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});


		Intent intent=null;
		if(validar.equals("Z"))
		{
			intent= new Intent(MainActivity.this,Activity_Tiendas.class);
			miHorario.setBackgroundResource(R.drawable.boton_mis_ubicaciones_x2);
			plantillaHorario.setBackgroundResource(R.drawable.boton_ubicaciones_mi_plantilla_x2);
			titulo.setText("¿Qué ubicaciones quieres ver?");
			titulo1.setText("Mis ubicaciones");

		}
		else if(validar.equals("L"))
		{
			miHorario.setBackgroundResource(R.drawable.boton_mis_justificaciones_x2);
			plantillaHorario.setBackgroundResource(R.drawable.boton_justificaciones_mi_plantilla_x2);

			intent= new Intent(MainActivity.this,ListaIncidenciasActivity.class);
			intent.putExtra("plantilla", false);
			titulo.setText("¿Qué incidencias quieres ver?");
			titulo1.setText(" Mis\njustificaciones");
			plantilla.setText("Justificaciones\nde mi plantilla");


		}

		if (validar.equals("H"))
		{
			miHorario.setBackgroundResource(R.drawable.boton_mis_horarios_x2);
			plantillaHorario.setBackgroundResource(R.drawable.boton_horarios_mi_plantilla_x2);

			intent= new Intent(MainActivity.this,Horarios_consulta.class);



		}
		if(validar.equals("P"))
		{

			miHorario.setBackgroundResource(R.drawable.boton_mis_permisos_x2);
			plantillaHorario.setBackgroundResource(R.drawable.boton_permisos_mi_plantilla_x2);

			titulo1.setText("Mis\npermisos");
			plantilla.setText(" Permisos\nde mi plantilla");
			intent= new Intent(MainActivity.this,Permisos_Activity.class);

		}

		final Intent intent1=intent;

		miHorario.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent1);

			}
		});
		plantillaHorario.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if(validar.equals("H"))
				{
					Intent intent= new Intent(MainActivity.this,Nombres_lista.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

				}
				if(validar.equals("L"))
				{
					Intent intent= new Intent(MainActivity.this,ListaIncidenciasActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("plantilla", true);
					startActivity(intent);
				}

				if(validar.equals("P"))
				{
					Intent intent2= new Intent(MainActivity.this,Permisos_Plantilla_Activity.class);
					intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent2);
				}

				if(validar.equals("Z")){
					Intent intent3=new Intent(MainActivity.this,Plantilla_ZonasYubicaciones.class);
					intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent3);
				}



			}
		});

		alert.setContentView(dialogo);
		alert.show();

	}

	public void validar()
	{
		DatosContacto datosContacto= new DatosContacto();

		if(!datosContacto.isguardar(this))
		{
			Intent intent= new Intent(this,Contactos.class);
			startActivity(intent);

		}
		else{

			Intent intent= new Intent(this,Panico.class);
			intent.putExtra("Main",true);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

	}

}