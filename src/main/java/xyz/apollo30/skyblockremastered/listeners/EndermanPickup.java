package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class EndermanPickup implements Listener {

    private final SkyblockRemastered plugin;

    public EndermanPickup(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void endermanPickup(EntityChangeBlockEvent e) {

        if (e.getEntity() instanceof Enderman) {
            e.setCancelled(true);
        }

    }

}
