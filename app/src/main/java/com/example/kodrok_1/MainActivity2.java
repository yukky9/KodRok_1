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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://thereawheel3.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInt retrofitInt = retrofit.create(RetrofitInt.class);

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
                        Call<String> call = retrofitInt.register(email.getText().toString(),pswrd.getText().toString(),pswrd_admin.getText().toString(),"");
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body().equals("success")){
                                    Intent intent = new Intent(MainActivity2.this, ProfileActivity.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(MainActivity2.this, "Такой пользователь уже есть!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(MainActivity2.this, "Ошибка на сервере", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<String> call = retrofitInt.register(email.getText().toString(),pswrd.getText().toString(),"",email_admin.getText().toString());
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body().equals("success")){
                                    Intent intent = new Intent(MainActivity2.this, ProfileEmployee.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(MainActivity2.this, "Такой пользователь уже есть!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(MainActivity2.this, "Ошибка на сервере", Toast.LENGTH_SHORT).show();
                            }
                        });
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