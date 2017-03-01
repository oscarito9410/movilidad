package com.gruposalinas.elektra.movilidadgs.Activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
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
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapasZonasUbicaciones extends Activity implements OnMapReadyCallback{

    TextView as,direccion;
    String nom="";
    double latitud,longitud;
    LinearLayout regresar;
    ImageView paginasweb;
    DrawerLayout dLayout;
    LinearLayout panico,menu_horario,mrnu_ubicacion,menu_panico,menu_incidencias,pendientes_zonas_horarios_menu;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    LinearLayout contacto;
    ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas_zonas_ubicaciones);
        init();
        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapasZonasUbicaciones.this, Contacto.class);
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
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapasvermostrar);
        mapFragment.getMapAsync(this);
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

        mrnu_ubicacion.setFitsSystemWindows(true);
        menu_horario.setFitsSystemWindows(true);
        menu_panico.setFitsSystemWindows(true);
        menu_incidencias.setFitsSystemWindows(true);

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MapasZonasUbicaciones.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        mrnu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MapasZonasUbicaciones.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent= new Intent(MapasZonasUbicaciones.this,ListaIncidenciasActivity.class);
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
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        mrnu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        as=(TextView)findViewById(R.id.provar);
        Bundle bundle=getIntent().getExtras();
        latitud=bundle.getDouble("latitud");
        longitud=bundle.getDouble("longitud");
        regresar=(LinearLayout)findViewById(R.id.regresar17);
        paginasweb=(ImageView)findViewById(R.id.paginaweb7);
        direccion=(TextView)findViewById(R.id.direccion);
        panico=(LinearLayout)findViewById(R.id.panico);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        back=(ImageButton)findViewById(R.id.boton_regresar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nom=bundle.getString("nombre");
        as.setText(nom);
        List<Address> addresses=null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try
        {
         addresses=geocoder.getFromLocation(latitud,longitud,1);

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
        LatLng sydney = new LatLng(latitud, longitud);

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

        googleMap.addMarker(new MarkerOptions()
                .title(as.getText().toString())
                .snippet("")
                .position(sydney));
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
}
