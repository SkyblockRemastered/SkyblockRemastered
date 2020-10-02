package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class InventoryItemMove implements Listener {

    private final SkyblockRemastered plugin;

    public InventoryItemMove(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void inventoryItemMove(InventoryMoveItemEvent e) {
        String item = e.getItem().getItemMeta().getDisplayName();

//        if (item != null && item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
//            e.setCancelled(true);
//        }
    }

}
