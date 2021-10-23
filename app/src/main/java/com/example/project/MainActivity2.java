package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private ImageButton bt1,bt2;
    private ImageView lgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bt1 = findViewById(R.id.imageButton1);
        bt2 = findViewById(R.id.imageButton2);
        lgo = findViewById(R.id.exit);
         Intent i  = getIntent();
         String name = i.getStringExtra("name");



        bt1.setOnClickListener(e->{
            Intent in = new Intent(this,Schedule.class);
            in.putExtra("name",name);
            startActivity(in);

        });
        bt2.setOnClickListener(e->{
            Intent in = new Intent(this,Valves.class);
            in.putExtra("name",name);
            startActivity(in);
        });
        lgo.setOnClickListener(e->{

            startActivity(new Intent(this, FullscreenActivity.class));
        });

    }
}