package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LagPreventerTask extends BukkitRunnable {

    private SkyblockRemastered plugin;

    public LagPreventerTask(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        // Island Unloader, checks if there are any players inside the islands.
        for (World world : Bukkit.getWorlds()) {
            if (world.getName().startsWith("islands/")) {
                if (world.getPlayers().size() == 0) {
                    for (Player plr : Bukkit.getOnlinePlayers()) {
                        if (plr.isOp())
                            plr.sendMessage(Utils.chat("[DEBUG] Unloaded World: " + world.getName().replace("islands/", "")));
                    }
                    Bukkit.getServer().unloadWorld(world, true);
                }
            }
        }

        // Checking for loose armor_stands
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getCustomName() != null && entity.getCustomName().startsWith(Utils.chat("&8[&7Lv")) && entity.getCustomName().endsWith(Utils.chat("&c" + GuiUtils.getUnicode("heart")))) {
                    if (entity.getVehicle() == null) entity.remove();
                }
            }
        }
        if (plugin.indicator.size() != 0) {
            for (Map.Entry<Entity, Long> entities : plugin.indicator.entrySet()) {
                if ((new Date().getTime() - entities.getValue()) > 2000) {
                    entities.getKey().remove();
                }
            }
        }

    }
}
