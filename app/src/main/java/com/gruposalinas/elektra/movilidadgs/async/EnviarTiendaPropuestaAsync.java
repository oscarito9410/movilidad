package com.gruposalinas.elektra.movilidadgs.async;

import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.MostrarMapaEnviar;
import com.gruposalinas.elektra.movilidadgs.Activities.listado_ubicaciones_plantilla;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.webservices.EnviarTiendaPropuestaWS;

/**
 * Created by Adrian Guzman on 10/02/2016.
 */
public class EnviarTiendaPropuestaAsync extends AsyncTask<Tiendas,Void,Tiendas>
{
    MostrarMapaEnviar mostrarMapaEnviar;
    listado_ubicaciones_plantilla plantilla;
    public EnviarTiendaPropuestaAsync(MostrarMapaEnviar mostrarMapaEnviar1)
    {
        this.mostrarMapaEnviar=mostrarMapaEnviar1;

    }

    public EnviarTiendaPropuestaAsync(listado_ubicaciones_plantilla plantilla) {
        this.plantilla = plantilla;
    }

    @Override
    protected Tiendas doInBackground(Tiendas... params)
    {
        EnviarTiendaPropuestaWS enviarTiendaPropuestaWS=new EnviarTiendaPropuestaWS();
        Tiendas tiendas=enviarTiendaPropuestaWS.tiendas(params[0]);
        return tiendas;
    }

    @Override
    protected void onPostExecute(Tiendas tiendas)
    {
        if(mostrarMapaEnviar!=null){

            mostrarMapaEnviar.ChecarEnviado(tiendas);
        }
        if(plantilla!=null){
            plantilla.finalizr(tiendas);
        }

    }
}
