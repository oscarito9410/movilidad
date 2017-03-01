package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.beans.ObtenerEstatusAlerta;
import com.gruposalinas.elektra.movilidadgs.geolocation.ServicePanico;
import com.gruposalinas.elektra.movilidadgs.webservices.ObtenerEstatusAlertaWS;

/**
 * Created by jgarciae on 31/03/2016.
 */
public class ObtenerEstatusAlertaAsync extends AsyncTask<ObtenerEstatusAlerta,Void,ObtenerEstatusAlerta>
{
     ServicePanico servicePanico;

    public ObtenerEstatusAlertaAsync(ServicePanico servicePanico)
    {
        this.servicePanico = servicePanico;
    }

    @Override
    protected ObtenerEstatusAlerta doInBackground(ObtenerEstatusAlerta... params)
    {
        ObtenerEstatusAlertaWS obtenerEstatusAlertaWS= new ObtenerEstatusAlertaWS();
        ObtenerEstatusAlerta obtenerEstatusAlerta=obtenerEstatusAlertaWS.obtenerEstatusAlerta(params[0]);
        return obtenerEstatusAlerta;
    }

    @Override
    protected void onPostExecute(ObtenerEstatusAlerta obtenerEstatusAlerta) {

        servicePanico.obtenerestatus(obtenerEstatusAlerta);

    }
}
