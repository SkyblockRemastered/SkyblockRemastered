package xyz.apollo30.skyblockremastered.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

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

    public static void batphoneMenu(Player plr, String uuid, SkyblockRemastered plugin) {
        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("&8Slayers"));

        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 11, 1, 46, 48, 50, 52, 54);
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 3, 1, 47, 49, 51, 53);
        // ill make this better later so make some backstory idk
        GUIHelper.addItem(inv, 293, 1, 11, "&eRevenant Horror", "", "&7The Revenant Horror can", "&7be spawned by killing &fzombies.", "", "&eClick to view slayer!");
        GUIHelper.addItem(inv, 30, 1, 13, "&eTarantula Broodfather", "", "&7The Tarantula Broodfather can", "&7be spawned by killing &fspiders.", "", "&eClick to view slayer!");
        GUIHelper.addItem(inv, 352, 1, 15, "&eSven Packmaster", "", "&7The Sven Packmaster can", "&7be spawned by killing &fwolves.", "", "&eClick to view slayer!");
        GUIHelper.addItem(inv, 261, 1, 17, "&eSkeletor Master", "", "&7The Skeletor Master can", "&7be spawned by killing &fskeletons.", "", "&eClick to view slayer!");
        // ill name this coming soon l8r but yea
        GUIHelper.addGlass(inv, "BEDROCK", 0, 1, 29, 31, 33, 35);
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY0YTkxNDMzZDY2YmZjNmVmMWM5YWM0ZTM3MWE4ODZhMWM0NjJjOTc1ZmY4MTI3NzYxZDcwM2UwYTNlNzMwNiJ9fX0="),
                "&aOver-All Slayer Stats:",
                "&7Total Slayers Killed: &f" + player_data.getTotalKills(),
                "&7Total Money Spent: &f" + Utils.doubleFormat(player_data.getTotalSpent()),
                "&7Total Amount of Crazy RNG Drops: &f" + player_data.getTotalRng()
        ), 50);

        plr.openInventory(inv);
    }

    public static void bankMenu(Player plr, String uuid, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        double bank = player_data.getBank();

        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank"));

        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 34, 35, 36);
        GUIHelper.addItem(inv, 54, 1, 12, "&aDeposit Coins", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&7Store coins in the bank to", "&7keep them safe while you go", "&7on adventures!", "", "&7You will earn &b2%&7 interest every", "&7season for your first &610 million", "&7banked coins.", "", "&7Until Interest: &b00h00m00s", "", "&eClick to make a deposit!");
        GUIHelper.addItem(inv, 158, 1, 14, "&aWithdraw Coins", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&7Take your coins out of the", "&7bank in order to spend", "&7them.", "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 358, 0, 1, 16, "&aRecent transactions", "", "&7&oYou know, I am lazy to code this", "&7&oIt's kinda useless tbh if theres no co-ops.");
        GUIHelper.addItem(inv, 166, 1, 32, "&cClose");
        GUIHelper.addItem(inv, 76, 1, 33, "&aInformation", "&7Keep your coins safe in the bank!", "&7You lose half the coins in your", "&7purse when dying in combat.", "", "&7Balance limit: &650 Million", "", "&7The banker rewards you every 31", "&7hours with &binterest&7 for the", "&7coins in your bank balance", "", "&7Interest in: &b00h00m00s", "&7Last Interest: &60 coins", "&7Projected: ^60 coins &b(0%)");

        plr.openInventory(inv);
    }

    public static void craftingMenu(Player plr, String uuid, String type) {

        if (type.equals("empty")) {
            Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Crafting Grid"));
            GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15, 16, 18, 19, 23, 25, 27, 28, 32, 33, 34, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45);
            GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 14, 1, 46, 47, 48, 49, 50, 51, 52, 53, 54);

            // Quick Crafting Slots
            if (!plr.hasPermission("sbr.quickcraft.slot1"))
                GUIHelper.addItem(inv, 160, 14, 1, 17, "&cQuick Crafting (Locked)", "&7To use quick craft, you can purchase", "&7this using Gems in the community store.");
            else
                GUIHelper.addItem(inv, 160, 14, 1, 17, "&aQuick Crafting", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");

            if (!plr.hasPermission("sbr.quickcraft.slot2"))
                GUIHelper.addItem(inv, 160, 14, 1, 26, "&cQuick Crafting (Locked)", "&7To use quick craft, you can purchase", "&7this using Gems in the community store.");
            else
                GUIHelper.addItem(inv, 160, 14, 1, 26, "&aQuick Crafting", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");

            if (!plr.hasPermission("sbr.quickcraft.slot3"))
                GUIHelper.addItem(inv, 160, 14, 1, 35, "&aQuick Crafting", "&7No recipes found in your inventory,", "&7try looking at your recipe book!");
            else
                GUIHelper.addItem(inv, 160, 14, 1, 35, "&cQuick Crafting (Locked)", "&7To use quick craft, you can purchase", "&7this using Gems in the community store.");

            plr.openInventory(inv);
        }

    }

    public static void bankDeposit(Player plr, String uuid, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        double purse = player_data.getPurse();
        double bank = player_data.getBank();

        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank Deposit"));

        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34, 35, 36);
        GUIHelper.addItem(inv, 54, 64, 12, "&aYour whole purse", "&8Bank deposit", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount to deposit: &6" + String.format("%,.1f", purse), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 54, 32, 14, "&aHalf your purse", "&8Bank deposit", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount to deposit: &6" + String.format("%,.1f", purse / 2), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 323, 0, 1, 16, "&aSpecific amount", "&7Current balance: &6" + String.format("%,.1f", bank), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 166, 1, 32, "&cClose");
        plr.openInventory(inv);

    }

    public static void bankWithdrawal(Player plr, String uuid, SkyblockRemastered plugin) {

        PlayerObject player_data = plugin.playerManager.getPlayerData(plr);

        double bank = player_data.getBank();

        Inventory inv = Bukkit.createInventory(plr, 36, Utils.chat("Bank Withdrawal"));

        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34, 35, 36);
        GUIHelper.addItem(inv, 158, 1, 11, "&aEverything in your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank), "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 158, 1, 13, "&aHalf your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank / 2), "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 158, 1, 15, "&a20% of your account", "&8Bank withdrawal", "", "&7Current balance: &6" + String.format("%,.0f", bank), "&7Amount of withdraw: &6" + String.format("%,.1f", bank * .2), "", "&eClick to withdraw coins!");
        GUIHelper.addItem(inv, 323, 0, 1, 17, "&aSpecific amount", "&7Current balance: &6" + String.format("%,.0f", bank), "", "&eClick to deposit coins!");
        GUIHelper.addItem(inv, 166, 1, 32, "&cClose");
        plr.openInventory(inv);

    }

    public static void skyblockMenu(Player plr, String uuid, FileConfiguration db, SkyblockRemastered plugin) {

        //  try {

        PlayerObject po = plugin.playerManager.getPlayerData(plr);

        int health = po.getMaxHealth();
        int defense = po.getDefense();
        int strength = po.getStrength();
        int speed = po.getSpeed();
        int critdamage = po.getCritDamage();
        int critchance = po.getCritChance();
        int atkspeed = po.getAtkSpeed();
        int intel = po.getMaxIntelligence();
        int seacreaturechance = po.getSeaCreatureChance();
        int magicfind = po.getMagicFind();
        int petluck = po.getPetLuck();

        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("SkyBlock Menu"));
        GUIHelper.addItem(inv, 54, 1, 26, "&aPersonal Vault", "&7Store global items that you want",
                "&7to access at any time from", "&7anywhere right here.", " ", "&eClick to open!");
        GUIHelper.addItem(inv, 276, 1, 20, "&aYour Skills", "&7View your Skill progression", "&7rewards.", " ",
                "&eClick to view!");
        GUIHelper.addItem(inv, 321, 1, 21, "&aCollection", "&7View all of the items availiable",
                "&7in SkyBlock. Collect more of an", "&7item to unlock rewards on your",
                "&7way to becoming an Expert in Skyblock!", " ", "&eClick to view!");
        GUIHelper.addItem(inv, 340, 1, 22, "&aRecipe Book", "&7Through your adventure, you will",
                "&7unlock recipes for all kinds of", "&7special items! You can view how",
                "&7to craft these items here.", " ", "&eClick to view!");
        GUIHelper.addItem(inv, 388, 1, 23, "&aTrades", "&7View your available trades.",
                "&7These trades are always", "&7availiable and accessible through", "&7the Skyblock Menu.", " ",
                "&eClick to view!");
        GUIHelper.addItem(inv, 386, 1, 24, "&aQuest Log", "&7Not Coming Soon", " ", "&eClick to view!");
