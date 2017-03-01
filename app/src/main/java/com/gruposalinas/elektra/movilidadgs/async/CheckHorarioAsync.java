package com.gruposalinas.elektra.movilidadgs.async;
import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.Activity_horarios_Autorizar;
import com.gruposalinas.elektra.movilidadgs.Activities.Horarios_consulta;
import com.gruposalinas.elektra.movilidadgs.Activities.Nombres_lista;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
import com.gruposalinas.elektra.movilidadgs.webservices.ListadoHorariosEmpleadoPlantillaWS;
import com.gruposalinas.elektra.movilidadgs.webservices.ValidarRechazarHorarioEmpleadoWS;

/**
 * Created by Adrian Guzman on 20/01/2016.
 */
public class CheckHorarioAsync extends AsyncTask<Horarios, Void, Horarios> {
    Horarios_consulta horarios_consulta;
    Nombres_lista menu_horario_zonas_pendientes;
    Activity_horarios_Autorizar activity_horarios_autorizar;


    public CheckHorarioAsync( Horarios_consulta _horarios_consulta)
    {
        this.horarios_consulta=_horarios_consulta;

    }
    public CheckHorarioAsync(Nombres_lista menu_horario_zonas_pendientes){
        this.menu_horario_zonas_pendientes=menu_horario_zonas_pendientes;
    }

    public CheckHorarioAsync(Activity_horarios_Autorizar activity_horarios_autorizar)
    {
        this.activity_horarios_autorizar=activity_horarios_autorizar;

    }

    @Override
    protected Horarios doInBackground(Horarios... params)
    {
        Horarios horarios=null;
        if(horarios_consulta!=null)
        {
           // CheckHorarioEmpleadoWS checkHorarioEmpleadoWS=new CheckHorarioEmpleadoWS();
           //horarios =checkHorarioEmpleadoWS.ObtenerHorarios(params[0]);
            ListadoHorariosEmpleadoPlantillaWS listadoHorariosEmpleadoPlantillaWS= new ListadoHorariosEmpleadoPlantillaWS();
            horarios= listadoHorariosEmpleadoPlantillaWS.ObtenerHorarios(params[0]);


        }
        if(menu_horario_zonas_pendientes!=null)
        {
            ListadoHorariosEmpleadoPlantillaWS listadoHorariosEmpleadoPlantillaWS= new ListadoHorariosEmpleadoPlantillaWS();
            horarios= listadoHorariosEmpleadoPlantillaWS.ObtenerHorarios(params[0]);

        }

        if(activity_horarios_autorizar!=null){
            ValidarRechazarHorarioEmpleadoWS validarRechazarHorarioEmpleadoWS= new ValidarRechazarHorarioEmpleadoWS();
            horarios=validarRechazarHorarioEmpleadoWS.ObtenerHorarios(params[0]);
        }


        return horarios;
    }

    @Override
    protected void onPostExecute(Horarios horarios)
    {

        if(horarios_consulta!=null){
            horarios_consulta.mostrarDatos(horarios);
        }
        if(menu_horario_zonas_pendientes!=null)
        {
         menu_horario_zonas_pendientes.mostrarNombres(horarios);
        }
        if(activity_horarios_autorizar!=null)
        {
            activity_horarios_autorizar.confirmar(horarios);
        }


    }
}
