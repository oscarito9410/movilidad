package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.Singleton;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

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
 * Created by Adrian guzman on 11/05/2016.
 */
public class escribir_LogWS
{
    public static String TAG = "Log_datos";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public Singleton checar(Singleton singleton)
    {
        singleton.setSuccess(false);
        //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/escribirlog";// DESARROLLO
      // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/escribirlog";
        String URL = Constants.DOMAIN_URL + "/" + Constants.escribirlog;

        String numero_empleado=(singleton.getNumero_empleado());

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("id_num_empleado",(numero_empleado));
            jsonObject.put("id_dispositivo",singleton.getLog());
            jsonObject.put("token",Encryption.md5((singleton.getNumero_empleado())+Encryption.getHour()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "inicia log" + jsonObject+"");
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

                System.out.println("" + sb.toString());



            }else{
                System.out.println(urlConnection.getResponseMessage());
                singleton.setSuccess(false);
                singleton.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;



            try{
                obj = new JSONObject(resultado);

                singleton.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    singleton.setSuccess(true);

                }
                else{

                    singleton.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                singleton.setMensajeError(e1.toString());
                singleton.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            singleton.setMensajeError(e.toString());
            singleton.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            singleton.setMensajeError(e.toString());
            singleton.setSuccess(false);
        } catch (IOException e) {
            singleton.setMensajeError(e.toString());
            e.printStackTrace();
            singleton.setSuccess(false);
        }
        finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        Log.d(TAG,"envio del log");

        singleton.setRESPUESTA(TAG+" "+ " otra respuesta "+singleton.getMensajeError());

        return singleton;
    }

}
