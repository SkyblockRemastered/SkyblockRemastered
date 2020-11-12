package xyz.apollo30.skyblockremastered.events.bagHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.apollo30.skyblockremastered.GUIs.GUIHelper;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.templates.PlayerTemplate;
import xyz.apollo30.skyblockremastered.utils.Helper;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class AccessoryBag implements Listener {

    private final SkyblockRemastered plugin;

    public AccessoryBag(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // 3 for Redstone II
    // 9 for Redstone VI
    // 15 for Redstone IX
    // 21 for Redstone X
    // 27 for Redstone XI
    // 33 for Redstone XII
    // 39 for Redstone XIII
    // 45 for Redstone XIV

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) return;

        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;

        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title.equals("Accessory Bag")) {
            if (item == null) {
                e.setCancelled(true);
                plr.sendMessage(Utils.chat("&cYou cannot put this item in your Accessory Bag."));
                return;
            }

            if (e.getInventory().getSize() == 18) {
                if (e.getSlot() >= 10) {
                    e.setCancelled(true);
                    return;
                }
            }
//            if (e.getInventory().getSize() == 18) {
//                if (e.getSlot() >= 4) {
//                    e.setCancelled(true);
//                    return;
//                }
//            } else if (e.getInventory().getSize() == 18) {
//                if (e.getSlot() >= 10) {
//                    e.setCancelled(true);
//                    return;
//                }
//            } else if (e.getInventory().getSize() == 18) {
//                if (e.getSlot() >= 37) {
//                    e.setCancelled(true);
//                    return;
//                }
//            }

            if (!isAccessory(e.getCurrentItem())) {
                e.setCancelled(true);
                plr.sendMessage(Utils.chat("&cYou cannot put this item in your Accessory Bag."));
                return;
            }
        }

        if (item == null) return;

        if (item.equals(Utils.chat("&aAccessory Bag"))) {
            if (po.getAccessoryBag() == null) {
                Inventory inv = Bukkit.createInventory(plr, 18, "Accessory Bag");
                GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 10, 11, 12, 15, 16, 17, 18);
                GUIHelper.addItem(inv, 262, 1, 13, "&aGo Back");
                GUIHelper.addItem(inv, 166, 1, 14, "&cClose");
                po.setAccessoryBag(Helper.inventoryToString(inv));
            }
            Inventory inv = Helper.stringToInventory(po.getAccessoryBag());
            plr.openInventory(inv);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player plr = (Player) e.getPlayer();
        String title = e.getInventory().getTitle();
        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title.equals("Accessory Bag")) {
            po.setAccessoryBag(Helper.inventoryToString(e.getInventory()));
        }
    }

    private boolean isAccessory(ItemStack item) {
        try {
            if (item == null || !item.hasItemMeta() || item.hasItemMeta() && !item.getItemMeta().hasLore()) return false;
            for (String lore : item.getItemMeta().getLore()) {
                if (lore.contains("ACCESSORY")) return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
