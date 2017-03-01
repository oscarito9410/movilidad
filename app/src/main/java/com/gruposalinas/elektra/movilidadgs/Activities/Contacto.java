package com.gruposalinas.elektra.movilidadgs.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.paginaweb;

import java.util.ArrayList;

public class Contacto extends Activity {

    LinearLayout marcar1;
    LinearLayout marcar2;
    LinearLayout marcar3;
    TextView numero1,numero2,numero3;
    String numero;
    LinearLayout regresar;
    ImageView cuentanos;
    LinearLayout panico;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        init();


        cuentanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new paginaweb(getApplicationContext());
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Contacto.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        marcar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero = numero1.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            }
        });
        marcar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numero = numero2.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            }
        });

        marcar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero = numero3.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(Contacto.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
    }

    private void init(){
        marcar1=(LinearLayout)findViewById(R.id.marcar1);
        numero1=(TextView)findViewById(R.id.numero1);
        marcar2=(LinearLayout)findViewById(R.id.marcar2);
        numero2=(TextView)findViewById(R.id.numero2);
        marcar3=(LinearLayout)findViewById(R.id.marcar3);
        numero3=(TextView)findViewById(R.id.numero3);
        regresar=(LinearLayout)findViewById(R.id.regresar);
        cuentanos=(ImageView)findViewById(R.id.paginaweb5);
        panico=(LinearLayout)findViewById(R.id.panico);
        back=(ImageButton)findViewById(R.id.boton_regresar);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(Contacto.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

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
