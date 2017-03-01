package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Agregar_permisos_plantilla;
import com.gruposalinas.elektra.movilidadgs.Activities.Permisos_Activity;
import com.gruposalinas.elektra.movilidadgs.Activities.Permisos_Plantilla_Activity;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.webservices.Permisos_empleadoWS;

/**
 * Created by yvegav on 20/09/2016.
 */
public class ConsultarExclusionesEmpleadosAsync extends AsyncTask<Permisos,Void,Permisos>
{
    Permisos_Activity permisos_activity;
    Permisos_Plantilla_Activity permisos_plantilla_activity;
    Agregar_permisos_plantilla agregar_permisos_plantilla;
    public ConsultarExclusionesEmpleadosAsync(Permisos_Activity permisos_activity)
    {
        this.permisos_activity=permisos_activity;
    }

    public ConsultarExclusionesEmpleadosAsync(Permisos_Plantilla_Activity permisos_plantilla_activity)
    {
        this.permisos_plantilla_activity=permisos_plantilla_activity;
    }

    public ConsultarExclusionesEmpleadosAsync(Agregar_permisos_plantilla agregar_permisos_plantilla) {
        this.agregar_permisos_plantilla = agregar_permisos_plantilla;
    }

    @Override
    protected Permisos doInBackground(Permisos... params)
    {
        Permisos_empleadoWS permisos_empleadoWS= new Permisos_empleadoWS();
        Permisos permisos=permisos_empleadoWS.permisos(params[0]);
        return permisos;
    }

    @Override
    protected void onPostExecute(Permisos permisos)
    {
        if(permisos_activity!=null)
        {
            permisos_activity.respuesta(permisos);
        }
        else if (permisos_plantilla_activity!=null){
            permisos_plantilla_activity.mostar(permisos);
        }

        if(agregar_permisos_plantilla!=null){

            agregar_permisos_plantilla.mostar(permisos);
        }

    }
}
