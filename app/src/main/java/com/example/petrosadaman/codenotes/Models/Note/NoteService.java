package com.example.petrosadaman.codenotes.Models.Note;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteService {

    @POST("add")
    Call<ResponseBody> addNote(@Body NoteModel note);


    @GET("notes/{username}")
    Call<ResponseBody> getList(@Path("username") String username);

    @POST("update")
    Call<ResponseBody> updateNote(@Body NoteModel note);

}
