package com.example.adrian.vistaweb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class vista_web_descarga extends Activity {

    String url,version;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_web_descarga);
        Bundle bundle=getIntent().getExtras();
        url=bundle.getString("URL");
        version=bundle.getString("version");

        alertas();
    }

    public void alertas()
    {

        final Dialog alert = new Dialog(vista_web_descarga.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.mensaje, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        titulodialo.setText("Aviso: ");
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.texto);
        LinearLayout cancelar=(LinearLayout)dialogo.findViewById(R.id.boton_cancelar);
        error1.setText("Deseas actualizar en este momento.");
        alert.setContentView(dialogo);
        alert.show();
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                finish();
            }
        });

       cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });




    }
}
