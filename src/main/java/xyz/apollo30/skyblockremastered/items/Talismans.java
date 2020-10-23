package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Talismans {

    private final SkyblockRemastered plugin;

    public Talismans(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack HUNTER_RING = Utils.addLore(new ItemStack(Material.SKULL, 1, (byte) 3), "&9Hunter Ring", "&7Gain &b+5% &7Combat XP from", "&7monster kills."," ", "&4☠ &cRequires &5Wolf Slayer 7", "&9&lRARE ACCESSORY");
    public ItemStack HUNTER_TALISMAN = Utils.addLore(new ItemStack(Material.SKULL), "&aHunter Talisman", "&7Gain &b+2% &7Combat XP from", "&7monster kills."," ", "&4☠ &cRequires &5Wolf Slayer 7", "&a&lUNCOMMON ACCESSORY");
    public ItemStack RED_CLAW_ARTIFACT = Utils.addLore(new ItemStack(Material.SKULL), "&5Red Claw Artifact", " ", "&7Crit Damage: &c+5%", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 5", "&5&lEPIC ACCESSORY");
    public ItemStack RED_CLAW_RING = Utils.addLore(new ItemStack(Material.SKULL), "&9Red Claw Ring", " ", "&7Crit Damage: &c+3%", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 5", "&9&lRARE ACCESSORY");
    public ItemStack RED_CLAW_TALISMAN = Utils.addLore(new ItemStack(Material.SKULL), "&aRed Claw Talisman", " ", "&7Crit Damage: &c+1%", " ", "&8This item can be reforged!", "&4☠ &cRequires &5Wolf Slayer 1", "&a&lUNCOMMON ACCESSORY");
}