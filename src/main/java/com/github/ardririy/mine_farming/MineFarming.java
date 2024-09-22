package com.github.ardririy.mine_farming;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public final class MineFarming extends JavaPlugin {
    private final HashMap<Position, Farmland> farmingData = new HashMap<Position, Farmland>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        loadFarmingData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // farmingDataを保存する。最終的にはDBにいれる必要があるかもしれんが、一旦はファイルに保存することとする
        saveDataToFile("farming_data.json");
    }

    public void loadFarmingData() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Position.class, new PositionSerializer()) // Positionのカスタムデシリアライザを登録
                .registerTypeAdapter(new TypeToken<HashMap<Position, Farmland>>() {}.getType(), new PositionMapDeserializer())
                .create();

        try (FileReader reader = new FileReader("farming_data.json")) {
            HashMap<Position, Farmland> dataFromFile = gson.fromJson(reader, new TypeToken<HashMap<Position, Farmland>>() {}.getType());
            if (dataFromFile != null) {
                farmingData.clear();
                farmingData.putAll(dataFromFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // データをファイルに保存する
    public void saveDataToFile(String filePath) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Position.class, new PositionSerializer()) // Positionのカスタムシリアライザを登録
                .create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(farmingData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Position, Farmland> getFarmingData() {
        return farmingData;
    }
}

class PositionMapDeserializer implements JsonDeserializer<Map<Position, Farmland>> {
    @Override
    public Map<Position, Farmland> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map<Position, Farmland> result = new HashMap<>();
        JsonObject jsonObject = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            String[] parts = key.replace("(", "").replace(")", "").split(", ");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);

            Position position = new Position(x, y, z);
            Farmland farmland = context.deserialize(value, Farmland.class);
            result.put(position, farmland);
        }
        return result;
    }
}
