package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Plantilla_ZonasYubicaciones_pendientes;
import com.gruposalinas.elektra.movilidadgs.beans.AceptarRechazarZonas;
import com.gruposalinas.elektra.movilidadgs.webservices.AceptarRechazarZonasWS;

/**
 * Created by yvegav on 06/10/2016.
 */
public class AceptarRechazarZonasAsync extends AsyncTask<AceptarRechazarZonas,Void,AceptarRechazarZonas> {

    Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes;

    public AceptarRechazarZonasAsync(Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes) {
        this.plantilla_zonasYubicaciones_pendientes = plantilla_zonasYubicaciones_pendientes;
    }

    @Override
    protected AceptarRechazarZonas doInBackground(AceptarRechazarZonas... params) {
        AceptarRechazarZonasWS aceptarRechazarZonasWS= new AceptarRechazarZonasWS();
        AceptarRechazarZonas aceptarRechazarZonas= aceptarRechazarZonasWS.aceptarRechazarZonas(params[0]);
        return  aceptarRechazarZonas;
    }

    @Override
    protected void onPostExecute(AceptarRechazarZonas aceptarRechazarZonas)
    {

        plantilla_zonasYubicaciones_pendientes.finalizr(aceptarRechazarZonas);

    }
}
