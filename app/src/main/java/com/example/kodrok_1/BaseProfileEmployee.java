package com.example.kodrok_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BaseProfileEmployee extends AppCompatActivity {

    ImageButton editProfile;
    ImageButton wifi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_profile_employee);

        editProfile = findViewById(R.id.imageButton6);
        wifi = findViewById(R.id.imageButton7);

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseProfileEmployee.this, WifiState.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseProfileEmployee.this, ProfileEmployee.class);
                startActivity(intent);
            }
        });
    }
}