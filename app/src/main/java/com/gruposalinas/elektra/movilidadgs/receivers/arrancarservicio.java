package com.gruposalinas.elektra.movilidadgs.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gruposalinas.elektra.movilidadgs.geolocation.EmployeeLocationService;
import com.gruposalinas.elektra.movilidadgs.geolocation.HoraExacta;

/**
 * Created by Adrian guzman on 11/03/2016.
 */
public class arrancarservicio extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
        {
            Intent pushIntent = new Intent(context, EmployeeLocationService.class);
            context.startService(pushIntent);
            Intent intent1=new Intent(context, HoraExacta.class);
            context.startService(intent1);
        }

    }
}
