package xyz.apollo30.skyblockremastered.utils.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class CraftingMenu implements Listener {

    public SkyblockRemastered plugin;

    public CraftingMenu(final SkyblockRemastered plugin) { this.plugin = plugin; }

    public static void craftingMenu(Player plr, String uuid, String type) {

        if (type == "empty") {
            Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Crafting Grid"));
            Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16, 18, 19, 23, 25, 27, 28, 32, 33, 34, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);
            Utils.createGlass(inv, "STAINED_GLASS_PANE", 14, 1, 46, 47, 48, 49, 50, 51, 52, 53, 54);

            // Quick Crafting Slots
            if (!plr.hasPermission("sbr.quickcraft.slot1"))
                Utils.createItemByte(inv, 160, 14, 1, 17, "&cQuick Crafting (Locked)", "&7To use quick craft, you can purchase", "&7this using Gems in the community store.");
            else
                Utils.createItemByte(inv, 160, 14, 1, 17, "&aQuick Crafting", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");

            if (!plr.hasPermission("sbr.quickcraft.slot2"))
                Utils.createItemByte(inv, 160, 14, 1, 26, "&cQuick Crafting (Locked)", "&7To use quick craft, you can purchase", "&7this using Gems in the community store.");
            else
                Utils.createItemByte(inv, 160, 14, 1, 26, "&aQuick Crafting", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");

            if (!plr.hasPermission("sbr.quickcraft.slot3"))
                Utils.createItemByte(inv, 160, 14, 1, 35, "&aQuick Crafting", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");
            else
                Utils.createItemByte(inv, 160, 14, 1, 35, "&cQuick Crafting (Locked)", "&7To use quick craft, you can purchase", "&7this using Gems in the community store.");

            plr.openInventory(inv);
        }

    }
}
