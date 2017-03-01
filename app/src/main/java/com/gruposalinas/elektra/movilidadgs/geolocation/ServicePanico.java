package com.gruposalinas.elektra.movilidadgs.geolocation;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.gruposalinas.elektra.movilidadgs.Activities.capture_video;
import com.gruposalinas.elektra.movilidadgs.async.EnvioAlertaAsync;
import com.gruposalinas.elektra.movilidadgs.async.MultimediaAsync;
import com.gruposalinas.elektra.movilidadgs.async.ObtenerEstatusAlertaAsync;
import com.gruposalinas.elektra.movilidadgs.beans.DatosContacto;
import com.gruposalinas.elektra.movilidadgs.beans.Multimedia;
import com.gruposalinas.elektra.movilidadgs.beans.ObtenerEstatusAlerta;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;
import com.gruposalinas.elektra.movilidadgs.webservices.AgregarEmpleado_ArchivoClipMultimediaWS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class ServicePanico extends Service
{
    Timer timer1;
    boolean revisar=false;
    Handler handler,handler1;
     Runnable runnable,runnable1;
    private static String mFileName,mFileName1,mFileName2 = null;
    private MediaRecorder mRecorder = null, recorder2=null,recorder3=null;
    String graba;
    int valor=0;
    String graba1;
   Multimedia multimedia;
    @Override
    public void onCreate() {
        super.onCreate();

        takePhoto();
        multimedia= new Multimedia();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock1=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DonotSlepp");
        wakeLock1.acquire();

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/grabando1.mp4";
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setMaxDuration(10000);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);



         graba = Environment.getExternalStorageDirectory().getAbsolutePath();
        graba += "/grabando2.mp4";

        recorder2 = new MediaRecorder();
        recorder2.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder2.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder2.setMaxDuration(10000);
        recorder2.setOutputFile(graba);
        recorder2.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            recorder2.prepare();
        } catch (IOException e)
        {
            System.out.print("no se pudo preparar el microfono");

        }

         graba1= Environment.getExternalStorageDirectory().getAbsolutePath();
        graba1 += "/grabando3.mp4";

        recorder3 = new MediaRecorder();
        recorder3.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder3.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder3.setMaxDuration(10000);
        recorder3.setOutputFile(graba1);
        recorder3.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            recorder3.prepare();
        } catch (IOException e)
        {
            System.out.print("no se pudo preparar el microfono");


        }



        handler1= new Handler();
        final String finalGraba = graba1;
        runnable1= new Runnable() {
            @Override
            public void run() {

                valor++;

                if(valor==1)
                {


                    try {
                        mRecorder.prepare();
                    } catch (IOException e)
                    {
                        System.out.print("no se pudo preparar el microfono");


                    }

                  mRecorder.start();
                    Log.d("inicia", "inicia grabacion1");

                }
                if (valor==2)
                {
                    GPSTracker gps = new GPSTracker(getApplicationContext());
                    SMS(gps.getLatitud() + "", gps.getLongitud() + "");

                    Log.d("se para", "parar grabacion1");
                  mRecorder.stop();
                    File file= new File(mFileName);
                    try {

                         String a=  Base64.encodeToString(Utils.loadFile(file),0);

//                        System.out.println(a);

                        multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                        multimedia.setArchivo(a);
                        multimedia.setNombre_archivo("audio");
                        multimedia.setExtension("mp4");
                        multimedia.setTamano(file.length() + "");
                     //  enviarMultimedia(multimedia);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("inicia", "inicia grabacion2");
                  recorder2.start();

                    // inicia la grabacion 2
                }


                if(valor==3)
                {


                    Log.d("se para", "inicia grabacion2");


                    // metodo para parar grabacion 2
                  recorder2.stop();
                    File file= new File(graba);
                    try {

                        String a=  Base64.encodeToString(Utils.loadFile(file),0);

                      //   System.out.println(a);


                        multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                        multimedia.setArchivo(a);
                        multimedia.setNombre_archivo("audio1");
                        multimedia.setExtension("mp4");
                        multimedia.setTamano(file.getTotalSpace() + "");
                    //    enviarMultimedia(multimedia);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("inicia", "grbacion3");
                  recorder3.start();
                }

                if (valor==4){
                    Log.d("se para", "inicia grabacion3");

                  recorder3.stop();


                    File file= new File(graba1);
                    try {

                        String a=  Base64.encodeToString(Utils.loadFile(file),0);

                    //     System.out.println(a);

                        multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                        multimedia.setArchivo(a);
                        multimedia.setNombre_archivo("audio3");
                        multimedia.setExtension("mp4");
                        multimedia.setTamano(file.getTotalSpace() + "");

                      //  enviarMultimedia(multimedia);




                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    // prueba//

                    Intent intent1= new Intent(getApplicationContext(),capture_video.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);

                    //



                }
                handler1.postDelayed(runnable1,10000);
            }
        };
        handler1.post(runnable1);


        handler= new Handler();
        runnable= new Runnable() {
           @Override
           public void run() {
               revisar=true;
               alertapanico();
               handler.postDelayed(runnable,30000);
           }
       };
        handler.post(runnable);


        timer1 = new Timer();

        TimerTask task1 = new TimerTask() {

            @Override
            public void run()
            {

                ObtenerEstatusAlerta obtenerEstatusAlerta= new ObtenerEstatusAlerta();
                obtenerEstatusAlerta.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                ObtenerEstatusAlertaAsync obtenerEstatusAlertaAsync =new ObtenerEstatusAlertaAsync(ServicePanico.this);
                obtenerEstatusAlertaAsync.execute(obtenerEstatusAlerta);
            }
        };
        // Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
        timer1.schedule(task1, 10000, 300000);

        Log.d("se crea", "servicio panico");
        wakeLock1.release();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        alertapanico();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public  void alertapanico()
    {

        if(revisar){
            Log.d("Entra TIMER", true +"");
            GPSTracker gps = new GPSTracker(this);
            muestraPosicionpanico(gps);

        }else{
            GPSTracker gps = new GPSTracker(this);
            muestraPosicionpanico(gps);
        }

    }
    public void muestraPosicionpanico(GPSTracker _location){

      String l=_location.getLatitud()+"";
        String L=_location.getLongitud()+"";
      Date  date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DAY_FORMAT);
        dateFormatter.setLenient(false);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat(Constants.HOUR_FORMAT);
        timeFormatter.setLenient(false);


       String dateString = dateFormatter.format(date);
       String hourString = timeFormatter.format(date);


        RegistroGPS registroGPS= new RegistroGPS();
        registroGPS.setLatitud(_location.getLatitud());
        registroGPS.setLongitud(_location.getLongitud());
        registroGPS.setJsonDate(generateJsonDate(dateString, hourString));
        registroGPS.setNumEmpleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));

        System.out.println(registroGPS+"");

        EnvioAlertaAsync envioAlertaAsync= new EnvioAlertaAsync(this);
       envioAlertaAsync.execute(registroGPS);
    }

    private String generateJsonDate(String fecha ,String hora){
        //Setting jsonDate
        String jsonDate = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(fecha + " " + hora);
        } catch (java.text.ParseException e) {
            Log.e("error", "Error al parsear fecha");
        }
        jsonDate = Utils.getJsonDate(date);

        return jsonDate;
    }

    @SuppressLint("UnlocalizedSms")
    public void SMS(String l,String L)
    {
        String numeroJefe = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.NUMERO_JEFE, "");
        DatosContacto datosContacto= new DatosContacto();
        String nombrepersona=getSharedPreferences(Constants.SHARED_PREFERENCES,Context.MODE_PRIVATE).getString(Constants.SP_NAME,"");

        ArrayList<String> d= new ArrayList<>();
        StringTokenizer separar= new StringTokenizer(nombrepersona);

        while (separar.hasMoreTokens())
        {
           d.add(separar.nextToken());

        }
        System.out.print(d.size());
        String temp="";
        for(int t=0;t<d.size();t++)
        {
            if(t==0)
            {
                temp=d.get(t);
            }
            else{
                temp=temp+d.get(t).substring(0,1);
            }

        }

        String mensaje=temp+" Necesita ayuda http://maps.google.com/maps?f=q&q=("+l+","+L+")";

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

    public  void obtenerestatus(ObtenerEstatusAlerta obtenerEstatusAlerta)
    {

        if(valor>4)
        {
            handler1.removeCallbacks(runnable1);
            Log.d("finalizar","muere hilo de grabacion");
        }

        if(obtenerEstatusAlerta.isSuccess())
        {
           if(!obtenerEstatusAlerta.issuccess())
           {
               handler.removeCallbacks(runnable);
               timer1.cancel();
               try {
                   this.finalize();
                   stopService(new Intent(getBaseContext(), ServicePanico.class));
               } catch (Throwable throwable)
               {
                   throwable.printStackTrace();
               }

           }

        }

    }


    private void takePhoto() {

        Camera camera = null;
        int cameraCount = 1;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            SystemClock.sleep(1000);

            Camera.getCameraInfo(camIdx, cameraInfo);

            try {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                Camera.Parameters parameters = camera.getParameters();
                parameters.set("jpeg-quality", 70);
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.setPictureSize(640, 480);
                camera.setParameters(parameters);



            } catch (RuntimeException e)
            {
                System.out.println("Camera no disponible: " + camIdx);
                camera = null;
                //e.printStackTrace();
            }
            try {
                if (null == camera)
                {
                    System.out.println("No se puede obtener una instancia de la camara");
                } else {
                    try {


                        camera.setPreviewTexture(new SurfaceTexture(0));

                        camera.startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    camera.takePicture(null, null, new Camera.PictureCallback() {

                        @Override
                        public void onPictureTaken(byte[] data, Camera camera)
                        {

                            File pictureFileDir = getFilesDir();
                            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                                return;
                            }
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                            String date = dateFormat.format(new Date());
                            String photoFile = "/Fotografia_" + "_" + date + ".JPEG";
                            mFileName1 = Environment.getExternalStorageDirectory().getAbsolutePath()+photoFile;
                            File mainPicture = new File(mFileName1);
                       //   MMS(mainPicture);

                            // addImageFile(mainPicture);

                            try {
                                FileOutputStream fos = new FileOutputStream(mainPicture);
                                fos.write(data);
                                fos.close();
                                System.out.println("Imagen guardada");
                                String a=  Base64.encodeToString(Utils.loadFile(mainPicture),0);

                                //     System.out.println(a);

                                multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                                multimedia.setArchivo(a);
                                multimedia.setNombre_archivo("fotoTrasera");
                                multimedia.setExtension("JPEG");
                                multimedia.setTamano(mainPicture.getTotalSpace() + "");

                               enviarMultimedia(multimedia);
                            } catch (Exception error)
                            {
                                System.out.println("La imagen no pudo ser guardada");
                            }
                            camera.release();
                            frontalFoto();
                        }
                    });
                }
            } catch (Exception e) {
                if (camera != null) {
                    camera.release();
                }

            }
        }
    }

    private void frontalFoto() {

        Camera camera = null;
        int cameraCount = 1;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            SystemClock.sleep(1000);

            Camera.getCameraInfo(camIdx, cameraInfo);

            try {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                Camera.Parameters parameters = camera.getParameters();
                parameters.set("jpeg-quality", 70);
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.setPictureSize(640, 480);
                camera.setParameters(parameters);

            } catch (RuntimeException e) {
                System.out.println("Camera no disponible: " + camIdx);
                camera = null;
                //e.printStackTrace();
            }
            try {
                if (null == camera) {
                    System.out.println("No se puede obtener una instancia de la camara");
                } else {
                    try {
                        camera.setPreviewTexture(new SurfaceTexture(0));
                        camera.startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    camera.takePicture(null, null, new Camera.PictureCallback() {

                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            File pictureFileDir = getFilesDir();
                            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                                return;
                            }
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                            String date = dateFormat.format(new Date());
                            String photoFile = "/FotografiaFrontal_" + "_" + date + ".JPEG";
                            mFileName2= Environment.getExternalStorageDirectory().getAbsolutePath()+photoFile;
                            File mainPicture = new File(mFileName2);
                          //  MMS(mainPicture);

                            // addImageFile(mainPicture);

                            try {
                                FileOutputStream fos = new FileOutputStream(mainPicture);
                                fos.write(data);
                                fos.close();
                                System.out.println("Imagen guardada");
                                String a=  Base64.encodeToString(Utils.loadFile(mainPicture),0);

                                //     System.out.println(a);

                                multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                                multimedia.setArchivo(a);
                                multimedia.setNombre_archivo("fotoFrontal");
                                multimedia.setExtension("jpg");
                                multimedia.setTamano(mainPicture.getTotalSpace() + "");

                                enviarMultimedia(multimedia);
                            } catch (Exception error) {
                                System.out.println("La imagen no pudo ser guardada");
                            }
                            camera.release();
                        }
                    });
                }
            } catch (Exception e) {
                if (camera != null) {
                    camera.release();
                }
            }
        }}

    public void MMS(File file) {



        Uri attached_Uri=Uri.fromFile(file);
        Intent mmsIntent = new Intent( Intent.ACTION_SEND, attached_Uri);
        mmsIntent.setType("image/*");
        mmsIntent.putExtra("sms_body", "Please see the attached image");
        mmsIntent.putExtra("address","555555;444455;66666");

       mmsIntent.putExtra(Intent.EXTRA_STREAM,attached_Uri);
        mmsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mmsIntent);

    }

    public void enviarMultimedia(Multimedia multimedia)
    {
        MultimediaAsync multimediaAsync= new MultimediaAsync(this);
        multimediaAsync.execute(multimedia);

    }



    }
