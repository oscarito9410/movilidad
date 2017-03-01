package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.AgregarJustificacionaAsync;
import com.gruposalinas.elektra.movilidadgs.async.JustificanteCatalogoAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Incidencias;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class justificacionCatalogo extends Activity
{
    TextView fecha,tipo;
    TextView spinner;
    EditText editText;
    JSONArray array;
    String cata[]= null;
    String valorJustificado[]=null;
    String valor;
    Button enviar;
    RelativeLayout progreso;
    ImageView acceso,camara;
    int CSC;
    boolean bit_temp_fija;
    private Uri mImageUri;
    Bitmap bitmap1;
    ImageView ver;
    String proar="";
    String numerodemiempleado;
    int valortemp;
    DrawerLayout dLayout;
    LinearLayout regresar,panico, menu_horario,menu_ubicacion,menu_incidencias,menu_panico,pendientes_zonas_horarios_menu;
    ImageView cuentanos,delete;
    TextView texto_menuHorario,texto_menuUbicacion,texto_panico,texto_incidencias;
    ImageView reloj,tocar,inciendenciasimas,panicoimas;
    LinearLayout contacto,conten;
    TextView num;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justificacion_catalogo);
        init();

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submenu("");
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
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
                Intent intent = new Intent(justificacionCatalogo.this, Contacto.class);
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
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progreso.setVisibility(View.VISIBLE);

                if (editText.getText().toString().equals(""))
                {
                    alertasconfir("El campo de comentarios no puede ir vacio");


                    progreso.setVisibility(View.GONE);
                }

                else  if(editText.getText().toString().length()<30 &&bit_temp_fija )
                {
                    alertasconfir("Debes tener un comentario como minimo de 30 caracteres");
                    progreso.setVisibility(View.GONE);

                }
                else if (Integer.valueOf(valor) < 0) {
                    alertasconfir("Seleccione un tipo.");
                    progreso.setVisibility(View.GONE);

                }
                else if((editText.getText().toString().length()>=30) && Integer.valueOf(valor)>0 && bit_temp_fija)
                {
                    alertas();
                }

                else if((editText.getText().toString().length()>0) && Integer.valueOf(valor)>0 && !bit_temp_fija)
                {
                    alertas();
                }


            }
        });


        acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1020);

            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1022);
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

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Intent intent = new Intent(justificacionCatalogo.this, Horarios_consulta.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        menu_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(justificacionCatalogo.this, Activity_Tiendas.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        menu_incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(justificacionCatalogo.this, ListaIncidenciasActivity.class);
                intent.putExtra("plantilla", false);
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

    public  void agregarjustificar()
    {
        int ta;
        if(proar.equals(""))
        {
            ta=0;

        }
        else{
            ta =proar.length();
        }
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Incidencias incidencias= new Incidencias();
        incidencias.setNumero_empleado((numerodemiempleado));
       incidencias.setD_num_empleado_justifica(prefs.getString(Constants.SP_ID, ""));
        incidencias.setCSCINCD(CSC);
        //incidencias.setD_num_empleado_justifica();
        incidencias.setVa_comentarios(editText.getText().toString());
         valortemp=Integer.valueOf(valor);
        incidencias.setId_justificacion(valortemp);
        progreso.setVisibility(View.VISIBLE);


         incidencias.setArchivo(proar);
        incidencias.setExtension("JPEG");
        incidencias.setBit_temp_fija(bit_temp_fija);


       incidencias.setTamano(ta);
        System.out.print(proar.length());
        AgregarJustificacionaAsync agregarJustificacionaAsync= new AgregarJustificacionaAsync(this);
        agregarJustificacionaAsync.execute(incidencias);


    }

    public void  acaboEnviar(Incidencias incidencias)
    {
        progreso.setVisibility(View.GONE);

        if(incidencias.isSuccess())
        {
           // String mensaje=getString(R.string.mensajejust);

            alertaExito();

        }
        else{
            String mensaje=getString(R.string.erroralertajustificacion);
            alertasconfir("No se pudieron enviar los datos intente más tarde.");


        }

    }

    public  void alertas(){

        final Dialog alert = new Dialog(justificacionCatalogo.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_dialogo_confirma, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        LinearLayout cancelar =(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        titulodialo.setText("Solicitar justificación de está incidencia.");
        alert.setContentView(dialogo);
        alert.show();
        progreso.setVisibility(View.GONE);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarjustificar();
                alert.dismiss();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });



    }

    public  void alertasconfir(String mensaje){


        final Dialog alert = new Dialog(justificacionCatalogo.this,R.style.Theme_Dialog_Translucent);
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
    public void alertaExito()
    {
        final Dialog alert = new Dialog(justificacionCatalogo.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_exito, null);
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_aceptar);
        alert.setContentView(dialogo);
        alert.show();
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(bit_temp_fija)
                {
                    Intent intent= new Intent(justificacionCatalogo.this,ListaIncidenciasActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("plantilla", false);
                    startActivity(intent);
                    finish();

                }
                else if(!bit_temp_fija)
                {
                    Intent intent= new Intent(justificacionCatalogo.this,ListaIncidenciasActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("plantilla", true);
                    startActivity(intent);
                    finish();


                }
                alert.dismiss();
            }
        });


    }
    public void init(){
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
        fecha=(TextView)findViewById(R.id.fecha_nojustificada);
        tipo=(TextView)findViewById(R.id.tipo_nojustificado);
        spinner=(TextView)findViewById(R.id.tiposeleccion);
        editText=(EditText) findViewById(R.id.comentario);
        enviar=(Button)findViewById(R.id.justficar);
        progreso=(RelativeLayout)findViewById(R.id.progresocatalogojustificacion);
        acceso=(ImageView)findViewById(R.id.adjuntar);
        camara=(ImageView)findViewById(R.id.tomarfoto);
        ver=(ImageView)findViewById(R.id.probar);
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        panico=(LinearLayout)findViewById(R.id.panico);
        cuentanos=(ImageView)findViewById(R.id.paginaweb5);
        contacto=(LinearLayout)findViewById(R.id.contacto);
        num=(TextView)findViewById(R.id.numeroEmpleado);
        conten=(LinearLayout)findViewById(R.id.contenedor);
        back=(ImageButton)findViewById(R.id.boton_regresar);


        Bundle bundle=getIntent().getExtras();
        String fecg=bundle.getString("fecha");
        String gt=bundle.getString("incidencia");
        CSC=bundle.getInt("CSC");
        numerodemiempleado=bundle.getString("empleadonumero");
        String nm=bundle.getString("nombre");
        bit_temp_fija=bundle.getBoolean("tipo");

        if(!bit_temp_fija){
          num.setText(numerodemiempleado+" -"+nm);
        }
        else{
            conten.setVisibility(View.INVISIBLE);
        }

        fecha.setText(fecg.replace("-","/"));
        tipo.setText(gt);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Incidencias incidencias= new Incidencias();
        incidencias.setNumero_empleado(prefs.getString(Constants.SP_ID, ""));
        progreso.setVisibility(View.VISIBLE);
        JustificanteCatalogoAsync justificanteCatalogoAsync=new JustificanteCatalogoAsync(this);
        justificanteCatalogoAsync.execute(incidencias);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();

            }
        });
    }

    public  void catalogomostrar(Incidencias incidencias)
    {
        progreso.setVisibility(View.GONE);
        if(incidencias.isSuccess())
        {
            array=incidencias.getListaCatalogo();
            cata=new String[array.length()];
            valorJustificado= new String[array.length()];
            for (int i=0;i<array.length();i++)
            {
                JSONObject incidenciasdatos= null;
                try {
                    incidenciasdatos = array.getJSONObject(i);
                    cata[i]=incidenciasdatos.getString("descripcion");
                    valorJustificado[i]=incidenciasdatos.getString("valor");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            spinner.setText(cata[0]);
            valor=valorJustificado[0];


        }
        else{
            alertasconfir(getString(R.string.msjError));
            spinner.setText("Vacio");
            valor="-1";
        }

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrar(cata, spinner);


            }
        });

          }

    public  void mostrar(String lista1[], final TextView textView){
        final Dialog alert = new Dialog(justificacionCatalogo.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.lista_horas_pm, null);
        GridView lista=(GridView)dialogo.findViewById(R.id.listado);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.spinner_item,lista1);
        lista.setAdapter(arrayAdapter);
        alert.setContentView(dialogo);
        alert.show();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = parent.getItemAtPosition(position).toString();
                textView.setText(a);
                valor = valorJustificado[position];
                alert.dismiss();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1020 && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            getImage(mImageUri);

            System.out.print(mImageUri+"");
        }
        if(requestCode==1022 && resultCode==RESULT_OK){
           Bitmap bitmap2=(Bitmap)data.getExtras().get("data");
           // bitmap2 = Bitmap.createScaledBitmap(bitmap2, 800, 800, true);
            proar =  encodeToBase64(bitmap2, Bitmap.CompressFormat.JPEG, 100);
            ver.setImageBitmap(bitmap2);

        }
    }

    public Bitmap getImage(Uri uri) {
        BitmapFactory.Options generalOptions;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = null;
        try {
            is = this.getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(is, null, options);
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        generalOptions = options;
        return scaleImage(options, uri, 300);
    }

    public static int nearest2pow(int value) {
        return value == 0 ? 0
                : (32 - Integer.numberOfLeadingZeros(value - 1)) / 2;
    }
    public Bitmap scaleImage(BitmapFactory.Options options, Uri uri, int targetWidth) {
        BitmapFactory.Options generalOptions = null;
        if (options == null)
            options = generalOptions;
        Bitmap bitmap = null;
        double ratioWidth = ((float) targetWidth) / (float) options.outWidth;
        double ratioHeight = ((float) targetWidth) / (float) options.outHeight;
        double ratio = Math.min(ratioWidth, ratioHeight);
        int dstWidth = (int) Math.round(ratio * options.outWidth);
        int dstHeight = (int) Math.round(ratio * options.outHeight);
        ratio = Math.floor(1.0 / ratio);
        int sample = nearest2pow((int)ratio);

        options.inJustDecodeBounds = false;
        if (sample <= 0) {
            sample = 1;
        }
        options.inSampleSize = (int) sample;
        options.inPurgeable = true;
        try {
            InputStream is;
            is = this.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is, null, options);
            if (sample > 1)
                bitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
            proar =  encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,100);
           // System.out.print(proar);
            //decodeBase64(proar);
           ver.setImageBitmap(decodeBase64(proar));

            is.close();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bitmap1;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), 0);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }

        if(progreso.isShown()){
            JustificanteCatalogoAsync justificanteCatalogoAsync=new JustificanteCatalogoAsync(this);
            justificanteCatalogoAsync.cancel(true);
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


    public void submenu(final String validar)
    {
        final Dialog alert = new Dialog(justificacionCatalogo.this,R.style.Theme_Dialog_Translucent);
        final LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.sub_menu, null);
        final ImageButton miHorario=(ImageButton)dialogo.findViewById(R.id.misHorarios);
        ImageButton plantillaHorario=(ImageButton)dialogo.findViewById(R.id.miplantillahorario);
        TextView titulo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        TextView titulo1=(TextView)dialogo.findViewById(R.id.textotitulo);
        TextView plantilla=(TextView)dialogo.findViewById(R.id.plantilla);
        LinearLayout cerrar=(LinearLayout)dialogo.findViewById(R.id.cerrar);
        miHorario.setBackgroundResource(android.R.drawable.ic_menu_camera);
        titulo1.setText("Camara");
        plantilla.setText("Galeria");

        plantillaHorario.setBackgroundResource(android.R.drawable.ic_menu_gallery);


        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        miHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1022);
                alert.dismiss();

            }
        });
        plantillaHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1020);
                alert.dismiss();


            }
        });

        alert.setContentView(dialogo);
        alert.show();

    }

}
