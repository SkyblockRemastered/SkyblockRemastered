package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.util.Date;

public class ConstantTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;

    public ConstantTask(SkyblockRemastered plugin) {
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

            // Checking if the player's inventory is full.
            if ((plr.getInventory().firstEmpty() == -1)) {
                if (po.getLastInvFullNotification() > new Date().getTime()) return;
                plr.sendMessage(Utils.chat("&cYour inventory is full!"));
                PacketManager.sendTitle(plr, 0, 180, 40, Utils.chat("&cInventory Full!"), "");
                plr.playSound(plr.getLocation(), Sound.CHEST_OPEN, 1000L, 5L);
                po.setLastInvFullNotification(new Date().getTime() + 30000);
            }
        }
    }
}


