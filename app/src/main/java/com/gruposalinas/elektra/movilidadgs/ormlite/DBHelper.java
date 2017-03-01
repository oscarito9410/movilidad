package com.gruposalinas.elektra.movilidadgs.ormlite;

import java.sql.SQLException;

import com.gruposalinas.elektra.movilidadgs.beans.Configuracion;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.ObtenerEstatusAlerta;
import com.gruposalinas.elektra.movilidadgs.beans.RangoMonitoreo;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG 				= "DB_HELPER";
    private static final String DATABASE_NAME 		= "movilidad_gs.db";
    private static final int 	DATABASE_VERSION 	=3;
 
    private Dao<Empleado, Integer> 			empleadoDAO;
    private Dao<RegistroGPS, Integer> 		gpsDAO;
    private Dao<Configuracion, Integer>		configuracionDAO;
    private Dao<RangoMonitoreo, Integer>	rangoMonitoreoDAO;
    private Dao<ObtenerEstatusAlerta,Integer> obtenerEstatusAlertasDAO;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {

                TableUtils.createTable(connectionSource, Empleado.class);
                TableUtils.createTable(connectionSource, RegistroGPS.class);
                TableUtils.createTable(connectionSource, Configuracion.class);
                TableUtils.createTable(connectionSource, RangoMonitoreo.class);
            TableUtils.createTable(connectionSource, ObtenerEstatusAlerta.class);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try {
            if(oldVersion<2)
            {
                if(gpsDAO.isTableExists())
                {
                    TableUtils.dropTable(connectionSource,RegistroGPS.class,false);
                    TableUtils.createTable(connectionSource, RegistroGPS.class);



                }

            }
            if(oldVersion<3){
                TableUtils.createTable(connectionSource, ObtenerEstatusAlerta.class);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

 
    public Dao<Empleado, Integer> getEmpleadoDAO() throws SQLException {
        if (empleadoDAO == null) {
            empleadoDAO = getDao(Empleado.class);
        }
        return empleadoDAO;
    }
    
    public Dao<RegistroGPS, Integer> getGpsDAO() throws SQLException {
        if (gpsDAO == null) {
        	gpsDAO = getDao(RegistroGPS.class);
        }
        return gpsDAO;
    }
 
    public Dao<Configuracion, Integer> getConfiguracionDao() throws SQLException {
        if (configuracionDAO == null) {
        	configuracionDAO = getDao(Configuracion.class);
        }
        return configuracionDAO;
    }
    
    public Dao<RangoMonitoreo, Integer> getRangosMonitoreoDao() throws SQLException {
        if (rangoMonitoreoDAO == null) {
        	rangoMonitoreoDAO = getDao(RangoMonitoreo.class);
        }
        return rangoMonitoreoDAO;
    }
    public Dao<ObtenerEstatusAlerta,Integer> getObtenerEstatusAlertasDAO()throws SQLException{
        if(obtenerEstatusAlertasDAO==null){
            obtenerEstatusAlertasDAO=getDao(ObtenerEstatusAlerta.class);
        }
        return obtenerEstatusAlertasDAO;
    }

    @Override
    public void close() {
        super.close();
        empleadoDAO = null;
        gpsDAO 		= null;
    }
    
    //Borra la tabla de Empleado
    public void dropEmpleado(ConnectionSource connectionSource, int table){
    	if(table == 0){
    		try{
    			TableUtils.dropTable(connectionSource, Empleado.class, true);
    			Log.d(TAG, "Se ha borrado la tabla de Empleado");
    		}catch(Exception e){
    			Log.e(TAG, "No se pudo borrar la tabla de Empleado");
    		}
    	}	
    }
    	
    //Borra la tabla de GPS
    public void dropGps(ConnectionSource connectionSource, int table){
    	if(table == 0){
    		try{
    			TableUtils.dropTable(connectionSource, RegistroGPS.class, true);
    			Log.d(TAG, "Se ha borrado la tabla de GPS Status");
    		}catch(Exception e){
    			Log.e(TAG, "No se pudo borrar la tabla de GPS Status");
    		}
    	}	
    }
    
  //Borra la tabla de Configuracion
    public void dropConfiguracion(ConnectionSource connectionSource, int table){
    	if(table == 0){
    		try{
    			TableUtils.dropTable(connectionSource, Configuracion.class, true);
    			Log.d(TAG, "Se ha borrado la tabla de Configuracion");
    		}catch(Exception e){
    			Log.e(TAG, "No se pudo borrar la tabla de Configuracion");
    		}
    	}	
    }
    
  //Borra la tabla de RangoMonitoreo
    public void dropRangoMonitoreo( int table){
    	if(table == 0){
    		try{
    			TableUtils.dropTable(connectionSource, RangoMonitoreo.class, true);
    			Log.d(TAG, "Se ha borrado la tabla de RangoMonitoreo");
    		}catch(Exception e){
    			Log.e(TAG, "No se pudo borrar la tabla de RangoMonitoreo");
    		}
    	}	
    }

    
    public static DBHelper getHelper(Context context, DBHelper dbHelper) {
        if (dbHelper == null) {
        	dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
        }
        return dbHelper;
    }
}