//            GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
//                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0="),
//                    "&dFairy Soul", "&7Collect the souls of dead fairies", "&7around the map, for permanent stats.", "",
//                    " &c✖ &eFound: &d"
//                            + db.getConfigurationSection(plr.getUniqueId().toString() + ".Information")
//                            .getInt("fairySouls")
//                            + "&7/&d0"),
//                    25);
        GUIHelper.addItem(inv, 352, 1, 31, "&aPets", "&7View and manager all of your Pets.", " ",
                "&7Level up your pets faster by", "&7gaining xp in their favorite", "&7skill!", " ",
                "&7Selected pet: &fNone", " ", "&eClick to view!");
        GUIHelper.addItem(inv, 299, 1, 33, "&aWardrobe", "&7Store armor sets and quickly", "&7swap between them!",
                "", "&eClick to view!");
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM2ZTk0ZjZjMzRhMzU0NjVmY2U0YTkwZjJlMjU5NzYzODllYjk3MDlhMTIyNzM1NzRmZjcwZmQ0ZGFhNjg1MiJ9fX0"),
                "&aPersonal Bank", "&7Contact your Banker from", "&7anywhere.", "&7Cooldown: &e-", " ",
                "&7Banker Status:", "&a-", " ", "&eClick to open!"), 34);
