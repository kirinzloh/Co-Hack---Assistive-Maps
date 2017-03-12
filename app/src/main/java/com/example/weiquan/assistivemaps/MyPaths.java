package com.example.weiquan.assistivemaps;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPaths extends AppCompatActivity {
    private ArrayList<ArrayList<Location>> recordedPath = RecordPath.recordedPath;
    private static int pathNumber;
    String target;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_paths);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
    }

    @Override
    public void onStart() {
        super.onStart();
        displayText();
    }

    public void displayText(){
        TextView infoTV = (TextView) findViewById(R.id.my_paths_info);
        LinearLayout ll = (LinearLayout)findViewById(R.id.content_my_paths);
        if (recordedPath.size()>0){
            infoTV.setText(R.string.empty);
            ll.removeView(infoTV);
            addPath();
        }
        else {
            infoTV.setText(R.string.search_paths_info);
        }
    }

    public void addPath(){
        pathNumber = 1;
        for (int i =0; i<recordedPath.size();i++){
            Button myButton = new Button(this);
            myButton.setText("Path " + pathNumber);
            target = Integer.toString(i);
            intent = new Intent(this, MyPathMapsActivity.class);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("targetPath", target);
                    startActivity(intent);
                }
            });
            pathNumber += 1;
            LinearLayout ll = (LinearLayout)findViewById(R.id.content_my_paths);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
        }

    }

    public void openMapsActivity(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
