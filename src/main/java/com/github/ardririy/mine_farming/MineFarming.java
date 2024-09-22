package com.github.ardririy.mine_farming;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MineFarming extends JavaPlugin {
    private final HashMap<Position, Farmland> farmingData = new HashMap<Position, Farmland>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<Position, Farmland> getFarmingData() {
        return farmingData;
    }
}

