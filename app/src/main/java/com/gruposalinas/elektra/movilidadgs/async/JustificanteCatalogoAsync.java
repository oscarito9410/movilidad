package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.Activities.justificacionCatalogo;
import com.gruposalinas.elektra.movilidadgs.webservices.CatalogoTiposJustificantesWS;

/**
 * Created by Adrian Guzman on 09/03/2016.
 */
public class JustificanteCatalogoAsync extends AsyncTask<Incidencias,Void,Incidencias>
{
    justificacionCatalogo justificacionCatalogo;

    public JustificanteCatalogoAsync(justificacionCatalogo justificacionCatalogo)
    {
        this.justificacionCatalogo = justificacionCatalogo;
    }

    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        CatalogoTiposJustificantesWS catalogoTiposJustificantesWS= new CatalogoTiposJustificantesWS();
        Incidencias incidencias= catalogoTiposJustificantesWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias)
    {
        justificacionCatalogo.catalogomostrar(incidencias);

    }
}
