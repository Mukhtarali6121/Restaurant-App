package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

public class cust_parcel extends AppCompatActivity {

    String str;
    DatabaseReference ref,mref,ref2;
    ListView menu;
    ArrayList<String> mname=new ArrayList<>();
    ArrayList<String> key=new ArrayList<>();
    ArrayList<String> value=new ArrayList<>();
    TextView total;
    Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_parcel);

        str=getIntent().getStringExtra("userid");

        total=findViewById(R.id.total);
        order=findViewById(R.id.order_btn);

        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(str).child("Menu");
        ref2=FirebaseDatabase.getInstance().getReference().child("Restaurant Signup").child(str).child("Parcel Order");
        mref=FirebaseDatabase.getInstance().getReference().child("Customer Signup").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("Parcel");

        menu=findViewById(R.id.menu_list);

        final ArrayAdapter<String> adapt=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mname);
        menu.setAdapter(adapt);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String str=dataSnapshot.getKey();
                str+="\t"+dataSnapshot.getValue(String.class);
                mname.add(str);
                key.add(dataSnapshot.getKey());
                value.add(dataSnapshot.getValue().toString());
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

        menu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(cust_parcel.this);
                builder.setTitle("Order");
                builder.setMessage("Do you want to add this item?");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dish,price;
                        dish=key.get(position);
                        price=value.get(position);
                        mref.child(dish).setValue(price);
                        ref2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("1");
                        int total1 = Integer.parseInt(total.getText().toString().trim());
                        total1+=Integer.parseInt(price);
                        total.setText(Integer.toString(total1));
                        menu.getChildAt(position).setBackgroundColor(Color.GRAY);
                    }
                });
                builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dish=key.get(position);
                        String price=value.get(position);
                        int t=Integer.parseInt(total.getText().toString().trim());
                        t-=Integer.parseInt(price);
                        total.setText(Integer.toString(t));
                        DatabaseReference temp=ref.child(dish);
                        temp.removeValue();
                        menu.getChildAt(position).setBackgroundColor(Color.WHITE);
                    }
                });
                builder.create().show();
                return false;
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Cust_bill.class);
                intent.putExtra("userid",str);
                intent.putExtra("total1",total.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
