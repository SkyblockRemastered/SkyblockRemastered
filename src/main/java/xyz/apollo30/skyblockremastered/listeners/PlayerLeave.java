package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class PlayerLeave implements Listener {

    private final SkyblockRemastered plugin;

    public PlayerLeave(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player plr = e.getPlayer();
        e.setQuitMessage(Utils.chat("&e" + plr.getName() + " has left the game."));

        plugin.playerManager.savePlayerData(plr);
        plugin.playerManager.playerObjects.remove(plr);

    }

}
