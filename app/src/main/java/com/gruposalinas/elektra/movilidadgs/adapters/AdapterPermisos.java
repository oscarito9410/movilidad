package com.gruposalinas.elektra.movilidadgs.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import java.util.ArrayList;

/**
 * Created by yvegav on 21/09/2016.
 */
public class AdapterPermisos extends ArrayAdapter
{
     ArrayList descripcion_exclusion;
   ArrayList dt_fecha_hora_final;
   ArrayList dt_fecha_hora_inicial;
   ArrayList <Integer>id_estatus;
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDescripcion_exclusion(ArrayList descripcion_exclusion) {
        this.descripcion_exclusion = descripcion_exclusion;
    }

    public void setDt_fecha_hora_final(ArrayList dt_fecha_hora_final) {
        this.dt_fecha_hora_final = dt_fecha_hora_final;
    }

    public void setDt_fecha_hora_inicial(ArrayList dt_fecha_hora_inicial) {
        this.dt_fecha_hora_inicial = dt_fecha_hora_inicial;
    }

    public void setId_estatus(ArrayList id_estatus) {
        this.id_estatus = id_estatus;
    }

    public AdapterPermisos(Context context, int resource,ArrayList datos)
    {
        super(context, resource,datos);
        this.context=context;

    }



    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View vista = inflater.inflate(R.layout.item_permisos, parent, false);


        TextView fecha1=(TextView)vista.findViewById(R.id.fechaocurriencia);
        TextView incidencia1=(TextView)vista.findViewById(R.id.tipo);
        LinearLayout justicada=(LinearLayout)vista.findViewById(R.id.justificacion);
        // TextView numero=(TextView)vista.findViewById(R.id.numerodeEmpleadoplantilla);
        LinearLayout contenedor=(LinearLayout)vista.findViewById(R.id.contenedor);
        //numero.setText(numeroEmpleado.get(position));



        incidencia1.setText("Del:  "+(dt_fecha_hora_inicial.get(position)+"")+"\n Al:  "+(dt_fecha_hora_final.get(position)+"")+"");
        fecha1.setText(descripcion_exclusion.get(position)+"");


        boolean validar=esImpar(position);
        if(!validar)
        {
            contenedor.setBackgroundColor(Color.WHITE);
        }

        if(id_estatus.get(position)==1)
        {
            justicada.setBackgroundResource((R.drawable.icono_pendiente_autorizar_x2));


        }
        if(id_estatus.get(position)==2)
        {
            justicada.setBackgroundResource((R.drawable.icono_autorizado_x2));


        }





        return  vista;
    }


    public boolean esImpar(int iNumero) {
        if (iNumero%2!=0)
            return true;
        else
            return false;
    }
}
