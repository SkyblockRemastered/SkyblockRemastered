package xyz.apollo30.skyblockremastered.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BoardManager {

    private final SkyblockRemastered plugin;

    public BoardManager(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }


    public void rePlayerScoreboard() {

        for (Player plr : Bukkit.getOnlinePlayers()) {

            PlayerObject po = plugin.playerManager.playerObjects.get(plr);

            ScoreboardManager sM = plugin.getServer().getScoreboardManager();
            Scoreboard sb = sM.getNewScoreboard();
            Objective obj = sb.registerNewObjective("Player", "Scoreboard");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);


            new BukkitRunnable() {

                @Override
                public void run() {


                }
            }.runTaskTimer(plugin, 0, 10);
        }


    }
}
