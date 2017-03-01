package com.gruposalinas.elektra.movilidadgs.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gruposalinas.elektra.movilidadgs.beans.Configuracion;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.RangoMonitoreo;
import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.geolocation.GPSTracker;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ParseException;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class GetSettingstWS
{
	public static String TAG = "GET_SETTINGS_WS";
	String line=null;
	StringBuilder sb = new StringBuilder();
	String resultado="";

	public Configuracion setMovement(Empleado empleado){
		Configuracion configuracion = new Configuracion();
		empleado.setSuccess(false);

		String url = Constants.DOMAIN_URL + "/" + Constants.GET_SETTINGS_WS;
		//String url="http://10.112.51.5:8083/ServicioEmpleados.svc/obtenerconfiguraciones";// desarrollo
		
		String jsonEmpleado = "{"
							+ "\"id_dispositivo\":\"" 		+ empleado.getImei() + "\","
							+ "\"id_num_empleado\":\"" 		+ empleado.getIdEmployee()  + "\","
							+ "\"va_numero_telefono\":\"" 	+ empleado.getPhoneNumber() + "\","
							+ "\"token\":\"" 				+ Encryption.md5(empleado.getIdEmployee() + Encryption.getHour()) + "\""
							+ "}";

		JSONObject jsonObject= new JSONObject();
		try {
			jsonObject.put("id_dispositivo",empleado.getImei());
			jsonObject.put("id_num_empleado",(empleado.getIdEmployee()));
			jsonObject.put("va_numero_telefono",empleado.getPhoneNumber());
			jsonObject.put("token",Encryption.md5((empleado.getIdEmployee())+Encryption.getHour()));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.d("JsonEnviado",jsonObject.toString());
		Log.i(TAG, "Usuario = " + empleado.getIdEmployee() + "\nHora = " + Encryption.getHour() + "\nToken = " + Encryption.md5(empleado.getIdEmployee() + Encryption.getHour()));
		HttpsURLConnection urlConnection=null;
		try {
			java.net.URL url1 = new URL(url);
			urlConnection = (HttpsURLConnection) url1.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setHostnameVerifier(Utils.hostnameVerifier());// valida el hostname
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(60000);
			urlConnection.setReadTimeout(60000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.connect();
			OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
			out.write(jsonObject+"");
			out.close();
			int HttpResult =urlConnection.getResponseCode();
			if(HttpResult == HttpsURLConnection.HTTP_OK){
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(),"utf-8"));
				line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
					resultado=sb.toString();
				}
				br.close();

				System.out.println(""+sb.toString());

			}else{
				System.out.println(urlConnection.getResponseMessage());
				empleado.setSuccess(false);
				empleado.setMensajeError(urlConnection.getResponseMessage());
			}

			Log.i(TAG, "EntityUtils =" + resultado);
			
			ArrayList<RangoMonitoreo> rangoMonitoreoList = new ArrayList<RangoMonitoreo>();
			Log.d(TAG, "1");
			try{
				JSONObject mainConfiguracionJSON = new JSONObject(resultado);
				empleado.setMensajeError(mainConfiguracionJSON.getString("mensajeError"));
				Log.d(TAG, "2");
				if(HttpResult == HttpURLConnection.HTTP_OK){
					JSONObject configuracionJSON = new JSONObject(mainConfiguracionJSON.getString("configuraciones"));

					Log.d(TAG, "3 = " + mainConfiguracionJSON.toString());
					if(mainConfiguracionJSON.getString("error").equals("false")){
						configuracion.setSuccess(true);
						
						Log.d(TAG, "4");
						configuracion.setDistancia(configuracionJSON.getInt("int_distancia"));
						configuracion.setRango(configuracionJSON.getInt("int_radio"));
						configuracion.setTiempo(configuracionJSON.getInt("int_tiempo"));
							
						Log.d(TAG, "5");
						Log.d(TAG, "5.1 Tiempo = " + configuracion.getTiempo());
						JSONArray rangosMonitoreoJSON = mainConfiguracionJSON.getJSONArray("rangosMonitoreo");
						//Se agregan los rangos de monitoreo
						for(int i = 0; i < rangosMonitoreoJSON.length(); i++){
							RangoMonitoreo rangoMonitoreo = new RangoMonitoreo();
								
							String horaInicial = rangosMonitoreoJSON.getJSONObject(i).getString("tm_hora_inicial_string");
							String horaFinal = rangosMonitoreoJSON.getJSONObject(i).getString("tm_hora_final_string");
							
							rangoMonitoreo.setNumeroDia(rangosMonitoreoJSON.getJSONObject(i).getInt("int_numero_dia"));
							rangoMonitoreo.setHoraInicial((horaInicial));
							rangoMonitoreo.setHoraFinal((horaFinal));
							rangoMonitoreo.setExclusion(false);
							rangoMonitoreoList.add(rangoMonitoreo);
						}
						
						Log.d(TAG, "6");
						//Se agregan las exclusiones a los rangos de monitoreo
						configuracion.setRangosMonitoreo(rangoMonitoreoList);
						JSONArray exclusionesJSON = new JSONArray(mainConfiguracionJSON.getString("exclusiones"));
						for(int i = 0; i < exclusionesJSON.length(); i++){
							RangoMonitoreo exclusion = new RangoMonitoreo();
							
							String horaInicial = exclusionesJSON.getJSONObject(i).getString("tm_hora_inicial_string");
							String horaFinal = exclusionesJSON.getJSONObject(i).getString("tm_hora_final_string");

							exclusion.setFecha(Utils.parseJsonDate(exclusionesJSON.getJSONObject(i).getString("da_fecha")));
							
							exclusion.setHoraInicial((horaInicial));
							exclusion.setHoraFinal((horaFinal));
							exclusion.setExclusion(true);
							String exculsion=Utils.parseJsonDate(exclusionesJSON.getJSONObject(i).getString("da_fecha"));
							
							rangoMonitoreoList.add(exclusion);
						}
					}
				}
				
			} catch (ParseException e) {
				Log.i(TAG, "JSON fail!");
				e.printStackTrace();
			
			} catch (JSONException e) {
				Log.i(TAG, "JSON fail!");
			}
			
		} catch (MalformedURLException e) {
		e.printStackTrace();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}	
		Log.i(TAG, "Success = " + configuracion.isSuccess());
         
        return configuracion;
	}
}