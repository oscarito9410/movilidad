package com.gruposalinas.elektra.movilidadgs.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.ObtenerddlPermisoAsync;
import com.gruposalinas.elektra.movilidadgs.async.SolicitarExclusionAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Horarios;
import com.gruposalinas.elektra.movilidadgs.beans.Permisos;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Agregar_Permisos_activity extends Activity   {

    LinearLayout regresar;
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
   static TextView fecha_inicio,fecha_fin;
    Button agregarPermiso;
   TextView horaincio,horafin,minutosinicio,minutosfin;
    TextView motivo;
    JSONArray jsonArray;
    String []llave;
     String[] valor;
    String obtienevalor;
    RelativeLayout progreso;
    ImageButton back;

    String EntradaHora="", EntradaMinuto="",SalidaHora="",SalidaMinuto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar__permisos_activity);
        init();
        action_boton();
    }


    private void init(){
        regresar=(LinearLayout)findViewById(R.id.regresar2);
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
        fecha_fin=(TextView)findViewById(R.id.fechafin);
        fecha_inicio=(TextView)findViewById(R.id.fechainicio);
        agregarPermiso=(Button)findViewById(R.id.agregarpermiso);
        horaincio=(TextView)findViewById(R.id.ponerhora);
        horafin=(TextView)findViewById(R.id.horafinal);
        minutosinicio=(TextView)findViewById(R.id.minutoponer);
        minutosfin=(TextView)findViewById(R.id.minutosfinal);
        motivo=(TextView)findViewById(R.id.motivo);
        progreso=(RelativeLayout)findViewById(R.id.progresoIncidencia);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String id_empleado=prefs.getString(Constants.SP_ID, "");

        Horarios horarios= new Horarios();
        horarios.setId_empleado(id_empleado);
        ObtenerddlPermisoAsync obtenerddlPermisoAsync= new ObtenerddlPermisoAsync(this);
        obtenerddlPermisoAsync.execute(horarios);
        progreso.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Agregar_Permisos_activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });


    }

    private void action_boton()
    {
        final String [] horas=getResources().getStringArray(R.array.hora);
        final  String [] minutos=getResources().getStringArray(R.array.am_pm);

        cotacto_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Agregar_Permisos_activity.this, Contacto.class);
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
                Intent intent = new Intent(Agregar_Permisos_activity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Agregar_Permisos_activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Agregar_Permisos_activity.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        incidencias_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Agregar_Permisos_activity.this,ListaIncidenciasActivity.class);
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
        fecha_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new fechas();

                newFragment.show(getFragmentManager(), "datePicker");

            }
        });
        fecha_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment newFragment = new fechas1();

                newFragment.show(getFragmentManager(), "datePicker");

            }
        });

        agregarPermiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                ordenarhorario(horafin.getText().toString(),minutosfin.getText().toString(),horaincio.getText().toString(),minutosinicio.getText().toString());
                SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

                Permisos permisos= new Permisos();
                permisos.setId_numero_empleado(prefs.getString(Constants.SP_ID, ""));
                    permisos.setTipo("Mias");
                permisos.setTipo_exclusion(obtienevalor);
              String fin=  EntradaHora+":"+EntradaMinuto;
                String inicio=SalidaHora+":"+SalidaMinuto;
                permisos.setFecha_final(generateJsonDate(fecha_inicio.getText().toString(),inicio));
                permisos.setFecha_inicial(generateJsonDate(fecha_fin.getText().toString(), fin));
                agregarSolicitud(permisos);


            }
        });
        horaincio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mostrar(horas, horaincio);
            }
        });
        horafin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar(horas,horafin);
            }
        });
        minutosfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mostrar(minutos,minutosfin);
            }
        });
        minutosinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar(minutos,minutosinicio);

            }
        });

        motivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarvalores(llave,motivo);

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

    private void agregarSolicitud(Permisos permisos)
    {
        SolicitarExclusionAsync solicitarExclusionAsync= new SolicitarExclusionAsync(this);
        solicitarExclusionAsync.execute(permisos);
        progreso.setVisibility(View.VISIBLE);

    }

    public void mostrar(String lis[], final TextView textView)
    {

        final Dialog alert = new Dialog(Agregar_Permisos_activity.this,R.style.Theme_Dialog_Translucent);
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
              //  obtienevalor = valor[position];
                alert.dismiss();

            }
        });

    }


    public void mostrarvalores(String lis[], final TextView textView)
    {

        final Dialog alert = new Dialog(Agregar_Permisos_activity.this,R.style.Theme_Dialog_Translucent);
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
                obtienevalor=    valor[position];
                alert.dismiss();

            }
        });

    }


    @SuppressLint("ValidFragment")
  public  class fechas extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it

            return new DatePickerDialog(getActivity(),this, year, month, day);

        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            monthOfYear= monthOfYear+1;
            if(monthOfYear<10)
            {
                fechauno(String.valueOf(dayOfMonth),"0"+String.valueOf(monthOfYear),String.valueOf(year));
            }
            else {
                fechauno(String.valueOf(dayOfMonth),String.valueOf(monthOfYear),String.valueOf(year));
            }

        }
    }


    @SuppressLint("ValidFragment")
    public  class fechas1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it

            return new DatePickerDialog(getActivity(),this, year, month, day);

        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
           monthOfYear= monthOfYear+1;
            if(monthOfYear<10){
                fechados(String.valueOf(dayOfMonth),"0"+String.valueOf(monthOfYear),String.valueOf(year));
            }

            else {
                fechados(String.valueOf(dayOfMonth),String.valueOf(monthOfYear),String.valueOf(year));
            }

        }
    }


    @SuppressLint("SetTextI18n")
    private static void fechauno(String dia,String mes,String ano)
    {
      fecha_inicio.setText(dia+"/"+mes+"/"+ano);

    }

    @SuppressLint("SetTextI18n")
    private  static void fechados(String dia,String mes,String ano){

        fecha_fin.setText(dia+"/"+mes+"/"+ano);
    }


    private String generateJsonDate(String date1,String hours){
        //Setting jsonDate
        String jsonDate;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT1);
        Date date = new Date();
        try {
            date = formatter.parse(date1 + " " + hours);
        } catch (java.text.ParseException e) {
            Log.e("", "Error al parsear fecha");
        }
        jsonDate = Utils.getJsonDate(date);

        return jsonDate;
    }

    public void exito(Permisos permisos)
    {
        progreso.setVisibility(View.GONE);
        Alertas alertas;
        alertas=new Alertas(this);
        if(permisos.isSuccess())
        {
             alertas();

        }
        else{
            alertas.alertas("Error de conexión intentar más tarde.",Agregar_Permisos_activity.this);
        }

    }
    public void getlistaCatalogo(Horarios horarios)
    {
        progreso.setVisibility(View.GONE);
        if(horarios.isSuccess())
        {
            jsonArray=horarios.getDatosParse();

            llave= new String[jsonArray.length()];
            valor= new String[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++)
            {
                try {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    llave[i]=jsonObject.getString("Key");
                    valor[i]=jsonObject.getString("Value");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            motivo.setText(llave[0]+"");
            obtienevalor=valor[0];


        }
        else{
            motivo.setText("vacio");
        }

    }


    public void alertas(){
        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
        final LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent intent = new Intent(Agregar_Permisos_activity.this, Permisos_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        alert.show();


    }


    public void ordenarhorario(String HEntrada,String am_pm1,String Hsalida,String am_pm2)
    {
        EntradaHora=""; EntradaMinuto=""; SalidaHora=""; SalidaMinuto="";
        StringTokenizer stringTokenizer= new StringTokenizer(HEntrada,":");
        int HR=0;

        while (stringTokenizer.hasMoreTokens())
        {
            if(HR==0)
            {
                EntradaHora=stringTokenizer.nextToken();

            }else{
                EntradaMinuto=stringTokenizer.nextToken();
            }

            HR++;

        }

        StringTokenizer stringTokenizer1= new StringTokenizer(Hsalida,":");
        int hr=0;

        while(stringTokenizer1.hasMoreTokens())
        {
            if(hr==0)
            {
                SalidaHora=stringTokenizer1.nextToken();

            }else{
                SalidaMinuto=stringTokenizer1.nextToken();
            }
            hr++;

        }

        if(am_pm1.equals("am"))
        {
            if(EntradaHora.equals("12"))
            {
                EntradaHora="00";
            }

        }
        else
        {
            if(EntradaHora.equals("1"))
            {
                EntradaHora="13";
            }

            if(EntradaHora.equals("2"))
            {
                EntradaHora="14";
            }
            if(EntradaHora.equals("3"))
            {
                EntradaHora="15";

            }
            if(EntradaHora.equals("4"))
            {
                EntradaHora="16";

            }
            if(EntradaHora.equals("5"))
            {
                EntradaHora="17";
            }
            if(EntradaHora.equals("6"))
            {
                EntradaHora="18";
            }
            if(EntradaHora.equals("7"))
            {
                EntradaHora="19";
            }
            if(EntradaHora.equals("8"))
            {
                EntradaHora="20";
            }
            if(EntradaHora.equals("9"))
            {
                EntradaHora="21";
            }
            if(EntradaHora.equals("10"))
            {
                EntradaHora="22";
            }
            if(EntradaHora.equals("11"))
            {
                EntradaHora="23";
            }
            if(EntradaHora.equals("12"))
            {
                EntradaHora="12";
            }

        }

        // salida//


        if(am_pm2.equals("am"))
        {
            if(SalidaHora.equals("12"))
            {
                SalidaHora="00";
            }

        }
        else
        {
            if(SalidaHora.equals("1"))
            {
                SalidaHora="13";
            }

            if(SalidaHora.equals("2"))
            {
                SalidaHora="14";
            }
            if(SalidaHora.equals("3"))
            {
                SalidaHora="15";

            }
            if(SalidaHora.equals("4"))
            {
                SalidaHora="16";

            }
            if(SalidaHora.equals("5"))
            {
                SalidaHora="17";
            }
            if(SalidaHora.equals("6"))
            {
                SalidaHora="18";
            }
            if(SalidaHora.equals("7"))
            {
                SalidaHora="19";
            }
            if(SalidaHora.equals("8"))
            {
                SalidaHora="20";
            }
            if(SalidaHora.equals("9"))
            {
                SalidaHora="21";
            }
            if(SalidaHora.equals("10"))
            {
                SalidaHora="22";
            }
            if(SalidaHora.equals("11"))
            {
                SalidaHora="23";
            }
            if(SalidaHora.equals("12"))
            {
                SalidaHora="12";
            }

        }

        //
    }


}
