package com.example.petrosadaman.codenotes.Web;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteService;
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
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApi {

    // Base URL should end with '/' symbol !!!
    private static final String BASE_URL = "http://178.128.138.0:8080/users/";
//    private static final String BASE_URL = "http://requestbin.fullcontact.com/uhoyh9uh/";
//    private static final String BASE_URL = "http://127.0.0.1:8080/users/";

    private static final UserApi INSTANCE = new UserApi();

    /**
     * GSON достаточно тяжелый объект с долгой инициализацией. Не стоит создавать его каждый
     * раз для разбора JSON - имеет смысл переиспользовать объект.
     */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(UserModel.class, new UserAdapter())
            .create();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final UserService userService;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private UserApi() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    public static UserApi getInstance() {
        return INSTANCE;
    }

    public ListenerHandler<OnUserGetListener> authUser(final UserModel user, final OnUserGetListener listener) {
        final ListenerHandler<OnUserGetListener> handler = new ListenerHandler<>(listener);
        executor.execute(() -> {
            try {
                final Response<ResponseBody> response = userService.getUser(user).execute();
                try (final ResponseBody responseBody = response.body()) {
                    System.out.println("CODE____________" + response.toString());
                    if (response.code() != 200) {
                        throw new IOException("HTTP code " + response.code());
                    }
                    if (responseBody == null) {
                        throw new IOException("Empty body body");
                    }
                    final String body = responseBody.string();
                    invokeSuccess(handler, parseMessage(body));
                }
            } catch (IOException e) {
                invokeFailure(handler, e);
            }
        });
        return handler;
    }

    public ListenerHandler<OnUserGetListener> regUser(final UserModel user, final OnUserGetListener listener) {
        final ListenerHandler<OnUserGetListener> handler = new ListenerHandler<>(listener);
        executor.execute(() -> {
            try {
                final Response<ResponseBody> response = userService.regUser(user).execute();
                try (final ResponseBody responseBody = response.body()) {
                    System.out.println("CODE____________" + response.toString());
                    if (response.code() >= 300) {
                        //TODO | при ошибке тоже возвращается код, надо его принимать, чтобы узнать, что произошло на беке
                        //TODO | либо интерпретировать код ошибки
                        throw new IOException("HTTP code " + response.code());
                    }
                    if (responseBody == null) {
                        throw new IOException("Empty body body");
                    }
                    final String body = responseBody.string();
                    invokeSuccess(handler, parseMessage(body));
                }
            } catch (IOException e) {
                invokeFailure(handler, e);
            }
        });

        return handler;
    }

    private void invokeSuccess(ListenerHandler<OnUserGetListener> handler, final MessageModel message) {
        mainHandler.post(() -> {
            System.out.println("in invoke success!");
            OnUserGetListener listener = handler.getListener();
            if (listener!= null) {
                Log.d("API", "listener NOT null");
                listener.onUserSuccess(message);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private void invokeFailure(ListenerHandler<OnUserGetListener> handler, IOException e) {
        mainHandler.post(() -> {
            System.out.println("in invoke failure!");
            OnUserGetListener listener = handler.getListener();
            if (listener != null) {
                Log.d("API", "listener NOT null");
                listener.onUserError(e);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private MessageModel parseMessage(final String body) throws IOException {
        try {
            return GSON.fromJson(body, MessageModel.class);
        } catch (JsonSyntaxException e) {
            throw new IOException(e);
        }
    }


    public interface OnUserGetListener {
        void onUserSuccess(final MessageModel message);

        void onUserError(final Exception error);
    }
}
