package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class EntityCombust implements Listener {

    private final SkyblockRemastered plugin;

    public EntityCombust(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent e) {
        if (e.getEntity() instanceof Zombie) e.setCancelled(true);
        else if (e.getEntity() instanceof Skeleton) e.setCancelled(true);
    }
}
