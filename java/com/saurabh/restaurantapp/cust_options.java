package com.saurabh.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class cust_options extends AppCompatActivity {

    Button menu,parcel,seat;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_options);
        str=getIntent().getStringExtra("userid");

        menu=findViewById(R.id.vmenu_btn);
        parcel=findViewById(R.id.parcel_btn);
        seat=findViewById(R.id.btable_btn);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),cust_menu_list.class);
                i.putExtra("userid1",str);
                startActivity(i);
            }
        });
        seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),cust_table.class);
                intent.putExtra("userid",str);
                startActivity(intent);
            }
        });
        parcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),cust_parcel.class);
                intent.putExtra("userid",str);
                startActivity(intent);
            }
        });
    }
}
