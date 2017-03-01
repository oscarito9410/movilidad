package com.gruposalinas.elektra.movilidadgs.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.Activities.Detalle_Horario_pendiente;
import com.gruposalinas.elektra.movilidadgs.Activities.Horarios_consulta;
import com.gruposalinas.elektra.movilidadgs.Activities.Nombres_lista;
import com.gruposalinas.elektra.movilidadgs.R;

import java.util.ArrayList;

/**
 * Created by jgarciae on 12/04/2016.
 */
public class Adapterhorarios extends ArrayAdapter<String>
{
    String diaSemana[];
    String EntradaHora[];
    String SalidaHora[];
    Horarios_consulta horarios_consulta;
    Detalle_Horario_pendiente detalle_horario_pendiente;
    Nombres_lista nombres_lista;
  String[] color;



    public void setColor(String[] color) {
        this.color = color;
    }

    public Adapterhorarios(Horarios_consulta context, int resource,String string[])
    {
        super(context, resource,string);
        this.context=context;
        horarios_consulta=context;
    }
    public Adapterhorarios(Detalle_Horario_pendiente context, int resource,String string[])
    {
        super(context, resource,string);
        this.context=context;
        this.detalle_horario_pendiente=context;
    }

    public Adapterhorarios(Nombres_lista context, int resource,String [] string)
    {
        super(context, resource,string);
        this.context=context;
        this.nombres_lista=context;
    }




    public void setDiaSemana(String[] diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setEntradaHora(String[] entradaHora) {
        EntradaHora = entradaHora;
    }

    public void setSalidaHora(String[] salidaHora) {
        SalidaHora = salidaHora;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    Context context;


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View vista = inflater.inflate(R.layout.item_horarios_am_pm, parent, false);
        TextView dia=(TextView)vista.findViewById(R.id.dia_item);
        TextView entrada=(TextView)vista.findViewById(R.id.entrada_item);
        TextView salida=(TextView)vista.findViewById(R.id.salida_item);
        LinearLayout linearLayout=(LinearLayout)vista.findViewById(R.id.contenedor);
        LinearLayout color=(LinearLayout)vista.findViewById(R.id.ponerColor);
        dia.setText(diaSemana[position]);
        entrada.setText(EntradaHora[position]);
        salida.setText(SalidaHora[position]);
        boolean validar=esImpar(position);

        if(this.color[position].equals("PROPUESTA"))
        {
          //  color.setBackgroundColor(context.getResources().getColor(R.color.amarillo));
        }
        else if(this.color[position].equals("PEND. POR LIBERAR")){
       //     color.setBackgroundColor(context.getResources().getColor(R.color.rojo));
            entrada.setText("Día libre");
            entrada.setTextColor(context.getResources().getColor(R.color.rojo));
            salida.setText("");

        }
        else if(this.color[position].equals("VALIDO")){
            color.setVisibility(View.INVISIBLE);
        }
        else {
            color.setVisibility(View.INVISIBLE);
        }

        if(horarios_consulta!=null)
        {
            if(EntradaHora[position].equals(""))
            {
                entrada.setText("Día libre");
                entrada.setTextColor(context.getResources().getColor(R.color.rojo));
            }


        }

        if(nombres_lista!=null)
        {
            if(EntradaHora[position].equals(""))
            {
                entrada.setText("Día libre");
                entrada.setTextColor(context.getResources().getColor(R.color.rojo));
            }


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
