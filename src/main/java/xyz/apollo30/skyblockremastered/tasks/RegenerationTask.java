package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.InventoryManager;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.UUID;

public class RegenerationTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;

    public RegenerationTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player plr : Bukkit.getOnlinePlayers()) {

            PlayerObject po = plugin.playerManager.playerObjects.get(plr);

            // Health Regeneration
            if (po.getHealth() < po.getMaxHealth()) {
                if (plr.getFireTicks() <= 0) {
                    int health_regen = po.getHealth() + (int) ((po.getMaxHealth() * 0.01) + 1.5);
                    po.setHealth(Math.min(health_regen, po.getMaxHealth()));
                }
            }

            // Mana Regeneration
            if (po.getIntelligence() < po.getMaxIntelligence()) {
                int mana_regen = po.getIntelligence() + (int) ((po.getMaxIntelligence() * 0.01) + 1.5);
                po.setIntelligence(Math.min(mana_regen, po.getMaxIntelligence()));
            }

        }
    }

}
