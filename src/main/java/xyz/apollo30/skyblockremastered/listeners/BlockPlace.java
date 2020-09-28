package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.Random;

public class BlockPlace implements Listener {

    private final SkyblockRemastered plugin;

    public BlockPlace(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // Island Border
        if (plr.getWorld().getName().startsWith("islands/")) {
            if (!Utils.isInZone(e.getBlock().getLocation(), new Location(plr.getWorld(), -80, 255, -80), new Location(plr.getWorld(), 80, 0, 80))){
                plr.sendMessage(Utils.chat("&cYou've reached the world border.\n&aTo expand it, you can use your &2gems&a by heading to the Community Center!"));
                e.setCancelled(true);
            }
        }

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
                return;
            }
        }
    }
}
