package com.gruposalinas.elektra.movilidadgs.geolocation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gruposalinas.elektra.movilidadgs.utils.Constants;

public class GPSTracker extends Service  implements LocationListener  {
	private static final String TAG = "GPS";
	private Context contexto;

	boolean habilitadoGPS = false;
	boolean hayRed = false;
	boolean puedeLocalizar = false;
	Location location=null;
	double latitud;
	double longitud;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 metros
	private static final long MIN_TIME_BW_UPDATES = 0; // 1 minuto
	int provider;

	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.contexto = context;
		localiza();
	}


	public Location localiza() {
		try {
			locationManager = (LocationManager)contexto.getSystemService(LOCATION_SERVICE);

		//	habilitadoGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			hayRed = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


			if (!habilitadoGPS && !hayRed) {
				// no hay Red
			}
			else {
				this.puedeLocalizar = true;
				if (habilitadoGPS)
				{
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						location.getAltitude();
						location.getLongitude();
						location.getProvider();
						if (location != null) {
							latitud = location.getLatitude();
							longitud = location.getLongitude();
							if(location.getProvider().equals("gps"))
							{
								if(contexto.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getBoolean(Constants.PROVIDER, false)){
									provider=0;
								}
								else
								{
									provider=1;
								}


							}

						}
					}

				}

				if (hayRed) {
					if (location == null)
					{
						locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							location.getAltitude();
							location.getLongitude();
							location.getProvider();

							if (location != null) {
								latitud = location.getLatitude();
								longitud = location.getLongitude();

								if(location.getProvider().equals("network"))
								{

									if(contexto.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getBoolean(Constants.PROVIDER, false)){
										provider=0;
									}
									else{
										provider=2;
									}

								}

							}
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Error GPS: " + e.toString());


				provider=3;

		}

		return location;


	}

	public void noLocalizar(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	public double getLatitud(){
		if(location != null){
			latitud = location.getLatitude();
		}
		return latitud;
	}

	public double getLongitud(){
		if(location != null){
			longitud = location.getLongitude();
		}

		return longitud;
	}

	public int  getProvider()
	{
		if(location!=null){
			if(location.getProvider().equals("network")){
				provider=2;
			}
			if(location.getProvider().equals("gps")){
				provider=1;
			}
		}

		return provider;

	}




	public boolean puedeLocalizar() {
		return this.puedeLocalizar;
	}


	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}


	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}