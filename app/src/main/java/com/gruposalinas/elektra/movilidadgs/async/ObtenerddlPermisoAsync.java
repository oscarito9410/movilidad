package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Agregar_Permisos_activity;
import com.gruposalinas.elektra.movilidadgs.Activities.Agregar_permisos_plantilla;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
import com.gruposalinas.elektra.movilidadgs.webservices.ObtenerddlPermisoWS;

/**
 * Created by yvegav on 23/09/2016.
 */
public class ObtenerddlPermisoAsync extends AsyncTask<Horarios,Void,Horarios>
{
    Agregar_Permisos_activity agregar_permisos_activity;
    Agregar_permisos_plantilla agregar_permisos_plantilla;

    public ObtenerddlPermisoAsync(Agregar_Permisos_activity agregar_permisos_activity) {
        this.agregar_permisos_activity = agregar_permisos_activity;
    }

    public ObtenerddlPermisoAsync(Agregar_permisos_plantilla agregar_permisos_plantilla) {
        this.agregar_permisos_plantilla = agregar_permisos_plantilla;
    }

    @Override
    protected Horarios doInBackground(Horarios... params) {
        ObtenerddlPermisoWS obtenerddlPermisoWS= new ObtenerddlPermisoWS();
        Horarios permisolista=obtenerddlPermisoWS.checar(params[0]);
        return permisolista;
    }

    @Override
    protected void onPostExecute(Horarios horarios) {
        if (agregar_permisos_activity!=null){
            agregar_permisos_activity.getlistaCatalogo(horarios);
        }
        if(agregar_permisos_plantilla!=null){
            agregar_permisos_plantilla.getlistaCatalogo(horarios);
        }

    }
}
