package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Activity_Tiendas;
import com.gruposalinas.elektra.movilidadgs.Activities.Plantilla_ZonasYubicaciones;
import com.gruposalinas.elektra.movilidadgs.Activities.Plantilla_ZonasYubicaciones_pendientes;
import com.gruposalinas.elektra.movilidadgs.Activities.TiendaDetalle;
import com.gruposalinas.elektra.movilidadgs.Activities.tienda_detalle_plantilla;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.CheckTiendasWS;

/**
 * Created by Adrian Guzman on 05/02/2016.
 */
public class CheckTiendasAsync extends AsyncTask<Tiendas,Void,Tiendas>
{
    Activity_Tiendas tiendas_t;
    TiendaDetalle tiendaDetalle;
    Plantilla_ZonasYubicaciones plantilla_zonasYubicaciones;
    tienda_detalle_plantilla TiendadetalleP;
    Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes;

    public CheckTiendasAsync(Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes) {
        this.plantilla_zonasYubicaciones_pendientes = plantilla_zonasYubicaciones_pendientes;
    }

    public  CheckTiendasAsync(Activity_Tiendas Tiendas)
    {
        this.tiendas_t=Tiendas;

    }
    public  CheckTiendasAsync (TiendaDetalle tiendaDetalle){
        this.tiendaDetalle=tiendaDetalle;
    }

    public CheckTiendasAsync(Plantilla_ZonasYubicaciones plantilla_zonasYubicaciones) {
        this.plantilla_zonasYubicaciones = plantilla_zonasYubicaciones;
    }

    public CheckTiendasAsync(tienda_detalle_plantilla tiendadetalleP) {
        TiendadetalleP = tiendadetalleP;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params)
    {
        CheckTiendasWS checkTiendasWS=new CheckTiendasWS();
        Tiendas tiendas1=checkTiendasWS.tiendas(params[0]);
        return tiendas1 ;
    }


    @Override
    protected void onPostExecute(Tiendas tiendas)

    {
        if(tiendas_t!=null){
            tiendas_t.ObtenerTiendas(tiendas);
        }
        if(tiendaDetalle!=null){
            tiendaDetalle.pintar(tiendas);
        }
        if(plantilla_zonasYubicaciones!=null){
            plantilla_zonasYubicaciones.ObtenerTiendas(tiendas);
        }
        if(plantilla_zonasYubicaciones_pendientes!=null){
            plantilla_zonasYubicaciones_pendientes.ObtenerTiendas(tiendas);
        }

    }
}