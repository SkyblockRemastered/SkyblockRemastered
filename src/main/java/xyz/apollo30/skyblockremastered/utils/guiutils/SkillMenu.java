package xyz.apollo30.skyblockremastered.utils.guiutils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class SkillMenu {
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
            Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 27, 28, 29, 30, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 51, 52, 53, 54);
            Utils.createItemID(inv, 276, 1, 5, "&aYour Skills", "&7View your skill progression and", "&7rewards", "", "&eClick to show rankings!", "&c&oNot Coming Soon");
            Utils.createItemByte(inv, 294, 0, 1, 20, "&aFarming " + farming.getInt("level"), "&7Harvest crops and shear sheep to", "&7earn Farming XP!", "", "&7Progress to Level " + (farming.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + farming.getInt("neededXP"));
            Utils.createItemByte(inv, 274, 0, 1, 21, "&aMining " + mining.getInt("level"), "&7Spelunk islands for ores and", "&7valuable materials to earn", "&7Mining XP!", "", "&7Progress to Level " + (mining.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + mining.getInt("neededXP"));
            Utils.createItemByte(inv, 272, 0, 1, 22, "&aCombat " + combat.getInt("level"), "&7Fight mobs and players to earn", "&7Combat XP!", "", "&7Progress to Level " + (combat.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + combat.getInt("neededXP"));
            Utils.createItemByte(inv, 6, 3, 1, 23, "&aForaging " + foraging.getInt("level"), "&7Cut trees and forage for other", "&7plants to earn Foraging XP!", "", "&7Progress to Level " + (foraging.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + foraging.getInt("neededXP"));
            Utils.createItemByte(inv, 346, 0, 1, 24, "&aFishing " + fishing.getInt("level"), "&7Visit your local pond to fish", "&7and earn Fishing XP!", "", "&7Progress to Level " + (fishing.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + fishing.getInt("neededXP"));
            Utils.createItemByte(inv, 116, 0, 1, 25, "&aEnchanting " + enchanting.getInt("level"), "&7Enchant items to earn Enchanting", "&7XP!", "", "&7Progress to Level " + (enchanting.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + enchanting.getInt("neededXP"));
            Utils.createItemByte(inv, 379, 0, 1, 26, "&aAlchemy " + alchemy.getInt("level"), "&7Brew potions to earn Alchemy XP!", "", "&7Progress to Level " + (alchemy.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + alchemy.getInt("neededXP"));
            Utils.createItemByte(inv, 58, 0, 1, 31, "&aCarpentry " + carpentry.getInt("level"), "&7Craft items to earn Carpentry", "&7XP!", "", "&7Progress to Level " + (carpentry.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + carpentry.getInt("neededXP"));
            Utils.createItemByte(inv, 378, 0, 1, 32, "&aRunecrafting " + runecrafting.getInt("level"), "&7Slay bosses, runic mobs & fuse", "&7runes to earn Runecrafting XP!", "", "&7Progress to Level " + (runecrafting.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + runecrafting.getInt("neededXP"));
            Utils.createItemByte(inv, 383, 0, 1, 33, "&aTaming " + taming.getInt("level"), "&7Level up pets to earn Taming XP!", "", "&7Progress to Level " + (taming.getInt("level") + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + taming.getInt("neededXP"));
            Utils.createItemID(inv, 262, 1, 49, "&aGo Back", "&7To SkyBlock Menu");
            Utils.createItemID(inv, 166, 1, 50, "&cClose");
            plr.openInventory(inv);

        } catch (Exception ignored) {

        }

    }
}
