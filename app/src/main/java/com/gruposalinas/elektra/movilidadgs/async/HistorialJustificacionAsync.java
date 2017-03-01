package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.VerDetalleJustificacionActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.webservices.HistorialJustificacionWS;

/**
 * Created by Adrian Guzman Martinez on 09/03/2016.
 */
public class HistorialJustificacionAsync extends AsyncTask<Incidencias,Void,Incidencias>
{
    VerDetalleJustificacionActivity verDetalleJustificacionActivity;

    public HistorialJustificacionAsync(VerDetalleJustificacionActivity verDetalleJustificacionActivity) {
        this.verDetalleJustificacionActivity = verDetalleJustificacionActivity;
    }

    @Override

    protected Incidencias doInBackground(Incidencias... params)
    {
        HistorialJustificacionWS historialJustificacionWS= new HistorialJustificacionWS();
        Incidencias incidencias= historialJustificacionWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias) {
        verDetalleJustificacionActivity.mostrarIncidencias(incidencias);
    }
}
