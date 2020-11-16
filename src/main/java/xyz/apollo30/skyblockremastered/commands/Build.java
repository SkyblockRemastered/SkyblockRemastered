package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Build implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Build(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("build").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player plr = (Player) sender;

        if (!plr.hasPermission("sbr.buildbypass") || !plr.isOp()) return false;
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        plr.sendMessage(Utils.chat(po.isBlockBreak() ? "&cYou can no longer alter any islands" : "&aYou can now alter islands."));
        po.setBlockBreak(!po.isBlockBreak());

        return false;
    }
}
