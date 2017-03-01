package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterUbicaciones_Zonas;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.CheckZonasAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListaZonasActivity extends Activity {
    GridView listaZonas;
    //TextView nombreEmpleado,numeroEmpleado;
    LinearLayout regresar;
    RelativeLayout progreesbarZonas;
    ArrayAdapter listazonas=null;
    String id_empleado="";
    JSONArray listasZonas=null;
    String nombresZonas[]=null;
    int posicioneszonas[]=null;
    private AdapterUbicaciones_Zonas adapterUbicaciones_zonas;
    int imagen[]=null;
    DrawerLayout dLayout;
    LinearLayout menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    LinearLayout contacto;
    Alertas alertas;
    ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zonas);
        init();

        alertas = new Alertas(this);
        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaZonasActivity.this, Contacto.class);
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
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaZonasActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setFitsSystemWindows(true);
        menu_horario.setFitsSystemWindows(true);
        menu_panico.setFitsSystemWindows(true);
        menu_incidencias.setFitsSystemWindows(true);

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ListaZonasActivity.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ListaZonasActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(ListaZonasActivity.this,ListaIncidenciasActivity.class);
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
        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
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
        listaZonas=(GridView)findViewById(R.id.listadeZonas);
        progreesbarZonas=(RelativeLayout)findViewById(R.id.progressbar_Zonaslistas);
        progreesbarZonas.setVisibility(View.GONE);
        regresar=(LinearLayout)findViewById(R.id.regresar3);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        listazonas=new ArrayAdapter(this,R.layout.lista_zonas_ubicacion);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListaZonasActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_empleado=prefs.getString(Constants.SP_ID, "");
        Tiendas tiendas=new Tiendas();
        tiendas.setId_empleado(id_empleado);
        TareaOntenerZonas(tiendas);

    }

    public void TareaOntenerZonas(Tiendas tiendas)
    {
        progreesbarZonas.setVisibility(View.VISIBLE);
        CheckZonasAsync checkZonasAsync= new CheckZonasAsync(this);
        checkZonasAsync.execute(tiendas);
    }

    public void resultadoTarea(Tiendas tiendas)
    {
       if(tiendas.isSuccess())
       {
           llenarListaZonas(tiendas);


       }else{
           // error al cargar listaZonas//
           //alerta(getString(R.string.msjError));
           alertas.alertas(getString(R.string.msjError),this);
           progreesbarZonas.setVisibility(View.GONE);
           nombresZonas=new String[1];
           imagen= new int[1];
           imagen[0]=R.drawable.flecha_lista;
           nombresZonas[0]="";
           adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombresZonas);
           adapterUbicaciones_zonas.setContext(this);
           adapterUbicaciones_zonas.setImagen(imagen);
           adapterUbicaciones_zonas.setNombres_Ubicaciones(nombresZonas);
           listaZonas.setAdapter(adapterUbicaciones_zonas);
           listaZonas.setVisibility(View.INVISIBLE);


       }
    }

    public void llenarListaZonas(Tiendas tiendas)
    {
        progreesbarZonas.setVisibility(View.GONE);
     listasZonas=tiendas.getArregloTiendas();
        nombresZonas=new String[listasZonas.length()];
        posicioneszonas=new int [listasZonas.length()];
        imagen= new int[listasZonas.length()];
        System.out.print("mostrando zonas");

        for(int t=0;t<listasZonas.length();t++)
        {
            try {
                JSONObject obtenerZonas=listasZonas.getJSONObject(t);
                nombresZonas[t]=obtenerZonas.getString("va_descripcion_zn_posic").trim();
                posicioneszonas[t]=(obtenerZonas.getInt("id_csc_zo_pos"));
                imagen[t]=R.drawable.flecha_lista;


            } catch (JSONException e)
            {
                e.printStackTrace();
                tiendas.setMensajeError(e.getMessage());
               // alerta(getString(R.string.msjError));
                alertas.alertas(getString(R.string.msjError),this);
            }

        }

        adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombresZonas);
        adapterUbicaciones_zonas.setContext(this);
        adapterUbicaciones_zonas.setImagen(imagen);
        adapterUbicaciones_zonas.setNombres_Ubicaciones(nombresZonas);
        listaZonas.setAdapter(adapterUbicaciones_zonas);


        listaZonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int posicionZonas = posicioneszonas[position];
                Intent intent = new Intent(ListaZonasActivity.this, DetalleZonaActivity.class);
                intent.putExtra("posicion", posicionZonas);
                intent.putExtra("vienede", "catalogo");
                startActivity(intent);
            }
        });

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(progreesbarZonas.isShown()){
            CheckZonasAsync checkZonasAsync= new CheckZonasAsync(this);
            checkZonasAsync.cancel(true);
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