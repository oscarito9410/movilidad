package com.gruposalinas.elektra.movilidadgs.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

public class Activity_Tiendas extends Activity
{
    LinearLayout textoBotonpendiente,Panico1;
    GridView listasTienda,listaubicaciones;
    String nombre;
    private AdapterUbicaciones_Zonas adapterUbicaciones_zonas;
    JSONArray array,Zonas;
    RelativeLayout progreesbar;
    Double []latitud=null;
    Double []longitud=null;
    String [] va_numero_pos=null;
    int [] d_csc_zo_pos=null;
    int contador=0,contadorZonas=0,tamano=0,tamanoZ=0;
    ImageView listaTiendasD,delete;
    LayoutInflater layoutInflater;
    View popView;
    PopupWindow popupWindow;
    LinearLayout regresar;
    ImageView paginaWEb;
    String Zonas1[]=null;
    String []NombreTiendas=null;
    int [] imagen=null;
    DrawerLayout dLayout;
    LinearLayout menu_horario,menu_ubicacion;
    LinearLayout menu_incidencias, menu_panico,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    LinearLayout contacto;
    Alertas alertas;
    ImageButton back;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__tiendas);
        init();
       alertas = new Alertas(this);


        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tiendas.this, Contacto.class);
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
                Intent intent = new Intent(Activity_Tiendas.this, TiendaDetalle.class);
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
                       Intent intent = new Intent(Activity_Tiendas.this, ListadoTiendas.class);
                       startActivity(intent);
                       popupWindow.dismiss();
                       Log.d("salirpop", "cerrar");

                   }
               });

                // te muestra las zonas

                zonas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Activity_Tiendas.this, ListaZonasActivity.class);
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


                        Intent intent= new Intent(Activity_Tiendas.this,Horarios_consulta.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                menu_ubicacion1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        Intent intent= new Intent(Activity_Tiendas.this,Activity_Tiendas.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                });

                menu_incidencias1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent= new Intent(Activity_Tiendas.this,ListaIncidenciasActivity.class);
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
                Intent intent = new Intent(Activity_Tiendas.this, MainActivity.class);
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

                Intent intent = new Intent(Activity_Tiendas.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Activity_Tiendas.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Activity_Tiendas.this,ListaIncidenciasActivity.class);
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

  // se inicializa todas las variables contenidas en el xml de la vista
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
        back=(ImageButton)findViewById(R.id.boton_regresar);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        nombre=prefs.getString(Constants.SP_NAME,"");
        Tiendas tiendas=new Tiendas();
        tiendas.setId_empleado(id_empleado);
        // inicia tarea asincrona
        TareaAsync(tiendas);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Activity_Tiendas.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

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
          int posicion = d_csc_zo_pos[position];
          String pos=String.valueOf(posicion);

          Intent intent = new Intent(Activity_Tiendas.this, DetalleZonaEliminar.class);
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

        latitud1 = latitud[position];
        longitud1 = longitud[position];
        va_numero_pos1 = va_numero_pos[position];


        Intent intent = new Intent(Activity_Tiendas.this, Mostrar_mapas.class);
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

        array=tiendas.getArregloTiendas();
        if(tiendas.isSuccess())
        {
          //  boolean validar=false;
            String id_estatus_posic="";


            for(int y=0;y<array.length();y++)
            {
                try{
                    JSONObject object=array.getJSONObject(y);

                    id_estatus_posic=object.getString("id_estatus_posic");

                    if(id_estatus_posic.equals("C"))
                    {
                        tamano++;


                    }
                    else if(id_estatus_posic.equals("P")){
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
                latitud=new Double[tamano];
                longitud=new Double[tamano];
                va_numero_pos=new String[tamano];

            }
            NombreTiendas=new String[tamano];
            imagen= new int [tamano];

            for(int i=0;i<array.length();i++)
            {
                try{
                    JSONObject object=array.getJSONObject(i);

                   // validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if(id_estatus_posic.equals("C"))
                    {
                        NombreTiendas[contador]=object.getString("va_nombre_pos").trim();
                        latitud[contador]=object.getDouble("dec_latitud");
                       longitud[contador]=object.getDouble("dec_longitud");
                       va_numero_pos[contador]=object.getString("va_numero_pos").trim();
                        imagen[contador]=R.drawable.ubicacion_lista;
                       contador++;

                    }
                    else if(id_estatus_posic.equals("P"))
                    {
                        // visualizar el boton en caso haya tiendas  falso
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


            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,NombreTiendas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(NombreTiendas);
            listaubicaciones.setAdapter(adapterUbicaciones_zonas);


        }
        else{
            //alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);

            NombreTiendas=new String[1];
            NombreTiendas[0]="";
            imagen=new int[1];
            imagen[0]=R.drawable.ubicacion_lista;
            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,NombreTiendas);
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

                  //  validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if (id_estatus_posic.equals("C"))
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



            Zonas1=new String[tamanoZ];
            d_csc_zo_pos=new int [tamanoZ];
            imagen= new int [tamanoZ];

            for(int i=0;i<JsonArrar.length();i++)
            {
                try {
                    JSONObject object=JsonArrar.getJSONObject(i);

                  //  validar=object.getBoolean("bit_valida");
                    id_estatus_posic=object.getString("id_estatus_posic");

                    if (id_estatus_posic.equals("C"))
                    {

                        Zonas1[contadorZonas] = object.getString("va_descripcion_zn_posic").trim();
                        int pos=object.getInt("id_csc_zo_pos");
                        d_csc_zo_pos[contadorZonas]=pos;
                        imagen[contadorZonas]=R.drawable.flecha_lista;
                        contadorZonas++;

                    }

                    else if(id_estatus_posic.equals("P")){
                        textoBotonpendiente.setVisibility(View.VISIBLE);
                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    //alertas("No se cargaron los datos.");
                    alertas.alertas("No se cargaron los datos.",this);
                }

            }
            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,Zonas1);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(Zonas1);
            listasTienda.setAdapter(adapterUbicaciones_zonas);



        }
        else{
           // alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);
            Zonas1= new String[1];
            imagen= new int[1];
            imagen[0]=R.drawable.flecha_lista;
            Zonas1[0]="";
            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,Zonas1);
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
            Intent intent=new Intent(Activity_Tiendas.this,MainActivity.class);
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
}
