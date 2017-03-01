package com.gruposalinas.elektra.movilidadgs.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;

import java.util.StringTokenizer;

/**
 * Created by Adrian Guzman on 09/03/2016.
 */
public class AdapterListaDetalleJustifica extends ArrayAdapter<String>
{
    Context context;
    String [] fechas;
    String [] tipos;
    String [] comentarios;
    String [] descripciones;
    String [] fechavalidacion1;
    String [] tipoReal;

    public void setTipoReal(String[] tipoReal) {
        this.tipoReal = tipoReal;
    }



    public void setFechavalidacion1(String[] fechavalidacion1) {
        this.fechavalidacion1 = fechavalidacion1;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFechas(String[] fechas) {
        this.fechas = fechas;
    }

    public void setTipos(String[] tipos) {
        this.tipos = tipos;
    }

    public void setComentarios(String[] comentarios) {
        this.comentarios = comentarios;
    }

    public void setDescripciones(String[] descripciones) {
        this.descripciones = descripciones;
    }

    public void setJustificador(String[] justificador) {
        this.justificador = justificador;
    }

    String [] justificador;
    public AdapterListaDetalleJustifica(Context context, int resource,String [] strings)
    {
        super(context, resource,strings);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View vista = inflater.inflate(R.layout.item_validadojustificador, parent, false);
        String fecha=fechas[position];
        String fechapura="";

        StringTokenizer tokenizer = new StringTokenizer(fecha);
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


        // fecha validacion//

        String fechav=fechavalidacion1[position];
        String fechapurav="";

        StringTokenizer tokenizerv = new StringTokenizer(fechav);
        int contador1v = 0;
        String[] datosv = new String[2];
        while (tokenizerv.hasMoreTokens())
        {
            datosv[contador1v] = tokenizerv.nextToken();
            contador1v++;
        }
        fechapurav=datosv[0];
        int contadorv=0;
        String datos1v[]= new String[3];
        StringTokenizer tokenizer1v = new StringTokenizer(fechapurav,"-");

        while (tokenizer1v.hasMoreTokens())
        {
            datos1v[contadorv]=tokenizer1v.nextToken();
            contadorv++;
        }

        String finalfechav=datos1v[2]+"/"+datos1v[1]+"/"+datos1v[0];


        TextView feha=(TextView)vista.findViewById(R.id.fechavalida);
        TextView fechavalidacion=(TextView)vista.findViewById(R.id.fecha_validacion);
        TextView tipo=(TextView)vista.findViewById(R.id.tipovalido);
        TextView comentario=(TextView)vista.findViewById(R.id.comentariohecho);
        TextView descrip=(TextView)vista.findViewById(R.id.tipojus);
        TextView Justificador=(TextView)vista.findViewById(R.id.justificador);
        TextView autorizado=(TextView)vista.findViewById(R.id.autorizado);
        feha.setText(finalfecha);
        autorizado.setText(autorizado.getText()+finalfecha);
        fechavalidacion.setText(fechavalidacion.getText()+tipoReal[position]);
        comentario.setText(comentario.getText()+comentarios[position]);
        descrip.setText(descrip.getText()+"\n"+descripciones[position]);
        Justificador.setText(Justificador.getText() + justificador[position]);
        return vista;

    }
}
