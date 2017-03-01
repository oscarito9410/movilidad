package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
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
 * Created by Adrian Guzman on 05/02/2016.
 */
public class CheckTiendasWS
{
    public static String TAG = "checarTiendas";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";


    public Tiendas tiendas (Tiendas tiendas)
    {
        tiendas.setSuccess(false);

       //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/consultarposicionesporempleado";// DESARROLLO
       // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/consultarposicionesporempleado";
        String URL = Constants.DOMAIN_URL + "/" + Constants.consultarposicionesporempleado;

        String numero_empleado=String.valueOf(tiendas.getId_empleado());
        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("id_num_empleado",(numero_empleado));
            jsonObject.put("token",Encryption.md5((tiendas.getId_empleado())+Encryption.getHour()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "inicia tiendas"+jsonObject.toString());

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
                tiendas.setSuccess(false);
                tiendas.setMensajeError(urlConnection.getResponseMessage());
            }



            JSONObject obj;


            try{
                obj = new JSONObject(resultado);
                tiendas.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {

                    if(obj.getString("Posiciones")!=null){

                        JSONArray array=new JSONArray(obj.getString("Posiciones"));
                        tiendas.setArregloTiendas(array);


                    }
                    if(obj.getString("zonas")!=null)
                    {

                        JSONArray zonas=new JSONArray(obj.getString("zonas"));

                        tiendas.setZona(zonas);


                    }



                    tiendas.setSuccess(true);

                }
                else{

                    tiendas.setSuccess(false);

                }
            }
            catch (JSONException e1){
                e1.printStackTrace();
                tiendas.setMensajeError(e1.toString());

            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            tiendas.setMensajeError(e.toString());
            tiendas.setSuccess(false);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            tiendas.setMensajeError(e.toString());
            tiendas.setSuccess(false);
        } catch (IOException e) {
            tiendas.setMensajeError(e.toString());
            e.printStackTrace();
            tiendas.setSuccess(false);
        }

        Log.d(TAG, "checando tiendas");

        return tiendas;
    }


}
