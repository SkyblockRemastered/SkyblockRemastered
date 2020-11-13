package xyz.apollo30.skyblockremastered.commands;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.apollo30.skyblockremastered.GUIs.ItemBrowser;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class AuctionHouse implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public AuctionHouse(SkyblockRemastered plugin) {
        this.plugin = plugin;
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

        String prefix = plugin.so.getPrefix();

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

        new ItemBrowser(SkyblockRemastered.getMenuUtility(plr)).open();

//        Inventory inv = Bukkit.createInventory(plr, 54, "Auctions Browser");
//        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 18, 20, 27, 29, 36, 38, 45, 47, 48, 49, 50, 51, 52, 53, 54);
//
//        Accessories accessories = new Accessories(plugin);
//
//        Field[] fields = Accessories.class.getFields();
//        List<Field> fieldList = Arrays.stream(fields).filter(field -> Modifier.isPublic(field.getModifiers())).collect(Collectors.toList());
//
//        for (Field field : fieldList) {
//            try {
//                ItemStack item = (ItemStack) field.get(accessories);
//                inv.addItem(item);
//            } catch (Exception ignore) {
//
//            }
//        }
//        plr.openInventory(inv);
    }
}
