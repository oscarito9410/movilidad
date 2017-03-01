package com.gruposalinas.elektra.movilidadgs.webservices;

import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Adrian Guzman on 14/03/2016.
 */
public class ObtenerConfiguracionesAplicacionWS
{
    public static String TAG = "Check Versión del sistema de la aplicación";
    String datos1;
    String line=null;
    StringBuilder sb = new StringBuilder();
    String resultado="";

    public Incidencias incidencias(Incidencias incidencias)
    {
        //String URL="http://10.112.51.5/ServicioEmpleados.svc/ObtenerConfiguracionesAplicacion";// DESARROLLO
      // String URL="https://200.38.122.208/ServicioEmpleados.svc/ObtenerConfiguracionesAplicacion";
        String URL = Constants.DOMAIN_URL + "/" + Constants.ObtenerConfiguracionesAplicacion;


        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("token",Encryption.md5((incidencias.getNumero_empleado())+Encryption.getHour()));
            jsonObject.put("id_num_empleado",((incidencias.getNumero_empleado())));
            jsonObject.put("sistema",incidencias.getSistema());
            jsonObject.put("version_actual",incidencias.getVersion());
            jsonObject.put("id_dispositivo",incidencias.getID_Dispositivo());
            jsonObject.put("modelo_celular",incidencias.getModelo_Celular());
            jsonObject.put("version_sistema_operativo",incidencias.getVersion_S_O());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "inicia WS version App" + jsonObject.toString());

       // HttpURLConnection urlConnection=null;
        HttpsURLConnection urlConnection=null;

        try{
         //   Esta parte sirve para saltar la validacion del certificado//
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }};

            // Ignore differences between given hostname and certificate hostname
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };

            // Install the all-trusting trust manager

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
               HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
               HttpsURLConnection.setDefaultHostnameVerifier(hv);




            ///fin de salta validacion///
            java.net.URL url = new URL(URL);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
          //  urlConnection.setHostnameVerifier(Utils.hostnameVerifier());// valida el hostname
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
            OutputStreamWriter out = new   OutputStreamWriter(urlConnection.getOutputStream());
           out.write(jsonObject+"");
            out.close();
            int HttpsResult =urlConnection.getResponseCode();
            if(HttpsResult == HttpsURLConnection.HTTP_OK)
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
                    incidencias.setFECHA_LIMITE(obj.getString("FECHA"));
                    incidencias.setURL(obj.getString("URL"));
                    incidencias.setVersion(obj.getString("VERSION"));
                    incidencias.setNumero_jefe(obj.getString("NUM_JEFE"));

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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally
        {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        return incidencias;
    }


}
