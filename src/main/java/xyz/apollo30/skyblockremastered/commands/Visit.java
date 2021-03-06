package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Visit implements CommandExecutor {

    public Visit(SkyblockRemastered plugin) {
        plugin.getCommand("visit").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player plr = (Player) sender;


        if (args.length <= 0) {
            plr.sendMessage(Utils.chat("&cInsufficient arguments! Please supply a player name to visit!"));
            return true;
        }

        String uuid = Utils.getUuid(args[0]);
        Utils.broadCast(plr.getUniqueId().toString());
        String name = Utils.getName(uuid) != null ? Utils.getName(uuid) : args[0];

        if (Bukkit.getServer().getWorld("playerislands/" + uuid) == null) {
            plr.sendMessage(Utils.chat("&cCouldn't find that player/island! Maybe a typo?"));
            return true;
        }
        //        PlayerObject pot = plugin.playerManager.playerObjects.get()
        plr.sendMessage(Utils.chat("&7Sending you to " + name + "'s Island"));

        World island = Bukkit.getServer().createWorld(new WorldCreator("playerislands/" + uuid));
        Location loc = new Location(island, island.getSpawnLocation().getX(), island.getSpawnLocation().getY(), island.getSpawnLocation().getZ());
        plr.teleport(loc);

        return false;
    }
    
}
