package com.example.piezosole;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email,pwd;
    Button signin;
    FirebaseAuth firebaseAuth;
    Button  newuser;
    Register reg = new Register();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextEmail);
        pwd = findViewById(R.id.editTextPassword);
        signin = findViewById(R.id.buttonLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        newuser = findViewById(R.id.newusereg);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent dash = new Intent(Login.this,Dashboard.class);
                //startActivity(dash);
                userLogin();
            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(Login.this,Register.class);
                startActivity(dash);
                //userLogin();
            }
        });
    }
        private void userLogin()
        {
            String emailid = email.getText().toString().trim();
            String password = pwd.getText().toString().trim();
            if(TextUtils.isEmpty(emailid))
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

            startActivity(new Intent(getApplicationContext(),Dashboard.class));

            //if email and password are correct then display the progressdialog
            //  progressDialog.setMessage("Logging In...Please Wait");
            // progressDialog.show();

//            firebaseAuth.signInWithEmailAndPassword(emailid,password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                            if(task.isSuccessful())
//                            {
//                                //start the profile activity
//                                startActivity(new Intent(getApplicationContext(),Dashboard.class));
//                            }
//                            else{
//                                Toast.makeText(Login.this,"Login Error",Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    });
//
      }



}
