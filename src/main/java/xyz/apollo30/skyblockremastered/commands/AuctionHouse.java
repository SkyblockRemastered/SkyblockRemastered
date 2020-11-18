package xyz.apollo30.skyblockremastered.commands;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.guis.ItemBrowser.ItemBrowser;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class AuctionHouse implements CommandExecutor {

    public AuctionHouse(SkyblockRemastered plugin) {
        plugin.getCommand("auctionhouse").setExecutor(this);
    }

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Weapons - Orange
        // Armor - Blue
        // Accessories - Lime
        // Consumables - Red
        // Blocks - White
        // Tools / Misc - Pink

        String prefix = SkyblockRemastered.so.getPrefix();

        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage(Utils.chat(prefix + "&cUsage: &c/ah [player/uuid]"));
            } else if (Bukkit.getServer().getPlayer(args[0]) == null) {
                sender.sendMessage(Utils.chat(prefix + "&cThat player does not exist or they're not online"));
                return false;
            } else {
                Player plr = Bukkit.getServer().getPlayer(args[0]);
                openAuctionHouse(plr);
            }
            return true;
        } else {
            Player plr = (Player) sender;
            openAuctionHouse(plr);
            return false;
        }
    }

    private void openAuctionHouse(Player plr) {

        if (!SkyblockRemastered.so.isAuctionHouse()) {
            plr.sendMessage(Utils.chat("&cThe Auction House is disabled"));
            return;
        }

        new ItemBrowser(SkyblockRemastered.getMenuUtility(plr)).open();

    }
}
