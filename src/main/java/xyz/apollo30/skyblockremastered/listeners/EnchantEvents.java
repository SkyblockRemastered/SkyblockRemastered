package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EnchantEvents implements Listener {

    private final SkyblockRemastered plugin;

    public EnchantEvents(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    List<Arrow> arrows = new ArrayList<>();

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        Arrow arrow = (Arrow) e.getProjectile();
        updateArrow(arrow);
        arrows.add(arrow);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            arrows.remove(arrow); // Remove Arrow
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        LivingEntity p = e.getPlayer();

        for (Arrow arrow : arrows) {
            List<UUID> uuids = arrow.getNearbyEntities(5, 5, 5).stream().map(Entity::getUniqueId).collect(Collectors.toList());
            if (uuids.contains(p.getUniqueId())) { // Check if player is next to an arrow
                updateArrow(arrow);
            }
        }
    }

    private void updateArrow(Arrow arrow) {
        LivingEntity target = null;
        double targetDistance = 0;
        // Search for nearest Player
        for (Entity nearest : arrow.getNearbyEntities(5, 100, 5)) {
            if (nearest instanceof LivingEntity && // Is a player
                    !nearest.getUniqueId().equals(((LivingEntity) arrow.getShooter()).getUniqueId())) { // Not the shooter
                double distance = arrow.getLocation().distance(nearest.getLocation());
                if (target == null ||
                        distance < targetDistance) {
                    targetDistance = distance;
                    target = (LivingEntity) nearest;
                }
            }
        }
        if (target != null) {
            try {
                arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector())); // Set direction of arrow
            } catch (Exception ignored) {

            }
        }
    }

}
