package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Agregar_Permisos_activity;
import com.gruposalinas.elektra.movilidadgs.Activities.Agregar_permisos_plantilla;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.webservices.SolicitarExclusionWS;

/**
 * Created by yvegav on 22/09/2016.
 */
public class SolicitarExclusionAsync extends AsyncTask<Permisos,Void,Permisos>
{
    Agregar_Permisos_activity agregar_permisos_activity;
    Agregar_permisos_plantilla agregar_permisos_plantilla;

    public SolicitarExclusionAsync(Agregar_Permisos_activity agregar_permisos_activity) {
        this.agregar_permisos_activity = agregar_permisos_activity;
    }

    public SolicitarExclusionAsync(Agregar_permisos_plantilla agregar_permisos_plantilla) {
        this.agregar_permisos_plantilla = agregar_permisos_plantilla;
    }

    @Override
    protected Permisos doInBackground(Permisos... params) {
        SolicitarExclusionWS solicitarExclusionWS= new SolicitarExclusionWS();
        Permisos permisos= solicitarExclusionWS.permisos(params[0]);
        return  permisos;
    }

    @Override
    protected void onPostExecute(Permisos permisos)
    {
        if(agregar_permisos_activity!=null){
            agregar_permisos_activity.exito(permisos);
        }
        if(agregar_permisos_plantilla!=null){
            agregar_permisos_plantilla.exito(permisos);
        }


    }
}
