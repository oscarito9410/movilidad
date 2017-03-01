package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;
import com.gruposalinas.elektra.movilidadgs.beans.ObtenerEstatusAlerta;
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
 * Created by Adrian guzman on 31/03/2016.
 */
public class ObtenerEstatusAlertaWS
{

    public static String TAG = "checar ObtenerEstatusAlerta";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";

    public ObtenerEstatusAlerta obtenerEstatusAlerta(ObtenerEstatusAlerta obtenerEstatusAlerta)
    {
        //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/ObtenerEstatusAlerta";// DESARROLLO
       // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/ObtenerEstatusAlerta";
        String URL = Constants.DOMAIN_URL + "/" + Constants.ObtenerEstatusAlerta;

        String pruebas = null;

        JSONObject a= new JSONObject();
        try {
            a.put("token",Encryption.md5((obtenerEstatusAlerta.getId_numero_empleado() + Encryption.getHour()))+"");
            a.put("id_num_empleado",(obtenerEstatusAlerta.getId_numero_empleado())+"");
            pruebas=a.toString();
            System.out.print(pruebas);

            Log.d(TAG, "inicia WS Enviar ObtenerEstatusAlerta " + a.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            out.write(pruebas);
            out.close();
            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                    resultado=sb.toString();
                }
                br.close();

                System.out.println("Obtener estatus de la alerta:"+sb.toString());

            }else{
                System.out.println(urlConnection.getResponseMessage());
                obtenerEstatusAlerta.setSuccess(false);
                obtenerEstatusAlerta.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;


            try{
                obj = new JSONObject(resultado);

                obtenerEstatusAlerta.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    obtenerEstatusAlerta.setSuccess(true);
                    obtenerEstatusAlerta.setsuccess(obj.getBoolean("activo"));
                    obtenerEstatusAlerta.setFecha(obj.getString("fecha_desactivacion"));
                    obtenerEstatusAlerta.setNombre_desactivo(obj.getString("nombre_desactivo"));


                }
                else{

                    obtenerEstatusAlerta.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                obtenerEstatusAlerta.setMensajeError(e1.toString());
                obtenerEstatusAlerta.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            obtenerEstatusAlerta.setMensajeError(e.toString());
            obtenerEstatusAlerta.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            obtenerEstatusAlerta.setMensajeError(e.toString());
            obtenerEstatusAlerta.setSuccess(false);
        } catch (IOException e) {
            obtenerEstatusAlerta.setMensajeError(e.toString());
            e.printStackTrace();
            obtenerEstatusAlerta.setSuccess(false);
        }
        finally
        {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }



        return obtenerEstatusAlerta;
    }


}
