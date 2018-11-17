package com.example.petrosadaman.codenotes;

import android.database.sqlite.SQLiteDatabase;
import com.example.petrosadaman.codenotes.DBCom.DBCommunication;

public class LoginValidator {
    public Boolean validate(String username, String password) {
        // some validation

//        return DBCommunication.checkUser(username, password);
        return true;
    }
}
