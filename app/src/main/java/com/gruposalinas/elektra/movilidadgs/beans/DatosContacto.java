package com.gruposalinas.elektra.movilidadgs.beans;

import android.content.Context;

import com.gruposalinas.elektra.movilidadgs.utils.Constants;

/**
 * Created by Adrian Guzman on 08/07/2016.
 */
public class DatosContacto
{
    public String gettel1(Context context)
    {
       return context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.tel1, "(55)");


    }

    public String gettel2(Context context)
    {
       return context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.tel2, "(55)");

    }
    public String gettel3(Context context)
    {
       return context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.tel3, "(55)");

    }

    public String getProvider(Context context){
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(Constants.provider,"network");
    }
    public boolean isguardar(Context context){

        return context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getBoolean("guardar", false);

    }
}
