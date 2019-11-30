package com.example.piezosole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private TextView userReg,signIn;
    private EditText username,emailid,pass,age,wt,ht;
    private Button register;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userReg = findViewById(R.id.textView);
        signIn = findViewById(R.id.textViewSignIn);
        username = findViewById(R.id.editUsername);
        emailid = findViewById(R.id.editTextEmail);;
        pass = findViewById(R.id.editTextPassword);
        register = findViewById(R.id.buttonRegister);
        age = findViewById(R.id.editAge);
        wt = findViewById(R.id.editWt);
        ht = findViewById(R.id.editHt);
//        firebaseAuth = firebaseAuth.getInstance();
//
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check if all fields are accurate
                //If yes,
                registerUser();
            }
        });

    }
    private void registerUser()
    {
        String email = emailid.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String names = username.getText().toString().trim();
        String ages = age.getText().toString().trim();
        String hts = ht.getText().toString().trim();
        String wts = wt.getText().toString().trim();


        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
       if(TextUtils.isEmpty(names)){
            Toast.makeText(this, "Please Enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(ages)){
            Toast.makeText(this, "Please Enter age", Toast.LENGTH_SHORT).show();
            return;
        }

       if(TextUtils.isEmpty(hts)){
            Toast.makeText(this, "Please Enter Height", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(wts)){
            Toast.makeText(this, "Please Enter Weights", Toast.LENGTH_SHORT).show();
            return;
        }
        //if validations are ok
        // we will first show a progress bar
        //  progressDialog.setMessage("Registering User");
        // progressDialog.show();


            startActivity(new Intent(getApplicationContext(),Login.class));


//        firebaseAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            //user Registered successfully
//                            Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            Toast.makeText(Register.this,"could not register",Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
    }
}
