package com.gruposalinas.elektra.movilidadgs.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.geolocation.GPSTracker;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class horarecibe extends BroadcastReceiver {
    public horarecibe() {
    }

    Context context;
    private DBHelper mDBHelper;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO: This method is called when the BroadcastReceiver is receiving
        this.context=context;
        GPSTracker gps = new GPSTracker(context);
        muestraPosicionActual(gps);

    }
    public void muestraPosicionActual(GPSTracker _location){
        Log.i("", "17- muestraPosicionActual");


        Log.i("", "18- Latitude = " + _location.getLatitud() + "\nLongitud = " + _location.getLongitud());
      Date  date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);

        SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);

       String dateString = dateFormatter.format(date);
        String hourString = timeFormatter.format(date);


        Log.i("aa", "19- Date = " + dateString + " " + hourString);

        RegistroGPS registroGps = new RegistroGPS(_location.getLatitud(), _location.getLongitud(), context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""),
                dateString,
                hourString,
                Utils.getBatteryLevel(context),
                Utils.getJsonDate(date),
                Utils.generateMovementId(dateString, hourString),_location.getProvider());
        registroGps.setJsonDate(generateJsonDate(registroGps));
        onPositionSaved(registroGps);
    }


    private String generateJsonDate(RegistroGPS registro){
        //Setting jsonDate
        String jsonDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(registro.getFecha() + " " + registro.getHora());
        } catch (java.text.ParseException e) {
            Log.e("", "Error al parsear fecha");
        }
        jsonDate = Utils.getJsonDate(date);
        registro.setJsonDate(jsonDate);

        return jsonDate;
    }

    public void onPositionSaved(RegistroGPS result){
        Log.i("", "20- onPositionSaved");
        Log.d("", "20.1 - Registro con id = " + result.getId() + "\nFecha = " + result.getFecha() + " " + result.getHora() + "\nGeoloc = " + result.getLatitud() + "|" + result.getLongitud());
        Dao dao;

        try {
            if(result.isSuccess()){
                Log.i("", "21- Se ha mandado la ubicación exitosamente: " + result.getId());
            }
            else{
                Log.i("", "22- No se ha mandado la ubicación. Guardando en cola. Hora = " + result.getFecha() + " " + result.getHora());
                Log.i("", "22.1- jsonDate = " + result.getJsonDate());
            }

            boolean isNew = true;

            //TODO revisar primero si ya existe el ID, para crearlo o actualizarlo
            //TODO compara el registro con los de BD
            DBHelper dbHelper = null;
            List<RegistroGPS> registrosTemp = null;

            dao = DBHelper.getHelper(context.getApplicationContext(), mDBHelper).getGpsDAO();
            try {
                Log.i("", "23-Test probando buscar registro con ID = " + result.getId());
                QueryBuilder queryBuilder = dao.queryBuilder();
                queryBuilder.setWhere(queryBuilder.where().eq(RegistroGPS.ID, result.getId()));
                registrosTemp = dao.query(queryBuilder.prepare());
                Log.d("", "24.1- ID = " + registrosTemp.get(0).getId() + "/" + registrosTemp.get(0).isSuccess());
                Log.e("", "Result id = " + result.getId());
                Log.e("", "registroTemp id = " + registrosTemp.get(0).getId());
                if (registrosTemp == null){
                    Log.i("", "25- No se ha encontrado nada");
                }
                else if(registrosTemp.size() == 0 && !registrosTemp.get(0).getId().equals(result.getId())) {
                    Log.i("", "26- Ningún registro con id = " + result.getId());
                } else {
                    Log.i("", "27- Recuperado registro con id = " + registrosTemp.get(0).getId());
                    isNew = false;
                }

            }catch(Exception e){
                Log.e("", "28- No se hizo la búsqueda el registro");
            }

            if(isNew){
                Log.i("", "29- Se creará la coordenada: " + result.getJsonDate());
                dao.create(result);

                Log.i("", "30- Coordenada creado exitosamente");
            }
            else{
                if(result.isSuccess()){
                    Log.i("", "31- Se actualizará la coordenada: " + result.getJsonDate());
                    dao.update(result);

                    Log.i("", "32- Coordenada actualizada exitosamente");
                }
                else{
                    Log.i("", "33- Falló el reintento de mandar la solicitud");
                }
            }

        } catch (SQLException e) {
            Log.e("", "33- Error creando coordenada");
        }
    }

}
