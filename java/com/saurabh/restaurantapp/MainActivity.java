package com.saurabh.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button rest_btn,custo_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rest_btn=findViewById(R.id.rest_btn);
        custo_btn=findViewById(R.id.custo_btn);
        rest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_resto_signin();
            }
        });
        custo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_custo_signin();
            }
        });
    }
    public void open_resto_signin(){
        Intent intent=new Intent(this,Signin_rest.class);
        startActivity(intent);
    }
    public void open_custo_signin(){
        Intent intent=new Intent(this,Signin_custo.class);
        startActivity(intent);
    }
}
