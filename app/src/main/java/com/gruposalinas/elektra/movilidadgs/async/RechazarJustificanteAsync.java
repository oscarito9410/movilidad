package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.VerDetalleJustificacionActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.webservices.RechazarJustificanteWS;

/**
 * Created by Adrian Guzman on 18/03/2016.
 */
public class RechazarJustificanteAsync extends AsyncTask<Incidencias,Void,Incidencias>
{
    VerDetalleJustificacionActivity verDetalleJustificacionActivity;

    public RechazarJustificanteAsync(VerDetalleJustificacionActivity verDetalleJustificacionActivity) {
        this.verDetalleJustificacionActivity = verDetalleJustificacionActivity;
    }


    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        RechazarJustificanteWS rechazarJustificanteWS= new RechazarJustificanteWS();
        Incidencias incidencias= rechazarJustificanteWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias) {
        verDetalleJustificacionActivity.rechazarjustificante(incidencias);
    }
}
