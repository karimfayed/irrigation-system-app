package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimePik extends AppCompatActivity {

    private TextView timer1,timer2,ttl;
    private String ans;
    ImageView im1,im2;
    private int hr1,min1,hr2,min2,f1=0,f2=0;
    private String stTime,edTime;
    private Button bt;
    private FirebaseDatabase root;
    private DatabaseReference reference;
    String key;
    private long maxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pik);
        timer1 = findViewById(R.id.timer_1);
        timer2 = findViewById(R.id.timer_2);
        bt = findViewById(R.id.save);
        im1 = findViewById(R.id.left_icon);
        im2 = findViewById(R.id.right_icon);
        ttl = findViewById(R.id.ttl);
        Intent in  = getIntent();
        String name = in.getStringExtra("name");
        //Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
        root = FirebaseDatabase.getInstance("https://agriculture-mobapp-default-rtdb.firebaseio.com/");
        reference = root.getReference("schedules");
        Scheduler scheduler = new Scheduler();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        clocks();
        bt.setOnClickListener(e->{
            String sched = "from "+ timer1.getText().toString() +" to "+timer2.getText().toString();
            scheduler.setName(name);
            scheduler.setTime(sched);
            reference.child(String.valueOf(maxid+1)).setValue(scheduler);
           // reference.child("users").child(key).setValue(sched);
           // Toast.makeText(this,key,Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this,Schedule.class);
            i.putExtra("name",name);
            startActivity(i);
        });


        im1.setOnClickListener(e->{
            Intent intent = new Intent(TimePik.this,Schedule.class);
            intent.putExtra("name",name);
            startActivity(intent);
        });

        im2.setOnClickListener(e->{

            startActivity(new Intent(this, FullscreenActivity.class));
        });
        ttl.setText("My Schedules");


    }

    private void clocks()
    {
        timer1.setOnClickListener(e->{
            TimePickerDialog tpd = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                    new TimePickerDialog.OnTimeSetListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hr1 = hourOfDay;
                            min1 = minute;
                            String time = hr1 + ":"+ min1;
                            stTime = hr1 + ":"+ min1;
                            SimpleDateFormat f24hrs = new SimpleDateFormat("HH:mm");
                            try {
                                Date date = f24hrs.parse(time);
                                SimpleDateFormat f12hr = new SimpleDateFormat("hh:mm aa");
                                timer1.setText(f12hr.format(date));
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    },12,0,false);
            tpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            tpd.updateTime(hr1,min1);
            tpd.show();
            f1=1;
            check();

        });

        timer2.setOnClickListener(e->{
            TimePickerDialog tpd = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                    new TimePickerDialog.OnTimeSetListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hr2 = hourOfDay;
                            min2 = minute;
                            String time = hr2 + ":"+ min2;
                            edTime = hr2 + ":"+ min2;
                            SimpleDateFormat f24hrs = new SimpleDateFormat("HH:mm");
                            try {
                                Date date = f24hrs.parse(time);
                                SimpleDateFormat f12hr = new SimpleDateFormat("hh:mm aa");
                                timer2.setText(f12hr.format(date));
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    },12,0,false);
            tpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            tpd.updateTime(hr2,min2);
            tpd.show();
            f2=1;
            check();



        });
    }

    private void check()
    {
        if (f1==1 && f2==1)
            bt.setEnabled(true);
    }


}