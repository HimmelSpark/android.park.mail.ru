package com.example.petrosadaman.codenotes.Models.Note;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteService {
    @POST("add")
    Call<ResponseBody> addNote(@Body NoteModel note);

    @GET("http://178.128.138.0:8080/users/notes/{username}/")
    Call<ResponseBody> getList(@Path("username") String username);

//    @GET("http://localhost:8080/users/notes/{username}/")
//    Call<ResponseBody> getList(@Path("username") String username);
}
