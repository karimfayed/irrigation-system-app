package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class Valves extends AppCompatActivity {

    private ImageView lm,rm;
    private ImageButton ib;
    private int v =0;
    private String ans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valves);
        lm = findViewById(R.id.left_icon);
        rm = findViewById(R.id.right_icon);
        ib = findViewById(R.id.valve);
        Intent i  = getIntent();
        String name = i.getStringExtra("name");
        Toast.makeText(this, "Press on button to open and close the valve",Toast.LENGTH_LONG).show();
        lm.setOnClickListener(e->{
            Intent in = new Intent(this,MainActivity2.class);
            in.putExtra("name",name);
            startActivity(in);
        });
        rm.setOnClickListener(e->{
            startActivity(new Intent(this, FullscreenActivity.class));
        });

        ib.setOnClickListener(e->{
            if(v==0)
            {
                Toast.makeText(this,"Opening Valve",Toast.LENGTH_LONG).show();
                v=1;
                Open process1 = new Open();
                process1.execute();

            }
            else
            {
                Toast.makeText(this,"Closing Valve",Toast.LENGTH_LONG).show();
                v=0;
                Close process1 = new Close();
                process1.execute();

            }

        });
    }

}