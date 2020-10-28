package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.commands.Item;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Weapons {

    private final SkyblockRemastered plugin;

    public Weapons(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack ASPECT_OF_THE_DRAGONS = Utils.addLore(new ItemStack(Material.DIAMOND_SWORD), "&6&6Aspect of the Dragons", "&7Damage: &c+225", "&7Strength: &c+100", " ", "&6Item Ability: Dragon Rage &e&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a1,050 &7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &3100", "&8Cooldown: &a5s", " ", "&8This item can be reforged!", "&6&lLEGENDARY SWORD");

    public ItemStack EDIBLE_MACE = Utils.addLore(new ItemStack(Material.MUTTON), "&9Edible Mace", " ", "&7Damage: &c+125", "&7Strength: &c+25", " ", "&6Item Ability: ME SMASH HEAD &e&lRIGHT CLICK", "&7Your next attack deals &cdouble", "&cdamage &7and weakens animals,", "&7making them deal&c-35% &7damage", "&7for &a30 &7seconds.", "&8Debuff doesn't stack.", "&8Mana Cost: &3100", " ", "&4☠ &cRequires &5Wolf Slayer 5", "&8This item can be reforged!", "&9&lRARE SWORD");

    public ItemStack POOCH_SWORD = Utils.addLore(new ItemStack(Material.GOLD_SWORD), "&6Pooch Sword", " ", "&7Damage: &c+120", "&7Strength: &c+20", "&7Crit Damage: &c+50%", " ", "&7Speed: &a+5", " ", "&7Deal &c+1 Damage &7per &c50 max ❤&7.", "&7Receive &a-20% &7damage from wolves.", "&7Gain &c+150❁ Strength &7against wolves.", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 6", "&6&lLEGENDARY SWORD");

    public ItemStack SHAMAN_SWORD = Utils.addLore(new ItemStack(Material.IRON_SWORD), "&5Shaman Sword", " ", "&7Damage: &c+100", "&7Strength: &c+20", " ", "&7Speed: &a+5", " ", "&7Deal &c+1 Damage &7per &c50 max ❤&7.", "&7Receive &a-20% &7damage from wolves.", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 3", "&5&lEPIC SWORD");

    public ItemStack ROGUE_SWORD = Utils.addLore(new ItemStack(Material.GOLD_SWORD), "&fRogue Sword", "&7Damage: &c+20", " ", "&6Item Ability: Speed Boost &e&lRIGHT CLICK", "&7Increases your movement &f✦", "&fSpeed&7 by &a+20&7 for &a30", "&7seconds.", "&8Mana Cost: &350", "&8Half speed if already active.", " ", "&8This item can be reforged!", "&f&lCOMMON SWORD");

    public ItemStack LIGHTING_STAFF = Utils.addLore(new ItemStack(Material.BLAZE_ROD), "&6Lightning Staff", "shoots lightning at people", "uses no mana, just a 10 sec cooldown", "deals double your strength", "this is gonna get nerfed it's way too op");

    public ItemStack MAGE_BEAM = Utils.addLore(new ItemStack(Material.IRON_SWORD), "&6Mage Beam", "you already know who it is");
}