//            GUIHelper.addItem(inv, 345, 1, 46, "&aPlugin Developer Menu",
//                    "&7View the Plugin Developer Menu, only availiable", "&7to the plugin developers.", " ",
//                    plr.getName().equalsIgnoreCase("apollo30") ? "&aACCESS GRANTED" : "&cACCESS DENIED");
        GUIHelper.addItem(inv, 166, 1, 50, "&cClose");
        GUIHelper.addItem(inv, 58, 1, 32, "&aCrafting Table", "&7Opens the crafting grid", " ", "&eClick to use!");
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17,
                18, 19, 27, 28, 29, 30, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 47, 52, 53, 54);
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
                "ewogICJ0aW1lc3RhbXAiIDogMTU5OTQ2Nzk1ODg0NSwKICAicHJvZmlsZUlkIiA6ICIwMWIzZWY3YmQzOWI0OGFiOTc0YmE1NTdiMzFhMDczOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJBcG9sbG8zMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85YmM2ZjczM2Q0NTIyZTMwMTdmZjAyNGY0NWIxZjc5OGY2Nzc0YTU5N2Q3ZGFlZTljNzkxNWVkNDEyZGVkZDJjIgogICAgfQogIH0KfQ"),
                "&aCredits", "&7Click to view credits for this project!"), 46);
        GUIHelper.addSkull(inv, plr.getName(), 1, 14, "&aYour Skyblock Profile",
                "  " + (po.getAbsorptionHealth() > 0 ? "&e" : "&c") + getUnicode("heart") + " Health " + (po.getAbsorptionHealth() > 0 ? "" : "&f") + (health + po.getExtraHealth() + po.getAbsorptionHealth()) + " HP",
                "  &a" + getUnicode("defense") + " Defense &f" + defense,
                po.getTrueDefense() > 0 ? "  &f" + getUnicode("td") + " True Defense " + po.getTrueDefense() : "",
                "  &c" + getUnicode("strength") + " Strength &f" + strength,
                "  &f" + getUnicode("speed") + " Speed " + speed,
                "  &9" + getUnicode("cc") + " Crit Chance &f" + critchance + "%",
                "  &9" + getUnicode("cd") + " Crit Damage &f" + critdamage + "%",
                "  &e" + getUnicode("atkspeed") + " Bonus Attack Speed &f" + atkspeed + "%",
                "  &b" + getUnicode("intel") + " Intelligence &f" + (intel + po.getExtraIntelligence()),
                "  &3" + getUnicode("seacreature") + "&3 Sea Creature Chance &f" + seacreaturechance + "%",
                "  &b" + getUnicode("mf") + " Magic Find &f" + magicfind,
                "  &d" + getUnicode("petluck") + " Pet Luck &f" + petluck,
                " ", "&eClick to view your profile!");
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0MDk0MmYzNjRmNmNiY2VmZmNmMTE1MTc5NjQxMDI4NmE0OGIxYWViYTc3MjQzZTIxODAyNmMwOWNkMSJ9fX0="),
                "&bFast Travel", "&7Teleport to the hub or", "&7your island!", "",
                "&bLeft-Click to warp home.", "&eRight-Click to warp to hub."), 48);
