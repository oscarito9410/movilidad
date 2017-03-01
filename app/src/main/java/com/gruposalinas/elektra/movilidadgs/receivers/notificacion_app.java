package com.gruposalinas.elektra.movilidadgs.receivers;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.adrian.vistaweb.vista_web_descarga;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.ObtenerConfiguracionesAplicacion;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class notificacion_app extends BroadcastReceiver {

    Context context;
    String url;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        this.context=context;
      TareaVersion(context);



    }

    public void TareaVersion(Context context)
    {
        String versionS_O= Build.VERSION.RELEASE;
        String modelo_celular=Build.MANUFACTURER +" "+Build.MODEL;
        String id_IMEI= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        Incidencias incidencias= new Incidencias();
        String numero_Empleado=context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
        incidencias.setNumero_empleado(numero_Empleado);
        incidencias.setVersion(context.getString(R.string.version_name));
        incidencias.setSistema("ANDROID");
        incidencias.setVersion_S_O(versionS_O);
        incidencias.setModelo_Celular(modelo_celular);
        incidencias.setID_Dispositivo(id_IMEI);
        ObtenerConfiguracionesAplicacion obtenerConfiguracionesAplicacion= new ObtenerConfiguracionesAplicacion(this);
        obtenerConfiguracionesAplicacion.execute(incidencias);
    }

    public  void validarversion(Incidencias incidencias)
    {
        if(incidencias.isSuccess())
        {
            String fechapura;
            StringTokenizer tokenizer = new StringTokenizer(incidencias.getFECHA_LIMITE());
            int contador1 = 0;
            String[] datos = new String[2];
            while (tokenizer.hasMoreTokens())
            {
                datos[contador1] = tokenizer.nextToken();
                contador1++;
            }
            fechapura=datos[0];
            int contador=0;
            String datos1[]= new String[3];
            StringTokenizer tokenizer1 = new StringTokenizer(fechapura,"-");

            while (tokenizer1.hasMoreTokens())
            {
                datos1[contador]=tokenizer1.nextToken();
                contador++;
            }

            String finalfecha1=datos1[2]+"-"+datos1[1]+"-"+datos1[0];
            String finalfecha=datos1[0]+"-"+datos1[1]+"-"+datos1[2];

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");

url=incidencias.getURL();
            String currentDateandTime = sdf.format(new Date());
            Date date1;
            Date date2;
            try {
                date2 = sdf1.parse(finalfecha);
               date1= sdf.parse(currentDateandTime);

                if (date1.compareTo(date2)<0){
                    System.out.print("es menor");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (currentDateandTime.compareTo(finalfecha)<0 || currentDateandTime.compareTo(finalfecha)>0)
            {
                if(context.getString(R.string.version_name).compareTo(incidencias.getVersion())<0)
                {
                    notificacionversion(finalfecha1,url);

                }

            }

            if(currentDateandTime.compareTo(finalfecha)==0)
            {
                if (context.getString(R.string.version_name).compareTo(incidencias.getVersion())<0)
                {
                    notificacionversion(finalfecha1,url);

                }


            }


        }

    }

    private void notificacionversion(String finalfecha ,String url1)
    {
        int notificationId = 1;
        long[] pattern = new long[]{1000,500,1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_socio_mas_x2)
                .setContentTitle(context.getText(R.string.versionnombre))
                .setContentText(context.getText(R.string.versionaccion)+finalfecha)
                .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE,1,2).setSound(defaultSound).setCategory(NotificationCompat.CATEGORY_MESSAGE).setPriority(NotificationCompat.PRIORITY_MAX);

        Intent intent = new Intent(context, vista_web_descarga.class);
        intent.putExtra("URL",url1);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(vista_web_descarga.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCompat.setAutoCancel(true);
        notificationManager.notify(notificationId, notificationCompat.build());
    }


}
