package com.gruposalinas.elektra.movilidadgs.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.gruposalinas.elektra.movilidadgs.Activities.ListaIncidenciasActivity;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.IncidenciasAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class incidenciasreciver extends BroadcastReceiver {
   Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        this.context=context;
        tareaAsyncNotifi();
        //prueba();
    }


    public void tareaAsyncNotifi(){
        Incidencias incidencias= new Incidencias();

        Calendar c = Calendar.getInstance();
        // saca la fecha anterior
        c.add(Calendar.DATE, -1);
        Date date = c.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_anterior = sdf1.format(date);
        System.out.print(fecha_anterior);
        //
        String numero_Empleado=context.getSharedPreferences(Constants.SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
        incidencias.setNumero_empleado(numero_Empleado);
        // fecha actual//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        incidencias.setFecha_fin(currentDateandTime);
        //

       incidencias.setFecha_inicio(fecha_anterior);
        //incidencias.setFecha_inicio("2016-01-01");
        IncidenciasAsync incidenciasAsync= new IncidenciasAsync(this);
        incidenciasAsync.execute(incidencias);

    }


    public void validar(Incidencias incidencias)
    {
        if(incidencias.isSuccess() && (incidencias.getLista().length()>0 || incidencias.getListaCatalogo().length()>0))
        {

            int notificationId = 1;
            long[] pattern = new long[]{1000,500,1000};
            Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.app_socio_mas_x2)
                    .setContentTitle(context.getText(R.string.titlenotificacion))
                    .setContentText(context.getText(R.string.pruebanotificacionmensaje))
                    .setWhen(System.currentTimeMillis()).setVibrate(pattern).setLights(Color.WHITE,1,2).setSound(defaultSound).setCategory(NotificationCompat.CATEGORY_MESSAGE).setPriority(NotificationCompat.PRIORITY_MAX);

            Intent intent = new Intent(context, ListaIncidenciasActivity.class);
            intent.putExtra("plantilla",false);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addParentStack(ListaIncidenciasActivity.class);
            taskStackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(notificationId, PendingIntent.FLAG_UPDATE_CURRENT);
            notificationCompat.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationCompat.setAutoCancel(true);
            notificationManager.notify(notificationId, notificationCompat.build());

        }

    }


}