//            GUIHelper.addItem(inv, 421, 1, 49, "&aProfile Management", "&7You are limited to &c1 &7profile in this",
//                    "&7plugin.", " ", "&7Profiles: &e1&6/&e1",
//                    "&7Playing on: " + db.getConfigurationSection(plr.getUniqueId().toString()).getString("Profile"));
        GUIHelper.addItem(inv, 76, 1, 51, "&aSettings", "&7View and edit your SkyBlock settings.", " ",
                "&eClick to altar!");
        plr.openInventory(inv);
        // } catch (Exception e) {
        //     Utils.broadCast(e.toString());
        // }
    }

    public static void skillMenu(Player plr, String uuid, FileConfiguration db) {

        try {

            ConfigurationSection farming = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Farming");
            ConfigurationSection mining = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Mining");
            ConfigurationSection combat = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Combat");
            ConfigurationSection foraging = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Foraging");
            ConfigurationSection fishing = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Fishing");
            ConfigurationSection enchanting = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Enchanting");
            ConfigurationSection alchemy = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Alchemy");
            ConfigurationSection carpentry = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Carpentry");
            ConfigurationSection runecrafting = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Runecrafting");
            ConfigurationSection taming = db.getConfigurationSection(plr.getUniqueId().toString() + ".Skills.Taming");

            Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Skills"));
            GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 27, 28, 29, 30, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 51, 52, 53, 54);
            GUIHelper.addItem(inv, 276, 1, 5, "&aYour Skills", "&7View your skill progression and", "&7rewards", "", "&eClick to show rankings!", "&c&oNot Coming Soon");
            GUIHelper.addItem(inv, 294, 0, 1, 20, "&aFarming " + farming.getInt("level"), "&7Harvest crops and shear sheep to", "&7earn Farming XP!", "", "&7Progress to Level " + (farming.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + farming.getInt("neededXP"));
            GUIHelper.addItem(inv, 274, 0, 1, 21, "&aMining " + mining.getInt("level"), "&7Spelunk islands for ores and", "&7valuable materials to earn", "&7Mining XP!", "", "&7Progress to Level " + (mining.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + mining.getInt("neededXP"));
            GUIHelper.addItem(inv, 272, 0, 1, 22, "&aCombat " + combat.getInt("level"), "&7Fight mobs and players to earn", "&7Combat XP!", "", "&7Progress to Level " + (combat.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + combat.getInt("neededXP"));
            GUIHelper.addItem(inv, 6, 3, 1, 23, "&aForaging " + foraging.getInt("level"), "&7Cut trees and forage for other", "&7plants to earn Foraging XP!", "", "&7Progress to Level " + (foraging.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + foraging.getInt("neededXP"));
            GUIHelper.addItem(inv, 346, 0, 1, 24, "&aFishing " + fishing.getInt("level"), "&7Visit your local pond to fish", "&7and earn Fishing XP!", "", "&7Progress to Level " + (fishing.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + fishing.getInt("neededXP"));
            GUIHelper.addItem(inv, 116, 0, 1, 25, "&aEnchanting " + enchanting.getInt("level"), "&7Enchant items to earn Enchanting", "&7XP!", "", "&7Progress to Level " + (enchanting.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + enchanting.getInt("neededXP"));
            GUIHelper.addItem(inv, 379, 0, 1, 26, "&aAlchemy " + alchemy.getInt("level"), "&7Brew potions to earn Alchemy XP!", "", "&7Progress to Level " + (alchemy.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + alchemy.getInt("neededXP"));
            GUIHelper.addItem(inv, 58, 0, 1, 31, "&aCarpentry " + carpentry.getInt("level"), "&7Craft items to earn Carpentry", "&7XP!", "", "&7Progress to Level " + (carpentry.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + carpentry.getInt("neededXP"));
            GUIHelper.addItem(inv, 378, 0, 1, 32, "&aRunecrafting " + runecrafting.getInt("level"), "&7Slay bosses, runic mobs & fuse", "&7runes to earn Runecrafting XP!", "", "&7Progress to Level " + (runecrafting.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + runecrafting.getInt("neededXP"));
            GUIHelper.addItem(inv, 383, 0, 1, 33, "&aTaming " + taming.getInt("level"), "&7Level up pets to earn Taming XP!", "", "&7Progress to Level " + (taming.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + taming.getInt("neededXP"));
            GUIHelper.addItem(inv, 262, 1, 49, "&aGo Back", "&7To SkyBlock Menu");
            GUIHelper.addItem(inv, 166, 1, 50, "&cClose");
            plr.openInventory(inv);

        } catch (Exception ignored) {

        }

    }

    public static void tradeMenu(Player plr, String s) {

        try {
            Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Trades"));

            GUIHelper.addItem(inv, 351, 8, 1, 11, "&c???", "&7Progress through your item", "&7collections and explore the", "&7world to unlock new trades!");
            GUIHelper.addItem(inv, 263, 2, 12, "&fCoal &8x2", "&8Brewing Ingredient", "&7increases the speed of", "&7your minion by &a5%", "&7for 30 minutes!", "", "&7Cost", "&fOak Wood", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 2, 4, 13, "&fGrass &8x4", "", "&7Cost", "&fDirt &8x4", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 3, 2, 14, "&fDirt &8x2", "", "&7Cost", "&fSeeds &8x8", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 337, 1, 15, "&fClay", "", "&7Cost", "&fSeeds &8x12", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 18, 1, 16, "&fOak Leaves", "", "&7Cost", "&fOak Sapling", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 18, 1, 1, 17, "&fSpruce Leaves", "", "&7Cost", "&fSpruce Sapling", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 18, 2, 1, 20, "&fBirch Leaves", "", "&7Cost", "&fBirch Sapling", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 18, 3, 1, 21, "&fJungle Leaves", "", "&7Cost", "&fJungle Sapling", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 161, 0, 1, 22, "&fAcacia Leaves", "", "&7Cost", "&fAcacia Sapling", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 161, 1, 1, 23, "&fDark Oak Leaves", "", "&7Cost", "&fDark Oak Sapling", "", "&eClick to trade!");
            GUIHelper.addEnchantedItem(inv, 351, 15, 1, 24, "&fEnchanted Bone Meal", "&7Instantly grows crops and", "&7saplings.", "", "&7Cost", "&fBone &8x64", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 31, 1, 1, 25, "&fLong Grass", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 31, 2, 1, 26, "&fFern", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 32, 1, 1, 29, "&fDead Bush", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 175, 2, 1, 30, "&fDouble Tallgrass", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 31, 1, 1, 31, "&fLong Grass", "", "&7Cost", "&fDirt &8x8", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 326, 0, 1, 32, "&fWater Bucket", "", "&7Cost", "&612 Coins", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 327, 0, 1, 33, "&fLava Bucket", "", "&7Cost", "&620 Coins", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 12, 1, 1, 34, "&fRed Sand", "", "&7Cost", "&fSand", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 88, 0, 1, 35, "&fSoul Sand", "", "&7Cost", "&fSand &8x2", "&fFermented Spider Eye", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 110, 0, 1, 38, "&fMycelium", "", "&7Cost", "&fDirt", "&fRed Mushroom", "&fBrown Mushroom", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 405, 0, 1, 39, "&fNether Brick", "", "&7Cost", "&fNetherrack", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 19, 0, 1, 40, "&fSponge", "", "&7Cost", "&fWet Sponge", "", "&eClick to trade!");
            GUIHelper.addEnchantedItem(inv, 120, 0, 2, 40, "&aTeleport Pad &8x2", "", "&7Cost", "&aEnchanted Eye of Ender &8x2", "", "&eClick to trade!");
            GUIHelper.addItem(inv, 262, 1, 49, "&aGo Back", "&7To SkyBlock Menu");
            GUIHelper.addItem(inv, 166, 1, 50, "&cClose");
            GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18, 19, 27, 28, 36, 37, 45, 46, 47, 48, 51, 52, 53, 54);

            plr.openInventory(inv);
        } catch (Exception ignored) {

        }
    }

    public static void adminPanel(Player plr, String uuid) {
        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Items"));
        GUIHelper.addItem(inv, 276, 0, 1, 11, "&9Aspect of The End", "&7Damage: &c+1", "&7Strength: &c+12", "&7Crit Chance: &c+9%", "&7Crit Damage: &c+15%", "&7Bonus Attack Speed: &c+5%", "", "&7Intelligence: &a+12", "", "&6Item Ability: Instant Transmission &a&lRIGHT CLICK", "&7Teleport &a8 blocks&7 ahead of", "&7you and again &a+50 &f" + getUnicode("speed") + " Speed", "&7for &a3 seconds&7.", "&8Mana Cost: &b50");
        GUIHelper.addItem(inv, 276, 0, 1, 12, "&9Aspect of The End", "&7Damage: &c+110", "&7Strength: &c+112", "&7Crit Chance: &c+9%", "&7Crit Damage: &c+15%", "&7Bonus Attack Speed: &c+5%", "", "&7Intelligence: &a+12", "", "&6Item Ability: Instant Transmission &a&lRIGHT CLICK", "&7Teleport &a8 blocks&7 ahead of", "&7you and again &a+50 &f" + getUnicode("speed") + " Speed", "&7for &a3 seconds&7.", "&8Mana Cost: &b50");
        GUIHelper.addItem(inv, 276, 0, 1, 13, "&6Aspect of the Dragons", "&7Damage: &c+225", "&7Strength: &c+100", "", "&6Item Ability: Dragon Rage &a&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a&l1,050 &r&7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &b100", "&8Cooldown: &a5s");
        GUIHelper.addItem(inv, 276, 0, 1, 14, "&6Aspect of the Dragons", "&7Damage: &c+69,420", "&7Strength: &c+420", "&7Bonus Attack Speed: &c+100%", "", "&6Item Ability: Dragon Rage &a&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a&l1,050 &r&7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &b100", "&8Cooldown: &a5s");
        GUIHelper.addItem(inv, GUIHelper.addSkull("aHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NDhhOTE4YTAyZmViMWIzOWQwY2I2YzNjZDQ1ZGEzY2JkMGE3NTUxMjI4MWIyODU5NmFhZjBiZWFjM2FmYzMwP3Y2"), 15);
        GUIHelper.addItem(inv, GUIHelper.addLore(
                GUIHelper.addSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
                "&5Summoning Eye",
                "&7Use this at the &5Ender Altar",
                "&7in the &5Dragon's Nest&7 to",
                "&7summon Ender Dragons!",
                "",
                "&5&lEPIC"), 16);
        GUIHelper.addItem(inv, GUIHelper.addLore(
                GUIHelper.addSkull(Utils.urlToBase64("https://textures.minecraft.net/texture/9336d7cc95cbf6689f5e8c954294ec8d1efc494a4031325bb427bc81d56a484d")),
                "&aMaddox Batphone",
                "&6Item Ability: Whassup? &e&lRIGHT CLICK",
                "&7Lets you call &dMaddox&7, when",
                "&7he's not busy.",
                "",
                "&a&lUNCOMMON"), 17);
        GUIHelper.addItem(inv, 2, 0, 1, 54, "&aCreative");
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18, 19, 27, 28, 36, 37, 45,
                46, 47, 48, 49, 50, 51, 52, 53);
        plr.openInventory(inv);
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
