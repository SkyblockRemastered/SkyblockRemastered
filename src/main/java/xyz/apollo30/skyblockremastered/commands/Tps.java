package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.LagManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Tps implements CommandExecutor {

    private final xyz.apollo30.skyblockremastered.SkyblockRemastered plugin;

    public Tps(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("tps").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {

        long tps = Math.round(LagManager.getTPS());
        double lag = Math.round(100 - ((1.0D - Integer.parseInt(String.valueOf(tps)) / 20.0D) * 100.0D));

        String color = "&a";
        if (Integer.parseInt(String.valueOf(tps)) < 10) color = "&c";
        else if (Integer.parseInt(String.valueOf(tps)) < 17) color = "&e";
        else if (Integer.parseInt(String.valueOf(tps)) < 20) color = "&a";

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&6TPS &8» " + color + tps + " (" + lag + "%)"));
            return true;
        }

        Player plr = (Player) sender;

        plr.sendMessage(Utils.chat("&6TPS &8» " + color +  tps + " (" + lag + "%)"));
        return false;
    }
}
