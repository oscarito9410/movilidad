package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.MultimediaAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Multimedia;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;

import java.io.File;
import java.io.IOException;

public class capture_video extends Activity implements SurfaceHolder.Callback2 {

    MediaRecorder mRecorder;
    private static String mFileName = null;
    SurfaceHolder holder;
    int valor;
    Handler handler1;
    Runnable runnable1;
    Multimedia multimedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);


        multimedia= new Multimedia();

        handler1= new Handler();
        runnable1= new Runnable() {
            @Override
            public void run() {

                valor++;

                if(valor==1)
                {
                    mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                    mFileName += "/videoprueba.mp4";

                    Log.d("inicia", "inicia grabacion1");

                    contenido();


                }
                if (valor==2)
                {
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder=null;

                    File file= new File(mFileName);
                   // MMS(file);

                    String a= null;
                    try {
                        a = Base64.encodeToString(Utils.loadFile(file), 0);

                        multimedia.setId_numero_empleado(getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.SP_ID, ""));
                        multimedia.setArchivo(a);
                        multimedia.setNombre_archivo("video");
                        multimedia.setExtension("mp4");
                        multimedia.setTamano(file.getTotalSpace() + "");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    enviarMultimedia(multimedia);

                    finish();


                }


                handler1.postDelayed(runnable1,30000);
            }
        };
        handler1.post(runnable1);



    }


    private void   contenido()
    {

        mRecorder = new MediaRecorder();
        init();

        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.videocam);

        Button boton=(Button)findViewById(R.id.parar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


    }



    private void prepareRecorder() {
        mRecorder.setPreviewDisplay(holder.getSurface());

        try {

            mRecorder.prepare();
            mRecorder.start();



        } catch (IllegalStateException e)
        {
            e.printStackTrace();
            finish();
        } catch (IOException e)
        {
            e.printStackTrace();
            finish();
        }

       // finish();
    }


    private void init(){
        mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_LOW);

        mRecorder.setProfile(cpHigh);
        mRecorder.setOutputFile(mFileName);

        mRecorder.setMaxDuration(30000); // 10 seconds
        mRecorder.setMaxFileSize(5000000); // Approximately 5 megabytes
////////////////////////////////////////////////////

    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

        prepareRecorder();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        prepareRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        prepareRecorder();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    public void MMS(File file)
    {

        Uri attached_Uri=Uri.fromFile(file);
        Intent mmsIntent = new Intent( Intent.ACTION_SEND, attached_Uri);
        mmsIntent.putExtra("sms_body", "Please see the attached image");
        mmsIntent.putExtra("address","555555;444455;66666");

        mmsIntent.putExtra(Intent.EXTRA_STREAM,attached_Uri);
        mmsIntent.setType("video/mp4");
        mmsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mmsIntent);
    }



    public void enviarMultimedia(Multimedia multimedia)
    {
        MultimediaAsync multimediaAsync= new MultimediaAsync(this);
        multimediaAsync.execute(multimedia);

    }
}
