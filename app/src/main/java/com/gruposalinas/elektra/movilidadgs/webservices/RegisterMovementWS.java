package com.gruposalinas.elektra.movilidadgs.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import android.net.ParseException;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class RegisterMovementWS {
	public static String TAG = "REGISTER_MOVEMENT_WS";
	String line=null;
	StringBuilder sb = new StringBuilder();
	String resultado="";


	public RegistroGPS setMovement(RegistroGPS registro){
		Log.i(TAG, "Iniciado");
		registro.setSuccess(false);
		//String url = "http://200.38.122.208:8081/ServicioEmpleados.svc/RegistrarEmpleadoMovimientos_UTC";// actual
		//String url = "http://10.112.51.5:8083/ServicioEmpleados.svc/RegistrarEmpleadoMovimientos_UTC";// desarrollo
		String url = Constants.DOMAIN_URL + "/" + Constants.REGISTER_MOVEMENT_WS;

		JSONObject jsonObject= new JSONObject();
		JSONObject jsonObject1= new JSONObject();
		JSONArray jsonArray= new JSONArray();

		String d=Utils.parseJsonDate(registro.getJsonDate());

		try {
			jsonObject1.put("dec_latitud",registro.getLatitud()+"");
			jsonObject1.put("dec_longitud",registro.getLongitud()+"");
			jsonObject1.put("dt_fecha_hora",registro.getJsonDate());
			jsonObject1.put("dec_bateria",registro.getBateria()+"");
			jsonObject1.put("provider",registro.getProvider());

			jsonArray.put(jsonObject1);

			jsonObject.put("id_num_empleado",(registro.getNumEmpleado()));
			jsonObject.put("token",Encryption.md5((registro.getNumEmpleado()) + Encryption.getHour()));
			jsonObject.put("movimientos",jsonArray);

		} catch (JSONException e) {
			e.printStackTrace();
		}


		Log.i(TAG, "Json enviado" +jsonObject.toString());
		HttpsURLConnection urlConnection=null;
		try {

			java.net.URL url1 = new URL(url);
			urlConnection = (HttpsURLConnection) url1.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setHostnameVerifier(Utils.hostnameVerifier());// valida el hostname
			urlConnection.setRequestMethod("POST");
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(60000);
			urlConnection.setReadTimeout(60000);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.connect();
			OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
			out.write(jsonObject+"");
			out.close();
			int HttpResult =urlConnection.getResponseCode();
			if(HttpResult == HttpURLConnection.HTTP_OK){
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
				registro.setSuccess(false);
				registro.setMensajeError(urlConnection.getResponseMessage());
			}


			try {
				JSONObject registroJSON = new JSONObject(resultado);
				
				registro.setMensajeError(registroJSON.getString("mensajeError"));
				
				if(HttpResult == HttpURLConnection.HTTP_OK){
					if(registroJSON.getString("error").toLowerCase().trim().equals("false")){
						registro.setSuccess(true);
					}
				}
				
			} catch (ParseException e) {
				Log.i(TAG, "JSON fail!");
				e.printStackTrace();
				registro.setSuccess(false);
			} catch (JSONException e) {
				e.printStackTrace();
				registro.setSuccess(false);
				Log.i(TAG, "JSON fail!");
			}
			
		} catch (MalformedURLException e)
		{
			registro.setSuccess(false);
		e.printStackTrace();
			 
		} catch (IOException e) {
			registro.setSuccess(false);
			e.printStackTrace();
		}	
		
		Log.i(TAG, "Success = " + registro.isSuccess());
		
        return registro;
	}
}