package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Debug implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Debug(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("debug").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Utils.chat("&cUsage: /debug [reload] [player]"));
            return false;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (args.length == 1) {
                sender.sendMessage(Utils.chat("&cUsage: /debug [reload] [player]"));
                return false;
            } else if (args[1].equalsIgnoreCase("all")) {
                sender.sendMessage(Utils.chat("&aReloading everyone's profile"));
                for (Player plr : Bukkit.getOnlinePlayers()) {
                    try {
                        PlayerManager.playerObjects.remove(plr);
                        PlayerManager.savePlayerData(plr);
                        PlayerManager.createPlayerData(plr);
                        sender.sendMessage(Utils.chat("&aReloaded " + plr.getName() + "'s profile!"));
                    } catch (Exception ignored) {
                        sender.sendMessage(Utils.chat("&cFailed to reload " + plr.getName() + "'s profile!"));
                    }
                }
            } else {
                Player plr = Bukkit.getPlayer(args[1]);
                if (plr == null) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(Utils.chat("&cPlayer is offline or nonexistent."));
                        return true;
                    } else plr = (Player) sender;
                }

                try {
                    PlayerManager.playerObjects.remove(plr);
                    PlayerManager.savePlayerData(plr);
                    PlayerManager.createPlayerData(plr);
                    sender.sendMessage(Utils.chat("&aReloaded " + plr.getName() + "'s profile!"));
                } catch (Exception ignored) {
                    sender.sendMessage(Utils.chat("&cFailed to reload" + plr.getName() + "'s profile!"));
                }
            }
        }
        return false;
    }
}
