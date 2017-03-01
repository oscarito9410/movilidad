package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.DetalleZonaActivity;
import com.gruposalinas.elektra.movilidadgs.Activities.DetalleZonaEliminar;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.DetalleZonalistaWS;

/**
 * Created by Adrian guzman on 23/02/2016.
 */
public class DetalleZonaAsync extends AsyncTask<Tiendas,Void,Tiendas> {

    DetalleZonaActivity detalleZonaActivity;
    DetalleZonaEliminar detalleZonaEliminar;

    public DetalleZonaAsync(DetalleZonaEliminar detalleZonaEliminar) {
        this.detalleZonaEliminar = detalleZonaEliminar;
    }

    public DetalleZonaAsync(DetalleZonaActivity detalleZonaActivity)
    {
        this.detalleZonaActivity=detalleZonaActivity;

    }

    @Override

    protected Tiendas doInBackground(Tiendas... params)
    {
        DetalleZonalistaWS detalleZonalistaWS=new DetalleZonalistaWS();
        Tiendas tiendas=detalleZonalistaWS.obtenerzonasdetalle(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas)
    {
        if(detalleZonaActivity==null)
        {
           detalleZonaEliminar.detallever(tiendas);
        }
        else
        {
            detalleZonaActivity.datosZonasobtenidos(tiendas);
        }


    }
}
