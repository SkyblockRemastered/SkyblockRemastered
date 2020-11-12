package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class SpawnEvents implements Listener {

    private final SkyblockRemastered plugin;

    public SpawnEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if (e.getEntity() != null) {
            Entity entity = e.getEntity();
            EntityType type = e.getEntityType();

            // If the entity's armor stand just return
            if (type == EntityType.ARMOR_STAND)
                return;

            if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
                if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
                    if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DISPENSE_EGG) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }

            // Return if its a baby zombie.
            if (type == EntityType.ZOMBIE) {
                Zombie zom = (Zombie) e.getEntity();
                if (zom.isBaby())
                    e.setCancelled(true);
            }
        }
    }
}
