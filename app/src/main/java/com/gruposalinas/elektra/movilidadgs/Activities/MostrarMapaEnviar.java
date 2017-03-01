package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.EnviarTiendaPropuestaAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Tiendas;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MostrarMapaEnviar extends Activity implements OnMapReadyCallback{
TextView nombreTienda;
    Button enviar;
    Double dato2,dato3;
    String va_numero_pos,dato1;
    String id_empleado;
    LinearLayout regresar;
    ImageView paginasweb;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,menu_ubicacion,menu_panico,menu_incidencias,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    LinearLayout contacto;
    Alertas alertas;
    ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mapa_enviar);
        init();

        alertas = new Alertas(this);

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarMapaEnviar.this, Contacto.class);
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

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapasverenviar);
        mapFragment.getMapAsync(this);
        final Tiendas tiendas=new Tiendas();
        tiendas.setPosicion(va_numero_pos);
        tiendas.setId_empleado(id_empleado);
        tiendas.setTipo("Mias");

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje(tiendas);
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        paginasweb.setOnClickListener(new View.OnClickListener() {
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


                Intent intent = new Intent(MostrarMapaEnviar.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MostrarMapaEnviar.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(MostrarMapaEnviar.this,ListaIncidenciasActivity.class);
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
        nombreTienda=(TextView)findViewById(R.id.nombreTiendasEnviar);
        panico=(LinearLayout)findViewById(R.id.panico);
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        enviar=(Button)findViewById(R.id.enviartienda);
        Bundle bundle=getIntent().getExtras();
        dato1= bundle.getString("nombreTienda");
        dato2= bundle.getDouble("latitud");
        dato3= bundle.getDouble("longitud");
        va_numero_pos=bundle.getString("va_numero_pos");
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        id_empleado=prefs.getString(Constants.SP_ID, "");
        regresar=(LinearLayout)findViewById(R.id.regresar19);
        paginasweb=(ImageView)findViewById(R.id.web4);
        TextView direccion=(TextView)findViewById(R.id.direccion1);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nombreTienda.setText(dato1);

        List<Address> addresses=null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try
        {
            addresses=geocoder.getFromLocation(dato2,dato3,1);

            if(addresses != null) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
                }

                direccion.setText(strAddress.toString());

            }

            else
                direccion.setText("No location found..!");


        }
        catch (IOException e)
        {

        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        LatLng sydney = new LatLng(dato2, dato3);

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        googleMap.addMarker(new MarkerOptions()
                .title(nombreTienda.getText().toString())
                .snippet("")
                .position(sydney));


    }

    public void mensaje(final Tiendas tiendas)
    {

        final Dialog alert = new Dialog(MostrarMapaEnviar.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar=(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        titulodialo.setText("Solicitar agregar Ubicación");

        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tareaEnviar(tiendas);

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

    public void tareaEnviar(Tiendas tiendas)
    {
        EnviarTiendaPropuestaAsync enviarTiendaPropuestaAsync=new EnviarTiendaPropuestaAsync(this);
        enviarTiendaPropuestaAsync.execute(tiendas);

    }

    public  void ChecarEnviado(Tiendas tiendas)
    {
        String mensaje=tiendas.getMensajeError();

        if (tiendas.isSuccess())
        {
          finaliz();

        }
        else{
           // alertas(getString(R.string.msjError));
            alertas.alertas(getString(R.string.msjError),this);

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

    public void finaliz(){
        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
        final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);

        alert.setContentView(dialogo);
        alert.setCancelable(false);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(MostrarMapaEnviar.this,Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        alert.show();

    }
}
