package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Mostrar_mapas;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.CheckEliminarTiendaWS;

/**
 * Created by Adrian Guzman on 19/02/2016.
 */
public class EliminarTiendaAsync extends AsyncTask<Tiendas,Void,Tiendas> {

    Mostrar_mapas mostrar_mapas;
    public EliminarTiendaAsync(Mostrar_mapas mostrar_mapas1)
    {
        this.mostrar_mapas=mostrar_mapas1;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params) {
        CheckEliminarTiendaWS checkEliminarTienda= new CheckEliminarTiendaWS();
        Tiendas tiendas=checkEliminarTienda.tiendas(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas) {
        mostrar_mapas.ChecarEliminar(tiendas);
    }
}
