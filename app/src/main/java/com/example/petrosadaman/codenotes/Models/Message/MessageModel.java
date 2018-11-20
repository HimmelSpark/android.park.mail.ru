package com.example.petrosadaman.codenotes.Models.Message;

public class MessageModel {

    //TODO переделать в перечисление

    private String message;

    MessageModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
