package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class Item implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Item(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("item").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player plr = (Player) sender;

        Inventory inv = Bukkit.createInventory(plr, 54, "Item List (1/1)");
        inv.addItem(plugin.miscs.RADIANT_POWER_ORB, plugin.miscs.MANA_FLUX_POWER_ORB, plugin.miscs.OVERFLUX_POWER_ORB, plugin.miscs.PLASMAFLUX_POWER_ORB);
        inv.addItem(plugin.armor.SUPERIOR_DRAGON_HELMET, plugin.armor.SUPERIOR_DRAGON_BOOTS, plugin.armor.SUPERIOR_DRAGON_LEGGINGS, plugin.armor.SUPERIOR_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.WISE_DRAGON_HELMET, plugin.armor.WISE_DRAGON_BOOTS, plugin.armor.WISE_DRAGON_LEGGINGS, plugin.armor.WISE_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.OLD_DRAGON_HELMET, plugin.armor.OLD_DRAGON_BOOTS, plugin.armor.OLD_DRAGON_LEGGINGS, plugin.armor.OLD_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.PROTECTOR_DRAGON_HELMET, plugin.armor.PROTECTOR_DRAGON_BOOTS, plugin.armor.PROTECTOR_DRAGON_LEGGINGS, plugin.armor.PROTECTOR_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.STRONG_DRAGON_HELMET, plugin.armor.STRONG_DRAGON_BOOTS, plugin.armor.STRONG_DRAGON_LEGGINGS, plugin.armor.STRONG_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.YOUNG_DRAGON_HELMET, plugin.armor.YOUNG_DRAGON_BOOTS, plugin.armor.YOUNG_DRAGON_LEGGINGS, plugin.armor.YOUNG_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.UNSTABLE_DRAGON_HELMET, plugin.armor.UNSTABLE_DRAGON_BOOTS, plugin.armor.UNSTABLE_DRAGON_LEGGINGS, plugin.armor.UNSTABLE_DRAGON_CHESTPLATE);
        inv.addItem(plugin.armor.CELESTIAL_DRAGON_HELMET);
        inv.addItem(plugin.weapons.ASPECT_OF_THE_DRAGONS, plugin.weapons.EDIBLE_MACE, plugin.weapons.POOCH_SWORD, plugin.weapons.ROGUE_SWORD, plugin.weapons.SHAMAN_SWORD, plugin.weapons.ASPECT_OF_THE_END);
        inv.addItem(plugin.armor.HELMET_OF_THE_STARS, plugin.armor.CHESTPLATE_OF_THE_STARS, plugin.armor.LEGGINGS_OF_THE_STARS, plugin.armor.BOOTS_OF_THE_STARS);
        inv.addItem(plugin.miscs.GRAPPLING_HOOK, plugin.armor.TARANTULA_HELMET, plugin.armor.TARANTULA_CHESTPLATE, plugin.armor.TARANTULA_LEGGINGS, plugin.armor.TARANTULA_BOOTS, plugin.bows.RUNAANS_BOW);
        plr.openInventory(inv);

        return false;
    }
}
