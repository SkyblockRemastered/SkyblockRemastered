package xyz.apollo30.skyblockremastered.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class SkyblockMenu {

    public static void skyblockMenu(Player plr, String uuid, SkyblockRemastered plugin) {

        //  try {

        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

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
        int ferocity = po.getFerocity();

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
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk2OTIzYWQyNDczMTAwMDdmNmFlNWQzMjZkODQ3YWQ1Mzg2NGNmMTZjMzU2NWExODFkYzhlNmIyMGJlMjM4NyJ9fX0="),
                "&dFairy Soul", "&7Collect the souls of dead fairies", "&7around the map, for permanent stats.", "",
                " &c✖ &eFound: &d"
                        + po.getFairySouls()
                        + "&7/&d0"),
                25);
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
                "  " + (po.getAbsorptionHealth() > 0 ? "&e" : "&c") + GUIHelper.getUnicode("heart") + " Health " + (po.getAbsorptionHealth() > 0 ? "" : "&f") + (health + po.getExtraHealth() + po.getAbsorptionHealth()) + " HP",
                "  &a" + GUIHelper.getUnicode("defense") + " Defense &f" + defense,
                po.getTrueDefense() > 0 ? "  &f" + GUIHelper.getUnicode("td") + " True Defense " + po.getTrueDefense() : "",
                "  &c" + GUIHelper.getUnicode("strength") + " Strength &f" + strength,
                "  &f" + GUIHelper.getUnicode("speed") + " Speed " + speed,
                "  &9" + GUIHelper.getUnicode("cc") + " Crit Chance &f" + critchance + "%",
                "  &9" + GUIHelper.getUnicode("cd") + " Crit Damage &f" + critdamage + "%",
                "  &e" + GUIHelper.getUnicode("atkspeed") + " Bonus Attack Speed &f" + atkspeed + "%",
                "  &b" + GUIHelper.getUnicode("intel") + " Intelligence &f" + (intel + po.getExtraIntelligence()),
                "  &3" + GUIHelper.getUnicode("seacreature") + "&3 Sea Creature Chance &f" + seacreaturechance + "%",
                "  &b" + GUIHelper.getUnicode("mf") + " Magic Find &f" + magicfind,
                "  &d" + GUIHelper.getUnicode("petluck") + " Pet Luck &f" + petluck,
                "  &c" + GUIHelper.getUnicode("ferocity") + " Ferocity &f" + ferocity,
                " ", "&eClick to view your profile!");
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0MDk0MmYzNjRmNmNiY2VmZmNmMTE1MTc5NjQxMDI4NmE0OGIxYWViYTc3MjQzZTIxODAyNmMwOWNkMSJ9fX0="),
                "&bFast Travel", "&7Teleport to the hub or", "&7your island!", "",
                "&bLeft-Click to warp home.", "&eRight-Click to warp to hub."), 48);
        GUIHelper.addItem(inv, 421, 1, 49, "&aProfile Management", "&7You are limited to &c1 &7profile in this",
                "&7plugin.", " ", "&7Profiles: &e1&6/&e1",
                "&7Playing on: " + po.getProfile());
        GUIHelper.addItem(inv, 76, 1, 51, "&aSettings", "&7View and edit your SkyBlock settings.", " ", "&eClick to altar!");
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("4cb3acdc11ca747bf710e59f4c8e9b3d949fdd364c6869831ca878f0763d1787")), "&aQuiver", "&7A masterfully crafted Quiver", "&7which holds any kind of", "&7projectile you can think of!", " ", "&eClick to open!"), 45);
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("372466603bae9063ce7d94351103469beca3c5fe2b1e977e6427def37699ec9")), "&aPotion Bag", "&7A handy bag for holding your", "&7Potions in.", " ", "&eClick to open!"), 53);
        GUIHelper.addItem(inv, GUIHelper.addLore(GUIHelper.addSkull(Utils.urlToBase64("961a918c0c49ba8d053e522cb91abc74689367b4d8aa06bfc1ba9154730985ff")), "&aAccessory Bag", "&7A special bag which can hold", "&7Talismans, Rings, Artificats and", "&7Orbs within it. All will still", "&7work while in this bag!", " ", "&eClick to open!"), 54);
        plr.openInventory(inv);
        // } catch (Exception e) {
        //     Utils.broadCast(e.toString());
        // }
    }

}
