package xyz.apollo30.skyblockremastered.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class Items implements CommandExecutor {

    private final SkyblockRemastered plugin;

    public Items(SkyblockRemastered plugin) {
        this.plugin = plugin;
        plugin.getCommand("items").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat("&cOnly players are authorized to this command"));
            return true;
        }

        Player plr = (Player) sender;

        Inventory inv = Bukkit.createInventory(plr, 54, Utils.chat("Item Menu"));

        Utils.createGlass(inv, "STAINED_GLASS_PANE", 15, 1, 1,2,3,4,5,6,7,8,9,10,18,19,27,28,36,37,45,46,47,53,54);
        Utils.addItem(inv, Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODE2ZWEzNGE2YTZlYzVjMDUxZTY5MzJmMWM0NzFiNzAxMmIyOThkMzhkMTc5ZjFiNDg3YzQxM2Y1MTk1OWNkNCJ9fX0"), "&7Back to Page 1", ""), 48);
        Utils.addItem(inv, Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY1MmUyYjkzNmNhODAyNmJkMjg2NTFkN2M5ZjI4MTlkMmU5MjM2OTc3MzRkMThkZmRiMTM1NTBmOGZkYWQ1ZiJ9fX0"), "&7Go Back", ""), 49);
        Utils.createItemByte(inv, 166, 0, 1, 50, "&cClose");
        Utils.addItem(inv, Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmEzYjhmNjgxZGFhZDhiZjQzNmNhZThkYTNmZTgxMzFmNjJhMTYyYWI4MWFmNjM5YzNlMDY0NGFhNmFiYWMyZiJ9fX0"), "&7Go Forward", ""), 51);
        Utils.addItem(inv, Utils.addLore(Utils.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5ZWM3MWMxMDY4ZWM2ZTAzZDJjOTI4N2Y5ZGE5MTkzNjM5ZjNhNjM1ZTJmYmQ1ZDg3YzJmYWJlNjQ5OSJ9fX0"), "&7Forward to Page ?", ""), 52);

        // Items
        Utils.createInvisibleEnchantedItemByte(inv, 160, 15, 1, 24, " ");
        plr.openInventory(inv);

        return false;
    }
}
