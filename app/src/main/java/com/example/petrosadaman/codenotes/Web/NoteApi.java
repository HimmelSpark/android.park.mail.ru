package com.example.petrosadaman.codenotes.Web;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.petrosadaman.codenotes.DBManager.DBManager;
import com.example.petrosadaman.codenotes.Models.Message.MessageModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteListAdapter;
import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.Models.Note.NoteService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoteApi {
    // Base URL should end with '/' symbol !!!
    private static final String BASE_URL = "http://178.128.138.0:8080/users/";
//    private static final String BASE_URL = "http://requestbin.fullcontact.com/1ld9n2x1/";

    private static final NoteApi INSTANCE = new NoteApi();

    private DBManager db;

    /**
     * GSON достаточно тяжелый объект с долгой инициализацией. Не стоит создавать его каждый
     * раз для разбора JSON - имеет смысл переиспользовать объект.
     */
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NoteModel.class, new NoteListAdapter())
            .create();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final NoteService noteService;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private NoteApi() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        noteService = retrofit.create(NoteService.class);
    }

    public static NoteApi getInstance() {
        return INSTANCE;
    }

    public void setDB(DBManager dab) {
        this.db = dab;
    }

    public ListenerHandler<NoteApi.OnNoteGetListener> fetchNotes(final NoteApi.OnNoteGetListener listener, String user) {
        final ListenerHandler<NoteApi.OnNoteGetListener> handler = new ListenerHandler<>(listener);
        executor.execute(() -> {
            try {
                final Response<ResponseBody> response = noteService.getList("supreme").execute();
                try (final ResponseBody responseBody = response.body()) {
                    if (response.code() >= 300) {
                        System.out.println("HTTP code " + response.code());
                        throw new IOException("HTTP code " + response.code());
                    }
                    if (responseBody == null) {
                        System.out.println("Empty body");
                        throw new IOException("Empty body body");
                    }
                    final String body = responseBody.string();
                    List<NoteModel> listNotes = parseNotes(body);
                    invokeSuccess(handler, listNotes);
                }
            } catch (IOException e) {
                    try {
                        List<NoteModel> lst= db.getAllNotes(user);
                        System.out.print("Authed");
                        invokeSuccess(handler, lst);
                    }
                    catch (Exception io){
                        invokeFailure(handler, e);
                    }
            }
        });
        return handler;
    }

    public ListenerHandler<NoteApi.OnNoteCreateListener> createNote(final NoteModel noteModel, final NoteApi.OnNoteCreateListener listener) {
        final ListenerHandler<NoteApi.OnNoteCreateListener> handler = new ListenerHandler<>(listener);
        executor.execute(() -> {
            try {
                final Response<ResponseBody> response = noteService.addNote(noteModel).execute();
                try (final ResponseBody responseBody = response.body()) {
                    if (response.code() >= 300) {
                        System.out.println("HTTP code " + response.code());
                        throw new IOException("HTTP code " + response.code());
                    }

                    if (responseBody == null) {
                        System.out.println("Empty body");
                        throw new IOException("Empty body body");
                    }

                    final String body = responseBody.string();
                    MessageModel message = parseMessage(body);
                    invokeSuccessMessage(handler, message);

                }
            } catch (IOException e) {
                invokeFailureMessage(handler, e);
            }
        });
        return handler;
    }

    private void invokeSuccess(ListenerHandler<NoteApi.OnNoteGetListener> handler, final List<NoteModel> note) {
        mainHandler.post(() -> {
            System.out.println("in invoke success! note");
            NoteApi.OnNoteGetListener listener = handler.getListener();
            if (listener!= null) {
                Log.d("API", "listener NOT null");
                listener.onNoteSuccess(note);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private void invokeFailure(ListenerHandler<NoteApi.OnNoteGetListener> handler, IOException e) {
        mainHandler.post(() -> {
            System.out.println("in invoke failure! note");
            NoteApi.OnNoteGetListener listener = handler.getListener();
            if (listener != null) {
                Log.d("API", "listener NOT null");
                listener.onNoteError(e);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private void invokeSuccessMessage(ListenerHandler<NoteApi.OnNoteCreateListener> handler, final MessageModel message) {
        mainHandler.post(() -> {
            System.out.println("in invoke success! note");
            NoteApi.OnNoteCreateListener listener = handler.getListener();
            if (listener!= null) {
                Log.d("API", "listener NOT null");
                listener.onMessageSuccess(message);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private void invokeFailureMessage(ListenerHandler<NoteApi.OnNoteCreateListener> handler, IOException e) {
        mainHandler.post(() -> {
            System.out.println("in invoke failure! note");
            NoteApi.OnNoteCreateListener listener = handler.getListener();
            if (listener != null) {
                Log.d("API", "listener NOT null");
                listener.onMessageError(e);
            } else {
                Log.d("API", "listener is null");
            }
        });
    }

    private List<NoteModel> parseNotes(final String body) throws IOException {
        try {
            //todo:: просто жесть и замкнутый круг, пришлось исользовать новый gson в обход десериализатора
            Type listType = new TypeToken<List<NoteModel>>(){}.getType();
            List<NoteModel> listItemsDes = new Gson().fromJson(body, listType);
            return listItemsDes;
        } catch (JsonSyntaxException e) {
            System.out.println("JSON ERROR");
            throw new IOException(e);
        }
    }
    public interface OnNoteGetListener {
        void onNoteSuccess(final List<NoteModel> note);

        void onNoteError(final Exception error);
    }

    private MessageModel parseMessage(final String body) throws IOException {
        try {
            return GSON.fromJson(body, MessageModel.class);
        } catch (JsonSyntaxException e) {
            throw new IOException(e);
        }
    }
    public interface OnNoteCreateListener {
        void onMessageSuccess(final MessageModel message);

        void onMessageError(final Exception error);
    }
}
