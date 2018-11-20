package com.example.petrosadaman.codenotes.Models.User;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class UserAdapter implements JsonSerializer<UserModel>, JsonDeserializer<UserModel> {

    @Override
    public UserModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //TODO написать десериалайзер!!!
        System.out.println(json.toString());
        return null;
    }

    @Override
    public JsonElement serialize(UserModel src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getUsername());
        jsonObject.addProperty("password", src.getPassword());
        return jsonObject;
    }
}


//Если вдруг пригодится адаптер вот пример
//Адаптер для GSON, позволяющий парсить enum поля в Java объектах. В данном случае поле "пол" у
//пользователя.

/*
class GenderAdapter implements JsonDeserializer<Gender> {

    @Override
    public Gender deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            final JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                int value = primitive.getAsNumber().intValue();
                switch (value) {
                    case 0:
                        return Gender.UNKNOWN;
                    case 1:
                        return Gender.FEMALE;
                    case 2:
                        return Gender.MALE;
                }
            }
        }
        return Gender.UNKNOWN;
    }
}
 */