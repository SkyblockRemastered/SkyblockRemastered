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
import xyz.apollo30.skyblockremastered.items.Weapons;

import java.util.*;

public class EnchantEvents extends BukkitRunnable implements Listener {

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
            arrow.remove();
        }
    }

    private void updateArrow(Arrow arrow) {
        outerloop:
        for (double i = .5; i < 100; i += .5) { // This will rapidly increase the range so you don't get so many mobs at one, and instead checks a few blocks at a time
            for (Entity e : arrow.getNearbyEntities(i, 3, i)) { // Gets ALL nearby entities using the loop variable above it
                if (e != arrow.getShooter()) { // Checks to make sure the entities isn't the shooter
                    if (e.getType().isAlive() && e instanceof LivingEntity) { // Checks to make sure the entity is alive
                        if (e.getType() == EntityType.PLAYER || e.getType() == EntityType.ARMOR_STAND) return;
                        Location from = arrow.getLocation(); // Gets the arrows location
                        Location to = e.getLocation().add(0, 2, 0); // Gets the entities Location
                        Vector vFrom = from.toVector(); // Converts the from location to a vector
                        Vector vTo = to.toVector(); // Converts the to location to a vector
                        Vector direction = vTo.subtract(vFrom).normalize(); // Subtracts the to variable to the from variable and normalizes it.
                        arrow.setVelocity(direction); // Sets the arrows newfound direction
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
