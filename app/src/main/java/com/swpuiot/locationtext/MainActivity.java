package com.swpuiot.locationtext;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {
    private TextView mylocation;
    private TextView mylocation2;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mylocation = (TextView) findViewById(R.id.text_mylocation);
        mylocation2 = (TextView) findViewById(R.id.text_mylocation2);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(MainActivity.this, "no Location provider to use",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        Log.d("Main", "location = " + ((location == null) ? "null" : location.getLatitude()));
        if (location != null) {
            //显示位置
            showLocations(location);

        }
        locationManager.requestLocationUpdates(provider, 500, 0, locationListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //关闭程序时将监听器移除
            locationManager.removeUpdates(locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新当前位置
            showLocations(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showLocations(Location location) {
        String currentposition = "latitude is" + location.getLatitude();
        String currentposition2 = "longitude is" + location.getLongitude();
        mylocation.setText(currentposition);
        mylocation2.setText(currentposition2);
    }
}
