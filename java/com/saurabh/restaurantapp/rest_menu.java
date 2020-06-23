package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class rest_menu extends AppCompatActivity {

    ListView menu;
    Button add;
    DatabaseReference ref;
    ArrayList<String> mname=new ArrayList<>();
    ArrayList<String> key=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_menu);

       menu=findViewById(R.id.menu_list);
       add=findViewById(R.id.add_btn);

       ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Menu");

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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(rest_menu.this);
                View dv=getLayoutInflater().inflate(R.layout.add_menu,null);
                final EditText name =dv.findViewById(R.id.dname);
                final EditText price = dv.findViewById(R.id.dprice);
                Button add=dv.findViewById(R.id.dadd);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(name.getText().toString().isEmpty())
                        {
                            name.setError("Field cannot be empty");
                        }
                        else if(price.getText().toString().isEmpty())
                        {
                            price.setError("Field cannot be empty");
                        }
                        else
                        {
                            ref.child(name.getText().toString().trim()).setValue(price.getText().toString().trim());
                            mname.add(name.getText().toString().trim()+price.getText().toString().trim());
                            adapt.notifyDataSetChanged();
                        }
                    }
                });
                builder.setView(dv);
                builder.create().show();
            }
        });
        menu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(rest_menu.this);
                View dv = getLayoutInflater().inflate(R.layout.update_menu,null);
                final EditText name=dv.findViewById(R.id.name);
                final EditText price=dv.findViewById(R.id.price);
                Button update=dv.findViewById(R.id.update);
                Button delete=dv.findViewById(R.id.delete);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(name.getText().toString().isEmpty())
                        {
                            name.setError("Field cannot be empty");
                        }
                        else if(price.getText().toString().isEmpty())
                        {
                            price.setError("Field cannot be empty");
                        }
                        else
                        {
                            DatabaseReference dref=ref.child(key.get(position));
                            dref.removeValue();
                            ref.child(name.getText().toString().trim()).setValue(price.getText().toString().trim());
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        DatabaseReference dref=ref.child(key.get(position));
                        dref.removeValue();
                        mname.remove(position);
                        adapt.notifyDataSetChanged();
                    }
                });
                builder.setView(dv);
                builder.create().show();
                return false;
            }
        });

    }
}
