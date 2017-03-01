package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.ListadoTiendas;
import com.gruposalinas.elektra.movilidadgs.Activities.listado_ubicaciones_plantilla;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.ListadoTiendasCheckWS;

/**
 * Created by Adrian Guzman on 10/02/2016.
 */
public class listadoTiendasCheckAsync extends AsyncTask<Tiendas,Void,Tiendas>
{
    ListadoTiendas actividad;
    listado_ubicaciones_plantilla plantilla;

    public listadoTiendasCheckAsync(ListadoTiendas listadoTiendas)
    {
        this.actividad=listadoTiendas;

    }

    public listadoTiendasCheckAsync(listado_ubicaciones_plantilla plantilla) {
        this.plantilla = plantilla;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params) {

        ListadoTiendasCheckWS listadoTiendasCheckWS=new ListadoTiendasCheckWS();
        Tiendas tiendas=listadoTiendasCheckWS.tiendas(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas)
    {
        if(actividad!=null)
        {
            actividad.mostrarDatosListado(tiendas);

        }
        if(plantilla!=null){
            plantilla.mostrarDatosListado(tiendas);
        }

    }
}
