package com.gruposalinas.elektra.movilidadgs.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.AdapterListaDetalleJustifica;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.HistorialJustificacionAsync;
import com.gruposalinas.elektra.movilidadgs.async.ObtenerArchivosAdjuntosAsync;
import com.gruposalinas.elektra.movilidadgs.async.RechazarJustificanteAsync;
import com.gruposalinas.elektra.movilidadgs.async.ValidarJustificanteAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerDetalleJustificacionActivity extends Activity {

    TextView fecha,tipo,comentario,Des,Justificador;
    GridView lista;
    JSONArray array;
    String [] justificador=null;
    String  fechas[]= null;
    String tipos[]= null;
    String  descrip[]= null;
    String [] comentarios=null;
    String [] fechavalidacion=null;
    AdapterListaDetalleJustifica adapterListaDetalleJustifica;
    RelativeLayout progreso;
    String tipoReal[]=null;
    String nombreInci;
    int [] id_justificacion=null;
    String tipover;
    JSONArray arrayImagen;
    ImageView imageView;
    boolean isjefe;
    LinearLayout botones;
    Button autorizar,rechazar;
    int CSC;
    String numero_de_mi_empleado;
    LayoutInflater layoutInflater;
    View popView;
    PopupWindow popupWindow;
    Bitmap bitmap1;
    DrawerLayout dLayout;
    String imagenBase64="";
    LinearLayout regresar,panico,menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu;
    ImageView cuentanos;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas,delete;
    TextView datosempleado;
    ImageButton back;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_justificacion);

        init();

        delete=(ImageView)findViewById(R.id.dlete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dLayout.closeDrawer(GravityCompat.START);
            }
        });
        dLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        dLayout.setFitsSystemWindows(true);
        layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        popView = layoutInflater.inflate(R.layout.visor_imagen, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.dismiss();


        autorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // metodo de autorizar

               confirmar();
                //tareaautorizar();

            }
        });

        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcomentario();



            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                popView = layoutInflater.inflate(R.layout.visor_imagen, null);
                popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                ImageView imageView= (ImageView)popView.findViewById(R.id.imagen);
                imageView.setImageBitmap(bitmap1);
                popupWindow.showAsDropDown(imageView, 50, 50);
            }
        });


        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        cuentanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paginaweb paginaweb = new paginaweb(getApplicationContext());
            }
        });

        menu_ubicacion.setFitsSystemWindows(true);
        menu_horario.setFitsSystemWindows(true);
        menu_panico.setFitsSystemWindows(true);
        menu_incidencias.setFitsSystemWindows(true);

        menu_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerDetalleJustificacionActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VerDetalleJustificacionActivity.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent= new Intent(VerDetalleJustificacionActivity.this,ListaIncidenciasActivity.class);
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

    public void rechazo(String coment)
    {
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        Incidencias incidencias= new Incidencias();
        incidencias.setCSCINCD(CSC);// id de incidencia
        incidencias.setId_csc_justificacion(id_justificacion[0]);// id de justificacion
        incidencias.setTipo(tipover);
        incidencias.setNumero_empleado(prefs.getString(Constants.SP_ID, ""));

        incidencias.setD_num_empleado_justifica((numero_de_mi_empleado));
        incidencias.setVa_comentarios(coment);

        RechazarJustificanteAsync rechazarJustificanteAsync= new RechazarJustificanteAsync(this);
        rechazarJustificanteAsync.execute(incidencias);

    }



    public void tareaautorizar()
    {
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        Incidencias incidencias= new Incidencias();
        incidencias.setCSCINCD(CSC);// id de incidencia
        incidencias.setId_csc_justificacion(id_justificacion[0]);// id de justificacion
        incidencias.setTipo(tipover);
        incidencias.setNumero_empleado(prefs.getString(Constants.SP_ID, ""));

        incidencias.setD_num_empleado_justifica((numero_de_mi_empleado));

        ValidarJustificanteAsync validarJustificanteAsync= new ValidarJustificanteAsync(this);
        validarJustificanteAsync.execute(incidencias);
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
        menu_horario=(LinearLayout)findViewById(R.id.horarios_menu);
        menu_ubicacion=(LinearLayout)findViewById(R.id.Ubicacion_menu);
        menu_incidencias=(LinearLayout)findViewById(R.id.menu_incidencias);
        menu_panico=(LinearLayout)findViewById(R.id.menu_panico);
        fecha=(TextView)findViewById(R.id.fechavalida);
        tipo=(TextView)findViewById(R.id.fechavalida);
        comentario=(TextView)findViewById(R.id.comentariohecho);
        Des=(TextView)findViewById(R.id.tipojus);
        Justificador=(TextView)findViewById(R.id.justificador);
        lista=(GridView)findViewById(R.id.lista_de_justificacion_valida);
        progreso=(RelativeLayout)findViewById(R.id.progresoDetalle_justificacion);
        imageView=(ImageView)findViewById(R.id.pruebaverimagen);
        botones=(LinearLayout)findViewById(R.id.botones);
        rechazar=(Button)findViewById(R.id.rechazar);
        autorizar=(Button)findViewById(R.id.autorizar);
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        panico=(LinearLayout)findViewById(R.id.panico);
        cuentanos=(ImageView)findViewById(R.id.paginaweb);
        datosempleado=(TextView)findViewById(R.id.datos);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        Bundle bundle=getIntent().getExtras();
        isjefe=bundle.getBoolean("jefe");
        numero_de_mi_empleado=bundle.getString("empleadonumero");
        nombreInci =bundle.getString("nombre");


        if(isjefe){
            botones.setVisibility(View.VISIBLE);
            datosempleado.setText(bundle.getString("nombreEmpleado"));
        }
        else{
            botones.setVisibility(View.INVISIBLE);
        }
         CSC=bundle.getInt("CSC");
        Incidencias incidencias= new Incidencias();
        incidencias.setCSCINCD(CSC);
        incidencias.setNumero_empleado(prefs.getString(Constants.SP_ID, ""));
        progreso.setVisibility(View.VISIBLE);
        HistorialJustificacionAsync historialJustificacionAsync= new HistorialJustificacionAsync(this);
        historialJustificacionAsync.execute(incidencias);

    }

    public  void mostrarIncidencias(Incidencias incidencias)
    {
        progreso.setVisibility(View.GONE);
        if(incidencias.isSuccess())
        {
            array=incidencias.getLista();
            fechas= new String[1];
            tipos= new String[1];
            justificador= new String[1];
            descrip= new String[1];
            comentarios= new String[1];
            fechavalidacion= new String[1];
            tipoReal= new String[1];
            id_justificacion= new int [1];

            for(int i=0;i<array.length();i++)
            {
                try {

                    JSONObject incidenciasdatos=array.getJSONObject(i);
                    fechas[i]=incidenciasdatos.getString("Fecha");
                   tipover= tipos[i]=incidenciasdatos.getString("tipo");
                    id_justificacion[i]=incidenciasdatos.getInt("CSC_JUS");
                    if(tipos[i].equals("tmp"))
                    {
                          tipos[i]="No";
                    }
                    else if(tipos[i].equals("def"))
                    {
                        tipos[i]="Sí";

                    }

                    justificador[i]=incidenciasdatos.getString("Justificador");
                    descrip[i]=incidenciasdatos.getString("Descripcion");
                    comentarios[i]=incidenciasdatos.getString("Comentarios");
                   fechavalidacion[i]=incidenciasdatos.getString("Fecha");
                    tipoReal[i]=nombreInci;
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                break;

            }

            adapterListaDetalleJustifica= new AdapterListaDetalleJustifica(this,R.layout.lista_zonas_ubicacion,fechas);

            adapterListaDetalleJustifica.setContext(this);
            adapterListaDetalleJustifica.setComentarios(comentarios);
            adapterListaDetalleJustifica.setDescripciones(descrip);
            adapterListaDetalleJustifica.setFechas(fechas);
            adapterListaDetalleJustifica.setTipos(tipos);
            adapterListaDetalleJustifica.setJustificador(justificador);
            adapterListaDetalleJustifica.setFechavalidacion1(fechavalidacion);
            adapterListaDetalleJustifica.setTipoReal(tipoReal);
            lista.setAdapter(adapterListaDetalleJustifica);

            incidencias.setTipo(tipover);
            incidencias.setId_csc_justificacion(id_justificacion[0]);

            ObtenerArchivosAdjuntosAsync obtenerArchivosAdjuntosAsync= new ObtenerArchivosAdjuntosAsync(this);
            obtenerArchivosAdjuntosAsync.execute(incidencias);

        }
        else{
            // error
            fechas=new String[1];
            fechas[0]="";
            adapterListaDetalleJustifica= new AdapterListaDetalleJustifica(this,R.layout.lista_zonas_ubicacion,fechas);

            comentarios= new String[1];
            comentarios[0]="";
            descrip=new String[1];
            descrip[0]="";
            tipos=new String[1];
            tipos[0]="";
            justificador=new String[1];
            justificador[0]="";
            fechavalidacion= new String[1];
            fechavalidacion[0]="";
            tipoReal= new String[1];
            tipoReal[0]="";
            adapterListaDetalleJustifica.setContext(this);
            adapterListaDetalleJustifica.setComentarios(comentarios);
            adapterListaDetalleJustifica.setDescripciones(descrip);
            adapterListaDetalleJustifica.setFechas(fechas);
            adapterListaDetalleJustifica.setTipos(tipos);
            adapterListaDetalleJustifica.setJustificador(justificador);
            adapterListaDetalleJustifica.setFechavalidacion1(fechavalidacion);
            adapterListaDetalleJustifica.setTipoReal(tipoReal);
            lista.setAdapter(adapterListaDetalleJustifica);
            lista.setVisibility(View.GONE);

        }


    }


    public void verimagen(Incidencias incidencias)
    {

        if(incidencias.isSuccess())
        {
            arrayImagen=incidencias.getAdjunto();


            for(int i=0;i<arrayImagen.length();i++)
            {
                try {

                    JSONObject incidenciasdatos=arrayImagen.getJSONObject(i);
                    imagenBase64=incidenciasdatos.getString("archivo");
                   // System.out.print(imagenBase64);

                    break;
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }

        if(imagenBase64.equals(""))
        {

        }
        else
        {
            bitmap1= decodeBase64(imagenBase64);
           bitmap1=Bitmap.createScaledBitmap(bitmap1, 800, 800, true);
            imageView.setImageBitmap(bitmap1);




        }

    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

    }

    public void validarJustificante(Incidencias incidencias)
    {
        String mensaje="";
        if(incidencias.isSuccess())
        {

            final Dialog alert = new Dialog(VerDetalleJustificacionActivity.this,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
            LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
            TextView texto=(TextView)dialogo.findViewById(R.id.textoeditar);
            TextView textView=(TextView)dialogo.findViewById(R.id.temporal);
            texto.setText("Haz autorizado la justificación\n correctamente");
            textView.setVisibility(View.INVISIBLE);

            alert.setContentView(dialogo);



            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    alert.dismiss();
                    Intent intent= new Intent(VerDetalleJustificacionActivity.this,ListaIncidenciasActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("plantilla", true);
                    startActivity(intent);



                }
            });

            alert.show();
        }
        else{
            mensaje=getString(R.string.errorvalidacionjustif);
            alertas(mensaje);
        }

    }
    public void rechazarjustificante(Incidencias incidencias)
    {
        String mensaje="";
        if(incidencias.isSuccess())
        {
            final Dialog alert = new Dialog(VerDetalleJustificacionActivity.this,R.style.Theme_Dialog_Translucent);
            LayoutInflater inflater1=getLayoutInflater();
            @SuppressLint("InflateParams") final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
            LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
            TextView texto=(TextView)dialogo.findViewById(R.id.textoeditar);
            TextView textView=(TextView)dialogo.findViewById(R.id.temporal);
            texto.setText("Haz rechazado la justificación");
            textView.setVisibility(View.INVISIBLE);
            alert.setContentView(dialogo);



            confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    alert.dismiss();
                    Intent intent= new Intent(VerDetalleJustificacionActivity.this,ListaIncidenciasActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("plantilla", true);
                    startActivity(intent);


                }
            });

            alert.show();
        }
        else{
            mensaje=getString(R.string.rechazo3);
            alertas(mensaje);
        }

    }

    public void alertas(String error)
    {

        final Dialog alert = new Dialog(VerDetalleJustificacionActivity.this,R.style.Theme_Dialog_Translucent);
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


    public void confirmar()
    {


        final Dialog alert = new Dialog(VerDetalleJustificacionActivity.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tareaautorizar();
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

    public void confirmarrechazo()
    {

        final Dialog alert = new Dialog(VerDetalleJustificacionActivity.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  rechazo();
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

        if (popupWindow.isShowing()||drawer.isDrawerOpen(GravityCompat.START)){
            popupWindow.dismiss();
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            finish();
        }

        if(progreso.isShown()){
            HistorialJustificacionAsync historialJustificacionAsync= new HistorialJustificacionAsync(this);
            historialJustificacionAsync.cancel(true);

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

    private void addcomentario(){
        final Dialog alert = new Dialog(VerDetalleJustificacionActivity.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.comentario, null);
        final EditText coment=(EditText)dialogo.findViewById(R.id.comentario_);
        final LinearLayout aceptar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        final LinearLayout cerrar=(LinearLayout)dialogo.findViewById(R.id.cerrarx);
        alert.setContentView(dialogo);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(coment.getText().toString().equals("") ||coment.getText().toString().length()<30 )
                {
                 alertasconfir("Debes tener un comentario como minimo de 30 caracteres");
                }
                else
                {
                    rechazo(coment.getText().toString() + "");

                }


            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();


    }



    public  void alertasconfir(String mensaje){


        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText(mensaje);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();

    }



}


