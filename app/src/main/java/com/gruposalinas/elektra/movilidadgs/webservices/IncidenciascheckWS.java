package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
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
import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Adrian Guzman on 04/03/2016.
 */
public class IncidenciascheckWS
{
    public static String TAG = "checar Incidencia";
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";
    int contador1=0,contador2=0,contador3=0;
    String nom[]= null;
    ArrayList <Integer> autor;
    ArrayList <Integer> pendie;
    ArrayList <Integer> sin_just;
    String temporalNombre;
    String estatus;

    public Incidencias incidencias(Incidencias incidencias)
    {

        //String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/ListadoEmpleadosConIncidencias";// DESARROLLO
        //String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/ListadoEmpleadosConIncidencias";
        String URL = Constants.DOMAIN_URL + "/" + Constants.ListadoEmpleadosConIncidencias;

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("token", Encryption.md5((incidencias.getNumero_empleado()) + Encryption.getHour()));
            jsonObject.put("id_num_empleado",(incidencias.getNumero_empleado()));
            jsonObject.put("fechaInicio",incidencias.getFecha_inicio());
            jsonObject.put("fechaFin",incidencias.getFecha_fin());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "inicia WS incidencia" + jsonObject.toString());

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

                autor= new ArrayList<>();
                pendie= new ArrayList<>();
                sin_just= new ArrayList<>();

                incidencias.setMensajeError(obj.getString("mensajeError"));
                if(obj.getString("error").equals("false"))
                {
                    JSONArray array=new JSONArray(obj.getString("listado"));
                    JSONArray array1= new JSONArray(obj.getString("listado_plantilla"));
                    incidencias.setLista(array);
                    incidencias.setListaCatalogo(array1);/// este lo usamos para las plantillas
                    nom= new String[array1.length()];

                    for (int i=0;i<array1.length();i++)
                    {
                        try {
                            JSONObject nombres=array1.getJSONObject(i);
                            nom[i]=nombres.getString("Nombre");
                            temporalNombre=nombres.getString("Nombre");

                            if(i==0)
                            {
                                estatus=nombres.getString("EstatusJustificacion");

                                switch (estatus)
                                {
                                    case "AUTORIZADO":
                                        contador1++;
                                        break;
                                    case  "PENDIENTE DE AUTORIZACION":
                                        contador2++;
                                        break;
                                    case  "SIN JUSTIFICAR":
                                        contador3++;
                                        break;

                                }


                            }

                            if(i>=1)
                            {
                                if(nom[i-1].equals(temporalNombre))
                                {
                                    estatus=nombres.getString("EstatusJustificacion");


                                    switch (estatus)
                                    {
                                        case "AUTORIZADO":
                                            contador1++;
                                            break;
                                        case  "PENDIENTE DE AUTORIZACION":
                                            contador2++;
                                            break;
                                        case  "SIN JUSTIFICAR":
                                            contador3++;
                                            break;

                                    }



                                }
                                else
                                {
                                    autor.add(contador1);
                                    pendie.add(contador2);
                                    sin_just.add(contador3);
                                    contador1=0;
                                    contador2=0;
                                    contador3=0;

                                    estatus=nombres.getString("EstatusJustificacion");


                                    switch (estatus)
                                    {
                                        case "AUTORIZADO":
                                            contador1++;
                                            break;
                                        case  "PENDIENTE DE AUTORIZACION":
                                            contador2++;
                                            break;
                                        case  "SIN JUSTIFICAR":
                                            contador3++;
                                            break;

                                    }


                                }

                            }


                        }
                        catch (JSONException e){
                            incidencias.setMensajeError(e.toString());
                            incidencias.setSuccess(false);
                        }

                    }

                    incidencias.setSuccess(true);

                }
                else{

                    incidencias.setSuccess(false);
                    incidencias.setMensajeError("Error");
                    autor.add(0);
                    pendie.add(0);
                    sin_just.add(0);

                }

                autor.add(contador1);
                pendie.add(contador2);
                sin_just.add(contador3);
                contador1=0;
                contador2=0;
                contador3=0;
                incidencias.setContador1(autor);
                incidencias.setContador2(pendie);
                incidencias.setContador3(sin_just);



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
