package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterUbicaciones_Zonas;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.DetalleZonaAsync;
import com.gruposalinas.elektra.movilidadgs.async.DetallezonaEliminarAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalleZonaEliminar extends Activity {
    GridView lista;
    Button elminar;
    ArrayAdapter arrayAdapter;
    JSONArray jsonArray;
    RelativeLayout progrees;
    String nombres[]=null;
    String posiciondetalle;
    double latitud []=null;
    double longitud[]=null;
    String [] posi=null;
    LinearLayout regresar;
    ImageView paginasweb,delete;
    private AdapterUbicaciones_Zonas adapterUbicaciones_zonas;
    int imagen[]= null;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_panico,menu_incidencias,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    LinearLayout contacto;
    Alertas alertas;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_zona_eliminar);
        init();
        alertas = new Alertas(this);
        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleZonaEliminar.this, Contacto.class);
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
        elminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // metodo de eliminar//
                checar();

            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetalleZonaEliminar.this,Activity_Tiendas.class);
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


                Intent intent = new Intent(DetalleZonaEliminar.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(DetalleZonaEliminar.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(DetalleZonaEliminar.this, ListaIncidenciasActivity.class);
                intent.putExtra("plantilla", false);
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
        texto_menuUbicacion=(TextView)findViewById(R.id.texto_menuUbicacion);
        texto_menuHorario=(TextView)findViewById(R.id.texto_menuHorario);
        inciendenciasimas=(ImageView)findViewById(R.id.icono_incidenciasmenu);
        tocar=(ImageView)findViewById(R.id.tocar);
        texto_panico=(TextView)findViewById(R.id.texto_menupanico);
        panicoimas=(ImageView)findViewById(R.id.icono_panico_menu);
        reloj=(ImageView)findViewById(R.id.reloj);


        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
        menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
        menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
        progrees=(RelativeLayout)findViewById(R.id.progressbar_tiendasZonaDetalleeliminar);
        paginasweb=(ImageView)findViewById(R.id.paginaweb3);
        panico=(LinearLayout)findViewById(R.id.panico);
        lista=(GridView)findViewById(R.id.listatiendasDetalleZonaeliminar);
        elminar=(Button)findViewById(R.id.eliminarZona);
        arrayAdapter= new ArrayAdapter(this,R.layout.lista_zonas_ubicacion);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        regresar=(LinearLayout)findViewById(R.id.regresar10);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(DetalleZonaEliminar.this,Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        Bundle bundle=getIntent().getExtras();
         posiciondetalle=bundle.getString("posicion");
        Tiendas tiendas= new Tiendas();
        tiendas.setId_empleado(id_empleado);
        tiendas.setPosicion(posiciondetalle);
        progrees.setVisibility(View.VISIBLE);
        DetalleZonaAsync detalleZonaAsync= new DetalleZonaAsync(this);
        detalleZonaAsync.execute(tiendas);

    }

    public void tarea(Tiendas tiendas){
        progrees.setVisibility(View.VISIBLE);
        DetallezonaEliminarAsync detallezonaEliminarAsync= new DetallezonaEliminarAsync(this);
        detallezonaEliminarAsync.execute(tiendas);
    }

    public void detallever(Tiendas tiendas)
    {
        progrees.setVisibility(View.GONE);

        if(tiendas.isSuccess())
        {
            jsonArray=tiendas.getArregloTiendas();
            nombres= new String[jsonArray.length()];
            latitud=new double[jsonArray.length()];
            longitud= new double[jsonArray.length()];
            posi= new String [jsonArray.length()];
            imagen= new int[jsonArray.length()];


            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject= null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    nombres[i]=jsonObject.getString("va_nombre_pos").trim();
                    latitud[i]=jsonObject.getDouble("dec_latitud");
                    longitud[i]=jsonObject.getDouble("dec_longitud");
                    posi[i]=jsonObject.getString("va_numero_pos").trim();
                    imagen[i]=R.drawable.ubicacion_lista;

                } catch (JSONException e)
                {
                    e.printStackTrace();
                    //alertas(getString(R.string.msjError));
                    alertas.alertas(getString(R.string.msjError),this);
                }
            }

            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombres);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(nombres);
            lista.setAdapter(adapterUbicaciones_zonas);



        }
        else
        {
            nombres= new String[1];
            nombres[0]="";
            imagen= new int[1];
            imagen[0]=R.drawable.ubicacion_lista;
            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombres);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(nombres);
            lista.setAdapter(adapterUbicaciones_zonas);
            lista.setVisibility(View.INVISIBLE);

            // error//
           // alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                double la = latitud[position];
                double log = longitud[position];
                String nombre = nombres[position];
                boolean tienda = false;
                String posis = posi[position];
                Intent intent = new Intent(DetalleZonaEliminar.this, Mostrar_mapas.class);
                intent.putExtra("longitud", log);
                intent.putExtra("latitud", la);
                intent.putExtra("tiendas", tienda);
                intent.putExtra("nombreTienda", nombre);
                intent.putExtra("va_numero_pos", posis);
                startActivity(intent);
            }
        });
    }


    public void confirmacion(Tiendas tiendas)
    {
        progrees.setVisibility(View.GONE);

        if(tiendas.isSuccess())
        {
           alertas.Exito(this,null);
        }
        else{
            //alertas("No se pudieron enviar los datos intente más tarde.");
            alertas.alertas("No se pudieron enviar los datos intente más tarde.",this);
        }

    }

    public void checar()
    {
        progrees.setVisibility(View.GONE);
        final Dialog alert = new Dialog(DetalleZonaEliminar.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar=(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        titulodialo.setText("Solicitar eliminar zona");

        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tiendas tiendas = new Tiendas();
                SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String id_empleado = prefs.getString(Constants.SP_ID, "");
                tiendas.setId_empleado(id_empleado);
                tiendas.setPosicion(posiciondetalle);
                tarea(tiendas);
                alert.dismiss();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if(progrees.isShown()){
            DetalleZonaAsync detalleZonaAsync= new DetalleZonaAsync(this);
            detalleZonaAsync.cancel(true);

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
