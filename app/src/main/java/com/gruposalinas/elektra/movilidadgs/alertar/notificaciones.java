package com.gruposalinas.elektra.movilidadgs.alertar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.gruposalinas.elektra.movilidadgs.Activities.MainActivity;
import com.gruposalinas.elektra.movilidadgs.R;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Created by Adrian on 17/08/2016.
 */
public class notificaciones
{
    public void Iniciarserv(Context context,String titulo,String textoM,Activity activity){
        int notificationId = 1;
        long[] pattern = new long[]{1000,500,1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_socio_mas_x2)
                .setContentTitle(titulo)
                .setContentText(textoM)
                .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE,1,2).setSound(defaultSound).setCategory(NotificationCompat.CATEGORY_MESSAGE).setPriority(NotificationCompat.PRIORITY_MAX);

        Intent intent = new Intent(context, activity.getClass());
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addParentStack(activity.getClass());
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationCompat.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCompat.setAutoCancel(true);
        notificationManager.notify(notificationId, notificationCompat.build());
    }




    public void location(String titulo,String textoM,Context context){
        int notificationId = 1;
        long[] pattern = new long[]{1000,500,1000};
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.app_socio_mas_x2)
                .setContentTitle(titulo)
                .setContentText(textoM)
                .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE,1,2).setSound(defaultSound).setCategory(NotificationCompat.CATEGORY_MESSAGE).setPriority(NotificationCompat.PRIORITY_MAX);


        NotificationCompat.BigTextStyle bigTextStyle= new NotificationCompat.BigTextStyle(notificationCompat)
                .bigText(textoM)
                .setBigContentTitle(titulo);
        Intent intent = null;
        try {
          intent= new Intent(String.valueOf(ACTION_LOCATION_SOURCE_SETTINGS));

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(intent);
  PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
     notificationCompat.setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationCompat.setAutoCancel(true);
        notificationManager.notify(notificationId, bigTextStyle.build());


    }
}
