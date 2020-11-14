package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

import java.util.ArrayList;
import java.util.List;

public class MobSpawnTask extends BukkitRunnable {

    public SkyblockRemastered plugin;
    public MobSpawnTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    private final List<LivingEntity> entities = new ArrayList<>();

    @Override
    public void run() {

        if (plugin.endSpawnpoints.size() == 0) return;

        entities.removeIf(Entity::isDead);

        int foundZealots = (int) entities.stream().filter(entity -> !entity.isDead() && entity.getPassenger() != null && !entity.getPassenger().getCustomName().isEmpty() && entity.getPassenger().getCustomName().contains(Utils.chat("&cZealot"))).count();
        int foundWatchers = (int) entities.stream().filter(entity -> !entity.isDead() && entity.getPassenger() != null && !entity.getPassenger().getCustomName().isEmpty() && entity.getPassenger().getCustomName().contains(Utils.chat("&cWatcher"))).count();
        int foundObsidianDefenders = (int) entities.stream().filter(entity -> !entity.isDead() && entity.getPassenger() != null && !entity.getPassenger().getCustomName().isEmpty() && entity.getPassenger().getCustomName().contains(Utils.chat("&cObsidian Defender"))).count();

        List<Location> locs = new ArrayList<>(plugin.endSpawnpoints.keySet());

        while (foundZealots < 25) {
            Location loc = locs.get((int) Math.floor(Math.random() * plugin.endSpawnpoints.size()));
            Enderman enderman = (Enderman) loc.getWorld().spawnEntity(loc.add(0, 3, 0), EntityType.ENDERMAN);
            plugin.mobManager.createMob(enderman, "Zealot");
            entities.add(enderman);
            foundZealots++;
        }

        while (foundWatchers < 17) {
            Location loc = locs.get((int) Math.floor(Math.random() * plugin.endSpawnpoints.size()));
            Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc.add(0, 3, 0), EntityType.SKELETON);
            plugin.mobManager.createMob(skeleton, "Watcher");
            entities.add(skeleton);
            foundWatchers++;
        }

        while (foundObsidianDefenders < 17) {
            Location loc = locs.get((int) Math.floor(Math.random() * plugin.endSpawnpoints.size()));
            Skeleton witherSkeleton = (Skeleton) loc.getWorld().spawnEntity(loc.add(0, 3, 0), EntityType.SKELETON);
            witherSkeleton.setSkeletonType(Skeleton.SkeletonType.WITHER);
            witherSkeleton.getEquipment().setItemInHand(new ItemStack(Material.AIR));
            plugin.mobManager.createMob(witherSkeleton, "Obsidian Defender");
            entities.add(witherSkeleton);
            foundObsidianDefenders++;
        }
    }
}
