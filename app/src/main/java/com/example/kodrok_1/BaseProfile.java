package com.example.kodrok_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BaseProfile extends AppCompatActivity {
    ImageButton editProfile;
    ImageButton listEmployees;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_profile);

        editProfile = findViewById(R.id.imageButton6);
        listEmployees = findViewById(R.id.imageButton7);


        listEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseProfile.this, ListEmployees.class);
                startActivity(intent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseProfile.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}