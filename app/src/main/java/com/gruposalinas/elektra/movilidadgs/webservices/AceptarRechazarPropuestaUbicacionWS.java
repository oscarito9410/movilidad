package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.AceptarRechazarPropuestaUbicacion;
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
 * Created by yvegav on 06/10/2016.
 */
public class AceptarRechazarPropuestaUbicacionWS
{
    public static String TAG = "Rechazar univacion";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";

    public AceptarRechazarPropuestaUbicacion aceptarRechazarPropuestaUbicacion(AceptarRechazarPropuestaUbicacion aceptarRechazarPropuestaUbicacion)
    {

        String URL = Constants.DOMAIN_URL + "/" + Constants.AceptarRechazarPropuestaUbicacion;


        JSONObject a= new JSONObject();
        try {
            a.put("id_num_empleado",aceptarRechazarPropuestaUbicacion.getId_num_empleado1());
            a.put("token", Encryption.md5((aceptarRechazarPropuestaUbicacion.getId_num_empleado1()) + Encryption.getHour())+"");
            a.put("va_numero_pos",aceptarRechazarPropuestaUbicacion.getVa_numero_pos());
            a.put("va_nombre_pos",aceptarRechazarPropuestaUbicacion.getVa_nombre_pos());
            a.put("typeMov",aceptarRechazarPropuestaUbicacion.getTypeMov());
            a.put("motivo", aceptarRechazarPropuestaUbicacion.getMotivo());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("ddd",a.toString());

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
            out.write(a+"");
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
                aceptarRechazarPropuestaUbicacion.setSuccess(false);
                aceptarRechazarPropuestaUbicacion.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;


            try{
                obj = new JSONObject(resultado);

                aceptarRechazarPropuestaUbicacion.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    aceptarRechazarPropuestaUbicacion.setSuccess(true);

                }
                else{

                    aceptarRechazarPropuestaUbicacion.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                aceptarRechazarPropuestaUbicacion.setMensajeError(e1.toString());
                aceptarRechazarPropuestaUbicacion.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            aceptarRechazarPropuestaUbicacion.setMensajeError(e.toString());
            aceptarRechazarPropuestaUbicacion.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            aceptarRechazarPropuestaUbicacion.setMensajeError(e.toString());
            aceptarRechazarPropuestaUbicacion.setSuccess(false);
        } catch (IOException e) {
            aceptarRechazarPropuestaUbicacion.setMensajeError(e.toString());
            e.printStackTrace();
            aceptarRechazarPropuestaUbicacion.setSuccess(false);
        }
        finally
        {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }



        return aceptarRechazarPropuestaUbicacion;
    }




}
