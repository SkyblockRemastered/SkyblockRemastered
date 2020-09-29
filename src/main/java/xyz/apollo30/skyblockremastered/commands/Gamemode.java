package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Gamemode implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Gamemode(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("gamemode").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player og_plr = (Player) sender;
        Player target = (Player) sender;
        String prefix = "&6Skyblock &8» &7";

        if (!target.isOp() && !target.hasPermission("sbr.gamemode")) {
            og_plr.sendMessage(Utils.chat(prefix + "&cMissing Permission: &aaessentials.gamemode"));
            return true;
        } else {

            if (label.equalsIgnoreCase("gms") || label.equalsIgnoreCase("survival") || label.equalsIgnoreCase("esurvival")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6SURVIVAL"));
                target.setGameMode(GameMode.SURVIVAL);

                return true;
            } else if (label.equalsIgnoreCase("gmc") || label.equalsIgnoreCase("creative") || label.equalsIgnoreCase("ecreative")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6CREATIVE"));
                target.setGameMode(GameMode.CREATIVE);
                return true;
            } else if (label.equalsIgnoreCase("gma") || label.equalsIgnoreCase("adventure") || label.equalsIgnoreCase("eadventure")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6ADVENTURE"));
                target.setGameMode(GameMode.ADVENTURE);
                return true;
            } else if (label.equalsIgnoreCase("gmsp") || label.equalsIgnoreCase("spectator") || label.equalsIgnoreCase("spec") || label.equalsIgnoreCase("espec") || label.equalsIgnoreCase("especator")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6SPECTATOR"));
                target.setGameMode(GameMode.SPECTATOR);
                return true;
            } else if (args.length == 0) {
                target.sendMessage(Utils.chat(prefix + "&7Usage: &6/gamemode <gamemode> <player>\n&7Available Gamemodes: &6SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR"));
                return true;
            }

            if (args.length == 2) {
                target = Bukkit.getPlayerExact(args[2]);
                if (target == null) {
                    og_plr.sendMessage(Utils.chat(prefix + "&7That player does not exist or they're offline."));
                }
            }

            if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6SURVIVAL"));
                target.setGameMode(GameMode.SURVIVAL);
            } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6CREATIVE"));
                target.setGameMode(GameMode.CREATIVE);
            } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6ADVENTURE"));
                target.setGameMode(GameMode.ADVENTURE);
            } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("3")) {
                target.sendMessage(Utils.chat(prefix + "&7Changed your gamemode to &6SPECTATOR"));
                target.setGameMode(GameMode.SPECTATOR);
            } else {
                target.sendMessage(Utils.chat(prefix + "&7Usage: &6/gamemode <gamemode> <player>\n&7Available Gamemodes: &6SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR"));
                return true;
            }


        }


        return false;
    }
}