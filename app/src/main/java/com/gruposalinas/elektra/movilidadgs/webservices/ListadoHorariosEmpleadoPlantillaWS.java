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
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Adrian Guzman on 11/05/2016.
 */
public class ListadoHorariosEmpleadoPlantillaWS
{
    public static String TAG = "Pendientes_horarios_Validar";
    String datos1;
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";
    JSONArray arr,miHorario;
    String nombres[];
    String nombretemporal;
    ArrayList <String> lista;
    ArrayList <String>numeros;
    int activos=0,pendientes=0,liberar=0;
    String validar;
    ArrayList <Integer> cantidades;
    ArrayList <Integer> pen;
    ArrayList <Integer> libre;

    public Horarios ObtenerHorarios(Horarios horarios)
    {
        horarios.setSuccess(false);

      // String URL="http://200.38.122.208:8081/ServicioEmpleados.svc/ListadoHorariosEmpleadoPlantilla";
       // String URL="http://10.112.51.5:8083/ServicioEmpleados.svc/ListadoHorariosEmpleadoPlantilla";// DESARROLLO
        String URL = Constants.DOMAIN_URL + "/" + Constants.ListadoHorariosEmpleadoPlantilla;

        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("id_num_empleado",(horarios.getId_empleado()));
            jsonObject.put("token",Encryption.md5((horarios.getId_empleado())+Encryption.getHour()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "checando horario pendiente" + jsonObject.toString());
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
                horarios.setSuccess(false);
                horarios.setMensajeError(urlConnection.getResponseMessage());
            }



            JSONObject obj;
            try {
                obj = new JSONObject(resultado);
                arr = new JSONArray(obj.getString("horario_plantilla"));
                miHorario= new JSONArray(obj.getString("horario"));
                lista= new ArrayList<>();
                cantidades= new ArrayList<>();
                pen= new ArrayList<>();
                libre= new ArrayList<>();
                numeros= new ArrayList<>();


                horarios.setMensajeError(obj.getString("mensajeError"));

                if(obj.getString("error").equals("false"))
                {
                    horarios.setSuccess(true);
                    horarios.setDatosParse(arr);

                    nombres= new String[arr.length()];

                    for(int i=0;i<arr.length();i++){
                        try{
                            JSONObject obtenerHorarios=arr.getJSONObject(i);
                            nombres[i]=obtenerHorarios.getString("va_nombre_completo");
                            nombretemporal=obtenerHorarios.getString("va_nombre_completo");

                            if(i==0)
                            {
                                lista.add(obtenerHorarios.getString("va_nombre_completo"));
                                validar=obtenerHorarios.getString("Estatus");
                                numeros.add(obtenerHorarios.getString("id_num_empleado"));
                                if (validar.equals("VALIDO")){
                                    activos++;
                                }
                                else if(validar.equals("PROPUESTA")){
                                    pendientes++;
                                }
                                else if(validar.equals("PEND. POR LIBERAR")){
                                    liberar++;
                                }

                               }

                            if(i>=1)
                            {
                                if(nombres[i-1].equals(nombretemporal))
                                {
                                    validar=obtenerHorarios.getString("Estatus");
                                    if (validar.equals("VALIDO"))
                                    {
                                        activos++;
                                    }
                                    else if(validar.equals("PROPUESTA"))
                                    {
                                        pendientes++;
                                    }
                                    else if(validar.equals("PEND. POR LIBERAR")){
                                        liberar++;
                                    }
                                }
                                else
                                {
                                    cantidades.add(activos);
                                    pen.add(pendientes);
                                    libre.add(liberar);
                                    activos=0;
                                    pendientes=0;
                                    liberar=0;
                                    lista.add(obtenerHorarios.getString("va_nombre_completo"));
                                    validar=obtenerHorarios.getString("Estatus");
                                    numeros.add(obtenerHorarios.getString("id_num_empleado"));

                                    if (validar.equals("VALIDO"))
                                    {
                                        activos++;
                                    }
                                    else if(validar.equals("PROPUESTA"))
                                    {
                                        pendientes++;
                                    }
                                    else if(validar.equals("PEND. POR LIBERAR")){
                                        liberar++;
                                    }

                                }

                            }

                        }
                        catch (JSONException e){
                            horarios.setMensajeError(e.toString());
                            horarios.setSuccess(false);
                        }
                    }

                }
                else{
                    horarios.setSuccess(false);
                    horarios.setMensajeError("error");
                    lista.add("");
                    cantidades.add(0);
                    pen.add(0);
                    libre.add(0);
                }

                cantidades.add(activos);
                pen.add(pendientes);
                libre.add(liberar);
                activos=0;
                pendientes=0;
                liberar=0;

                horarios.setLista(lista);
                horarios.setActivos(cantidades);
                horarios.setPendiente(pen);
                horarios.setDatosParse(arr);
                horarios.setMisHorarios(miHorario);
                horarios.setLiberar(libre);
                horarios.setNumeros(numeros);

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
