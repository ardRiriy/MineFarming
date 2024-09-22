package com.github.ardririy.mine_farming;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class EventListener implements Listener {
    private final MineFarming plugin;

    public EventListener(MineFarming plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        HashMap<Position, Farmland> farmlandData = plugin.getFarmingData();
        Block clickedBlock = event.getClickedBlock();
        ItemStack item = event.getItem();
        if (event.getAction().isRightClick()) {
            if (clickedBlock != null && (clickedBlock.getType() == Material.GLASS || clickedBlock.getType() == Material.DIRT)) {
                // 土 or 草に右クリックした場合
                if (item != null && (item.getType().toString().contains("HOE"))) {
                    // クワを持っているなら耕したと見て初期化処理
                    Position pos = new Position(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());
                    Farmland farmland = new Farmland(pos);
                    farmlandData.put(pos, farmland);
                }
            } else if(clickedBlock != null && (clickedBlock.getType() == Material.FARMLAND)){
                // 耕地に右クリックした場合
                if (item != null && (item.getType() == Material.BOOK)) {
                    // 本を持っているなら耕地の情報を表示
                    Position pos = new Position(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());
                    Farmland farmland = farmlandData.get(pos);
                    Player player = event.getPlayer();
                    if(farmland == null) {
                        player.sendMessage("耕地の情報が見つかりません(Maybe bug?)");
                    } else {
                        player.sendMessage("耕地 " + pos + " :");
                        player.sendMessage("  水分量: " + farmland.moisture);
                        player.sendMessage("  窒素: " + farmland.nitrogen);
                        player.sendMessage("  リン: " + farmland.phosphorus);
                        player.sendMessage("  カリウム: " + farmland.kalium);
                    }
                }
            }
        }
    }
}
