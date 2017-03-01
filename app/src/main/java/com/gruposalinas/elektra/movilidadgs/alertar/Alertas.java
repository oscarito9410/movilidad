package com.gruposalinas.elektra.movilidadgs.alertar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.Activities.Permisos_Activity;
import com.gruposalinas.elektra.movilidadgs.Activities.Permisos_Plantilla_Activity;
import com.gruposalinas.elektra.movilidadgs.R;

/**
 * Created by Adrian on 09/08/2016.
 */
public class Alertas extends AlertDialog
{

    public Alertas(Context context) {
        super(context);
    }

    // errores
    public void alertas(String error,Context context)
    {
        final Dialog alert = new Dialog(context, R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText(error);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();


    }

    //alertas//confirmacion exito//

    public void Exito(final Context context, final Activity activity)
    {

        final Dialog alert = new Dialog(context,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
        final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);

        alert.setContentView(dialogo);
        alert.setCancelable(false);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity == null) {
                    alert.dismiss();
                } else {
                    Intent intent = new Intent(context, activity.getClass());
                    context.startActivity(intent);
                    alert.dismiss();

                }

            }
        });

        alert.show();
    }



}
