/**
 * 
 */
package com.gruposalinas.elektra.movilidadgs.Activities;

import java.sql.SQLException;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.async.CheckEmployeesAsync;
import com.gruposalinas.elektra.movilidadgs.async.RegisterEmployeeAsync;
import com.gruposalinas.elektra.movilidadgs.async.ValidarContrasenaAsync;
import com.gruposalinas.elektra.movilidadgs.beans.Empleado;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Eduardo Waring
 *
 */
public class RegistroActivity extends Activity {
	public static final String TAG = "REGISTRO_ACTIVITY";
	
	private EditText	empleadoEditText,contrasenia;
	private EditText 	telefonoEditText;
	private TextView 	registrarText;
	private DBHelper    mDBHelper;
	Empleado empleado1;
	private LinearLayout regresar;
	private RelativeLayout progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		empleadoEditText 	= (EditText)findViewById(R.id.registro_empleado_edittext);
		telefonoEditText 	= (EditText)findViewById(R.id.registro_telefono_edittext);
		registrarText 		= (TextView)findViewById(R.id.registro_registrar_button);
		progressBar 		= (RelativeLayout) findViewById(R.id.registro_progressbar);
		contrasenia         =(EditText)findViewById(R.id.contrasenia);
		regresar           = (LinearLayout)findViewById(R.id.regresar);

		registrarText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int t=telefonoEditText.length();

