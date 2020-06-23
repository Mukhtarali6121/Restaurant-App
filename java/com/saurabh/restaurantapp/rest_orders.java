package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class rest_orders extends AppCompatActivity {

    ListView olist;
    Button done;
    DatabaseReference ref;
    String uid;
    ArrayList<String> alist=new ArrayList<String>();
    ArrayAdapter<String> adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_orders);

        uid=getIntent().getStringExtra("userid");

        olist=findViewById(R.id.order_list);
        done=findViewById(R.id.done);

        adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alist);
        olist.setAdapter(adapt);

        ref= FirebaseDatabase.getInstance().getReference().child("Customer Signup").child(uid).child("Parcel");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {
                    String str = new String(ds.getKey() + " " + ds.getValue().toString());
                    alist.add(str);
                    adapt.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue();
                ref=FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("Parcel Order").child(uid);
                ref.removeValue();
                startActivity(new Intent(getApplicationContext(),rest_parcel.class));
            }
        });
    }
}
