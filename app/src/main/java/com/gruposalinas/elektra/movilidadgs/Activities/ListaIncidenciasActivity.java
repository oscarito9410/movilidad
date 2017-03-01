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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterIncidencia;
import com.gruposalinas.elektra.movilidadgs.adapters.Adapterplntillas;
import com.gruposalinas.elektra.movilidadgs.async.IncidenciasAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


public class ListaIncidenciasActivity extends Activity {
    GridView listaIncidencias,plantillas;
    JSONArray array,array1;
    String incidenciaNombre[],nombre4="";
    RelativeLayout progreso;
    AdapterIncidencia adapterIncidencia;
    String fechainicio[];
    boolean justifica[];
   String  EstatusJustificacion []=null;
    int CSC[]=null;
    String [] minumeroEmpleado;
    DrawerLayout dLayout;
    LinearLayout regresar,panico, menu_horario,menu_ubicacion,menu_panico,menu_incidencias,pendientes_zonas_horarios_menu;
    ImageView cuentanos;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    String Nombres_empleados[];
    Boolean va;
    LinearLayout contacto,cabeza;
    TextView titulo;
    LinearLayout spiner,conte;
    TextView selecion_empledo;
    ArrayList <String> nombreplantilla;
    ArrayList <String> fechainicioplantilla;
    ArrayList <Boolean> justificarplantilla;
    ArrayList <Integer> CSCplantilla;
    ArrayList <String> Estatusplantilla;
    ArrayList <String> numeroEmpleado;
    ArrayList <String> numeroDeEmpleados;
    LinearLayout linea;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_incidencias);
        Bundle bundle=getIntent().getExtras();
        va=bundle.getBoolean("plantilla");

        init();

        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaIncidenciasActivity.this, Contacto.class);
                startActivity(intent);
            }
        });

        if(va)
        {
            plantillas.setVisibility(View.INVISIBLE);
            listaIncidencias.setVisibility(View.GONE);
            titulo.setText(R.string.tituloincidencia);
            cabeza.setVisibility(View.INVISIBLE);



        }

        else
        {
            listaIncidencias.setVisibility(View.VISIBLE);
            plantillas.setVisibility(View.GONE);
            spiner.setVisibility(View.INVISIBLE);
            linea.setVisibility(View.INVISIBLE);


        }

        delete=(ImageView)findViewById(R.id.dlete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.closeDrawer(GravityCompat.START);
            }
        });
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dLayout.setFitsSystemWindows(true);


        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        cuentanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new paginaweb(getApplicationContext());
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


                Intent intent = new Intent(ListaIncidenciasActivity.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    Intent intent = new Intent(ListaIncidenciasActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent= new Intent(ListaIncidenciasActivity.this,ListaIncidenciasActivity.class);
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

    private void init()
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
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
        menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
        listaIncidencias=(GridView)findViewById(R.id.listadoIncidencias);
        progreso=(RelativeLayout)findViewById(R.id.progresoIncidencia);
        plantillas=(GridView)findViewById(R.id.listadoIncidenciasplantilla);
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        cuentanos=(ImageView)findViewById(R.id.paginaweb);
        panico=(LinearLayout)findViewById(R.id.panico);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        titulo=(TextView)findViewById(R.id.textView4);
        cabeza=(LinearLayout)findViewById(R.id.cabezaIncidencia);
        spiner=(LinearLayout)findViewById(R.id.linearLayout);
        conte=(LinearLayout)findViewById(R.id.linearLayout4);
        selecion_empledo=(TextView)findViewById(R.id.seleccionEmpleado);
        linea=(LinearLayout)findViewById(R.id.linea);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });

        cargar();

    }

    public  void cargar(){

        Incidencias incidencias= new Incidencias();
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        incidencias.setNumero_empleado(prefs.getString(Constants.SP_ID, ""));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        System.out.println("Fecha actual: " + currentDateandTime);
        Calendar c = Calendar.getInstance();
        // saca la fecha anterior
        c.add(Calendar.MONTH, -1);
        Date date = c.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      //  String fecha_anterior = sdf1.format(date);
//////////////////////////////////////////////////////////
        incidencias.setFecha_inicio("2016-01-01");// se debe colocar la fecha anterior para poduccion
        incidencias.setFecha_fin(currentDateandTime);
        progreso.setVisibility(View.VISIBLE);
        IncidenciasAsync incidenciasAsync= new IncidenciasAsync(this);
        incidenciasAsync.execute(incidencias);
    }


    public void mostrarIncidencias(final Incidencias incidencias)
    {
        progreso.setVisibility(View.GONE);
        if(incidencias.isSuccess()) {

            if (!va)
            {
                array = incidencias.getLista();
                incidenciaNombre = new String[array.length()];
                fechainicio = new String[array.length()];
                justifica = new boolean[array.length()];
                CSC = new int[array.length()];
                EstatusJustificacion = new String[array.length()];
                minumeroEmpleado = new String[array.length()];

                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject incidenciasdatos = array.getJSONObject(i);
                        incidenciaNombre[i] = incidenciasdatos.getString("Incidencia").trim();
                        fechainicio[i] = incidenciasdatos.getString("FechaOcurrencia").trim();
                        justifica[i] = incidenciasdatos.getBoolean("Justificada");
                        CSC[i] = incidenciasdatos.getInt("CSC");// se usa solo para cuando se vaya a justificar//
                        EstatusJustificacion[i] = incidenciasdatos.getString("EstatusJustificacion");
                        minumeroEmpleado[i] = incidenciasdatos.getString("Empleado");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        alertas(getString(R.string.msjError));
                    }

                }

                    adapterIncidencia = new AdapterIncidencia(this, R.layout.lista_zonas_ubicacion, incidenciaNombre);
                    adapterIncidencia.setIncidencia(incidenciaNombre);
                    adapterIncidencia.setFecha(fechainicio);
                    adapterIncidencia.setJustfica(justifica);
                    adapterIncidencia.setEstatusJustificacion(EstatusJustificacion);
                    listaIncidencias.setAdapter(adapterIncidencia);
                    listaIncidencias.setVisibility(View.VISIBLE);
            }
            if(va)
            {
                array1 = incidencias.getListaCatalogo();

                nombreplantilla= new ArrayList<>();
                fechainicioplantilla= new ArrayList<>();
                justificarplantilla= new ArrayList<>();
                CSCplantilla = new ArrayList<>();
                Estatusplantilla= new ArrayList<>();
                numeroEmpleado= new ArrayList<>();
                Nombres_empleados = new String[array1.length()];
                final ArrayList<String> arrayList = new ArrayList<>();
                numeroDeEmpleados= new ArrayList<>();




                int y = 0;
                String nombretemporal = "";
                String numero="";
                String prue[]= new String[array1.length()];

                for (int t = 0; t < array1.length(); t++) {

                    try {
                        JSONObject incidenciasdatos1 = array1.getJSONObject(t);

                        Nombres_empleados[t] = incidenciasdatos1.getString("Nombre");
                        nombretemporal = incidenciasdatos1.getString("Nombre");

                        prue[t]=incidenciasdatos1.getString("Empleado");
                        numero=incidenciasdatos1.getString("Empleado");


                        if (t == 0) {
                            arrayList.add(incidenciasdatos1.getString("Nombre"));
                            numeroDeEmpleados.add(incidenciasdatos1.getString("Empleado"));


                        }
                        if (t >= 1) {
                            if (Nombres_empleados[t - 1].equals(nombretemporal))
                            {

                            }
                            else
                            {

                                arrayList.add(incidenciasdatos1.getString("Nombre"));
                                numeroDeEmpleados.add(incidenciasdatos1.getString("Empleado"));

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        alertas(getString(R.string.msjError));
                    }

                }


                if (arrayList.isEmpty())
                {
                    arrayList.add("vacio");
                   // alertas("No tienes personal a cargo");
                    Toast.makeText(getApplicationContext(), "No tienes a nadie a cargo con justificaciones por autorizar", Toast.LENGTH_LONG).show();
                    finish();
                } else
                {
                    selecion_empledo.setText(arrayList.get(0));

                    datos(0);


                }

                selecion_empledo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mostrar(arrayList, selecion_empledo);


                    }
                });




            }
        }

        else{
            alertas(getString(R.string.msjError));
            selecion_empledo.setText("No hay empleados");
        }

        listaIncidencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (EstatusJustificacion[position].equals("PENDIENTE DE AUTORIZACION") || EstatusJustificacion[position].equals("AUTORIZADO")) {


                } else {
                    String fechas1 = fechainicio[position];
                    String fechapura = "";

                    StringTokenizer tokenizer = new StringTokenizer(fechas1);
                    int contador1 = 0;
                    String[] datos = new String[2];
                    while (tokenizer.hasMoreTokens()) {
                        datos[contador1] = tokenizer.nextToken();
                        contador1++;
                    }
                    fechapura = datos[0];
                    int contador = 0;
                    String datos1[] = new String[3];
                    StringTokenizer tokenizer1 = new StringTokenizer(fechapura, "-");

                    while (tokenizer1.hasMoreTokens()) {
                        datos1[contador] = tokenizer1.nextToken();
                        contador++;
                    }
                    String finalfecha = datos1[2] + "-" + datos1[1] + "-" + datos1[0];

                    Intent intent = new Intent(ListaIncidenciasActivity.this, justificacionCatalogo.class);
                    intent.putExtra("fecha", finalfecha);
                    intent.putExtra("incidencia", incidenciaNombre[position]);
                    intent.putExtra("CSC", CSC[position]);
                    intent.putExtra("empleadonumero", minumeroEmpleado[position]);
                    intent.putExtra("nombre", nombre4);
                    intent.putExtra("tipo", true);// se valida para saber de quien viene si de mis incidencias o de mis plantillas
                    startActivity(intent);
                }
            }
        });
             // lista de las plantillas
        plantillas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String status = Estatusplantilla.get(position);


                if (status.equals("SIN JUSTIFICAR")) {
                    String fechas1 = fechainicioplantilla.get(position);
                    String fechapura = "";

                    StringTokenizer tokenizer = new StringTokenizer(fechas1);
                    int contador1 = 0;
                    String[] datos = new String[2];
                    while (tokenizer.hasMoreTokens()) {
                        datos[contador1] = tokenizer.nextToken();
                        contador1++;
                    }
                    fechapura = datos[0];
                    int contador = 0;
                    String datos1[] = new String[3];
                    StringTokenizer tokenizer1 = new StringTokenizer(fechapura, "-");

                    while (tokenizer1.hasMoreTokens()) {
                        datos1[contador] = tokenizer1.nextToken();
                        contador++;
                    }
                    String finalfecha = datos1[2] + "/" + datos1[1] + "/" + datos1[0];

                    Intent intent = new Intent(ListaIncidenciasActivity.this, justificacionCatalogo.class);
                    intent.putExtra("fecha", finalfecha);
                    intent.putExtra("incidencia", nombreplantilla.get(position));
                    intent.putExtra("CSC", CSCplantilla.get(position));
                    intent.putExtra("tipo", false);
                    intent.putExtra("empleadonumero", numeroEmpleado.get(0));
                    intent.putExtra("nombre", selecion_empledo.getText().toString());
                    startActivity(intent);

                }
                if (status.equals("AUTORIZADO")) {
                   /* Intent intent = new Intent(ListaIncidenciasActivity.this, VerDetalleJustificacionActivity.class);
                    intent.putExtra("CSC", CSCplantilla.get(position));
                    intent.putExtra("nombre", nombreplantilla.get(position));
                    intent.putExtra("jefe", false);
                    intent.putExtra("empleadonumero", numeroEmpleado.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    */


                }

                if (status.equals("PENDIENTE DE AUTORIZACION")) {

                    Intent intent = new Intent(ListaIncidenciasActivity.this, VerDetalleJustificacionActivity.class);
                    intent.putExtra("CSC", CSCplantilla.get(position));
                    intent.putExtra("nombre", nombreplantilla.get(position));
                    intent.putExtra("jefe", true);
                    intent.putExtra("empleadonumero", numeroEmpleado.get(0));
                    intent.putExtra("nombreEmpleado", selecion_empledo.getText().toString());
                    startActivity(intent);

                    // se autoriza o recheza//
                }

            }
        });
    }

    public void alertas(String error)
    {
        final Dialog alert = new Dialog(ListaIncidenciasActivity.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText(error);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
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
        } else
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }

        if(progreso.isShown()){
            IncidenciasAsync incidenciasAsync= new IncidenciasAsync(this);
            incidenciasAsync.cancel(true);
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
            intent.putExtra("Main", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    public  void mostrar(ArrayList arrayList, final TextView textView){
        final Dialog alert = new Dialog(ListaIncidenciasActivity.this,R.style.Theme_Dialog_Translucent);
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
                selecion_empledo.setText(numeroDeEmpleados.get(position)+"-"+a);

                if(numeroEmpleado.size()>1){
                    numeroEmpleado.clear();
                    nombreplantilla.clear();
                    fechainicioplantilla.clear();
                    justificarplantilla.clear();
                    CSCplantilla.clear(); // se usa solo para cuando se vaya a justificar//
                    Estatusplantilla.clear();
                    numeroEmpleado.clear();


                }
                datos(position);

                alert.dismiss();
            }
        });

    }

    public void datos(int posicion){
        int cont=0;
        for(int i=0;i<array1.length();i++)
        {
            try
            {

                JSONObject incidenciasdatos1 = array1.getJSONObject(i);
                String nombrever=incidenciasdatos1.getString("Empleado");
                int tt=Integer.valueOf(nombrever);
             int df= Integer.valueOf(numeroDeEmpleados.get(posicion));


                // condicion
                if(tt==df)
                {
                    nombreplantilla.add(incidenciasdatos1.getString("Incidencia").trim());
                    fechainicioplantilla.add(incidenciasdatos1.getString("FechaOcurrencia").trim());
                    justificarplantilla.add(incidenciasdatos1.getBoolean("Justificada"));
                    CSCplantilla.add(incidenciasdatos1.getInt("CSC"));  // se usa solo para cuando se vaya a justificar//
                    Estatusplantilla.add(incidenciasdatos1.getString("EstatusJustificacion"));
                    numeroEmpleado.add(incidenciasdatos1.getString("Empleado"));

                    cont++;


                }

            } catch (JSONException e)
            {
                e.printStackTrace();
                alertas(getString(R.string.msjError));
            }

        }

        Adapterplntillas  adapterplntillas= new Adapterplntillas(this,R.layout.lista_zonas_ubicacion,nombreplantilla);
        adapterplntillas.setIncidencia(nombreplantilla);
        adapterplntillas.setFecha(fechainicioplantilla);
        adapterplntillas.setJustfica(justificarplantilla);
        adapterplntillas.setEstatusJustificacion(Estatusplantilla);
        adapterplntillas.setNumeroEmpleado(numeroEmpleado);
        plantillas.setAdapter(adapterplntillas);
        plantillas.setVisibility(View.VISIBLE);

    }
}
