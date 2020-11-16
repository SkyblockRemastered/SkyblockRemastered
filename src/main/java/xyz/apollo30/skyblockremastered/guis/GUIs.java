package xyz.apollo30.skyblockremastered.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class GUIs {

    public SkyblockRemastered plugin;

    public GUIs(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }


    public static void playerTradeMenu(Player plr, Player c) {
        Inventory menu1 = Bukkit.createInventory(null, 45, "You                  " + plr.getName());
        GUIHelper.addGlass(menu1, "STAINED_GLASS_PANE", 15, 1, 5, 14, 23, 32, 41);
        GUIHelper.addItem(menu1, 371, 0, 1, 37, "&6Coin Transaction", "", "&eClick to add coins to the trade menu", "&eYou can use prefixes like: H, K, M, B");
        GUIHelper.addItem(menu1, 351, 8, 1, 40, "&cInvalid Trade", "", "&7Place something in the trade menu in order", "&7to accept the trade");
        GUIHelper.addItem(menu1, 351, 8, 1, 42, "&7Other Person's Response", "", "&7You are currently", "&7trading with " + Helper.getRank(plr, false));

        Inventory menu2 = Bukkit.createInventory(null, 45, "You                  " + c.getName());
        GUIHelper.addGlass(menu2, "STAINED_GLASS_PANE", 15, 1, 5, 14, 23, 32, 41);
        GUIHelper.addItem(menu2, 371, 0, 1, 37, "&6Coin Transaction", "", "&eClick to add coins to the trade menu", "&eYou can use prefixes like: H, K, M, B");
        GUIHelper.addItem(menu1, 351, 8, 1, 40, "&cInvalid Trade", "", "&7Place something in the trade menu in order", "&7to accept the trade");
        GUIHelper.addItem(menu2, 351, 8, 1, 42, "&7Other Person's Response", "", "&7You are currently", "&7trading with " + Helper.getRank(c, false));
    }

//    public static void batphoneMenu(Player plr, String uuid, SkyblockRemastered plugin) {
//        PlayerTemplate player_data = plugin.playerManager.getPlayerData(plr);
//
//        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("&8Slayers"));
//
//        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);
//        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 11, 1, 46, 48, 50, 52, 54);
//        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 3, 1, 47, 49, 51, 53);
//        // ill make this better later so make some backstory idk
//        GUIHelper.addItem(inv, 293, 1, 11, "&eRevenant Horror", "", "&7The Revenant Horror can", "&7be spawned by killing &fzombies.", "", "&eClick to view slayer!");
//        GUIHelper.addItem(inv, 30, 1, 13, "&eTarantula Broodfather", "", "&7The Tarantula Broodfather can", "&7be spawned by killing &fspiders.", "", "&eClick to view slayer!");
//        GUIHelper.addItem(inv, 352, 1, 15, "&eSven Packmaster", "", "&7The Sven Packmaster can", "&7be spawned by killing &fwolves.", "", "&eClick to view slayer!");
//        GUIHelper.addItem(inv, 261, 1, 17, "&eSkeletor Master", "", "&7The Skeletor Master can", "&7be spawned by killing &fskeletons.", "", "&eClick to view slayer!");
//        // ill name this coming soon l8r but yea
//        GUIHelper.addGlass(inv, "BEDROCK", 0, 1, 29, 31, 33, 35);
//        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
//                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY0YTkxNDMzZDY2YmZjNmVmMWM5YWM0ZTM3MWE4ODZhMWM0NjJjOTc1ZmY4MTI3NzYxZDcwM2UwYTNlNzMwNiJ9fX0="),
//                "&aOver-All Slayer Stats:",
//                "&7Total Slayers Killed: &f" + player_data.getTota(),
//                "&7Total Money Spent: &f" + Utils.doubleFormat(player_data.getTotalSpent()),
//                "&7Total Amount of Crazy RNG Drops: &f" + player_data.getTotalRng()
//        ), 50);
//
//        plr.openInventory(inv);
//    }

    public static void craftingMenu(Player plr, String uuid, String type) {

        if (type.equals("empty")) {
            Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Crafting Grid"));
            GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16, 18, 19, 23, 25, 27, 28, 32, 33, 34, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);
            GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 14, 1, 46, 47, 48, 49, 50, 51, 52, 53, 54);

            // Quick Crafting Slots
            if (!Helper.getRank(plr, false).startsWith("&7")) {
                GUIHelper.addItem(inv, 160, 14, 1, 17, "&aQuick Crafting Slot", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");
                GUIHelper.addItem(inv, 160, 14, 1, 26, "&aQuick Crafting Slot", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");
                GUIHelper.addItem(inv, 160, 14, 1, 35, "&aQuick Crafting Slot", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");
            } else {
                GUIHelper.addItem(inv, 160, 14, 1, 17, "&cQuick Crafting Slot", "&7Quick crafting allows you to", "&7craft items without assembling the recipe.", " ", "&cRequires &aVIP &cor above.");
                GUIHelper.addItem(inv, 160, 14, 1, 26, "&cQuick Crafting Slot", "&7Quick crafting allows you to", "&7craft items without assembling the recipe.", " ", "&cRequires &aVIP &cor above.");
                GUIHelper.addItem(inv, 160, 14, 1, 35, "&cQuick Crafting Slot", "&7Quick crafting allows you to", "&7craft items without assembling the recipe.", " ", "&cRequires &aVIP &cor above.");
            }
            plr.openInventory(inv);
        }

    }

    public static String getUnicode(String type) {
        String unicode = "";
        if (type.equalsIgnoreCase("seacreature"))
            unicode = "α";
        else if (type.equalsIgnoreCase("intel"))
            unicode = "✎";
        else if (type.equalsIgnoreCase("heart"))
            unicode = "❤";
        else if (type.equalsIgnoreCase("defense"))
            unicode = "❈";
        else if (type.equalsIgnoreCase("cc"))
            unicode = "☣";
        else if (type.equalsIgnoreCase("infinite"))
            unicode = "∞";
        else if (type.equalsIgnoreCase("trademark"))
            unicode = "™";
        else if (type.equalsIgnoreCase("speed"))
            unicode = "✦";
        else if (type.equalsIgnoreCase("cd"))
            unicode = "☠";
        else if (type.equalsIgnoreCase("petluck"))
            unicode = "♣";
        else if (type.equalsIgnoreCase("truedefense") || type.equalsIgnoreCase("td"))
            unicode = "❂";
        else if (type.equalsIgnoreCase("strength"))
            unicode = "❁";
        else if (type.equalsIgnoreCase("atkspeed"))
            unicode = "⚔";
        else if (type.equalsIgnoreCase("moonleft"))
            unicode = "☽";
        else if (type.equalsIgnoreCase("moonright"))
            unicode = "☾";
        else if (type.equalsIgnoreCase("mf"))
            unicode = "✯";
        else if (type.equalsIgnoreCase("crithit"))
            unicode = "✯";
        return unicode;
    }

}
