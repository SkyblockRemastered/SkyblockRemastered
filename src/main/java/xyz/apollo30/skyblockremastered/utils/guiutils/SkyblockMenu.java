package xyz.apollo30.skyblockremastered.utils.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.UnicodeUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;
import org.bukkit.event.Listener;

public class SkyblockMenu implements Listener {
    public SkyblockRemastered plugin;

    public SkyblockMenu(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void skyblockMenu(Player plr, String uuid, FileConfiguration db, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        int health = player_data.getMaxHealth();
        int defense = player_data.getDefense();
        int strength = player_data.getStrength();
        int speed = player_data.getSpeed();
        int critdamage = player_data.getCrit_damage();
        int critchance = player_data.getCrit_chance();
        int atkspeed = player_data.getAtk_speed();
        int intel = player_data.getMaxIntelligence();
        int seacreaturechance = player_data.getSea_creature_chance();
        int magicfind = player_data.getMagic_find();
        int petluck = player_data.getPet_luck();

        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("SkyBlock Menu"));

        Utils.createItemID(inv, 130, 1, 26, "&aEnder Chest", "&7Store global items that you want",
                "&7to access at any time from", "&7anywhere right here.", " ", "&eClick to view!");
        Utils.createItemID(inv, 276, 1, 20, "&aYour Skills", "&7View your Skill progression", "&7rewards.", " ",
                "&eClick to view!");
        Utils.createItemID(inv, 321, 1, 21, "&aCollection", "&7View all of the items availiable",
                "&7in SkyBlock. Collect more of an", "&7item to unlock rewards on your",
                "&7way to becoming an Expert in Skyblock!", " ", "&eClick to view!");
        Utils.createItemID(inv, 340, 1, 22, "&aRecipe Book", "&7Through your adventure, you will",
                "&7unlock recipes for all kinds of", "&7special items! You can view how",
                "&7to craft these items here.", " ", "&eClick to view!");
        Utils.createItemID(inv, 388, 1, 23, "&aTrades", "&7View your available trades.",
                "&7These trades are always", "&7availiable and accessible through", "&7the Skyblock Menu.", " ",
                "&eClick to view!");
        Utils.createItemID(inv, 386, 1, 24, "&aQuest Log", "&7Not Coming Soon", " ", "&eClick to view!");
        Utils.addItem(inv, Utils.addLore(Utils.getSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0="),
                "&dFairy Soul", "&7Collect the souls of dead fairies", "&7around the map, for permanent stats.", "",
                " &c✖ &eFound: &d"
                        + db.getConfigurationSection(plr.getUniqueId().toString() + ".Information")
                        .getInt("fairySouls")
                        + "&7/&d0"),
                25);

        Utils.createItemID(inv, 352, 1, 31, "&aPets", "&7View and manager all of your Pets.", " ",
                "&7Level up your pets faster by", "&7gaining xp in their favorite", "&7skill!", " ",
                "&7Selected pet: &fNone", " ", "&eClick to view!");
        Utils.createItemID(inv, 299, 1, 33, "&aWardrobe", "&7Store armor sets and quickly", "&7swap between them!",
                "", "&eClick to view!");
        Utils.addItem(inv, Utils.addLore(Utils.getSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0"),
                "&aPersonal Bank", "&7Contact your Banker from", "&7anywhere.", "&7Cooldown: &e-", " ",
                "&7Banker Status:", "&a-", " ", "&eClick to open!"), 34);
        Utils.createItemID(inv, 345, 1, 46, "&aPlugin Developer Menu",
                "&7View the Plugin Developer Menu, only availiable", "&7to the plugin developers.", " ",
                plr.getName().equalsIgnoreCase("apollo30") ? "&aACCESS GRANTED" : "&cACCESS DENIED");
        Utils.createItemID(inv, 166, 1, 50, "&cClose");
        Utils.createItemID(inv, 58, 1, 32, "&aCrafting Table", "&7Opens the crafting grid", " ", "&eClick to use!");
        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17,
                18, 19, 27, 28, 29, 30, 35, 36, 38, 39, 40, 41, 42, 43, 44, 45, 47, 52, 53, 54);
        Utils.addItem(inv, Utils.addLore(Utils.getSkull(
                "ewogICJ0aW1lc3RhbXAiIDogMTU5OTQ2Nzk1ODg0NSwKICAicHJvZmlsZUlkIiA6ICIwMWIzZWY3YmQzOWI0OGFiOTc0YmE1NTdiMzFhMDczOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJBcG9sbG8zMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85YmM2ZjczM2Q0NTIyZTMwMTdmZjAyNGY0NWIxZjc5OGY2Nzc0YTU5N2Q3ZGFlZTljNzkxNWVkNDEyZGVkZDJjIgogICAgfQogIH0KfQ"),
                "&aCredits", "&fPlugin created by: &aApollo#6000"), 37);
        Utils.createSkull(inv, plr.getName(), 1, 14, "&aYour Skyblock Profile",
                "  &c" + UnicodeUtils.getUnicode("heart") + " Health &f" + health + " HP",
                "  &a" + UnicodeUtils.getUnicode("defense") + " Defense &f" + defense,
                "  &c" + UnicodeUtils.getUnicode("strength") + " Strength &f" + strength,
                "  &f" + UnicodeUtils.getUnicode("speed") + " Speed " + speed,
                "  &9" + UnicodeUtils.getUnicode("cc") + " Crit Chance &f" + critchance + "%",
                "  &9" + UnicodeUtils.getUnicode("cd") + " Crit Damage &f" + critdamage + "%",
                "  &e" + UnicodeUtils.getUnicode("atkspeed") + " Bonus Attack Speed &f" + atkspeed + "%",
                "  &b" + UnicodeUtils.getUnicode("intel") + " Intelligence &f" + intel,
                "  &3" + UnicodeUtils.getUnicode("seacreature") + "&3 Sea Creature Chance &f" + seacreaturechance + "%",
                "  &b" + UnicodeUtils.getUnicode("mf") + " Magic Find &f" + magicfind,
                "  &d" + UnicodeUtils.getUnicode("petluck") + " Pet Luck &f" + petluck,
                " ", "&eClick to view your profile!");

