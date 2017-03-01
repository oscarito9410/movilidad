package com.gruposalinas.elektra.movilidadgs.receivers;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.gruposalinas.elektra.movilidadgs.Activities.ListaIncidenciasActivity;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.alertar.notificaciones;
import com.gruposalinas.elektra.movilidadgs.geolocation.serviceEvento;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

public class Cambios_Configuracion extends BroadcastReceiver {

    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    Context context;
    notificaciones nnot;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        this.context=context;
       nnot= new notificaciones();


        prefs =context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        edit = prefs.edit();

        if("android.intent.action.AIRPLANE_MODE".equals(intent.getAction()))
        {
            if(isAirplaneModeOn(context))
            {
                edit.putInt(Constants.EVENTO, Constants.MODO_AVION);
                edit.apply();
                Intent pushIntent = new Intent(context, serviceEvento.class);
                context.startService(pushIntent);

            }
            else{
                edit.putInt(Constants.EVENTO, Constants.APAGARMODOAVION);
                edit.apply();
               // Intent pushIntent = new Intent(context, serviceEvento.class);
               // context.startService(pushIntent);

            }
        }

        if("android.location.PROVIDERS_CHANGED".equals(intent.getAction()))
        {

            if(gpsstatus(context)== Settings.Secure.LOCATION_MODE_OFF)
            {
                  Toast.makeText(context,"apagado",Toast.LENGTH_LONG).show();

                edit.putInt(Constants.EVENTO, Constants.APAGAR_GPS);
               // edit.putString(Constants.FECHA_EVENTO, fechaEvento());
                edit.putBoolean(Constants.PROVIDER, true);// se guarda esto para la base
                edit.apply();

                // se manda a llamar el servicio//
                Intent pushIntent = new Intent(context, serviceEvento.class);
                context.startService(pushIntent);
               // hilo.iniciar(context);



            } else {
                edit.putBoolean(Constants.PROVIDER, false);
                edit.putInt(Constants.EVENTO, Constants.PRENDERGPS);// se guarda esto para la base
                edit.apply();
                Intent pushIntent = new Intent(context, serviceEvento.class);
                context.startService(pushIntent);

            }
        }

        if("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction()))
        {
           // Toast.makeText(context,"se esta apagando",Toast.LENGTH_LONG).show();
            // se manda a llamar el servicio//
            edit.putInt(Constants.EVENTO,Constants.APAGAR_TELEFONO);
          //  edit.putString(Constants.FECHA_EVENTO, fechaEvento());
            edit.apply();
            Intent pushIntent = new Intent(context, serviceEvento.class);
            //pushIntent.putExtra("evento",Constants.APAGAR_TELEFONO);
            context.startService(pushIntent);


        }

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
        {

            Intent pushIntent = new Intent(context, serviceEvento.class);
            edit.putInt(Constants.EVENTO,Constants.PRENDERTElEFONO);
            //  edit.putString(Constants.FECHA_EVENTO, fechaEvento());
            edit.apply();
            context.startService(pushIntent);
        }

        if("android.intent.action.BATTERY_CHANGED".equals(intent.getAction()) || "android.intent.action.BATTERY_LOW".equals(intent.getAction()))
        {

            notificacionBateria();

        }


        /*
        if("android.intent.action.TIMEZONE_CHANGED".equals(intent.getAction()))
        {
            Toast.makeText(context,"cambio zona",Toast.LENGTH_LONG).show();

            edit.putInt(Constants.EVENTO,Constants.CAMBIO_ZONA);
            edit.putString(Constants.FECHA_EVENTO, fechaEvento());
            edit.commit();
        }
        */

        /*
        if("android.intent.action.TIME_SET".equals(intent.getAction())){

            Toast.makeText(context,"cambiando hora",Toast.LENGTH_LONG).show();

            edit.putInt(Constants.EVENTO,Constants.CAMBIO_HORA);
            edit.putString(Constants.FECHA_EVENTO, fechaEvento());
            edit.commit();


        }
        */

    }

    private static boolean isAirplaneModeOn(Context context) {

        return Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    public  int gpsstatus(Context context){

        ContentResolver contentResolver = context.getContentResolver();
        int mode = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
        return  mode;
    }


    private  void notificacionBateria()
    {

        int notificationId = 1;
        long[] pattern = new long[]{1000,500,1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);




        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_socio_mas_x2)
                .setContentTitle(context.getText(R.string.beteriabaja))
                .setContentText(context.getText(R.string.bateria))
                .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE,1,2).setSound(defaultSound).setCategory(NotificationCompat.CATEGORY_MESSAGE).setPriority(NotificationCompat.PRIORITY_MAX);

        Intent intent = null;
        try {
            intent = new Intent(context, Class.forName(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(ListaIncidenciasActivity.class);
        taskStackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
      //  notificationCompat.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCompat.setAutoCancel(true);
        notificationManager.notify(notificationId, notificationCompat.build());


    }


}
