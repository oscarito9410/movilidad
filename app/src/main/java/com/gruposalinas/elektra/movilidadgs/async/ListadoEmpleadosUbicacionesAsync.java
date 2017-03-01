package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Plantilla_ZonasYubicaciones;
import com.gruposalinas.elektra.movilidadgs.Activities.Plantilla_ZonasYubicaciones_pendientes;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.ListadoEmpleadosUbicacionesWS;

/**
 * Created by yvegav on 30/09/2016.
 */
public class ListadoEmpleadosUbicacionesAsync extends AsyncTask<Tiendas,Void,Tiendas>
{
    Plantilla_ZonasYubicaciones plantilla_zonasYubicaciones;
    Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes;

    public ListadoEmpleadosUbicacionesAsync(Plantilla_ZonasYubicaciones_pendientes plantilla_zonasYubicaciones_pendientes) {
        this.plantilla_zonasYubicaciones_pendientes = plantilla_zonasYubicaciones_pendientes;
    }

    public ListadoEmpleadosUbicacionesAsync(Plantilla_ZonasYubicaciones plantilla_zonasYubicaciones) {
        this.plantilla_zonasYubicaciones = plantilla_zonasYubicaciones;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params)
    {
        ListadoEmpleadosUbicacionesWS listadoEmpleadosUbicacionesWS= new ListadoEmpleadosUbicacionesWS();
        Tiendas tiendas=listadoEmpleadosUbicacionesWS.tiendas(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas)
    {

        if(plantilla_zonasYubicaciones!=null)
        {
            plantilla_zonasYubicaciones.Datos_empleados(tiendas);
        }
        if(plantilla_zonasYubicaciones_pendientes!=null){
            plantilla_zonasYubicaciones_pendientes.Datos_empleados(tiendas);
        }
    }
}