        Utils.addItem(inv, Utils.addLore(Utils.getSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0MDk0MmYzNjRmNmNiY2VmZmNmMTE1MTc5NjQxMDI4NmE0OGIxYWViYTc3MjQzZTIxODAyNmMwOWNkMSJ9fX0="),
                "&bFast Travel", "&7Teleport to islands you've", "&7already visited.", "",
                "&bRight-click to warp home!", "&eClick to pick a location!"), 48);
        Utils.createItemID(inv, 421, 1, 49, "&aProfile Management", "&7You are limited to &c1 &7profile in this",
                "&7plugin.", " ", "&7Profiles: &e1&6/&e1",
                "&7Playing on: " + db.getConfigurationSection(plr.getUniqueId().toString()).getString("Profile"));
        Utils.createItemID(inv, 76, 1, 51, "&aSettings", "&7View and edit your SkyBlock settings.", " ",
                "&eClick to alter!");
        plr.openInventory(inv);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player plr = (Player) e.getWhoClicked();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;
        String invName = e.getInventory().getTitle();
        // Inv is named skyblock menu
        if (invName.equalsIgnoreCase(Utils.chat("&aSkyblock Menu"))) { e.setCancelled(true); }
        if (item.equalsIgnoreCase(Utils.chat("&aSkyBlock Menu &7(Right Click)")) {
            FileConfiguration db = plugin.db.getPlayers();
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), db, plugin);
            assert item != null;
            e.setCancelled(true);
            // can't move items ^^
            if (item.equalsIgnoreCase(Utils.chat("&aPlugin Developer Menu"))) {
                AdminPanel.adminPanel(plr, plr.getUniqueId().toString());
            } else if (item.equalsIgnoreCase(Utils.chat("&aEnder Chest"))) {
                plr.openInventory(plr.getEnderChest());
                plr.playSound(plr.getLocation(), Sound.ENDERMAN_TELEPORT, 0.1F, 1F);
            } else if (item.equalsIgnoreCase(Utils.chat("&aGo Back"))) {
                skyblockMenu(plr, plr.getUniqueId().toString(), db, plugin);
            } else if (item.equals(Utils.chat("&aCrafting Table"))) {
                CraftingMenu.craftingMenu(plr, plr.getUniqueId().toString(), "empty");
            } else if (item.equals(Utils.chat("&aTrades"))) {
                TradeMenu.tradeMenu(plr, plr.getUniqueId().toString());
            } else if (item.equals(Utils.chat("&cClose"))) {
                plr.closeInventory();
            } else if (item.equals(Utils.chat("&aYour Skills"))) {
                SkillMenu.skillMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers());
            } else {
                return;
            } // pog i just love adding these returns dont ask whyyy
        }
    }
}
