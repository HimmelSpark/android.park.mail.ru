package com.example.petrosadaman.codenotes.Web;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.petrosadaman.codenotes.Models.User.UserAdapter;
import com.example.petrosadaman.codenotes.Models.User.UserModel;
import com.example.petrosadaman.codenotes.Models.User.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserApi {

    private static final String BASE_URL = "http://178.128.138.0:8080/users/";

    private static final UserApi INSTANCE = new UserApi();

    /**
     * GSON достаточно тяжелый объект с долгой инициализацией. Не стоит создавать его каждый
     * раз для разбора JSON - имеет смысл переиспользовать объект.
     */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(UserModel.class, new UserAdapter())
            .create();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final UserService service;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private UserApi() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(UserService.class);
    }

    public static UserApi getInstance() {
        return INSTANCE;
    }

    public ListenerHandler<OnUserGetListener> getUser(final UserModel user, final OnUserGetListener listener) {
        final ListenerHandler<OnUserGetListener> handler = new ListenerHandler<>(listener);
        executor.execute(() -> {
            try {

                final Response<ResponseBody> response = service.getUser(user).execute();

                try (final ResponseBody responseBody = response.body()) {

                    if (response.code() != 200) {
                        throw new IOException("HTTP code " + response.code());
                    }

                    if (responseBody == null) {
                        throw new IOException("Empty body body");
                    }

                    final String body = responseBody.string();
                    invokeSuccess(handler, parseUser(body));

                }
            } catch (IOException e) {
                invokeFailure(handler, e);
            }
        });

        return handler;
    }

    private void invokeSuccess(ListenerHandler<OnUserGetListener> handler, final UserModel user) {
        mainHandler.post(() -> {
            OnUserGetListener listener = handler.getListener();
            if (listener!= null) {
                Log.d("API", "listener NOT null");
                listener.onUserSuccess(user);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private void invokeFailure(ListenerHandler<OnUserGetListener> handler, IOException e) {
        mainHandler.post(() -> {
            OnUserGetListener listener = handler.getListener();
            if (listener != null) {
                Log.d("API", "listener NOT null");
                listener.onUserError(e);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private UserModel parseUser(final String body) throws IOException {
        try {
            return GSON.fromJson(body, UserModel.class);
        } catch (JsonSyntaxException e) {
            throw new IOException(e);
        }
    }


    public interface OnUserGetListener {
        void onUserSuccess(final UserModel user);

        void onUserError(final Exception error);
    }
}
