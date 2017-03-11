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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import static com.example.weiquan.assistivemaps.R.id.map;

public class FindPaths extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    private static final int REQUEST_READ_PERMISSION = 123;
    private GoogleApiClient mGoogleApiClient;
    private Location curLocation = new Location("");
    private boolean reached = false;


    private static PolylineOptions thePath = new PolylineOptions();
    private double[][] hope = {{1.340098,103.962367},{1.340106,103.962305}, {1.340115,103.962226}, {1.340125, 103.962164}, {1.340133, 103.962086}, {1.340170, 103.962036}, {1.340242, 103.962011}, {1.340312, 103.962000}, {1.340332, 103.962081}, {1.340348, 103.962148}, {1.340362, 103.962208}};
    private ArrayList<ArrayList<Double>> rawPath;
    private ArrayList<Location> pathArray = new ArrayList<Location>();
    private ArrayList<Location> pathArrayMe = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_paths);
        for (double[] array: hope) {
            Location temp = new Location("");
            temp.setLatitude(array[0]);
            temp.setLongitude(array[1]);
            pathArray.add(temp);
            pathArrayMe.add(temp);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    public void pathFollower() {
        mMap.clear();
        thePath = new PolylineOptions();

        thePath.color( Color.parseColor( "#CC0000FF" ) );
        thePath.width( 5 );
        thePath.visible( true );

        for ( Location locRecorded : pathArray )
        {
            thePath.add( new LatLng( Math.round(locRecorded.getLatitude() * 100000.0) / 100000.0,
                    Math.round(locRecorded.getLongitude() * 100000.0) / 100000.0));
        }
        mMap.addPolyline(thePath);
    }

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
        mGoogleApiClient.connect();
//        if(reached) {
//            Toast.makeText(getApplicationContext(),"Destination reached!",Toast.LENGTH_LONG).show();
//            mGoogleApiClient.disconnect();
//        }
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
        Toast.makeText(this, "changed",Toast.LENGTH_SHORT).show();
        if (location == null){
            Toast.makeText(this, "Can't get location",Toast.LENGTH_SHORT).show();
        }
        else{
            if(pathArray.size() > 1){
                if (location.distanceTo(pathArray.get(0)) > location.distanceTo(pathArray.get(1))) {
                    pathArray.remove(0);
                    pathArrayMe.remove(0);
                    pathFollower();
                }}
            else{
                if(!reached){
                    reached = true;
                    Toast.makeText(getApplicationContext(),"Destination reached!",Toast.LENGTH_LONG).show();
                    mGoogleApiClient.disconnect();
                }
            }
            pathFollower();
            mMap.addMarker(new MarkerOptions().position(new LatLng(
                    location.getLatitude(), location.getLongitude())));
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,20);
            mMap.animateCamera(update);
            curLocation = location;
        }
    }
}
