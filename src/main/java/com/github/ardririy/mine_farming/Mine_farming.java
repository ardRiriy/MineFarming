package com.github.ardririy.mine_farming;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mine_farming extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}

