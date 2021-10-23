package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

   private GifImageView gif;
    private Button bt1;
    private EditText ed1,ed2;
    private TextView txt,txt2;
    public static String email, password;
    private ImageView imv;
    private String ans;
    private FirebaseDatabase root;
    private DatabaseReference reference;
    private Users user;
    long maxId=0;
    boolean possible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gif = findViewById(R.id.gifView2);
        bt1 = findViewById(R.id.signup);
        ed1 = findViewById(R.id.name);
        ed2 = findViewById(R.id.password);
        txt2 = findViewById(R.id.text2);
        txt = findViewById(R.id.textView3);
        imv = findViewById(R.id.gr1);
        user = new Users();
        root = FirebaseDatabase.getInstance("https://agriculture-mobapp-default-rtdb.firebaseio.com/");
        reference = root.getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxId = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        if(!isConnected(MainActivity.this))
        {
            showCustomDialog();
        }

        ObjectAnimator animation = ObjectAnimator.ofFloat(gif, "translationY", -500f);
        animation.setDuration(3000);
       animation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bt1.setVisibility(View.VISIBLE);
                ed1.setVisibility(View.VISIBLE);
                ed2.setVisibility(View.VISIBLE);
                bt1.setVisibility(View.VISIBLE);
                txt.setVisibility(View.VISIBLE);
                txt2.setVisibility(View.VISIBLE);
                imv.setVisibility(View.VISIBLE);
            }
        },2000);
        //Creating a client to connect with server



        bt1.setOnClickListener(e->{

            if(ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() )
                Toast.makeText(this,"Please enter all credentials",Toast.LENGTH_LONG).show();



            user.setEmail(ed1.getText().toString().trim());
            user.setPassword(ed2.getText().toString().trim());

            isUser();
            if(possible==true)
            {
                reference.child(String.valueOf(maxId+1)).setValue(user);
                Intent i = new Intent(this,MainActivity2.class);
                i.putExtra("name",user.getEmail());
                startActivity(i);
                possible= false;
            }
            else
                Toast.makeText(this,"Username Already Exist",Toast.LENGTH_SHORT).show();

        });



        txt.setOnClickListener(e->{
            startActivity(new Intent(this, login.class));
        });


    }

    private void isUser() {
        String enteredMail = ed1.getText().toString().trim();
        String enteredPass = ed2.getText().toString().trim();

        root = FirebaseDatabase.getInstance("https://agriculture-mobproj-default-rtdb.firebaseio.com/");
        reference = root.getReference("users");
        Query checkUser = reference.orderByChild("email").equalTo(enteredMail);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    possible = false;
                }
                else
                    possible = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobcon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wificon != null && wificon.isConnected())||(mobcon != null && mobcon.isConnected()))
            return true;
        else
            return false;
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please Connect to the internet to proceed")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, FullscreenActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();




    }




}


