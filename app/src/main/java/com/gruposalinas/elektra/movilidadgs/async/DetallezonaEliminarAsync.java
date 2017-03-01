package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.DetalleZonaEliminar;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.EliminarZonaWS;

/**
 * Created by Adrian Guzman on 01/03/2016.
 */
public class DetallezonaEliminarAsync extends AsyncTask<Tiendas,Void,Tiendas> {
    DetalleZonaEliminar detalleZonaEliminar;

    public DetallezonaEliminarAsync(DetalleZonaEliminar detalleZonaEliminar) {
        this.detalleZonaEliminar = detalleZonaEliminar;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params)
    {
        EliminarZonaWS eliminarZonaWS=new EliminarZonaWS();
        Tiendas tiendas=eliminarZonaWS.eliminarZona(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas) {
        detalleZonaEliminar.confirmacion(tiendas);

    }
}
