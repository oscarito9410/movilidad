package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
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
 * Created by Adrian Guzman on 18/03/2016.
 */
public class RechazarJustificanteWS
{
    public static String TAG = "checar rechazar justificacion";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";

    public Incidencias incidencias(Incidencias incidencias)
    {
        //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/RechazarJustificante";// DESARROLLO
       // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/RechazarJustificante";
        String URL = Constants.DOMAIN_URL + "/" + Constants.RechazarJustificante;

        String pruebas = null;

        JSONObject a= new JSONObject();
        try {
            a.put("token", Encryption.md5((incidencias.getD_num_empleado_justifica()) + Encryption.getHour())+"");
            a.put("id_num_empleado", (incidencias.getD_num_empleado_justifica())+"");
            a.put("id_csc_justificacion",incidencias.getId_csc_justificacion());
            a.put("tipo",incidencias.getTipo()+"");
            a.put("empleado_valida",incidencias.getNumero_empleado());
            a.put("va_comentarios",incidencias.getVa_comentarios());
            pruebas=a.toString();
            System.out.print(pruebas);

            Log.d(TAG, "inicia WS Enviar rechazar " + a.toString());

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
                incidencias.setSuccess(false);
                incidencias.setMensajeError(urlConnection.getResponseMessage());
            }

            JSONObject obj;


            try{
                obj = new JSONObject(resultado);

                incidencias.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    incidencias.setSuccess(true);

                }
                else{

                    incidencias.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                incidencias.setMensajeError(e1.toString());
                incidencias.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            incidencias.setMensajeError(e.toString());
            incidencias.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            incidencias.setMensajeError(e.toString());
            incidencias.setSuccess(false);
        } catch (IOException e) {
            incidencias.setMensajeError(e.toString());
            e.printStackTrace();
            incidencias.setSuccess(false);
        }
        finally
        {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        return incidencias;
    }

}
