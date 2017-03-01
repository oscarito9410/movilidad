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
import com.gruposalinas.elektra.movilidadgs.async.AsignarZonaAsync;
import com.gruposalinas.elektra.movilidadgs.async.DetalleZonaAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalleZonaActivity extends Activity {
    GridView listaZonaDetalle;
    RelativeLayout progress;
    ArrayAdapter arrayAdapter=null;
    JSONArray array;
    Button enviarZona;
    String[] nombrestiendasZonas=null;
    Tiendas tiendas1=null;
    double latitud[]=null;
    double longitud[]=null;
    int contador=0;
    LinearLayout regresar;
    ImageView paginasweb2,delete;
    private AdapterUbicaciones_Zonas adapterUbicaciones_zonas;
    int imagen []= null;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    LinearLayout contacto;
    Alertas alertas;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_zona);
        init();

        alertas = new Alertas(this);

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleZonaActivity.this, Contacto.class);
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
        enviarZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con();

            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        paginasweb2.setOnClickListener(new View.OnClickListener() {
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
        menu_horario.setFitsSystemWindows(true);
        menu_ubicacion.setFitsSystemWindows(true);
        menu_panico.setFitsSystemWindows(true);
        menu_incidencias.setFitsSystemWindows(true);

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(DetalleZonaActivity.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(DetalleZonaActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(DetalleZonaActivity.this,ListaIncidenciasActivity.class);
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

    public  void tareaAsingnarZona(Tiendas tiendas)
    {
        progress.setVisibility(View.VISIBLE);
        AsignarZonaAsync asignarZonaAsync=new AsignarZonaAsync(this);
        asignarZonaAsync.execute(tiendas);

    }


    public void  init()
    {
        texto_incidencias=(TextView)findViewById(R.id.texto_menuIncidencias);
        texto_panico=(TextView)findViewById(R.id.texto_menupanico);
        panicoimas=(ImageView)findViewById(R.id.icono_panico_menu);
        inciendenciasimas=(ImageView)findViewById(R.id.icono_incidenciasmenu);
        reloj=(ImageView)findViewById(R.id.reloj);
        tocar=(ImageView)findViewById(R.id.tocar);
        texto_menuUbicacion=(TextView)findViewById(R.id.texto_menuUbicacion);
        texto_menuHorario=(TextView)findViewById(R.id.texto_menuHorario);
        pendientes_zonas_horarios_menu=(LinearLayout)findViewById(R.id.pendientes_autorizar);
        menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
        menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
        enviarZona=(Button)findViewById(R.id.enviarZona);
        listaZonaDetalle=(GridView)findViewById(R.id.listatiendasDetalleZona);
        progress=(RelativeLayout)findViewById(R.id.progressbar_tiendasZonaDetalle);
        arrayAdapter= new ArrayAdapter(this,R.layout.lista_zonas_ubicacion);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        regresar=(LinearLayout)findViewById(R.id.regresar8);
        paginasweb2=(ImageView)findViewById(R.id.paginaweb2);
        panico=(LinearLayout)findViewById(R.id.panico);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);

        Bundle bundle=getIntent().getExtras();
        int posiciondetalle=bundle.getInt("posicion");
        String verdonde=bundle.getString("vienede");
        // se verifica de donde viende para saber si ocultamos el boton enviar
        if(verdonde.equals("TiendaDetalle"))
        {
            enviarZona.setVisibility(View.INVISIBLE);

        }
        else{
            enviarZona.setVisibility(View.VISIBLE);
        }
        Tiendas tiendas=new Tiendas();
        tiendas.setId_empleado(id_empleado);
        tiendas.setPosicion(String.valueOf(posiciondetalle));
        progress.setVisibility(View.VISIBLE);
        tiendas1=new Tiendas();
        tiendas1.setPosicion(String.valueOf(posiciondetalle));
        tiendas1.setId_empleado(id_empleado);
        tiendas1.setTipo("Mias");
        DetalleZonaAsync detalleZonaAsync= new DetalleZonaAsync(this);
        detalleZonaAsync.execute(tiendas);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public void datosZonasobtenidos(Tiendas tiendas)
    {
        progress.setVisibility(View.GONE);
        if (tiendas.isSuccess())
        {
            array=tiendas.getArregloTiendas();
            nombrestiendasZonas=new String[array.length()];
            latitud=new double[array.length()];
            longitud=new double[array.length()];
            imagen= new int[array.length()];

            for (int i=0;i<array.length();i++)
            {
                try {

                    JSONObject jsonObject=array.getJSONObject(i);
                    nombrestiendasZonas[i]=jsonObject.getString("va_nombre_pos").trim();
                    latitud[contador]=jsonObject.getDouble("dec_latitud");
                    longitud[contador]=jsonObject.getDouble("dec_longitud");
                    imagen[contador]=R.drawable.ubicacion_lista;
                    contador++;
                   // arrayAdapter.add(nombrestiendasZonas[i]);
                   // listaZonaDetalle.setAdapter(arrayAdapter);


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    //alertas(getString(R.string.msjError));
                    alertas.alertas(getString(R.string.msjError),this);
                }

            }

            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombrestiendasZonas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(nombrestiendasZonas);
            listaZonaDetalle.setAdapter(adapterUbicaciones_zonas);


        }
        else{
            nombrestiendasZonas=new String[1];
            nombrestiendasZonas[0]="";
            imagen= new int[1];
            imagen[0]=R.drawable.ubicacion_lista;
            adapterUbicaciones_zonas=new AdapterUbicaciones_Zonas(this,R.layout.lista_zonas_ubicacion,nombrestiendasZonas);
            adapterUbicaciones_zonas.setContext(this);
            adapterUbicaciones_zonas.setImagen(imagen);
            adapterUbicaciones_zonas.setNombres_Ubicaciones(nombrestiendasZonas);
            listaZonaDetalle.setAdapter(adapterUbicaciones_zonas);
            listaZonaDetalle.setVisibility(View.INVISIBLE);

            //alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);
            progress.setVisibility(View.GONE);
        }
        listaZonaDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ir  mapa//
                Intent intent = new Intent(DetalleZonaActivity.this, MapasZonasUbicaciones.class);
                intent.putExtra("latitud", latitud[position]);
                intent.putExtra("longitud", longitud[position]);
                intent.putExtra("nombre", nombrestiendasZonas[position]);
                startActivity(intent);
            }
        });


    }




    public void con()
    {
        progress.setVisibility(View.GONE);
        final Dialog alert = new Dialog(DetalleZonaActivity.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar=(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        titulodialo.setText("Solicitar agregar zona");

        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tareaAsingnarZona(tiendas1);
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
    public void mensaje(Tiendas tiendas)
    {


        progress.setVisibility(View.GONE);
        if(tiendas.isSuccess())
        {
          finalizar();
        }
        else{
            //alertas("No se pudieron enviar los datos intente más tarde");
            alertas.alertas("No se pudieron enviar los datos intente más tarde",this);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if(progress.isShown()){
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
    public void finalizar(){
        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
        final LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
        final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);

        alert.setContentView(dialogo);
        alert.setCancelable(false);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(DetalleZonaActivity.this,Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        alert.show();

    }
}
