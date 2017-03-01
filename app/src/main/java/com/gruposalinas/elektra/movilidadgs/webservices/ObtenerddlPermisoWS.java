package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yvegav on 23/09/2016.
 */
public class ObtenerddlPermisoWS
{

    public static String TAG = "Lista_combo";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public Horarios checar(Horarios empleado)
    {
        empleado.setSuccess(false);
        String URL = Constants.DOMAIN_URL + "/" + Constants.ObtenerddlPermiso;

        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("id_num_empleado",empleado.getId_empleado());
            jsonObject.put("token",Encryption.md5(empleado.getId_empleado() + Encryption.getHour()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "Obtenerlista" + jsonObject.toString());
        HttpsURLConnection urlConnection=null;
        try{
            java.net.URL url = new URL(URL);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setHostnameVerifier(Utils.hostnameVerifier());// valida el hostname
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
            OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonObject.toString());
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

                System.out.println("" + sb.toString());



            }else{
                System.out.println(urlConnection.getResponseMessage());
                empleado.setSuccess(false);
                empleado.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;



            try{
                obj = new JSONObject(resultado);

                empleado.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    empleado.setSuccess(true);
                    JSONArray array=new JSONArray(obj.getString("tipo"));
                    empleado.setDatosParse(array);

                }
                else{

                    empleado.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                empleado.setMensajeError(e1.toString());
                empleado.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            empleado.setMensajeError(e.toString());
            empleado.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            empleado.setMensajeError(e.toString());
            empleado.setSuccess(false);
        } catch (IOException e) {
            empleado.setMensajeError(e.toString());
            e.printStackTrace();
            empleado.setSuccess(false);
        }
        finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        Log.d(TAG,"checando contrasena");

        empleado.setRESPUESTA(TAG+" "+ " otra respuesta "+empleado.getMensajeError());

        return empleado;
    }

}
