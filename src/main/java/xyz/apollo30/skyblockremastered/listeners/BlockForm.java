package xyz.apollo30.skyblockremastered.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import xyz.apollo30.skyblockremastered.SkyblockRemastered;

public class BlockForm implements Listener {

    private final SkyblockRemastered plugin;

    public BlockForm(SkyblockRemastered plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockFade(BlockFormEvent e) {
        e.setCancelled(true);
    }
}
