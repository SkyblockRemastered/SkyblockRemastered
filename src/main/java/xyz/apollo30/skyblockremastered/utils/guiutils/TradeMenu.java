package xyz.apollo30.skyblockremastered.utils.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class TradeMenu implements Listener {

    public SkyblockRemastered plugin;

    public TradeMenu(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void tradeMenu(Player plr, String s) {

        try {
            Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Trades"));

            Utils.createItemByte(inv, 351, 8, 1, 11, "&c???", "&7Progress through your item", "&7collections and explore the", "&7world to unlock new trades!");
            Utils.createItemID(inv, 263, 2, 12, "&fCoal &8x2", "&8Brewing Ingredient", "&7increases the speed of", "&7your minion by &a5%", "&7for 30 minutes!", "", "&7Cost", "&fOak Wood", "", "&eClick to trade!");
            Utils.createItemID(inv, 2, 4, 13, "&fGrass &8x4", "", "&7Cost", "&fDirt &8x4", "", "&eClick to trade!");
            Utils.createItemID(inv, 3, 2, 14, "&fDirt &8x2", "", "&7Cost", "&fSeeds &8x8", "", "&eClick to trade!");
            Utils.createItemID(inv, 337, 1, 15, "&fClay", "", "&7Cost", "&fSeeds &8x12", "", "&eClick to trade!");
            Utils.createItemID(inv, 18, 1, 16, "&fOak Leaves", "", "&7Cost", "&fOak Sapling", "", "&eClick to trade!");
            Utils.createItemByte(inv, 18, 1, 1, 17, "&fSpruce Leaves", "", "&7Cost", "&fSpruce Sapling", "", "&eClick to trade!");
            Utils.createItemByte(inv, 18, 2, 1, 20, "&fBirch Leaves", "", "&7Cost", "&fBirch Sapling", "", "&eClick to trade!");
            Utils.createItemByte(inv, 18, 3, 1, 21, "&fJungle Leaves", "", "&7Cost", "&fJungle Sapling", "", "&eClick to trade!");
            Utils.createItemByte(inv, 161, 0, 1, 22, "&fAcacia Leaves", "", "&7Cost", "&fAcacia Sapling", "", "&eClick to trade!");
            Utils.createItemByte(inv, 161, 1, 1, 23, "&fDark Oak Leaves", "", "&7Cost", "&fDark Oak Sapling", "", "&eClick to trade!");
            Utils.createInvisibleEnchantedItemByte(inv, 351, 15, 1, 24, "&fEnchanted Bone Meal", "&7Instantly grows crops and", "&7saplings.", "", "&7Cost", "&fBone &8x64", "", "&eClick to trade!");
            Utils.createItemByte(inv, 31, 1, 1, 25, "&fLong Grass", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            Utils.createItemByte(inv, 31, 2, 1, 26, "&fFern", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            Utils.createItemByte(inv, 32, 1, 1, 29, "&fDead Bush", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            Utils.createItemByte(inv, 175, 2, 1, 30, "&fDouble Tallgrass", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            Utils.createItemByte(inv, 31, 1, 1, 31, "&fLong Grass", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            Utils.createItemByte(inv, 326, 0, 1, 32, "&fWater Bucket", "", "&7Cost", "&612 Coins", "", "&eClick to trade!");
            Utils.createItemByte(inv, 327, 0, 1, 33, "&fLava Bucket", "", "&7Cost", "&620 Coins", "", "&eClick to trade!");
            Utils.createItemByte(inv, 12, 1, 1, 34, "&fRed Sand", "", "&7Cost", "&fSand", "", "&eClick to trade!");
            Utils.createItemByte(inv, 88, 0, 1, 35, "&fSoul Sand", "", "&7Cost", "&fSand &8x2", "&fFermented Spider Eye", "", "&eClick to trade!");
            Utils.createItemByte(inv, 110, 0, 1, 38, "&fMycelium", "", "&7Cost", "&fDirt", "&fRed Mushroom", "&fBrown Mushroom", "", "&eClick to trade!");
            Utils.createItemByte(inv, 405, 0, 1, 39, "&fNether Brick", "", "&7Cost", "&fNetherrack", "", "&eClick to trade!");
            Utils.createItemByte(inv, 19, 0, 1, 40, "&fSponge", "", "&7Cost", "&fWet Sponge", "", "&eClick to trade!");
            Utils.createInvisibleEnchantedItemByte(inv, 120, 0, 2, 40, "&aTeleport Pad &8x2", "", "&7Cost", "&aEnchanted Eye of Ender &8x2", "", "&eClick to trade!");
            Utils.createItemID(inv, 262, 1, 49, "&aGo Back", "&7To SkyBlock Menu");
            Utils.createItemID(inv, 166, 1, 50, "&cClose");
            Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 48, 51, 52, 53, 54);

            plr.openInventory(inv);
        } catch (Exception err) {

        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player plr = (Player) e.getWhoClicked();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;
        String invName = e.getInventory().getTitle();
        Inventory inv = plr.getInventory();
        if (invName.equals("Trades")) {
            e.setCancelled(true);
            if (item.equals(Utils.chat("&fCoal &8x2"))) {
                ItemStack need = new ItemStack(Material.LOG, 1, (short) 0);
                ItemStack give = new ItemStack(Material.COAL, 2);
                if (inv.containsAtLeast(need, 1))
                    Utils.successTrade(plr, need, give, 1);
                else
                    Utils.failTrade(plr);
            } else if (item.equals(Utils.chat("&fGrass &8x4"))) {
                ItemStack need = new ItemStack(Material.DIRT, 4);
                ItemStack give = new ItemStack(Material.GRASS, 4);
                if (inv.containsAtLeast(need, 4))
                    Utils.successTrade(plr, need, give, 4);
                else
                    Utils.failTrade(plr);
            }
        }
    }
}
