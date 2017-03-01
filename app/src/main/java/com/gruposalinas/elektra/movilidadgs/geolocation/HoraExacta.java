package com.gruposalinas.elektra.movilidadgs.geolocation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.alertar.notificaciones;
import com.gruposalinas.elektra.movilidadgs.async.GetSettingsAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Configuracion;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.RangoMonitoreo;
import com.gruposalinas.elektra.movilidadgs.receivers.horarecibe;
import com.gruposalinas.elektra.movilidadgs.receivers.recibeSalida;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Encryption;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HoraExacta extends Service
{
    Handler handler;
    Runnable runnable;
    notificaciones Notif;
    public HoraExacta() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        PowerManager.WakeLock wakeLock1=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DonotSlepp");

        wakeLock1.acquire();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, HoraExacta.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);

        Calendar alarmStartTime = Calendar.getInstance();

        alarmStartTime.set(Calendar.HOUR_OF_DAY, 0);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);


        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        // parte para notificacion//
        Notif= new notificaciones();

        Log.i("hora exacta", "1- Service Created");

        handler= new Handler();
        runnable= new Runnable() {
            @Override
            public void run() {

                LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                {
                    Notif.location("Ubicación", "Haz desactivado las ubicaciones de tu dispositivo vuelve activarlos para no generar incidencias.", getApplicationContext());


                }

                handler.postDelayed(runnable,60000);

            }
        };
        handler.post(runnable);


        wakeLock1.release();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        tareaAsync();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void tareaAsync()
    {

        String numeroEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
        Empleado horarios= new Empleado();
        horarios.setIdEmployee(numeroEmpleado);

        GetSettingsAsync getSettingsAsync= new GetSettingsAsync(this);
        getSettingsAsync.execute(horarios);

    }

    public void getHora(Configuracion configuracion){

        String Entrada = null;
        String Salida = null;
        String minutoEntrada = null;
        String minutoSalida = null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);


        int dia = 7;

        if (dayOfTheWeek.equalsIgnoreCase("Lunes")) {

            dia = 1;
        } else if (dayOfTheWeek.equalsIgnoreCase("Martes")) {
            dia = 2;

        } else if (dayOfTheWeek.equalsIgnoreCase("Miércoles")) {
            dia = 3;

        } else if (dayOfTheWeek.equalsIgnoreCase("Jueves")) {
            dia = 4;

        } else if (dayOfTheWeek.equalsIgnoreCase("Viernes")) {

            dia = 5;
        } else if (dayOfTheWeek.equalsIgnoreCase("Sábado")) {
            dia = 6;
        } else if (dayOfTheWeek.equalsIgnoreCase("Domingo")) {

            dia = 0;
        }

        if (configuracion.isSuccess()) {

            ArrayList<RangoMonitoreo> rangosMonitoreo = configuracion.getRangosMonitoreo();


            for (int i = 0; i < rangosMonitoreo.size(); i++) {

                if (dia == rangosMonitoreo.get(i).getNumeroDia()) {
                    int tama = rangosMonitoreo.get(i).getHoraInicial().length();
                    int a = rangosMonitoreo.get(i).getHoraFinal().length();
                    if (tama == 8) {
                        Entrada = rangosMonitoreo.get(i).getHoraInicial();
                        minutoEntrada = rangosMonitoreo.get(i).getHoraInicial().substring(3, 5);
                        Entrada = Entrada.substring(0, 2);

                    }
                    if (tama == 4) {
                        Entrada = rangosMonitoreo.get(i).getHoraInicial();
                        Entrada = Entrada.substring(0, 1);
                        minutoEntrada = rangosMonitoreo.get(i).getHoraInicial().substring(2, 4);
                        System.out.print(minutoEntrada);

                    }
                    if (a == 8) {
                        Salida = rangosMonitoreo.get(i).getHoraFinal();
                        minutoSalida = rangosMonitoreo.get(i).getHoraFinal().substring(3, 5);
                        Salida = Salida.substring(0, 2);

                    }
                    if (a == 4) {
                        Salida = rangosMonitoreo.get(i).getHoraFinal();
                        minutoSalida = Salida.substring(2, 4);
                        Salida = Salida.substring(0, 1);

                    }

                }


            }

            if(Entrada==null && Salida==null)
            {
                Entrada="0";
                Salida="0";
                minutoEntrada="0";
                minutoSalida="0";
            }

            AlarmaEntrada(Integer.valueOf(Entrada),Integer.valueOf(minutoEntrada));

            AlarmaSalida(Integer.valueOf(Salida), Integer.valueOf(minutoSalida));

        }

    }



    public void AlarmaEntrada(int entrada,int min) {
        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent1 = new Intent(this, horarecibe.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent1, 0);

        Calendar alarmStartTime1 = Calendar.getInstance();

        alarmStartTime1.set(Calendar.HOUR_OF_DAY, entrada);

        alarmStartTime1.set(Calendar.MINUTE, min);

        alarmStartTime1.set(Calendar.SECOND, 0);

      //  alarmManager1.setRepeating(AlarmManager.RTC, alarmStartTime1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager1.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime1.getTimeInMillis(), pendingIntent);
        }
        else{
            alarmManager1.setRepeating(AlarmManager.RTC, alarmStartTime1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
    }



    public void AlarmaSalida(int entrada,int min) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, recibeSalida.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        Calendar alarmStartTime = Calendar.getInstance();

        alarmStartTime.set(Calendar.HOUR_OF_DAY,entrada);

        alarmStartTime.set(Calendar.MINUTE, min);

        alarmStartTime.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarmStartTime.getTimeInMillis(),pendingIntent);
        }
        else{
            alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}
