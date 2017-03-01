package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Activity_horarios_Autorizar;
import com.gruposalinas.elektra.movilidadgs.Activities.Horarios_consulta;
import com.gruposalinas.elektra.movilidadgs.Activities.Nombres_lista;
import com.gruposalinas.elektra.movilidadgs.beans.Horapendiente;
import com.gruposalinas.elektra.movilidadgs.webservices.EditarEmpleadoHorarioServicioWS;
import com.gruposalinas.elektra.movilidadgs.webservices.HorarioPendienteWS;

/**
 * Created by Adrian Guzman on 28/01/2016.
 */
public class HorarioPendienteAsync extends AsyncTask<Horapendiente,Void,Horapendiente> {
    Horarios_consulta activity;
    String datos_Horarios;
    Activity_horarios_Autorizar activity_horarios_autorizar;
    Nombres_lista nombres_lista;
    public HorarioPendienteAsync(Horarios_consulta _activity,String Datos_horarios)
    {
        this.activity=_activity;
        this.datos_Horarios=Datos_horarios;
    }

    public HorarioPendienteAsync(Activity_horarios_Autorizar activity_horarios_autorizar,String datos_Horarios)
    {
        this.activity_horarios_autorizar=activity_horarios_autorizar;
        this.datos_Horarios=datos_Horarios;

    }

    public HorarioPendienteAsync(Nombres_lista nombres_lista,String datos_Horarios) {
        this.nombres_lista = nombres_lista;
        this.datos_Horarios=datos_Horarios;
    }

    @Override
    protected Horapendiente doInBackground(Horapendiente... params)
    {
        HorarioPendienteWS horarioPendienteWS=null;
        Horapendiente horapendiente=null;
        if(datos_Horarios.equals("1"))
        {
            horarioPendienteWS=new HorarioPendienteWS(datos_Horarios);
            horapendiente=horarioPendienteWS.pendiente(params[0]);


        }
        if(datos_Horarios.equals("2")){
            EditarEmpleadoHorarioServicioWS editarEmpleadoHorarioServicioWS= new EditarEmpleadoHorarioServicioWS();
            horapendiente= editarEmpleadoHorarioServicioWS.setEliminar(params[0]);
        }

        return horapendiente;
    }

    @Override
    protected void onPostExecute(Horapendiente horapendiente)
    {

        if(activity!=null){
            activity.checarEnvio(horapendiente);

        }
        else if(activity_horarios_autorizar!=null){
            activity_horarios_autorizar.confirmarlibre(horapendiente);
        }
        else if(nombres_lista!=null)
        {
            nombres_lista.confirmarlibre(horapendiente);

        }

        //metodo que mandara mensaje exitoso o fallido dependiendo el caso
    }
}
