package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.AsignarZona_Plantilla;
import com.gruposalinas.elektra.movilidadgs.Activities.DetalleZonaActivity;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.AsignarZonaUsuarioWS;

/**
 * Created by Adrian Guzman on 24/02/2016.
 */
public class AsignarZonaAsync extends AsyncTask<Tiendas,Void,Tiendas>
{
    DetalleZonaActivity detalleZonaActivity;
    AsignarZona_Plantilla asignarZona_plantilla;
    public AsignarZonaAsync(DetalleZonaActivity detalleZonaActivity)
    {
        this.detalleZonaActivity=detalleZonaActivity;
    }

    public AsignarZonaAsync(AsignarZona_Plantilla asignarZona_plantilla) {
        this.asignarZona_plantilla = asignarZona_plantilla;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params)
    {
        AsignarZonaUsuarioWS asignarZonaUsuarioWS=new AsignarZonaUsuarioWS();
        Tiendas tiendas=asignarZonaUsuarioWS.AsignarZona(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas)
    {
        if(detalleZonaActivity!=null)
        {

            detalleZonaActivity.mensaje(tiendas);
        }

        if(asignarZona_plantilla!=null){
            asignarZona_plantilla.finaliza(tiendas);
        }

    }
}
