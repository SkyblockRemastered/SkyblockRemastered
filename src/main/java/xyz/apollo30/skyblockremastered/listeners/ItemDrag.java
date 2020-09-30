package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;
import xyz.apollo30.skyblockremastered.utils.guiutils.SkyblockMenu;

public class ItemDrag implements Listener {

    private final SkyblockRemastered plugin;

    public ItemDrag(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDrag(InventoryDragEvent e) {
        Player plr = (Player) e.getView().getPlayer();
        String title = e.getInventory().getTitle();
        if (e.getCursor().getItemMeta() == null) return;
        String item = e.getCursor().getItemMeta().getDisplayName();

        if (title == null || item == null)
            return;

        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            SkyblockMenu.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
            e.setCancelled(true);
        }
    }

}
