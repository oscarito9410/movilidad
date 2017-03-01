package com.gruposalinas.elektra.movilidadgs.async;


import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.webservices.RegisterMovementWS;

import android.os.AsyncTask;
import android.util.Log;

public class RegisterMovementAsync extends AsyncTask<RegistroGPS, Void, RegistroGPS>{
	private static final String TAG = "REGISTER_MOVEMENT_ASYNC";
	private EmployeeLocationService service;


	public RegisterMovementAsync(EmployeeLocationService service) {
		super();
		this.service = service;
	}

	protected RegistroGPS doInBackground(RegistroGPS... params)
	{

			RegisterMovementWS movementWS = new RegisterMovementWS();
			RegistroGPS result = movementWS.setMovement(params[0]);

		Log.i(TAG, "TEST doInBackground");

		return result;
	}

	protected void onPostExecute(RegistroGPS result)
	{
		Log.i(TAG, "TEST onPostExecute");
		service.onPositionSaved(result);


    }
}