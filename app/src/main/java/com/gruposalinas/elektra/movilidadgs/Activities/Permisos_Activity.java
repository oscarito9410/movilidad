package com.gruposalinas.elektra.movilidadgs.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterPermisos;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.ConsultarExclusionesEmpleadosAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Permisos_Activity extends Activity {

    LinearLayout regresar;
    ImageView agregar;
    GridView permisoslista;
    DrawerLayout drawerLayout;
    ImageView delete;
    LinearLayout menu_horario;
    LinearLayout ubicacion_menu;
    LinearLayout incidencias_menu;
    LinearLayout cotacto_menu;
    LinearLayout menu_boton_panico;
    TextView texto_ubicacion_menu;
    ImageView imagen_ubicacion_menu;
    TextView texto_horario_menu;
    ImageView imagen_horario_menu;
    ImageView imagen_incidencias_menu;
    TextView texto_incidencias_menu;
    ImageView imagen_panico_menu;
    TextView texto_panico_menu;
    LinearLayout boton_panico_footer;
    ImageView Paginaweb;
    JSONArray da,d1;
    ArrayList <String> descripcion_exclusion;
    ArrayList <String>dt_fecha_hora_final;
    ArrayList<String> dt_fecha_hora_inicial;
    ArrayList <Integer>id_estatus;
    ArrayList tipo_exclusion;
    AdapterPermisos adapterPermisos;
    RelativeLayout progreso;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos_);
        init();
        drawerLayout.setFitsSystemWindows(true);
        action_boton();


    }

    public void init()
    {
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        agregar=(ImageView)findViewById(R.id.imagenagregar);
        permisoslista=(GridView)findViewById(R.id.listapermisos);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        delete=(ImageView)findViewById(R.id.dlete);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        ubicacion_menu=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        incidencias_menu=(LinearLayout)findViewById(R.id.menu_incidencias);
        cotacto_menu=(LinearLayout)findViewById(R.id.contacto);
        menu_boton_panico=(LinearLayout)findViewById(R.id.menu_panico);
        texto_ubicacion_menu=(TextView)findViewById(R.id.texto_menuUbicacion);
        imagen_ubicacion_menu=(ImageView)findViewById(R.id.tocar);
        texto_horario_menu=(TextView)findViewById(R.id.texto_menuHorario);
        imagen_horario_menu=(ImageView)findViewById(R.id.reloj);
        imagen_incidencias_menu=(ImageView)findViewById(R.id.icono_incidenciasmenu);
        texto_incidencias_menu=(TextView)findViewById(R.id.texto_menuIncidencias);
        texto_panico_menu=(TextView)findViewById(R.id.texto_menupanico);
        imagen_panico_menu=(ImageView)findViewById(R.id.icono_panico_menu);
        boton_panico_footer=(LinearLayout)findViewById(R.id.panico);
        Paginaweb=(ImageView)findViewById(R.id.paginaweb);
        progreso =(RelativeLayout)findViewById(R.id.progresoIncidencia);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Permisos permisos1= new Permisos();
        permisos1.setBusqueda("");
        permisos1.setEstatus("4");
        permisos1.setId_numero_empleado(prefs.getString(Constants.SP_ID, ""));
        permisos1.setTipo("Mias");
        ConsultarExclusionesEmpleadosAsync consultarExclusionesEmpleadosAsync= new ConsultarExclusionesEmpleadosAsync(this);
        consultarExclusionesEmpleadosAsync.execute(permisos1);
        progreso.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Permisos_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

    }

    private void action_boton()
    {
        cotacto_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Permisos_Activity.this, Contacto.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        ubicacion_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Permisos_Activity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Permisos_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Permisos_Activity.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        incidencias_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent= new Intent(Permisos_Activity.this,ListaIncidenciasActivity.class);
                intent.putExtra("plantilla",false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        menu_boton_panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();

            }
        });

        boton_panico_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        Paginaweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new paginaweb(getApplicationContext());
            }
        });


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Permisos_Activity.this,Agregar_Permisos_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

    public void respuesta(Permisos permisos)
    {
        progreso.setVisibility(View.GONE);
        String id;
        descripcion_exclusion= new ArrayList<>();
        dt_fecha_hora_final= new ArrayList<>();
        dt_fecha_hora_inicial= new ArrayList<>();
        id_estatus= new ArrayList<>();
        tipo_exclusion= new ArrayList();
        if(permisos.isSuccess())
        {
           da= permisos.getDatos();

            for(int i=0;i<da.length();i++)
            {
                try
                {
                    JSONObject obtener=da.getJSONObject(i);
                    id=obtener.getString("id_num_empleado");
                    d1=obtener.getJSONArray("exclusiones");

                    for (int y=0;y<d1.length();y++)
                    {
                        JSONObject object=d1.getJSONObject(y);

                        descripcion_exclusion.add(object.getString("descripcion_exclusion"));
                        dt_fecha_hora_inicial.add(object.getString("fecha_hora_inicial"));
                        dt_fecha_hora_final.add(object.getString("fecha_hora_final"));
                        id_estatus.add(object.getInt("id_estatus"));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
        else{
            Alertas alertas= new Alertas(this);

            alertas.alertas("error de conexión intente más tarde", this);
            descripcion_exclusion.add("");
            dt_fecha_hora_final.add("");
            dt_fecha_hora_inicial.add("");
            id_estatus.add(0);
        }

        adapterPermisos= new AdapterPermisos(this,R.layout.lista_zonas_ubicacion,id_estatus);
        adapterPermisos.setContext(this);
        adapterPermisos.setDescripcion_exclusion(descripcion_exclusion);
        adapterPermisos.setDt_fecha_hora_final(dt_fecha_hora_final);
        adapterPermisos.setDt_fecha_hora_inicial(dt_fecha_hora_inicial);
        adapterPermisos.setId_estatus(id_estatus);
        permisoslista.setAdapter(adapterPermisos);




    }

}
