package com.gruposalinas.elektra.movilidadgs.async;

import com.gruposalinas.elektra.movilidadgs.Activities.LoginActivity;
import com.gruposalinas.elektra.movilidadgs.Activities.RegistroActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.webservices.CheckEmployeesWS;

import android.os.AsyncTask;
import android.util.Log;

public class CheckEmployeesAsync extends AsyncTask<Empleado, Void, Empleado>{
	private static final String TAG = "CHECK_EMPLOYEES_ASYNC";
	LoginActivity activity;
	RegistroActivity registroActivity;
	
	public CheckEmployeesAsync(LoginActivity _activity) {
		super();
		this.activity = _activity;
	}
	public CheckEmployeesAsync (RegistroActivity registroActivity){
		this.registroActivity=registroActivity;
	}

	protected Empleado doInBackground(Empleado... params) {
		CheckEmployeesWS employeesWS = new CheckEmployeesWS();
		Empleado empleado = employeesWS.getEmployee(params[0]);
		Log.i(TAG, "TEST doInBackground");
		return empleado;
	}

	protected void onPostExecute(Empleado empleado) {
		Log.i(TAG, "TEST onPostExecute");
		if(registroActivity!=null)
		{
			registroActivity.session(empleado);
		}
		else
		{
			activity.goToMainActivity(empleado);

		}

    }
}