package com.example.kodrok_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    EditText email_admin;
    EditText pswrd_admin;
    ImageButton bt1;
    ImageButton bt2;
    TextView textView;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt1 = findViewById(R.id.imageButton4);
        bt2 = findViewById(R.id.imageButton2);
        textView = findViewById(R.id.textView10);
        email_admin = findViewById(R.id.editTextTextEmailAddress2);
        pswrd_admin = findViewById(R.id.editTextNumberPassword);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setImageResource(R.drawable.rectangle_44);
                bt2.setImageResource(R.drawable.rectangle_4);
                email_admin.setHint("Enter the supervisor's email");
                pswrd_admin.setHint("");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt1.setImageResource(R.drawable.rectangle_4);
                bt2.setImageResource(R.drawable.rectangle_44);
                pswrd_admin.setHint("Enter code");
                email_admin.setHint("");
            }
        });
    }
}