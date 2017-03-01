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

import java.util.ArrayList;

/**
 * Created by ADrian  on 17/05/2016.
 */
public class adapterHorarios_autorizar extends ArrayAdapter<String>
{

    ArrayList <String> diaSemana;
    ArrayList <String> EntradaHora;
    ArrayList <String> SalidaHora;
    ArrayList <String> validar;




    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDiaSemana(ArrayList<String> diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setEntradaHora(ArrayList<String> entradaHora) {
        EntradaHora = entradaHora;
    }

    public void setSalidaHora(ArrayList<String> salidaHora) {
        SalidaHora = salidaHora;
    }

    public void setValidar(ArrayList<String> validar) {
        this.validar = validar;
    }

    public adapterHorarios_autorizar(Context context, int resource,ArrayList strings) {
        super(context, resource, strings);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View vista = inflater.inflate(R.layout.vista_horario_autorizar, parent, false);

        TextView dia=(TextView)vista.findViewById(R.id.dia_item);
        TextView entrada=(TextView)vista.findViewById(R.id.entrada_item);
        TextView salida=(TextView)vista.findViewById(R.id.salida_item);
        LinearLayout valid=(LinearLayout)vista.findViewById(R.id.linearLayout5);
        LinearLayout contenedor=(LinearLayout)vista.findViewById(R.id.contenedor);
        dia.setText(diaSemana.get(position));
        entrada.setText(EntradaHora.get(position));
        salida.setText(SalidaHora.get(position));
        valid.setVisibility(View.GONE);

        if(validar.get(position).equals("PROPUESTA"))
        {

            valid.setBackground(context.getResources().getDrawable(R.drawable.icono_pendiente_autorizar_x2));
            dia.setTextColor(Color.BLACK);
            entrada.setTextColor(Color.BLACK);
            salida.setTextColor(Color.BLACK);

        }
        else if(validar.get(position).equals("PEND. POR LIBERAR"))
        {
            valid.setBackground(context.getResources().getDrawable(R.drawable.icono_por_justificar_x2));
            dia.setTextColor(Color.BLACK);
            entrada.setTextColor(Color.RED);
            salida.setTextColor(Color.BLACK);
            entrada.setText("Día libre");
            salida.setText("");

        }



        boolean validar=esImpar(position);
        if(!validar)
        {


            contenedor.setBackgroundColor(Color.WHITE);

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
