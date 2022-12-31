package com.karlina.kitchenjourney.helper;

import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    retrofit2.Call<ResponseBody> registerUser(@Field("name") String name, @Field("password")String password, @Field("email")String email);

    @FormUrlEncoded
    @POST("login.php")
    retrofit2.Call<ResponseBody> loginUser(@Field("email") String email, @Field("password")String password);


    @GET("getresep.php")
    retrofit2.Call<ResponseBody> getNews();

}
