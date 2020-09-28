package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Hub implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Hub(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("hub").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player plr = (Player) sender;

        plr.sendMessage(Utils.chat("&7Sending you to the hub."));
        World island = Bukkit.getServer().createWorld(new WorldCreator("hub"));
        Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
        plr.teleport(loc);

        return false;
    }

}
