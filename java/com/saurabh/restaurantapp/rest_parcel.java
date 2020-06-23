package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class rest_parcel extends AppCompatActivity {

    ListView list;
    ArrayList<String> alist=new ArrayList<>();
    ArrayAdapter<String> adapt;
    DatabaseReference ref,ref1;
    ArrayList<String> key=new ArrayList<>();
    ArrayList<String> uidlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_parcel);

        list=findViewById(R.id.orderer_names);

        adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alist);
        list.setAdapter(adapt);

        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Parcel Order");

        ref.addListenerForSingleValueEvent(vlistener);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),rest_orders.class);
                intent.putExtra("userid",key.get(position));
                startActivity(intent);
                return false;
            }
        });
    }
    ValueEventListener vlistener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            alist.clear();
            if(dataSnapshot.exists())
            {
                int i=1;
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    key.add(ds.getKey());
                    alist.add("Order "+ Integer.toString(i));
                    i++;
                }
                adapt.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}

