package com.gruposalinas.elektra.movilidadgs.async;

import com.gruposalinas.elektra.movilidadgs.Activities.LoginActivity;
import com.gruposalinas.elektra.movilidadgs.Activities.RegistroActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.webservices.RegisterEmployeeWS;

import android.os.AsyncTask;
import android.util.Log;

public class RegisterEmployeeAsync extends AsyncTask<Empleado, Void, Empleado>{
	private static final String TAG = "REGISTER_EMPLOYEE_ASYNC";
	RegistroActivity activity;
	LoginActivity loginActivity;
	
	public RegisterEmployeeAsync(RegistroActivity _activity) {
		super();
		this.activity = _activity;
	}

	public RegisterEmployeeAsync(LoginActivity loginActivity) {
		this.loginActivity = loginActivity;
	}

	protected Empleado doInBackground(Empleado... params) {
		RegisterEmployeeWS employeeWS = new RegisterEmployeeWS();
		Empleado result = employeeWS.setEmployee(params[0]);
		Log.i(TAG, "TEST doInBackground");
		
		return result;
	}

	protected void onPostExecute(Empleado result) {
		Log.i(TAG, "TEST onPostExecute");
        activity.goToLoginActivity(result);
		//loginActivity.goToMainActivity(result);
    }
}