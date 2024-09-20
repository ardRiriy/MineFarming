package com.github.ardririy.mine_farming;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

    }
}
