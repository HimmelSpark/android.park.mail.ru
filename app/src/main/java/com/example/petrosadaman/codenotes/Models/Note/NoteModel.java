package com.example.petrosadaman.codenotes.Models.Note;

import com.google.gson.annotations.SerializedName;

public class NoteModel {
    @SerializedName("id")
    private Integer id = 0;
    @SerializedName("author")
    private String author = "";
    @SerializedName("title")
    private String title = "";
    @SerializedName("body")
    private String body = "";
    @SerializedName("Timestamp")
    private String timestamp = "";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS notes ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " author CHAR(256), title CHAR(256), body TEXT," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {return timestamp;}

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
