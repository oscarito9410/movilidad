package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.VerDetalleJustificacionActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.webservices.ObtenerArchivosAdjuntosWS;

/**
 * Created by Adrian Guzman on 16/03/2016.
 */
public class ObtenerArchivosAdjuntosAsync extends AsyncTask<Incidencias,Void,Incidencias>
{

    VerDetalleJustificacionActivity verDetalleJustificacionActivity;
    public ObtenerArchivosAdjuntosAsync(VerDetalleJustificacionActivity verDetalleJustificacionActivity) {
        this.verDetalleJustificacionActivity = verDetalleJustificacionActivity;
    }

    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        ObtenerArchivosAdjuntosWS obtenerArchivosAdjuntosWS= new ObtenerArchivosAdjuntosWS();
        Incidencias incidencias= obtenerArchivosAdjuntosWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias) {
        verDetalleJustificacionActivity.verimagen(incidencias);
    }
}
