package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.guis.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Tools {

    private final SkyblockRemastered plugin;

    public Tools(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public static ItemStack STONK = GUIHelper.addLore(new ItemStack(Material.GOLD_PICKAXE), "&5Stonk", "&9Efficiency VI", "&7Increases how quickly your tool", "&7breaks blocks.", "&9Telekinesis I", "&7Block and mob drops go directly", "&7into your inventory.", " ", "&7When mining End Stone with this", "&7pickaxe, Endermites won't spawn", "&7experience will be dropped!", "&a5% &7chance to drop an extra", "&7End Stone block.", " ", "&8This item can be reforged!", "&5&lEPIC PICKAXE");


}
