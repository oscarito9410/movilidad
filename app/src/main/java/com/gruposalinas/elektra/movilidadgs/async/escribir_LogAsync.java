package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.LoginActivity;
import com.gruposalinas.elektra.movilidadgs.Activities.RegistroActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Singleton;
import com.gruposalinas.elektra.movilidadgs.webservices.escribir_LogWS;

/**
 * Created by Adrian Guzman on 11/05/2016.
 */
public class escribir_LogAsync extends AsyncTask<Singleton,Void,Singleton>
{
    LoginActivity loginActivity;
    RegistroActivity registroActivity;

    public escribir_LogAsync(LoginActivity loginActivity, RegistroActivity registroActivity) {
        this.loginActivity = loginActivity;
        this.registroActivity = registroActivity;
    }

    @Override
    protected Singleton doInBackground(Singleton... params) {
        escribir_LogWS escribir_logWS= new escribir_LogWS();

        Singleton singleton= escribir_logWS.checar(params[0]);

        return  singleton;
    }

    @Override
    protected void onPostExecute(Singleton singleton) {

        if(loginActivity!=null){

        }

        if(registroActivity!=null){

        }
    }
}
