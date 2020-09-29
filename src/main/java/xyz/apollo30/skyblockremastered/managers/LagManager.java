package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LagManager implements Runnable {

    public static int TICK_COUNT = 0;
    public static long[] TICKS = new long[600];
    public static long LAST_TICK = 0L;
    public HashMap<Entity, Long> indicator = new HashMap<>();

    private SkyblockRemastered plugin;
    public LagManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int ticks) {
        if (TICK_COUNT < ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    public static long getElapsed(int tickID) {
        if (TICK_COUNT - tickID >= TICKS.length) {
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void run() {
        TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT += 1;
    }

    public void lagManager() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getCustomName() != null && entity.getCustomName().startsWith(Utils.chat("&8[&7Lv")) && entity.getCustomName().endsWith(Utils.chat("&c" + GuiUtils.getUnicode("heart")))) {
                        if (entity.getVehicle() == null) entity.remove();
                    }
                }
            }
            if (indicator.size() != 0) {
                for (Map.Entry<Entity, Long> entities : indicator.entrySet()) {
                    if ((new Date().getTime() - entities.getValue()) > 2000) {
                        entities.getKey().remove();
                    }
                }
            }
        }, 10L, 10L);
    }
}
