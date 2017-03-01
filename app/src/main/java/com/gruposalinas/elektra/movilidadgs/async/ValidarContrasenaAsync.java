package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;
import android.widget.RelativeLayout;

import com.gruposalinas.elektra.movilidadgs.Activities.LoginActivity;
import com.gruposalinas.elektra.movilidadgs.Activities.RegistroActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.webservices.ChecarContrasenaWS;

/**
 * Created by Adrian Guzman on 19/01/2016.
 */
public class ValidarContrasenaAsync extends AsyncTask<Empleado,Void,Empleado> {
String datos;
    LoginActivity activity;
    RegistroActivity activity1;
    public ValidarContrasenaAsync(String a ,LoginActivity _activity) {
        datos=a;
        this.activity=_activity;
    }
    public ValidarContrasenaAsync(String a ,RegistroActivity _activity) {
        datos=a;
        this.activity1=_activity;
    }


    @Override
    protected Empleado doInBackground(Empleado... params) {
        ChecarContrasenaWS checarContrasenaWS =new ChecarContrasenaWS(datos);
        Empleado empleado =checarContrasenaWS.checar(params[0]);
        return  empleado;
    }

    @Override
    protected void onPostExecute(Empleado empleado)
    {

        if(activity!=null)
        {
            activity.Login(empleado);
        }
        else{
            activity1.Login(empleado);
        }



    }
}
