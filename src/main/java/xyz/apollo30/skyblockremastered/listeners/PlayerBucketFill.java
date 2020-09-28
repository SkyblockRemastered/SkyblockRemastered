package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;

public class PlayerBucketFill implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerBucketFill(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e) {

        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        if (plr.getWorld().getName().equals("hub")) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
                return;
            }
            return;
        } else if (!plr.getWorld().getName().replace("islands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
                return;
            }
        }

    }

}
