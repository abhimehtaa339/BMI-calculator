package com.weather.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    ImageView backbutton;
    ListView list;



    ArrayList<String> sett = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backbutton = findViewById(R.id.back_button);
        list = findViewById(R.id.list);


        sett.add("   Feedback                            ");
        sett.add("   Rate us                             ");



        ArrayAdapter<String> adapt = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , sett);
        list.setAdapter(adapt);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            String recipient = "abhimehtaa339@gmail.com";
            String Subject = "BMI APP FEEDBACK";


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    if(position == 0) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO  );
                        intent.setData(Uri.parse(  "mailto:" + recipient));
                        intent.putExtra(Intent.EXTRA_SUBJECT, Subject);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "No email app found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (position == 1){
                        Toast.makeText(Settings.this, "Coming soon to Playstore.....", Toast.LENGTH_SHORT).show();
                    }



            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(Settings.this , MainActivity.class);
                startActivity(back);
            }
        });
    }
}