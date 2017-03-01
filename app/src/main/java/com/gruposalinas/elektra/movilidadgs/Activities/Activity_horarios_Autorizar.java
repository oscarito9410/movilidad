package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.adapterHorarios_autorizar;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.CheckHorarioAsync;
import com.gruposalinas.elektra.movilidadgs.async.HorarioPendienteAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Horapendiente;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_horarios_Autorizar extends Activity {

    GridView lista;
    JSONArray listafiltrar;
    String listado,Nombre;
    ArrayList <String> dia;
    ArrayList <String> Hentrada;
    ArrayList <String> Hsalida;
    adapterHorarios_autorizar adapterhorarios;
    TextView nombrela;
    String numeroPersona;
    ArrayList <String>chcar;
    ArrayList <String> salidaH;
    ArrayList <String> EntradaH;
    RelativeLayout progreso;
    ArrayList<String>validar;
    LinearLayout regresar;
    ArrayList<String> listadoNombres;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu,delete;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,paginaweb5;
    ImageButton back;

    //
    Intent intent;
    Alertas alertas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_horarios__autorizar);
        listadoNombres= new ArrayList<>();

        Bundle bundle=getIntent().getExtras();
        listado=bundle.getString("lista");
         listadoNombres = (ArrayList<String>) getIntent().getStringArrayListExtra("Nombre");

        init();
        alertas= new Alertas(this);

        delete=(LinearLayout)findViewById(R.id.del);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.closeDrawer(GravityCompat.START);
            }
        });
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(Activity_horarios_Autorizar.this,Nombres_lista.class);
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

        menu_ubicacion.setFitsSystemWindows(true);
        menu_horario.setFitsSystemWindows(true);
        menu_panico.setFitsSystemWindows(true);
        menu_incidencias.setFitsSystemWindows(true);

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_horarios_Autorizar.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Activity_horarios_Autorizar.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Activity_horarios_Autorizar.this, ListaIncidenciasActivity.class);
                intent.putExtra("plantilla",false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(Activity_horarios_Autorizar.this,Panico.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Main", false);
                startActivity(intent);

            }
        });

        pendientes_zonas_horarios_menu.setFitsSystemWindows(true);
        pendientes_zonas_horarios_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        try {
            listafiltrar=new JSONArray(listado);
        } catch (JSONException e)
        {
            e.printStackTrace();
            listafiltrar=null;
        }

        mostrar();


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
        lista= (GridView)findViewById(R.id.listaHorario);
        nombrela=(TextView)findViewById(R.id.nombres);
        nombrela.setText(listadoNombres.get(0));
        progreso=(RelativeLayout)findViewById(R.id.horario_progressbar);
        regresar=(LinearLayout)findViewById(R.id.regresar);
        paginaweb5=(ImageView)findViewById(R.id.paginaweb5);
        panico=(LinearLayout)findViewById(R.id.panico);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                intent= new Intent(Activity_horarios_Autorizar.this,Nombres_lista.class);
                startActivity(intent);
                finish();

            }
        });

        nombrela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrar1(listadoNombres, nombrela);

            }
        });


    }

    public void mostrar()
    {
       String  Dia;
        String Entrada="";
        String Salida="";
        String minutosEntrada="";
        String minutosSalida="";

        dia=new ArrayList<>();
        Hentrada=new ArrayList<>();
        Hsalida=new ArrayList<>();
        EntradaH= new ArrayList<>();
        salidaH= new ArrayList<>();
        chcar= new ArrayList<>();
        validar= new ArrayList<>();

        for(int i=0;i<listafiltrar.length();i++)
        {
            try
            {
                JSONObject obtenerHorarios=listafiltrar.getJSONObject(i);
                String nombres=obtenerHorarios.getString("va_nombre_completo");

                if(nombrela.getText().toString().equals(nombres))
                {
                    Dia=obtenerHorarios.getString("ti_dia_semana");
                    Entrada=obtenerHorarios.getString("tm_hora_entrada");
                    Salida=obtenerHorarios.getString("tm_hora_salida");
                    String Entra=Entrada.substring(0, 2);
                    minutosEntrada=Entrada.substring(3, 5);
                    int pru=Integer.valueOf(Entra);
                    String Sal=Salida.substring(0, 2);
                    minutosSalida=Salida.substring(3, 5);
                    int pr=Integer.valueOf(Sal);
                    numeroPersona=obtenerHorarios.getString("id_num_empleado");
                 String   validar1=obtenerHorarios.getString("Estatus");

                    if(validar1.equals("PROPUESTA")||validar1.equals("PEND. POR LIBERAR")){
                        switch (Dia)
                        {
                            case "1":
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));
                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));
                                validar.add(obtenerHorarios.getString("Estatus"));
                                dia.add("Lunes");
                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }

                                }
                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }


                                }

                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");
                                }

                                break;
                            case  "2":

                                dia.add("Martes");
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));
                                validar.add(obtenerHorarios.getString("Estatus"));

                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));

                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }

                                }
                                ///

                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }

                                }
                                ///

                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");
                                }


                                break;
                            case "3":

                                dia.add("Miercoles");
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));
                                validar.add(obtenerHorarios.getString("Estatus"));
                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));

                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }

                                }

                                //
                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }


                                }

                                //

                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");
                                }


                                break;

                            case  "4":
                                dia.add("Jueves");
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));

                                validar.add(obtenerHorarios.getString("Estatus"));

                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));

                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }

                                }

                                //

                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }


                                }

                                //
                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");
                                }

                                break;
                            case "5":

                                dia.add("Viernes");
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));

                                validar.add(obtenerHorarios.getString("Estatus"));

                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));

                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }


                                }
                                //

                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }


                                }

                                //

                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");

                                }



                                break;
                            case "6":
                                dia.add("Sabado");
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));
                                validar.add(obtenerHorarios.getString("Estatus"));

                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));


                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }

                                }

                                //


                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }


                                }

                                //


                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");
                                }

                                break;

                            case "0":
                                dia.add("Domingo");
                                chcar.add(obtenerHorarios.getString("ti_dia_semana"));
                                validar.add(obtenerHorarios.getString("Estatus"));

                                EntradaH.add(obtenerHorarios.getString("tm_hora_entrada"));
                                salidaH.add(obtenerHorarios.getString("tm_hora_salida"));

                                if(pru<=12)
                                {
                                    if(Entra.equals("00"))
                                    {
                                        Entrada="12:"+minutosEntrada;
                                        Hentrada.add(Entrada+" am");

                                    }else
                                    {
                                        Hentrada.add(pru+":"+minutosEntrada+" am");
                                    }

                                }

                                //

                                if(pru>12)
                                {
                                    Entra=  horarios(String.valueOf(pru));
                                    Hentrada.add(Entra+":"+minutosEntrada+" pm");
                                }

                                if(pr<=12)
                                {
                                    if(Salida.equals("00"))
                                    {
                                        Salida="12:"+minutosSalida;
                                        Hsalida.add(Salida+" am");

                                    }else
                                    {
                                        Hsalida.add(pr+":"+minutosSalida+" am");
                                    }


                                }

                                //

                                if(pr>12)
                                {
                                    Salida=  horarios(String.valueOf(pr));
                                    Hsalida.add(Salida+":"+minutosSalida+" pm");
                                }


                                break;
                        }
                    }






                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            // alerta que menciona si autorizas o rechazas

                            if(validar.get(position).equals("PROPUESTA"))
                            {
                                alertaConfirmar(position,"");


                            }
                            else if(validar.get(position).equals("PEND. POR LIBERAR")){
                                // metodo de aprobar o rechazar

                                alertaConfirmar(position, "2");


                            }


                        }
                    });
                }


            }
            catch (JSONException e)
            {

            }

        }

        adapterhorarios = new adapterHorarios_autorizar(this,R.layout.item_horariosvista,dia);
        adapterhorarios.setContext(this);
        adapterhorarios.setDiaSemana(dia);
        adapterhorarios.setEntradaHora(Hentrada);
        adapterhorarios.setSalidaHora(Hsalida);
        adapterhorarios.setValidar(validar);
        lista.setAdapter(adapterhorarios);


        if(adapterhorarios==null)
        {
            Toast.makeText(getApplicationContext(),"No tiene horarios pendientes",Toast.LENGTH_LONG).show();

        }


    }

    public String horarios(String hora)
    {
        String hora_exacta="";
        int entero=Integer.valueOf(hora);

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


    public void dialiberar(String entrada,String salida,String comentario,String dia ,String edicion)
    {
        progreso.setVisibility(View.VISIBLE);
        Horapendiente horapendiente=new Horapendiente();
        if(edicion.equals("aprobar"))
        {
            horapendiente.setId_empleado(numeroPersona);
            horapendiente.setTi_dia_semana(dia);
            horapendiente.setTm_hora_entrada(entrada);
            horapendiente.setTm_hora_salida(salida);
            horapendiente.setId_empleadoLogeado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
            horapendiente.setComentario(comentario);
            horapendiente.setEdicion(edicion);
            HorarioPendienteAsync horarioPendienteAsync= new HorarioPendienteAsync(this,"2");
            horarioPendienteAsync.execute(horapendiente);
        }
        if(edicion.equals("rechazar"))
        {

            comentario(entrada,salida,dia,false,comentario,"rechazar");

        }

    }
    public void alertaConfirmar(int posicion, final String liberar)
    {
        final String entrada=EntradaH.get(posicion);
        final String salida=salidaH.get(posicion);
        final String di=chcar.get(posicion);


        final Dialog alert = new Dialog(Activity_horarios_Autorizar.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.comentario_plantilla_permiso, null);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_rechazar);
        final EditText editar=(EditText)dialogo.findViewById(R.id.comentario_);
        final LinearLayout cerrar=(LinearLayout)dialogo.findViewById(R.id.cerrar);

        alert.setContentView(dialogo);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        editar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    // Ocultar teclado virtual
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    procesado = true;
                }
                return procesado;

            }
        });
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                if (liberar.equals("")) {
                    autorizar(entrada, salida, di, true, editar.getText().toString());


                } else if (liberar.equals("2")) {
                    dialiberar(entrada, salida, editar.getText().toString(), di, "aprobar");

                }


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();

                if (liberar.equals("")) {
                    comentario(entrada, salida, di, false, editar.getText().toString(), "");

                } else if (liberar.equals("2")) {
                    dialiberar(entrada, salida, editar.getText().toString(), di, "rechazar");

                }


            }
        });
        alert.show();




    }

    public void autorizar(String hora,String salida,String dia,boolean validar,String comentario)
    {


            progreso.setVisibility(View.VISIBLE);
            Horarios horarios= new Horarios();
            horarios.setNumerodelEmpleado(numeroPersona);
            horarios.setComentario(comentario);
            horarios.setTi_dia_semana(dia);
            horarios.setTm_hora_entrada(hora);
            horarios.setTm_hora_salida(salida);
            horarios.setBit_valido(validar);
            horarios.setId_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
            CheckHorarioAsync checkHorarioAsync= new CheckHorarioAsync(this);
            checkHorarioAsync.execute(horarios);

    }

    public  void confirmar(Horarios horarios)
    {
        progreso.setVisibility(View.GONE);

        if(horarios.isSuccess())
        {
            final Dialog alert = new Dialog(Activity_horarios_Autorizar.this,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
            LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
            TextView texto=(TextView)dialogo.findViewById(R.id.temporal);
            texto.setText("");

            alert.setContentView(dialogo);
            alert.setCancelable(false);


            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                    Intent intent= new Intent(Activity_horarios_Autorizar.this,Nombres_lista.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });
            alert.show();

        }
        else{
             //error("No se pudieron enviar los datos.");
            alertas.alertas("No se pudieron enviar los datos.",this);
        }

    }


    public String comentario(final String hora, final String salida, final String dia, final boolean validar, final String comentario, final String edicion)
    {

        if (edicion.equals("rechazar")) {
            RechazarLibre(hora, salida,comentario , edicion, dia);
        } else {
            rechazar(hora, salida, dia, validar, comentario);

        }



return "";
    }

    public void RechazarLibre(String entrada,String salida,String comentario,String edicion,String dia){

        progreso.setVisibility(View.VISIBLE);
        Horapendiente horapendiente= new Horapendiente();
        horapendiente.setId_empleado(numeroPersona);
        horapendiente.setTi_dia_semana(dia);
        horapendiente.setTm_hora_entrada(entrada);
        horapendiente.setTm_hora_salida(salida);
        horapendiente.setId_empleadoLogeado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
        horapendiente.setComentario(comentario);
        horapendiente.setEdicion(edicion);
        HorarioPendienteAsync horarioPendienteAsync= new HorarioPendienteAsync(this,"2");
        horarioPendienteAsync.execute(horapendiente);

    }

    public  void rechazar(String hora,String salida,String dia,boolean validar,String comentario){
        progreso.setVisibility(View.VISIBLE);
        Horarios horarios= new Horarios();
        horarios.setNumerodelEmpleado(numeroPersona);
        horarios.setComentario(comentario);
        horarios.setTi_dia_semana(dia);
        horarios.setTm_hora_entrada(hora);
        horarios.setTm_hora_salida(salida);
        horarios.setBit_valido(validar);
        horarios.setId_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
        CheckHorarioAsync checkHorarioAsync= new CheckHorarioAsync(this);
        checkHorarioAsync.execute(horarios);

    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {

            intent= new Intent(Activity_horarios_Autorizar.this,Nombres_lista.class);
            startActivity(intent);
            finish();


        }


    }


    public  void confirmarlibre(Horapendiente horapendiente)
    {
        progreso.setVisibility(View.GONE);
        if(horapendiente.isSuccess())
        {
            final Dialog alert = new Dialog(Activity_horarios_Autorizar.this,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
            LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
            TextView texto=(TextView)dialogo.findViewById(R.id.temporal);
            texto.setText("");
            alert.setContentView(dialogo);
            alert.setCancelable(false);

            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                    Intent intent= new Intent(Activity_horarios_Autorizar.this,Nombres_lista.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });
            alert.show();

        }
        else{
           // error("Error de conexión intentar más tarde.");

            alertas.alertas("Error de conexión intentar más tarde.", this);
        }

    }


    public  void mostrar1(ArrayList arrayList, final TextView textView){
        final Dialog alert = new Dialog(Activity_horarios_Autorizar.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.lista_horas_pm, null);
        GridView lista=(GridView)dialogo.findViewById(R.id.listado);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.spinner_item,arrayList);
        lista.setAdapter(arrayAdapter);
        alert.setContentView(dialogo);
        alert.show();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = parent.getItemAtPosition(position).toString();
                textView.setText(a);
                mostrar();
                alert.dismiss();
            }
        });

    }


}
