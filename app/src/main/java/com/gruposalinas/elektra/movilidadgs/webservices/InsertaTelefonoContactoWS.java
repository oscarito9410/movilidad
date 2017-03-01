package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Multimedia;
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
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yvegav on 17/10/2016.
 */
public class InsertaTelefonoContactoWS
{
    public static String TAG = "enviar contactos";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";
    public Multimedia contactos(Multimedia multimedia)
    {
        String URL = Constants.DOMAIN_URL + "/" + Constants.InsertaTelefonoContacto;

        JSONObject Json=new JSONObject();
        JSONArray jsonArray= new JSONArray(multimedia.getListacontacos());

        try
        {
            Json.put("id_num_empleado", (multimedia.getId_numero_empleado()));
            Json.put("token", Encryption.md5((multimedia.getId_numero_empleado()) + Encryption.getHour()));
            Json.put("contactos",jsonArray);

        }
        catch (JSONException e)
        {
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
                multimedia.setSuccess(false);
                multimedia.setMensajeError(urlConnection.getResponseMessage());
            }


            JSONObject obj;
            try {
                obj = new JSONObject(resultado);

                multimedia.setMensajeError(obj.getString("mensajeError"));

                if(obj.getString("error").equals("false"))
                {
                    multimedia.setSuccess(true);
                }
                else{
                    multimedia.setSuccess(false);
                    multimedia.setMensajeError("error");
                }


            }
            catch (JSONException e){
                e.printStackTrace();
                multimedia.setMensajeError(e.getMessage());
                multimedia.setSuccess(false);
            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
            multimedia.setMensajeError(e.toString());
            multimedia.setSuccess(false);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            multimedia.setMensajeError(e.toString());
            multimedia.setSuccess(false);
        }
        catch (IOException e){
            e.printStackTrace();
            multimedia.setMensajeError(e.toString());
            multimedia.setSuccess(false);
        }





        return multimedia;
    }
}
