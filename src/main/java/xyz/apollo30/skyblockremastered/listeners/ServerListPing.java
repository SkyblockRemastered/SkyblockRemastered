package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;
import xyz.apollo30.skyblockremastered.utils.Utils;

public class ServerListPing implements Listener {


    private final SkyblockRemastered plugin;

    public ServerListPing(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {

        e.setMotd(Utils.chat("&6SkyblockRemastered &7- &8[&71.8&8]\n&b&k|&r &9Beta Release Coming Soon &b&k|&r"));
        e.setMaxPlayers(100);

    }

}