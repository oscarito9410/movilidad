package com.gruposalinas.elektra.movilidadgs.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.Adapterhorarios;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.CheckHorarioAsync;
import com.gruposalinas.elektra.movilidadgs.async.HorarioPendienteAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Horapendiente;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class Horarios_consulta extends Activity {
    GridView listView;
    LinearLayout pendiente;
    RelativeLayout progressBar;
    int Lunes,martes,miercoles,jueves,viernes,sabado,domingo;
    JSONArray nuevo=null;
    String EntradaHora="",EntradaMinuto="",SalidaHora="",SalidaMinuto="",diasemana;
    boolean checarpendiente=false;
    ImageView indicador,paginaweb5,delete;
    LayoutInflater layoutInflater;
    View popView;
     PopupWindow popupWindow;
    LinearLayout regresar;
    // lista personalizada
    Adapterhorarios adapterhorarios;
    String[] dia=null;
    String[]Hentrada=null;
    String[]Hsalida=null;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    String descanso1="1";
    LinearLayout contacto;
    String[] color;
    boolean Dia_propuesta[];
    Alertas alertas;

    ImageButton back;
    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios_consulta);
        init();
        alertas = new Alertas(this);

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Horarios_consulta.this, Contacto.class);
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
         layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
         popView = layoutInflater.inflate(R.layout.pop, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.dismiss();


        pendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Horarios_consulta.this, Detalle_Horario_pendiente.class);
                intent.putExtra("datos", nuevo.toString());
                startActivity(intent);
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Horarios_consulta.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        paginaweb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginaweb paginaweb = new paginaweb(getApplicationContext());
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
                Intent intent = new Intent(Horarios_consulta.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Horarios_consulta.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(Horarios_consulta.this,ListaIncidenciasActivity.class);
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

        pendientes_zonas_horarios_menu.setFitsSystemWindows(true);
        pendientes_zonas_horarios_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
       indicador=(ImageView)findViewById(R.id.imagenIndicadorhorario);
        progressBar=(RelativeLayout)findViewById(R.id.horario_progressbar);
        listView=(GridView)findViewById(R.id.listaHorario);
        pendiente= (LinearLayout)findViewById(R.id.Detalles_false);
        pendiente.setVisibility(View.INVISIBLE);
        regresar=(LinearLayout)findViewById(R.id.regresar);
        paginaweb5=(ImageView)findViewById(R.id.paginaweb5);
        panico=(LinearLayout)findViewById(R.id.panico);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Horarios_consulta.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });


        Horarios horarios=new Horarios();

        	ObtenerHorarios(horarios);// COMENTADO PARA PRUEBA

    }


    public void ObtenerHorarios(Horarios horarios)
    {
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        String id_empleado1=String.valueOf(id_empleado);
        horarios.setId_empleado(id_empleado1);
        CheckHorarioAsync checkHorarioAsync= new CheckHorarioAsync(this);
        checkHorarioAsync.execute(horarios);


    }

    public void mostrarDatos(Horarios horarios){
         String Dia,Entrada="",Salida="";
        JSONArray datos=null;
        Lunes=0; martes=0;miercoles=0;jueves=0;viernes=0;sabado=0;domingo=0;

        nuevo=horarios.getMisHorarios();
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
        Dia_propuesta= new boolean[7];
        Dia_propuesta[0]=false;
        Dia_propuesta[1]=false;
        Dia_propuesta[2]=false;
        Dia_propuesta[3]=false;
        Dia_propuesta[4]=false;
        Dia_propuesta[5]=false;
        Dia_propuesta[6]=false;








        if(horarios.isSuccess())
        {
            datos=horarios.getMisHorarios();
           // boolean validar=false;
            String validar;

            System.out.println("mostrando horario");
            for(int i=0;i<datos.length();i++)
            {

                try{
                    JSONObject obtenerHorarios=datos.getJSONObject(i);
                    validar=obtenerHorarios.getString("Estatus");

                    if(validar.equals("PROPUESTA")||validar.equals("PEND. POR LIBERAR"))
                    {
                        String dia_temp=obtenerHorarios.getString("ti_dia_semana");

                        switch (dia_temp)
                        {
                            case "1":
                                Dia_propuesta[0]=true;

                                break;
                            case "2":
                                Dia_propuesta[1]=true;

                                break;
                            case "3":
                                Dia_propuesta[2]=true;

                                break;
                            case "4":
                                Dia_propuesta[3]=true;

                                break;
                            case "5":
                                Dia_propuesta[4]=true;

                                break;
                            case "6":
                                Dia_propuesta[5]=true;

                                break;
                            case "0":
                                Dia_propuesta[6]=true;
                                break;


                        }

                    }

                    if(validar.equals("VALIDO"))
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

                                if(pr<12)
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

                                if(pr<12)
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

                                if(pr<12)
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


                                if(pru<=12)
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

                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada[3]=Entra+":"+minutosEntrada+" pm";
                                }

                                if(pr<12)
                                {
                                    if(Salida.substring(0,2).equals("00"))
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

                                if(pr<12)
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

                                if(pr<12)
                                {
                                    if(Salida.equals("00"))
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

                                if(pr<12)
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
                    else{
                        pendiente.setVisibility(View.VISIBLE);
                        checarpendiente=true;
                    }



                }
                catch (JSONException e)
                {
                    horarios.setMensajeError(e.toString());
                   // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    //alertaError("No se cargaron los datos.");
                    alertas.alertas("No se cargaron los datos.",this);
                }
            }

            progressBar.setVisibility(View.GONE);




        }
        else{

            progressBar.setVisibility(View.GONE);
          //  Toast.makeText(getApplicationContext(),horarios.getMensajeError(),Toast.LENGTH_LONG).show();
            adapterhorarios = new Adapterhorarios(this, R.layout.item_horariosvista,dia);
            adapterhorarios.setContext(this);
            adapterhorarios.setDiaSemana(dia);
            adapterhorarios.setEntradaHora(Hentrada);
            adapterhorarios.setSalidaHora(Hsalida);
            listView.setAdapter(adapterhorarios);

            //alertaError(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);

        }
        adapterhorarios = new Adapterhorarios(this,R.layout.item_horariosvista,dia);
        adapterhorarios.setContext(this);
        adapterhorarios.setDiaSemana(dia);
        adapterhorarios.setEntradaHora(Hentrada);
        adapterhorarios.setSalidaHora(Hsalida);
        adapterhorarios.setColor(color);
        listView.setAdapter(adapterhorarios);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("InflateParams")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String datosPoner = parent.getItemAtPosition(position).toString();

                if(!Dia_propuesta[position])
                {
                    String obtener_horaE=Hentrada[position];
                    String obtener_horaS=Hsalida[position];
                    String horasEntradas1="";
                    String pm="";
                    String horasSalidas1="";
                    String pm1="";

                    int lineas=0;
                    int lineas1=0;

                    StringTokenizer stringTokenizer= new StringTokenizer(obtener_horaE);

                    while (stringTokenizer.hasMoreTokens())
                    {
                        if(lineas==0)
                        {
                            horasEntradas1=stringTokenizer.nextToken();
                        }
                        else{
                            pm=stringTokenizer.nextToken();
                        }

                        lineas++;
                    }

                    StringTokenizer stringTokenizer1= new StringTokenizer(obtener_horaS);
                    while (stringTokenizer1.hasMoreTokens())
                    {
                        if(lineas1==0)
                        {
                            horasSalidas1=stringTokenizer1.nextToken();
                        }
                        else{
                            pm1=stringTokenizer1.nextToken();
                        }

                        lineas1++;

                    }
                    final String[] obtenerHoras = getResources().getStringArray(R.array.hora);
                    final String [] am_pm=getResources().getStringArray(R.array.am_pm);

                    diasemana=datosPoner;
                    layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popView = layoutInflater.inflate(R.layout.pop, null);
                    popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                    final LinearLayout button = (LinearLayout) popView.findViewById(R.id.enviarhh);
                    TextView tituloDia = (TextView) popView.findViewById(R.id.tituloDia);
                    final LinearLayout cerrar = (LinearLayout) popView.findViewById(R.id.cerrar);
                    final TextView horaEntrada=(TextView)popView.findViewById(R.id.seleccionarHEntradaHoras);
                    final TextView am_pma=(TextView)popView.findViewById(R.id.am_pm);
                    final TextView horaSalida=(TextView)popView.findViewById(R.id.seleccionarHSalidaHoras);
                    final TextView am_pm2=(TextView)popView.findViewById(R.id.am_pm1);
                    final LinearLayout panico=(LinearLayout)popView.findViewById(R.id.panico);
                    final LinearLayout menu_horario1=(LinearLayout)popView.findViewById(R.id.horarios_menu);
                    final LinearLayout menu_ubicacion1=(LinearLayout)popView.findViewById(R.id.Ubicacion_menu);
                    final LinearLayout menu_incidencias1=(LinearLayout)popView.findViewById(R.id.menu_incidencias);
                    final LinearLayout menu_panico1=(LinearLayout)popView.findViewById(R.id.menu_panico);
                    final CheckBox descanso=(CheckBox)popView.findViewById(R.id.descanso);
                    final TextView textodescanso=(TextView)popView.findViewById(R.id.textoDescanso);

                    descanso.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            if(descanso.isChecked())
                            {
                                //   Toast.makeText(getApplicationContext(),"activo",Toast.LENGTH_LONG).show();
                                horaEntrada.setEnabled(false);
                                horaSalida.setEnabled(false);
                                am_pma.setEnabled(false);
                                am_pm2.setEnabled(false);
                                descanso1="2";

                            }
                            if(!descanso.isChecked())
                            {
                                horaEntrada.setEnabled(true);
                                horaSalida.setEnabled(true);
                                am_pma.setEnabled(true);
                                am_pm2.setEnabled(true);
                                descanso1="1";

                            }


                        }
                    });
                    horaEntrada.setText(horasEntradas1);
                    horaSalida.setText(horasSalidas1);
                    am_pma.setText(pm);
                    am_pm2.setText(pm1);
                    if(horasEntradas1.equals(""))
                    {
                        horaEntrada.setText(obtenerHoras[36]);
                        descanso.setVisibility(View.INVISIBLE);
                        textodescanso.setVisibility(View.INVISIBLE);
                    }

                    if(horasSalidas1.equals(""))
                    {
                        horaSalida.setText(obtenerHoras[28]);
                    }

                    if(pm.equals(""))
                    {
                        am_pma.setText("am");
                    }
                    if(pm1.equals(""))
                    {
                        am_pm2.setText("pm");
                    }

                    menu_ubicacion1.setFitsSystemWindows(true);
                    menu_horario1.setFitsSystemWindows(true);
                    menu_panico1.setFitsSystemWindows(true);
                    menu_incidencias1.setFitsSystemWindows(true);

                    menu_horario1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent= new Intent(Horarios_consulta.this,Horarios_consulta.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    });
                    menu_ubicacion1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {


                            Intent intent= new Intent(Horarios_consulta.this,Activity_Tiendas.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    });

                    menu_incidencias1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                              Intent intent= new Intent(Horarios_consulta.this,ListaIncidenciasActivity.class);
                            intent.putExtra("plantilla",false);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    });

                    menu_panico1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            validar();
                        }
                    });
                    ImageView paginawe = (ImageView) popView.findViewById(R.id.paginawe11);
                    tituloDia.setText(tituloDia.getText() + diasemana);
                    ///
                    horaEntrada.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            mostrar(obtenerHoras,horaEntrada);
                        }
                    });

                    horaSalida.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mostrar(obtenerHoras,horaSalida);

                        }
                    });
                    am_pm2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mostrar(am_pm,am_pm2);
                        }
                    });
                    am_pma.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mostrar(am_pm, am_pma);


                        }
                    });

                    panico.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            validar();
                        }
                    });

                    //

                    popupWindow.showAtLocation(listView, Gravity.CENTER_HORIZONTAL, 0, 0);
                    popupWindow.update(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    pendiente.setVisibility(View.INVISIBLE);

                    //cerrar ventana pop//
                    cerrar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("venta emergente pop: ", "ventana CERRADA");
                            popupWindow.dismiss();
                            if (checarpendiente) {
                                pendiente.setVisibility(View.VISIBLE);
                            } else {
                                pendiente.setVisibility(View.INVISIBLE);
                            }

                        }
                    });

                    // boton para enviar datos //
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            // METODO PARA ARMAR EL HORARIO //

                            String entrada,salida,am,pm;
                            entrada=horaEntrada.getText().toString();
                            salida=horaSalida.getText().toString();
                            am=am_pma.getText().toString();//Entrada
                            pm=am_pm2.getText().toString();
                            ordenarhorario(entrada,am,salida,pm,popupWindow);
                        }
                    });
                    paginawe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paginaweb paginaweb = new paginaweb(getApplicationContext());
                        }
                    });


                    // confirmacion//


                    popupWindow.showAsDropDown(listView, 50, 50);

                }
                else{

                    validarDia(datosPoner);

                }
            }
        });


    }

    public void ordenarhorario(String HEntrada,String am_pm1,String Hsalida,String am_pm2,PopupWindow popupWindow)
    {
        EntradaHora=""; EntradaMinuto=""; SalidaHora=""; SalidaMinuto="";
        StringTokenizer stringTokenizer= new StringTokenizer(HEntrada,":");
         int HR=0;

        while (stringTokenizer.hasMoreTokens())
        {
            if(HR==0)
            {
                EntradaHora=stringTokenizer.nextToken();

            }else{
                EntradaMinuto=stringTokenizer.nextToken();
            }

            HR++;

        }

        StringTokenizer stringTokenizer1= new StringTokenizer(Hsalida,":");
        int hr=0;

        while(stringTokenizer1.hasMoreTokens())
        {
            if(hr==0)
            {
                SalidaHora=stringTokenizer1.nextToken();

            }else{
                SalidaMinuto=stringTokenizer1.nextToken();
            }
            hr++;

        }

        if(am_pm1.equals("am"))
        {
            if(EntradaHora.equals("12"))
            {
                EntradaHora="00";
            }

        }
        else
        {
            if(EntradaHora.equals("1"))
            {
                EntradaHora="13";
            }

            if(EntradaHora.equals("2"))
            {
                EntradaHora="14";
            }
            if(EntradaHora.equals("3"))
            {
                EntradaHora="15";

            }
            if(EntradaHora.equals("4"))
            {
                EntradaHora="16";

            }
            if(EntradaHora.equals("5"))
            {
                EntradaHora="17";
            }
            if(EntradaHora.equals("6"))
            {
                EntradaHora="18";
            }
            if(EntradaHora.equals("7"))
            {
                EntradaHora="19";
            }
            if(EntradaHora.equals("8"))
            {
                EntradaHora="20";
            }
            if(EntradaHora.equals("9"))
            {
                EntradaHora="21";
            }
            if(EntradaHora.equals("10"))
            {
                EntradaHora="22";
            }
            if(EntradaHora.equals("11"))
            {
                EntradaHora="23";
            }
            if(EntradaHora.equals("12"))
            {
                EntradaHora="12";
            }

        }

        // salida//


        if(am_pm2.equals("am"))
        {
            if(SalidaHora.equals("12"))
            {
                SalidaHora="00";
            }

        }
        else
        {
            if(SalidaHora.equals("1"))
            {
                SalidaHora="13";
            }

            if(SalidaHora.equals("2"))
            {
                SalidaHora="14";
            }
            if(SalidaHora.equals("3"))
            {
                SalidaHora="15";

            }
            if(SalidaHora.equals("4"))
            {
                SalidaHora="16";

            }
            if(SalidaHora.equals("5"))
            {
                SalidaHora="17";
            }
            if(SalidaHora.equals("6"))
            {
                SalidaHora="18";
            }
            if(SalidaHora.equals("7"))
            {
                SalidaHora="19";
            }
            if(SalidaHora.equals("8"))
            {
                SalidaHora="20";
            }
            if(SalidaHora.equals("9"))
            {
                SalidaHora="21";
            }
            if(SalidaHora.equals("10"))
            {
                SalidaHora="22";
            }
            if(SalidaHora.equals("11"))
            {
                SalidaHora="23";
            }
            if(SalidaHora.equals("12"))
            {
                SalidaHora="12";
            }

        }

        confirmar(EntradaHora, EntradaMinuto, SalidaHora, SalidaMinuto, popupWindow);
        //
    }
    public void armarJsonHora(String dia,String HoraEntrada,String MinutoEntrada,String HoraSalida,String MinutoSalida)
    {
        Horapendiente horapendiente=new Horapendiente();
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        String id_empleado1=(id_empleado);

        if(dia.equals("Lunes"))
        {
         horapendiente.setTi_dia_semana("1");
        }
        if(dia.equals("Martes"))
        {
         horapendiente.setTi_dia_semana("2");
        }
        if(dia.equals("Miércoles"))
        {
            horapendiente.setTi_dia_semana("3");
        }
        if(dia.equals("Jueves"))
        {
            horapendiente.setTi_dia_semana("4");
        }
        if(dia.equals("Viernes"))
        {
           horapendiente.setTi_dia_semana("5");
        }
        if(dia.equals("Sábado"))
        {
          horapendiente.setTi_dia_semana("6");
        }
        if(dia.equals("Domingo"))
        {
            horapendiente.setTi_dia_semana("0");

        }
        String HoraEntradaFinal="";
        String HoraSalidaFinal="";
        HoraEntradaFinal=HoraEntrada+":"+MinutoEntrada+":"+"00";
        HoraSalidaFinal=HoraSalida+":"+MinutoSalida+":"+"00";
        horapendiente.setTm_hora_entrada(HoraEntradaFinal);
        horapendiente.setTm_hora_salida(HoraSalidaFinal);

        horapendiente.setId_empleado(id_empleado1);
        horapendiente.setId_empleadoLogeado(id_empleado1);
        horapendiente.setComentario("");
        horapendiente.setEdicion("eliminar");
        horapendiente.setBit_valido(false);
        horapendiente.setTipo("Mias");
        HorarioPendienteAsync horarioPendienteAsync=new HorarioPendienteAsync(this,descanso1);
        horarioPendienteAsync.execute(horapendiente);

    }

    public void cerrarpop(PopupWindow popupWindow)
    {
        popupWindow.dismiss();


    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (popupWindow.isShowing() ||drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            popupWindow.dismiss();
            if(checarpendiente)
            {
                pendiente.setVisibility(View.VISIBLE);
            }
            else{
                pendiente.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            Intent intent=new Intent(Horarios_consulta.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
        if(progressBar.isShown()){
            CheckHorarioAsync checkHorarioAsync= new CheckHorarioAsync(this);
            checkHorarioAsync.cancel(true);

        }


    }




    public void checarEnvio(final Horapendiente horapendiente){

        if(horapendiente.isSuccess())
        {
            progressBar.setVisibility(View.GONE);
            alertas.Exito(this,Horarios_consulta.this);
        }
        else{
            //alertaError("No se pudieron enviar los datos intente más tarde.");
            alertas.alertas("No se pudieron enviar los datos intente más tarde.",this);
            progressBar.setVisibility(View.GONE);
        }

    }

    public void confirmar(final String Entadahr, final String entradaM, final String SalidaHR, final String SalidaM, final PopupWindow popupWindow)
    {

         final Dialog alert = new Dialog(Horarios_consulta.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        final TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);

        String mensaje=getString(R.string.alerta1);
        progressBar.setVisibility(View.GONE);
        alert.setContentView(dialogo);
        alert.setCancelable(true);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mandar datos: ", "enviar...");

                if (Entadahr.equals("selecciona") || entradaM.equals("selecciona") || SalidaHR.equals("selecciona") || SalidaM.equals("selecciona")) {
                    Log.d("mandar datos: ", "error con horarios");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    armarJsonHora(diasemana, EntradaHora, EntradaMinuto, SalidaHora, SalidaMinuto);
                    cerrarpop(popupWindow);
                    if (checarpendiente) {
                        pendiente.setVisibility(View.VISIBLE);
                    } else {
                        pendiente.setVisibility(View.INVISIBLE);
                    }

                }
                alert.dismiss();

            }

        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("mandar datos: ", "cancelado");
                alert.cancel();

            }
        });

        alert.show();


    }

    public String horarios(String hora)
    {
        String hora_exacta="";
        int entero=Integer.valueOf(hora);

        if(entero==12)
        {
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


    public void mostrar(String lis[], final TextView textView)
    {

        final Dialog alert = new Dialog(Horarios_consulta.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.lista_horas_pm, null);
        GridView lista=(GridView)dialogo.findViewById(R.id.listado);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.spinner_item,lis);
        lista.setAdapter(arrayAdapter);
        alert.setContentView(dialogo);
        alert.show();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
             String a = parent.getItemAtPosition(position).toString();
                textView.setText(a);
                alert.dismiss();
            }
        });

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


    public void validarDia(String dia_){

        final Dialog alert = new Dialog(Horarios_consulta.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        titulodialo.setText("Aviso:");
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText("Ya existe una solicitud pendiente de autorización para el día "+dia_+"."+"\n\n"+"Pide a tu jefe inmediato autorice o rechaze está solicitud.");
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();
    }


}

