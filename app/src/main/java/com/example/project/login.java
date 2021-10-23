package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private Button bt;
    private EditText ed1,ed2;
    private String email,password;
    private String ans;
    private FirebaseDatabase root;
    private DatabaseReference ref;
    private String p;
    private int s,e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt = findViewById(R.id.login);
        ed1 = findViewById(R.id.name1);
        ed2 = findViewById(R.id.password1);
        bt.setOnClickListener(e->{

            if(ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() )
                Toast.makeText(this,"Please enter all credentials",Toast.LENGTH_LONG).show();
            else
                isUser();

                //startActivity(new Intent(this, MainActivity2.class));
            //else toast Incorrect email or password

        });
    }

    private void isUser() {
        String enteredMail = ed1.getText().toString().trim();
        String enteredPass = ed2.getText().toString().trim();

        root = FirebaseDatabase.getInstance("https://agriculture-mobapp-default-rtdb.firebaseio.com/");
        ref = root.getReference("users");
        Query checkUser = ref.orderByChild("email").equalTo(enteredMail);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    //Toast.makeText(login.this,dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();
                    String passFromDB = dataSnapshot.getValue().toString();
                    if(passFromDB.contains("null"))
                    {
                         s = passFromDB.indexOf('=');
                         e = passFromDB.indexOf(',',s);
                         p=passFromDB.substring(s+1,e);

                    }
                     else
                    {
                         p = passFromDB.split("=")[2].split(",")[0];
                       // Toast.makeText(login.this,p,Toast.LENGTH_LONG).show();
                    }




                    if (p.equals(enteredPass))
                    {
                        Intent i = new Intent(login.this,MainActivity2.class);
                        i.putExtra("name",ed1.getText().toString());
                        startActivity(i);
                    }

                    else
                        Toast.makeText(login.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(login.this,"This username isn't registered",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}