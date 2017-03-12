package com.example.weiquan.assistivemaps;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements DetailsInterface {
    private DrawerLayout mDrawerLayout;
    public static List<Destination> dest_list;
    public static ArrayList<Destination> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        // creating navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_reorder_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.find_paths:
                                goToSearchPaths(menuItem.getActionView());
                                break;
                            case R.id.my_paths:
                                goToMyPaths(menuItem.getActionView());
                                break;
                            case R.id.add_path:
                                goToRecordPath(menuItem.getActionView());
                                break;
                            case R.id.maps:
                                openMapsActivity(menuItem.getActionView());
                                break;
                            default:
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void showDestDetails(View view,String destName, Integer destPhoto){
        Intent intent = new Intent(this, Dest_details.class);
        intent.putExtra("name", destName);
        intent.putExtra("img", destPhoto);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                this, view.findViewById(R.id.dest_photo), "img_transition");
        startActivity(intent, options.toBundle());

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    public void openMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    // creating view
    @Override
    public void onStart() {
        super.onStart();

        //creating recycling view
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        initializeData();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(dest_list,this);
        rv.setAdapter(adapter);
    }


//  Navigation Drawer ===================================================================
    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void goToRecordPath(View v){
        Intent i = new Intent(HomeScreen.this,RecordPath.class);
        startActivity(i);
    }

    public void goToSearchPaths(View v){
        Intent i = new Intent(HomeScreen.this,SearchPaths.class);
        startActivity(i);
    }

    public void goToMyPaths(View v){
        Intent i = new Intent(HomeScreen.this,MyPaths.class);
        startActivity(i);
    }

//===============================================================================


// Data ===============================================================================
    public static class Destination {
        String name;
        String id;
        String weblink;
        String tripadvisor;
        String description;
        String operatingHours;


        int photoId;

        Destination(String name, int photoId,String weblink, String description , String operatingHours ,String tripadvisor) {
            this.name = name;
            this.photoId = photoId;
            this.id = name.substring(0,1);
            this.weblink = weblink ;
            this.tripadvisor = tripadvisor ;
            this.description = description;
            this.operatingHours = operatingHours;
        }
    }

    private void initializeData() {
        // Initialize only once
        dest_list = new ArrayList<>();
        dest_list.add(new Destination("Singapore Flyer", R.drawable.c, getString(R.string.SF_weblink), getString(R.string.SF_des), getString(R.string.SF_opert), getString(R.string.SF_trip)));
        dest_list.add(new Destination("Buddha Tooth Relic Temple", R.drawable.bt, getString(R.string.BTRT_weblink), getString(R.string.BTRT_des), getString(R.string.BTRT_opert), getString(R.string.BTRT_trip)));
        dest_list.add(new Destination("Vivo City", R.drawable.a, getString(R.string.VC_weblink), getString(R.string.VC_des), getString(R.string.VC_opert), getString(R.string.VC_trip)));
        dest_list.add(new Destination("Resort World Sentosa", R.drawable.rws, getString(R.string.RWS_weblink), getString(R.string.RWS_des), getString(R.string.RWS_opert), getString(R.string.RWS_trip)));
        dest_list.add(new Destination("Zoo", R.drawable.zoo, getString(R.string.Z_weblink), getString(R.string.Z_des), getString(R.string.Z_opert), getString(R.string.Z_trip)));
        data_list = new ArrayList<>(dest_list);
    }

}
