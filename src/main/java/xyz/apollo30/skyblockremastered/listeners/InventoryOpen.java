package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryOpen implements Listener {

    private final SkyblockRemastered plugin;

    public InventoryOpen(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        // Fetching the player's data
        Player plr = (Player) e.getPlayer();
        PlayerObject po = plugin.playerManager.playerObjects.get(plr);

        // If the player is in their island or not.
        if (!plr.getWorld().getName().equals("islands/" + plr.getUniqueId().toString())) {
            // Do a little checcing
            List<String> containers = new ArrayList<>();
            containers.add("container.dropper");
            containers.add("container.chest");
            containers.add("container.dispenser");

            if (!po.isBlockBreak() && containers.contains(e.getInventory().getName())) {
                e.setCancelled(true);
            }
        }

    }
}