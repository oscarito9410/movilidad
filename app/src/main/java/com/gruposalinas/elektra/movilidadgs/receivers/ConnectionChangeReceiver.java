package com.gruposalinas.elektra.movilidadgs.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public abstract class ConnectionChangeReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent){
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetInfo 	= connectivityManager.getActiveNetworkInfo();
		NetworkInfo mobNetInfso 		= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if(manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()){
			onNewState(1);
		}
		else{
			if(manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting()){
				onNewState(2);
			}
			else{
				onNewState(0);
			}
		}
    }

	protected abstract void onNewState(int opc);
}