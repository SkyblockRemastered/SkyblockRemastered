package xyz.apollo30.skyblockremastered.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.MobObject;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.guis.GUIs;
import xyz.apollo30.skyblockremastered.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScoreboardTask extends BukkitRunnable {

    private final SkyblockRemastered plugin;

    public ScoreboardTask(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        for (Player plr : Bukkit.getOnlinePlayers()) {

            Scoreboard sb = plugin.getServer().getScoreboardManager().getNewScoreboard();
            Objective obj = sb.registerNewObjective("dummy", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            String title = "&e&lSKYBLOCK";
            obj.setDisplayName(Utils.chat(title));

            PlayerObject po = PlayerManager.playerObjects.get(plr);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter d = DateTimeFormatter.ofPattern("dd");
            LocalDateTime now = LocalDateTime.now();

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            String[] names = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            String coins_gained = "";
            if (po.getCoins_gained() > 0) {
                coins_gained = " &e(+" + String.format("%,.0f", po.getCoins_gained()) + ")";
                plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1F, 10F);
                po.setPurse(po.getPurse() + po.getCoins_gained());
            }

            String purse = "&fPurse: &6" + Utils.doubleFormat(po.getPurse()) + coins_gained;
            String bits = "&fBits: &b" + Utils.doubleFormat(po.getBits());

            String number = d.format(now).startsWith("0") ? d.format(now) : d.format(now);
            List<String> contents = new ArrayList<>();
            contents.add("&7" + dtf.format(now));
            contents.add("&f&7 ");
            contents.add(" &f" + names[dayOfWeek - 1] + " " + number + Utils.getDayOfMonthSuffix(Integer.parseInt(d.format(now))));
            contents.add(" &7⋄ " + Utils.getLocation(plr));
            contents.add("&2&8 ");
            contents.add(purse.length() > 40 ? purse.substring(0, 40) : purse);
            contents.add(bits);

            if (SkyblockRemastered.so.isDragonFight()) {
                if (!plr.getWorld().getName().equals("hub")) return;
                MobObject mo = SkyblockRemastered.so.getEnderDragon();
                if (mo != null) {
                    if (SkyblockRemastered.dragonEvent.playerDamage.get(plr) != null) {
                        contents.add("&7&8 ");
                        contents.add("&fDragon HP: &a" + Utils.doubleFormat(mo.getHealth() < 0 ? (double) 0 : (double) mo.getHealth()) + " &c" + GUIs.getUnicode("heart"));
                        contents.add("&fYour Damage: &c" + Utils.doubleFormat(SkyblockRemastered.dragonEvent.playerDamage.get(plr)));
                    } else SkyblockRemastered.dragonEvent.playerDamage.put(plr, (double) 0);
                }
            }

            contents.add("&2&5");
            contents.add(SkyblockRemastered.so.isHypixelip() ? "&ewww.hypixel.net" : "&eplay.apollo30.xyz");

            int cycle = contents.size();
            for (String list : contents) {
                obj.getScore(Utils.chat(list)).setScore(cycle);
                cycle--;
            }
            plr.setScoreboard(sb);
            po.setCoins_gained(0);
        }
    }

}
