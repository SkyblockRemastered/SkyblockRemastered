package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Armor {

    private final SkyblockRemastered plugin;

    public Armor(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack superiorHelmet = Utils.addLore(
            Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc4YmM0YjI1NjRmMWFlZjI5MzlkNWFhMjg1ZjNhZmFlMDJlOWQ5ZjA4MjQzYjI1Nzk5MTNmZDhmZWRkY2I1NiJ9fX0="),
            "&6Superior Dragon Helmet",
            "&7Strength: &c+10",
            "&7Crit Chance: &c+12%",
            "&7Crit Damage: &c+10%",
            "",
            "&7Health: &a+90",
            "&7Defense: &a+130",
            "&7Speed: &a+3",
            "&7Intelligence: &a+25",
            "",
            "&6Full Set Bonus: Superior Blood",
            "&7All your stats are increased by",
            "&a5% &7and &6Aspect of the",
            "&6Dragons &7ability deals &a50%",
            "&7more damage",
            "",
            "&8This item can be reforged",
            "&6&lLEGENDARY HELMET");

    public ItemStack superiorChestplate = Utils.addLore(
            Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 242, 223, 17),
            "&6Superior Dragon Chestplate",
            "&7Strength: &c+10",
            "&7Crit Chance: &c+2%",
            "&7Crit Damage: &c+10%",
            "",
            "&7Health: &a+150",
            "&7Defense: &a+190",
            "&7Speed: &a+3",
            "&7Intelligence: &a+25",
            "",
            "&6Full Set Bonus: Superior Blood",
            "&7All your stats are increased by",
            "&a5% &7and &6Aspect of the",
            "&6Dragons &7ability deals &a50%",
            "&7more damage",
            "",
            "&8This item can be reforged",
            "&6&lLEGENDARY CHESTPLATE");

    public ItemStack superiorLeggings = Utils.addLore(
            Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 242, 223, 17),
            "&6Superior Dragon Leggings",
            "&7Strength: &c+10",
            "&7Crit Chance: &c+2%",
            "&7Crit Damage: &c+10%",
            "",
            "&7Health: &a+130",
            "&7Defense: &a+170",
            "&7Speed: &a+3",
            "&7Intelligence: &a+25",
            "",
            "&6Full Set Bonus: Superior Blood",
            "&7All your stats are increased by",
            "&a5% &7and &6Aspect of the",
            "&6Dragons &7ability deals &a50%",
            "&7more damage",
            "",
            "&8This item can be reforged",
            "&6&lLEGENDARY LEGGINGS");

    public ItemStack superiorBoots = Utils.addLore(
            Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 242, 93, 24),
            "&6Superior Dragon Boots",
            "&7Strength: &c+10",
            "&7Crit Chance: &c+2%",
            "&7Crit Damage: &c+10%",
            "",
            "&7Health: &a+80",
            "&7Defense: &a+110",
            "&7Speed: &a+3",
            "&7Intelligence: &a+25",
            "",
            "&6Full Set Bonus: Superior Blood",
            "&7All your stats are increased by",
            "&a5% &7and &6Aspect of the",
            "&6Dragons &7ability deals &a50%",
            "&7more damage",
            "",
            "&8This item can be reforged",
            "&6&lLEGENDARY BOOTS");


}
