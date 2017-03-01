package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.MenuAplicaciones;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.receivers.notificacion_app;
import com.gruposalinas.elektra.movilidadgs.webservices.ObtenerConfiguracionesAplicacionWS;

/**
 * Created by Adrian Guzman on 14/03/2016.
 */
public class ObtenerConfiguracionesAplicacion extends AsyncTask<Incidencias,Void,Incidencias>
{
    notificacion_app Notificacion_app;
    MenuAplicaciones menuAplicaciones;

    public ObtenerConfiguracionesAplicacion(MenuAplicaciones menuAplicaciones) {
        this.menuAplicaciones = menuAplicaciones;
    }

    public ObtenerConfiguracionesAplicacion(notificacion_app notificacion_app)
    {
        this.Notificacion_app=notificacion_app;
    }

    @Override
    protected Incidencias doInBackground(Incidencias... params)
    {
        ObtenerConfiguracionesAplicacionWS obtenerConfiguracionesAplicacionWS= new ObtenerConfiguracionesAplicacionWS();
        Incidencias incidencias= obtenerConfiguracionesAplicacionWS.incidencias(params[0]);
        return incidencias;
    }

    @Override
    protected void onPostExecute(Incidencias incidencias)
    {
        if(menuAplicaciones!=null)
        {
            menuAplicaciones.respuesta(incidencias);
        }
        else{
            Notificacion_app.validarversion(incidencias);


        }

    }
}
