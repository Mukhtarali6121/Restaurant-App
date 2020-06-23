package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin_custo extends AppCompatActivity {

    EditText email,pass;
    Button signin;
    FirebaseAuth fauth;
    TextView cust_signin_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_custo);

        email=findViewById(R.id.signin_emailtxt);
        pass=findViewById(R.id.signin_pass_txt);
        signin=findViewById(R.id.signin_custo_btn);
        fauth=FirebaseAuth.getInstance();

        cust_signin_link=findViewById(R.id.cust_signin_link);
        String text="new users signup";
        SpannableString ss=new SpannableString(text);

        ClickableSpan cs=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                open_cust_signup();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ss.setSpan(cs,10,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cust_signin_link.setText(ss);
        cust_signin_link.setMovementMethod(LinkMovementMethod.getInstance());

            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate_data()) {
                        fauth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Sucessful Signin", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(),cust_rest_list.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Unsucessful signin", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private boolean validate_data() {
        if(email.getText().toString().trim().isEmpty())
        {
            email.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())
        {
            email.setError("Please enter a vaild Email address");
            return false;
        }
        if(pass.getText().toString().trim().isEmpty())
        {
            return false;
        }
        else if(pass.getText().toString().trim().length()<8)
        {
            pass.setError("Password should have atleast 8 character");
            return false;
        }
        return true;
    }

    public void open_cust_signup()
    {
        Intent intent =new Intent(this,cust_signup.class);
        startActivity(intent);
    }
}
