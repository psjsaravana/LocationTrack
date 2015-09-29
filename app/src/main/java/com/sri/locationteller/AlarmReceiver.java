package com.sri.locationteller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String messageToSend = "test message";
        Bundle bundle = intent.getExtras();
        String number= bundle.getString("mobileNo");
        Log.i("saravana...", "saravana" + number);
        SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);


    }



}

