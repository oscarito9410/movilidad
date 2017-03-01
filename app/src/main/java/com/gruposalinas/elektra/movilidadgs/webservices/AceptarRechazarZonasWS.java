package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.AceptarRechazarPropuestaUbicacion;
import com.gruposalinas.elektra.movilidadgs.beans.AceptarRechazarZonas;
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
public class AceptarRechazarZonasWS
{
    public static String TAG = "Rechazar univacion";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public AceptarRechazarZonas aceptarRechazarZonas(AceptarRechazarZonas aceptarRechazarZonas)
    {

        String URL = Constants.DOMAIN_URL + "/" + Constants.AceptarRechazarZonas;


        JSONObject a= new JSONObject();
        try {
            a.put("id_num_empleado",aceptarRechazarZonas.getD_num_empleado6());
            a.put("token", Encryption.md5((aceptarRechazarZonas.getD_num_empleado6()) + Encryption.getHour())+"");
            a.put("id_csc_zo_pos",aceptarRechazarZonas.getId_csc_zo_pos());
            a.put("status",aceptarRechazarZonas.getStatus());
            a.put("va_comentario",aceptarRechazarZonas.getComentario44());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("ddd", a.toString());

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
                aceptarRechazarZonas.setSuccess(false);
                aceptarRechazarZonas.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;


            try{
                obj = new JSONObject(resultado);

                aceptarRechazarZonas.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    aceptarRechazarZonas.setSuccess(true);

                }
                else{

                    aceptarRechazarZonas.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                aceptarRechazarZonas.setMensajeError(e1.toString());
                aceptarRechazarZonas.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            aceptarRechazarZonas.setMensajeError(e.toString());
            aceptarRechazarZonas.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            aceptarRechazarZonas.setMensajeError(e.toString());
            aceptarRechazarZonas.setSuccess(false);
        } catch (IOException e) {
            aceptarRechazarZonas.setMensajeError(e.toString());
            e.printStackTrace();
            aceptarRechazarZonas.setSuccess(false);
        }
        finally
        {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }



        return aceptarRechazarZonas;
    }


}
