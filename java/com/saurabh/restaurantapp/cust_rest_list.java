package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class cust_rest_list extends AppCompatActivity {

    ListView list;
    DatabaseReference ref;
    ArrayList<String> alist=new ArrayList<>();
    ArrayList<String> key=new ArrayList<>();
    ArrayAdapter<String> adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_rest_list);

        list=findViewById(R.id.list);
        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup");

        adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alist);
        list.setAdapter(adapt);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str=dataSnapshot.child("name").getValue(String.class);
                alist.add(str);
                key.add(dataSnapshot.getKey());

                adapt.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str=dataSnapshot.child("name").getValue(String.class);
                String key=dataSnapshot.getKey();

                int index=key.indexOf(key);
                alist.set(index,str);

                adapt.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),cust_options.class);
                intent.putExtra("userid",key.get(position));
                startActivity(intent);
            }
        });
    }
}
