package com.gruposalinas.elektra.movilidadgs.geolocation;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.async.RegistrarEventoTelefonoASync;
import com.gruposalinas.elektra.movilidadgs.beans.ObtenerEstatusAlerta;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;

public class serviceEvento extends Service
{
    boolean revisar=false;
    Handler handler;
    Runnable runnable;
    private DBHelper mDBHelper;
    Date date;
    Timer timer;
    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.d("Se creo","Servicio Creado");

        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        //wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
        PowerManager.WakeLock wakeLock1=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DonotSlepp");
        wakeLock1.acquire();

        mandar();

         timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                datos_noEnviados();

            }
        }, 0, 1000);


        wakeLock1.release();

    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);






    }

    @SuppressLint("LongLogTag")
    private void mandar()
    {
        date= new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);

      String  dateString = dateFormatter.format(date);
      String  hourString = timeFormatter.format(date);

        int Evento=getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getInt(Constants.EVENTO, 0);
       // String fechaEvento=getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.FECHA_EVENTO, "sin fecha");
        String numeroEmpleado=getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
        ObtenerEstatusAlerta obtener=new ObtenerEstatusAlerta(numeroEmpleado,false,fechaEvento(),Evento,Utils.getBatteryLevel(this),Utils.generateMovementId(dateString,hourString));
        guardar(obtener);

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }

    private String  fechaEvento()
    {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);
        String dateString = dateFormatter.format(date);
        String hourString = timeFormatter.format(date);
        return generateJsonDate(dateString,hourString);

    }

    private String generateJsonDate(String fecha ,String hora){
        //Setting jsonDate
        String jsonDate = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(fecha + " " + hora);
        } catch (java.text.ParseException e) {
            Log.e("error", "Error al parsear fecha");
        }
        jsonDate = Utils.getJsonDate(date);

        return jsonDate;
    }

    public void guardar(ObtenerEstatusAlerta obtenerEstatusAlerta)
    {
         Dao dao;

        try {
            if(obtenerEstatusAlerta.isSuccess()){
                Log.i("se envio", "21- el evento " + obtenerEstatusAlerta.getIdEvento());
            }
            else{

                Log.i("sin enviar","pendiente");
            }

            boolean isNew = true;

            //TODO revisar primero si ya existe el ID, para crearlo o actualizarlo
            //TODO compara el registro con los de BD
            DBHelper dbHelper = null;
            List<ObtenerEstatusAlerta> registrosTemp = null;

            dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getObtenerEstatusAlertasDAO();


            try {
                Log.i("buscando", "23-Test probando buscar registro con ID = " + obtenerEstatusAlerta.getIdEvento());
                QueryBuilder queryBuilder = dao.queryBuilder();
                queryBuilder.setWhere(queryBuilder.where().eq(ObtenerEstatusAlerta.IDEVENTO, obtenerEstatusAlerta.getIdEvento()));
                registrosTemp = dao.query(queryBuilder.prepare());

                Log.d("", "24.1- ID = " + registrosTemp.get(0).getIdEvento() + "/" + registrosTemp.get(0).isSuccess());
                Log.e("", "Result id = " + obtenerEstatusAlerta.getIdEvento());
                Log.e("", "registroTemp id = " + registrosTemp.get(0).getIdEvento());
                if (registrosTemp == null){
                    Log.i("", "25- No se ha encontrado nada");
                }
                else if(registrosTemp.size() == 0 && !registrosTemp.get(0).getIdEvento().equals(obtenerEstatusAlerta.getIdEvento())) {
                    Log.i("", "26- Ningún registro con id = " + obtenerEstatusAlerta.getIdEvento());
                } else {
                    Log.i("", "27- Recuperado registro con id = " + registrosTemp.get(0).getIdEvento());
                    isNew = false;
                }

            }catch(Exception e){
                Log.e("", "28- No se hizo la búsqueda el registro");
            }

            if(isNew){

                dao.create(obtenerEstatusAlerta);

            }
            else{
                if(obtenerEstatusAlerta.isSuccess()){

                    dao.update(obtenerEstatusAlerta);

                }
                else{
                    Log.i("", "33- Falló el reintento de mandar la solicitud");
                }
            }

        } catch (SQLException e) {
            Log.e("", "33- Error");
        }



    }

    @SuppressLint("LongLogTag")
    public int datos_noEnviados(){
        Dao dao;
        DBHelper dbHelper = null;
        try {
            String noEmpleado = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, "");
            dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getObtenerEstatusAlertasDAO();

            QueryBuilder queryBuilder = dao.queryBuilder();
            queryBuilder.setWhere(queryBuilder.where().eq(ObtenerEstatusAlerta.ID, noEmpleado)
                    .and().eq(ObtenerEstatusAlerta.SUCCESS, false));

            List<ObtenerEstatusAlerta> registros = dao.query(queryBuilder.prepare());
            System.out.print("datos no "+registros.size());

            if(registros.size()>0){
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

                //For 3G check
                boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .isConnectedOrConnecting();

                //	For WiFi Check
                boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                        .isConnectedOrConnecting();

                if(isWifi || is3g)
                {
                    for(int i=0;i<registros.size();i++){
                        RegistrarEventoTelefonoASync registrarEventoTelefonoASync= new RegistrarEventoTelefonoASync(this);
                        registrarEventoTelefonoASync.execute(registros.get(i));
                    }

                    Log.d("Enviando estado telefono","enviando");

                }else{
                    Log.d("pendiente","envio");
                }

            }
            else if(registros.size()==0 || registros.isEmpty())
            {
                timer.cancel();
                stopService(new Intent(getBaseContext(), serviceEvento.class));
                Log.d("stop", " se para el servcio nada mas que enviar");
            }

        } catch (SQLException e)
        {
            Log.d("no se obtienen el tamaño", "");
        }

        return 0;
    }
}

