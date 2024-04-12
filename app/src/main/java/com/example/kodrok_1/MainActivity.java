package com.example.kodrok_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText pswrd;
    ImageButton login;
    TextView tw;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editTextTextEmailAddress);
        pswrd = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.imageButton);
        tw = findViewById(R.id.textView20);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thereawheel3.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInt retrofitInt = retrofit.create(RetrofitInt.class);

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your email to login", Toast.LENGTH_SHORT).show();
                }
                if (pswrd.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your password to login", Toast.LENGTH_SHORT).show();
                }else {
                    Call<String> call = retrofitInt.login(email.getText().toString(),pswrd.getText().toString());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.body().equals("error")){
                                Toast.makeText(MainActivity.this, "Такого пользователя нет", Toast.LENGTH_SHORT).show();
                            } else if (response.body().equals("user")) {
                                Intent intent = new Intent(MainActivity.this, BaseProfileEmployee.class);
                                startActivity(intent);
                            } else if (response.body().equals("admin")) {
                                Intent intent = new Intent(MainActivity.this, BaseProfile.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }
}