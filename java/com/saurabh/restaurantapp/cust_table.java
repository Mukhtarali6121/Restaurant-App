package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class cust_table extends AppCompatActivity {

    ImageView t1,t2,t3,t4;
    DatabaseReference ref;
    String str;
    boolean f;
    Button relese;
    HashMap<String,String>mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_table);

        str=getIntent().getStringExtra("userid");
        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(str).child("Table");


        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);
        relese=findViewById(R.id.relese);
        mp=new HashMap<String, String>();

        t1.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
        t2.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
        t3.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
        t4.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    if(ds.getKey().equals("T1"))
                    {
                        t1.setImageResource(R.drawable.ic_launcher_background);
                    }
                    if(ds.getKey().equals("T2"))
                    {
                        t2.setImageResource(R.drawable.ic_launcher_background);
                    }
                    if(ds.getKey().equals("T3"))
                    {
                        t3.setImageResource(R.drawable.ic_launcher_background);
                    }
                    if(ds.getKey().equals("T4"))
                    {
                        t4.setImageResource(R.drawable.ic_launcher_background);
                    }
                    mp.put(ds.getKey(),ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        relese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f = false;
                Iterator<Map.Entry<String,String>> it = mp.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String,String> m1= it.next();
                    if (m1.getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        DatabaseReference mref = ref.child(m1.getKey());
                        mref.removeValue();
                        //mp.remove(m1.getKey());
                        it.remove();
                        Toast.makeText(getApplicationContext(), "Table Released", Toast.LENGTH_LONG).show();
                        if (m1.getKey().equals("T1")) {
                            t1.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
                        }
                        if (m1.getKey().equals("T2")) {
                            t2.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
                        }
                        if (m1.getKey().equals("T3")) {
                            t3.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
                        }
                        if (m1.getKey().equals("T4")) {
                            t4.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
                        }
                        f = true;
                    }
                }
                if (f == false) {
                    Toast.makeText(getApplicationContext(), "You have not booked any Table", Toast.LENGTH_LONG).show();
                }
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=false;
                for(Map.Entry m1: mp.entrySet())
                {
                    if(m1.getKey().toString().equals("T1"))
                    {
                        f=true;
                        break;
                    }
                }
                if(f==false)
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(cust_table.this);
                    builder.setTitle("Book Table");
                    builder.setMessage("Do you want to book this table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ref.child("T1").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            mp.put("T1",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Toast.makeText(getApplicationContext(),"Table Booked",Toast.LENGTH_LONG).show();
                            t1.setImageResource(R.drawable.ic_launcher_background);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Table Already Booked",Toast.LENGTH_LONG).show();
                }
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=false;
                for(Map.Entry m1: mp.entrySet())
                {
                    if(m1.getKey().toString().equals("T2"))
                    {
                        f=true;
                        break;
                    }
                }
                if(f==false)
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(cust_table.this);
                    builder.setTitle("Book Table");
                    builder.setMessage("Do you want to book this table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ref.child("T2").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            mp.put("T2",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Toast.makeText(getApplicationContext(),"Table Booked",Toast.LENGTH_LONG).show();
                            t2.setImageResource(R.drawable.ic_launcher_background);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Table Already Booked",Toast.LENGTH_LONG).show();
                }
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=false;
                for(Map.Entry m1: mp.entrySet())
                {
                    if(m1.getKey().toString().equals("T3"))
                    {
                        f=true;
                        break;
                    }
                }
                if(f==false)
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(cust_table.this);
                    builder.setTitle("Book Table");
                    builder.setMessage("Do you want to book this table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ref.child("T3").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            mp.put("T3",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Toast.makeText(getApplicationContext(),"Table Booked",Toast.LENGTH_LONG).show();
                            t3.setImageResource(R.drawable.ic_launcher_background);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Table Already Booked",Toast.LENGTH_LONG).show();
                }
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=false;
                for(Map.Entry m1: mp.entrySet())
                {
                    if(m1.getKey().toString().equals("T4"))
                    {
                        f=true;
                        break;
                    }
                }
                if(f==false)
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(cust_table.this);
                    builder.setTitle("Book Table");
                    builder.setMessage("Do you want to book this table?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ref.child("T4").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            mp.put("T4",FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Toast.makeText(getApplicationContext(),"Table Booked",Toast.LENGTH_LONG).show();
                            t4.setImageResource(R.drawable.ic_launcher_background);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Table Already Booked",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
