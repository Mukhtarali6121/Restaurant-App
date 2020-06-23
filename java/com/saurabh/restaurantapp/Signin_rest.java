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

public class Signin_rest extends AppCompatActivity {

    EditText email1,pass1;
    Button signin1;
    TextView resto_signin_link;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_rest);

        email1=findViewById(R.id.signin_emailtxt);
        pass1=findViewById(R.id.signin_passtxt);
        signin1=findViewById(R.id.signin_restbtn);
        fauth=FirebaseAuth.getInstance();

        resto_signin_link=findViewById(R.id.resto_signin_link);
        String text="new users signup";
        SpannableString ss=new SpannableString(text);

        ClickableSpan cs=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                open_resto_signup();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ss.setSpan(cs,10,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        resto_signin_link.setText(ss);
        resto_signin_link.setMovementMethod(LinkMovementMethod.getInstance());

        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate_data())
                {
                    fauth.signInWithEmailAndPassword(email1.getText().toString().trim(),pass1.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Signin Sucessful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),rest_option.class));
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Incorrect Email or Password",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Unsucessfull Signup",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean validate_data() {
        if(email1.getText().toString().trim().isEmpty())
        {
            email1.setError("Field cannot be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email1.getText().toString().trim()).matches())
        {
            email1.setError("Please enter a vaild Email address");
            return false;
        }
        if(pass1.getText().toString().trim().isEmpty())
        {
            return false;
        }
        else if(pass1.getText().toString().trim().length()<8)
        {
            pass1.setError("Password should have atleast 8 character");
            return false;
        }
        return true;
    }

    public void open_resto_signup()
    {
        Intent intent=new Intent(this,resto_signup.class);
        startActivity(intent);
    }
}
