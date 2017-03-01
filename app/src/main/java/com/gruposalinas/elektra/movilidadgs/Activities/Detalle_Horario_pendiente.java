package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.Adapterhorarios;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Detalle_Horario_pendiente extends Activity {
    GridView listView;
    RelativeLayout progressBar;
    JSONArray datos;
    String horario;
    ImageView paginaweb1,delete;
    LinearLayout regresar;
    String[] dia=null;
    String[]Hentrada=null;
    String[]Hsalida=null;
    Adapterhorarios adapterhorarios;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    LinearLayout contacto;
   String[] color;
    Alertas alertas;
    ImageButton back;





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__horario_pendiente);
        init();
        alertas = new Alertas(this);


        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalle_Horario_pendiente.this, Contacto.class);
                startActivity(intent);
            }
        });

        delete=(ImageView)findViewById(R.id.dlete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.closeDrawer(GravityCompat.START);
            }
        });
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        pendientes_zonas_horarios_menu.setFitsSystemWindows(true);
        pendientes_zonas_horarios_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        // se obtiene el array json para ver pendientes
        Bundle bundle=getIntent().getExtras();
      horario=  bundle.getString("datos");
        //nombre=bundle.getString("nombreEmpleado");
        try {
            datos=new JSONArray(horario);
        } catch (JSONException e) {
            e.printStackTrace();
            datos=null;
        }
        mostrarDatos(datos);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Detalle_Horario_pendiente.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        paginaweb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginaweb paginaweb2 = new paginaweb(getApplicationContext());
            }
        });

        panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validar();

            }
        });
        menu_ubicacion.setFitsSystemWindows(true);
        menu_horario.setFitsSystemWindows(true);
        menu_panico.setFitsSystemWindows(true);
        menu_incidencias.setFitsSystemWindows(true);

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Detalle_Horario_pendiente.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Detalle_Horario_pendiente.this,Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Detalle_Horario_pendiente.this,ListaIncidenciasActivity.class);
                intent.putExtra("plantilla",false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validar();


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Detalle_Horario_pendiente.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });

    }

    public void init()
    {
        texto_incidencias=(TextView)findViewById(R.id.texto_menuIncidencias);
        texto_panico=(TextView)findViewById(R.id.texto_menupanico);
        panicoimas=(ImageView)findViewById(R.id.icono_panico_menu);
        inciendenciasimas=(ImageView)findViewById(R.id.icono_incidenciasmenu);
        reloj=(ImageView)findViewById(R.id.reloj);
        tocar=(ImageView)findViewById(R.id.tocar);
        texto_menuUbicacion=(TextView)findViewById(R.id.texto_menuUbicacion);
        texto_menuHorario=(TextView)findViewById(R.id.texto_menuHorario);
        menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
        menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
        paginaweb1=(ImageView)findViewById(R.id.paginaweb1);
        progressBar=(RelativeLayout)findViewById(R.id.horario_progressbar_pendiente);
        listView=(GridView)findViewById(R.id.listaHorario_pendiente);
        regresar= (LinearLayout)findViewById(R.id.regresar1);
        panico=(LinearLayout)findViewById(R.id.panico);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);


    }


    public void mostrarDatos(JSONArray datos1){
        String Dia,Entrada="",Salida="";
        JSONArray datos=null;
        String minutosEntrada="";
        String minutosSalida="";
        dia=new String[7];
        Hentrada=new String[7];
        Hsalida=new String[7];
        color= new String[7];
        color[0]="VALIDO";
        color[1]="VALIDO";
        color[2]="VALIDO";
        color[3]="VALIDO";
        color[4]="VALIDO";
        color[5]="VALIDO";
        color[6]="VALIDO";

        dia[0]="Lunes";
        dia[1]="Martes";
        dia[2]="Miércoles";
        dia[3]="Jueves";
        dia[4]="Viernes";
        dia[5]="Sábado";
        dia[6]="Domingo";
        //Entrada
        Hentrada[0]="";
        Hentrada[1]="";
        Hentrada[2]="";
        Hentrada[3]="";
        Hentrada[4]="";
        Hentrada[5]="";
        Hentrada[6]="";
        //Salida

        Hsalida[0]="";
        Hsalida[1]="";
        Hsalida[2]="";
        Hsalida[3]="";
        Hsalida[4]="";
        Hsalida[5]="";
        Hsalida[6]="";

        if(datos1!=null)
        {
            datos=datos1;

            //boolean validar;
            String validar;

            for(int i=0;i<datos.length();i++)
            {
                try{
                    JSONObject obtenerHorarios=datos.getJSONObject(i);;
                    validar=obtenerHorarios.getString("Estatus");

                    if(validar.equals("PROPUESTA")||validar.equals("PEND. POR LIBERAR"))
                    {
                        Dia=obtenerHorarios.getString("ti_dia_semana");
                        Entrada=obtenerHorarios.getString("tm_hora_entrada");
                        Salida=obtenerHorarios.getString("tm_hora_salida");
                        String Entra=Entrada.substring(0,2);
                        minutosEntrada=Entrada.substring(3,5);
                        int pru=Integer.valueOf(Entra);
                        String Sal=Salida.substring(0, 2);
                        minutosSalida=Salida.substring(3,5);
                        int pr=Integer.valueOf(Sal);

                        switch (Dia)
                        {
                            case "1":

                                color[0]=obtenerHorarios.getString("Estatus");
                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[0]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[0]=pru+":"+minutosEntrada+" am";
                                    }

                                }
                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[0]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<=12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[0]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[0]=pr+":"+minutosSalida+" am";
                                    }


                                }

                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[0]=Salida+":"+minutosSalida+" pm";
                                }

                                break;
                            case  "2":

                                color[1]=obtenerHorarios.getString("Estatus");

                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[1]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[1]=pru+":"+minutosEntrada+" am";
                                    }

                                }
                                ///

                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[1]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<=12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[1]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[1]=pr+":"+minutosSalida+" am";
                                    }


                                }


                                ///

                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[1]=Salida+":"+minutosSalida+" pm";
                                }


                                break;
                            case "3":

                                color[2]=obtenerHorarios.getString("Estatus");

                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[2]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[2]=pru+":"+minutosEntrada+" am";
                                    }

                                }

                                //
                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[2]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<=12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[2]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[2]=pr+":"+minutosSalida+" am";
                                    }


                                }

                                //

                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[2]=Salida+":"+minutosSalida+" pm";
                                }


                                break;
                            case  "4":

                                color[3]=obtenerHorarios.getString("Estatus");

                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[3]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[3]=pru+":"+minutosEntrada+" am";
                                    }

                                }

                                //

                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[3]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[3]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[3]=pr+":"+minutosSalida+" am";
                                    }


                                }

                                //
                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[3]=Salida+":"+minutosSalida+" pm";
                                }

                                break;
                            case "5":
                                color[4]=obtenerHorarios.getString("Estatus");

                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[4]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[4]=pru+":"+minutosEntrada+" am";
                                    }


                                }
                                //

                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[4]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<=12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[4]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[4]=pr+":"+minutosSalida+" am";
                                    }


                                }

                                //



                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[4]=Salida+":"+minutosSalida+" pm";

                                }



                                break;
                            case "6":
                                color[5]=obtenerHorarios.getString("Estatus");


                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[5]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[5]=pru+":"+minutosEntrada+" am";
                                    }

                                }

                                //


                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[5]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<=12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[5]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[5]=pr+":"+minutosSalida+" am";
                                    }


                                }

                                //


                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[5]=Salida+":"+minutosSalida+" pm";
                                }

                                break;

                            case "0":
                                color[6]=obtenerHorarios.getString("Estatus");

                                if(pru<12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada[6]=Entrada+" am";

                                    }else
                                    {
                                        Hentrada[6]=pru+":"+minutosEntrada+" am";
                                    }

                                }

                                //

                                if(pru>=12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[6]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<=12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida[6]=Salida+" am";

                                    }else
                                    {
                                        Hsalida[6]=pr+":"+minutosSalida+" am";
                                    }


                                }

                                //

                                if(pr>=12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida[6]=Salida+":"+minutosSalida+" pm";
                                }


                                break;
                        }

                    }
                    else
                    {

                    }

                    adapterhorarios = new Adapterhorarios(this,R.layout.item_horariosvista,dia);
                    adapterhorarios.setContext(this);
                    adapterhorarios.setDiaSemana(dia);
                    adapterhorarios.setEntradaHora(Hentrada);
                    adapterhorarios.setSalidaHora(Hsalida);
                    adapterhorarios.setColor(color);
                    listView.setAdapter(adapterhorarios);


                }
                catch (JSONException e)
                {
                  // alertaError(e.toString());
                    alertas.alertas(e.toString(),this);
                }

            }

            progressBar.setVisibility(View.GONE);




        }
        else{

            progressBar.setVisibility(View.GONE);
            //alertaError(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);
        }









    }



    public String horarios(String hora)
    {
        String hora_exacta="";
        int entero=Integer.valueOf(hora);
        if(entero==12){
            hora_exacta="12";
        }

        if(entero==13)
        {
            hora_exacta="1";
        }
        if(entero==14)
        {
            hora_exacta="2";

        }
        if(entero==15){
            hora_exacta="3";
        }
        if(entero==16){
            hora_exacta="4";
        }
        if(entero==17){
            hora_exacta="5";
        }
        if(entero==18){
            hora_exacta="6";
        }
        if(entero==19)
        {
            hora_exacta="7";

        }
        if(entero==20){
            hora_exacta="8";
        }
        if(entero==21)
        {
            hora_exacta="9";
        }
        if(entero==22)
        {
            hora_exacta="10";

        }
        if(entero==23)
        {
            hora_exacta="11";

        }

        return hora_exacta;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void validar()
    {
        DatosContacto datosContacto= new DatosContacto();

        if(!datosContacto.isguardar(this))
        {
            Intent intent= new Intent(this,Contactos.class);
            startActivity(intent);

        }
        else{

            Intent intent= new Intent(this,Panico.class);
            intent.putExtra("Main",false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}
