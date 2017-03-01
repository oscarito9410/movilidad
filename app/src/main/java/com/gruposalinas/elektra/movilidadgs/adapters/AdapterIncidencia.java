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

import java.util.StringTokenizer;

/**
 * Created by Adrian Guzman on 07/03/2016.
 */
public class AdapterIncidencia extends ArrayAdapter<String>
{
    String [] fecha;
    String [] incidencia;
    boolean[] justfica;
    String []EstatusJustificacion;
    Context context;

    public void setEstatusJustificacion(String[] estatusJustificacion) {
        EstatusJustificacion = estatusJustificacion;
    }

    public AdapterIncidencia(Context context, int resource,String string[])
    {
        super(context, resource,string);
        this.context=context;


    }


    public void setFecha(String[] fecha) {
        this.fecha = fecha;
    }



    public void setIncidencia(String[] incidencia) {
        this.incidencia = incidencia;
    }



    public void setJustfica(boolean[] justfica) {
        this.justfica = justfica;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View vista = inflater.inflate(R.layout.item_incidencia, parent, false);
        String fechas=fecha[position];
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
        LinearLayout contenedor=(LinearLayout)vista.findViewById(R.id.contenedor);
        fecha1.setText(finalfecha);
        incidencia1.setText(incidencia[position]);

        boolean validar=esImpar(position);
        if(!validar)
        {


            contenedor.setBackgroundColor(Color.WHITE);
        }
        if(EstatusJustificacion[position].equals("SIN JUSTIFICAR"))
        {
           // justicada.setBackgroundColor(Color.RED);
            justicada.setBackgroundResource((R.drawable.icono_por_justificar_x2));

        }
        if(EstatusJustificacion[position].equals("AUTORIZADO"))
        {
           // justicada.setBackgroundColor(Color.GREEN);
            justicada.setBackgroundResource((R.drawable.icono_autorizado_x2));


        }

        if(EstatusJustificacion[position].equals("PENDIENTE DE AUTORIZACION"))
        {

          //  justicada.setBackgroundColor(Color.YELLOW);
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
