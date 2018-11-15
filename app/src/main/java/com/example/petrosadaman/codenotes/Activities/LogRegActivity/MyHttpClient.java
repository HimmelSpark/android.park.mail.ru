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

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void doResp(String name, String password) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://178.128.138.0:8080/users/auth")
                .post(RequestBody.create(JSON, "{" +
                        "\"name\": " + name + "\n" +
                        "\"password\":" + password + "\n" +
                        "}"))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                System.out.println(e.getMessage() + "_____________message");
                System.out.println("FAIL________________");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("SUCCESS______________");
                System.out.println(response.body().string());
            }
        });

    }
}
