/**
 * 
 */
package com.gruposalinas.elektra.movilidadgs.Activities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gruposalinas.elektra.movilidadgs.R;
import com.gruposalinas.elektra.movilidadgs.adapters.RegistroAdapter;
import com.gruposalinas.elektra.movilidadgs.beans.RegistroGPS;
import com.gruposalinas.elektra.movilidadgs.ormlite.DBHelper;
import com.gruposalinas.elektra.movilidadgs.utils.Constants;
import com.gruposalinas.elektra.movilidadgs.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author esandovalv
 *
 */
public class MovementListActivity extends ListActivity {
	private static final String TAG = "MOVEMENT_LIST_ACTIVITY";
	private ListView listView;
	
        
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maptrack);
        
        listView = (ListView) findViewById(android.R.id.list);
     
        //Manda un log imprimiendo todos los datos en la BD
      	Dao dao;
      	DBHelper dbHelper = null;
      	List<RegistroGPS> registros = null;
      		   
      	try {
      		dao = DBHelper.getHelper(getApplicationContext(), dbHelper).getGpsDAO();
      	    registros = dao.queryForAll();
      	       
      	    Log.i(TAG, "Size = " + registros.size());
      	    if (registros.size() < 1) {
      	        Toast.makeText(this, "Ningún registro encontrado ", Toast.LENGTH_SHORT).show();
      	    } 
      	    else {
      	    	String result = "";
      	        for(int i = 0; i < registros.size(); i++){
      	        	if(i == 0){
      	        		result += "REG#" + i + " = " + registros.get(i).getFecha() + ":" + registros.get(i).getHora()
      	     			   	         + "/" + registros.get(i).getLatitud() + "||" + registros.get(i).getLongitud()
      	      			             + "->" + registros.get(i).isSuccess();
      	        	}
      	        	else{
      	        		result += "\nREG#" + i + " = " + registros.get(i).getFecha() + " " + registros.get(i).getHora()
      	     			   	         + "/" + registros.get(i).getLatitud() + "||" + registros.get(i).getLongitud()
      	      			             + "->" + registros.get(i).isSuccess();
      	        	}
      	        }
      	        
      	        Toast.makeText(this, result +" numero de elementos."+registros.size(), Toast.LENGTH_LONG).show();
      	    }
      	}catch(Exception e){
      		Log.e(TAG, "No se cargaron registros");
      	}
      	
      	ArrayList<RegistroGPS> registrosList = (ArrayList) registros;
    	Log.d(TAG, "Registro 0 = " + registrosList.get(0).getId());	   
      	
        // Sets the data behind this ListView
        Log.i(TAG, "Elementos = " + registros.size());
      //  listView.setAdapter(new RegistroAdapter(getApplicationContext(), R.layout.item_registro, registrosList));
     
        // Register a callback to be invoked when an item in this AdapterView
        // has been clicked
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> adapter, View view,
                    int position, long arg) {
            }
        });
		
       // setListAdapter(new RegistroAdapter(getApplicationContext(), R.layout.item_registro, registrosList));
        
        //MapFragment fragment = new MapFragment();
    	//this.getFragmentManager().beginTransaction().replace(R.id.map, fragment).commit();
    }
}
