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
    public void onServerListPing(ServerListPingEvent event) {

        event.setMotd(Utils.chat("&fSkyblock&0Remastered &7- &8[&71.8&8]\n&f&k|&r &6Beta Release Coming Soon &f&k|&r"));
        event.setMaxPlayers(500);
    }

}
