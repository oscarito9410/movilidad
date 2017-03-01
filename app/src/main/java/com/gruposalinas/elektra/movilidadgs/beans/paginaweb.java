package com.gruposalinas.elektra.movilidadgs.beans;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.adrian.vistaweb.vistawebview;
import com.gruposalinas.elektra.movilidadgs.R;

/**
 * Created by Adrian Guzman on 12/04/2016.
 */
public class paginaweb
{


    Context context1;
    public paginaweb(Context context)
    {
        this.context1=context;
        Intent intent = new Intent(context1, vistawebview.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("pie_pagina",true);
        context1.startActivity(intent);
    }

    //


}
