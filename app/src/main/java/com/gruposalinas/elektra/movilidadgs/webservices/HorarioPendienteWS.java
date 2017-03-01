package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Horapendiente;
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
 * Created by Adrian Guzman on 28/01/2016.
 */
public class HorarioPendienteWS
{
    public static String TAG = "HorarioPendiente";

 String Envio;
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public HorarioPendienteWS(String enviado)
    {
        this.Envio=enviado;

    }

    public Horapendiente pendiente (Horapendiente horapendiente)
    {
        horapendiente.setSuccess(false);

       // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/registrarempleadoprop";// produccion
        //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/registrarempleadoprop";//desarrollo
        String URL = Constants.DOMAIN_URL + "/" + Constants.registrarempleadoprop+"Copia";


        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObject1= new JSONObject();
        JSONArray jsonArray= new JSONArray();
        try {
            jsonObject.put("bit_valido",horapendiente.isBit_valido());
            jsonObject.put("id_num_empleado", (horapendiente.getId_empleado()));
            jsonObject.put("ti_dia_semana",horapendiente.getTi_dia_semana());
            jsonObject.put("tm_hora_entrada",horapendiente.getTm_hora_entrada()+"");
            jsonObject.put("tm_hora_salida",horapendiente.getTm_hora_salida()+"");
            jsonArray.put(jsonObject);
            jsonObject1.put("id_num_empleado",(horapendiente.getId_empleado()));
            jsonObject1.put("token",Encryption.md5((horapendiente.getId_empleado()) + Encryption.getHour()));
            jsonObject1.put("horario",jsonArray);
            jsonObject1.put("tipo",horapendiente.getTipo());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG,"checando horario pendiente"+ jsonObject1.toString());

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
            out.write(jsonObject1+"");
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
                horapendiente.setSuccess(false);
                horapendiente.setMensajeError(urlConnection.getResponseMessage());
            }
            JSONObject obj;


            try{
                obj = new JSONObject(resultado);

                horapendiente.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {

                    horapendiente.setSuccess(true);

                }
                else{

                    horapendiente.setSuccess(false);
                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                horapendiente.setMensajeError(e1.toString());
                horapendiente.setSuccess(false);

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            horapendiente.setMensajeError(e.toString());
            horapendiente.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            horapendiente.setMensajeError(e.toString());
            horapendiente.setSuccess(false);
        } catch (IOException e) {
            horapendiente.setMensajeError(e.toString());
            e.printStackTrace();
            horapendiente.setSuccess(false);
        }

        return horapendiente;


    }
}
