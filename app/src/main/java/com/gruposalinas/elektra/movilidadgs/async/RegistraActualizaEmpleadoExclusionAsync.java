package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Permisos_Plantilla_Activity;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.webservices.RegistraActualizaEmpleadoExclusionWS;

/**
 * Created by yvegav on 25/09/2016.
 */
public class RegistraActualizaEmpleadoExclusionAsync extends AsyncTask<Permisos,Void,Permisos>
{
    Permisos_Plantilla_Activity permisos_plantilla_activity;

    public RegistraActualizaEmpleadoExclusionAsync(Permisos_Plantilla_Activity permisos_plantilla_activity) {
        this.permisos_plantilla_activity = permisos_plantilla_activity;
    }

    @Override
    protected Permisos doInBackground(Permisos... params)
    {
        RegistraActualizaEmpleadoExclusionWS registraActualizaEmpleadoExclusionWS= new RegistraActualizaEmpleadoExclusionWS();
        Permisos permisos= registraActualizaEmpleadoExclusionWS.checar(params[0]);
        return permisos;
    }

    @Override
    protected void onPostExecute(Permisos permisos) {
        permisos_plantilla_activity.exito(permisos);
    }
}
