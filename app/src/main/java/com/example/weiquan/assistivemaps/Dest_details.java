package com.example.weiquan.assistivemaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by weiqu on 3/11/2017.
 */

public class Dest_details extends AppCompatActivity {

    TextView title;
    TextView descriptiontext;
    ImageView imgview;

    String weblink ;
    String descript ;
    String opert ;
    String trip ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dest_details);

        title = (TextView) this.findViewById(R.id.detail_titlename);
        descriptiontext = (TextView) this.findViewById(R.id.description);

        imgview = (ImageView) this.findViewById(R.id.dest_photo);


        Intent i = getIntent();
        String name = i.getStringExtra("name");
        int img = i.getIntExtra("img", 0 );

        for(HomeScreen.Destination d : HomeScreen.data_list){
            if(d.name.equals(name)){
                weblink = d.weblink;
                descript = d.description;
                opert = d.operatingHours;
                trip = d.tripadvisor;
            }
        }

        title.setText(name);
        imgview.setImageResource(img);
        descriptiontext.setText(descript);

        final ImageButton button = (ImageButton) findViewById(R.id.button_link);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(weblink); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        final ImageButton button2 = (ImageButton) findViewById(R.id.button_link2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(trip); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        final ImageButton button3 = (ImageButton) findViewById(R.id.button_link3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Dest_details.this).create();
                alertDialog.setTitle("Operating Hours");
                alertDialog.setMessage(opert);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }

        });

    }

}
