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
import android.widget.Toast;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterPermisos;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.ConsultarExclusionesEmpleadosAsync;
import com.gruposalinas.elektra.movilidadgs.async.RegistraActualizaEmpleadoExclusionAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.StringTokenizer;

public class Permisos_Plantilla_Activity extends Activity {

    LinearLayout regresar;
    ImageView agregar;
    GridView permisos_plantillla;
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
    ArrayList<String> descripcion_exclusion;
    ArrayList <String>dt_fecha_hora_final;
    ArrayList<String> dt_fecha_hora_inicial;
    ArrayList <Integer>id_estatus;
    ArrayList <String> nombreEmpleado;
    AdapterPermisos adapterPermisos;
    String Nombres_empleados[];
    TextView seleccionEmpleado;
     ArrayList<String> arrayList;
    ArrayList <String> numeros;
    String numeroEmpleado[];
    RelativeLayout progreso;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos__plantilla_);
        init();
        action_boton();
    }


    private void init(){
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        agregar=(ImageView)findViewById(R.id.imagenagregar);
        permisos_plantillla=(GridView)findViewById(R.id.permiso_plantilla);
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
        seleccionEmpleado=(TextView)findViewById(R.id.seleccionEmpleado);
        progreso=(RelativeLayout)findViewById(R.id.progresoIncidencia);
        back=(ImageButton)findViewById(R.id.boton_regresar);


        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Permisos permisos1= new Permisos();
        permisos1.setBusqueda("");
        permisos1.setEstatus("4");
        permisos1.setId_numero_empleado(prefs.getString(Constants.SP_ID, ""));
        permisos1.setTipo("Línea directa");
        ConsultarExclusionesEmpleadosAsync consultarExclusionesEmpleadosAsync= new ConsultarExclusionesEmpleadosAsync(this);
        consultarExclusionesEmpleadosAsync.execute(permisos1);
        progreso.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Permisos_Plantilla_Activity.this, MainActivity.class);
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
                intent = new Intent(Permisos_Plantilla_Activity.this, Contacto.class);
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
              //  texto_ubicacion_menu.setTextColor(getResources().getColor(R.color.amarillo));
                //imagen_ubicacion_menu.setBackgroundResource(R.drawable.ubicacion_a);
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Permisos_Plantilla_Activity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Permisos_Plantilla_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Permisos_Plantilla_Activity.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        incidencias_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Permisos_Plantilla_Activity.this,ListaIncidenciasActivity.class);
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
                Intent intent=new Intent(Permisos_Plantilla_Activity.this,Agregar_permisos_plantilla.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        seleccionEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar(arrayList,seleccionEmpleado);
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

    public void mostar(final Permisos permisos)
    {
        progreso.setVisibility(View.GONE);

        if(permisos.isSuccess())
        {
            String id;
            descripcion_exclusion= new ArrayList<>();
            dt_fecha_hora_final= new ArrayList<>();
            dt_fecha_hora_inicial= new ArrayList<>();
            id_estatus= new ArrayList<>();
            nombreEmpleado= new ArrayList<>();

            if(permisos.isSuccess())
            {


                da= permisos.getDatos();
                Nombres_empleados = new String[da.length()];
                numeroEmpleado=new String[da.length()];
             arrayList = new ArrayList<>();
                numeros= new ArrayList<>();
                int y = 0;
                String nombretemporal = "";
                String numer_t="";

                for (int t = 0; t < da.length(); t++) {

                    try {
                        JSONObject incidenciasdatos1 = da.getJSONObject(t);

                        Nombres_empleados[t] = incidenciasdatos1.getString("va_nombre_completo");
                        nombretemporal = incidenciasdatos1.getString("va_nombre_completo");
                        numeroEmpleado[t]=incidenciasdatos1.getString("id_num_empleado");
                        numer_t=incidenciasdatos1.getString("id_num_empleado");

                        if (t == 0) {
                            arrayList.add(incidenciasdatos1.getString("va_nombre_completo"));
                            numeros.add(incidenciasdatos1.getString("id_num_empleado"));


                        }
                        if (t >= 1) {
                            if (Nombres_empleados[t - 1].equals(nombretemporal))
                            {

                            }
                            else
                            {

                                arrayList.add(incidenciasdatos1.getString("va_nombre_completo"));
                                numeros.add(incidenciasdatos1.getString("id_num_empleado"));


                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Alertas alertas= new Alertas(this);
                        alertas.alertas("Error de conexión",this);
                    }

                }
                if(da.length()>0){
                    seleccionEmpleado.setText(arrayList.get(0));
                }
                if(da.length()<=0){
                    finish();
                    Toast.makeText(getApplicationContext(),"No tienes a nadie a cargo con permisos por autorizar",Toast.LENGTH_LONG).show();
                }

                ///permisos


                for(int i=0;i<da.length();i++)
                {
                    try
                    {
                        JSONObject obtener=da.getJSONObject(i);
                        id=obtener.getString("id_num_empleado");
                        d1=obtener.getJSONArray("exclusiones");

                        for (int y1=0;y1<d1.length();y1++)
                        {
                            JSONObject object=d1.getJSONObject(y1);
                            String tem=object.getString("id_num_empleado");
                            String n=seleccionEmpleado.getText().toString();
                            if(numeros.get(0).equals(tem))
                            {
                                descripcion_exclusion.add(object.getString("descripcion_exclusion"));
                                dt_fecha_hora_inicial.add(object.getString("fecha_hora_inicial"));
                                dt_fecha_hora_final.add(object.getString("fecha_hora_final"));
                                id_estatus.add(object.getInt("id_estatus"));

                            }

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
            permisos_plantillla.setAdapter(adapterPermisos);

            permisos_plantillla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    StringTokenizer stringTokenizer= new StringTokenizer(seleccionEmpleado.getText().toString(),"-");
                    String s[]= new String[2];
                    int contador=0;


                    while (stringTokenizer.hasMoreTokens())
                    {
                     s[contador]=stringTokenizer.nextToken();
                        contador++;

                    }
                    if(id_estatus.get(position)==1)
                    {
                        Permisos permisos1= new Permisos();
                        permisos1.setId_numero_empleado(s[0]);
                     String d=   dt_fecha_hora_final.get(position).replace("a las", " ").substring(0,10);
                      String d1=  dt_fecha_hora_inicial.get(position).replace("a las", " ").substring(0,10);
                        String h=   dt_fecha_hora_final.get(position).replace("a las", " ").substring(10,18);
                        String h1=  dt_fecha_hora_inicial.get(position).replace("a las", " ").substring(10, 18);


                        String g=d+" "+h;
                        String g1=d1+" "+h1;
                      //  @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.DATE_FORMAT1);

                        Date date = null;
                        Date date1=null;
                        try {
                            // nicial
                           //  date=dateFormatter.parse(d1);
                                 date=timeFormatter.parse(g1);

                            //final
                          //  date1=dateFormatter.parse(d);
                            date1=timeFormatter.parse(g);

                            //final
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                       String sw=Utils.getJsonDate(date);
                        String sw1=Utils.getJsonDate(date1);


                       permisos1.setFecha_inicial(sw);
                        permisos1.setFecha_final(sw1);

                        String des=descripcion_exclusion.get(position);
                        if(des.equals("Visita médica /Incapacidad")){
                            permisos1.setTipo_exclusion("1");

                        }

                        if(des.equals("Asunto personal")){
                            permisos1.setTipo_exclusion("2");

                        }

                        if(des.equals("Descanso / Vacaciones")){
                            permisos1.setTipo_exclusion("4");

                        }


                       Rechazar_autorizar(permisos1);



                    }
                }
            });
        }
    }


    public void mostrar(ArrayList lis, final TextView textView)
    {

        final Dialog alert = new Dialog(Permisos_Plantilla_Activity.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogo=inflater1.inflate(R.layout.lista_horas_pm, null);
        GridView lista=(GridView)dialogo.findViewById(R.id.listado);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.spinner_item,lis);
        lista.setAdapter(arrayAdapter);
        alert.setContentView(dialogo);
        alert.show();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = parent.getItemAtPosition(position).toString();
                textView.setText(a);
                alert.dismiss();
                if(numeros.size()>0)
                {
                    descripcion_exclusion.clear();
                    dt_fecha_hora_inicial.clear();
                    dt_fecha_hora_final.clear();
                    id_estatus.clear();

                }
                mostrardatosEmpleado(position);

            }
        });

    }

    public void  mostrardatosEmpleado(int t){


        for(int i=0;i<da.length();i++)
        {
            try
            {
                JSONObject obtener=da.getJSONObject(i);
                String id=obtener.getString("id_num_empleado");
                d1=obtener.getJSONArray("exclusiones");

                for (int y1=0;y1<d1.length();y1++)
                {
                    JSONObject object=d1.getJSONObject(y1);
                    String tem=object.getString("id_num_empleado");
                    String n=seleccionEmpleado.getText().toString();
                    if(numeros.get(t).equals(tem))
                    {
                        descripcion_exclusion.add(object.getString("descripcion_exclusion"));
                        dt_fecha_hora_inicial.add(object.getString("fecha_hora_inicial"));
                        dt_fecha_hora_final.add(object.getString("fecha_hora_final"));
                        id_estatus.add(object.getInt("id_estatus"));

                    }



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

            adapterPermisos= new AdapterPermisos(this,R.layout.lista_zonas_ubicacion,id_estatus);
            adapterPermisos.setContext(this);
            adapterPermisos.setDescripcion_exclusion(descripcion_exclusion);
            adapterPermisos.setDt_fecha_hora_final(dt_fecha_hora_final);
            adapterPermisos.setDt_fecha_hora_inicial(dt_fecha_hora_inicial);
            adapterPermisos.setId_estatus(id_estatus);
            permisos_plantillla.setAdapter(adapterPermisos);

    }


    public void exito(Permisos permisos)
    {
        if(permisos.isSuccess()){
            final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
            final LayoutInflater inflater1=getLayoutInflater();
            final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
            final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
            final TextView texto=(TextView)dialogo.findViewById(R.id.textoeditar);
            final TextView textView=(TextView)dialogo.findViewById(R.id.temporal);
            if(permisos.getEdicion().equals("1"))
            {
                texto.setText("Se ha rechazo el permiso a:");
                textView.setText(seleccionEmpleado.getText().toString());
            }
            if(permisos.getEdicion().equals("2")){

                texto.setText("Se ha autorizado a:");
                textView.setText(seleccionEmpleado.getText().toString());
            }

            alert.setContentView(dialogo);
            alert.setCancelable(false);
            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent= new Intent(Permisos_Plantilla_Activity.this,Permisos_Plantilla_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });

            alert.show();
        }

    }

    public void llamada(Permisos permisos){
        RegistraActualizaEmpleadoExclusionAsync re= new RegistraActualizaEmpleadoExclusionAsync(this);

        re.execute(permisos);

    }


    public void Rechazar_autorizar(final Permisos permisos3)
    {

        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.comentario_plantilla_permiso, null);
        final LinearLayout rechazar=(LinearLayout)dialogo.findViewById(R.id.boton_rechazar);
        final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        final EditText comentario=(EditText)dialogo.findViewById(R.id.comentario_);
        alert.setContentView(dialogo);
        comentario.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    // Ocultar teclado virtual
                    InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    procesado = true;
                }
                return procesado;

            }
        });
        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos3.setEstatus("3");// puede ser rechazdo o autorizado
                permisos3.setComentario("" + comentario.getText().toString());
                permisos3.setEdicion("1");// rechazar

                llamada(permisos3);
                alert.dismiss();


            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos3.setEstatus("2");// puede ser rechazdo o autorizado
                permisos3.setEdicion("2");// autorizo
                permisos3.setComentario("" + comentario.getText().toString());

                llamada(permisos3);
                alert.dismiss();

            }
        });

        alert.show();
    }


}
