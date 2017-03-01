package com.gruposalinas.elektra.movilidadgs.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.InsertaTelefonoContactoAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Multimedia;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

import java.util.ArrayList;

public class Contactos extends Activity {

    EditText tel1,tel2,tel3;
    Button guardar;
    String t1,t2,t3;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    LinearLayout regresar;
    ImageButton back;
    ArrayList <String> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        init();
        captutrar();
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Contactos.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init()
    {
        tel1=(EditText)findViewById(R.id.tel1);
        tel2=(EditText)findViewById(R.id.tel2);
        tel3=(EditText)findViewById(R.id.tel3);
        guardar=(Button)findViewById(R.id.guardar);
        regresar=(LinearLayout)findViewById(R.id.regresar2);
        back=(ImageButton)findViewById(R.id.boton_regresar);
        mostrarContactos();
        lista= new ArrayList<>();

    }

    private void mostrarContactos()
    {
        DatosContacto datosContacto=new DatosContacto();
        tel1.setText(datosContacto.gettel1(this));
        tel2.setText(datosContacto.gettel2(this));
        tel3.setText(datosContacto.gettel3(this));
    }

    private void captutrar()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contactos.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                t1 = tel1.getText().toString();
                t2 = tel2.getText().toString();
                t3 = tel3.getText().toString();
                if (t1.equals("") && t2.equals("") && t3.equals("") || t1.equals("(55)") && t2.equals("(55)") && t3.equals("(55)")) {
                    alerta("Debes ingresar al menos un contacto para en caso de emergencia.");
                    //  GuardarDatos(t1, t2, t3);


                }
                if ((!t1.equals("")) || (!t2.equals("")) || (!t3.equals(""))) {
                    GuardarDatos(t1, t2, t3);


                } else {
                    alerta("Ingresa en orden los datos de tu contacto de emergencia.");

                }



            }
        });


    }

    private void GuardarDatos(String t1,String t2,String t3)
    {
        prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        edit= prefs.edit();
        edit.putString(Constants.tel1, t1);
        edit.putString(Constants.tel2, t2);
        edit.putString(Constants.tel3,t3);
        edit.putBoolean("guardar", true);
        edit.apply();
        lista.add(t1);
        lista.add(t2);
        lista.add(t3);
        tarea(lista);
        finish();
    }

    public void alerta(String mensaje){

        final Dialog alert = new Dialog(this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        titulodialo.setText("  Aviso error");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Contactos.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void tarea(ArrayList arrayList)
    {
        Multimedia multimedia= new Multimedia();
        multimedia.setListacontacos(arrayList);
        multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
        InsertaTelefonoContactoAsync insertaTelefonoContactoAsync= new InsertaTelefonoContactoAsync(this);
        insertaTelefonoContactoAsync.execute(multimedia);

    }
}
