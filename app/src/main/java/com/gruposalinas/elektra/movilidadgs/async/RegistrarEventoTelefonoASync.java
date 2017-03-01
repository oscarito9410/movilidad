package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.beans.ObtenerEstatusAlerta;
import com.gruposalinas.elektra.movilidadgs.geolocation.serviceEvento;
import com.gruposalinas.elektra.movilidadgs.webservices.RegistrarEventoTelefonoWS;

/**
 * Created by Adrian Guzman on 07/04/2016.
 */
public class RegistrarEventoTelefonoASync extends AsyncTask<ObtenerEstatusAlerta,Void,ObtenerEstatusAlerta> {

    serviceEvento serviceEvento;

    public RegistrarEventoTelefonoASync(com.gruposalinas.elektra.movilidadgs.geolocation.serviceEvento serviceEvento) {
        this.serviceEvento = serviceEvento;
    }

    @Override
    protected ObtenerEstatusAlerta doInBackground(ObtenerEstatusAlerta... params)
    {
        RegistrarEventoTelefonoWS registrarEventoTelefonoWS= new RegistrarEventoTelefonoWS();
        ObtenerEstatusAlerta obtenerEstatusAlerta=registrarEventoTelefonoWS.obtenerEstatusAlerta(params[0]);

        return obtenerEstatusAlerta;
    }

    @Override
    protected void onPostExecute(ObtenerEstatusAlerta obtenerEstatusAlerta) {
        serviceEvento.guardar(obtenerEstatusAlerta);
    }
}
