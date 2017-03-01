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

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterUbicaciones_Zonas;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.EnviarTiendaPropuestaAsync;
import com.gruposalinas.elektra.movilidadgs.async.listadoTiendasCheckAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class listado_ubicaciones_plantilla extends Activity {

    GridView listaTiendas;
    ArrayAdapter arrayAdapter=null;
    JSONArray jsonArray=null;
    RelativeLayout progressbar;
    String numeroPosicion[]=null;
    Double latitud[]=null;
    Double longitud[]=null;
    String id_empleado;
    EditText buscar;
    ImageView iconoBuscar;
    LinearLayout regresar;
    String Nombres[]=null;
    AdapterUbicaciones_Zonas adapterUbicaciones_zonas;
    int imagen[]=null;
    DrawerLayout dLayout;
    LinearLayout menu_horario,menu_ubicacion,menu_panico,menu_incidencias,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    LinearLayout contacto;
    Alertas alertas;
    String numeroempleadoActual;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ubicaciones_plantilla);

        init();
        alertas = new Alertas(this);

        buscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    buscarTienda();
                    // Ocultar teclado virtual
                    InputMethodManager imm =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    procesado = true;
                }
                return procesado;

            }
        });

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listado_ubicaciones_plantilla.this, Contacto.class);
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
        iconoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adapterUbicaciones_zonas.clear();
                buscarTienda();

            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listado_ubicaciones_plantilla.this, Plantilla_ZonasYubicaciones.class);
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



                Intent intent = new Intent(listado_ubicaciones_plantilla.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(listado_ubicaciones_plantilla.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(listado_ubicaciones_plantilla.this, ListaIncidenciasActivity.class);
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
        iconoBuscar=(ImageView)findViewById(R.id.buscar1);
        buscar =(EditText)findViewById(R.id.buscar);
        listaTiendas=(GridView)findViewById(R.id.listadotiendaslista);
        arrayAdapter= new ArrayAdapter(this,R.layout.lista_zonas_ubicacion);
        progressbar=(RelativeLayout)findViewById(R.id.progressbar_tiendasLista);
        progressbar.setVisibility(View.GONE);
        regresar=(LinearLayout)findViewById(R.id.regresar4);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);

        Bundle bundle= getIntent().getExtras();

        numeroempleadoActual=bundle.getString("numeroempleado");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(listado_ubicaciones_plantilla.this, Plantilla_ZonasYubicaciones.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });


    }
    public void buscarTienda(){
        Tiendas tiendas=new Tiendas();
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_empleado=prefs.getString(Constants.SP_ID, "");
        tiendas.setId_empleado(id_empleado);
        progressbar.setVisibility(View.VISIBLE);
        String buscar1=buscar.getText().toString();
        tiendas.setBuscar(buscar1);
        listadoTiendasCheckAsync listadoTiendasCheckAsync=new listadoTiendasCheckAsync(this);
        listadoTiendasCheckAsync.execute(tiendas);
    }

    public void mostrarDatosListado(final Tiendas tiendas)
    {
        if(tiendas.getRespuesta()==200)
        {
            progressbar.setVisibility(View.GONE);
            if(tiendas.isSuccess())
            {

                jsonArray=tiendas.getArregloTiendas();
                Nombres=new String[jsonArray.length()];
                latitud=new Double[jsonArray.length()];
                longitud= new Double[jsonArray.length()];
                numeroPosicion=new String[jsonArray.length()];
                imagen= new int[jsonArray.length()];


                for(int i=0;i<jsonArray.length();i++)
                {
                    try {

                        JSONObject object=jsonArray.getJSONObject(i);
                        Nombres[i]=object.getString("va_nombre_pos").trim();
                        latitud[i]=object.getDouble("dec_latitud");
                        longitud[i]=object.getDouble("dec_longitud");
                        numeroPosicion[i]=object.getString("va_numero_pos").trim();
                        imagen[i]=R.drawable.ubicacion_lista;
                        // arrayAdapter.add(Nombres[i]);
                        // listaTiendas.setAdapter(arrayAdapter);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        // mensajeError(getString(R.string.msjError));
                        alertas.alertas(getString(R.string.msjError),this);


                    }

                }
                adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,Nombres);
                adapterUbicaciones_zonas.setContext(this);
                adapterUbicaciones_zonas.setImagen(imagen);
                adapterUbicaciones_zonas.setNombres_Ubicaciones(Nombres);
                listaTiendas.setAdapter(adapterUbicaciones_zonas);
                listaTiendas.setVisibility(View.VISIBLE);

            }
            else
            {
                if(tiendas.getArregloTiendas()==null)
                {
                    // mensajeError("No se encontraron ubicaciones validas.");
                    alertas.alertas("No se encontraron ubicaciones validas.",this);
                }else
                {
                    //mensajeError(getString(R.string.msjError));
                    alertas.alertas(getString(R.string.msjError),this);
                }

                Nombres=new String[1];
                Nombres[0]="";
                imagen= new int[1];
                imagen[0]=R.drawable.flecha_lista;
                adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,Nombres);
                adapterUbicaciones_zonas.setContext(this);
                adapterUbicaciones_zonas.setImagen(imagen);
                adapterUbicaciones_zonas.setNombres_Ubicaciones(Nombres);
                listaTiendas.setAdapter(adapterUbicaciones_zonas);
                listaTiendas.setVisibility(View.INVISIBLE);


            }

            listaTiendas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // metodo para enviar datos tienda//
                    //   Toast.makeText(getApplicationContext(),"probar",Toast.LENGTH_LONG).show();
                 /*   String datosPonernombretienda = parent.getItemAtPosition(position).toString();
                    String  p=numeroPosicion[position];
                    Double latitud1=latitud[position];
                    Double longitud1=longitud[position];
                    Intent intent =new Intent(ListadoTiendas.this,MostrarMapaEnviar.class);
                    intent.putExtra("va_numero_pos",p);
                    intent.putExtra("longitud",longitud1);
                    intent.putExtra("latitud",latitud1);
                    intent.putExtra("nombreTienda",datosPonernombretienda);
                    startActivity(intent);
                    */
                    String  p=numeroPosicion[position];

                    tarea(p);

                }
            });

        }
        else{
            //mensajeError(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError), this);
            progressbar.setVisibility(View.GONE);
        }


    }



    @Override
    public void onBackPressed() {

        if(progressbar.isShown()){
            listadoTiendasCheckAsync listadoTiendasCheckAsync=new listadoTiendasCheckAsync(this);
            listadoTiendasCheckAsync.cancel(true);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
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

    private   void tarea(String pos)
    {
        Tiendas tiendas= new Tiendas();

        tiendas.setId_empleado(numeroempleadoActual);
        tiendas.setPosicion(pos);
        tiendas.setTipo("Línea directa");
        EnviarTiendaPropuestaAsync enviarTiendaPropuestaAsync= new EnviarTiendaPropuestaAsync(this);
        enviarTiendaPropuestaAsync.execute(tiendas);
        progressbar.setVisibility(View.VISIBLE);

    }

    public void finalizr(Tiendas tiendas)
    {
        progressbar.setVisibility(View.GONE);
        if (tiendas.isSuccess())
        {

            final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            @SuppressLint("InflateParams") final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
            final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
            final TextView textView=(TextView)dialogo.findViewById(R.id.textoeditar);
            final TextView textView1=(TextView)dialogo.findViewById(R.id.temporal);
            textView.setText("Se ha Asignado la ubicación");
            textView1.setText("Correctamente al empleado con numero"+"\n"+numeroempleadoActual);


            alert.setContentView(dialogo);
            alert.setCancelable(false);
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent= new Intent(listado_ubicaciones_plantilla.this,Plantilla_ZonasYubicaciones.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });

            alert.show();
        }
        else {
            alertas.alertas("Error de conexión intente más tarde", getApplicationContext());
        }

    }
}
