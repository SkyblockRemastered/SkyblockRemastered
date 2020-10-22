package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.managers.PacketManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class ActionBarTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;

    public ActionBarTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player plr : Bukkit.getOnlinePlayers()) {

            PlayerObject po = plugin.playerManager.playerObjects.get(plr);

            String hp = Integer.toString(po.getHealth());
            String maxhp = Integer.toString(po.getMaxHealth());

            String defense = Integer.toString(po.getDefense());

            String intell = Integer.toString(po.getIntelligence());
            String maxIntell = Integer.toString(po.getMaxIntelligence());

            String actionbar = "&c" + hp + "/" + maxhp + GuiUtils.getUnicode("heart") + " HP" + "     &a" + defense + GuiUtils.getUnicode("defense") + " Defense" + "     &b" + intell + "/" + maxIntell + GuiUtils.getUnicode("intel") + " Intelligence";
            PacketManager.sendMessage(plr, Utils.chat(actionbar));
        }
    }

}
