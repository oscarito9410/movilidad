package com.gruposalinas.elektra.movilidadgs.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.geolocation.GPSTracker;
import com.gruposalinas.elektra.movilidadgs.geolocation.ServicePanico;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;

public class Panico extends Activity {

    Handler handler;
    Runnable runnable;
    CountDownTimer segundero;
    TextView mensaje,contacto1,contacto2,contacto3;
    ImageView imagen,editar;
    pruebaclase pruebaclase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panico);
        init();
        mandarServicio();
        // se va crear la vista boton panico//

        segundero = new CountDownTimer(Constants.tiempo, Constants.TIEMPO_INTERVALO) {

            public void onTick(long millisUntilFinished) {
                mensaje.setText("" + millisUntilFinished / Constants.TIEMPO_INTERVALO + " ");
            }

            public void onFinish() {
                mensaje.setText("0");
            }
        };

        segundero.start();

        imagen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    pruebaclase = new pruebaclase();

                    pruebaclase.execute(0);


                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    pruebaclase.cancel(true);


                }
                return true;
            }
        });


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                Intent intent = new Intent(Panico.this, Contactos.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public  void mandarServicio(){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // iniciar el servicio panico//

               startService(new Intent(getBaseContext(), ServicePanico.class));
               /* GPSTracker gps = new GPSTracker(getApplicationContext());
                SMS(gps.getLatitud() + "", gps.getLongitud() + "");

                */

                finish();


            }
        };
        handler.postDelayed(runnable, Constants.tiempo);
    }


    @SuppressLint("UnlocalizedSms")
    public void SMS(String l,String L){
        String numeroJefe = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.NUMERO_JEFE, "");
        DatosContacto datosContacto= new DatosContacto();
        String mensaje="Necesito ayuda http://maps.google.com/maps?f=q&q=("+l+","+L+")";
        System.out.print(mensaje.length());

        if(!numeroJefe.equals(""))
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(numeroJefe, null, mensaje , null, null);


                Toast.makeText(getApplicationContext(), "Enviando SMS!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
        if(!datosContacto.gettel1(this).equals(""))
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(datosContacto.gettel1(this), null, mensaje , null, null);


                Toast.makeText(getApplicationContext(), "Enviando SMS!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

        if(!datosContacto.gettel2(this).equals(""))
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(datosContacto.gettel2(this), null, mensaje , null, null);


                Toast.makeText(getApplicationContext(), "Enviando SMS!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

        if(!datosContacto.gettel3(this).equals(""))
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(datosContacto.gettel3(this), null, mensaje , null, null);


                Toast.makeText(getApplicationContext(), "Enviando SMS!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

    }


    public void init()
    {
        DatosContacto datosContacto= new DatosContacto();
        imagen=(ImageView)findViewById(R.id.animacion);
        imagen.setImageBitmap(dibujar(350,165));
        mensaje=(TextView)findViewById(R.id.timer);
        editar=(ImageView)findViewById(R.id.editarContactos);
        contacto1=(TextView)findViewById(R.id.contactoe);
        contacto2=(TextView)findViewById(R.id.contactoe1);
        contacto3=(TextView)findViewById(R.id.contactoe2);

        if(datosContacto.gettel1(this).equals(""))
        {
            contacto1.setText(contacto1.getText()+"No asignado");


        }
        else{
            contacto1.setText(contacto1.getText()+datosContacto.gettel1(this));


        }

        if(datosContacto.gettel2(this).equals(""))
        {
            contacto2.setText(contacto2.getText()+"No asignado");


        }else{

            contacto2.setText(contacto2.getText()+datosContacto.gettel2(this));

        }

        if(datosContacto.gettel3(this).equals(""))
        {
            contacto3.setText(contacto3.getText()+"No asignado");


        }else
        {
            contacto3.setText(contacto3.getText()+datosContacto.gettel3(this));


        }


    }

    public Bitmap dibujar(int x, int y){
        Bitmap b = Bitmap.createBitmap(700, 700, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        Paint paint = new Paint();
        Paint paint1= new Paint();

        paint.setColor(Color.parseColor("#FF0000"));
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        // dibujo aparte//


        paint1.setColor(Color.parseColor("#4A4C56"));
        paint1.setStrokeWidth(30);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(350, 165, 150, paint1);

        //


        canvas.drawCircle(x, y, 150, paint);
        paint.setColor(Color.parseColor("#FFDB4C"));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);
        final RectF oval = new RectF();
        paint.setStyle(Paint.Style.STROKE);
        oval.set(10, 10, 290, 290);
        canvas.drawArc(oval, 270, ((0) / 100), false, paint);
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
        canvas.drawText("CANCELAR", x, y + (paint.getTextSize() / 3), paint);
        return b;
    }

    class pruebaclase extends AsyncTask<Integer, Integer, Integer> {
        int contador=0;

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
            imagen.setImageBitmap(dibujar(350, 165));
            segundero.cancel();
            segundero.start();
            handler.removeCallbacks(runnable);
            mandarServicio();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Bitmap b = Bitmap.createBitmap(700, 700, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(b);
            Paint paint = new Paint();
            Paint paint1= new Paint();

            paint.setColor(Color.parseColor("#FF0000"));
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            //dibujo aparte

            paint1.setColor(Color.parseColor("#4A4C56"));
            paint1.setStrokeWidth(30);
            paint1.setAntiAlias(true);
            paint1.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(350,165,150,paint1);

            //
            canvas.drawCircle(350, 165, 140, paint);

            paint.setColor(Color.parseColor("#FFDB4C"));
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.FILL);
            final RectF oval = new RectF();
            paint.setStyle(Paint.Style.STROKE);
            oval.set(210, 30, 490, 300);
            canvas.drawArc(oval, 270, ((contador * 360) / 100), false, paint);
            paint.setStrokeWidth(0);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(Color.parseColor("#FFFFFF"));
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(70);
            canvas.drawText("" + contador, 350, 150 + (paint.getTextSize() / 3), paint);
            imagen.setImageBitmap(b);


        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {

                while (contador < 100) {
                    contador += 10;
                    publishProgress(contador);
                    Thread.sleep(100);


                }
                return contador;

            } catch (Exception ignored) {

            }
            return contador;


        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            handler.removeCallbacks(runnable);
            segundero.cancel();
         //   new Intent(getBaseContext(), ServicePanico.class);
            // mensaje.setText("Paraste la alerta de boton de panico");
            alerta("Se detuvo el envío de la alerta de Botón de Pánico");

        }
    }

    public void alerta(String mensaje)
    {

        final Dialog alert = new Dialog(Panico.this,R.style.Theme_Dialog_Translucent);
        LayoutInflater inflater1=getLayoutInflater();
        final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
        TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
        titulodialo.setText("  Aviso: ");
        LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
        TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
        error1.setText(mensaje);
        alert.setContentView(dialogo);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                boolean checar = bundle.getBoolean("Main");
                if (checar) {
                    Intent intent = new Intent(Panico.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    finish();
                }
                alert.dismiss();

            }
        });
        alert.show();



    }

}
