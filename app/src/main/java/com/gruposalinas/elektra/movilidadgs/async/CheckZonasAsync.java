package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.AsignarZona_Plantilla;
import com.gruposalinas.elektra.movilidadgs.Activities.ListaZonasActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.ObtenerZonaWS;

/**
 * Created by AdrianGuzman on 22/02/2016.
 */
public class CheckZonasAsync extends AsyncTask<Tiendas,Void,Tiendas>
{
    ListaZonasActivity listaZonasActivity;
    AsignarZona_Plantilla asignarZona_plantilla;


    public CheckZonasAsync(ListaZonasActivity listaZonasActivity) {
        this.listaZonasActivity = listaZonasActivity;
    }

    public CheckZonasAsync(AsignarZona_Plantilla asignarZona_plantilla) {
        this.asignarZona_plantilla = asignarZona_plantilla;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params)
    {
        ObtenerZonaWS obtenerZonaWS=new ObtenerZonaWS();
        Tiendas tiendas=obtenerZonaWS.obtenerzonas(params[0]);
        return tiendas;
    }


    @Override
    protected void onPostExecute(Tiendas tiendas) {
        // metodo con el resultado de las zonas//

        if(listaZonasActivity!=null)
        {

            listaZonasActivity.resultadoTarea(tiendas);
        }

        if(asignarZona_plantilla!=null){
            asignarZona_plantilla.resultadoTarea(tiendas);
        }

    }
}
