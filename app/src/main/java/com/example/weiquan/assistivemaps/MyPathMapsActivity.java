package com.example.weiquan.assistivemaps;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MyPathMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static PolylineOptions options = new PolylineOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_path_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String targetPath = getIntent().getStringExtra("targetPath");
        int target = Integer.parseInt(targetPath);
        if (RecordPath.recordedPath.get(target).size() >= 2){
            for ( Location locRecorded : RecordPath.recordedPath.get(target))
            {
                options.add( new LatLng( Math.round(locRecorded.getLatitude() * 100000.0) / 100000.0,
                        Math.round(locRecorded.getLongitude() * 100000.0) / 100000.0));
            }

            mMap.addPolyline(options);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
    }
}