				if(empleadoEditText.getText().toString().trim().equals("")){
					//Toast.makeText(getApplicationContext(), "¡Debe escribir su número de empleado!", Toast.LENGTH_LONG).show();
					alertaError("Debe escribir su número de empleado");
				}
				if(contrasenia.getText().toString().trim().equals("")){
					alertaError("Debes escribir tu contraseña.");
				}
				else if(telefonoEditText.getText().toString().trim().equals("")){
					//Toast.makeText(getApplicationContext(), "¡Debe escribir su número telefónico!", Toast.LENGTH_LONG).show();
					alertaError("Debe escribir su número telefónico");
				}
				else if(t<10)
				{
					alertaError("Debes tener un número de al menos 10 digitos");

				}
				else{

					registerEmployee();
				}

			}
		});

		regresar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),MenuAplicaciones.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});


	}
	
	private void registerEmployee(){
		progressBar.setVisibility(View.VISIBLE);
		Empleado empleado = new Empleado();
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
		empleado.setIdEmployee((empleadoEditText.getText().toString()));
		empleado.setPhoneNumber(telefonoEditText.getText().toString());
		String a=contrasenia.getText().toString();
		empleado.setRESPUESTA(a);
		
		if (telephonyManager.getDeviceId() != null){
			empleado.setImei(telephonyManager.getDeviceId()); 
	    }else{
	    	empleado.setImei(Secure.getString(getApplicationContext().getContentResolver(), Secure.ANDROID_ID)); 
	    }
		// metodo que permite validar contraseña
		//checar(a, empleado);// ya no se usa


/*
		RegisterEmployeeAsync registerEmployeeAsync = new RegisterEmployeeAsync(this);
	    registerEmployeeAsync.execute(empleado);*/

		llamada(empleado);

	}
	public boolean checar(String contrasena,Empleado empleado){

		ValidarContrasenaAsync validarContrasenaAsync=new ValidarContrasenaAsync(contrasena,this);
		validarContrasenaAsync.execute(empleado);

		return true;
	}
	// metodo que permite logearse despues de validar contraseña
	public void Login(Empleado empleado){
		empleado1= new Empleado();
		empleado1=empleado;
		if(empleado.isSuccess())
		{

			RegisterEmployeeAsync registerEmployeeAsync = new RegisterEmployeeAsync(this);
			registerEmployeeAsync.execute(empleado);

		}
		else{
			progressBar.setVisibility(View.GONE);
		//	Toast.makeText(getApplicationContext(),empleado.getMensajeError(),Toast.LENGTH_LONG).show();
			alertaError(empleado.getMensajeError());
					}

		String a=empleado.getRESPUESTA();
		System.out.print(a);




	}
	public void goToLoginActivity(Empleado result){
		progressBar.setVisibility(View.GONE);
		if(result.isSuccess())
		{
			SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
			Editor edit = prefs.edit();
			edit.putString(Constants.SP_HASLOGGED, Constants.SP_LOGGED);
			edit.putString(Constants.SP_NAME, result.getName());
			edit.putString(Constants.SP_ENTERPRISE, result.getEnterprise());
			edit.putInt(Constants.SP_POSITION, result.getPosition());
			edit.putString(Constants.SP_HASLOGGED, Constants.SP_LOGGED);
			edit.putString(Constants.SP_ID, result.getIdEmployee());
			edit.commit();
			
			//Después se procede a guardar en base de datos
			Dao dao;
			try {
			    dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getEmpleadoDAO();
			    dao.create(result);
			    Log.i(TAG, "Usuario creado exitosamente");
			} catch (SQLException e) {
			    Log.e(TAG, "Error creando usuario");
			    
			    try{
			    	dao = DBHelper.getHelper(getApplicationContext(), mDBHelper).getEmpleadoDAO();
			    	dao.update(result);
			    } catch(Exception ex){
			    	Log.e(TAG, "Error actualizando usuario");
			    }
			}
			
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.finish();
		}
		else{
			String mensajeError = "";
			if(result.getMensajeError() == null || result.getMensajeError().trim().equals(""))
				mensajeError = "Verifique su conexión";
			else
				mensajeError = result.getMensajeError();
			
		//	Toast.makeText(getApplicationContext(),  mensajeError, Toast.LENGTH_LONG).show();
			alertaError1(mensajeError,result);
		}

		String a=result.getRESPUESTA();
		System.out.print(a);
	}
	
	protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }

	public void alertaError(final String error)
	{
		progressBar.setVisibility(View.GONE);

		final Dialog alert = new Dialog(RegistroActivity.this,R.style.Theme_Dialog_Translucent);
		LayoutInflater inflater1=getLayoutInflater();
		final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
		TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
		LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
		TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
		error1.setText(error);
		alert.setContentView(dialogo);
		if (error.equals("El usuario ya ha sido registrado anteriormente."))
		{
			titulodialo.setText("Aviso:");
		}
		confirmar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (error.equals("El usuario ya ha sido registrado anteriormente."))
				{
					llamada(empleado1);
				}
				alert.dismiss();
			}
		});
		alert.show();

	}


	public void alertaError1(final String error, final Empleado empleado)
	{
		progressBar.setVisibility(View.GONE);
		final Dialog alert = new Dialog(RegistroActivity.this,R.style.Theme_Dialog_Translucent);
		LayoutInflater inflater1=getLayoutInflater();
		final View dialogo=inflater1.inflate(R.layout.alerta_error, null);
		TextView titulodialo=(TextView)dialogo.findViewById(R.id.tituloDialogo);
		LinearLayout confirmar=(LinearLayout)dialogo.findViewById(R.id.boton_confirmar);
		TextView error1=(TextView)dialogo.findViewById(R.id.mensajerrror);
		error1.setText(error);
		alert.setContentView(dialogo);
		confirmar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (error.equals("El usuario ya ha sido registrado anteriormente."))
				{
					llamada(empleado);
				}
				alert.dismiss();
			}
		});
		alert.show();

	}


	@Override
	public void onBackPressed()
	{
		finish();

	}

	public void llamada(Empleado empleado)
	{
		CheckEmployeesAsync checkEmployeesAsync = new CheckEmployeesAsync(this);
		checkEmployeesAsync.execute(empleado);

	}

	public  void session(Empleado empleado)
	{
		if(empleado.isSuccess())
		{
			saveInDatabase(empleado);
			// enviar peticion de log
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.finish();

		}
		else {
			alertaError(empleado.getMensajeError());
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

}