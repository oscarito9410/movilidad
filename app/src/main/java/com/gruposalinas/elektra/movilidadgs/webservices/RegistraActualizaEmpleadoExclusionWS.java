package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
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
 * Created by yvegav on 25/09/2016.
 */
public class RegistraActualizaEmpleadoExclusionWS
{
    public static String TAG = "Rechazar o autorizar";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public Permisos checar(Permisos permisos)
    {
        permisos.setSuccess(false);
        String URL = Constants.DOMAIN_URL + "/" + Constants.RegistraActualizaEmpleadoExclusion;


        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("id_num_empleado",permisos.getId_numero_empleado());
            jsonObject.put("token",Encryption.md5(permisos.getId_numero_empleado() + Encryption.getHour()));
            jsonObject.put("dt_fecha_hora_inicial",permisos.getFecha_inicial());
            jsonObject.put("dt_fecha_hora_final",permisos.getFecha_final());
            jsonObject.put("tipo_exclusion",Integer.valueOf(permisos.getTipo_exclusion()));
            jsonObject.put("estatus",Integer.valueOf(permisos.getEstatus()));
            jsonObject.put("comentario",permisos.getComentario());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "inicia rechazar o autorizar" + jsonObject.toString());
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
                permisos.setSuccess(false);
                permisos.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;



            try{
                obj = new JSONObject(resultado);

                permisos.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    permisos.setSuccess(true);

                }
                else{

                    permisos.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                permisos.setMensajeError(e1.toString());
                permisos.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            permisos.setMensajeError(e.toString());
            permisos.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            permisos.setMensajeError(e.toString());
            permisos.setSuccess(false);
        } catch (IOException e) {
            permisos.setMensajeError(e.toString());
            e.printStackTrace();
            permisos.setSuccess(false);
        }
        finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        Log.d(TAG, "autorizando o rechazando");

       // permisos.setRESPUESTA(TAG+" "+ " otra respuesta "+permisos.getMensajeError());

        return permisos;
    }


}
