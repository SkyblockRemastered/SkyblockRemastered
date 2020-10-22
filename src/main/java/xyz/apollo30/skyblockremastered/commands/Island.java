package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Island implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Island(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("island").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player plr = (Player) sender;

        if (plr.getWorld().getName().equals("playerislands/" + plr.getUniqueId().toString())) {
            plr.sendMessage(Utils.chat("You are already at your island"));
            return true;
        } else {
            plr.sendMessage(Utils.chat("&7Sending you to your island."));
            World island = Bukkit.getServer().createWorld(new WorldCreator("playerislands/" + plr.getUniqueId().toString()));
            Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
            plr.teleport(loc);
        }

        return false;
    }
}
