package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity {


    private ImageButton imb;
    private TextView textView;
    private String ans;
     String t1,t2 ;
     ImageView lim , rim;

     String times[];
     ListView lv;
    private FirebaseDatabase root;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        imb = findViewById(R.id.ad);
        lv = findViewById(R.id.scdls);
        textView = findViewById(R.id.noData);
        Intent in = getIntent();
        t1 = in.getStringExtra("t1");
        t2 = in.getStringExtra("t2");
        lim = findViewById(R.id.left_icon);
        rim = findViewById(R.id.right_icon);

        Intent i  = getIntent();
        String name = i.getStringExtra("name");
        root = FirebaseDatabase.getInstance("https://agriculture-mobapp-default-rtdb.firebaseio.com/");
        ref = root.getReference("schedules");
        Query checkUser = ref.orderByChild("name").equalTo(name);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                   long i = dataSnapshot.getChildrenCount();
                    //Toast.makeText(Schedule.this,dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                    String a[] = dataSnapshot.toString().split(name);
                    times = new String[(int) i];
                    for(int j=1;j<a.length;j++)
                    {
                        i--;
                        if(i<0)
                            break;
                        int m= a[j].indexOf('}');
                        times[(int) i]=a[j].substring(7,m);
                    }

                    ProgramAdapter pa = new ProgramAdapter(Schedule.this,times);
                    lv.setAdapter(pa);
                    //Toast.makeText(Schedule.this,a.length+"",Toast.LENGTH_LONG).show();


                }

                else
                    textView.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

                imb.setOnClickListener(e -> {
                    Intent intent = new Intent(this, TimePik.class);
                    intent.putExtra("name", name);
                    startActivity(intent);

                });

        lim.setOnClickListener(e->{
            Intent k = new Intent(this,MainActivity2.class);
            k.putExtra("name",name);
            startActivity(k);
        });
        rim.setOnClickListener(e->{

            startActivity(new Intent(this, FullscreenActivity.class));
        });

    }
}










