package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Plantilla_ZonasYubicaciones_pendientes;
import com.gruposalinas.elektra.movilidadgs.beans.AceptarRechazarPropuestaUbicacion;
import com.gruposalinas.elektra.movilidadgs.webservices.AceptarRechazarPropuestaUbicacionWS;

/**
 * Created by yvegav on 06/10/2016.
 */
public class AceptarRechazarPropuestaUbicacionAsync extends AsyncTask<AceptarRechazarPropuestaUbicacion,Void,AceptarRechazarPropuestaUbicacion> {
  Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes;

    public AceptarRechazarPropuestaUbicacionAsync(Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes) {
        this.plantilla_zonasYubicaciones_pendientes = plantilla_zonasYubicaciones_pendientes;
    }

    @Override
    protected AceptarRechazarPropuestaUbicacion doInBackground(AceptarRechazarPropuestaUbicacion... params) {
        AceptarRechazarPropuestaUbicacionWS aceptarRechazarPropuestaUbicacionWS= new AceptarRechazarPropuestaUbicacionWS();
        AceptarRechazarPropuestaUbicacion aceptarRechazarPropuestaUbicacion=aceptarRechazarPropuestaUbicacionWS.aceptarRechazarPropuestaUbicacion(params[0]);

        return aceptarRechazarPropuestaUbicacion;
    }

    @Override
    protected void onPostExecute(AceptarRechazarPropuestaUbicacion aceptarRechazarPropuestaUbicacion) {
        plantilla_zonasYubicaciones_pendientes.enviasr(aceptarRechazarPropuestaUbicacion);
    }
}
