package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class EntityExplode implements Listener {

    private final SkyblockRemastered plugin;

    public EntityExplode(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {

        if (e.getEntityType() != EntityType.PRIMED_TNT) e.setCancelled(true);
        else {
            if (e.getEntity().getWorld().getName().startsWith("islands/")) return;
        }

    }

}
