package xyz.apollo30.skyblockremastered.guis.SkillMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.guis.constructors.Menu;
import xyz.apollo30.skyblockremastered.guis.constructors.MenuUtility;
import xyz.apollo30.skyblockremastered.managers.PlayerManager;
import xyz.apollo30.skyblockremastered.objects.PlayerObject;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class SkillMenu extends Menu {

    private Player plr;

    public SkillMenu(MenuUtility menuUtility, Player p) {
        super(menuUtility);
        plr = p;
    }

    @Override
    public String getMenuName() {
        return "Skills";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        PlayerObject po = PlayerManager.playerObjects.get(plr);
        if (po == null) {
            plr.sendMessage(Utils.chat("&cError when loading the player's data, please try again later."));
            return;
        }
        GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 27, 28, 29, 30, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 51, 52, 53, 54);
        GUIHelper.addItem(inv, 276, 1, 5, "&aYour Skills", "&7View your skill progression and", "&7rewards", "", "&eClick to show rankings!", "&c&oNot Coming Soon");
        GUIHelper.addItem(inv, 294, 0, 1, 20, "&aFarming " + po.getFarmingLevel(), "&7Harvest crops and shear sheep to", "&7earn Farming XP!", "", "&7Progress to Level " + (po.getFarmingLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getFarmingNeeded());
        GUIHelper.addItem(inv, 274, 0, 1, 21, "&aMining " + po.getMiningLevel(), "&7Spelunk islands for ores and", "&7valuable materials to earn", "&7Mining XP!", "", "&7Progress to Level " + (po.getMiningLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getMiningNeeded());
        GUIHelper.addItem(inv, 272, 0, 1, 22, "&aCombat " + po.getCombatLevel(), "&7Fight mobs and players to earn", "&7Combat XP!", "", "&7Progress to Level " + (po.getCombatLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getCombatNeeded());
        GUIHelper.addItem(inv, 6, 3, 1, 23, "&aForaging " + po.getForagingLevel(), "&7Cut trees and forage for other", "&7plants to earn Foraging XP!", "", "&7Progress to Level " + (po.getForagingLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getForagingNeeded());
        GUIHelper.addItem(inv, 346, 0, 1, 24, "&aFishing " + po.getFishingLevel(), "&7Visit your local pond to fish", "&7and earn Fishing XP!", "", "&7Progress to Level " + (po.getFarmingLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getFishingNeeded());
        GUIHelper.addItem(inv, 116, 0, 1, 25, "&aEnchanting " + po.getEnchantingLevel(), "&7Enchant items to earn Enchanting", "&7XP!", "", "&7Progress to Level " + (po.getEnchantingLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getEnchantingNeeded());
        GUIHelper.addItem(inv, 379, 0, 1, 26, "&aAlchemy " + po.getAlchemyLevel(), "&7Brew potions to earn Alchemy XP!", "", "&7Progress to Level " + (po.getAlchemyLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getAlchemyNeeded());
        GUIHelper.addItem(inv, 58, 0, 1, 31, "&aCarpentry " + po.getCarpentryLevel(), "&7Craft items to earn Carpentry", "&7XP!", "", "&7Progress to Level " + (po.getCarpentryLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getCarpentryNeeded());
        GUIHelper.addItem(inv, 378, 0, 1, 32, "&aRunecrafting " + po.getRunecraftingLevel(), "&7Slay bosses, runic mobs & fuse", "&7runes to earn Runecrafting XP!", "", "&7Progress to Level " + (po.getRunecraftingLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getRunecraftingNeeded());
        GUIHelper.addItem(inv, 383, 0, 1, 33, "&aTaming " + po.getTamingLevel(), "&7Level up pets to earn Taming XP!", "", "&7Progress to Level " + (po.getTamingLevel() + 1) + ": &e0%", "&f-------------------- &a0&6/&e" + po.getTamingNeeded());
        GUIHelper.addItem(inv, 262, 1, 49, "&aGo Back", "&7To SkyBlock Menu");
        GUIHelper.addItem(inv, 166, 1, 50, "&cClose");
        plr.openInventory(inv);
    }
}
