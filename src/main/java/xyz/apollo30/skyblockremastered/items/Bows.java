package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Bows {

    private final SkyblockRemastered plugin;

    public Bows(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack RUNAANS_BOW = Utils.addLore(new ItemStack(Material.BOW), "&6Runaan's Bow", "&7Damage: &c+160", "&7Strength: &c+50", " ", "&6Item Ability: &6Triple Shot", "&7Shoot 3 arrows at a time! The 2", "&7extra arrows deal &a40%", "&7percent of the damage.", " ", "&8This item can be reforged!", "&6&lLEGENDARY BOW");
}
