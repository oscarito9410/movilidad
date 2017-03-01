package com.gruposalinas.elektra.movilidadgs.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.alertar.Alertas;
import com.gruposalinas.elektra.movilidadgs.async.CheckEmployeesAsync;
import com.gruposalinas.elektra.movilidadgs.async.RegisterEmployeeAsync;
import com.gruposalinas.elektra.movilidadgs.async.ValidarContrasenaAsync;
import com.gruposalinas.elektra.movilidadgs.async.escribir_LogAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.beans.Singleton;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * @author Eduardo WaringLong range = (long) getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).getLong(Constants.SP_RANGE, 500);
Log.i(TAG, "LONG = " + range);
 *
 */
public class LoginActivity extends Activity {
    private static final String TAG = "LOGIN_ACTIVITY";
    private EditText empleadoEditText,empleadocontrasea;
    private TextView registrarImage;
    private TextView loginText;
    private DBHelper mDBHelper;
    private RelativeLayout progressBar;
    AlertDialog.Builder alert;
    String respuesta;
    Alertas alertas;
    LinearLayout regresar;

    //Typeface myriadRegularTypeFace = Typeface.createFromAsset(getAssets(), "fonts/myriadproregular.ttf");
    //Typeface myriadBoldTypeFace = Typeface.createFromAsset(getAssets(), "fonts/myriadprobold.ttf");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        alertas = new Alertas(this);
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor edit = prefs.edit();
        edit.putString(Constants.SP_HASLOGGED, Constants.SP_NOTLOGGED);
        edit.commit();

        empleadoEditText 	= (EditText)findViewById(R.id.login_empleado_edittext);
        empleadocontrasea=(EditText)findViewById(R.id.login_llave_edittext);
        registrarImage 		= (TextView)findViewById(R.id.login_registrar_button);
        loginText 			= (TextView)findViewById(R.id.login_sesion_button);
        progressBar 		= (RelativeLayout) findViewById(R.id.login_progressbar);
        regresar            =(LinearLayout) findViewById(R.id.regresar);


        //loginText.setTypeface(myriadTypeFace);
        //registrarText.setTypeface(myriadTypeFace);

        loginText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                hideKeyBoard();

                Empleado empleado = new Empleado();
                checkEmpleado(empleado);
            }
        });

        registrarImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                hideKeyBoard();
                goToRegistroActivity();
            }
        });

        regresar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  new Intent(getApplicationContext(),MenuAplicaciones.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkEmpleado(Empleado empleado){
        //Indica si es válido el número de empleado, hacer async y mas cosas
        if(empleadoEditText.getText().toString() == null || empleadoEditText.getText().toString().equals("")){
            //TODO errorText.setVisibility(View.VISIBLE);

          //  Toast.makeText(getApplicationContext(), "¡Debes escribir tu número de empleado!", Toast.LENGTH_LONG).show();
           // alertaError("Debes escribir tu número de empleado");
            alertas.alertas("Debes escribir tu número de empleado",this);
        }
        if(empleadocontrasea.getText().toString().trim().equals("")){
            //alertaError("Debes escribir tu contraseña");
            alertas.alertas("Debes escribir tu contraseña",this);
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            Log.i(TAG, "TEST inicia ws");
            empleado.setIdEmployee((empleadoEditText.getText().toString()));
            String a=empleadocontrasea.getText().toString();

           // metodo que valida contrasena
 //checar(a, empleado);
           //  CheckEmployeesAsync checkEmployeesAsync = new CheckEmployeesAsync(this);
         //checkEmployeesAsync.execute(empleado);

            RegisterEmployeeAsync registerEmployeeAsync = new RegisterEmployeeAsync(this);
            registerEmployeeAsync.execute(empleado);



            Log.i(TAG, "TEST fin ws");
        }
    }

    public boolean checar(String contrasena,Empleado empleado){

        ValidarContrasenaAsync validarContrasenaAsync=new ValidarContrasenaAsync(contrasena,this);
        validarContrasenaAsync.execute(empleado);

        return true;
    }

    private void goToRegistroActivity(){
        //Manda los datos puestos en el Spinner y en la caja de texto en caso de ser llenados
        Intent intent = new Intent(this, RegistroActivity.class);
        intent.putExtra("idEmpleado", empleadoEditText.getText().toString());
        //intent.putExtra("llave", puestoSpinner.getSelectedItemPosition());
        startActivity(intent);
    }
    // metodo que permite logearse despues de validar contraseña
    public void Login(Empleado empleado){
        if(empleado.isSuccess())
        {
            CheckEmployeesAsync checkEmployeesAsync = new CheckEmployeesAsync(this);
            checkEmployeesAsync.execute(empleado);

        }
        else{

            progressBar.setVisibility(View.GONE);
           // Toast.makeText(getApplicationContext(),empleado.getMensajeError(),Toast.LENGTH_LONG).show();
            //alertaError(empleado.getMensajeError());
            alertas.alertas(empleado.getMensajeError(),this);

        }
        // ebviar
        empleado.getRESPUESTA();
        String a=empleado.getRESPUESTA();
        System.out.print(empleado.getRESPUESTA());
        respuesta= "respuesta1 :"+a+"";


    }

    public void goToMainActivity(Empleado empleado){
        Log.i(TAG, "TEST goToMainActivity");
        progressBar.setVisibility(View.GONE);
        respuesta=respuesta+" respuesta2 "+empleado.getRESPUESTA();
        if(empleado.getName() == null || empleado.getName().equals(Constants.ERROR)){
            String mensajeError = "";
            if(empleado.getMensajeError() == null || empleado.getMensajeError().trim().equals(""))
                mensajeError = getString(R.string.fallaservidor);
            else
                mensajeError = empleado.getMensajeError();

           // alertaError(mensajeError);
            alertas.alertas(mensajeError,this);
        }
        else{

            Singleton singleton= new Singleton();
            singleton.setNumero_empleado(empleado.getIdEmployee());
            singleton.setLog(respuesta);
            escribir_LogAsync escribirLogAsync= new escribir_LogAsync(this,null);
            escribirLogAsync.execute(singleton);
            saveInDatabase(empleado);
            // enviar peticion de log
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }

    }

    public void saveInDatabase(Empleado empleado){
        //Primero guardamos en Shared Preferences
        SharedPreferences prefs =getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Editor edit = prefs.edit();

        edit.putString(Constants.SP_NAME, empleado.getName());
        edit.putString(Constants.SP_ENTERPRISE, empleado.getEnterprise());
        edit.putInt(Constants.SP_POSITION, empleado.getPosition());
        edit.putString(Constants.SP_HASLOGGED, Constants.SP_LOGGED);
        edit.putString(Constants.SP_ID, empleado.getIdEmployee());
        edit.commit();
        Log.i(TAG, "Empleado = " + empleado.getName());

        //Después se procede a guardar en base de datos
        Dao dao;
        try {
            dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getEmpleadoDAO();
            dao.create(empleado);
            Log.i(TAG, "Usuario creado exitosamente");
        } catch (SQLException e) {
            Log.e(TAG, "Error creando usuario");
            try{
                dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getEmpleadoDAO();
                dao.update(empleado);
            } catch(Exception ex){
                Log.e(TAG, "Error actualizando usuario");
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }

    private void hideKeyBoard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    @Override
    public void onBackPressed() {

        finish();
    }
}
