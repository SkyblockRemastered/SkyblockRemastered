package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Weapons {

    private final SkyblockRemastered plugin;

    public Weapons(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack aspectOfTheDragons = Utils.addLore(new ItemStack(Material.DIAMOND_SWORD), "&6Aspect of the Dragons", "&7Damage: &c+225", "&7Strength: &c+100", "", "&6Item Ability: Dragon Rage &a&lRIGHT CLICK", "&7All Monsters in front of you", "&7take &a&l1,050 &r&7damage. Hit", "&7monsters take large knockback.", "&8Mana Cost: &b100", "&8Cooldown: &a5s");


}
