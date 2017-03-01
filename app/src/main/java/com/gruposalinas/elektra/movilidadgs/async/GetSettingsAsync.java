package com.gruposalinas.elektra.movilidadgs.async;

import com.gruposalinas.elektra.movilidadgs.Activities.MainActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Configuracion;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.geolocation.HoraExacta;
import com.gruposalinas.elektra.movilidadgs.webservices.GetSettingstWS;

import android.os.AsyncTask;
import android.util.Log;

public class GetSettingsAsync extends AsyncTask<Empleado, Void, Configuracion>{
	private static final String TAG = "GEtSettingsAsync";
	MainActivity activity;
	HoraExacta horaExacta;

	public GetSettingsAsync(MainActivity _activity) {
		super();
		this.activity = _activity;
	}

	public GetSettingsAsync(HoraExacta horaExacta) {
		this.horaExacta = horaExacta;
	}

	protected Configuracion doInBackground(Empleado... params) {
		GetSettingstWS settingsWS = new GetSettingstWS();
		Configuracion result = settingsWS.setMovement(params[0]);
		Log.i(TAG, "TEST doInBackground");
		
		return result;
	}

	protected void onPostExecute(Configuracion result)
	{
		Log.i(TAG, "onPostExecute" + result.getTiempo());


		if(activity!=null){
			activity.updateSettings(result);
		}
		if(horaExacta!=null){
			horaExacta.getHora(result);
		}


        Log.i(TAG, "RESULTS: R=" + result.getRango() + " / D=" + result.getDistancia() + " / T=" + result.getTiempo());
    }
}