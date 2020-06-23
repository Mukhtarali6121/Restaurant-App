package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class cust_menu_list extends AppCompatActivity {

    String str;
    DatabaseReference ref;
    ListView menu;
    ArrayList<String> mname=new ArrayList<>();
    ArrayList<String> key=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_menu_list);

        str=getIntent().getStringExtra("userid1");

        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(str).child("Menu");
        menu=findViewById(R.id.list_view);

        final ArrayAdapter<String> adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mname);
        menu.setAdapter(adapt);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str=dataSnapshot.getKey();
                str+="\t"+dataSnapshot.getValue(String.class);
                mname.add(str);
                key.add(dataSnapshot.getKey());
                adapt.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key=dataSnapshot.getKey();
                String str=dataSnapshot.getKey();
                str+="\n"+dataSnapshot.getValue(String.class);


                int index=key.indexOf(key);
                mname.set(index,str);

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

    }
}
