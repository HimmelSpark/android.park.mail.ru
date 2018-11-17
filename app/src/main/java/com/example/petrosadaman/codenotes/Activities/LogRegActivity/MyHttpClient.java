package com.example.petrosadaman.codenotes.Activities.LogRegActivity;


import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*

{
  "name": "testUser123",
  "password": "qwertyui",
  "email":"somemail@mail.ru"
}

 */

public class MyHttpClient {

    static enum METHOD {
        PUT,
        GET,
        POST
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static void doResp(String name, String password, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://178.128.138.0:8080/users/auth")
//                .url("https://requestbin.jumio.com/1emi44z1")
                .post(RequestBody.create(JSON, "{\n" +
                        "\"name\": " + name + ",\n" +
                        "\"password\":" + password + "\n" +
                        "}"))
                .build();

        client.newCall(request).enqueue(callback);

    }
}
