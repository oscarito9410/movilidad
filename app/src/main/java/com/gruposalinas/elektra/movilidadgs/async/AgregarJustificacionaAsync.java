package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.Activities.justificacionCatalogo;
import com.gruposalinas.elektra.movilidadgs.webservices.AgregarJustificacionWS;

/**
 * Created by Adrian guzman  on 10/03/2016.
 */
public class AgregarJustificacionaAsync extends AsyncTask<Incidencias,Void,Incidencias>
{
    justificacionCatalogo justificacionCatalogo;

    public AgregarJustificacionaAsync(com.gruposalinas.elektra.movilidadgs.Activities.justificacionCatalogo justificacionCatalogo)
    {
        this.justificacionCatalogo = justificacionCatalogo;
    }

    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        AgregarJustificacionWS agregarJustificacionWS= new AgregarJustificacionWS();
        Incidencias incidencias= agregarJustificacionWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias) {
        justificacionCatalogo.acaboEnviar(incidencias);
    }
}
