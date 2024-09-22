package com.github.ardririy.mine_farming;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PositionSerializer implements JsonSerializer<Position>, JsonDeserializer<Position> {
    @Override
    public JsonElement serialize(Position src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", src.x);
        jsonObject.addProperty("y", src.y);
        jsonObject.addProperty("z", src.z);
        return jsonObject;
    }

    @Override
    public Position deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int x = jsonObject.get("x").getAsInt();
        int y = jsonObject.get("y").getAsInt();
        int z = jsonObject.get("z").getAsInt();
        return new Position(x, y, z);
    }
}
