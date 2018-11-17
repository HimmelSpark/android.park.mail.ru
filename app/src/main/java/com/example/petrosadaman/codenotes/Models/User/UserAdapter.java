package com.example.petrosadaman.codenotes.Models.User;

public class UserAdapter {
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