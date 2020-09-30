package xyz.apollo30.skyblockremastered.utils.guiutils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;
// REMEMBER TO REGISTER THE EVENT!
public class MaddoxBatphone {

    public SkyblockRemastered plugin;

    public MaddoxBatphone(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void openGui(Player plr, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("&8Slayers"));

        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);
        Utils.createGlass(inv, "STAINED_GLASS_PANE", 11, 1, 46,48,50,52,54);
        Utils.createGlass(inv, "STAINED_GLASS_PANE", 3, 1, 47,49,51,53);

        Utils.createItemID(inv, 293, 1, 11, "&eRevenant Horror", "&7", "&7&oThis fearful boss was", "&7&oborn from a mutated zombie,", "&7&omany thousand years ago.", "&7&oIt will leave you beat up and", "&7&osmelling of flesh, or dead...", "&7", "&eClick to view slayer!");
        Utils.createItemID(inv, 30, 1, 13, "&eTarantula Broodfather", "&7", "&7&oEvolution was spontaneous in the", "&7&ohumanless land of the Spider's Den.", "&7&oDecades of development occurred", "&7&oin mere hours, creating this monstrosity.", "&7", "&eClick to view slayer!");
        Utils.createItemID(inv, 352, 1, 15, "&eSven Packmaster", "&7", "&7&oBefore the time of Skyblock,", "&7&othere was a sinister doctor named Sven.", "&7&oThe mad doctor preformed all manner", "&7&oof experimentation on his subjects including ripping,", "&7&otearing and gushing in order to create", "&7&ohis ultimate lifeform.", "&7&oAfter all of his experiments,", "&7&ohis dog was transformed into a", "&7&opowerful, yet gruesome monster.", "&7", "&eClick to view slayer!");
        Utils.createItemID(inv, 261, 1, 17, "&eBoneyard Bruiser", "&7", "&7&oClick and clack his bones go", "&7&oHe’s a master of bones, his weapons aren’t just for show", "&7&oDon’t try to run, he has all the bones he can throw", "&7&oHe once slew 7 men with one bone in a row!", "&7", "&eClick to view slayer!");

        Utils.createItem(inv,"BEDROCK", 1, 29, "&c&l&oComing Soon!");
        Utils.createItem(inv,"BEDROCK", 1, 31, "&c&l&oComing Soon!");
        Utils.createItem(inv,"BEDROCK", 1, 33, "&c&l&oComing Soon!");
        Utils.createItem(inv,"BEDROCK", 1, 35, "&c&l&oComing Soon!");

        Utils.addItem(inv, Utils.addLore(Utils.getSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY0YTkxNDMzZDY2YmZjNmVmMWM5YWM0ZTM3MWE4ODZhMWM0NjJjOTc1ZmY4MTI3NzYxZDcwM2UwYTNlNzMwNiJ9fX0="),
                "&aOver-All Slayer Stats:",
                "&7Total Slayers Killed: &f" + player_data.getTotalKills(),
                "&7Total Money Spent: &f" + Utils.coinFormat(player_data.getTotalSpent()),
                "&7Total Amount of Crazy RNG Drops: &f" + player_data.getTotalRng()
        ), 50);

        plr.openInventory(inv);
    }
    @EventHandler
    public void guiClickEvent(InventoryClickEvent e){
        // fail-fast
        String invName = e.getInventory().getTitle();
        if (!invName.equalsIgnoreCase(Utils.chat("&8Slayers"))) return;

        Player plr = (Player) e.getWhoClicked();
        e.setCancelled(true);

    }
}
