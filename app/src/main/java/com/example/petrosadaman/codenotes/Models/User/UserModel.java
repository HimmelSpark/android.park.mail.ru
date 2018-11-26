package com.example.petrosadaman.codenotes.Models.User;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("name")
    private String username = "";

    private String email = "";

    private String password = "";

    public static final String CREATE_TABLE =
            "CREATE TABLE  users ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " username CHAR(256), email CHAR(256), password TEXT "
                    + ")";


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
