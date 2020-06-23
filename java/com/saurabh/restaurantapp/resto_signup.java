package com.saurabh.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class resto_signup extends AppCompatActivity {

    EditText name,phone,address,email,pass;
    Button signup;
    FirebaseAuth fauth;
    DatabaseReference ref;
    restaurant_data rdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_signup);

        name=findViewById(R.id.name_txt);
        phone=findViewById(R.id.phone_txt);
        address=findViewById(R.id.add_txt);
        email=findViewById(R.id.signin_emailtxt);
        pass=findViewById(R.id.signin_pass_txt);
        signup=findViewById(R.id.signup_btn);

        fauth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference().child("Restaurant Signup");
        rdata=new restaurant_data();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validata_data())
                {
                    rdata.setName(name.getText().toString().trim());
                    rdata.setPhone(phone.getText().toString().trim());
                    rdata.setAdd(address.getText().toString().trim());
                    rdata.setEmail(email.getText().toString().trim());
                    rdata.setPass(pass.getText().toString().trim());

                    fauth.createUserWithEmailAndPassword(email.getText().toString().trim(),pass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                ref.child(fauth.getCurrentUser().getUid()).setValue(rdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(),"Sucessful Signup",Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(),Signin_rest.class));
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Unsucessful Signup",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"User already registered",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(),"Unsucessful signup",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public boolean validata_data()
    {
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

        if(phone.getText().toString().trim().isEmpty())
        {
            return false;
        }
        else if(phone.getText().toString().trim().length()!=10)
        {
            phone.setError("Enter a valid Phone number");
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
}
