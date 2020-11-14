package xyz.apollo30.skyblockremastered.events.bagHandler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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

public class QuiverBag implements Listener {

    private final SkyblockRemastered plugin;

    public QuiverBag(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    // 27 for String III
    // 36 for String VI
    // 45 for String IX

    private final Material[] projectiles = {
            Material.ARROW,
            Material.MAGMA_CREAM,
            Material.PRISMARINE_SHARD
    };

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) return;

        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        String item = e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() ? e.getCurrentItem().getItemMeta().getDisplayName() : null;

        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title.equals("Quiver")) {

            if (e.getInventory().getSize() == 27) {
                if (e.getSlot() >= 19) {
                    e.setCancelled(true);
                    return;
                }
            } else if (e.getInventory().getSize() == 36) {
                if (e.getSlot() >= 27) {
                    e.setCancelled(true);
                    return;
                }
            } else if (e.getInventory().getSize() == 45) {
                if (e.getSlot() >= 37) {
                    e.setCancelled(true);
                    return;
                }
            }

            if (!isProjectile(e.getCurrentItem())) {
                e.setCancelled(true);
                plr.sendMessage(Utils.chat("&cYou cannot put this item in your Quiver."));
                return;
            }
        }

        if (item == null) return;

        if (item.equals(Utils.chat("&aQuiver"))) {
            if (po.getQuiverBag() == null) {
                Inventory inv = Bukkit.createInventory(plr, 18, "Quiver");
                GUIHelper.addGlass(inv, "STAINED_GLASS_PANE", 15, 1, 10, 11, 12, 15, 16, 17, 18);
                GUIHelper.addItem(inv, 262, 1, 13, "&aGo Back");
                GUIHelper.addItem(inv, 166, 1, 14, "&cClose");
                for (int i = 0; i < 9; i++) {
                    inv.addItem(new ItemStack(Material.ARROW, 64));
                }
                po.setQuiverBag(Helper.inventoryToString(inv));
            }
            Inventory inv = Helper.stringToInventory(po.getQuiverBag());
            plr.openInventory(inv);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player plr = (Player) e.getPlayer();
        String title = e.getInventory().getTitle();
        PlayerTemplate po = plugin.playerManager.getPlayerData(plr);

        if (title.equals("Quiver")) {
            po.setQuiverBag(Helper.inventoryToString(e.getInventory()));
        }
    }

    private boolean isProjectile(ItemStack item) {
        for (Material material : projectiles) {
            if (material == item.getType()) return true;
        }
        return false;
    }
}
