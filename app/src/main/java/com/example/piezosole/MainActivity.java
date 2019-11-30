package com.example.piezosole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginbutton =  (Button) findViewById(R.id.loginbutton);
       Button  registerbutton =  (Button) findViewById(R.id.regbutton);
      //  title = findViewById(R.id.textView);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginact = new Intent(MainActivity.this, Login.class);
                startActivity(loginact);
            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regestact = new Intent(MainActivity.this, Register.class);
                startActivity(regestact);
            }
        });

    }
}
