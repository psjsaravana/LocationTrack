package com.sri.locationteller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    LocationManager locationManager;
    String number;
    Context appContext;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String messageToSend = "test message";
        appContext=context;
        Bundle bundle = intent.getExtras();
        number= bundle.getString("mobileNo");
        Log.i("saravana...", "saravana" + number);
       // SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0, 0, new MyLocationListener());
        try {
            showLocation(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showLocation(Context context) throws IOException {
        Location location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//here getting null.

        if (location != null) {
            //String message = String.format("Current Location: \n Longitude: %1$s \n Latitude: %2$s",location.getLongitude(), location.getLatitude());
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            getAddress(location,context);
        }
        else
        {
            Toast.makeText(context, "Location Not Found,Ensure Your GPS is working.", Toast.LENGTH_LONG).show();
        }
    }

    public void getAddress(Location location,Context mContext) throws IOException {
        if (location == null) {
            //return new Address(null);
        }
        Geocoder gc=new Geocoder(mContext);
        Address address=null;
        List<Address> addresses=gc.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        if (addresses.size() > 0) {
            address=addresses.get(0);
            String addressString =addresses.get(0).getFeatureName()+"-"+addresses.get(0).getLocality()+"-"+addresses.get(0).getAdminArea()+"-"+addresses.get(0).getCountryName();
            sendMessage(addressString);
        }

    }

    public  void sendMessage(String addressLocation)
    {
        String locationMessage="Hi,I am Currently in "+addressLocation;
        SmsManager.getDefault().sendTextMessage(number, null, locationMessage, null,null);
        Toast.makeText(appContext, "Location Found : "+addressLocation, Toast.LENGTH_LONG).show();
        Log.i("Saravana....","Saravana Address..."+addressLocation);
    }
}

