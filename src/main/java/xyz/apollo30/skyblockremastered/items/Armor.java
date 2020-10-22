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

    // Dragon Armor
    public ItemStack SUPERIOR_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc4YmM0YjI1NjRmMWFlZjI5MzlkNWFhMjg1ZjNhZmFlMDJlOWQ5ZjA4MjQzYjI1Nzk5MTNmZDhmZWRkY2I1NiJ9fX0="), "&6Superior Dragon Helmet", "&7Strength: &c+10", "&7Crit Chance: &c+12%", "&7Crit Damage: &c+10%", "", "&7Health: &a+90", "&7Defense: &a+130", "&7Speed: &a+3", "&7Intelligence: &a+25", "", "&6Full Set Bonus: Superior Blood", "&7All your stats are increased by", "&a5% &7and &6Aspect of the", "&6Dragons &7ability deals &a50%", "&7more damage", "", "&8This item can be reforged", "&6&lLEGENDARY HELMET");
    public ItemStack SUPERIOR_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 242, 223, 17), "&6Superior Dragon Chestplate", "&7Strength: &c+10", "&7Crit Chance: &c+2%", "&7Crit Damage: &c+10%", "", "&7Health: &a+150", "&7Defense: &a+190", "&7Speed: &a+3", "&7Intelligence: &a+25", "", "&6Full Set Bonus: Superior Blood", "&7All your stats are increased by", "&a5% &7and &6Aspect of the", "&6Dragons &7ability deals &a50%", "&7more damage", "", "&8This item can be reforged", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack SUPERIOR_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 242, 223, 17), "&6Superior Dragon Leggings", "&7Strength: &c+10", "&7Crit Chance: &c+2%", "&7Crit Damage: &c+10%", "", "&7Health: &a+130", "&7Defense: &a+170", "&7Speed: &a+3", "&7Intelligence: &a+25", "", "&6Full Set Bonus: Superior Blood", "&7All your stats are increased by", "&a5% &7and &6Aspect of the", "&6Dragons &7ability deals &a50%", "&7more damage", "", "&8This item can be reforged", "&6&lLEGENDARY LEGGINGS");
    public ItemStack SUPERIOR_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 242, 93, 24), "&6Superior Dragon Boots", "&7Strength: &c+10", "&7Crit Chance: &c+2%", "&7Crit Damage: &c+10%", "", "&7Health: &a+80", "&7Defense: &a+110", "&7Speed: &a+3", "&7Intelligence: &a+25", "", "&6Full Set Bonus: Superior Blood", "&7All your stats are increased by", "&a5% &7and &6Aspect of the", "&6Dragons &7ability deals &a50%", "&7more damage", "", "&8This item can be reforged", "&6&lLEGENDARY BOOTS");
    public ItemStack UNSTABLE_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjkyMmI1ZjhkNTU0Y2E5MjNmOTY4MzJhNWE0ZTkxNjliYzJjZGIzNjBhMmIwNmViZWMwOWI2YTZhZjQ2MThlMyJ9fX0="), "&6Unstable Dragon Helmet", " ", "&7Crit Damage: &c+15%", "&7Crit Chance: &c+5%", " ", "&7Health: &a+70", "&7Defense: &a+110", "&7Intelligence: &a+25", " ", "&6Full Set Bonus: Unstable Blood", "&7Sometimes strikes nearby mobs", "&7with lighting. It's unstable!", " ", "&8This item can be reforged!", "&6&lLEGENDARY HELMET");
    public ItemStack UNSTABLE_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 178, 18, 227), "&6Unstable Dragon Chestplate", " ", "&7Crit Damage: &c+15%", "&7Crit Chance: &c+5%", " ", "&7Health: &a+120", "&7Defense: &a+160", " ", "&6Full Set Bonus: Unstable Blood", "&7Sometimes strikes nearby mobs", "&7with lighting. It's unstable!", " ", "&8This item can be reforged!", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack UNSTABLE_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 178, 18, 227), "&6Unstable Dragon Leggings", " ", "&7Crit Damage: &c+15%", "&7Crit Chance: &c+5%", " ", "&7Health: &a+100", "&7Defense: &a+140", " ", "&6Full Set Bonus: Unstable Blood", "&7Sometimes strikes nearby mobs", "&7with lighting. It's unstable!", " ", "&8This item can be reforged!", "&6&lLEGENDARY LEGGINGS");
    public ItemStack UNSTABLE_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 178, 18, 227), "&6Unstable Dragon Boots", " ", "&7Crit Damage: &c+15%", "&7Crit Chance: &c+5%", " ", "&7Health: &a+60", "&7Defense: &a+90", " ", "&6Full Set Bonus: Unstable Blood", "&7Sometimes strikes nearby mobs", "&7with lighting. It's unstable!", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOOTS");
    public ItemStack YOUNG_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWM0ODZhZjNiODgyNzY2ZTgyYTBiYzE2NjVmZjAyZWI2ZTg3M2I2ZTBkNzcxZjNhZGFiYzc1OWI3MjAyMjZhIn19fQ=="), "&6Young Dragon Helmet", " ", "&7Health: &a+70", "&7Defense: &a+110", "&7Speed: &a+20", " ", "&6Full Set Bonus: Young Blood", "&7Gain &a+70&7 Walk Speed while", "&7you are above &a50%&7 HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY HELMET");
    public ItemStack YOUNG_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 221, 228, 240), "&6Young Dragon Chestplate", " ", "&7Health: &a+120", "&7Defense: &a+160", "&7Speed: &a+20", " ", "&6Full Set Bonus: Young Blood", "&7Gain &a+70&7 Walk Speed while", "&7you are above &a50%&7 HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack YOUNG_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 221, 228, 240), "&6Young Dragon Leggings", " ", "&7Health: &a+100", "&7Defense: &a+140", "&7Speed: &a+20", " ", "&6Full Set Bonus: Young Blood", "&7Gain &a+70&7 Walk Speed while", "&7you are above &a50%&7 HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY LEGGINGS");
    public ItemStack YOUNG_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 221, 228, 240), "&6Young Dragon Boots", " ", "&7Health: &a+60", "&7Defense: &a+90", "&7Speed: &a+20", " ", "&6Full Set Bonus: Young Blood", "&7Gain &a+70&7 Walk Speed while", "&7you are above &a50%&7 HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOOTS");
    public ItemStack PROTECTOR_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjM3YTU5NmNkYzRiMTFhOTk0OGZmYTM4YzJhYTNjNjk0MmVmNDQ5ZWIwYTM5ODIyODFkM2E1YjVhMTRlZjZhZSJ9fX0="), "&6Protector Dragon Helmet", " ", "&7Health: &a+70", "&7Defense: &a+135", " ", "&6Full Set Bonus: Protective Blood", "&7Increases the defence of each", "&7armor piece by &a+1% ❈", "&aDefense&7 for each missing", "&7percent of HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY HELMET");
    public ItemStack PROTECTOR_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 153, 151, 139), "&6Protector Dragon Chestplate", " ", "&7Health: &a+120", "&7Defense: &a+185", " ", "&6Full Set Bonus: Protective Blood", "&7Increases the defence of each", "&7armor piece by &a+1% ❈", "&aDefense&7 for each missing", "&7percent of HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack PROTECTOR_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 153, 151, 139), "&6Protector Dragon Leggings", " ", "&7Health: &a+100", "&7Defense: &a+165", " ", "&6Full Set Bonus: Protective Blood", "&7Increases the defence of each", "&7armor piece by &a+1% ❈", "&aDefense&7 for each missing", "&7percent of HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY LEGGINGS");
    public ItemStack PROTECTOR_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 153, 151, 139), "&6Protector Dragon Boots", " ", "&7Health: &a+60", "&7Defense: &a+115", " ", "&6Full Set Bonus: Protective Blood", "&7Increases the defence of each", "&7armor piece by &a+1% ❈", "&aDefense&7 for each missing", "&7percent of HP.", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOOTS");
    public ItemStack OLD_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTllOWU1NjAwNDEwYzFkMDI1NDQ3NGE4MWZlY2ZiMzg4NWMxY2Y2ZjUwNDE5MGQ4NTZmMGVjN2M5ZjA1NWM0MiJ9fX0="), "&6Old Dragon Helmet", " ", "&7Health: &a+110", "&7Defense: &a+90", " ", "&6Full Set Bonus: Old Blood", "&7Increases the strength of", "&l&9Growth&r&7, &l&9Protection&r&7,", "&l&9Feather Falling&r&7, &l&9Sugar", "&l&9Rush&r&7, and &l&9True Protection&r&7", "&7while worn.", " ", "&8This item can be reforged!", "&6&lLEGENDARY HELMET");
    public ItemStack OLD_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 240, 230, 170), "&6Old Dragon Chestplate", " ", "&7Health: &a+160", "&7Defense: &a+150", " ", "&6Full Set Bonus: Old Blood", "&7Increases the strength of", "&l&9Growth&r&7, &l&9Protection&r&7,", "&l&9Feather Falling&r&7, &l&9Sugar", "&l&9Rush&r&7, and &l&9True Protection&r&7", "&7while worn.", " ", "&8This item can be reforged!", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack OLD_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 240, 230, 170), "&6Old Dragon Leggings", " ", "&7Health: &a+130", "&7Defense: &a+140", " ", "&6Full Set Bonus: Old Blood", "&7Increases the strength of", "&l&9Growth&r&7, &l&9Protection&r&7,", "&l&9Feather Falling&r&7, &l&9Sugar", "&l&9Rush&r&7, and &l&9True Protection&r&7", "&7while worn.", " ", "&8This item can be reforged!", "&6&lLEGENDARY LEGGINGS");
    public ItemStack OLD_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 240, 230, 170), "&6Old Dragon Boots", " ", "&7Health: &a+80", "&7Defense: &a+90", " ", "&6Full Set Bonus: Old Blood", "&7Increases the strength of", "&l&9Growth&r&7, &l&9Protection&r&7,", "&l&9Feather Falling&r&7, &l&9Sugar", "&l&9Rush&r&7, and &l&9True Protection&r&7", "&7while worn.", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOOTS");
    public ItemStack WISE_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWEyOTg0Y2YwN2M0OGRhOTcyNDgxNmE4ZmYwODY0YmM2OGJjZTY5NGNlOGJkNmRiMjExMmI2YmEwMzEwNzBkZSJ9fX0="), "&6Wise Dragon Helmet", " ", "&7Health: &a+70", "&7Defense: &a+110", "&7Intelligence: &a+125", " ", "&6Full Set Bonus: Wise Blood", "&7Abilities have &a2/3&7 of the mana cost.", " ", "&8This item can be reforged!", "&6&lLEGENDARY HELMET");
    public ItemStack WISE_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 41, 240, 233), "&6Wise Dragon Chestplate", " ", "&7Health: &a+120", "&7Defense: &a+160", "&7Intelligence: &a+75", " ", "&6Full Set Bonus: Wise Blood", "&7Abilities have &a2/3&7 of the mana cost.", " ", "&8This item can be reforged!", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack WISE_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 41, 240, 233), "&6Wise Dragon Leggings", " ", "&7Health: &a+100", "&7Defense: &a+140", "&7Intelligence: &a+75", " ", "&6Full Set Bonus: Wise Blood", "&7Abilities have &a2/3&7 of the mana cost.", " ", "&8This item can be reforged!", "&6&lLEGENDARY LEGGINGS");
    public ItemStack WISE_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 41, 240, 233), "&6Wise Dragon Boots", " ", "&7Health: &a+60", "&7Defense: &a+90", "&7Intelligence: &a+75", " ", "&6Full Set Bonus: Wise Blood", "&7Abilities have &a2/3&7 of the mana cost.", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOOTS");
    public ItemStack STRONG_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWZkZTA5NjAzYjAyMjViOWQyNGE3M2EwZDNmM2UzYWYyOTA1OGQ0NDhjY2Q3Y2U1YzY3Y2QwMmZhYjBmZjY4MiJ9fX0="), "&6Strong Dragon Helmet", "&7Strength: &c+25", " ", "&7Health: &a+70", "&7Defense: &a+110", " ", "&6Full Set Bonus: Strong Blood", "&7Improves &9Aspect of the End", "&8⋗ &c+75 Damage", " ", "&7Instant Transmission:", "&8⋗ &a+2 &7teleport range", "&8⋗ &a+3 &7seconds", "&8⋗ &c+5❁ Strength &7on cast", " ", "&8This item can be reforged!", "&6&lLEGENDARY HELMET");
    public ItemStack STRONG_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 217, 30, 65), "&6Strong Dragon Helmet", "&7Strength: &c+25", " ", "&7Health: &a+120", "&7Defense: &a+160", " ", "&6Full Set Bonus: Strong Blood", "&7Improves &9Aspect of the End", "&8⋗ &c+75 Damage", " ", "&7Instant Transmission:", "&8⋗ &a+2 &7teleport range", "&8⋗ &a+3 &7seconds", "&8⋗ &c+5❁ Strength &7on cast", " ", "&8This item can be reforged!", "&6&lLEGENDARY CHESTPLATE");
    public ItemStack STRONG_DRAGON_LEGGINGS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), 217, 30, 65), "&6Strong Dragon Leggings", "&7Strength: &c+25", " ", "&7Health: &a+100", "&7Defense: &a+140", " ", "&6Full Set Bonus: Strong Blood", "&7Improves &9Aspect of the End", "&8⋗ &c+75 Damage", " ", "&7Instant Transmission:", "&8⋗ &a+2 &7teleport range", "&8⋗ &a+3 &7seconds", "&8⋗ &c+5❁ Strength &7on cast", " ", "&8This item can be reforged!", "&6&lLEGENDARY LEGGINGS");
    public ItemStack STRONG_DRAGON_BOOTS = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), 217, 30, 65), "&6Strong Dragon Boots", "&7Strength: &c+25", " ", "&7Health: &a+60", "&7Defense: &a+90", " ", "&6Full Set Bonus: Strong Blood", "&7Improves &9Aspect of the End", "&8⋗ &c+75 Damage", " ", "&7Instant Transmission:", "&8⋗ &a+2 &7teleport range", "&8⋗ &a+3 &7seconds", "&8⋗ &c+5❁ Strength &7on cast", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOOTS");
    public ItemStack CELESTIAL_DRAGON_HELMET = Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmEwMjI2ZjQwYzYwYWJjZDljOTMwYWU4MTRkMTlhMWFjMzlmOGYyNTJmOWY1OTE3MGVmYWY1YmY0YWJhNWY1NCJ9fX0="), "&bCelestial Dragon Helmet", "&7Strength: &c+20", "&7Crit Damage: &c+24%", "&7Crit Chance: &c+5%", " ", "&7Health: &a+130", "&7Defense: &a+160", "&7Speed: &a+6", "&7Intelligence: &a+45", " ", "&6Item Ability: &6Celestial Power", "&7All your stats are increased by &a10%", "&7while you gain an additional &b✯ 40 Magic Find&7.", "&7Your &6Aspect of the Dragons", "&7deals &a65% &7more damage.", " ", "&6Item Ability: Celestial Vision", "&7All mobs will slowly take an", "&7absurd amount of damage when", "&7looking directly at them.", "&8Cooldown: &a20s", " ", "&8This item can be reforged!", "&b&lCELESTIAL HELMET");
    public ItemStack CELESTIAL_DRAGON_CHESTPLATE = Utils.addLore(Helper.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), 217, 30, 65), "&bCelestial Dragon Chestplate", " ", "&7Strength: &c+20", "&7Crit Damage: &c+24%", "&7Crit Chance: &c+5%", " ", "&7Health: &a+165", "&7Defense: &a+210", "&7Speed: &a+6", "&7Intelligence: &a+45", " ", "&6Item Ability: &6Celestial Power &e&lRight Click", "&7All your stats are increased by &a10%", "&7while you gain an additional &b✯ 40 Magic Find&7.", "&7Your &6Aspect of the Dragons", "&7deals &a65% &7more damage.", " ", "&8This item can be reforged!", "&b&lCELESTIAL CHESTPLATE");
    public ItemStack RABBIT_HAT = Utils.addLore(new ItemStack(Material.SKULL), "&aRabbit Hat", "&7Speed: &a+5", "&7Intelligence: &a+5", " ", "&8This item can be reforged!", "&a&lUNCOMMON HELMET");

}
