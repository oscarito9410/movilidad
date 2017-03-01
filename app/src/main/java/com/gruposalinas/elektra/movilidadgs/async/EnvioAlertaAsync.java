package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.geolocation.ServicePanico;
import com.gruposalinas.elektra.movilidadgs.webservices.EnvioAlertaWS;

/**
 * Created by Adrian Guzman on 28/03/2016.
 */
public class EnvioAlertaAsync extends AsyncTask <RegistroGPS, Void,RegistroGPS> {

    ServicePanico servicePanico;

    public EnvioAlertaAsync(ServicePanico servicePanico)
    {
        this.servicePanico = servicePanico;
    }

    @Override
    protected RegistroGPS doInBackground(RegistroGPS... params)
    {
        EnvioAlertaWS envioAlertaWS = new EnvioAlertaWS();
        RegistroGPS registroGPS= envioAlertaWS.gps(params[0]);

        return registroGPS;
    }
}
