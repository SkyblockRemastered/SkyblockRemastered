package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.*;

public class EnchantEvents extends BukkitRunnable implements Listener {

    public EnchantEvents(SkyblockRemastered plugin) {
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
            arrow.remove();
        }
    }

    private void updateArrow(Arrow arrow) {
        outerloop:
        for (double i = .5; i < 100; i += .5) {
            for (Entity e : arrow.getNearbyEntities(i, 3, i)) {
                if (e != arrow.getShooter()) {
                    if (e.getType().isAlive() && e instanceof LivingEntity) {
                        if (e.getType() == EntityType.PLAYER || e.getType() == EntityType.ARMOR_STAND || e.getType() == EntityType.ENDERMAN) return;
                        Location from = arrow.getLocation();
                        Location to = e.getLocation().add(0, 2, 0);
                        Vector vFrom = from.toVector();
                        Vector vTo = to.toVector();
                        Vector direction = vTo.subtract(vFrom).normalize();
                        arrow.setVelocity(direction);
                        break outerloop;
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        for (Arrow arrow : arrows) {
            updateArrow(arrow);
        }
    }
}
