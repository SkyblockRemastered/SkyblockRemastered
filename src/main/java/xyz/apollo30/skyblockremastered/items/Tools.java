package xyz.apollo30.skyblockremastered.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Tools {

    private final SkyblockRemastered plugin;

    public Tools(SkyblockRemastered plugin) {
        this.plugin = plugin;
    }

    public ItemStack STONK = GUIHelper.addLore(new ItemStack(Material.GOLD_PICKAXE), "&5Stonk", " ", "&8This item can be reforged!", "&5&lEPIC PICKAXE");


}
