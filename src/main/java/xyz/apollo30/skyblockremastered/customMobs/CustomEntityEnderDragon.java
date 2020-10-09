package xyz.apollo30.skyblockremastered.customMobs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.entity.CreatureSpawnEvent;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class CustomEntityEnderDragon extends EntityEnderDragon {

    public CustomEntityEnderDragon(World world) {
        super(world);
    }

    public static EnderDragon spawn(Location loc, String name) {
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final CustomEntityEnderDragon customEntityEnderDragon = new CustomEntityEnderDragon(mcWorld);

        customEntityEnderDragon.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEntityEnderDragon.getBukkitEntity()).setRemoveWhenFarAway(true);
        mcWorld.addEntity(customEntityEnderDragon, CreatureSpawnEvent.SpawnReason.CUSTOM);
        customEntityEnderDragon.setCustomName(Utils.chat(name));
        customEntityEnderDragon.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(10.0D);
        customEntityEnderDragon.setCustomNameVisible(false);
        return (EnderDragon) customEntityEnderDragon.getBukkitEntity();
    }

}
