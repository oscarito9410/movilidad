package com.gruposalinas.elektra.movilidadgs.async;


import android.os.AsyncTask;

import com.gruposalinas.elektra.movilidadgs.Activities.capture_video;
import com.gruposalinas.elektra.movilidadgs.beans.Multimedia;
import com.gruposalinas.elektra.movilidadgs.geolocation.ServicePanico;
import com.gruposalinas.elektra.movilidadgs.webservices.AgregarEmpleado_ArchivoClipMultimediaWS;

/**
 * Created by Adrian Guzman on 23/06/2016.
 */
public class MultimediaAsync extends AsyncTask <Multimedia,Void,Multimedia>
{

    ServicePanico servicePanico;
    capture_video cap;

    public MultimediaAsync(ServicePanico servicePanico) {
        this.servicePanico = servicePanico;
    }

    public MultimediaAsync(capture_video cap) {
        this.cap = cap;
    }

    @Override
    protected Multimedia doInBackground(Multimedia... params)
    {
        AgregarEmpleado_ArchivoClipMultimediaWS agregarEmpleado_archivoClipMultimediaWS= new AgregarEmpleado_ArchivoClipMultimediaWS();
        Multimedia multimedia= agregarEmpleado_archivoClipMultimediaWS.multimedia(params[0]);
        return multimedia;
    }

    @Override
    protected void onPostExecute(Multimedia multimedia) {
        super.onPostExecute(multimedia);
    }
}
