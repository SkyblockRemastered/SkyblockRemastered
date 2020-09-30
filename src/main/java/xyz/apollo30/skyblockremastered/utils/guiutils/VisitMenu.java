package xyz.apollo30.skyblockremastered.utils.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class VisitMenu {

    public SkyblockRemastered plugin;

    public VisitMenu(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void visitMenu(Player plr, String uuid) {

        Inventory inv = Bukkit.createInventory(plr, 36, "Visit " + plr.getName());

        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 1,2,3,4,5,6,7,8,9,10,11,13,14,15,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,33,34,35,36);

        plr.openInventory(inv);
    }
}
