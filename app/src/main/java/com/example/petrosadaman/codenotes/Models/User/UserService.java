package com.example.petrosadaman.codenotes.Models.User;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @POST("auth/")
    Call<ResponseBody> getUser(@Body UserModel user);
}
