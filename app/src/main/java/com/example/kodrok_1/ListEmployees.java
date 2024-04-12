package com.example.kodrok_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListEmployees extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://thereawheel3.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitInt retrofitInt = retrofit.create(RetrofitInt.class);

    ListView listView;
    ImageButton back_admin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employees);

        back_admin = findViewById(R.id.back_bt);
        listView = findViewById(R.id.list);

        final String[] emails = new String[] {"gabramov1@gmail.com",
                "gabramov2@gmail.com",
                "gabramov3@gmail.com"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, emails);

        listView.setAdapter(adapter);

        back_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEmployees.this, BaseProfile.class);
                startActivity(intent);
            }
        });
    }
}