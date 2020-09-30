package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LagManager extends BukkitRunnable {

    private SkyblockRemastered plugin;
    public HashMap<Entity, Long> indicator = new HashMap<>();
    public LagManager(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public void lagManager() {

    }

    @Override
    public void run() {

        // Island Unloader, checks if there are any players inside the world.
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (World world : Bukkit.getWorlds()) {
                if (world.getName().startsWith("islands/")) {
                    if (world.getPlayers().size() == 0) {
                        for (Player plr : Bukkit.getOnlinePlayers()) {
                            if (plr.isOp()) plr.sendMessage(Utils.chat("[DEBUG] Unloaded World: " + world.getName().replace("islands/", "")));
                        }
                        Bukkit.getServer().unloadWorld(world, true);
                    }
                }
            }
        }, 6000L, 6000L);

        // Checking for loose armor_stands
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
