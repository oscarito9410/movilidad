package com.gruposalinas.elektra.movilidadgs.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Adrian guzman on 17/03/2016.
 */
public class Adapterplntillas extends ArrayAdapter<String> {

    ArrayList <String> fecha;
    ArrayList <Boolean> justfica;
    ArrayList <String> incidencia;
    ArrayList <String> EstatusJustificacion;
    ArrayList <String> numeroEmpleado;


    Context context;

    public void setFecha(ArrayList<String> fecha) {
        this.fecha = fecha;
    }

    public void setJustfica(ArrayList<Boolean> justfica) {
        this.justfica = justfica;
    }

    public void setIncidencia(ArrayList<String> incidencia) {
        this.incidencia = incidencia;
    }

    public void setEstatusJustificacion(ArrayList<String> estatusJustificacion) {
        EstatusJustificacion = estatusJustificacion;
    }

    public void setNumeroEmpleado(ArrayList<String> numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Adapterplntillas(Context context, int resource,ArrayList <String> string)
    {
        super(context, resource,string);
        this.context=context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View vista = inflater.inflate(R.layout.item_plantillas, parent, false);
        String fechas=fecha.get(position);
        String fechapura="";

        StringTokenizer tokenizer = new StringTokenizer(fechas);
        int contador1 = 0;
        String[] datos = new String[2];
        while (tokenizer.hasMoreTokens())
        {
            datos[contador1] = tokenizer.nextToken();
            contador1++;
        }
        fechapura=datos[0];
        int contador=0;
        String datos1[]= new String[3];
        StringTokenizer tokenizer1 = new StringTokenizer(fechapura,"-");

        while (tokenizer1.hasMoreTokens())
        {
            datos1[contador]=tokenizer1.nextToken();
            contador++;
        }

        String finalfecha=datos1[2]+"/"+datos1[1]+"/"+datos1[0];


        TextView fecha1=(TextView)vista.findViewById(R.id.fechaocurriencia);
        TextView incidencia1=(TextView)vista.findViewById(R.id.tipo);
        LinearLayout justicada=(LinearLayout)vista.findViewById(R.id.justificacion);
       // TextView numero=(TextView)vista.findViewById(R.id.numerodeEmpleadoplantilla);
        LinearLayout contenedor=(LinearLayout)vista.findViewById(R.id.contenedor);
        fecha1.setText(finalfecha);
        //numero.setText(numeroEmpleado.get(position));
        incidencia1.setText(incidencia.get(position));

        boolean validar=esImpar(position);
        if(!validar)
        {
            contenedor.setBackgroundColor(Color.WHITE);
        }

        if(EstatusJustificacion.get(position).equals("SIN JUSTIFICAR"))
        {
            justicada.setBackgroundResource((R.drawable.icono_por_justificar_x2));


        }
        if(EstatusJustificacion.get(position).equals("AUTORIZADO"))
        {
            justicada.setBackgroundResource((R.drawable.icono_autorizado_x2));


        }

        if(EstatusJustificacion.get(position).equals("PENDIENTE DE AUTORIZACION"))
        {

            justicada.setBackgroundResource(R.drawable.icono_pendiente_autorizar_x2);
        }


        return vista;

    }

    public boolean esImpar(int iNumero) {
        if (iNumero%2!=0)
            return true;
        else
            return false;
    }
}
