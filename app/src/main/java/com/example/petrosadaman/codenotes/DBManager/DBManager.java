package com.example.petrosadaman.codenotes.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.petrosadaman.codenotes.Models.Note.NoteModel;
import com.example.petrosadaman.codenotes.Models.User.UserModel;

import java.util.ArrayList;
import java.util.List;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DBManager extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(NoteModel.CREATE_TABLE);
        db.execSQL(UserModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS notes;");

        db.execSQL("DROP TABLE IF EXISTS users;");

        // Create tables again
        onCreate(db);
    }



    public long insertNote(String note, String title, String user) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put("body", note);
        values.put("title", title);
        values.put("author", user);

        // insert row
        long id = db.insert("notes", null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertUser(String username, String email, String password) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        // insert row
        long id = db.insert("users", null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<NoteModel> getAllNotes(String author) {
        List<NoteModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM notes WHERE author = ? ORDER BY timestamp DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{author});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteModel note = new NoteModel();
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                note.setBody(cursor.getString(cursor.getColumnIndex("body")));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex("timestamp")));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public int getNotesCount() {
        String countQuery = "SELECT  * FROM notes";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    public Boolean authorize(String username, String password)
    {
        String selectQuery = "SELECT * FROM users WHERE username = ? OR email = ?  LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{username,password});
        cursor.moveToFirst();
        return password.equals(cursor.getString(cursor.getColumnIndex("password")));

    }


    public int updateNote(NoteModel note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("body", note.getBody());

        // updating row
        return db.update("notes", values, "id" + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(NoteModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("notes", "id = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

}
