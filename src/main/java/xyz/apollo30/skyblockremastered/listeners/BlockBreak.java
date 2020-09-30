package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.Random;

public class BlockBreak implements Listener {

    private final SkyblockRemastered plugin;

    public BlockBreak(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        // Fetching the player's data
        Player plr = e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // Check if the player is in the hub or not.
        if (plr.getWorld().getName().equals("hub")) {
            // Checking if their settings is allowed.
            if (!po.isBlockBreak()) {
                // Wheat Detection
                if(Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), 3, 255, -194), new Location(plr.getWorld(), 81, 0, -116))) {
                    Utils.broadCast("yes.");

                    if (e.getBlock().getType() != Material.CROPS) {
                        e.setCancelled(true);
                        Utils.broadCast(e.getBlock().getType().toString());
                    }

                    // Oak Wood Detection
                } else if (Utils.isInZone(plr.getLocation(), new Location(plr.getWorld(), -236, 255, -82), new Location(plr.getWorld(), -90, 0, 34))) {
                    Utils.broadCast("yes2");
                    if (e.getBlock().getType() != Material.LOG && e.getBlock().getTypeId() != 0) {
                        e.setCancelled(true);
                        Utils.broadCast(e.getBlock().getType().toString());
                    }

                }
            }


            // If the player is in their island
        } else if (!plr.getWorld().getName().replace("islands/", "").equals(plr.getUniqueId().toString())) {
            if (!po.isBlockBreak()) {
                e.setCancelled(true);
                return;
            }
        }
    }

}
