package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.guis.Admin.StatsMenu;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Stats implements CommandExecutor {

    public SkyblockRemastered plugin;

    public Stats(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("stats").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&cThis command can only be executed by a player."));
            return false;
        }

        Player plr = (Player) sender;
        if (!plr.isOp()) return false;
        Player target;
        if (args.length == 0) target = (Player) sender;
        else {
            target = Bukkit.getPlayer(args[0]);
        }

        if (target == null) {
            plr.sendMessage(Utils.chat("&cThat player cannot be found or is offline."));
            return false;
        }

        new StatsMenu(SkyblockRemastered.getMenuUtility(target), plr, target).open();
        return false;
    }
}
