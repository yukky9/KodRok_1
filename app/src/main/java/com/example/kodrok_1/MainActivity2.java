package com.example.kodrok_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    ImageButton register;
    EditText email;
    EditText pswrd;
    EditText email_admin;
    EditText pswrd_admin;
    ImageButton bt1;
    ImageButton bt2;
    TextView textView;

    boolean isAdmin = false;

    @SuppressLint({"WrongViewCast", "MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt1 = findViewById(R.id.imageButton4);
        email = findViewById(R.id.editTextTextEmailAddress1);
        pswrd = findViewById(R.id.editTextTextPassword);
        bt2 = findViewById(R.id.imageButton2);
        register = findViewById(R.id.imageButt);
        textView = findViewById(R.id.textView10);
        email_admin = findViewById(R.id.editTextTextEmailAddress2);
        pswrd_admin = findViewById(R.id.editTextTextEmailAddress2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity2.this, "Enter your email to register", Toast.LENGTH_SHORT).show();
                }
                if (pswrd.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity2.this, "Enter your password to regist", Toast.LENGTH_SHORT).show();
                }
                if (isAdmin && pswrd_admin.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity2.this, "Enter code to regist", Toast.LENGTH_SHORT).show();
                } else if (!isAdmin && email_admin.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity2.this, "Enter email admin to regist", Toast.LENGTH_SHORT).show();
                } else {
                    if (isAdmin){
                        Intent intent = new Intent(MainActivity2.this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity2.this, WifiState.class);
                        startActivity(intent);
                    }
                }
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setImageResource(R.drawable.rectangle_44);
                bt2.setImageResource(R.drawable.rectangle_4);
                email_admin.setHint("Enter the supervisor's email");
                isAdmin = false;
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setImageResource(R.drawable.rectangle_4);
                bt2.setImageResource(R.drawable.rectangle_44);
                pswrd_admin.setHint("Enter code");
                isAdmin = true;
            }
        });
    }
}