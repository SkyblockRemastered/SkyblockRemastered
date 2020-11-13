package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Drops {

    private final SkyblockRemastered plugin;

    public Drops(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack RENAME_ME = GUIHelper.addLore(new ItemStack(Material.RAW_FISH, 1, (byte) 2), "&9Rename Me", "&eRight-click to view recipes!", " ", "&9RARE");
    public ItemStack OVERFLUX_CAPACITOR = GUIHelper.addLore(new ItemStack(Material.QUARTZ), "&5Overflux Capacitor", "&eRight-click to view recipes!", " ","&5EPIC");
    public ItemStack HAMSTER_WHEEL = Helper.addEnchantGlow(GUIHelper.addLore(new ItemStack(Material.TRAP_DOOR), "&9Hamster Wheel", "&7increases the speed of", "&7your minion by &a50%", "&7for 24 hours!"," ", "&9RARE"));
    public ItemStack RED_CLAW_EGG = GUIHelper.addLore(new ItemStack(Material.MONSTER_EGG, 1, (byte) 3), "&5Red Claw Egg", "&7Drops very rarely from Sven", "&7Packmaster. No one knows what's", "&7in the egg."," ", "&5EPIC");
    public ItemStack GOLDEN_TOOTH = Helper.addEnchantGlow(GUIHelper.addLore(new ItemStack(Material.GOLD_NUGGET), "&9Golden Tooth", "&eRight-click to view recipes!", " ", "&9RARE"));
    public ItemStack WOLF_TOOTH = Helper.addEnchantGlow(GUIHelper.addLore(new ItemStack(Material.GHAST_TEAR), "&aWolf Tooth", "&eRight-click to view recipes!", " ", "&aUNCOMMON"));
    public ItemStack SILENT_PEARL = Helper.addEnchantGlow(GUIHelper.addLore(new ItemStack(Material.ENDER_PEARL), "&9Silent Pearl", "&9&lRARE"));

}
