package com.gruposalinas.elektra.movilidadgs.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import android.net.ParseException;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class RegisterEmployeeWS {
	public static String TAG = "CHECK_EMPLOYEES_WS";
	String line=null;
	StringBuilder sb = new StringBuilder();
	String resultado="";

	public Empleado setEmployee(Empleado employee){
		employee.setSuccess(false);
		//String url = "http://10.112.51.5:8083/ServicioEmpleados.svc/Registrarempleado";
		//String url = "http://200.38.122.208:8081/ServicioEmpleados.svc/Registrarempleado";
		String url = Constants.DOMAIN_URL + "/" + Constants.InicioRegistroEmpleado;



		JSONObject jsonObject= new JSONObject();
		try {
			jsonObject.put("id_num_empleado",(employee.getIdEmployee()));
			jsonObject.put("id_dispositivo",employee.getImei());
			jsonObject.put("va_numero_telefono",employee.getPhoneNumber());
			jsonObject.put("token",Encryption.md5((employee.getIdEmployee()) + Encryption.getHour()));
			jsonObject.put("strPassword",employee.getRESPUESTA());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.d(TAG,jsonObject.toString());
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
			if(HttpResult == HttpsURLConnection.HTTP_OK)
			{
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
				employee.setSuccess(false);
				employee.setMensajeError(urlConnection.getResponseMessage());
			}

			Log.i(TAG, "EntityUtils =" + resultado);


			try {
				JSONObject empleadoJSON = new JSONObject(resultado);

				employee.setMensajeError(empleadoJSON.getString("mensajeError"));
				
				if(HttpResult == HttpURLConnection.HTTP_OK)
				{
					if(empleadoJSON.getString("error").trim().equals("false")){
						employee.setSuccess(true);
						
						empleadoJSON = new JSONObject(empleadoJSON.getString("empleado"));
						employee.setName(empleadoJSON.getString("va_nombre_completo"));
						employee.setEnterprise(empleadoJSON.getString("id_empresa"));
						employee.setPosition(empleadoJSON.getInt("id_puesto"));
						employee.setIdEmployee(empleadoJSON.getString("id_num_empleado"));
						Log.i(TAG, "Nombre = " + employee.getName());
					}
				}
				
			} catch (ParseException e) {
				Log.i(TAG, "JSON fail!");
				e.printStackTrace();
				employee.setSuccess(false);
				employee.setMensajeError(e.toString());
			} catch (JSONException e) {
				Log.i(TAG, "JSON fail!");
				employee.setSuccess(false);
				employee.setMensajeError(e.getMessage());
			}
			
		} catch (MalformedURLException e) {
		e.printStackTrace();
			employee.setSuccess(false);
			employee.setMensajeError(e.getMessage());
			 
		} catch (IOException e) {
			e.printStackTrace();
			employee.setSuccess(false);
			employee.setMensajeError(e.getMessage());
		}	
         
        return employee;
	}
}


