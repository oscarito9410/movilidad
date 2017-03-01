package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.VerDetalleJustificacionActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.webservices.ValidarJustificanteWS;

/**
 * Created by Adrian Guzman on 18/03/2016.
 */
public class ValidarJustificanteAsync extends AsyncTask<Incidencias,Void,Incidencias> {

    VerDetalleJustificacionActivity verDetalleJustificacionActivity;

    public ValidarJustificanteAsync(VerDetalleJustificacionActivity verDetalleJustificacionActivity) {
        this.verDetalleJustificacionActivity = verDetalleJustificacionActivity;
    }

    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        ValidarJustificanteWS validarJustificanteWS= new ValidarJustificanteWS();
        Incidencias incidencias= validarJustificanteWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias)
    {

        verDetalleJustificacionActivity.validarJustificante(incidencias);

    }
}
