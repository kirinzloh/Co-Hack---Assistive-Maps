package com.example.weiquan.assistivemaps;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import static com.example.weiquan.assistivemaps.R.id.map;

public class RecordPath extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private boolean started = false;
    private GoogleMap mMap;
    private static ArrayList<Location> listLocsToDraw = new ArrayList<Location>();
    private static PolylineOptions options = new PolylineOptions();
    private static final int REQUEST_READ_PERMISSION = 123;
    private DataManager dm = new DataManager();
    private static double[] coordinates = {0, 0};
    private GoogleApiClient mGoogleApiClient;
    private static boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_path);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        Button startRecording = (Button) findViewById(R.id.startPath);
        Button stopRecording = (Button) findViewById(R.id.stopPath);
        startRecording.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!isRecording){
                    Toast.makeText(getApplicationContext(), "Start Recording",
                            Toast.LENGTH_LONG).show();
                    mGoogleApiClient.connect();
                    isRecording = true;
                    listLocsToDraw = new ArrayList<Location>();
                }
            }
        });
        stopRecording.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isRecording){
                    Toast.makeText(getApplicationContext(), "Stop Recording",
                            Toast.LENGTH_LONG).show();
                    mGoogleApiClient.disconnect();
                    isRecording = false;
                    started = false;}
            }
        });

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!(listLocsToDraw.size() < 2))
                                {options = new PolylineOptions();

                                    options.color( Color.parseColor( "#CC0000FF" ) );
                                    options.width( 5 );
                                    options.visible( true );

                                    for ( Location locRecorded : listLocsToDraw )
                                    {
                                        options.add( new LatLng( Math.round(locRecorded.getLatitude() * 100000.0) / 100000.0,
                                                Math.round(locRecorded.getLongitude() * 100000.0) / 100000.0));
                                    }

                                    mMap.addPolyline(options);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
//    public boolean googleServicesAvailable(){
//        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
//        int isAvailable = api.isGooglePlayServicesAvailable(this);
//        if(isAvailable == ConnectionResult.SUCCESS){
//            return true;
//        }
//        else if (api.isUserResolvableError(isAvailable)){
//            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
//            dialog.show();
//        }
//        else{
//            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng singapore = new LatLng(1.370606, 103.804709);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(singapore)      // Sets the center of the map to location user
                .zoom(10.4f)
                .bearing(0)
                .tilt(0)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_READ_PERMISSION);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null){
            Toast.makeText(this, "Can't get location",Toast.LENGTH_SHORT).show();
        }
        else{
            if(!started && listLocsToDraw.size() == 1){
                listLocsToDraw.remove(0);
                started = true;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                coordinates[0] = Math.round(location.getLongitude() * 100000.0) / 100000.0;
                coordinates[1] = Math.round(location.getLatitude() * 100000.0) / 100000.0;
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,20);
                listLocsToDraw.add(location);
                mMap.animateCamera(update);
            }
            else if(!started && listLocsToDraw.size() == 0){
                Toast.makeText(getApplicationContext(),""+location.getLatitude()+"\n"+location.getLongitude(),Toast.LENGTH_SHORT).show();
                listLocsToDraw.add(location);
            }
            else if (listLocsToDraw.size() >= 1 && started){
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                if(listLocsToDraw.get(listLocsToDraw.size()-1).distanceTo(location) > 1 && listLocsToDraw.get(listLocsToDraw.size()-1).distanceTo(location) < 2){
                    coordinates[0] = Math.round(location.getLongitude() * 100000.0) / 100000.0;
                    coordinates[1] = Math.round(location.getLatitude() * 100000.0) / 100000.0;
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,20);
                    listLocsToDraw.add(location);
                    mMap.animateCamera(update);
                }
            }
        }
    }
}
