package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

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
 * Created by Adrian guzman martinez on 12/05/2016.
 */
public class ValidarRechazarHorarioEmpleadoWS
{
    public static String TAG = "Validar_rechazarWS";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public Horarios ObtenerHorarios(Horarios horarios)
    {
        horarios.setSuccess(false);

    // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/ValidarRechazarHorarioEmpleado";
      //  String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/ValidarRechazarHorarioEmpleado";// DESARROLLO
        String URL = Constants.DOMAIN_URL + "/" + Constants.ValidarRechazarHorarioEmpleado;

        JSONObject a= new JSONObject();
        try {
            a.put("token", Encryption.md5(horarios.getNumerodelEmpleado() + Encryption.getHour())+"");
            a.put("id_num_empleado",horarios.getNumerodelEmpleado());
            a.put("validar",horarios.isBit_valido());
            a.put("diasArray",horarios.getTi_dia_semana());
            a.put("horaEntradaArray",horarios.getTm_hora_entrada());
            a.put("horaSalidaArray",horarios.getTm_hora_salida());
            a.put("comentario",horarios.getComentario());
            a.put("id_num_empleado_logeado",horarios.getId_empleado());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "checando horario de empleado" + a);
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
            if(HttpResult == HttpsURLConnection.HTTP_OK){
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
                horarios.setSuccess(false);
                horarios.setMensajeError(urlConnection.getResponseMessage());
            }



            JSONObject obj;
            try {
                obj = new JSONObject(resultado);

                horarios.setMensajeError(obj.getString("mensajeError"));

                if(obj.getString("error").equals("false"))
                {
                   horarios.setSuccess(true);
                }
                else{
                    horarios.setSuccess(false);
                    horarios.setMensajeError("error");
                }


            }
            catch (JSONException e){
                e.printStackTrace();
                horarios.setMensajeError(e.getMessage());
                horarios.setSuccess(false);
            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            horarios.setMensajeError(e.toString());
            horarios.setSuccess(false);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            horarios.setMensajeError(e.toString());
            horarios.setSuccess(false);
        }
        catch (IOException e){
            e.printStackTrace();
            horarios.setMensajeError(e.toString());
            horarios.setSuccess(false);
        }


        return  horarios;
    }

}
