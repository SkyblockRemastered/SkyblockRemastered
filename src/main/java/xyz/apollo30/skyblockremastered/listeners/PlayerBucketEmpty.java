package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;

public class PlayerBucketEmpty implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerBucketEmpty(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {

        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
                return;
            }
        }

        // If the player is in their island
        if (!plr.getWorld().getName().replace("islands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
            }
        }

    }

}
