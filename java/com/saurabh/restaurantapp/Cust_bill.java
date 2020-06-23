package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cust_bill extends AppCompatActivity {

    Button pay;
    DatabaseReference ref;
    ListView bill;
    ArrayList<String> alist=new ArrayList<>();
    TextView total;
    ArrayAdapter<String> adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_bill);

        String tot= getIntent().getStringExtra("total1");

        pay=findViewById(R.id.pay);
        total=findViewById(R.id.t1);
        bill=findViewById(R.id.bill);
        ref=FirebaseDatabase.getInstance().getReference().child("Customer Signup").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Parcel");

        adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alist);
        bill.setAdapter(adapt);

        total.setText(tot);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String str=ds.getKey();
                    str+="         ";
                    str+=ds.getValue();
                    alist.add(str);
                    adapt.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ref.removeValue();
            }
        });
    }

}
