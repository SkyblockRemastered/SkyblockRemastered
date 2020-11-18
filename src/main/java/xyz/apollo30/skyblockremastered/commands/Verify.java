package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.guis.Admin.StatsMenu;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Verify implements CommandExecutor {

    public Verify(SkyblockRemastered plugin) {
        plugin.getCommand("verify").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&cThis command can only be executed by a player."));
            return false;
        }

        Player plr = (Player) sender;
        plr.sendMessage(Utils.chat("&cPsst.. You verify yourself at our discord, not in here."));

        return false;
    }
}
