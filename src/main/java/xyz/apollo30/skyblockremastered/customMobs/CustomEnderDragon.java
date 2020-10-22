package xyz.apollo30.skyblockremastered.customMobs;

import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class CustomEnderDragon extends EntityEnderDragon {

    public CustomEnderDragon(World world) {
        super(world);
    }

    public static CustomEnderDragon spawn(Location loc, String name) {
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final CustomEnderDragon enderDragon = new CustomEnderDragon(mcWorld);

        enderDragon.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) enderDragon.getBukkitEntity()).setRemoveWhenFarAway(true);
        mcWorld.addEntity(enderDragon, CreatureSpawnEvent.SpawnReason.CUSTOM);
        enderDragon.setCustomName(Utils.chat(name));

        enderDragon.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(500D);
        enderDragon.setCustomNameVisible(false);
        return enderDragon;
    }

    public static void randomizeMovement(CustomEnderDragon enderDragon) {

        LivingEntity eDragon = (LivingEntity) enderDragon.getBukkitEntity();
        Location loc = eDragon.getLocation();

        Location loc1 = loc.add(-20, 0, -20);
        Location loc2 = loc.add(20, 0, 20);

        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(5, 40);
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(5, 40);
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        Location target = new Location(eDragon.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));

        if (!Utils.isInZone(target, new Location(eDragon.getWorld(), -38, 49, -38), new Location(loc.getWorld(), 45, 5, 37))) {
            Player plr = (Player) eDragon.getWorld().getPlayers().toArray()[(int) Math.floor(Math.random() * eDragon.getWorld().getPlayers().size())];
            if (Utils.isInZone(plr.getLocation(), new Location(loc.getWorld(), -64, 101, -68), new Location(plr.getWorld(), 72, 1, 67))) {
                enderDragon.getNavigation().a(plr.getLocation().getX(), plr.getLocation().getY(), plr.getLocation().getZ());
            } else enderDragon.getNavigation().a(23, 23, -4);
            enderDragon.getNavigation().a(randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ), 1);
        } else enderDragon.getNavigation().a(target.getX(), target.getY(), target.getZ(), 1);
        Utils.broadCast("[DEBUG] " + target.getX() + ", " + target.getY() + ", " + target.getZ());
    }

    private static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

}
