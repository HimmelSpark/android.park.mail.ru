package com.example.petrosadaman.codenotes.Models.Note;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter implements JsonSerializer<NoteModel>, JsonDeserializer<NoteModel> {
    @Override
    public NoteModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        System.out.println("JSON epta: " + json);
//        Type listType = new TypeToken<List<NoteModel>>(){}.getType();
//        List<NoteModel> listItemsDes = new Gson().fromJson(json, listType);
        return null;
    }

    @Override
    public JsonElement serialize(NoteModel src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("author", src.getAuthor());
        jsonObject.addProperty("title", src.getTitle());
        jsonObject.addProperty("body", src.getBody());
        return jsonObject;
    }
}
