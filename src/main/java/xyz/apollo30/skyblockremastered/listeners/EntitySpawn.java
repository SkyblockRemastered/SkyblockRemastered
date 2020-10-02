package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class EntitySpawn implements Listener {

    private final SkyblockRemastered plugin;

    public EntitySpawn(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {

        if (e.getEntity() instanceof LivingEntity) {
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

        plugin.mobManager.createMob(e.getEntity(), e.getEntity().getName());

    }

}
