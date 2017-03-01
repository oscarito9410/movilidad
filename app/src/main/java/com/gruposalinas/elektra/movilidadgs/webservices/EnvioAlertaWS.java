package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
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
 * Created by Adrian Guzman on 23/03/2016.
 */
public class EnvioAlertaWS
{
    public static String TAG = "EnvioAlerta";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public RegistroGPS gps(RegistroGPS gps)
    {
       //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/EnvioAlerta";// DESARROLLO
       // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/EnvioAlerta";
        String URL = Constants.DOMAIN_URL + "/" + Constants.EnvioAlerta;

        String pruebas = null;


        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id_num_empleado",(gps.getNumEmpleado()));
            jsonObject.put("dec_latitud",gps.getLatitud()+"");
            jsonObject.put("dec_longitud",gps.getLongitud()+"");
            jsonObject.put("dt_fecha_hora",gps.getJsonDate()+"");
            jsonObject.put("token", Encryption.md5((gps.getNumEmpleado()) + Encryption.getHour()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // System.out.print(jsonEmpleado);
        Log.d(TAG, "inicia WS Enviando alerta " + jsonObject.toString());



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

                System.out.println("Envio de alerta respuesta: "+sb.toString());

            }else{
                System.out.println(urlConnection.getResponseMessage());
                gps.setSuccess(false);
                gps.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;


            try{
                obj = new JSONObject(resultado);

                gps.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    gps.setSuccess(true);

                }
                else{

                    gps.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                gps.setMensajeError(e1.toString());
                gps.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            gps.setMensajeError(e.toString());
            gps.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            gps.setMensajeError(e.toString());
            gps.setSuccess(false);
        } catch (IOException e) {
            gps.setMensajeError(e.toString());
            e.printStackTrace();
            gps.setSuccess(false);
        }
        finally
        {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }



        return gps;
    }


}
