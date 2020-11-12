package xyz.apollo30.skyblockremastered.events.bagHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PotionBag implements Listener {

    private final SkyblockRemastered plugin;

    public PotionBag(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) return;

        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();

        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title.equals("Potion Bag")) {
            if (e.getInventory().getSize() == 18) {
                if (e.getSlot() >= 10) {
                    e.setCancelled(true);
                    return;
                }
            }

            if (!isPotion(e.getCurrentItem())) {
                e.setCancelled(true);
                plr.sendMessage(Utils.chat("&cYou cannot put this item in your Potion Bag."));
                return;
            }
        }

        if (e.getCurrentItem().hasItemMeta() && !e.getCurrentItem().getItemMeta().getDisplayName().isEmpty() && e.getCurrentItem().getItemMeta().getDisplayName().equals(Utils.chat("&aPotion Bag"))) {
            if (po.getPotionBag() == null) {
                Inventory inv = Bukkit.createInventory(plr, 18, "Potion Bag");
                GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 10, 11, 12, 15, 16, 17, 18);
                GUIHelper.addItem(inv, 262, 1, 13, "&aGo Back");
                GUIHelper.addItem(inv, 166, 1, 14, "&cClose");
                po.setPotionBag(Helper.inventoryToString(inv));
            }
            Inventory inv = Helper.stringToInventory(po.getPotionBag());
            plr.openInventory(inv);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player plr = (Player) e.getPlayer();
        String title = e.getInventory().getTitle();
        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title.equals("Potion Bag")) {
            po.setPotionBag(Helper.inventoryToString(e.getInventory()));
        }
    }

    private boolean isPotion(ItemStack item) {
        try {
            Potion.fromItemStack(item);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
