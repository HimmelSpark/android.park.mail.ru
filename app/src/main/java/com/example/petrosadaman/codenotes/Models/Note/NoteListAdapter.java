package com.example.petrosadaman.codenotes.Models.Note;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NoteListAdapter implements JsonSerializer<List<NoteModel>>, JsonDeserializer<List<NoteModel>> {
    @Override
    public List<NoteModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        System.out.println("JSON list epta: " + json);
//        Type listType = new TypeToken<List<NoteModel>>(){}.getType();
//        List<NoteModel> listItemsDes = new Gson().fromJson(json, listType);
        return null;
    }

    @Override
    public JsonElement serialize(List<NoteModel> src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
