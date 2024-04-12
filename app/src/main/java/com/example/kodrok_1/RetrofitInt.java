package com.example.kodrok_1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface RetrofitInt {
    @GET("userLogin")
    Call<String> login(@Query("email") String email, @Query("password") String password);
    @GET("userRegister")
    Call<String> register(@Query("email") String email, @Query("password") String password,
                          @Query("code") String code, @Query("adminMail") String adminMail);
    @GET("updateTime")
    Call<String> updateTime(@Query("email") String email, @Query("startTime") String startTime,
                            @Query("Endtime") String Endtime, @Query("SSID") String SSID,
                            @Query("date") String date);
    @GET("updateUserInfo")
    Call<String> updateInfo(@Query("email") String email, @Query("password") String password,
                            @Query("fio") String fio, @Query("image") String image,
                            @Query("contactInfo") String contactInfo,
                            @Query("additionalInfo") String additionalInfo);
    @GET("getDates")
    Call<List<List<String>>> getDates(@Query("email") String email);

    @GET("returnEmailByAdmin")
    Call<List<String>> getEmails(@Query("email") String email);
}