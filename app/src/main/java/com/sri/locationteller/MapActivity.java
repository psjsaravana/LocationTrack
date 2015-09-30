package com.sri.locationteller;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity {
    GoogleMap map;
    String interval,phNo;
    PendingIntent pendingIntent;
    Intent alarmIntent;
    AlarmManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle bundle = getIntent().getExtras();
        interval=bundle.getString("interval");
        phNo=bundle.getString("phNo");
       // Log.i("saravana...","saravana"+phNo);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
        else
        {
            renderMap();
            startAlarm();
        }
    }

  /* public void  onStopHandle(View view)
    {
       // startActivity(new Intent(this,NotificationActivity.class));
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void renderMap()
    {
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_view)).getMap();
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setMyLocationEnabled(true);

        }
    }

    public void startAlarm()
    {
        alarmIntent = new Intent(MapActivity.this, AlarmReceiver.class);
        //alarmIntent.putExtra("phNo",phNo);
        alarmIntent.putExtra("mobileNo",phNo);
       // Log.i("saravana...","saravana"+phNo);
        pendingIntent = PendingIntent.getBroadcast(MapActivity.this, 0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

         manager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int i= Integer.parseInt(interval);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                (1000 * 60 * i) , pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

    }

    public void onStopHandle(View view)
    {
        manager.cancel(pendingIntent);
        this.finish();
    }
}
