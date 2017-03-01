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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yvegav on 30/09/2016.
 */
public class ListadoEmpleadosUbicacionesWS
{
    public static String TAG = "listado_plantilla";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";
    ArrayList <String> nombres;
    ArrayList <String> numeros;
    ArrayList <String> nombre_numero;


    public Tiendas tiendas(Tiendas tiendas)
    {
        tiendas.setSuccess(false);
        String http = Constants.DOMAIN_URL + "/" + Constants.ListadoEmpleadosUbicaciones;

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id_num_empleado",(tiendas.getId_empleado()));
            jsonObject.put("token",Encryption.md5((tiendas.getId_empleado()) + Encryption.getHour()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("EntraWSListadoTienda", "entra" + jsonObject.toString());

        HttpsURLConnection urlConnection=null;
        try {
            URL url = new URL(http);
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
            if(HttpResult == HttpsURLConnection.HTTP_OK)
            {
                tiendas.setRespuesta(HttpResult);
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
                tiendas.setRespuesta(HttpResult);
                System.out.println(urlConnection.getResponseMessage());
                tiendas.setSuccess(false);
                tiendas.setMensajeError(urlConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
            tiendas.setSuccess(false);
            tiendas.setMensajeError(e.toString());
        }
        catch (IOException e) {
            tiendas.setRespuesta(100);
            tiendas.setSuccess(false);
            tiendas.setMensajeError(e.getMessage());
            e.printStackTrace();
        } finally{
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
        try {
            JSONObject  obj = new JSONObject(resultado);
            JSONArray array=new JSONArray(obj.getString("empleados_plantilla"));
           // tiendas.setArregloTiendas(array);

            tiendas.setMensajeError(obj.getString("mensajeError"));
            if(obj.getString("error").equals("false"))
            {
                tiendas.setSuccess(true);

                nombres=new ArrayList<>();
              numeros= new ArrayList<>();
                nombre_numero= new ArrayList<>();
                for(int i=0;i<array.length();i++)
                {
                    JSONObject datos_empleado=array.getJSONObject(i);
                    nombres.add(datos_empleado.getString("nombre_empleado"));
                    numeros.add(datos_empleado.getString("id_num_empleado"));
                    nombre_numero.add(datos_empleado.getString("mostrar_empleado"));

                }

                if(array.length()<0){
                    nombres.add("");
                    numeros.add("");
                    nombre_numero.add("");
                }

                tiendas.setMostrar_Nombres(nombres);
                tiendas.setNumero_empleados(numeros);
                tiendas.setMostrarCompleto(nombre_numero);

            }
            else{

                tiendas.setSuccess(false);


            }



        } catch (JSONException e)
        {
            tiendas.setSuccess(false);
            tiendas.setMensajeError(e.getMessage());
            e.printStackTrace();
        }
        Log.d("FinWSlistaTienda","fin");


        return tiendas;
    }

}
