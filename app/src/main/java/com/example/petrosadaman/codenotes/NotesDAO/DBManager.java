package com.example.petrosadaman.codenotes.NotesDAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

class DBManager {

    private static final int VERSION = 1;


    @SuppressLint("StaticFieldLeak")
    private static final DBManager INSTANCE = new DBManager();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private Context context;

    private SQLiteDatabase notesDB;

    private static final String DB_NAME = "Notes.db";

    static DBManager getInstance(Context context) {
        INSTANCE.context = context.getApplicationContext();
        return INSTANCE;
    }

    private void checkInitialized() {
        if (notesDB != null) {
            return;
        }

        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, DB_NAME, null, VERSION) {

            @Override
            public void onCreate(SQLiteDatabase db) {
                createDatabase(db);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };

        notesDB = helper.getWritableDatabase();
    }

    private void createDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE 'users' (ID INTEGER PRIMARY KEY,username VARCHAR(64) NOT NULL, password VARCHAR(64));");
        db.execSQL("CREATE TABLE 'notes' (ID INTEGER PRIMARY KEY,title VARCHAR(256) NOT NULL, note TEXT, username VARCHAR(64) NOT NULL," +
                " CONSTRAINT userg FOREIGN KEY (username) REFERENCES users(username));");
        db.execSQL("INSERT INTO 'users' (username, password) VALUES ('admin', 'admin');");
    }


    private Boolean checkUser(String user, String password){
        try {
            Cursor us = notesDB.rawQuery("SELECT * FROM users WHERE username = ? ;", new String[]{user});
            return  (us.getString(us.getColumnIndex("password")).equals(password)) ;

        }catch (Exception exp){
            return false;
        }
    }



}
