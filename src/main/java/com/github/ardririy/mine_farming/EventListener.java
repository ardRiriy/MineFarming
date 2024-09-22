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
                Position pos = new Position(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());
                Farmland farmland = farmlandData.get(pos);
                Player player = event.getPlayer();
                if (farmland == null) {
                    player.sendMessage("耕地の情報が見つかりません(Maybe bug?)");
                    return;
                }

                if(item != null) {
                    switch (item.getType()) {
                        case Material.BOOK -> {
                            // 本を持っているなら耕地の情報を表示
                            player.sendMessage("耕地 " + pos + " :");
                            player.sendMessage("  水分量: " + farmland.moisture);
                            player.sendMessage("  窒素: " + farmland.nitrogen);
                            player.sendMessage("  リン: " + farmland.phosphorus);
                            player.sendMessage("  カリウム: " + farmland.kalium);
                        }
                        case Material.POTION -> {
                            // (水入りに限らないが)瓶: 水 +20
                            event.setCancelled(true); // 水を飲むモーションを止める
                            farmland.moisture += 20;
                        }
                        case Material.ROTTEN_FLESH -> {
                            // 腐った肉: (5, 23, 0, 6)
                            farmland.moisture += 5;
                            farmland.nitrogen += 23;
                            farmland.kalium += 6;
                        }
                        case Material.SUGAR_CANE -> {
                            // サトウキビ: (3, 2, 21, 4)
                            farmland.moisture += 3;
                            farmland.nitrogen += 5;
                            farmland.phosphorus += 21;
                            farmland.kalium += 4;
                        }
                        case Material.BONE_MEAL -> {
                            // 骨: (-8, 1, 0, 21)
                            farmland.moisture = Math.max(farmland.moisture - 8, 0);
                            farmland.nitrogen += 1;
                            farmland.kalium += 21;
                        }
                        default -> { }
                    }
                }
            }
        }
    }
}
