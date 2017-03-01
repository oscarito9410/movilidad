package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Horapendiente;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
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
 * Created by Adrian Guzman on 21/06/2016.
 */
public class EditarEmpleadoHorarioServicioWS
{
    public static String TAG = "EliminarHorario";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";

    public Horapendiente setEliminar(Horapendiente horarios)
    {

        String URL = Constants.DOMAIN_URL + "/" + Constants.ELiminar_horario;

        JSONObject Json=new JSONObject();

        try {
            Json.put("token", Encryption.md5(horarios.getId_empleado() + Encryption.getHour())+"");
            Json.put("id_num_empleado",(horarios.getId_empleado()));
            Json.put("id_num_empleado_logeado",(horarios.getId_empleadoLogeado()));
            Json.put("dia_semana",horarios.getTi_dia_semana());
            Json.put("nv_hora_entrada",horarios.getTm_hora_entrada());
            Json.put("nv_hora_salida",horarios.getTm_hora_salida());
            Json.put("edicion",horarios.getEdicion());
            Json.put("comentario",horarios.getComentario());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "checando Json" + Json.toString());
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
            out.write(Json+"");
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

        return horarios;


    }

}
