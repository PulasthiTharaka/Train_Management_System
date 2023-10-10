package com.example.train_management_system.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @GET("User")
    Call<JsonObject> getAllUsers();

    @Headers("Content-Type: application/json")
    @POST("User/login")
    Call<JsonObject> login(@Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("User/register")
    Call<JsonObject> register(@Body JsonObject jsonObject);

    @GET("Booking")
    Call<JsonArray> getBookings();

}
