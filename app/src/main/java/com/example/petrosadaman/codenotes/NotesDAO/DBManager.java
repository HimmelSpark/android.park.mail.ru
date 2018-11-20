package com.example.petrosadaman.codenotes.NotesDAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class DBCommunication {



    public DBCommunication(){
        nodesDB =  openOrCreateDatabase("nodes.db", null, null);
        nodesDB.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR(200), password VARCHAR(200))");
        nodesDB.execSQL("CREATE TABLE IF NOT EXISTS notes (name VARCHAR(100), note TEXT, user VARCHAR(200) )");
        nodesDB.execSQL("INSERT INTO users VALUES('admin', 'admin')");
    }

    public Boolean checkUser(String user, String password){
        try {
            Cursor us = nodesDB.rawQuery("SELECT * FROM users WHERE username = ? ;", new String[]{user});
            return  (us.getString(us.getColumnIndex("password")).equals(password)) ;

        }catch (Exception exp){
            return false;
        }
    }



}
