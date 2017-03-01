package com.gruposalinas.elektra.movilidadgs.Activities;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterUbicaciones_ZonasPlantilla;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.CheckTiendasAsync;
import com.gruposalinas.elektra.movilidadgs.async.ListadoEmpleadosUbicacionesAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Plantilla_ZonasYubicaciones extends Activity {

    LinearLayout textoBotonpendiente,Panico1;
    GridView listasTienda,listaubicaciones;
    String nombre;
    private AdapterUbicaciones_ZonasPlantilla adapterUbicaciones_zonas;
    JSONArray array,Zonas;
    RelativeLayout progreesbar;
    ArrayList <Double>latitud=null;
  ArrayList <Double>longitud=null;
    ArrayList<String> va_numero_pos=null;
    ArrayList<Integer> d_csc_zo_pos=null;
    int contador=0,contadorZonas=0,tamano=0,tamanoZ=0;
    ImageView listaTiendasD,delete;
    LayoutInflater layoutInflater;
    View popView;
    PopupWindow popupWindow;
    LinearLayout regresar;
    ImageView paginaWEb;
    ArrayList <String>Zonas1=null;
    ArrayList <String>NombreTiendas=null;
    ArrayList<Integer>imagen=null;
    DrawerLayout dLayout;
    LinearLayout menu_horario,menu_ubicacion;
    LinearLayout menu_incidencias, menu_panico,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    LinearLayout contacto;
    Alertas alertas;
    TextView selecionaEmpleado;
    String numero_empleado_actual;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantilla__zonas_yubicaciones);

       init();
        alertas = new Alertas(this);


        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, Contacto.class);
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
        dLayout.setFitsSystemWindows(true);

        // iniciliar pop para poder cerrarlo//

        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        popView = layoutInflater.inflate(R.layout.pop_menu, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        popupWindow.dismiss();


        //////////////////////////////////
        textoBotonpendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // manda el array json para ver los pendientes
                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, Plantilla_ZonasYubicaciones_pendientes.class);
                //  intent.putExtra("datosArrayJson", array.toString());
                // intent.putExtra("datosZonas",Zonas.toString());
                startActivity(intent);
            }
        });
        listaTiendasD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                // abre un pop para poder ver zonas o ubicaciones segun la seleecion
                layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popView = layoutInflater.inflate(R.layout.pop_menu, null);
                popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                Button zonas=(Button)popView.findViewById(R.id.MainZonas);
                Button UbicacionEspecifica=(Button)popView.findViewById(R.id.Especifica);
                LinearLayout cerrarPop=(LinearLayout)popView.findViewById(R.id.cerrarpop);
                LinearLayout panicos=(LinearLayout)popView.findViewById(R.id.panico);
                ImageView paginawe=(ImageView)popView.findViewById(R.id.we7);
                final LinearLayout panico=(LinearLayout)popView.findViewById(R.id.panico);
                final LinearLayout menu_horario1=(LinearLayout)popView.findViewById(R.id.horarios_menu);
                final LinearLayout menu_ubicacion1=(LinearLayout)popView.findViewById(R.id.Ubicacion_menu);
                final LinearLayout menu_incidencias1=(LinearLayout)popView.findViewById(R.id.menu_incidencias);
                final LinearLayout menu_panico1=(LinearLayout)popView.findViewById(R.id.menu_panico);

                popupWindow.showAtLocation(listaTiendasD, Gravity.CENTER_HORIZONTAL, 0, 0);
                popupWindow.update(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                Log.d("EntraPOP", "entrar");

                panicos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validar();
                    }
                });



                UbicacionEspecifica.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // te mmuestra las unicaciones
                      Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, listado_ubicaciones_plantilla.class);
                        intent.putExtra("numeroempleado",numero_empleado_actual);
                        startActivity(intent);
                        popupWindow.dismiss();
                        Log.d("salirpop", "cerrar");


                    }
                });

                // te muestra las zonas

                zonas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, AsignarZona_Plantilla.class);
                        intent.putExtra("numeroempleado",numero_empleado_actual);
                        startActivity(intent);
                        popupWindow.dismiss();
                        Log.d("salepop", "cerrar");


                    }
                });

                menu_ubicacion1.setFitsSystemWindows(true);
                menu_horario1.setFitsSystemWindows(true);
                menu_panico1.setFitsSystemWindows(true);
                menu_incidencias1.setFitsSystemWindows(true);

                menu_horario1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent= new Intent(Plantilla_ZonasYubicaciones.this,Horarios_consulta.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                menu_ubicacion1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        Intent intent= new Intent(Plantilla_ZonasYubicaciones.this,Activity_Tiendas.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                });

                menu_incidencias1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent= new Intent(Plantilla_ZonasYubicaciones.this,ListaIncidenciasActivity.class);
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

                cerrarPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Log.d("salepop", "salir");

                    }
                });
                paginawe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paginaweb paginaweb= new paginaweb(getApplicationContext());
                    }
                });
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        paginaWEb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paginaweb paginaweb = new paginaweb(getApplicationContext());


            }
        });
        Panico1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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

                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Plantilla_ZonasYubicaciones.this,ListaIncidenciasActivity.class);
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
        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
        reloj=(ImageView)findViewById(R.id.reloj);
        tocar=(ImageView)findViewById(R.id.tocar);
        texto_menuUbicacion=(TextView)findViewById(R.id.texto_menuUbicacion);
        texto_menuHorario=(TextView)findViewById(R.id.texto_menuHorario);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
        menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        listasTienda=(GridView)findViewById(R.id.listatiendas);
        listaubicaciones=(GridView)findViewById(R.id.listatiendasUbicaciones);
        textoBotonpendiente=(LinearLayout)findViewById(R.id.tiendas_pendientes);
        textoBotonpendiente.setVisibility(View.INVISIBLE);
        progreesbar= (RelativeLayout)findViewById(R.id.progressbar_tiendas);
        progreesbar.setVisibility(View.GONE);
        Panico1=(LinearLayout)findViewById(R.id.panico);
        listaTiendasD=(ImageView)findViewById(R.id.imagenagregar);
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        paginaWEb=(ImageView)findViewById(R.id.paginaweb);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        selecionaEmpleado=(TextView)findViewById(R.id.seleccionEmpleado);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

              llamarDatosEmpleado();

    }
    // tarea en segundo plano que permite ver tanto zonas como ubicaciones
    public void TareaAsync(Tiendas tiendas)
    {
        progreesbar.setVisibility(View.VISIBLE);
        CheckTiendasAsync checkTiendasAsync=new CheckTiendasAsync(this);
        checkTiendasAsync.execute(tiendas);


    }

    public void ObtenerTiendas(final Tiendas tiendas)
    {




        zonas(tiendas);
        ubicaciones(tiendas);


// te manda a ver el detalle de zona y a la ves quitarla
        listasTienda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ver detalle//
                int posicion = d_csc_zo_pos.get(position);
                String pos = String.valueOf(posicion);

                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, DetalleZonaEliminar.class);
                intent.putExtra("posicion", pos);
                startActivity(intent);


            }
        });
        // te deja ver el mapa y a la ves quitar esta zona de tu lista
        listaubicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String datosPonernombretienda = parent.getItemAtPosition(position).toString();
                Double latitud1, longitud1;
                latitud1 = null;
                longitud1 = null;
                String va_numero_pos1 = "";

                latitud1 = latitud.get(position);
                longitud1 = longitud.get(position);
                va_numero_pos1 = va_numero_pos.get(position);


                Intent intent = new Intent(Plantilla_ZonasYubicaciones.this, Mostrar_mapas.class);
                boolean tienda = true;
                //ubicaciones
                intent.putExtra("nombreTienda", datosPonernombretienda);
                intent.putExtra("latitud", latitud1);
                intent.putExtra("longitud", longitud1);
                intent.putExtra("va_numero_pos", va_numero_pos1);
                intent.putExtra("tiendas", tienda);
                startActivity(intent);

            }
        });
    }

    public void ubicaciones(Tiendas tiendas)
    {
        progreesbar.setVisibility(View.GONE);
        String id_estatus_posic="";

        array=tiendas.getArregloTiendas();
        if(tiendas.isSuccess())
        {
           // boolean validar=false;


            for(int y=0;y<array.length();y++)
            {
                try{
                    JSONObject object=array.getJSONObject(y);

                    //validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if(id_estatus_posic.equals("C"))
                    {
                        tamano++;


                    }
                    else {
                        textoBotonpendiente.setVisibility(View.VISIBLE);
                    }

                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                    tiendas.setMensajeError(e.toString());
                    // alertas(getString(R.string.msjError));
                    alertas.alertas(getString(R.string.msjError),this);
                }

            }

            if(latitud==null && longitud==null && va_numero_pos==null)
            {
                latitud=new ArrayList<>();
                longitud=new ArrayList<>();
                va_numero_pos=new ArrayList<>();

            }
            latitud=new ArrayList<>();
            longitud=new ArrayList<>();
            va_numero_pos=new ArrayList<>();
            NombreTiendas=new ArrayList<>();
            imagen= new ArrayList<>();

            for(int i=0;i<array.length();i++)
            {
                try{
                    JSONObject object=array.getJSONObject(i);

                   // validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if(id_estatus_posic.equals("C"))
                    {
                        NombreTiendas.add(object.getString("va_nombre_pos").trim());
                        latitud.add(object.getDouble("dec_latitud"));
                        longitud.add(object.getDouble("dec_longitud"));
                        va_numero_pos.add(object.getString("va_numero_pos").trim());
                        imagen.add(R.drawable.ubicacion_lista);
                        contador++;

                    }
                    else if(id_estatus_posic.equals("P"))
                    {
                        // visualizar el boton en caso haya tiendas  falvso
                        textoBotonpendiente.setVisibility(View.VISIBLE);
                    }
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                    tiendas.setMensajeError(e.toString());
                    //alertas(getString(R.string.msjError));
                    alertas.alertas(getString(R.string.msjError),this);
                }
            }
            //


            adapterUbicaciones_zonas=new AdapterUbicaciones_ZonasPlantilla(this,R.layout.lista_zonas_ubicacion,NombreTiendas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(NombreTiendas);
            listaubicaciones.setAdapter(adapterUbicaciones_zonas);


        }
        else{
            //alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);

            NombreTiendas=new ArrayList<>();
            NombreTiendas.add("");
            imagen=new ArrayList<>();
            imagen.add(R.drawable.ubicacion_lista);
            adapterUbicaciones_zonas=new AdapterUbicaciones_ZonasPlantilla(this,R.layout.lista_zonas_ubicacion,NombreTiendas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(NombreTiendas);
            listaubicaciones.setAdapter(adapterUbicaciones_zonas);
            listaubicaciones.setVisibility(View.INVISIBLE);


        }

    }

    public void zonas(Tiendas tiendas)
    {
        JSONArray JsonArrar=null;
        progreesbar.setVisibility(View.GONE);
      //  boolean validar=false;
        String id_estatus_posic="";
        if(tiendas.isSuccess())
        {
            JsonArrar=tiendas.getZona();
            Zonas=tiendas.getZona();
            for(int i=0;i<JsonArrar.length();i++)
            {
                try {
                    JSONObject object=JsonArrar.getJSONObject(i);

                   // validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if (id_estatus_posic.equals("C"));
                    {

                        tamanoZ++;

                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    // alertas(getString(R.string.msjError));
                    alertas.alertas(getString(R.string.msjError),this);
                }

            }



            Zonas1=new ArrayList<>();
            d_csc_zo_pos=new ArrayList<>();
            imagen= new ArrayList<>();

            for(int i=0;i<JsonArrar.length();i++)
            {
                try {
                    JSONObject object=JsonArrar.getJSONObject(i);

                  //  validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if (id_estatus_posic.equals("C"))
                    {

                        Zonas1.add(object.getString("va_descripcion_zn_posic").trim());

                        d_csc_zo_pos.add(object.getInt("id_csc_zo_pos"));
                        imagen.add(R.drawable.flecha_lista);
                        contadorZonas++;

                    }

                     if(id_estatus_posic.equals("P")){
                        textoBotonpendiente.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    //alertas("No se cargaron los datos.");
                    alertas.alertas("No se cargaron los datos.",this);
                }

            }
            adapterUbicaciones_zonas=new AdapterUbicaciones_ZonasPlantilla(this,R.layout.lista_zonas_ubicacion,Zonas1);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(Zonas1);
            listasTienda.setAdapter(adapterUbicaciones_zonas);



        }
        else{
            // alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);
            Zonas1= new ArrayList<>();
            imagen= new ArrayList<>();
            imagen.add(R.drawable.flecha_lista);
            Zonas1.add("");
            adapterUbicaciones_zonas=new AdapterUbicaciones_ZonasPlantilla(this,R.layout.lista_zonas_ubicacion,Zonas1);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(Zonas1);
            listasTienda.setAdapter(adapterUbicaciones_zonas);
            listasTienda.setVisibility(View.INVISIBLE);

        }

    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (popupWindow.isShowing() ||drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            popupWindow.dismiss();
        }
        else
        {
            Intent intent=new Intent(Plantilla_ZonasYubicaciones.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        if(progreesbar.isShown())
        {
            CheckTiendasAsync checkTiendasAsync=new CheckTiendasAsync(this);
            checkTiendasAsync.cancel(true);


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

    public void Datos_empleados(final Tiendas tiendas)
    {
        progreesbar.setVisibility(View.GONE);

        if(tiendas.isSuccess())
        {
            if(tiendas.getMostrar_Nombres()==null || tiendas.getMostrar_Nombres().size()<0){
                selecionaEmpleado.setText("vacio");

            }
            else
            {
                if(tiendas.getMostrarCompleto().isEmpty()){
                    selecionaEmpleado.setText("no tiene");
                    finish();
                    Toast.makeText(getApplicationContext(),"No tienes a nadie a cargo con zonas o ubicaciones por autorizar",Toast.LENGTH_LONG).show();
                }
                else{

                    selecionaEmpleado.setText(tiendas.getMostrarCompleto().get(0));


                    numero_empleado_actual=tiendas.getNumero_empleados().get(0);
                    zonasdatos(tiendas.getNumero_empleados().get(0));


                }

            }


            selecionaEmpleado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrar(tiendas.getMostrarCompleto(),selecionaEmpleado,tiendas);
                }
            });




        }

    }

    public void llamarDatosEmpleado()
    {

        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        nombre=prefs.getString(Constants.SP_NAME,"");
        Tiendas tiendas=new Tiendas();
        tiendas.setId_empleado(id_empleado);

        ListadoEmpleadosUbicacionesAsync listadoEmpleadosUbicacionesAsync= new ListadoEmpleadosUbicacionesAsync(this);

        listadoEmpleadosUbicacionesAsync.execute(tiendas);
        progreesbar.setVisibility(View.VISIBLE);
    }


    public  void mostrar(ArrayList arrayList, final TextView textView , final Tiendas tiendas){
        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
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
                if(!adapterUbicaciones_zonas.isEmpty())
                {
                    Zonas1.clear();
                    d_csc_zo_pos.clear();
                    imagen.clear();
                    tamanoZ=0;
                    contadorZonas=0;

                    latitud.clear();
                    longitud=null;
                    va_numero_pos.clear();
                    NombreTiendas.clear();
                    contador=0;


                }

                textoBotonpendiente.setVisibility(View.INVISIBLE);

                zonasdatos(tiendas.getNumero_empleados().get(position));
                numero_empleado_actual=tiendas.getNumero_empleados().get(position);

                alert.dismiss();
            }
        });

    }

    public void zonasdatos(String numero){


        Tiendas tiendas = new Tiendas();
        tiendas.setId_empleado(numero);
        TareaAsync(tiendas);


    }
}
