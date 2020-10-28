package xyz.apollo30.skyblockremastered.customMobs;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.events.Dragon;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomEnderDragon extends EntityEnderDragon {

    private boolean onPath = false;
    private Location destinationLoc = null;
    private final Location stoppedLoc = null;
    private final boolean finalBoss = false;
    private long locCooldown = 0;
    private Location currentLoc = new Location(getBukkitEntity().getWorld(), 1, 63, -4);
    private double curX = currentLoc.getX();
    private double curY = currentLoc.getY();
    private double curZ = currentLoc.getZ();

    public static int endCrystals = 0;
    public static boolean targetingPlayer = false;
    public static boolean isStopped = false;

    public Location getRandomLocation(Location entity, Location loc1, Location loc2) {

        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        double curX = entity.getX();
        double curY = entity.getY();
        double curZ = entity.getZ();

        double targetX = randomDouble(minX, maxX);
        double targetY = randomDouble(minY, maxY);
        double targetZ = randomDouble(minZ, maxZ);

        int threshold = 50; // Change this number to change how for away the dragon has to be to let the location be returned.

        while (Math.sqrt(Math.pow(curX - targetX, 2) + Math.pow(curY - targetY, 2) + Math.pow(curZ - targetZ, 2)) < threshold) {
            targetX = randomDouble(minX, maxX);
            targetY = randomDouble(minY, maxY);
            targetZ = randomDouble(minZ, maxZ);
        }

        return new Location(loc1.getWorld(), targetX, targetY, targetZ);
    }

    private void respawnCrystal() {
        List<Location> crystalSpawns = new ArrayList<>();
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -10.5, 44.5, 48.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -39.5, 69.5, 35.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -43.5, 44.5, -27.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), 36.5, 51.5, 38.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), 29.5, 36.5, 25.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), -25.5, 35.5, 22.5));
        crystalSpawns.add(new Location(getBukkitEntity().getWorld(), 1.5, 58.5, 42));

        Location loc = crystalSpawns.get((int) Math.floor(Math.random() * crystalSpawns.size()));
        for (Entity entity : loc.getChunk().getEntities()) {
            if (entity.getType() == EntityType.ENDER_CRYSTAL) return;
        }
        Entity endCrystal = loc.getWorld().spawnEntity(loc, EntityType.ENDER_CRYSTAL);
        Dragon.endCrystals.add(endCrystal.getLocation());
        broadcastWorld(getBukkitEntity().getWorld(), "&5❂ &dAn Ender Crystal has respawned!");
    }

    private void broadcastWorld(org.bukkit.World world, String msg) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(Utils.chat(msg));
        }
    }

    public CustomEnderDragon(World world) {
        super(world);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getBukkitEntity().isDead()) this.cancel();
                if (!onPath) {
                    if (locCooldown > new Date().getTime()) return;
                    currentLoc = getBukkitEntity().getLocation();
                    curX = currentLoc.getX();
                    curY = currentLoc.getY();
                    curZ = currentLoc.getZ();
                    destinationLoc = getRandomLocation(getBukkitEntity().getLocation(), new Location(getBukkitEntity().getWorld(), -24, 9, -33), new Location(getBukkitEntity().getWorld(), 32, 70, 29));
                    onPath = true;
                    // Utils.broadCast("Path Started at " + destinationLoc.getX() + ", " + destinationLoc.getY() + ", " + destinationLoc.getZ());
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (getBukkitEntity().isDead()) this.cancel();

                Utils.broadCast(Utils.isInZone(getBukkitEntity().getLocation(), new Location(getBukkitEntity().getWorld(), -46, 9, -48), new Location(getBukkitEntity().getWorld(), 54, 70, 38)) + " ");

                if (destinationLoc == null && !Utils.isInZone(getBukkitEntity().getLocation(), new Location(getBukkitEntity().getWorld(), -46, 9, -48), new Location(getBukkitEntity().getWorld(), 54, 70, 38))) {
                    destinationLoc = getRandomLocation(getBukkitEntity().getLocation(), new Location(getBukkitEntity().getWorld(), -24, 9, -33), new Location(getBukkitEntity().getWorld(), 32, 70, 29));
                    currentLoc = getBukkitEntity().getLocation();
                    curX = currentLoc.getX();
                    curY = currentLoc.getY();
                    curZ = currentLoc.getZ();
                    onPath = true;
                    locCooldown = 0;
                    targetingPlayer = false;
                    return;
                }

                if (!targetingPlayer || destinationLoc != null) {

                    double desX = destinationLoc.getX();
                    double desY = destinationLoc.getY();
                    double desZ = destinationLoc.getZ();

                    double speed = 0.6;
                    String name = getBukkitEntity().getCustomName();
                    if (name != null) {
                        if (name.toLowerCase().contains("young")) speed = .7;
                        else if (name.toLowerCase().contains("old")) speed = .55;
                    }

                    curX += (desX - curX) > 0 ? speed : -speed;
                    curY += (desY - curY) > 0 ? speed : -speed;
                    curZ += (desZ - curZ) > 0 ? speed : -speed;

                    if (curX + 5 >= desX && curY + 5 >= desY && curZ + 5 >= desZ) {
                        // Utils.broadCast("Path Ended at " + destinationLoc.getX() + ", " + destinationLoc.getY() + ", " + destinationLoc.getZ());
                        destinationLoc = null;
                        currentLoc = getBukkitEntity().getLocation();
                        onPath = false;
                        locCooldown = new Date().getTime() + 5000;
                        return;
                    }

                    Vector vec = getBukkitEntity().getLocation().toVector().subtract(destinationLoc.toVector());
                    Location loc = ((LivingEntity) getBukkitEntity()).getEyeLocation().setDirection(vec);

                    setPositionRotation(curX, curY, curZ, loc.getYaw(), loc.getPitch());
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 1L);

        new BukkitRunnable() {
            @Override
            public void run() {

                if (getBukkitEntity().isDead()) this.cancel();
                if (Math.random() > .3 && endCrystals < 5) respawnCrystal();

                // 1, 64, -3

            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 1200L, 1200L);
    }

    private void stopDragon() {

    }

    // Ignore this, this works fine.
    public static CustomEnderDragon spawn(Location loc, String name) {
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final CustomEnderDragon enderDragon = new CustomEnderDragon(mcWorld);

        enderDragon.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) enderDragon.getBukkitEntity()).setRemoveWhenFarAway(true);
        mcWorld.addEntity(enderDragon, CreatureSpawnEvent.SpawnReason.CUSTOM);
        enderDragon.setCustomName(Utils.chat(name));
        enderDragon.setCustomNameVisible(false);
        return enderDragon;
    }

    private static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }
}
