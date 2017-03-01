package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.ListaIncidenciasActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.receivers.incidenciasreciver;
import com.gruposalinas.elektra.movilidadgs.webservices.IncidenciascheckWS;

/**
 * Created by Adrian Guzman on 04/03/2016.
 */
public class IncidenciasAsync extends AsyncTask<Incidencias,Void,Incidencias>
{
    ListaIncidenciasActivity listaIncidenciasActivity;
    incidenciasreciver Incidencias_;

    public IncidenciasAsync(ListaIncidenciasActivity listaIncidenciasActivity)
    {
        this.listaIncidenciasActivity = listaIncidenciasActivity;
    }



    public IncidenciasAsync(incidenciasreciver Incidencias) {
        this.Incidencias_ = Incidencias;
    }

    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        IncidenciascheckWS incidenciascheckWS=new IncidenciascheckWS();
        Incidencias incidencias=incidenciascheckWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias)
    {
        if(listaIncidenciasActivity!=null)
        {

            listaIncidenciasActivity.mostrarIncidencias(incidencias);

        }
        else
        {
            if(incidencias!=null)
            {
                if( Incidencias_!=null &&incidencias.getLista()!=null)
                {

                    Incidencias_.validar(incidencias);

                }

            }


        }





    }



}
