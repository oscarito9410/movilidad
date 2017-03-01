package com.gruposalinas.elektra.movilidadgs.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterUbicaciones_Zonas;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.CheckTiendasAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class tienda_detalle_plantilla extends Activity {

    GridView listaTiendas,listaUbicaciones;
    String tiendassss,Zonas;
    JSONArray data,zona;// son los que pintan listas
    double latitud[]=null;
    double longitud[]= null;
    int contadorzonas=0;
    int contadorubicaciones=0;
    String va_numero_pos[]=null;
    LinearLayout regresar;
    int [] d_csc_zo_pos= null;
    ImageView paginasweb;
    String nombreZonas[]=null;
    int tamanoZ=0,tamanoU=0;
    private AdapterUbicaciones_Zonas adapterUbicaciones_zonas;
    String nombretiendas[]=null;;
    int imagen[]=null;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_panico,menu_incidencias,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    LinearLayout contacto;
    RelativeLayout progressbar;
    Alertas alertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_detalle_plantilla);

        init();
        alertas = new Alertas(this);


        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tienda_detalle_plantilla.this, Contacto.class);
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
            public void onClick(View v)
            {
                Intent intent=new Intent(tienda_detalle_plantilla.this,Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        paginasweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginaweb paginaweb= new paginaweb(getApplicationContext());
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


                Intent intent = new Intent(tienda_detalle_plantilla.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(tienda_detalle_plantilla.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(tienda_detalle_plantilla.this,ListaIncidenciasActivity.class);
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
        panico=(LinearLayout)findViewById(R.id.panico);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        listaTiendas=(GridView)findViewById(R.id.listatiendasDetalleZonas);
        listaUbicaciones=(GridView)findViewById(R.id.listatiendasUbicacionesdetalle);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        regresar = (LinearLayout)findViewById(R.id.regresar5);
        paginasweb=(ImageView)findViewById(R.id.we5);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        progressbar=(RelativeLayout)findViewById(R.id.progressbar_tiendas);

        Tiendas tiendas=new Tiendas();
        tiendas.setId_empleado(id_empleado);
        // inicia tarea asincrona
        TareaAsync(tiendas);


    }

    public void TareaAsync(Tiendas tiendas)
    {
        progressbar.setVisibility(View.VISIBLE);
        CheckTiendasAsync checkTiendasAsync=new CheckTiendasAsync(this);
        checkTiendasAsync.execute(tiendas);


    }

    public void pintar(Tiendas tiendas)
    {
        progressbar.setVisibility(View.GONE);
        if(tiendas.isSuccess())
        {
            mostrarTienda(tiendas.getArregloTiendas());
            datosZonas(tiendas.getZona());
        }
        else{
            //  alertas("no se cargaron las ubicaciones y zonas");
            alertas.alertas("no se cargaron las ubicaciones y zonas",this);
        }

    }

    public  void mostrarTienda(JSONArray array)
    {
        JSONArray data1=null;
        boolean validar=false;
        data1=array;
        if(data1!=null)
        {


            for(int t=0;t<data1.length();t++)
            {
                try
                {

                    JSONObject tendas=data1.getJSONObject(t);
                    validar=tendas.getBoolean("bit_valida");
                    if(!validar)
                    {
                        tamanoU++;
                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    // alertas(e.toString());
                    alertas.alertas(e.toString(),this);
                }

            }
            nombretiendas=new String[tamanoU];
            latitud =new double[tamanoU];
            longitud=new double[tamanoU];
            va_numero_pos=new String[tamanoU];
            imagen= new  int[tamanoU];


            for(int t=0;t<data1.length();t++)
            {
                try
                {

                    JSONObject tendas=data1.getJSONObject(t);

                    validar=tendas.getBoolean("bit_valida");
                    if(validar==false)
                    {
                        nombretiendas[contadorubicaciones]=tendas.getString("va_nombre_pos").trim();
                        latitud[contadorubicaciones]=tendas.getDouble("dec_latitud");
                        longitud[contadorubicaciones]=tendas.getDouble("dec_longitud");
                        va_numero_pos[contadorubicaciones]=tendas.getString("va_numero_pos").trim();
                        imagen[contadorubicaciones]=R.drawable.ubicacion_lista;
                        contadorubicaciones++;
                    }


                    listaUbicaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String nombre= listaUbicaciones.getItemAtPosition(position).toString();
                            Intent intent=new Intent(tienda_detalle_plantilla.this,MapasZonasUbicaciones.class);
                            intent.putExtra("latitud",latitud[position]);
                            intent.putExtra("longitud",longitud[position]);
                            intent.putExtra("nombre",nombre);
                            startActivity(intent);

                        }

                    });

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    // alertas(e.toString());
                    alertas.alertas(e.toString(),this);
                }

            }
            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombretiendas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(nombretiendas);
            listaUbicaciones.setAdapter(adapterUbicaciones_zonas);
        }

    }

    public void datosZonas(JSONArray datos)
    {

        JSONArray datos1=datos;
        boolean validar=false;

        if(datos!=null)
        {

            for(int i=0;i<datos1.length();i++)
            {
                try {
                    JSONObject object=datos1.getJSONObject(i);

                    validar=object.getBoolean("bit_valida");

                    if (!validar)
                    {

                        tamanoZ++;

                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    // alertas(e.toString());
                    alertas.alertas(e.toString(),this);
                }

            }
            nombreZonas=new String[tamanoZ];
            d_csc_zo_pos= new int[tamanoZ];
            imagen= new int[tamanoZ];


            for(int i=0;i<datos1.length();i++)
            {
                try
                {
                    JSONObject zonas1=datos1.getJSONObject(i);
                    validar=zonas1.getBoolean("bit_valida");
                    int pos=zonas1.getInt("id_csc_zo_pos");

                    if(validar==false)
                    {
                        nombreZonas[contadorzonas]=zonas1.getString("va_descripcion_zn_posic").trim();

                        d_csc_zo_pos[contadorzonas]=pos;
                        imagen[contadorzonas]=R.drawable.flecha_lista;
                        contadorzonas++;


                    }
                    else{

                    }

                    listaTiendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int posi=d_csc_zo_pos[position];
                            Intent intent=new Intent(tienda_detalle_plantilla.this,DetalleZonaActivity.class);
                            intent.putExtra("posicion",posi);
                            intent.putExtra("vienede","TiendaDetalle");
                            startActivity(intent);
                        }
                    });


                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }

            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombreZonas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(nombreZonas);
            listaTiendas.setAdapter(adapterUbicaciones_zonas);



        }

    }



    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(progressbar.isShown()){
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

}
