package com.example.kodrok_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    Button bt1;
    TextView textView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt1 = findViewById(R.id.imageButton4);
        textView = findViewById(R.id.textView6);
    }

    public void Click(View view) {
        bt1.setBackgroundColor(Color.BLUE);
    }
}