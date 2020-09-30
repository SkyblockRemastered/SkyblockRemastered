package xyz.apollo30.skyblockremastered.utils.guiutils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.UnicodeUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class AdminPanel {

    public SkyblockRemastered plugin;

    public AdminPanel(final SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static void adminPanel(Player plr, String uuid) {
        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("&aPlugin Developer Menu"));
        Utils.createItemByte(inv, 276, 0, 1, 11, "&9Aspect of The End", "&7Damage: &c+1", "&7Strength: &c+12", "&7Crit Chance: &c+9%", "&7Crit Damage: &c+15%", "&7Bonus Attack Speed: &c+5%", "", "&7Intelligence: &a+12", "", "&6Item Ability: Instant Transmission &a&lRIGHT CLICK", "&7Teleport &a8 blocks&7 ahead of", "&7you and again &a+50 &f" + UnicodeUtils.getUnicode("speed") + " Speed", "&7for &a3 seconds&7.", "&8Mana Cost: &b50");
        Utils.createItemByte(inv, 276, 0, 1, 12, "&9Aspect of The End", "&7Damage: &c+110", "&7Strength: &c+112", "&7Crit Chance: &c+9%", "&7Crit Damage: &c+15%", "&7Bonus Attack Speed: &c+5%", "", "&7Intelligence: &a+12", "", "&6Item Ability: Instant Transmission &a&lRIGHT CLICK", "&7Teleport &a8 blocks&7 ahead of", "&7you and again &a+50 &f" + UnicodeUtils.getUnicode("speed") + " Speed", "&7for &a3 seconds&7.", "&8Mana Cost: &b50");
        Utils.createItemByte(inv, 276, 0, 1, 13, "&6Aspect of the Dragons", "&7Damage: &c+225", "&7Strength: &c+100", "", "&6Item Ability: Dragon Rage &a&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a&l1,050 &r&7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &b100", "&8Cooldown: &a5s");
        Utils.createItemByte(inv, 276, 0, 1, 14, "&6Aspect of the Dragons", "&7Damage: &c+69,420", "&7Strength: &c+420", "&7Bonus Attack Speed: &c+100%", "", "&6Item Ability: Dragon Rage &a&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a&l1,050 &r&7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &b100", "&8Cooldown: &a5s");
        Utils.addItem(inv, Utils.getSkull("aHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NDhhOTE4YTAyZmViMWIzOWQwY2I2YzNjZDQ1ZGEzY2JkMGE3NTUxMjI4MWIyODU5NmFhZjBiZWFjM2FmYzMwP3Y2"), 15);
        Utils.addItem(inv, Utils.addLore(
                Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFhOGZjOGRlNjQxN2I0OGQ0OGM4MGI0NDNjZjUzMjZlM2Q5ZGE0ZGJlOWIyNWZjZDQ5NTQ5ZDk2MTY4ZmMwIn19fQ=="),
                "&5Summoning Eye",
                "&7Use this at the &5Ender Altar",
                "&7in the &5Dragon's Nest&7 to",
                "&7summon Ender Dragons!",
                "",
                "&5&lEPIC"), 16);
        Utils.addItem(inv, Utils.addLore(
                Utils.getSkull(Utils.urlToBase64("https://textures.minecraft.net/texture/9336d7cc95cbf6689f5e8c954294ec8d1efc494a4031325bb427bc81d56a484d")),
                "&aMaddox Batphone",
                "&6Item Ability: Whassup? &e&lRIGHT CLICK",
                "&7Lets you call &dMaddox&7, when",
                "&7he's not busy.",
                "",
                "&a&lUNCOMMON"), 17);
        Utils.createItemByte(inv, 2, 0, 1, 54, "&aCreative");
        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 18, 19, 27, 28, 36, 37, 45,
                46, 47, 48, 49, 50, 51, 52, 53);
        if (plr.hasPermission("sbr.openpanel")) {
            plr.openInventory(inv);
        }
        plr.sendMessage(ChatColor.RED + "Sorry, only admins can open this!");
    }
}
