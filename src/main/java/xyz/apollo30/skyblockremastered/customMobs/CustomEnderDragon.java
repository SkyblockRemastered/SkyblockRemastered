package xyz.apollo30.skyblockremastered.customMobs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CustomEnderDragon extends EntityEnderDragon {

    private BukkitTask checkTask = null;
    private BukkitTask task = null;
    private boolean onPath = false;
    private Location destinationLoc = null;

    public CustomEnderDragon(World world) {
        super(world);

        checkTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (getBukkitEntity().isDead()) checkTask.cancel();
                if (!onPath) {
                    // -54, 78, -47 47, 11, 47
                    destinationLoc = randomLocation((LivingEntity) getBukkitEntity());
                    onPath = true;
                    Utils.broadCast("Path Started at " + destinationLoc.getX() + ", " + destinationLoc.getY() + ", " + destinationLoc.getZ());
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 1L);

        task = new BukkitRunnable() {

            final Location currentLoc = new Location(getBukkitEntity().getWorld(), 1, 63, -4);
            double curX = currentLoc.getX();
            double curY = currentLoc.getY();
            double curZ = currentLoc.getZ();

            @Override
            public void run() {

                if (getBukkitEntity().isDead()) task.cancel();

                if (destinationLoc != null) {

                    double desX = destinationLoc.getX();
                    double desY = destinationLoc.getY();
                    double desZ = destinationLoc.getZ();

                    curX += (desX - curX) > 0 ? 1 : -1;
                    curY += (desY - curY) > 0 ? 1 : -1;
                    curZ += (desZ - curZ) > 0 ? 1 : -1;

                    if (curX + 5 >= desX && curY + 5 >= desY && curZ + 5 >= desZ) {
                        Utils.broadCast("Path Ended at " + destinationLoc.getX() + ", " + destinationLoc.getY() + ", " + destinationLoc.getZ());
                        destinationLoc = null;
                        onPath = false;
                    }

                    Vector vec = getBukkitEntity().getLocation().toVector().subtract(destinationLoc.toVector());
                    Location loc = ((LivingEntity) getBukkitEntity()).getEyeLocation().setDirection(vec);

                    setPositionRotation(curX, curY, curZ, loc.getYaw(), loc.getPitch());
                }
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(SkyblockRemastered.class), 0L, 1L);
    }

    private Location randomStopLocation(LivingEntity entity) {
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location(entity.getWorld(), -34, 87, -37));
        locationList.add(new Location(entity.getWorld(), -1, 73, -31));
        locationList.add(new Location(entity.getWorld(), 18, 51, -43));
        locationList.add(new Location(entity.getWorld(), 19, 43, -14));
        locationList.add(new Location(entity.getWorld(), -11, 35, -6));
        locationList.add(new Location(entity.getWorld(), -33, 43, -4));
        locationList.add(new Location(entity.getWorld(), -40, 47, 13));
        locationList.add(new Location(entity.getWorld(), -37, 27, -24));
        locationList.add(new Location(entity.getWorld(), -45, 22, -18));
        locationList.add(new Location(entity.getWorld(), -16, 18, -59));
        locationList.add(new Location(entity.getWorld(), -2, 14, -56));
        locationList.add(new Location(entity.getWorld(), 50, 14, -44));
        locationList.add(new Location(entity.getWorld(), 28, 22, -2));
        locationList.add(new Location(entity.getWorld(), -11, 35, -6));
        locationList.add(new Location(entity.getWorld(), -33, 43, -4));
        locationList.add(new Location(entity.getWorld(), -40, 47, 13));
        locationList.add(new Location(entity.getWorld(), -14, 60, 8));
        locationList.add(new Location(entity.getWorld(), -1, 74, -31));
        locationList.add(new Location(entity.getWorld(), 20, 76, -21));
        locationList.add(new Location(entity.getWorld(), 41, 81, -4));
        locationList.add(new Location(entity.getWorld(), 59, 55, 16));
        locationList.add(new Location(entity.getWorld(), 18, 84, 29));
        locationList.add(new Location(entity.getWorld(), 59, 55, 16));
        locationList.add(new Location(entity.getWorld(), 17, 60, 48));
        locationList.add(new Location(entity.getWorld(), -5, 44, 43));
        locationList.add(new Location(entity.getWorld(), -32, 73, 40));
        locationList.add(new Location(entity.getWorld(), 41, 81, -4));
        locationList.add(new Location(entity.getWorld(), -34, 87, -37));
        locationList.add(new Location(entity.getWorld(), -14, 81, 3));
        locationList.add(new Location(entity.getWorld(), -32, 73, 40));
        locationList.add(new Location(entity.getWorld(), -57, 11, 13));
        locationList.add(new Location(entity.getWorld(), 39, 14, 35));
        locationList.add(new Location(entity.getWorld(), 63, 25, -4));
        locationList.add(new Location(entity.getWorld(), 34, 12, 1));
        locationList.add(new Location(entity.getWorld(), -19, 16, -3));
        locationList.add(new Location(entity.getWorld(), 15, 16, -1));

        int index = new Random().nextInt(locationList.size());
        return locationList.get(index);
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

    // This here is a function has obtains attempt #2 and #3 of moving the dragon to a specific location
    public static void randomizeMovement(CustomEnderDragon enderDragon) {

        // This just randomizes the moment from a range of 20 blocks.
        LivingEntity eDragon = (LivingEntity) enderDragon.getBukkitEntity();
        Location loc = eDragon.getLocation();

        Location loc1 = loc.add(-20, 0, -20);
        Location loc2 = loc.add(20, 0, 20);

        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        Location target = new Location(eDragon.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));

        // Attempt #2 (failed)
        enderDragon.getNavigation().a(10, 52, -2); // target.getX(), target.getY(), target.getZ()
        // Attempt #3 (failed)
        ((CraftEnderDragon) enderDragon.getBukkitEntity()).getHandle().getNavigation().a(10, 52, -2); // target.getX(), target.getY(), target.getZ()
        // Logs the randomized movement (Doesn't actually move to the specified block)
        Utils.broadCast("[DEBUG] " + target.getX() + ", " + target.getY() + ", " + target.getZ());
    }

    /*
    Ignore the code below, these work fine.
     */
    private static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

    private static Object getPrivateField(String fieldName, Object object) {
        Field field;
        Object o = null;
        try {
            field = PathfinderGoalSelector.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

}
