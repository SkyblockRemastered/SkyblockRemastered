package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.GuiUtils;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PlayerDrop implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerDrop(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        Player plr = e.getPlayer();
        String item = e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
        if (item == null)
            return;
        if (item.equals(Utils.chat("&aSkyBlock Menu &7(Right Click)"))) {
            GuiUtils.skyblockMenu(plr, plr.getUniqueId().toString(), plugin.db.getPlayers(), plugin);
            e.setCancelled(true);
        }
    }

}
