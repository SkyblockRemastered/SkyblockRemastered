package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.managers.MobManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class EntityDamage implements Listener {

    private final SkyblockRemastered plugin;

    public EntityDamage(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
            return;
        }

        // Preventing damage from a players island.
        if (e.getEntity() instanceof Player) {
            Player plr = (Player) e.getEntity();
            if (plr.getWorld().getName().startsWith("islands/")) {
                if (!plr.getWorld().getName().replace("islands/", "").equals(plr.getUniqueId().toString())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        try {
            // Checking if the damaged Entity was an ARMOR_STAND, since .setInvulnerable doesn't work.
            if (e.getEntityType() == EntityType.ARMOR_STAND) e.setCancelled(true);

            // Checking if the entity is a creature and not an arrow or a dropped item
            if (e.getEntity() instanceof LivingEntity) {

                MobObject mo = plugin.mobManager.mobObjects.get(e.getEntity());
                PlayerObject player_data = plugin.playerManager.getPlayerData((Player) e.getEntity());
                if (mo == null && e.getEntityType() != EntityType.PLAYER) e.getEntity().remove();
                String damage_type = "normal";
                int damage = 5;

                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
                if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    if (e.getEntityType() == EntityType.PLAYER) {
                        player_data.setHealth(0);
                    }
                }

                EntityDamageEvent.DamageCause[] uniqueCauses = {
                        EntityDamageEvent.DamageCause.POISON,
                        EntityDamageEvent.DamageCause.FIRE_TICK,
                        EntityDamageEvent.DamageCause.FIRE,
                        EntityDamageEvent.DamageCause.LAVA,
                        EntityDamageEvent.DamageCause.WITHER,
                        EntityDamageEvent.DamageCause.DROWNING
                };

                // Checks if the damage cause was unique to have its own custom color.
                for (EntityDamageEvent.DamageCause cause : uniqueCauses) {
                    if (e.getCause() == cause) {
                        damage_type = cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA ? "fire" : cause == EntityDamageEvent.DamageCause.DROWNING ? "water" : cause == EntityDamageEvent.DamageCause.WITHER ? "wither" : cause == EntityDamageEvent.DamageCause.POISON ? "poison" : "normal";
                        damage = cause == EntityDamageEvent.DamageCause.LAVA ? 20 : 5;
                        break;
                    }
                }

                // Shows the damage dealt using armor stands.
                Utils.damageIndicator(e.getEntity(), damage, damage_type, plugin);
                e.setDamage(0);

                if (player_data != null) {
                    player_data.subtractHealth(damage);
                    int health = player_data.getHealth();
                    if (health <= 0) {
                        ((LivingEntity) e.getEntity()).setHealth(0);
                        player_data.resetHealth();
                    }
                    // Utils.broadCast(Integer.toString(health));
                    return;
                }

                mo.subtractHealth(damage);
                if (mo.getHealth() <= 0) ((LivingEntity) e.getEntity()).setHealth(0);
                e.getEntity().getPassenger().setCustomName(Utils.chat(Utils.getDisplayHP(mo.getLevel(), mo.getName(), mo.getHealth(), mo.getMaxHealth())));
            }
        } catch (Exception err) {

        }
    }
}
