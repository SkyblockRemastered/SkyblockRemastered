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

public class ActionBarTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;

    public ActionBarTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player plr : Bukkit.getOnlinePlayers()) {

            PlayerObject po = plugin.playerManager.playerObjects.get(plr);

            // Set Saturation
            plr.setSaturation(20);
            plr.setWalkSpeed(po.getSpeed() > 500 ? 1.0f : po.getSpeed() <= 0 ? 0.0f : (float) po.getSpeed() / 500);
            plr.setFlySpeed(po.getSpeed() / 2 > 500 ? 1.0f : po.getSpeed() / 2 <= 0 ? 0.0f : (float) po.getSpeed() / 2 / 500);

            String hp = Integer.toString(po.getHealth());
            String maxhp = Integer.toString(po.getMaxHealth());

            String defense = Integer.toString(po.getDefense());

            String intell = Integer.toString(po.getIntelligence());
            String maxIntell = Integer.toString(po.getMaxIntelligence());

            String actionbar = "&c" + hp + "/" + maxhp + InventoryManager.getUnicode("heart") + " HP" + "     &a" + defense + InventoryManager.getUnicode("defense") + " Defense" + "     &b" + intell + "/" + maxIntell + InventoryManager.getUnicode("intel") + " Intelligence";
            PacketManager.sendMessage(plr, Utils.chat(actionbar));

        }
    }

}
